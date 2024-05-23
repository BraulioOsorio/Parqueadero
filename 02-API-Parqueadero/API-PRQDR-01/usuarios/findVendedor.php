<?php


include '../Conexion.php';

if (!empty($_POST['documento']) ) {

    $documento = $_POST['documento'];

    try {
        $consulta = $base_de_datos->prepare("SELECT * FROM usuarios WHERE documento = :doc AND rol = 'vendedor' ");

        $consulta->bindParam(':doc', $documento);
        $proceso = $consulta->execute();

        if ($proceso) {
            if ($consulta->rowCount() == 1) {
                
                
                $datos = $consulta->fetch();
                $datos = mb_convert_encoding($datos,"UTF-8","iso-8859-1");
                $respuesta = [
                    'status' => true,
                    'mesagge' => "OK##ENCONTRADO",
                    'vendedor' => $datos
                ];
                echo json_encode($respuesta);
                
                
            }else{
                $respuesta = [
                    'status' => false,
                    'mesagge' => "Error no encontrado"
                ];
                echo json_encode($respuesta);
            }
            
        } else {
            $respuesta = [
                'status' => false,
                'mesagge' => "ERROR##USER##FIND"
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