<?php


include '../Conexion.php';

if (!empty($_GET['placa'])) {

    $placa = $_GET['placa'];
    $id = $_GET['id'];
    $documento = $_GET['documento'];

    try {
        $consulta = $base_de_datos->prepare("SELECT t.placa, TIME(NOW()) AS hora_salida,t.hora_ingreso AS hora_ingreso, TIMEDIFF(TIME(NOW()), t.hora_ingreso) AS tiempo_estacionado, ROUND(CASE WHEN vr.tipo = 'carro' THEN p.carro WHEN vr.tipo = 'moto' THEN p.moto WHEN vr.tipo = 'camioneta' THEN p.camioneta WHEN vr.tipo = 'camion' THEN p.camiones ELSE 0 END * CEIL(TIME_TO_SEC(TIMEDIFF(TIME(NOW()), t.hora_ingreso)) / 3600), 2) AS total_a_pagar FROM tickets t INNER JOIN vehiculo_registrados vr ON t.placa = vr.placa INNER JOIN parqueadero p ON vr.tipo = CASE WHEN vr.tipo = 'carro' THEN 'carro' WHEN vr.tipo = 'moto' THEN 'moto' WHEN vr.tipo = 'camioneta' THEN 'camioneta' WHEN vr.tipo = 'camion' THEN 'camiones' ELSE '' END WHERE t.placa = :pla AND p.id_parqueadero =:idP AND t.estado_pago = 0 AND t.vendedor =:ven LIMIT 1");
        $consulta->bindParam(':pla', $placa);
        $consulta->bindParam(':idP', $id);
        $consulta->bindParam(':ven', $documento);

        $proceso = $consulta->execute();
        $costo = $consulta->fetch(PDO::FETCH_ASSOC);
        $costo = mb_convert_encoding($costo,"UTF-8","iso-8859-1");

        if ($proceso) {
            if ($consulta->rowCount() == 1) {
                $respuesta['registros'] = $costo;
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