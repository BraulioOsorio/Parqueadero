<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST");
header("Access-Control-Allow-Headers: Content-Type");

// Establecer la zona horaria de Colombia
date_default_timezone_set('America/Bogota');

$servidor = 'localhost';
$usuario = 'root';
$contrasena = '';
$nombre_de_base = 'prqdr_db_01';

try {
   $base_de_datos = new PDO("mysql:host=$servidor;dbname=$nombre_de_base", $usuario, $contrasena);
} catch (Exception $e) {
   echo 'Ha surgido un error y no se puede conectar a la base de datos. Detalle: ' . $e->getMessage();
   exit;
}
