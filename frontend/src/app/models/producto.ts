import { CategoriaProducto } from './categoria-producto';

export interface Producto {
  idProducto: number;
  nombreProducto: string;
  descripcion?: string | null;
  precio: number;
  stock: number;
  imagenUrl?: string | null;
  categoria?: CategoriaProducto | null;
}
