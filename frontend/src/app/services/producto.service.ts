import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, InjectionToken, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { Producto } from '../models/producto';

export const PRODUCTOS_API_URL = new InjectionToken<string>('PRODUCTOS_API_URL');

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = inject(PRODUCTOS_API_URL);

  getProductos(idCategoria?: number): Observable<Producto[]> {
    let params = new HttpParams();

    if (idCategoria !== undefined) {
      params = params.set('idCategoria', idCategoria);
    }

    return this.http.get<Producto[]>(this.apiUrl, { params });
  }
}
