DROP DATABASE IF EXISTS prqdr_db_01;
CREATE DATABASE prqdr_db_01;
USE prqdr_db_01;

CREATE TABLE usuarios(
    documento INT UNSIGNED PRIMARY KEY,
    nombre VARCHAR(60),
    correo VARCHAR(70) UNIQUE,
    contrasenia VARCHAR(255),
    rol ENUM('admin','vendedor'),
    estado TINYINT(1) DEFAULT '1'
);

CREATE TABLE parqueadero (
    id_parqueadero INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    direccion VARCHAR(100),
    carro FLOAT DEFAULT '0',
    moto FLOAT DEFAULT '0',
    camioneta FLOAT DEFAULT '0',
    camiones FLOAT DEFAULT '0',
    estado TINYINT(1) DEFAULT '1'
);

CREATE TABLE parqueadero_vendedores(
    id_usuario INT UNSIGNED,
    id_parqueadero INT UNSIGNED,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(documento),
    FOREIGN KEY (id_parqueadero) REFERENCES parqueadero(id_parqueadero)
);

CREATE TABLE vehiculo_registrados(
    placa CHAR(6) PRIMARY KEY,
    propietario VARCHAR(50),
    tipo ENUM('carro','moto','camioneta','camion')
);

CREATE TABLE tickets(
    id_tickets INT  UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    hora_ingreso DATETIME DEFAULT current_timestamp(), 
    hora_salida DATETIME,
    placa CHAR(6),
    estado_pago TINYINT(1) DEFAULT '0',
    total_pago FLOAT,
    parqueadero INT UNSIGNED,
    FOREIGN KEY (placa) REFERENCES vehiculo_registrados(placa),
    FOREIGN KEY (parqueadero) REFERENCES parqueadero(id_parqueadero)
);

INSERT INTO usuarios VALUES(1088,"braulio","braulio@gmail.com","123","admin","1");
-- Usuarios con empresa
INSERT INTO usuarios (documento, nombre, correo, contrasenia, rol, estado)
VALUES
  (1001, 'Usuario 1', 'usuario1@gmail.com', '123', 'vendedor', 1),
  (1002, 'Usuario 2', 'usuario2@gmail.com', '123', 'vendedor', 1),
  (1003, 'Usuario 3', 'usuario3@gmail.com', '123', 'vendedor', 1),
  (1004, 'Usuario 4', 'usuario4@gmail.com', '123', 'vendedor', 1),
  (1005, 'Usuario 5', 'usuario5@gmail.com', '123', 'vendedor', 1);

-- Usuarios sin empresa
INSERT INTO usuarios (documento, nombre, correo, contrasenia, rol, estado)
VALUES
  (1006, 'Usuario 6', 'usuario6@gmail.com', '123', 'vendedor', 1),
  (1007, 'Usuario 7', 'usuario7@gmail.com', '123', 'vendedor', 1),
  (1008, 'Usuario 8', 'usuario8@gmail.com', '123', 'vendedor', 1),
  (1009, 'Usuario 9', 'usuario9@gmail.com', '123', 'vendedor', 1),
  (1010, 'Usuario 10', 'usuario10@gmail.com', '123', 'vendedor', 1);


-- Empresas
INSERT INTO parqueadero (nombre, direccion, carro, moto, camioneta, camiones)
VALUES
  ('Empresa 1', 'Dirección 1', 50.0, 30.0, 40.0, 20.0),
  ('Empresa 2', 'Dirección 2', 60.0, 35.0, 45.0, 22.0),
  ('Empresa 3', 'Dirección 3', 70.0, 40.0, 55.0, 25.0),
  ('Empresa 4', 'Dirección 4', 80.0, 45.0, 60.0, 30.0),
  ('Empresa 5', 'Dirección 5', 90.0, 50.0, 70.0, 35.0);

-- Asignando usuarios a parqueaderos

INSERT INTO parqueadero_vendedores (id_usuario, id_parqueadero)
VALUES (1001, 1);

-- Vendedor 2 asociado al Parqueadero 2
INSERT INTO parqueadero_vendedores (id_usuario, id_parqueadero)
VALUES (1002, 2);

-- Vendedor 3 asociado al Parqueadero 3
INSERT INTO parqueadero_vendedores (id_usuario, id_parqueadero)
VALUES (1003, 3);

-- Vendedor 4 asociado al Parqueadero 4
INSERT INTO parqueadero_vendedores (id_usuario, id_parqueadero)
VALUES (1004, 4);

-- Vendedor 5 asociado al Parqueadero 5
INSERT INTO parqueadero_vendedores (id_usuario, id_parqueadero)
VALUES (1005, 5);

-- Insertar datos en la tabla vehiculo_registrados
INSERT INTO vehiculo_registrados (placa, propietario, tipo)
VALUES
  ('ABC123', 'Propietario 1', 'carro'),
  ('XYZ789', 'Propietario 2', 'moto'),
  ('LMN456', 'Propietario 3', 'camioneta'),
  ('PQR789', 'Propietario 4', 'camion');


