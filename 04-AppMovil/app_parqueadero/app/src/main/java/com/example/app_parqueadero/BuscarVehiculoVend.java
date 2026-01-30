package com.example.app_parqueadero;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityBuscarVehiculoVendBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BuscarVehiculoVend extends DrawerVendedor{

    ActivityBuscarVehiculoVendBinding activityBuscarVehiculoVendBinding;
    RecyclerView recyclerVehiculos;
    AdapBuscarVehiculo adapBuscarVehiculo;
    Config config;
    Utils functions;
    String url;
    EditText campo_email;
    TextView etq_notificacion;
    ImageView img_buscar;
    LinearLayout Datos,Cargandi;
    ImageView loading_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBuscarVehiculoVendBinding = activityBuscarVehiculoVendBinding.inflate(getLayoutInflater());
        setContentView(activityBuscarVehiculoVendBinding.getRoot());
        campo_email = findViewById(R.id.campo_email);
        img_buscar = findViewById(R.id.img_buscar);
        etq_notificacion = findViewById(R.id.etq_notificacion);
        recyclerVehiculos = findViewById(R.id.recyclerVehiculos);
        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        functions = new Utils(getApplicationContext());
        config = new Config(getApplicationContext());
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);
        SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        String idParqueadero = archivo.getString("id_parqueadero", null);
        url = idParqueadero != null
                ? config.getEndPoint("/vehiculos/getVehiculos.php") + "?id_parqueadero=" + idParqueadero
                : config.getEndPoint("/vehiculos/getVehiculos.php");
        Datos = findViewById(R.id.Datos);
        printAllVehiculos(url);
        campo_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            //Si requiere que el evento sea boolean
            public boolean onTouch(View v, MotionEvent event) {
                // Verificar si se hizo clic en el icono cancelar
                if (event.getAction() == MotionEvent.ACTION_UP &&
                        event.getRawX() >= campo_email.getRight() - campo_email.getCompoundDrawables()[2].getBounds().width()) {
                    // Se hizo clic en el icono cancelar
                    campo_email.setText(""); // Limpiar el texto del campo de email
                    printAllVehiculos(url);
                    return true;
                }
                return false;
            }
        });
    }

    public  void printAllVehiculos(String url){
        Datos.setVisibility(View.INVISIBLE);
        cargarGif();
        functions.consumoGet(url, new Utils.JsonResponseListener(){

            @Override
            public void onResponse(JSONObject response) {
                if (response != null){
                    try {
                        etq_notificacion.setVisibility(View.GONE);
                        JSONArray registros = response.getJSONArray("registros");
                        adapBuscarVehiculo = new AdapBuscarVehiculo(registros);
                        recyclerVehiculos.setAdapter(adapBuscarVehiculo);
                        Datos.setVisibility(View.VISIBLE);
                        ocultarGif();
                    } catch (JSONException e) {
                        cargarGif();
                        throw new RuntimeException(e);


                    }
                }else{
                    ocultarGif();
                    etq_notificacion.setVisibility(View.VISIBLE);
                    etq_notificacion.setText("¡AUN NO HAY VEHICULOS REGISTRADOS!");
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("Error del servidor: " + errorMessage);
                cargarGif();
            }
        });
    }

    public  void buscarVehiculo(View v){
        String txtSearch = campo_email.getText().toString();
        System.out.println("Placa que se escribió: "+txtSearch);
        findVehicle(config.getEndPoint("/vehiculos/findVehiculo.php"),txtSearch);
    }

    public  void findVehicle(String urlFind, String txtSearch){
        Datos.setVisibility(View.INVISIBLE);

        cargarGif();
        SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        String idParqueadero = archivo.getString("id_parqueadero", null);
        Map<String, String> datosPost = new HashMap<String, String>();
        datosPost.put("placa", txtSearch);
        if (idParqueadero != null) {
            datosPost.put("id_parqueadero", idParqueadero);
        }

        functions.consumoPOST(urlFind, datosPost, new Utils.JsonResponseListenerPOST(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);

                    if (responseJson.getBoolean("status")){
                        JSONArray registros = responseJson.getJSONArray("registros");
                        etq_notificacion.setVisibility(View.GONE);
                        adapBuscarVehiculo = new AdapBuscarVehiculo(registros);
                        recyclerVehiculos.setAdapter(adapBuscarVehiculo);
                        Datos.setVisibility(View.VISIBLE);

                        ocultarGif();

                    }else {
                        ocultarGif();
                        etq_notificacion.setVisibility(View.VISIBLE);
                        etq_notificacion.setText("¡No se encontró nigún vehículo! \n");
                        etq_notificacion.append("revise bien los datos");
                    }
                } catch (JSONException e) {
                    cargarGif();
                    throw new RuntimeException(e);

                }
            }

            @Override
            public void onError(String errorMessage) {
                cargarGif();
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
