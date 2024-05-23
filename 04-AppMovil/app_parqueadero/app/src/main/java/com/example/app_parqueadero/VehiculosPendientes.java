package com.example.app_parqueadero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityVehiculosPendientesBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VehiculosPendientes extends DrawerVendedor {
    ActivityVehiculosPendientesBinding activityVehiculosPendientesBinding;
    String id_parqueadero;
    Utils functions;
    Config dataConfig;
    RecyclerView recyclerVehiculosPend;
    LinearLayout Cargandi,info;
    ImageView loading_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVehiculosPendientesBinding = activityVehiculosPendientesBinding.inflate(getLayoutInflater());
        setContentView(activityVehiculosPendientesBinding.getRoot());

        SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        id_parqueadero = archivo.getString("id_parqueadero", null);

        functions = new Utils(getApplicationContext());
        dataConfig = new Config(getApplicationContext());

        recyclerVehiculosPend = findViewById(R.id.recyclerVehiculosPend);
        recyclerVehiculosPend.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        info = findViewById(R.id.info);
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);
        ObtenerVehiculosPend();
    }

    public void ObtenerVehiculosPend(){
        info.setVisibility(View.INVISIBLE);
        cargarGif();
        String url = dataConfig.getEndPoint("/tickets/getTi.php");
        Map<String,String> datosGet = new HashMap<>();
        datosGet.put("idP",id_parqueadero);

        functions.consumoGetParams(url, datosGet, new Utils.JsonResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                if (response != null){

                    try {
                        JSONArray listaTicket = response.getJSONArray("registros");
                        int countPending = 0;
                        JSONArray nuevaListaTicket = new JSONArray();
                        for (int i = 0; i < listaTicket.length(); i++) {
                            JSONObject ticket = listaTicket.getJSONObject(i);
                            if (ticket.getString("estado_pago").equals("0")){
                                nuevaListaTicket.put(ticket);
                                countPending++;
                            }
                        }


                        AdapVehiculoPend adaptadoTicketsPend = new AdapVehiculoPend(nuevaListaTicket,countPending);
                        recyclerVehiculosPend.setAdapter(adaptadoTicketsPend);
                        info.setVisibility(View.VISIBLE);
                        ocultarGif();

                    }catch (Exception e){
                        info.setVisibility(View.INVISIBLE);
                        cargarGif();
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(String errorMessage) {
                cargarGif();
                System.out.println(errorMessage);
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