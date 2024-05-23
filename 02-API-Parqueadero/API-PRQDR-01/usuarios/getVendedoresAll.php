<?php 


    include '../Conexion.php';

    $consulta = $base_de_datos->query("SELECT usuarios.*, parqueadero.nombre AS nombre_parqueadero FROM usuarios INNER JOIN parqueadero_vendedores ON usuarios.documento = parqueadero_vendedores.id_usuario INNER JOIN parqueadero ON parqueadero_vendedores.id_parqueadero = parqueadero.id_parqueadero WHERE usuarios.rol = 'vendedor'");
    $datos = $consulta->fetchAll();
    $datos = mb_convert_encoding($datos,"UTF-8","iso-8859-1");

    $respuesta['registros'] = $datos;
    echo json_encode($respuesta);
    
?>