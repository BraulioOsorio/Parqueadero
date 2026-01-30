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
    placa CHAR(6) NOT NULL,
    id_parqueadero INT UNSIGNED NOT NULL,
    propietario VARCHAR(50),
    tipo ENUM('carro','moto','camioneta','camion'),
    PRIMARY KEY (placa, id_parqueadero),
    FOREIGN KEY (id_parqueadero) REFERENCES parqueadero(id_parqueadero)
);

CREATE TABLE tickets(
    id_tickets INT  UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    hora_ingreso DATETIME DEFAULT current_timestamp(), 
    hora_salida DATETIME,
    placa CHAR(6),
    estado_pago TINYINT(1) DEFAULT '0',
    total_pago FLOAT,
    parqueadero INT UNSIGNED,
    FOREIGN KEY (placa, parqueadero) REFERENCES vehiculo_registrados(placa, id_parqueadero),
    FOREIGN KEY (parqueadero) REFERENCES parqueadero(id_parqueadero)
);

INSERT INTO usuarios VALUES(1088,"admin","admin@gmail.com","07021Xd","admin","1");
