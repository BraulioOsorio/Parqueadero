<?php


include '../Conexion.php';

if (!empty($_POST['documento'])) {

    $documento = $_POST['documento'];

    try {
        $delete = $base_de_datos->prepare("DELETE FROM parqueadero_vendedores WHERE documento= :doc");

        $delete->bindParam(':doc', $documento);
        $proceso_delete = $delete->execute();

        if ($proceso_delete) {
            $consulta = $base_de_datos->prepare("UPDATE usuarios SET estado=0 WHERE documento = :doc");

            $consulta->bindParam(':doc', $documento);
            $proceso = $consulta->execute();
        }



        if ($proceso) {
            $respuesta = [
                'status' => true,
                'mesagge' => "OK##STATUS##USER##UPDATE"
            ];
            echo json_encode($respuesta);
        } else {
            $respuesta = [
                'status' => false,
                'mesagge' => "ERROR##STATUS##USER##UPDATE"
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