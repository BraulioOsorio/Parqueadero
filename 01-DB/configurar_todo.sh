#!/bin/bash
# Configura la base de datos para la API del parqueadero.
# Ejecutar en la terminal (te pedirá UNA vez la contraseña de sudo):
#
#   cd /home/braulio07/Otros/Parqueadero/01-DB && chmod +x configurar_todo.sh && ./configurar_todo.sh
#

set -e
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

echo ""
echo "=== Configurando base de datos parqueadero ==="
echo "  (se pedirá la contraseña de sudo)"
echo ""

echo "1. Creando base de datos y tablas (parqueadero.sql)..."
sudo mysql < parqueadero.sql
echo "   OK."
echo ""

echo "2. Creando usuario prqdr_user para la API (crear_usuario_app.sql)..."
sudo mysql < crear_usuario_app.sql
echo "   OK."
echo ""

echo "=== Listo. Ya puedes usar la API (Java y Android). ==="
echo "  - Usuario admin: braulioosoriomartinez@gmail.com / 070221Xd"
echo "  - Usuario vendedor: usuario1@gmail.com / 123"
echo ""
