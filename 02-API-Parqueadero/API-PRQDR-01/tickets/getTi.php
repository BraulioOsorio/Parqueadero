<?php

include '../Conexion.php';

if (!empty($_GET['idP'])) {
    $id_parqueadero = $_GET['idP'];

    try {
        // Get current date and time in Colombia timezone
        $colombia_timezone = new DateTimeZone('America/Bogota');
        $current_datetime = new DateTime('now', $colombia_timezone);
        $colombia_now = $current_datetime->format('Y-m-d H:i:s');
        
        $consulta = $base_de_datos->prepare("SELECT *, CASE WHEN estado_pago = 1 THEN TIMEDIFF(hora_salida, hora_ingreso) ELSE TIMEDIFF(:colombia_now, hora_ingreso) END AS tiempo_transcurrido FROM tickets WHERE parqueadero = :id ORDER BY id_tickets DESC");
        
        $consulta->bindParam(':id', $id_parqueadero, PDO::PARAM_INT);
        $consulta->bindParam(':colombia_now', $colombia_now);
        $proceso = $consulta->execute();
        $datos = $consulta->fetchAll(PDO::FETCH_ASSOC);
        $datos = mb_convert_encoding($datos, "UTF-8", "iso-8859-1");

        if ($proceso) {
            $respuesta['registros'] = $datos;
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
