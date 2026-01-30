<?php 

    
    include '../Conexion.php';

    if (!empty($_POST['placa']) && !empty($_POST['tipo_vehiculo']) && !empty($_POST['id_parqueadero'])) {

        $placa = $_POST['placa'];
        $id_parqueadero = (int) $_POST['id_parqueadero'];
        // Si no se envía propietario, se usa la placa como nombre del propietario
        $propietario = !empty($_POST['propietario']) ? $_POST['propietario'] : $placa;
        $tipo_vehiculo = $_POST['tipo_vehiculo'];

        try {
            $consulta = $base_de_datos->prepare("INSERT INTO vehiculo_registrados (placa, id_parqueadero, propietario, tipo) VALUES(:pla, :idP, :pro, :tip) ");

            $consulta->bindParam(':pla', $placa);
            $consulta->bindParam(':idP', $id_parqueadero, PDO::PARAM_INT);
            $consulta->bindParam(':pro', $propietario);
            $consulta->bindParam(':tip', $tipo_vehiculo);
            
            $proceso = $consulta->execute();

            if( $proceso ){
                $respuesta = [
                                'status' => true,
                                'mesagge' => "OK##PARKING##INSERT"
                              ];
                echo json_encode($respuesta);
            }else{
                $respuesta = [
                                'status' => false,
                                'mesagge' => "ERROR##PARKING##INSERT"
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
    }else{
        $respuesta = [
                        'status' => false,
                        'mesagge' => "ERROR##DATOS##POST",
                        '$_GET' => $_GET,
                        '$_POST' => $_POST
                      ];
        echo json_encode($respuesta);
    }
?>
