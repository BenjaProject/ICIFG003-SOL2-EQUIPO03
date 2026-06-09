import { Component, ViewChild, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Menu } from './components/menu/menu';
import { Producto } from './components/producto/producto';
import { Mensaje } from './components/mensaje/mensaje';
import { Contacto } from './components/contacto/contacto';
import { ProductoStore } from './services/producto.store';
import { CategoriaProductoStore } from './services/categoria-producto.store';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Menu, Producto, Mensaje, Contacto],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  title = 'frontend';
  private readonly productoStore = inject(ProductoStore);
  readonly categoriaStore = inject(CategoriaProductoStore);
  readonly categoriaSeleccionada = this.productoStore.categoriaSeleccionada;
  readonly categorias = this.categoriaStore.categorias;
  readonly productos = this.productoStore.productos;

  readonly placeholderImage =
    'data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="400" height="300" viewBox="0 0 400 300"><rect width="400" height="300" fill="%23f0f2f5"/><text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="%238a94a6" font-family="Arial" font-size="18">Sin imagen</text></svg>';

  @ViewChild('productoComponent') productoComponent!: Producto;

  onCategoriaChange(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const idCategoria = target.value ? parseInt(target.value, 10) : undefined;
    
    this.productoStore.filtrarPorCategoria(idCategoria);
  }

  agregarAlCarritoPromo(item: any): void {
    if (this.productoComponent) {
      this.productoComponent.agregarAlCarrito(item);
    }
  }
}