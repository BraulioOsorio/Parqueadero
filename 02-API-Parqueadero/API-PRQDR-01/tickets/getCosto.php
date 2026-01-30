<?php

include '../Conexion.php';

if (!empty($_GET['placa'])) {
    $placa = $_GET['placa'];

    try {
        $colombia_timezone = new DateTimeZone('America/Bogota');
        $current_datetime = new DateTime('now', $colombia_timezone);
        $colombia_now = $current_datetime->format('Y-m-d H:i:s');

        // Sin idP: consulta pública para dueños del vehículo (ver costo, tiempo, parqueadero).
        // Con idP: solo devuelve el ticket si es del parqueadero indicado (vendedor para registrar salida).
        if (empty($_GET['idP'])) {
            $consulta = $base_de_datos->prepare("SELECT t.placa, p.nombre, :colombia_now AS hora_salida, t.hora_ingreso AS hora_ingreso, TIMEDIFF(:colombia_now, t.hora_ingreso) AS tiempo_estacionado, ROUND(CASE WHEN vr.tipo = 'carro' THEN p.carro WHEN vr.tipo = 'moto' THEN p.moto WHEN vr.tipo = 'camioneta' THEN p.camioneta WHEN vr.tipo = 'camion' THEN p.camiones ELSE 0 END * CEIL(TIME_TO_SEC(TIMEDIFF(:colombia_now, t.hora_ingreso)) / 3600), 2) AS total_a_pagar FROM tickets t INNER JOIN vehiculo_registrados vr ON t.placa = vr.placa AND t.parqueadero = vr.id_parqueadero INNER JOIN parqueadero p ON t.parqueadero = p.id_parqueadero WHERE t.placa = :pla AND t.estado_pago = 0 LIMIT 1");
            $consulta->bindParam(':pla', $placa);
            $consulta->bindParam(':colombia_now', $colombia_now);
        } else {
            $id = $_GET['idP'];
            $consulta = $base_de_datos->prepare("SELECT t.placa, p.nombre, :colombia_now AS hora_salida, t.hora_ingreso AS hora_ingreso, TIMEDIFF(:colombia_now, t.hora_ingreso) AS tiempo_estacionado, ROUND(CASE WHEN vr.tipo = 'carro' THEN p.carro WHEN vr.tipo = 'moto' THEN p.moto WHEN vr.tipo = 'camioneta' THEN p.camioneta WHEN vr.tipo = 'camion' THEN p.camiones ELSE 0 END * CEIL(TIME_TO_SEC(TIMEDIFF(:colombia_now, t.hora_ingreso)) / 3600), 2) AS total_a_pagar FROM tickets t INNER JOIN vehiculo_registrados vr ON t.placa = vr.placa AND t.parqueadero = vr.id_parqueadero INNER JOIN parqueadero p ON t.parqueadero = p.id_parqueadero WHERE t.placa = :pla AND t.parqueadero = :idP AND t.estado_pago = 0 LIMIT 1");
            $consulta->bindParam(':pla', $placa);
            $consulta->bindParam(':idP', $id);
            $consulta->bindParam(':colombia_now', $colombia_now);
        }

        $proceso = $consulta->execute();
        $costo = $consulta->fetch(PDO::FETCH_ASSOC);
        $costo = mb_convert_encoding($costo,"UTF-8","iso-8859-1");

        if ($proceso) {
            if ($consulta->rowCount() == 1) {
                $respuesta['status'] = true;
                $respuesta['registros'] = $costo;
                echo json_encode($respuesta);
            } else {
                $respuesta['status'] = false;
                $respuesta['message'] = "No se encontraron registros";
                echo json_encode($respuesta);
            }
        } else {
            $respuesta['status'] = false;
            $respuesta['message'] = "ERROR##PARQUEADERO##GET";
            echo json_encode($respuesta);
        }
    } catch (Exception $e) {
        $respuesta['status'] = false;
        $respuesta['message'] = "ERROR##SQL";
        $respuesta['exception'] = $e;
        echo json_encode($respuesta);
    }
} else {
    $respuesta['status'] = false;
    $respuesta['message'] = "ERROR##DATOS##POST";
    echo json_encode($respuesta);
}
?>
