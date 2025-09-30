-- Tabla principal: hu_cat_moneda
CREATE TABLE hu_cat_moneda (
    num_cia INTEGER , 
    clave_moneda VARCHAR(10),
    descripcion VARCHAR(100),
    simbolo VARCHAR(10),
    estatus VARCHAR(10),
    CONSTRAINT pk_moneda PRIMARY KEY (num_cia, clave_moneda)
);

-- Índice adicional por clave_moneda (opcional si se consulta frecuentemente)
CREATE INDEX idx_moneda_clave ON hu_cat_moneda(clave_moneda);

-- Tabla dependiente: hu_empls
CREATE TABLE hu_empls (
    num_emp INTEGER NOT NULL, 
    num_cia INTEGER NOT NULL,             
    clave_moneda VARCHAR(10) NOT NULL,
    nombre VARCHAR(50),
    apellido_paterno VARCHAR(50),
    apellido_materno VARCHAR(50),
    puesto VARCHAR(50),
    CONSTRAINT pk_empls PRIMARY KEY (num_cia, num_emp),
    CONSTRAINT fk_empls_moneda FOREIGN KEY (num_cia, clave_moneda)
    REFERENCES hu_cat_moneda(num_cia, clave_moneda)
);

-- Índices adicionales para búsquedas frecuentes
CREATE INDEX idx_empls_num_cia ON hu_empls(num_cia);
CREATE INDEX idx_empls_clave_moneda ON hu_empls(clave_moneda);

-- Insertar monedas
INSERT INTO hu_cat_moneda (num_cia, clave_moneda, descripcion, simbolo, estatus) VALUES
(1, 'MXN', 'Peso Mexicano', '$', 'A'),

(2, 'USD', 'Dólar Estadounidense', '$', 'A'),
(3, 'COL','Peso Colombiano', '$','A');

-- Insertar empleados
INSERT INTO hu_empls (num_emp, num_cia, clave_moneda, nombre, apellido_paterno, apellido_materno, puesto) VALUES
(1001, 1, 'MXN', 'Juan', 'Pérez', 'Gómez', 'Analista'),
(1002, 2, 'USD', 'Ana', 'López', 'Martínez', 'Desarrolladora');