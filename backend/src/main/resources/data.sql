-- Crear el cliente genérico para los carritos
INSERT INTO cliente (rut, nombre_cliente, apellido_cliente, correo, telefono, direccion, fecha_registro) 
VALUES ('1-9', 'Consumidor', 'Genérico', 'tienda@petshop.com', '123456789', 'Dirección genérica', CURRENT_DATE);

-- Crear las categorías obligatorias del RF03
INSERT INTO categoria_producto (nombre_categoria, descripcion) VALUES ('Perros', 'Productos para caninos');
INSERT INTO categoria_producto (nombre_categoria, descripcion) VALUES ('Gatos', 'Productos para felinos');
INSERT INTO categoria_producto (nombre_categoria, descripcion) VALUES ('Accesorios', 'Juguetes y correas');