import { HttpClient } from '@angular/common/http';
import { Injectable, InjectionToken, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { CategoriaProducto } from '../models/categoria-producto';

export const CATEGORIAS_API_URL = new InjectionToken<string>('CATEGORIAS_API_URL');

@Injectable({ providedIn: 'root' })
export class CategoriaProductoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = inject(CATEGORIAS_API_URL);

  getCategorias(): Observable<CategoriaProducto[]> {
    return this.http.get<CategoriaProducto[]>(this.apiUrl);
  }
}
