import { Component, ViewChild, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Menu } from './components/menu/menu';
import { Producto } from './components/producto/producto';
import { Mensaje } from './components/mensaje/mensaje';
import { Contacto } from './components/contacto/contacto';
import { ProductoStore } from './services/producto.store';
import { CategoriaProductoStore } from './services/categoria-producto.store';
// 1. CAMBIO: Importamos tu servicio del carrito real
import { CarritoService } from './services/carrito.service'; 

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Menu, Mensaje, Contacto],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  title = 'frontend';
  private readonly productoStore = inject(ProductoStore);
  readonly categoriaStore = inject(CategoriaProductoStore);
  
  // 2. CAMBIO: Inyectamos el servicio del carrito usando la sintaxis moderna inject()
  private readonly carritoService = inject(CarritoService); 

  readonly categoriaSeleccionada = this.productoStore.categoriaSeleccionada;
  readonly categorias = this.categoriaStore.categorias;
  readonly productos = this.productoStore.productos;

  readonly placeholderImage =
    'data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="400" height="300" viewBox="0 0 400 300"><rect width="400" height="300" fill="%23f0f2f5"/><text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="%238a94a6" font-family="Arial" font-size="18">Sin imagen</text></svg>';

  // NOTA: Dejamos el ViewChild por si acaso, aunque ya no se usará para la promo
  @ViewChild('productoComponent') productoComponent!: Producto;

  constructor() {
    this.productoStore.loadProductos();
  }

  onCategoriaChange(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const idCategoria = target.value ? parseInt(target.value, 10) : undefined;
    
    this.productoStore.filtrarPorCategoria(idCategoria);
  }

  // 3. CAMBIO: Reescribimos la función para que use tu CarritoService e idProducto
  agregarAlCarritoPromo(item: any): void {
    // Usamos item.idProducto que viene directamente desde tu @for del app.html
    this.carritoService.agregarProducto(item.idProducto, 1).subscribe({
      next: (carritoActualizado) => {
        console.log('¡Producto de la promo agregado con éxito!', carritoActualizado);
        
        // Forzamos el refresco para que la vista del carrito se entere del cambio 
        // ya que este botón vive fuera del router-outlet
        window.location.reload();
      },
      error: (err) => {
        console.error('Error al intentar agregar el producto de la promoción:', err);
      }
    });
  }
}