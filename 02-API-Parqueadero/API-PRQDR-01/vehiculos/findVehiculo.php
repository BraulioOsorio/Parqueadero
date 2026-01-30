<?php



include '../Conexion.php';

if (!empty($_POST['placa']) && !empty($_POST['id_parqueadero'])) {
    $placa = $_POST['placa'];
    $id_parqueadero = (int) $_POST['id_parqueadero'];
    $parametroPlaca = '%' . $placa . '%';

    try {
        $consulta = $base_de_datos->prepare("SELECT * FROM vehiculo_registrados WHERE id_parqueadero = :idP AND (placa LIKE :pla OR propietario LIKE :pla OR tipo LIKE :pla)");
        $consulta->bindParam(':idP', $id_parqueadero, PDO::PARAM_INT);
        $consulta->bindValue(':pla', $parametroPlaca, PDO::PARAM_STR);

        $proceso = $consulta->execute();
        $vehiculo = $consulta->fetchAll(PDO::FETCH_ASSOC);
        $vehiculo = mb_convert_encoding($vehiculo,"UTF-8","iso-8859-1");
        $filas = $consulta->rowCount();

        if ($filas >= 1) {
            $respuesta = [
                'status' => true,
                'mesagge' => $filas === 1 ? "Se encontró un vehículo" : "Se encontraron vehículos",
                'registros' => $vehiculo
            ];
            echo json_encode($respuesta);
        } else {
            $respuesta = [
                'status' => false,
                'mesagge' => "No se encontraron registros"
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