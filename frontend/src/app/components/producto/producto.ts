import { Component, computed, inject } from '@angular/core';

import { CategoriaProductoStore } from '../../services/categoria-producto.store';
import { ProductoStore } from '../../services/producto.store';

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

  constructor() {
    this.productoStore.loadProductos();
    this.categoriaStore.loadCategorias();
  }

  filtrarPorCategoria(idCategoria?: number): void {
    this.productoStore.filtrarPorCategoria(idCategoria);
  }
}