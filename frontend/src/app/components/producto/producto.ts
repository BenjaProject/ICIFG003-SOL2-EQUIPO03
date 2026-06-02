import { Component } from '@angular/core';

@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [],
  templateUrl: './producto.html',
  styleUrl: './producto.css'
})
export class Producto {
  // Aqui aparece la lista de productos que luego vendrá del backend
  listaProductos = [
    { id: 1, nombre: 'Producto Aleatorio A', precio: 28, textoImg: 'imagen de producto 1' },
    { id: 2, nombre: 'Producto Aleatorio B', precio: 28000, textoImg: 'imagen de producto 2' },
    { id: 3, nombre: 'Producto Aleatorio C', precio: 2800,  textoImg: 'imagen de producto 3' },
    { id: 4, nombre: 'Producto Aleatorio D', precio: 280, textoImg: 'imagen de producto 4' }
  ];
}