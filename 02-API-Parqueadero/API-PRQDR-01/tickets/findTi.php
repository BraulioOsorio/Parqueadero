<?php
include '../Conexion.php';

if (!empty($_POST['placa'])) {
    $placa = $_POST['placa'];
    $id_parqueadero = $_POST['idP'];

    try {
        // Get current date and time in Colombia timezone
        $colombia_timezone = new DateTimeZone('America/Bogota');
        $current_datetime = new DateTime('now', $colombia_timezone);
        $colombia_now = $current_datetime->format('Y-m-d H:i:s');

        $consulta = $base_de_datos->prepare("SELECT *, CASE WHEN t.estado_pago = 1 THEN TIMEDIFF(t.hora_salida, t.hora_ingreso) ELSE TIMEDIFF(:colombia_now, t.hora_ingreso) END AS tiempo_transcurrido FROM tickets AS t INNER JOIN vehiculo_registrados AS vr ON t.placa = vr.placa WHERE (t.placa LIKE :pla OR vr.tipo LIKE :pla OR vr.propietario LIKE :pla) AND t.parqueadero = :id");

        $parametroPlaca = '%' . $placa . '%'; 

        $consulta->bindValue(':pla', $parametroPlaca, PDO::PARAM_STR);
        $consulta->bindParam(':id', $id_parqueadero, PDO::PARAM_INT);
        $consulta->bindParam(':colombia_now', $colombia_now, PDO::PARAM_STR); // Bind the Colombia datetime

        $proceso = $consulta->execute();
        $datos = $consulta->fetchAll(PDO::FETCH_ASSOC);
        $datos = mb_convert_encoding($datos,"UTF-8","iso-8859-1");

        if ($proceso) {
            $respuesta = [
                'status' => true,
                'registros' => $datos
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
