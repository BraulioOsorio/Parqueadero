<?php 

    include '../Conexion.php';

    $id_parqueadero = isset($_REQUEST['id_parqueadero']) ? (int) $_REQUEST['id_parqueadero'] : 0;
    if ($id_parqueadero <= 0) {
        echo json_encode(['status' => false, 'mesagge' => 'id_parqueadero requerido', 'registros' => []]);
        exit;
    }

    try {
        $consulta = $base_de_datos->prepare("SELECT * FROM vehiculo_registrados WHERE id_parqueadero = :idP ORDER BY placa");
        $consulta->bindParam(':idP', $id_parqueadero, PDO::PARAM_INT);
        $consulta->execute();
        $datos = $consulta->fetchAll(PDO::FETCH_ASSOC);
        $datos = mb_convert_encoding($datos, "UTF-8", "iso-8859-1");
        $respuesta['registros'] = $datos;
        echo json_encode($respuesta);
    } catch (Exception $e) {
        echo json_encode(['status' => false, 'mesagge' => 'ERROR##SQL', 'registros' => []]);
    }
?>