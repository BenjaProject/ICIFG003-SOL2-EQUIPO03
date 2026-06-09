import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core'; // <-- AGREGADO: ChangeDetectorRef
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
  // <-- AGREGADO: Inyección del disparador de renderizado nativo
  private readonly cdr = inject(ChangeDetectorRef); 

  carrito: Carrito | null = null;
  cargando: boolean = true;

  ngOnInit(): void {
    this.obtenerDatosDelCarrito();
  }

  obtenerDatosDelCarrito(): void {
    this.cargando = true;
    this.carritoService.getCarritoActivo().subscribe({
      next: (res) => {
        this.carrito = res;
        this.cargando = false;
        this.cdr.detectChanges(); // <-- ¡OBLIGA A ANGULAR A REDIBUJAR LA PANTALLA AL INSTANTE!
      },
      error: (err) => {
        console.error('Error al conectar con la API del carrito:', err);
        this.cargando = false;
        this.cdr.detectChanges(); // <-- También obliga a redibujar en caso de error
      }
    });
  }

  eliminarItem(idProducto: number): void {
    this.carritoService.eliminarProducto(idProducto).subscribe({
      next: (carritoActualizado) => {
        this.carrito = carritoActualizado;
        this.cdr.detectChanges(); // <-- Actualiza la tabla inmediatamente al borrar
      },
      error: (err) => console.error('Error al eliminar ítem:', err)
    });
  }

  vaciarCarritoCompleto(): void {
    this.carritoService.vaciarCarrito().subscribe({
      next: (carritoVacio) => {
        this.carrito = carritoVacio;
        this.cdr.detectChanges(); // <-- Actualiza la interfaz inmediatamente al vaciar
      },
      error: (err) => console.error('Error al vaciar la cesta:', err)
    });
  }
}