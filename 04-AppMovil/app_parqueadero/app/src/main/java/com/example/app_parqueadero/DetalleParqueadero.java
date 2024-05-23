package com.example.app_parqueadero;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityDetalleParqueaderoBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetalleParqueadero extends DrawerVendedor {
    ActivityDetalleParqueaderoBinding activityDetalleParqueaderoBinding;
    Utils functions;
    Config config;
    String id_usuario;
    TextView etq_nombre_parqueadero;
    TextView etq_direccion_parqueadero;
    TextView etq_precio_carro;
    TextView etq_precio_moto;
    TextView etq_precio_camioneta;
    TextView etq_precio_camion;
    LinearLayout info,Cargandi;
    ImageView loading_screen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetalleParqueaderoBinding = activityDetalleParqueaderoBinding.inflate(getLayoutInflater());
        setContentView(activityDetalleParqueaderoBinding.getRoot());
        functions = new Utils(getApplicationContext());
        config = new Config(getApplicationContext());
        SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        id_usuario = archivo.getString("id_usuario", null);
        //----------------------------------------------------------------
        etq_nombre_parqueadero = findViewById(R.id.etq_nombre_parqueadero);
        etq_direccion_parqueadero = findViewById(R.id.etq_direccion_parqueadero);
        etq_precio_carro = findViewById(R.id.etq_precio_carro);
        etq_precio_moto = findViewById(R.id.etq_precio_moto);
        etq_precio_camioneta = findViewById(R.id.etq_precio_camioneta);
        etq_precio_camion = findViewById(R.id.etq_precio_camion);
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);
        info = findViewById(R.id.info);

        //----------------------------------------------------------------
        printDatosParking();

    }

    public  void printDatosParking(){
        info.setVisibility(View.INVISIBLE);
        cargarGif();
        String url = config.getEndPoint("/parqueaderos/getParqueaderoVend.php");
        Map<String, String> datosGet = new HashMap<String, String>();
        datosGet.put("id_usuario", id_usuario);
        functions.consumoGetParams(url, datosGet, new Utils.JsonResponseListener(){

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Respuesta del getParqueaderoVend: "+response.toString());
                try {
                    JSONObject parqueadero = response.getJSONObject("registros");
                    etq_nombre_parqueadero.setText(parqueadero.getString("nombre"));
                    etq_direccion_parqueadero.setText(parqueadero.getString("direccion"));
                    etq_precio_carro.setText(parqueadero.getString("carro"));
                    etq_precio_moto.setText(parqueadero.getString("moto"));
                    etq_precio_camioneta.setText(parqueadero.getString("camioneta"));
                    etq_precio_camion.setText(parqueadero.getString("camioneta"));
                    info.setVisibility(View.VISIBLE);
                    ocultarGif();
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