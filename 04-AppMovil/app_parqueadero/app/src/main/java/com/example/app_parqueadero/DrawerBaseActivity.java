package com.example.app_parqueadero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;

import com.example.app_parqueadero.core.Utils;
import com.google.android.material.navigation.NavigationView;


public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Utils functions;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);
        functions = new Utils(getApplicationContext());
        // androidx.appcompat.w
        toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation_drawer,R.string.close_Navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        drawerLayout.closeDrawer(GravityCompat.START );
        manejaracciondenavegacion(item.getItemId());
        return  false;
    }


    private void manejaracciondenavegacion(int itemId){
        if (itemId == R.id.nav_inicio_admin){
            iniciarNuevaActividad(InicioActivity.class);
        }else if(itemId == R.id.nav_crear_parqueadero){
            iniciarNuevaActividad(CrearParqueadero.class);
        }else if(itemId == R.id.nav_editar_parqueadero){
            iniciarNuevaActividad(EditarParqueadero.class);
        } else if (itemId == R.id.cerrar_sesion) {
            functions.cerrarSesion();
            iniciarNuevaActividad(Login.class);
            finish();
        }else if (itemId == R.id.nav_crear_vendedor) {
            iniciarNuevaActividad(CrearVendedor.class);
        } else if (itemId == R.id.nav_asignar_parqueadero) {
            iniciarNuevaActividad(CardsParqueaderos.class);
        }
    }

    public void iniciarNuevaActividad(Class<?> destinoActividad){
        startActivity(new Intent(this,destinoActividad));
        overridePendingTransition(0,0);
    }


}