/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package principal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author INSTRUCTOR
 */
public class PSalida extends javax.swing.JPanel {

    /**
     * Creates new form PSalida
     */
    ConsumoAPI conexion = new ConsumoAPI();
    Gson gson = new Gson();
    int pagar;
    String id;

    public PSalida(String id) {
        this.id = id;
        initComponents();
        soloNumeros(campo_dinero);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etq_placa = new javax.swing.JLabel();
        campo_placa = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        etq_placa1 = new javax.swing.JLabel();
        etq_placa2 = new javax.swing.JLabel();
        salida = new javax.swing.JLabel();
        entrada = new javax.swing.JLabel();
        etq_placa3 = new javax.swing.JLabel();
        estacionado = new javax.swing.JLabel();
        etq_placa4 = new javax.swing.JLabel();
        costo = new javax.swing.JLabel();
        btn_buscar1 = new javax.swing.JButton();
        etq_placa5 = new javax.swing.JLabel();
        campo_dinero = new javax.swing.JTextField();
        etq_placa6 = new javax.swing.JLabel();
        cambio = new javax.swing.JLabel();
        recibido = new javax.swing.JLabel();
        valor = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        etq_placa.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etq_placa.setForeground(new java.awt.Color(49, 138, 222));
        etq_placa.setText("Placa:");

        campo_placa.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        campo_placa.setForeground(Color.decode("#318ade"));
        campo_placa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#318ade")));
        campo_placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_placaKeyPressed(evt);
            }
        });

        btn_buscar.setBackground(Color.decode("#318ade"));
        btn_buscar.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_buscar.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar.setText("BUSCAR");
        btn_buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscar.setFocusable(false);
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

        etq_placa1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etq_placa1.setForeground(new java.awt.Color(49, 138, 222));
        etq_placa1.setText("Tiempo Estacionado:");

        etq_placa2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etq_placa2.setForeground(new java.awt.Color(49, 138, 222));
        etq_placa2.setText("Hora Entrada:");

        salida.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(49, 138, 222)));

        entrada.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(49, 138, 222)));

        etq_placa3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etq_placa3.setForeground(new java.awt.Color(49, 138, 222));
        etq_placa3.setText("Hora Salida:");

        estacionado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(49, 138, 222)));

        etq_placa4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etq_placa4.setForeground(new java.awt.Color(49, 138, 222));
        etq_placa4.setText("Costo Total:");

        costo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(49, 138, 222)));

        btn_buscar1.setBackground(Color.decode("#318ade"));
        btn_buscar1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_buscar1.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar1.setText("PAGAR");
        btn_buscar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscar1.setFocusable(false);
        btn_buscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar1ActionPerformed(evt);
            }
        });

        etq_placa5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etq_placa5.setForeground(new java.awt.Color(49, 138, 222));
        etq_placa5.setText("Valor Recibido:");

        campo_dinero.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        campo_dinero.setForeground(Color.decode("#318ade"));
        campo_dinero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#318ade")));
        campo_dinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campo_dineroActionPerformed(evt);
            }
        });
        campo_dinero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_dineroKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campo_dineroKeyReleased(evt);
            }
        });

        etq_placa6.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etq_placa6.setForeground(new java.awt.Color(49, 138, 222));
        etq_placa6.setText("Factura:");

        cambio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(49, 138, 222)));

        recibido.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(49, 138, 222)));

        valor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(49, 138, 222)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(etq_placa)
                            .addComponent(campo_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(btn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etq_placa2)
                            .addComponent(entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etq_placa3)
                            .addComponent(salida, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etq_placa1)
                            .addComponent(estacionado, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etq_placa4)
                            .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etq_placa6)
                            .addComponent(recibido, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(campo_dinero, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(etq_placa5)
                                .addGap(144, 144, 144))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(233, 233, 233)
                                .addComponent(btn_buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cambio, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(etq_placa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campo_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(etq_placa2)
                                    .addComponent(etq_placa3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(salida, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(etq_placa1)
                            .addComponent(etq_placa4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(estacionado, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etq_placa5)
                    .addComponent(etq_placa6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(campo_dinero, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(btn_buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(recibido, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cambio, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void campo_placaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_placaKeyPressed
        if (evt.getKeyChar() == '\n') {
            bucar();
            
        }
    }//GEN-LAST:event_campo_placaKeyPressed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed

        bucar();
        
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_buscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar1ActionPerformed
        
        facturar();
        
    }//GEN-LAST:event_btn_buscar1ActionPerformed

    private void campo_dineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campo_dineroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campo_dineroActionPerformed

    private void campo_dineroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_dineroKeyReleased
        
    }//GEN-LAST:event_campo_dineroKeyReleased

    private void campo_dineroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_dineroKeyPressed
        if (evt.getKeyChar() == '\n') {
            facturar();
            
        }
        
    }//GEN-LAST:event_campo_dineroKeyPressed
    public void facturar(){
        String dinero  = campo_dinero.getText();
        int dineroNumero = Integer.parseInt(dinero);
        if(dineroNumero >= pagar){
            int Cambioo = dineroNumero - pagar;
            
            String cambioo = Integer.toString(Cambioo);
            Map<String, String> Consultar = new HashMap<>();
            Consultar.put("salida", salida.getText());
            Consultar.put("placa", campo_placa.getText());
            Consultar.put("ingreso", entrada.getText());
            Consultar.put("pago", costo.getText().replaceAll("\\.", ""));
            System.out.println(entrada.getText());
            
            
            String temporal = conexion.ConsumoGET("https://apiparqueadero.000webhostapp.com/tickets/updateCosto.php", Consultar);
            System.out.println(temporal);
            salida.setText("");
            entrada.setText("");
            estacionado.setText("");
            valor.setText("Costo: "+costo.getText());
            costo.setText("");
            campo_placa.setEnabled(true);
            campo_dinero.setText("");
            campo_placa.setText("");
            cambio.setText("Cambio: "+formatoMoneda(cambioo));
            recibido.setText("Recibido: "+formatoMoneda(dinero));
            campo_placa.requestFocus();
            
            
        }else{
            JOptionPane.showMessageDialog(null, "Dinero insuficiente");
        }
    
    }
    public void bucar(){
        cambio.setText("");
        recibido.setText("");
        valor.setText("");
        String placa = campo_placa.getText();
        

        
        if (placa.length() > 0) {
            Map<String, String> Consultar = new HashMap<>();
            Consultar.put("placa", placa);
            Consultar.put("id", this.id);

            try {
                String temporal = conexion.ConsumoGET("https://apiparqueadero.000webhostapp.com/tickets/getCosto.php", Consultar);
                System.out.println(temporal);
                JsonObject jsonObject = gson.fromJson(temporal, JsonObject.class);

                if (jsonObject.has("registros")) {
                    JsonObject registros = jsonObject.getAsJsonObject("registros");
                    String horaSalida = registros.get("hora_salida").getAsString();
                    String horaIngreso = registros.get("hora_ingreso").getAsString();
                    String tiempoEstacionado = registros.get("tiempo_estacionado").getAsString();
                    String totalPagar = formatoMoneda(registros.get("total_a_pagar").getAsString());
                    
                    pagar = Integer.parseInt(totalPagar.replaceAll("\\.", ""));
                    // Asignar valores a los campos en el panel
                    salida.setText(horaSalida);
                    entrada.setText(horaIngreso);
                    estacionado.setText(tiempoEstacionado);
                    costo.setText(totalPagar);
                    campo_placa.setEnabled(false);
                    
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro El vehiculo en este parqueadero");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Inténtalo de nuevo.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese una placa");
        }
    
    }
    
    public void soloNumeros(JTextField campo){
        PlainDocument doc = (PlainDocument) campo.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (esNumero(string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (esNumero(text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean esNumero(String text) {
                return text.matches("^[0-9]*\\.?[0-9]*$");
            }
        });
    }
    public static String formatoMoneda(String numeroString) {
        try {
            // Parsea el valor de la cadena a float
            float numero = Float.parseFloat(numeroString);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            String pattern = "###,###,###,###.##"; // Patrón para el formato de moneda colombiana

            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            return decimalFormat.format(numero);
        } catch (NumberFormatException e) {
            // Manejo de excepciones en caso de que la cadena no sea un número válido
            return "Formato no válido";
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_buscar1;
    private javax.swing.JLabel cambio;
    private javax.swing.JTextField campo_dinero;
    private javax.swing.JTextField campo_placa;
    private javax.swing.JLabel costo;
    private javax.swing.JLabel entrada;
    private javax.swing.JLabel estacionado;
    private javax.swing.JLabel etq_placa;
    private javax.swing.JLabel etq_placa1;
    private javax.swing.JLabel etq_placa2;
    private javax.swing.JLabel etq_placa3;
    private javax.swing.JLabel etq_placa4;
    private javax.swing.JLabel etq_placa5;
    private javax.swing.JLabel etq_placa6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel recibido;
    private javax.swing.JLabel salida;
    private javax.swing.JLabel valor;
    // End of variables declaration//GEN-END:variables
}
