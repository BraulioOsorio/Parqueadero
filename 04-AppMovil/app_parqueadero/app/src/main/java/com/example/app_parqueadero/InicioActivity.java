package com.example.app_parqueadero;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.databinding.ActivityInicioBinding;

public class InicioActivity extends DrawerBaseActivity {
    ActivityInicioBinding activityInicioBinding;
    ImageView gifCarro;

    TextView nom_logueado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInicioBinding = activityInicioBinding.inflate(getLayoutInflater());
        setContentView(activityInicioBinding.getRoot());
        gifCarro = findViewById(R.id.gifCarro);

        nom_logueado = findViewById(R.id.nom_logueado);

        cargarGif();
    }

    public  void cargarGif(){
        gifCarro.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(R.drawable.cargando).into(gifCarro);

        SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        nom_logueado.setText(archivo.getString("nombre", null));
    }
}