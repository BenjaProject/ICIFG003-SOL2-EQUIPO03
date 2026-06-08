-- Crear el cliente genérico para los carritos
INSERT INTO cliente (rut, nombre_cliente, apellido_cliente, correo, telefono, direccion, fecha_registro) 
VALUES ('1-9', 'Consumidor', 'Generico', 'tienda@petshop.com', '123456789', 'Direccion generica', CURRENT_DATE)
ON CONFLICT (rut) DO NOTHING;

-- Crear las categorías obligatorias del RF03
INSERT INTO categoria_producto (nombre_categoria, descripcion) 
SELECT 'Perros', 'Productos para caninos'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria_producto WHERE nombre_categoria = 'Perros'
);

INSERT INTO categoria_producto (nombre_categoria, descripcion) 
SELECT 'Gatos', 'Productos para felinos'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria_producto WHERE nombre_categoria = 'Gatos'
);

INSERT INTO categoria_producto (nombre_categoria, descripcion) 
SELECT 'Accesorios', 'Juguetes y correas'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria_producto WHERE nombre_categoria = 'Accesorios'
);

-- Crear productos con los datos correctos e imágenes
INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Alimento Pro Plan Perros Adultos 15kg', 'Nutricion de vanguardia para perros adultos de razas medianas. Ayuda a fortalecer el sistema inmunologico y mantiene el pelaje brillante.', 64990.00, 20, 'https://media.istockphoto.com/id/1498237967/es/foto/croquetas-secas-para-mascotas-comida-para-perros-o-gatos-en-una-mesa-vieja.jpg?s=1024x1024&w=is&k=20&c=QjbJ5RmAyTibeFtMbJIjWT2iXDO9LI77FQTOiYaXK4M=', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Perros')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Alimento Pro Plan Perros Adultos 15kg'
);

INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Arnes H-Harness Regulable', 'Arnes de alta resistencia para paseos seguros. Fabricado en poliester suave con cierre de seguridad de 4 puntos y totalmente ajustable.', 14990.00, 15, 'https://http2.mlstatic.com/D_NQ_NP_2X_633873-MLA99948352975_112025-F.webp', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Perros')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Arnes H-Harness Regulable'
);

INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Juguete Mordedor KONG Classic L', 'El estandar de oro de los juguetes para perros. Fabricado con goma roja ultra duradera, ideal para rellenar con premios y calmar la ansiedad.', 12500.00, 30, 'https://images.unsplash.com/photo-1601758228041-f3b2795255f1?auto=format&fit=crop&w=900&q=80', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Perros')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Juguete Mordedor KONG Classic L'
);

-- Productos para la categoría de Gatos
INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Alimento Royal Canin Gatos Adultos Fit 32 7.5kg', 'Alimento completo y equilibrado para Gatos adultos de 1 a 7 anos con actividad moderada.', 38990.00, 12, 'https://http2.mlstatic.com/D_NQ_NP_2X_875339-MLA99885741903_112025-F.webp', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Gatos')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Alimento Royal Canin Gatos Adultos Fit 32 7.5kg'
);

INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Rascador de Gatos Tipo Torre', 'Rascador multifuncional para Gatos, ideal para jugar y afilar sus unas.', 24990.00, 8, 'https://i5.walmartimages.cl/asr/f5de9304-739a-4933-8b49-e183ecd7e68a.69d52c0a7bf10b343ee652caf25049aa.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Gatos')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Rascador de Gatos Tipo Torre'
);

INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Arena Sanitaria Aglutinante Odor Control 10kg', 'Arena sanitaria premium de alta absorcion and control de olores.', 14990.00, 20, 'https://media.falabella.com/sodimacCL/5414180_01/w=1200,h=1200,fit=pad', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Gatos')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Arena Sanitaria Aglutinante Odor Control 10kg'
);

-- Productos para la categoría de Accesorios
INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Comedero Doble de Acero Inoxidable', 'Comedero y bebedero doble con base antideslizante de silicona.', 9990.00, 15, 'https://http2.mlstatic.com/D_NQ_NP_2X_785594-MLC90771004853_082025-F.webp', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Accesorios')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Comedero Doble de Acero Inoxidable'
);

INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Correa Retractil para Perros y Gatos 5m', 'Correa retractil de alta resistencia con boton de freno y bloqueo.', 7990.00, 25, 'https://http2.mlstatic.com/D_NQ_NP_2X_677090-CBT89285295115_082025-F-correa-retractil-para-perro-5m-hasta-50kg-con-plato-plegable.webp', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Accesorios')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Correa Retractil para Perros y Gatos 5m'
);

INSERT INTO producto (nombre_producto, descripcion, precio, stock, imagen, id_categoria)
SELECT 'Juguete Pelota Texturada para Premios', 'Pelota de caucho resistente disenada para rellenar con snacks.', 4990.00, 30, 'https://http2.mlstatic.com/D_NQ_NP_2X_623793-MLA99583725362_122025-F.webp', (SELECT id_categoria FROM categoria_producto WHERE nombre_categoria = 'Accesorios')
WHERE NOT EXISTS (
    SELECT 1 FROM producto WHERE nombre_producto = 'Juguete Pelota Texturada para Premios'
);



