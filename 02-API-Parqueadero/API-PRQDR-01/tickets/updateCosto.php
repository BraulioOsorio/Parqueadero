<?php


include '../Conexion.php';

if (!empty($_GET['salida']) and !empty($_GET['placa']) and !empty($_GET['ingreso']) and !empty($_GET['pago']) and !empty($_GET['idP'])) {

    $salida = $_GET['salida'];
    $placa = $_GET['placa'];
    $ingreso = $_GET['ingreso'];
    $pago = $_GET['pago'];
    $idP = $_GET['idP'];

    try {
        // Solo se actualiza si el ticket pertenece al parqueadero indicado
        $consulta = $base_de_datos->prepare("UPDATE tickets SET hora_salida=:sal, total_pago=:pag, estado_pago = 1 WHERE placa = :pla AND hora_ingreso = :ing AND parqueadero = :idP");

        $consulta->bindParam(':sal', $salida);
        $consulta->bindParam(':pla', $placa);
        $consulta->bindParam(':pag', $pago);
        $consulta->bindParam(':ing', $ingreso);
        $consulta->bindParam(':idP', $idP);

        $proceso = $consulta->execute();
        $filas = $consulta->rowCount();

        if ($proceso && $filas > 0) {
            $respuesta = [
                'status' => true,
                'mesagge' => "OK##PARKING##UPDATE"
            ];
            echo json_encode($respuesta);
        } else {
            $respuesta = [
                'status' => false,
                'mesagge' => $filas === 0 ? "El ticket no pertenece a este parqueadero. Solo puede registrar la salida el parqueadero donde entró el vehículo." : "ERROR##PARKING##UPDATE"
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
        'mesagge' => empty($_GET['idP']) ? "Debe indicar el parqueadero para registrar la salida." : "ERROR##DATOS##POST",
        '$_GET' => $_GET,
        '$_POST' => $_POST
    ];
    echo json_encode($respuesta);
}
?>