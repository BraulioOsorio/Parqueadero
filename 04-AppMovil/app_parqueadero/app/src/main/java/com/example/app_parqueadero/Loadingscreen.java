package com.example.app_parqueadero;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.databinding.ActivityInicioBinding;

public class Loadingscreen extends DrawerBaseActivity {
    ActivityInicioBinding activityInicioBinding;
    ImageView loading_screen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInicioBinding = activityInicioBinding.inflate(getLayoutInflater());
        setContentView(activityInicioBinding.getRoot());
        loading_screen = findViewById(R.id.loading_screen);
        cargarGif();
    }

    public  void cargarGif(){
        loading_screen.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(R.drawable.pantalla_carga).into(loading_screen);
    }
}