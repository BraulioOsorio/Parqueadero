<?php



include '../Conexion.php';

if (!empty($_POST['placa'])) {
    $placa = $_POST['placa'];

    try {
        $consulta = $base_de_datos->prepare("SELECT * FROM vehiculo_registrados WHERE placa LIKE :pla OR propietario LIKE :pla OR tipo LIKE :pla");
        $parametroPlaca = '%' . $placa . '%'; 

        $consulta->bindValue(':pla', $parametroPlaca, PDO::PARAM_STR);

        $proceso = $consulta->execute();
        $vehiculo = $consulta->fetchAll(PDO::FETCH_ASSOC);
        $vehiculo = mb_convert_encoding($vehiculo,"UTF-8","iso-8859-1");
        $filas = $consulta->rowCount();

        if ($filas == 1) {
            $respuesta = [
                'status' => true,
                'mesagge' => "Se encontró un vehículo",
                'registros' => $vehiculo
            ];
            echo json_encode($respuesta);
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