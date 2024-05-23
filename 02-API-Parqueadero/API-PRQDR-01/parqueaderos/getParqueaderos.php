<?php 


    include '../Conexion.php';

    $consulta = $base_de_datos->query("SELECT * FROM parqueadero");
    $datos = $consulta->fetchAll();
    $datos = mb_convert_encoding($datos,"UTF-8","iso-8859-1");

    $respuesta['registros'] = $datos;
    echo json_encode($respuesta);
    
?>