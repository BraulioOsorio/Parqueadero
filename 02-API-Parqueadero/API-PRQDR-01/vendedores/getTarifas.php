<?php

include '../Conexion.php';

if (!empty($_GET['id']) ) {

    $id = $_GET['id'];

    try {
        $consulta = $base_de_datos->prepare("SELECT * FROM parqueadero WHERE id_parqueadero = :id");

        $consulta->bindParam(':id', $id);
        $proceso = $consulta->execute();

        if ($proceso) {
            $datos = $consulta->fetch();
            $datos = mb_convert_encoding($datos,"UTF-8","iso-8859-1");
            $respuesta['tarifas'] = $datos;
            echo json_encode($respuesta);
        } else {
            $respuesta = [
                'status' => false,
                'mesagge' => "ERROR##PARKING##FIND"
            ];
            echo json_encode($respuesta);
        }
    } catch (Exception $e) {
        $respuesta = [
            'status' => false,
            'mesagge' => "ERROR##SQL",
            'exception' => $e
        ];
        echo json_encode($respuesta);
    }
} else {
    $respuesta = [
        'status' => false,
        'mesagge' => "ERROR##DATOS##POST",
        '$_GET' => $_GET,
        '$_POST' => $_POST
    ];
    echo json_encode($respuesta);
}
?>