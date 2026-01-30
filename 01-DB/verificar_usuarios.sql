-- Verificar usuarios en la base de datos
-- Ejecutar: mysql -u root -p prqdr_db_01 < verificar_usuarios.sql
-- O desde mysql: USE prqdr_db_01; luego pegar estas consultas.

SELECT '=== USUARIOS EN LA BASE DE DATOS ===' AS '';
SELECT documento, nombre, correo, rol, estado FROM usuarios ORDER BY documento;

SELECT '=== Para entrar como ADMIN (app/Android) ===' AS '';
SELECT CONCAT('Correo: ', correo, '  |  Contraseña: (la que tengas en la BD)') AS dato FROM usuarios WHERE rol = 'admin' LIMIT 1;

SELECT '=== Para entrar como VENDEDOR (ejemplo) ===' AS '';
SELECT CONCAT('Correo: ', correo, '  |  Contraseña: 123') AS dato FROM usuarios WHERE rol = 'vendedor' LIMIT 1;
