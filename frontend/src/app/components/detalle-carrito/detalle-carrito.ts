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

  ngOnInit(): void {
    this.obtenerDatosDelCarrito();
  }

  obtenerDatosDelCarrito(): void {
    this.cargando.set(true);
    this.error.set(null);
    this.carritoService.getCarritoActivo().subscribe({
      next: (res) => {
        this.carrito.set(res);
        this.cargando.set(false);
      },
      error: (err) => {
        console.error('Error al conectar con la API del carrito:', err);
        this.error.set('No se pudo conectar con el servidor para obtener el carrito.');
        this.cargando.set(false);
      }
    });
  }

  eliminarItem(idProducto: number): void {
    this.carritoService.eliminarProducto(idProducto).subscribe({
      next: (carritoActualizado) => {
        this.carrito.set(carritoActualizado);
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
          this.carrito.set(carritoVacio);
          alert('¡El carrito ha sido vaciado con éxito! 🐾');
        },
        error: (err) => {
          console.error('Error al vaciar la cesta:', err);
          alert('No se pudo vaciar el carrito. Por favor, intenta de nuevo.');
        }
      });
    }
  }

  realizarCompra(): void {
    const items = this.carrito()?.items;
    if (!items || items.length === 0) {
      alert('No puedes realizar una compra con el carrito vacío.');
      return;
    }

    this.carritoService.comprarCarrito().subscribe({
      next: (nuevoCarritoVacio) => {
        this.carrito.set(nuevoCarritoVacio);
        alert('¡Compra realizada con éxito! Tu pedido ha sido procesado. 🐶🎉');
      },
      error: (err) => {
        console.error('Error al procesar la compra:', err);
        alert('Ocurrió un error al procesar tu compra. Por favor, verifica tu conexión e intenta nuevamente.');
      }
    });
  }
}