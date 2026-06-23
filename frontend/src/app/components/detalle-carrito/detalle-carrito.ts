import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CarritoService } from '../../services/carrito.service';
import { Carrito } from '../../models/carrito';

@Component({
  selector: 'app-detalle-carrito',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './detalle-carrito.html',
  styleUrl: './detalle-carrito.css'
})
export class DetalleCarritoComponent implements OnInit {
  private readonly carritoService = inject(CarritoService);

  readonly carrito = signal<Carrito | null>(null);
  readonly cargando = signal<boolean>(true);
  readonly error = signal<string | null>(null);

  private setCarritoOrdenado(car: Carrito | null): void {
    if (car && car.items) {
      car.items.sort((a, b) => {
        const idA = a.idDetalleCarrito ?? 0;
        const idB = b.idDetalleCarrito ?? 0;
        if (idA !== idB) {
          return idA - idB;
        }
        return (a.producto.idProducto ?? 0) - (b.producto.idProducto ?? 0);
      });
    }
    this.carrito.set(car);
  }

  ngOnInit(): void {
    this.obtenerDatosDelCarrito();
  }

  obtenerDatosDelCarrito(): void {
    this.cargando.set(true);
    this.error.set(null);
    this.carritoService.getCarritoActivo().subscribe({
      next: (res) => {
        this.setCarritoOrdenado(res);
        this.cargando.set(false);
      },
      error: (err) => {
        console.error('Error al conectar con la API del carrito:', err);
        this.error.set('Servicio temporalmente no disponible. Estamos trabajando para volver pronto.');
        this.cargando.set(false);
      }
    });
  }

  eliminarItem(idProducto: number): void {
    this.carritoService.eliminarProducto(idProducto).subscribe({
      next: (carritoActualizado) => {
        this.setCarritoOrdenado(carritoActualizado);
      },
      error: (err) => {
        console.error('Error al eliminar ítem:', err);
        alert('No se pudo eliminar el producto del carrito. Por favor, intenta de nuevo.');
      }
    });
  }

  vaciarCarritoCompleto(): void {
    if (confirm('¿Estás seguro de que deseas vaciar el carrito?')) {
      this.carritoService.vaciarCarrito().subscribe({
        next: (carritoVacio) => {
          this.setCarritoOrdenado(carritoVacio);
          alert('El carrito ha sido vaciado con éxito.');
        },
        error: (err) => {
          console.error('Error al vaciar la cesta:', err);
          alert('No se pudo vaciar el carrito. Por favor, intenta de nuevo.');
        }
      });
    }
  }

  incrementarCantidad(item: any): void {
    if (item.producto.stock <= 0) {
      alert('¡No queda más stock disponible en bodega para este producto!');
      return;
    }
    this.carritoService.agregarProducto(item.producto.idProducto, 1).subscribe({
      next: (carritoActualizado) => {
        this.setCarritoOrdenado(carritoActualizado);
      },
      error: (err) => {
        console.error('Error al incrementar cantidad:', err);
        const msg = err?.error?.message || 'No se pudo agregar más unidades de este producto.';
        alert(msg);
      }
    });
  }

  decrementarCantidad(item: any): void {
    if (item.cantidad <= 1) {
      if (confirm(`¿Deseas quitar "${item.producto.nombreProducto}" de tu carrito?`)) {
        this.eliminarItem(item.producto.idProducto);
      }
      return;
    }

    this.carritoService.restarProducto(item.producto.idProducto, 1).subscribe({
      next: (carritoActualizado) => {
        this.setCarritoOrdenado(carritoActualizado);
      },
      error: (err) => {
        console.error('Error al decrementar cantidad:', err);
        alert('No se pudo reducir la cantidad del producto. Intenta nuevamente.');
      }
    });
  }

  realizarCompra(): void {
    const items = this.carrito()?.items;
    if (!items || items.length === 0) {
      alert('No puedes realizar una compra con el carrito vacío.');
      return;
    }

    if (!confirm('¿Estás seguro de que deseas efectuar la compra de tu pedido?')) {
      return;
    }

    this.carritoService.comprarCarrito().subscribe({
      next: (nuevoCarritoVacio) => {
        this.setCarritoOrdenado(nuevoCarritoVacio);
        alert('Compra realizada con éxito. Tu pedido ha sido procesado.');
      },
      error: (err) => {
        console.error('Error al procesar la compra:', err);
        alert('Ocurrió un error al procesar tu compra. Por favor, verifica tu conexión e intenta nuevamente.');
      }
    });
  }
}