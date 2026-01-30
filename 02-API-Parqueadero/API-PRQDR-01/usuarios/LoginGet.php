<?php

include '../Conexion.php';

if (!empty($_GET['correo']) && !empty($_GET['contrasenia'])) {

    $correo = $_GET['correo'];
    $contrasenia = $_GET['contrasenia'];

    try {
        $consulta = $base_de_datos->prepare("SELECT * FROM usuarios WHERE correo = :cor AND contrasenia = :cont");
        $consulta->bindParam(':cor', $correo);
        $consulta->bindParam(':cont', $contrasenia);
        $proceso = $consulta->execute();
        $usuario = $consulta->fetch(PDO::FETCH_ASSOC);

        if (!$proceso || $consulta->rowCount() !== 1) {
            echo json_encode([
                'status' => false,
                'mesagge' => "No se encontraron registros",
                'message' => "No se encontraron registros"
            ]);
            exit;
        }

        $usuario = mb_convert_encoding($usuario, "UTF-8", "iso-8859-1");

        // Vendedor: debe tener empresa asignada y parqueadero activo
        if ($usuario['rol'] === 'vendedor') {
            // Usuario inactivo
            if ((int) $usuario['estado'] === 0) {
                echo json_encode([
                    'status' => false,
                    'mesagge' => "Usuario inactivo. Contacte al administrador.",
                    'message' => "Usuario inactivo. Contacte al administrador."
                ]);
                exit;
            }

            // Comprobar si tiene parqueadero asignado y si está activo
            $doc = $usuario['documento'];
            $consultaParq = $base_de_datos->prepare(
                "SELECT p.* FROM parqueadero p
                 INNER JOIN parqueadero_vendedores pv ON p.id_parqueadero = pv.id_parqueadero
                 WHERE pv.id_usuario = :id LIMIT 1"
            );
            $consultaParq->bindParam(':id', $doc);
            $consultaParq->execute();
            $parqueadero = $consultaParq->fetch(PDO::FETCH_ASSOC);

            if (!$parqueadero) {
                echo json_encode([
                    'status' => false,
                    'mesagge' => "No tiene empresa (parqueadero) asignada. Contacte al administrador.",
                    'message' => "No tiene empresa (parqueadero) asignada. Contacte al administrador."
                ]);
                exit;
            }

            if ((int) $parqueadero['estado'] === 0) {
                echo json_encode([
                    'status' => false,
                    'mesagge' => "El parqueadero está inactivo. Contacte al administrador.",
                    'message' => "El parqueadero está inactivo. Contacte al administrador."
                ]);
                exit;
            }
        }

        // Admin o vendedor válido: permitir login
        echo json_encode([
            'status' => true,
            'message' => "Usuario encontrado",
            'usuario' => $usuario
        ]);

    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'mesagge' => "ERROR##SQL",
            'message' => "Error en el servidor. Intente de nuevo."
        ]);
    }
} else {
    echo json_encode([
        'status' => false,
        'mesagge' => "ERROR##DATOS##POST",
        'message' => "Correo y contraseña son obligatorios."
    ]);
}
?>