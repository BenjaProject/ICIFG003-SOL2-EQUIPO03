import { DestroyRef, Injectable, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { Producto } from '../models/producto';
import { ProductoService } from './producto.service';

@Injectable({ providedIn: 'root' })
export class ProductoStore {
  private readonly productoService = inject(ProductoService);
  private readonly destroyRef = inject(DestroyRef);

  readonly productos = signal<Producto[]>([]);
  readonly loading = signal<boolean>(false);
  readonly error = signal<string | null>(null);
  readonly categoriaSeleccionada = signal<number | undefined>(undefined);

  loadProductos(idCategoria?: number): void {
    this.loading.set(true);
    this.error.set(null);

    this.productoService
      .getProductos(idCategoria)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (productos) => {
          const sorted = (productos ?? []).sort((a, b) => a.idProducto - b.idProducto);
          this.productos.set(sorted);
          this.loading.set(false);
        },
        error: () => {
          this.error.set('No se pudo cargar el catalogo.');
          this.loading.set(false);
        }
      });
  }

  filtrarPorCategoria(idCategoria?: number): void {
    this.categoriaSeleccionada.set(idCategoria);
    this.loadProductos(idCategoria);
  }
}
