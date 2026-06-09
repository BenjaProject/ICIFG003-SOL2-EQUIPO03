import { Component, computed, inject, signal } from '@angular/core';

import { CategoriaProductoStore } from '../../services/categoria-producto.store';
import { ProductoStore } from '../../services/producto.store';
import { CarritoService } from '../../services/carrito.service';
import { Carrito } from '../../models/carrito';

@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [],
  templateUrl: './producto.html',
  styleUrl: './producto.css'
})
export class Producto {
  private readonly productoStore = inject(ProductoStore);
  private readonly categoriaStore = inject(CategoriaProductoStore);
  private readonly carritoService = inject(CarritoService);

  readonly productos = this.productoStore.productos;
  readonly loading = this.productoStore.loading;
  readonly error = this.productoStore.error;
  readonly categorias = this.categoriaStore.categorias;
  readonly categoriaSeleccionada = this.productoStore.categoriaSeleccionada;
  readonly noHayProductos = computed(
    () => !this.loading() && this.productos().length === 0
  );
  readonly placeholderImage =
    'data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="400" height="300" viewBox="0 0 400 300"><rect width="400" height="300" fill="%23f0f2f5"/><text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="%238a94a6" font-family="Arial" font-size="18">Sin imagen</text></svg>';

  // Logica reactiva del carrito conectada al backend
  readonly carrito = signal<Carrito | null>(null);

  readonly cantidadCarrito = computed(() => {
    const c = this.carrito();
    if (!c || !c.items) return 0;
    return c.items.reduce((acc, item) => acc + item.cantidad, 0);
  });

  readonly totalPrecio = computed(() => {
    return this.carrito()?.totalAcumulado || 0;
  });

  constructor() {
    this.productoStore.loadProductos();
    this.categoriaStore.loadCategorias();
    this.cargarCarrito();
  }

  cargarCarrito(): void {
    this.carritoService.getCarritoActivo().subscribe({
      next: (car) => this.carrito.set(car),
      error: (err) => console.error('Error al cargar carrito:', err)
    });
  }

  filtrarPorCategoria(idCategoria?: number): void {
    this.productoStore.filtrarPorCategoria(idCategoria);
  }

  agregarAlCarrito(prod: any): void {
    if (prod.stock <= 0) {
      alert('¡No hay stock disponible para este producto!');
      return;
    }

    this.carritoService.agregarProducto(prod.idProducto, 1).subscribe({
      next: (car) => {
        this.carrito.set(car);
        // Recargar productos para refrescar el stock en pantalla
        this.productoStore.loadProductos(this.categoriaSeleccionada());
        // Feedback visual
        this.mostrarFeedback(`¡"${prod.nombreProducto}" agregado al carrito con éxito!`);
      },
      error: (err) => {
        console.error('Error al agregar al carrito:', err);
        const msg = err?.error?.message || 'No se pudo agregar el producto.';
        alert(msg);
      }
    });
  }

  readonly toastMessage = signal<string | null>(null);

  mostrarFeedback(mensaje: string): void {
    this.toastMessage.set(mensaje);
    setTimeout(() => {
      if (this.toastMessage() === mensaje) {
        this.toastMessage.set(null);
      }
    }, 3000);
  }

  vaciarCarrito(): void {
    if (confirm('¿Estás seguro de que deseas vaciar el carrito?')) {
      this.carritoService.vaciarCarrito().subscribe({
        next: (car) => {
          this.carrito.set(car);
          // Recargar productos para refrescar el stock en pantalla
          this.productoStore.loadProductos(this.categoriaSeleccionada());
          alert('¡El carrito ha sido vaciado con éxito! 🐾');
        },
        error: (err) => console.error('Error al vaciar carrito:', err)
      });
    }
  }
}