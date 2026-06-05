-- Crear el cliente genérico para los carritos
INSERT INTO cliente (rut, nombre_cliente, apellido_cliente, correo, telefono, direccion, fecha_registro) 
VALUES ('1-9', 'Consumidor', 'Genérico', 'tienda@petshop.com', '123456789', 'Dirección genérica', CURRENT_DATE)
ON CONFLICT (rut) DO NOTHING;
-- Crear las categorías obligatorias del RF03
INSERT INTO categoria_producto (nombre_categoria, descripcion) VALUES ('Perros', 'Productos para caninos');
INSERT INTO categoria_producto (nombre_categoria, descripcion) VALUES ('Gatos', 'Productos para felinos');
INSERT INTO categoria_producto (nombre_categoria, descripcion) VALUES ('Accesorios', 'Juguetes y correas');
INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
VALUES 
('Alimento Pro Plan Perros Adultos 15kg', 'Nutricion de vanguardia para perros adultos de razas medianas. Ayuda a fortalecer el sistema inmunologico y mantiene el pelaje brillante.', 64990.00, 20, 'https://images.unsplash.com/photo-1589924691106-073b69789e06?auto=format&fit=crop&w=900&q=80', 1),

('Arnes H-Harness Regulable', 'Arnes de alta resistencia para paseos seguros. Fabricado en poliester suave con cierre de seguridad de 4 puntos y totalmente ajustable.', 14990.00, 15, 'https://images.unsplash.com/photo-1601758228041-f3b2795255f1?auto=format&fit=crop&w=900&q=80', 1),

('Juguete Mordedor KONG Classic L', 'El estandar de oro de los juguetes para perros. Fabricado con goma roja ultra duradera, ideal para rellenar con premios y calmar la ansiedad.', 12500.00, 30, 'https://images.unsplash.com/photo-1507146426996-ef05306b995a?auto=format&fit=crop&w=900&q=80', 1);

UPDATE producto SET imagen = 'https://media.istockphoto.com/id/1498237967/es/foto/croquetas-secas-para-mascotas-comida-para-perros-o-gatos-en-una-mesa-vieja.jpg?s=1024x1024&w=is&k=20&c=QjbJ5RmAyTibeFtMbJIjWT2iXDO9LI77FQTOiYaXK4M='
WHERE nombre_producto = 'Alimento Pro Plan Perros Adultos 15kg';

UPDATE producto SET imagen = 'https://http2.mlstatic.com/D_NQ_NP_2X_633873-MLA99948352975_112025-F.webp'
WHERE nombre_producto IN ('Arnes H-Harness Regulable', 'Arnés H-Harness Regulable');

UPDATE producto SET imagen = 'https://images.unsplash.com/photo-1601758228041-f3b2795255f1?auto=format&fit=crop&w=900&q=80'
WHERE nombre_producto = 'Juguete Mordedor KONG Classic L';

UPDATE producto SET nombre_producto = 'Arnes H-Harness Regulable'
WHERE nombre_producto = 'Arnés H-Harness Regulable';
