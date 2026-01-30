<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST");
header("Access-Control-Allow-Headers: Content-Type");

// Establecer la zona horaria de Colombia
date_default_timezone_set('America/Bogota');

// Usar config.php si existe (GoogieHost/producción); si no, valores locales
if (file_exists(__DIR__ . '/config.php')) {
    require __DIR__ . '/config.php';
} else {
    $servidor = '127.0.0.1';
    $puerto = '3306';
    $nombre_de_base = 'prqdr_db_01';
    // Usuario dedicado para la app (evita "Access denied" 1698 de root en Linux)
    $usuario = 'prqdr_user';
    $contrasena = 'prqdr_pass';
}

$dsn = "mysql:host=$servidor;port=" . (isset($puerto) ? $puerto : '3306') . ";dbname=$nombre_de_base;charset=utf8mb4";

try {
   $base_de_datos = new PDO($dsn, $usuario, $contrasena);
} catch (Exception $e) {
   echo json_encode([
       'error' => true,
       'mensaje' => 'No se puede conectar a la base de datos.',
       'detalle' => $e->getMessage()
   ]);
   exit;
}
