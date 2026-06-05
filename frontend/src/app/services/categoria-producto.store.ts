import { DestroyRef, Injectable, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { CategoriaProducto } from '../models/categoria-producto';
import { CategoriaProductoService } from './categoria-producto.service';

@Injectable({ providedIn: 'root' })
export class CategoriaProductoStore {
  private readonly categoriaService = inject(CategoriaProductoService);
  private readonly destroyRef = inject(DestroyRef);

  readonly categorias = signal<CategoriaProducto[]>([]);
  readonly loading = signal<boolean>(false);
  readonly error = signal<string | null>(null);

  loadCategorias(): void {
    this.loading.set(true);
    this.error.set(null);

    this.categoriaService
      .getCategorias()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (categorias) => {
          this.categorias.set(categorias ?? []);
          this.loading.set(false);
        },
        error: () => {
          this.error.set('No se pudieron cargar las categorias.');
          this.loading.set(false);
        }
      });
  }
}
