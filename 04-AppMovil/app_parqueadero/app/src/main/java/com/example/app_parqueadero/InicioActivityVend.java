package com.example.app_parqueadero;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityInicioVendBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InicioActivityVend extends DrawerVendedor {

    ActivityInicioVendBinding activityInicioVendBinding;
    ImageView gifLoading;
    SharedPreferences archivo;
    TextView nom_logueado;
    Config dataConfig;
    Utils functions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInicioVendBinding = activityInicioVendBinding.inflate(getLayoutInflater());
        setContentView(activityInicioVendBinding.getRoot());
        functions = new Utils(getApplicationContext());
        dataConfig = new Config(getApplicationContext());
        gifLoading = findViewById(R.id.gifCarro);
        nom_logueado = findViewById(R.id.nom_logueado);
        cargarGif();
        getIdParqueadero();
    }



    public  void cargarGif(){
        gifLoading.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(R.drawable.loading_logo).into(gifLoading);

        archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        nom_logueado.setText(archivo.getString("nombre", null));
    }

    public void getIdParqueadero(){
        archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        String id_usuario = archivo.getString("id_usuario",null);
        if (id_usuario != null){
            String endpoint = dataConfig.getEndPoint("/parqueaderos/getParqueaderoVend.php");
            Map<String,String> datosGET = new HashMap<>();
            datosGET.put("id_usuario",id_usuario);
            functions.consumoGetParams(endpoint, datosGET, new Utils.JsonResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject parqueadero = response.getJSONObject("registros");
                        String id_parqueadero =  parqueadero.getString("id_parqueadero");
                        SharedPreferences.Editor editor = archivo.edit();
                        editor.putString("id_parqueadero", id_parqueadero);
                        editor.apply();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    System.out.println(errorMessage);
                }
            });
        }
    }
}