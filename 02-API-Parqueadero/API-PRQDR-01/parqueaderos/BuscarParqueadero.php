<?php


include '../Conexion.php';

if (!empty($_POST['nombre']) ) {

    $nombre = $_POST['nombre'];

    try {
        $consulta = $base_de_datos->prepare("SELECT * FROM parqueadero WHERE nombre = :nom");

        $consulta->bindParam(':nom', $nombre);
        $proceso = $consulta->execute();
        $proceso = mb_convert_encoding($proceso,"UTF-8","iso-8859-1");

        if ($proceso) {
            $datos = $consulta->fetchAll();
            $respuesta['parqueadero'] = $datos;
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