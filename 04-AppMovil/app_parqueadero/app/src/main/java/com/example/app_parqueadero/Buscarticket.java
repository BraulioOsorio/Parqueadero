package com.example.app_parqueadero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityBuscarticketBinding;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

public class Buscarticket extends AppCompatActivity {

    Utils functions;
    Map<String, String> datosPots;
    Config dataConfig;
    Utils conexion;
    EditText campo_buscar_placa;
    LinearLayout datosCarro;
    LinearLayout factura_pendiente,Cargandi;

    Button btn_iniciar_sesion;
    ImageView loading_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscarticket);

        campo_buscar_placa = findViewById(R.id.campo_buscar_placa);
        dataConfig = new Config(getApplicationContext());
        conexion = new Utils(getApplicationContext());
        datosCarro = findViewById(R.id.datosCarro);
        factura_pendiente = findViewById(R.id.factura_pendiente);
        btn_iniciar_sesion = findViewById(R.id.btn_iniciar_sesion);
        functions = new Utils(getApplicationContext());
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functions.iniciarNuevaActividad(Login.class);

            }
        });
    }

    public void buscarCarro(View view) {
        factura_pendiente.setVisibility(View.INVISIBLE);
        datosCarro.setVisibility(View.INVISIBLE);

        String placa = campo_buscar_placa.getText().toString();
        if (placa.equals("")) {
            Toast.makeText(getApplicationContext(), "El campo es requerido", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        String idParqueadero = archivo.getString("id_parqueadero", null);

        // Propietario sin sesión: consultar ticket directamente (getCosto sin idP)
        if (idParqueadero == null) {
            datosTikect(placa, null);
            return;
        }

        // Vendedor con sesión: comprobar si el vehículo está registrado en su parqueadero y luego mostrar ticket
        cargarGif();
        String url = dataConfig.getEndPoint("/vehiculos/findPlaca.php");
        datosPots = new HashMap<String, String>();
        datosPots.put("placa", placa);
        datosPots.put("id_parqueadero", idParqueadero);

        conexion.consumoPOST(url, datosPots, new Utils.JsonResponseListenerPOST() {
            @Override
            public void onResponse(String response) {
                System.out.println("Respuesta del servidor: " + response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    TextView notifi = findViewById(R.id.notifi);
                    if (jsonResponse.optBoolean("error", false)) {
                        ocultarGif();
                        Toast.makeText(Buscarticket.this, jsonResponse.optString("mensaje", "Error del servidor."), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Boolean statusResponse = jsonResponse.getBoolean("status");
                    if (statusResponse) {
                        notifi.setText("El vehículo no tiene una Factura pendiente");
                        datosTikect(placa, idParqueadero);
                    } else {
                        datosCarro.setVisibility(View.INVISIBLE);
                        factura_pendiente.setVisibility(View.VISIBLE);
                        notifi.setText("El vehículo no está registrado en este parqueadero");
                        ocultarGif();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ocultarGif();
                    Toast.makeText(Buscarticket.this, "Error al leer la respuesta.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("Error del servidor: " + errorMessage);
                ocultarGif();
                Toast.makeText(Buscarticket.this, "Error de conexión.", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void datosTikect(String placa, String idParqueadero){
        datosCarro.setVisibility(View.INVISIBLE);
        factura_pendiente.setVisibility(View.INVISIBLE);
        cargarGif();
        String urlL = dataConfig.getEndPoint("/tickets/getCosto.php");

        datosPots = new HashMap<String, String>();
        datosPots.put("placa", placa);
        if (idParqueadero != null) {
            datosPots.put("idP", idParqueadero);
        }


        conexion.consumoGetParams(urlL, datosPots, new Utils.JsonResponseListener() {


            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Respuesta del servidor: " + response.toString());
                try {
                    if (response.optBoolean("error", false)) {
                        ocultarGif();
                        Toast.makeText(Buscarticket.this, response.optString("mensaje", "Error del servidor."), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Boolean statusResponse = response.getBoolean("status");
                    if(statusResponse){
                        factura_pendiente.setVisibility(View.INVISIBLE);
                        JSONObject objectUser = response.getJSONObject("registros");
                        System.out.println(objectUser.toString());
                        datosCarro.setVisibility(View.VISIBLE);
                        TextView tiempoTranscurrido = findViewById(R.id.tiempoTranscurrido);
                        tiempoTranscurrido.setText("Tiempo estacionado: "+objectUser.getString("tiempo_estacionado"));

                        TextView horaIngreso = findViewById(R.id.horaIngreso);
                        horaIngreso.setText("Hora de ingreso: "+objectUser.getString("hora_ingreso"));

                        TextView totalPagar = findViewById(R.id.totalPagar);
                        totalPagar.setText("Total a pagar: \n"+formatoMoneda(objectUser.getString("total_a_pagar")));

                        TextView parqueadero = findViewById(R.id.parqueadero);
                        parqueadero.setText(objectUser.getString("nombre"));

                        ocultarGif();
                    }else{
                        ocultarGif();
                        datosCarro.setVisibility(View.INVISIBLE);
                        factura_pendiente.setVisibility(View.VISIBLE);
                        TextView notifi = findViewById(R.id.notifi);
                        notifi.setText("No hay factura pendiente en este parqueadero.");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    ocultarGif();
                    Toast.makeText(Buscarticket.this, "Error al leer la respuesta.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                ocultarGif();
                System.out.println("Error del servidor: " + errorMessage);
                Toast.makeText(Buscarticket.this, "Error de conexión.", Toast.LENGTH_LONG).show();
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