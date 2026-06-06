import { Component, ViewChild, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Menu } from './components/menu/menu';
import { Producto } from './components/producto/producto';
import { Mensaje } from './components/mensaje/mensaje';
import { ProductoStore } from './services/producto.store';
import { CategoriaProductoStore } from './services/categoria-producto.store';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Menu, Producto, Mensaje],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  title = 'frontend';
  private readonly productoStore = inject(ProductoStore);
  readonly categoriaStore = inject(CategoriaProductoStore);
  readonly categoriaSeleccionada = this.productoStore.categoriaSeleccionada;
  readonly categorias = this.categoriaStore.categorias;

  @ViewChild('productoComponent') productoComponent!: Producto;

  onCategoriaChange(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const idCategoria = target.value ? parseInt(target.value, 10) : undefined;
    
    this.productoStore.filtrarPorCategoria(idCategoria);
  }
}