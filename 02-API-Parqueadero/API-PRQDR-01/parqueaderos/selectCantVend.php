<?php


include '../Conexion.php';

if (!empty($_POST['id_parqueadero'])) {

    $id_parqueadero = $_POST['id_parqueadero'];

    try {
        $consulta = $base_de_datos->prepare("SELECT COUNT(DISTINCT id_usuario) AS cantidad_vendedores FROM parqueadero_vendedores WHERE id_parqueadero = :id");
        $consulta->bindParam(':id', $id_parqueadero);

        $proceso = $consulta->execute();
        $cantidadVendedores = $consulta->fetch(PDO::FETCH_ASSOC)['cantidad_vendedores'];
        $cantidadVendedores = mb_convert_encoding($cantidadVendedores,"UTF-8","iso-8859-1");

        if ($proceso) {
            if ($consulta->rowCount() == 1) {
                $respuesta = [
                    'status' => true,
                    'message' => "Contando vendedores",
                    'cantVendedores' => $cantidadVendedores
                ];
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