package com.example.app_parqueadero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityCardsParqueaderosBinding;

import org.json.JSONArray;
import org.json.JSONObject;

public class CardsParqueaderos extends DrawerBaseActivity {
    ActivityCardsParqueaderosBinding activityCardsParqueaderosBinding;
    Utils functions;
    Config dataConfig;
    RecyclerView recyclerCardAsig;
    LinearLayout Cargandi,empresas;
    ImageView loading_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCardsParqueaderosBinding = activityCardsParqueaderosBinding.inflate(getLayoutInflater());
        setContentView(activityCardsParqueaderosBinding.getRoot());
        functions = new Utils(getApplicationContext());
        dataConfig = new Config(getApplicationContext());
        recyclerCardAsig = findViewById(R.id.recyclerCardAsig);

        recyclerCardAsig.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        empresas = findViewById(R.id.empresas);
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);

        getParkings();
    }

    public void getParkings(){

        cargarGif();
        String endpoint = dataConfig.getEndPoint("/parqueaderos/getParqueaderos.php");
        functions.consumoGet(endpoint, new Utils.JsonResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray registros = response.getJSONArray("registros");
                    if (registros.length() > 0){
                        AdapAsignar adaptadoTicketsPend = new AdapAsignar(registros);
                        recyclerCardAsig.setAdapter(adaptadoTicketsPend);
                        empresas.setVisibility(View.VISIBLE);
                        ocultarGif();

                    }else{
                        Toast.makeText(getApplicationContext(),"No hay parqueaderos registrados en el sistema", Toast.LENGTH_LONG).show();

                        ocultarGif();

                    }
                }catch (Exception e){
                    e.printStackTrace();

                    cargarGif();
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println(errorMessage);

                cargarGif();
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