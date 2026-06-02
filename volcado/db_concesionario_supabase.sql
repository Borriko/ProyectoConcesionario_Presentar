-- =====================================
-- TABLA USUARIOS
-- =====================================

CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          password VARCHAR(100) NOT NULL,
                          email VARCHAR(150) UNIQUE NOT NULL,
                          url_imagen VARCHAR(255),
                          dinero DECIMAL(10,2) DEFAULT 0,
                          fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- TABLA COCHES
-- =====================================

CREATE TABLE coches (
                        id SERIAL PRIMARY KEY,
                        marca VARCHAR(50) NOT NULL,
                        modelo VARCHAR(50) NOT NULL,
                        tipo_vehiculo VARCHAR(50) NOT NULL,
                        anio INT NOT NULL,
                        color VARCHAR(30),
                        kilometros INT NOT NULL,
                        combustible VARCHAR(30),
                        transmision VARCHAR(30),
                        potencia INT,
                        precio DECIMAL(10,2) NOT NULL,
                        descripcion TEXT,
                        estado VARCHAR(20) DEFAULT 'DISPONIBLE',
                        fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- TABLA COMPRAS
-- =====================================

CREATE TABLE compras (
                         id SERIAL PRIMARY KEY,
                         usuario_id INT NOT NULL,
                         coche_id INT NOT NULL,
                         fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         precio_final DECIMAL(10,2),

                         CONSTRAINT fk_usuario
                             FOREIGN KEY(usuario_id)
                                 REFERENCES usuarios(id)
                                 ON DELETE CASCADE,

                         CONSTRAINT fk_coche
                             FOREIGN KEY(coche_id)
                                 REFERENCES coches(id)
                                 ON DELETE CASCADE
);

-- =====================================
-- TABLA IMAGENES
-- =====================================

CREATE TABLE imagenes_coches (
                                 id SERIAL PRIMARY KEY,
                                 coche_id INT NOT NULL,
                                 ruta_imagen VARCHAR(255) NOT NULL,

                                 CONSTRAINT fk_imagen_coche
                                     FOREIGN KEY(coche_id)
                                         REFERENCES coches(id)
                                         ON DELETE CASCADE
);

-- =====================================
-- USUARIOS
-- =====================================

INSERT INTO usuarios (nombre, password, email, dinero) VALUES
                                                           ('Carlos Garcia', '333', 'carlos@gmail.com', 15000.00),
                                                           ('Ana Lopez', '123', 'ana@gmail.com', 8000.00),
                                                           ('Luis Martinez', '111', 'luis@gmail.com', 12000.00),
                                                           ('Marta Ruiz', '222', 'marta@gmail.com', 20000.00),
                                                           ('Pedro Sanchez', '444', 'pedro@gmail.com', 5000.00),
                                                           ('Lucia Gomez', '555', 'lucia@gmail.com', 30000.00),
                                                           ('Javier Torres', '666', 'javier@gmail.com', 18000.00),
                                                           ('Elena Diaz', '777', 'elena@gmail.com', 22000.00),
                                                           ('David Aguilera', '111', 'david@gmail.com', 9999.00);

-- =====================================
-- COCHES
-- =====================================

INSERT INTO coches
(marca, modelo, tipo_vehiculo, anio, color, kilometros, combustible, transmision, potencia, precio, descripcion, estado)
VALUES
    ('Mercedes', 'Clase E', 'Berlina', 2008, 'Negro', 190000, 'Diesel', 'Automatica', 224, 8999.99,
     'Mercedes clase e del 2008 muy bien conservado muchos extras', 'DISPONIBLE'),

    ('Mercedes', 'Clase C', 'Berlina', 2020, 'Blanco', 42000, 'Diesel', 'Automatica', 194, 32999.99,
     'Mercedes Clase C con acabado AMG', 'DISPONIBLE'),

    ('Mercedes', 'Clase E', 'Berlina', 2017, 'Gris', 98000, 'Diesel', 'Automatica', 220, 27999.99,
     'Mercedes Clase E ideal para viajes largos', 'DISPONIBLE'),

    ('Mercedes', 'GLA 200', 'SUV', 2021, 'Rojo', 31000, 'Gasolina', 'Automatica', 163, 35999.99,
     'SUV compacto premium muy equipado', 'RESERVADO'),

    ('Mercedes', 'GLC 300', 'SUV', 2022, 'Azul', 18000, 'Hibrido', 'Automatica', 258, 51999.99,
     'Mercedes GLC híbrido enchufable', 'DISPONIBLE'),

    ('Mercedes', 'AMG GT', 'Deportivo', 2019, 'Amarillo', 25000, 'Gasolina', 'Automatica', 530, 104999.99,
     'Deportivo AMG con motor V8 biturbo', 'DISPONIBLE'),

    ('Mercedes', 'Sprinter', 'Furgoneta', 2016, 'Blanco', 145000, 'Diesel', 'Manual', 143, 16999.99,
     'Furgoneta ideal para trabajo', 'DISPONIBLE'),

    ('Mercedes', 'Clase S', 'Berlina', 2023, 'Negro', 9000, 'Hibrido', 'Automatica', 367, 89999.99,
     'Lujo absoluto con interior premium', 'RESERVADO'),

    ('Mercedes', 'Clase S', 'Berlina', 1993, 'Negro', 220000, 'Diesel', 'Automatica', 243, 12999.99,
     'Mercedes clase s del 1993 muy bien conservado a pesar de los años', 'DISPONIBLE'),

    ('BMW', 'Serie 1', 'Compacto', 2015, 'Blanco', 120000, 'Diesel', 'Manual', 116, 12999.99,
     'BMW Serie 1 económico y fiable', 'DISPONIBLE'),

    ('BMW', 'X5', 'SUV', 2021, 'Negro', 35000, 'Diesel', 'Automatica', 286, 58999.99,
     'BMW X5 con paquete M', 'DISPONIBLE'),

    ('BMW', 'i4', 'Electrico', 2023, 'Azul', 8000, 'Electrico', 'Automatica', 340, 54999.99,
     'BMW eléctrico de última generación', 'DISPONIBLE'),

    ('Audi', 'Q3', 'SUV', 2020, 'Gris', 46000, 'Diesel', 'Automatica', 150, 30999.99,
     'Audi Q3 muy equipado', 'DISPONIBLE'),

    ('Audi', 'A6', 'Berlina', 2018, 'Negro', 89000, 'Diesel', 'Automatica', 204, 28999.99,
     'Audi A6 elegante y cómodo', 'DISPONIBLE'),

    ('Audi', 'TT', 'Deportivo', 2016, 'Rojo', 67000, 'Gasolina', 'Manual', 230, 24999.99,
     'Audi TT deportivo compacto', 'VENDIDO'),

    ('Tesla', 'Model S', 'Electrico', 2021, 'Blanco', 27000, 'Electrico', 'Automatica', 670, 79999.99,
     'Tesla Model S Long Range', 'DISPONIBLE'),

    ('Tesla', 'Model Y', 'SUV', 2023, 'Negro', 12000, 'Electrico', 'Automatica', 351, 48999.99,
     'SUV eléctrico muy espacioso', 'DISPONIBLE'),

    ('Toyota', 'Corolla', 'Compacto', 2014, 'Plateado', 156000, 'Gasolina', 'Manual', 110, 9999.99,
     'Toyota Corolla muy fiable', 'DISPONIBLE'),

    ('Toyota', 'RAV4', 'SUV', 2022, 'Verde', 22000, 'Hibrido', 'Automatica', 218, 37999.99,
     'Toyota RAV4 híbrido', 'DISPONIBLE'),

    ('Ford', 'Mustang', 'Deportivo', 2019, 'Azul', 39000, 'Gasolina', 'Manual', 450, 45999.99,
     'Ford Mustang GT V8', 'DISPONIBLE'),

    ('Ford', 'Focus', 'Compacto', 2013, 'Blanco', 175000, 'Diesel', 'Manual', 95, 6999.99,
     'Ford Focus económico', 'DISPONIBLE'),

    ('Volkswagen', 'Golf', 'Compacto', 2017, 'Gris', 87000, 'Diesel', 'Manual', 115, 14999.99,
     'Volkswagen Golf en buen estado', 'DISPONIBLE'),

    ('Volkswagen', 'Tiguan', 'SUV', 2021, 'Blanco', 33000, 'Diesel', 'Automatica', 200, 35999.99,
     'SUV familiar muy cómodo', 'DISPONIBLE');

-- =====================================
-- COMPRAS
-- =====================================

INSERT INTO compras (usuario_id, coche_id, precio_final) VALUES
                                                             (9, 1, 8999.99),
                                                             (2, 2, 32999.99),
                                                             (3, 3, 27999.99),
                                                             (4, 4, 35999.99),
                                                             (5, 5, 51999.99),
                                                             (6, 6, 104999.99),
                                                             (7, 7, 16999.99),
                                                             (8, 8, 89999.99),
                                                             (9, 9, 12999.99);

-- =====================================
-- IMAGENES
-- =====================================

INSERT INTO imagenes_coches (coche_id, ruta_imagen) VALUES
                                                        (1, 'img/coches/img_mercedes_clase_e_id_1.png'),
                                                        (2, 'img/coches/img_mercedes_clase_c_id_2.png'),
                                                        (9, 'img/coches/img_mercedes_clase_s_id_9.png');