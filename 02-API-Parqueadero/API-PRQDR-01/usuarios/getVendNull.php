<?php 


    include '../Conexion.php';

    $consulta = $base_de_datos->query("SELECT usuarios.* FROM usuarios LEFT JOIN parqueadero_vendedores ON usuarios.documento = parqueadero_vendedores.id_usuario WHERE parqueadero_vendedores.id_usuario IS NULL");
    
    $datos = $consulta->fetchAll();
    $datos = mb_convert_encoding($datos,"UTF-8","iso-8859-1");

    $respuesta['registros'] = $datos;
    echo json_encode($respuesta);
    
?>