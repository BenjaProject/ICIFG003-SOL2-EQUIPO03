import { Routes } from '@angular/router';
import { DetalleCarritoComponent } from './components/detalle-carrito/detalle-carrito';
import { Producto } from './components/producto/producto';

export const routes: Routes = [
  { path: '', redirectTo: '/catalogo', pathMatch: 'full' },
  { path: 'catalogo', component: Producto },
  { path: 'carrito', component: DetalleCarritoComponent },
  { path: '**', redirectTo: '/catalogo' }
];