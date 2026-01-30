<?php
/**
 * Prueba la conexión a la base de datos.
 * Abre en el navegador: http://localhost:8080/API-PRQDR-01/test_conexion.php
 * O por terminal: php test_conexion.php
 */

header("Content-Type: application/json; charset=utf-8");

if (file_exists(__DIR__ . '/config.php')) {
    require __DIR__ . '/config.php';
} else {
    $servidor = '127.0.0.1';
    $puerto = '3306';
    $nombre_de_base = 'prqdr_db_01';
    $usuario = 'prqdr_user';
    $contrasena = 'prqdr_pass';
}

$dsn = "mysql:host=$servidor;port=" . (isset($puerto) ? $puerto : '3306') . ";dbname=$nombre_de_base;charset=utf8mb4";

$salida = [
    'config' => [
        'servidor' => $servidor,
        'puerto' => $puerto ?? '3306',
        'base' => $nombre_de_base,
        'usuario' => $usuario,
    ],
    'conexion' => null,
    'error' => null,
];

try {
    $pdo = new PDO($dsn, $usuario, $contrasena);
    $salida['conexion'] = 'OK';
    // Comprobar que hay tablas
    $stmt = $pdo->query("SELECT COUNT(*) FROM usuarios");
    $salida['usuarios_count'] = $stmt ? $stmt->fetchColumn() : 0;
} catch (PDOException $e) {
    $salida['error'] = $e->getMessage();
    $salida['codigo'] = $e->getCode();
} catch (Exception $e) {
    $salida['error'] = $e->getMessage();
}

echo json_encode($salida, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
