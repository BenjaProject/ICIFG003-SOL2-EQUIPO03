import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Menu } from './components/menu/menu';
import { Producto } from './components/producto/producto';
import { Mensaje } from './components/mensaje/mensaje';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Menu, Producto, Mensaje],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  title = 'frontend';
}