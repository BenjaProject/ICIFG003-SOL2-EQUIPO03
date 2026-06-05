import { provideHttpClient } from '@angular/common/http';
import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { environmentCategorias, environmentProductos } from './environments/environments';
import { CATEGORIAS_API_URL } from './services/categoria-producto.service';
import { PRODUCTOS_API_URL } from './services/producto.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(),
    { provide: PRODUCTOS_API_URL, useValue: environmentProductos.apiUrl },
    { provide: CATEGORIAS_API_URL, useValue: environmentCategorias.apiUrl }
  ]
};
