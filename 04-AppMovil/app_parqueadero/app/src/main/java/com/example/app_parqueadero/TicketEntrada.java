package com.example.app_parqueadero;


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
import com.example.app_parqueadero.databinding.ActivityTicketEntradaBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TicketEntrada extends DrawerVendedor {
    LinearLayout facturaPendiente,Cargandi;
    LinearLayout loultimo;
    LinearLayout bien;
    Button insertar;
    Button cancelar;
    EditText campo_buscar_placa;
    String url;
    String placaDB;
    Map<String, String> datosPots;
    Config dataConfig;
    Utils conexion;
    ActivityTicketEntradaBinding activityEntradaBinding;

    TextView placaV;
    TextView propietarioV;
    TextView tipoV;

    String id_parqueadero;
    ImageView loading_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket_entrada);
        activityEntradaBinding = activityEntradaBinding.inflate(getLayoutInflater());
        setContentView(activityEntradaBinding.getRoot());
        facturaPendiente = findViewById(R.id.factura_pendiente);
        loultimo = findViewById(R.id.loultimo);
        bien = findViewById(R.id.bien);
        cancelar = findViewById(R.id.cancelar);
        insertar = findViewById(R.id.insertar);
        campo_buscar_placa = findViewById(R.id.campo_buscar_placa);
        iniciarBotones();
        dataConfig = new Config(getApplicationContext());
        conexion = new Utils(getApplicationContext());
        tipoV = findViewById(R.id.tipo_transporte);
        propietarioV = findViewById(R.id.propietario);
        placaV = findViewById(R.id.placa);
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);

        // obtener el id_parqueadero
        SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        id_parqueadero = archivo.getString("id_parqueadero", null);

    }

    public void buscarCarro(View view) {
        bien.setVisibility(View.INVISIBLE);
        loultimo.setVisibility(View.INVISIBLE);
        facturaPendiente.setVisibility(View.INVISIBLE);

        cargarGif();
        String placa = campo_buscar_placa.getText().toString();
        url = dataConfig.getEndPoint("/vehiculos/findPlaca.php");

        if (!placa.equals("")) {
            datosPots = new HashMap<String, String>();
            datosPots.put("placa",placa);

            conexion.consumoPOST(url, datosPots, new Utils.JsonResponseListenerPOST() {

                @Override
                public void onResponse(String response) {
                    System.out.println("Respuesta del servidor: " + response.toString());

                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        Boolean statusResponse = jsonResponse.getBoolean("status");

                        if(statusResponse){
                            loultimo.setVisibility(View.INVISIBLE);
                            facturaPendiente.setVisibility(View.INVISIBLE);
                            bien.setVisibility(View.VISIBLE);
                            JSONObject objectUser = jsonResponse.getJSONObject("registros");
                            placaDB = objectUser.getString("placa");
                            String propietario = objectUser.getString("propietario");
                            String tipo = objectUser.getString("tipo");
                            placaV.setText(placa);
                            propietarioV.setText(propietario);
                            tipoV.setText(tipo);
                            ocultarGif();
                        }else{
                            ocultarGif();
                            bien.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"No se encontro el vehiculo, registralo porfavor", Toast.LENGTH_LONG).show();


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



        } else {
            ocultarGif();
            Toast.makeText(getApplicationContext(),"El campo es requerido", Toast.LENGTH_LONG).show();
        }
    }

    public void iniciarBotones(){
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarticke();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bien.setVisibility(View.INVISIBLE);
                campo_buscar_placa.setText("") ;
            }
        });

    }
    public void insertarticke(){
        loultimo.setVisibility(View.INVISIBLE);
        facturaPendiente.setVisibility(View.INVISIBLE);
        bien.setVisibility(View.INVISIBLE);
        url = dataConfig.getEndPoint("/tickets/insertTicket.php");

        datosPots = new HashMap<String, String>();
        datosPots.put("placa",placaDB);
        datosPots.put("id_parqueadero",id_parqueadero);

        conexion.consumoPOST(url, datosPots, new Utils.JsonResponseListenerPOST() {

            @Override
            public void onResponse(String response) {
                int min = 1; // Valor mínimo (inclusive)
                int max = 10000; // Valor máximo (inclusive)
                int numeroAleatorio = (int) (Math.random() * (max - min + 1)) + min;
                String numRamdon = Integer.toString(numeroAleatorio);
                System.out.println("Respuesta del servidor: " + response.toString());

                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    Boolean statusResponse = jsonResponse.getBoolean("status");
                    String mensaje = jsonResponse.getString("mesagge");
                    if(statusResponse){
                        facturaPendiente.setVisibility(View.INVISIBLE);
                        bien.setVisibility(View.INVISIBLE);
                        loultimo.setVisibility(View.VISIBLE);
                        JSONObject objectUser = jsonResponse.getJSONObject("registros");

                        TextView horaIngreso = findViewById(R.id.hora);
                        horaIngreso.setText(objectUser.getString("hora_ingreso"));

                        TextView propietario2 = findViewById(R.id.propietario2);
                        propietario2.setText(objectUser.getString("propietario"));

                        TextView placa2 = findViewById(R.id.placa2);
                        placa2.setText(objectUser.getString("placa"));

                        TextView parqueadero = findViewById(R.id.parqueadero);
                        parqueadero.setText(objectUser.getString("nombre"));

                        TextView tipo2 = findViewById(R.id.tipo2);
                        tipo2.setText(objectUser.getString("tipo"));
                    }else{
                        bien.setVisibility(View.INVISIBLE);
                        if(mensaje.equals("FACTURA#PENDIENTE")){
                            facturaPendiente.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(getApplicationContext(),"No se encontro el vehiculo, registralo porfavor", Toast.LENGTH_LONG).show();
                        }

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