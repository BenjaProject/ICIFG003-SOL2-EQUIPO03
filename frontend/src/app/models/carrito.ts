import { Producto } from './producto';

export interface DetalleCarrito {
  idDetalleCarrito?: number;
  producto: Producto;
  cantidad: number;
  precioUnitario: number;
}

export interface Carrito {
  idCarrito?: number;
  fechaCreacion: string;
  items: DetalleCarrito[];
  totalAcumulado: number;
}
