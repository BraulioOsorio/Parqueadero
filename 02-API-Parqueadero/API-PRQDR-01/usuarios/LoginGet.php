<?php


include '../Conexion.php';

if (!empty($_GET['correo']) and !empty($_GET['contrasenia'])) {

    $correo = $_GET['correo'];
    $contrasenia = $_GET['contrasenia'];

    try {
        $consulta = $base_de_datos->prepare("SELECT * FROM usuarios  WHERE correo = :cor AND contrasenia = :cont  ");
        $consulta->bindParam(':cor', $correo);
        $consulta->bindParam(':cont', $contrasenia);


        $proceso = $consulta->execute();
        $usuario = $consulta->fetch(PDO::FETCH_ASSOC);
        $usuario = mb_convert_encoding($usuario,"UTF-8","iso-8859-1");

        if ($proceso) {
            if ($consulta->rowCount() == 1) {
                $respuesta = [
                    'status' => true,
                    'message' => "Usuario encontrado",
                    'usuario' => $usuario
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
                'mesagge' => "ERROR##INGRESO##INSERT"
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