import { HttpClient } from '@angular/common/http';
import { Injectable, InjectionToken, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { Contacto } from '../models/contacto';

export const CONTACTOS_API_URL = new InjectionToken<string>('CONTACTOS_API_URL');

@Injectable({ providedIn: 'root' })
export class ContactoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = inject(CONTACTOS_API_URL);

  enviarMensaje(contacto: Contacto): Observable<Contacto> {
    return this.http.post<Contacto>(this.apiUrl, contacto);
  }
}
