<?php


include '../Conexion.php';

if (!empty($_GET['id_usuario'])) {

    $id_usuario = $_GET['id_usuario'];

    try {
        $consulta = $base_de_datos->prepare("SELECT parqueadero.* FROM parqueadero JOIN parqueadero_vendedores ON parqueadero.id_parqueadero = parqueadero_vendedores.id_parqueadero WHERE parqueadero_vendedores.id_usuario = :id AND parqueadero.estado = 1 LIMIT 1");
        $consulta->bindParam(':id', $id_usuario);

        $proceso = $consulta->execute();
        $parqueadero = $consulta->fetch(PDO::FETCH_ASSOC);
        $parqueadero = mb_convert_encoding($parqueadero,"UTF-8","iso-8859-1");

        if ($proceso) {
            if ($consulta->rowCount() == 1) {
                $respuesta['registros'] = $parqueadero;
                echo json_encode($respuesta);
               
            } else {
                $respuesta = [
                    'status' => false,
                    'mesagge' => "No se encontraron registros"
                ];
                echo json_encode($respuesta);
            }
        } else {
            $respuesta = [
                'status' => false,
                'mesagge' => "ERROR##PARQUEADERO##GET"
            ];
            echo json_encode($respuesta);
        }
    } catch (Exception $e) {
        $respuesta = [
            'status' => false,
            'mesagge' => "ERROR##SQL",
            'exception' => $e,
            '$_GET' => $_GET,
            '$_POST' => $_POST
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