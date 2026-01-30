<?php

include '../Conexion.php';

if (!empty($_POST['placa']) && !empty($_POST['id_parqueadero'])) {

    $placa = $_POST['placa'];
    $id_parqueadero = $_POST['id_parqueadero'];

    try {
        // Solo bloquear si ya hay ticket pendiente en ESTE parqueadero (misma placa + mismo parqueadero)
        $contador = $base_de_datos->prepare("SELECT COUNT(*) FROM tickets WHERE placa = :pla AND parqueadero = :idp AND estado_pago = 0");
        $contador->bindParam(':pla', $placa);
        $contador->bindParam(':idp', $id_parqueadero);
        $contador->execute();
        $pendientes = (int) $contador->fetchColumn();

        if ($pendientes > 0) {
            echo json_encode([
                'status' => false,
                'mesagge' => "FACTURA#PENDIENTE"
            ]);
            exit;
        }

        $colombia_timezone = new DateTimeZone('America/Bogota');
        $current_datetime_colombia = new DateTime('now', $colombia_timezone);
        $hora_ingreso_colombia = $current_datetime_colombia->format('Y-m-d H:i:s');

        $consulta = $base_de_datos->prepare("INSERT INTO tickets (placa, parqueadero, hora_ingreso) VALUES(:pla, :idp, :hora_ingreso)");
        $consulta->bindParam(':pla', $placa);
        $consulta->bindParam(':idp', $id_parqueadero);
        $consulta->bindParam(':hora_ingreso', $hora_ingreso_colombia);
        $proceso = $consulta->execute();

        if (!$proceso) {
            echo json_encode([
                'status' => false,
                'mesagge' => "ERROR##TICKET##INSERT"
            ]);
            exit;
        }

        // Devolver el ticket creado (JOIN por placa + parqueadero por si hay misma placa en varios parqueaderos)
        $consultaSelect = $base_de_datos->prepare(
            "SELECT t.hora_ingreso, t.placa, p.nombre, vr.propietario, vr.tipo
             FROM tickets t
             INNER JOIN vehiculo_registrados vr ON t.placa = vr.placa AND t.parqueadero = vr.id_parqueadero
             INNER JOIN parqueadero p ON p.id_parqueadero = t.parqueadero
             WHERE t.placa = :pla AND t.parqueadero = :idp
             ORDER BY t.id_tickets DESC LIMIT 1"
        );
        $consultaSelect->bindParam(':pla', $placa);
        $consultaSelect->bindParam(':idp', $id_parqueadero);
        $consultaSelect->execute();
        $ticket = $consultaSelect->fetch(PDO::FETCH_ASSOC);
        $ticket = $ticket ? mb_convert_encoding($ticket, "UTF-8", "iso-8859-1") : null;

        if ($ticket) {
            echo json_encode([
                'status' => true,
                'mesagge' => "Se creó el ticket",
                'registros' => $ticket
            ]);
        } else {
            echo json_encode([
                'status' => true,
                'mesagge' => "Se creó el ticket",
                'registros' => ['hora_ingreso' => $hora_ingreso_colombia, 'placa' => $placa]
            ]);
        }

    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'mesagge' => "ERROR##SQL",
            'exception' => $e->getMessage()
        ]);
    }
} else {
    echo json_encode([
        'status' => false,
        'mesagge' => "ERROR##DATOS##POST"
    ]);
}
?>
