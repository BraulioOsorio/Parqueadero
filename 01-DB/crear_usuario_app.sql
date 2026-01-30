-- Crear usuario para la API (evita el error 1698 de root en Linux)
-- Ejecutar como root: sudo mysql < crear_usuario_app.sql

CREATE USER IF NOT EXISTS 'prqdr_user'@'localhost' IDENTIFIED BY 'prqdr_pass';
GRANT ALL PRIVILEGES ON prqdr_db_01.* TO 'prqdr_user'@'localhost';
FLUSH PRIVILEGES;
