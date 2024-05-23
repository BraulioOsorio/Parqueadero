package com.example.app_parqueadero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.app_parqueadero.databinding.ActivityCardsParqueaderosBinding;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.app_parqueadero.databinding.ActivityAsignarParqueaderoBinding;

import java.util.HashMap;
import java.util.Map;

public class AsignarParqueadero extends DrawerBaseActivity {
    Utils functions;
    Config dataConfig;
    String idParqueadero,documento,nombreParqueadero;
    TextView etq_nameParking,notifiVendedor,nombreVendedor,correoVendedor,rolVendedor;
    ImageView btn_searchVendedor;
    EditText campo_searchVendedor;
    LinearLayout datosVendedor,Cargandi;
    Button btn_asignarVendedor;
    ActivityAsignarParqueaderoBinding activityAsignarParqueaderoBinding;
    Loadingscreen carga;
    ImageView loading_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAsignarParqueaderoBinding = activityAsignarParqueaderoBinding.inflate(getLayoutInflater());
        setContentView(activityAsignarParqueaderoBinding.getRoot());
        // -------------------------------------------------
        datosVendedor = findViewById(R.id.datosVendedor);
        etq_nameParking = findViewById(R.id.etq_nameParking);
        btn_searchVendedor = findViewById(R.id.btn_searchVendedor);
        campo_searchVendedor = findViewById(R.id.campo_searchVendedor);
        notifiVendedor = findViewById(R.id.notifiVendedor);
        nombreVendedor = findViewById(R.id.nombreVendedor);
        correoVendedor = findViewById(R.id.correoVendedor);
        rolVendedor = findViewById(R.id.rolVendedor);
        btn_asignarVendedor=findViewById(R.id.btn_asignarVendedor);
        carga = new Loadingscreen();


        // --------------------------------------------------
        functions = new Utils(getApplicationContext());
        dataConfig = new Config(getApplicationContext());

        Intent intent = getIntent();
        nombreParqueadero = intent.getStringExtra("nombre");
        idParqueadero = intent.getStringExtra("id_parqueadero");
        etq_nameParking.setText(nombreParqueadero);
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);

        btn_searchVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarVendedor();
            }
        });

        btn_asignarVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarParqueadero(documento,idParqueadero);
            }
        });

    }

    public void buscarVendedor(){
        datosVendedor.setVisibility(View.INVISIBLE);
        String cedula = campo_searchVendedor.getText().toString();
        cargarGif();
        if (!cedula.isEmpty()){
            String endpoint = dataConfig.getEndPoint("/usuarios/findVendedor.php");
            Map<String,String> dataPost = new HashMap<>();
            dataPost.put("documento",cedula);
            functions.consumoPOST(endpoint, dataPost, new Utils.JsonResponseListenerPOST() {
                @Override
                public void onResponse(String response) {
                    System.out.println(idParqueadero);
                    System.out.println(response);


                    try {

                        JSONObject objectResponse = new JSONObject(response);

                        Boolean status = objectResponse.getBoolean("status");
                        System.out.println(status);
                        if(!status){
                            datosVendedor.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"El usuario que usted ingreso es admin", Toast.LENGTH_LONG).show();

                        }else{
                            JSONObject vendedor = objectResponse.getJSONObject("vendedor");

                            documento = vendedor.getString("documento");
                            nombreVendedor.setText(vendedor.getString("nombre"));
                            correoVendedor.setText(vendedor.getString("correo"));
                            rolVendedor.setText(vendedor.getString("rol"));
                            datosVendedor.setVisibility(View.VISIBLE);
                            ocultarGif();
                        }





                    }catch (Exception e){
                        cargarGif();
                    }


                }

                @Override
                public void onError(String errorMessage) {
                    cargarGif();
                    System.out.println(errorMessage);

                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Ingresa la cedula del vendedor", Toast.LENGTH_LONG).show();
        }
    }

    public void asignarParqueadero(String documento,String idParqueadero){
        String endpoint = dataConfig.getEndPoint("/parqueadero_vendedores/asignarVendedor.php");
        Map<String,String> dataPost = new HashMap<>();
        dataPost.put("id_usuario",documento);
        dataPost.put("id_parqueadero",idParqueadero);
        functions.consumoPOST(endpoint, dataPost, new Utils.JsonResponseListenerPOST() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    Boolean statusResponse = respuesta.getBoolean("status");
                    if (statusResponse){
                        Toast.makeText(getApplicationContext(),"Vendedor asignado con exito", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"El usuario no es vendedor o no esta registrado", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String errorMessage) {

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