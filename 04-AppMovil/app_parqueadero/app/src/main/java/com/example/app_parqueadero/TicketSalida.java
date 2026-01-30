package com.example.app_parqueadero;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityTicketSalidaBinding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

public class TicketSalida extends DrawerVendedor {
    ActivityTicketSalidaBinding activitySalidaBinding;
    EditText campo_buscar_placa;
    Utils conexion;
    String placaDB;
    Config dataConfig;
    String url;
    LinearLayout detalleSalida;
    Map<String, String> datosPots;
    Map<String, String> datosget;
    Button insertar;
    TextView horaSalida;
    TextView horaIngreso;
    TextView costoTotal;
    LinearLayout facturita,Cargandi;
    String placaIntent;
    ImageView loading_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_salida);
        activitySalidaBinding = activitySalidaBinding.inflate(getLayoutInflater());
        setContentView(activitySalidaBinding.getRoot());
        campo_buscar_placa = findViewById(R.id.campo_buscar_placa);
        dataConfig = new Config(getApplicationContext());
        conexion = new Utils(getApplicationContext());
        insertar = findViewById(R.id.insertar);
        detalleSalida = findViewById(R.id.detalleSalida);
        horaSalida = findViewById(R.id.horaSalida);
        horaIngreso = findViewById(R.id.horaEntrada);
        costoTotal = findViewById(R.id.costoTotal);
        facturita = findViewById(R.id.facturita);
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);
        Bundle datos = getIntent().getExtras();
        placaIntent = (datos!=null)? datos.getString("placa",null) : null;
        iniciarBotones();
        if(placaIntent!=null){
            campo_buscar_placa.setText(placaIntent);
            buscarCarro(null);
        }
    }

    public void buscarCarro(View view){
        detalleSalida.setVisibility(View.INVISIBLE);
        cargarGif();
        url = dataConfig.getEndPoint("/tickets/getCosto.php");

        SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        String idParqueadero = archivo.getString("id_parqueadero", null);
        if (idParqueadero == null) {
            ocultarGif();
            Toast.makeText(getApplicationContext(), "No se pudo identificar el parqueadero. Cierre sesión y vuelva a entrar.", Toast.LENGTH_LONG).show();
            return;
        }

        datosPots = new HashMap<String, String>();
        if (campo_buscar_placa.getText().toString().equalsIgnoreCase("")){
            datosPots.put("placa", placaIntent);
        }else{
            datosPots.put("placa", campo_buscar_placa.getText().toString());
        }
        datosPots.put("idP", idParqueadero);

        conexion.consumoGetParams(url, datosPots, new Utils.JsonResponseListener() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Respuesta del servidor: " + response.toString());

                try {

                    Boolean statusResponse = response.getBoolean("status");

                    if(statusResponse){
                        facturita.setVisibility(View.INVISIBLE);
                        detalleSalida.setVisibility(View.VISIBLE);
                        JSONObject objectUser = response.getJSONObject("registros");

                        horaIngreso.setText(objectUser.getString("hora_ingreso"));
                        horaSalida.setText(objectUser.getString("hora_salida"));

                        TextView tiempoEstacionado = findViewById(R.id.tiempoEstacionado);
                        tiempoEstacionado.setText(objectUser.getString("tiempo_estacionado"));

                        costoTotal.setText(formatoMoneda(objectUser.getString("total_a_pagar")));
                        placaDB = objectUser.getString("placa");
                        ocultarGif();
                    }else{
                        ocultarGif();
                        limpiarDatosTicket();
                        detalleSalida.setVisibility(View.INVISIBLE);
                        facturita.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "No hay factura pendiente en este parqueadero.", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    cargarGif();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {
                cargarGif();
                System.out.println("Error del servidor: " + errorMessage);
            }
        });

    }
    public void iniciarBotones(){
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCosto();
            }
        });
    }

    public void updateCosto(){
        url = dataConfig.getEndPoint("tickets/updateCosto.php");
        EditText valorPagado = findViewById(R.id.valorPagado);
        if(valorPagado.getText().toString().equalsIgnoreCase("")){

            Toast.makeText(getApplicationContext(),"Llenelo socio", Toast.LENGTH_LONG).show();

        }else{
            int dineroRecibidoNumero = Integer.parseInt(valorPagado.getText().toString());

            int costoNumero = Integer.parseInt(costoTotal.getText().toString().replaceAll("\\.", ""));

            if (dineroRecibidoNumero >= costoNumero ){

                int devuelta = dineroRecibidoNumero - costoNumero;
                String cambioFormato = Integer.toString(devuelta);

                String hola = costoTotal.getText().toString();

                SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
                String idParqueadero = archivo.getString("id_parqueadero", null);
                if (idParqueadero == null) {
                    Toast.makeText(getApplicationContext(), "No se pudo identificar el parqueadero.", Toast.LENGTH_LONG).show();
                    return;
                }
                datosget = new HashMap<String, String>();
                datosget.put("salida", horaSalida.getText().toString().replace(" ", "%20"));
                datosget.put("placa", placaDB);
                datosget.put("ingreso", horaIngreso.getText().toString().replace(" ", "%20"));
                datosget.put("pago", costoTotal.getText().toString().replaceAll("\\.", ""));
                datosget.put("idP", idParqueadero);

                conexion.consumoGetParams(url, datosget, new Utils.JsonResponseListener() {


                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Respuesta del servidor: " + response.toString());

                        try {

                            Boolean statusResponse = response.getBoolean("status");
                            if(statusResponse){
                                detalleSalida.setVisibility(View.INVISIBLE);
                                facturita.setVisibility(View.VISIBLE);

                                TextView recibido = findViewById(R.id.recibido);
                                recibido.setText(valorPagado.getText().toString());

                                TextView costo = findViewById(R.id.costo);
                                costo.setText(costoTotal.getText().toString());

                                TextView cambio = findViewById(R.id.cambio);
                                cambio.setText(formatoMoneda(cambioFormato));

                            }else{
                                String msg = response.optString("mesagge", "Hubo un error");
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                limpiarDatosTicket();
                                detalleSalida.setVisibility(View.INVISIBLE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        System.out.println("Error del servidor: " + errorMessage);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(),"Valor recibido menor al costo total", Toast.LENGTH_LONG).show();
            }

        }




    }

    /** Limpia los datos del ticket para no mostrar info de otro parqueadero. */
    private void limpiarDatosTicket() {
        placaDB = null;
        if (horaIngreso != null) horaIngreso.setText("");
        if (horaSalida != null) horaSalida.setText("");
        if (costoTotal != null) costoTotal.setText("");
        TextView tiempoEstacionado = findViewById(R.id.tiempoEstacionado);
        if (tiempoEstacionado != null) tiempoEstacionado.setText("");
        EditText valorPagado = findViewById(R.id.valorPagado);
        if (valorPagado != null) valorPagado.setText("");
    }

    public static String formatoMoneda(String numeroString) {
        try {
            // Parsea el valor de la cadena a float
            float numero = Float.parseFloat(numeroString);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            String pattern = "###,###,###,###.##";

            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            return decimalFormat.format(numero);
        } catch (NumberFormatException e) {
            return "Formato no válido";
        }
    }
    public  void cargarGif(){
        Cargandi.setVisibility(View.VISIBLE);
        loading_screen.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(R.drawable.pantalla_carga).into(loading_screen);
    }
    public void ocultarGif(){
        Cargandi.setVisibility(View.INVISIBLE);

        loading_screen.setVisibility(View.INVISIBLE);

    }
}