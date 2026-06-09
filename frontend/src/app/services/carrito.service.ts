import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Carrito } from '../models/carrito';

@Injectable({ providedIn: 'root' })
export class CarritoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/v1/entities/carritos';

  getCarritoActivo(): Observable<Carrito> {
    return this.http.get<Carrito>(`${this.apiUrl}/`);
  }

  agregarProducto(idProducto: number, cantidad: number = 1): Observable<Carrito> {
    let params = new HttpParams()
      .set('idProducto', idProducto.toString())
      .set('cantidad', cantidad.toString());
    return this.http.post<Carrito>(`${this.apiUrl}/agregar`, null, { params });
  }

  eliminarProducto(idProducto: number): Observable<Carrito> {
    let params = new HttpParams().set('idProducto', idProducto.toString());
    return this.http.delete<Carrito>(`${this.apiUrl}/eliminar`, { params });
  }

  vaciarCarrito(): Observable<Carrito> {
    return this.http.delete<Carrito>(`${this.apiUrl}/vaciar`);
  }
}
