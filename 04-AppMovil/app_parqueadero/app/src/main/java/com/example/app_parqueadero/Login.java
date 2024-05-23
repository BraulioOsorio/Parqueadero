package com.example.app_parqueadero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kotlin.Suppress;


public class Login extends AppCompatActivity {
    Utils functions;
    Button btn_login;
    Utils conexion;
    EditText correo;
    EditText pass;
    Config dataConfig;
    String url;
    LinearLayout info,Cargandi;

    ImageView loading_screen;

    Map<String, String> datosGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        functions = new Utils(getApplicationContext());
        btn_login = findViewById(R.id.btn_login);
        conexion = new Utils(getApplicationContext());
        conexion.validarSesion(this);
        info = findViewById(R.id.info);
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard(v);
            }
        });

    }

    public void dashboard(View v) {

        correo = findViewById(R.id.campo_email);
        pass = findViewById(R.id.campo_password);
        dataConfig = new Config(getApplicationContext());
        url = dataConfig.getEndPoint("/usuarios/LoginGet.php");


        if(correo.getText().toString().equals("") || pass.getText().toString().equals("")){
            // Que vibre
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(1000);
            }
            Toast.makeText(getApplicationContext(),"Todos los datos son obligatorios", Toast.LENGTH_LONG).show();
        }else{
            info.setVisibility(View.INVISIBLE);
            cargarGif();
            datosGet = new HashMap<String, String>();
            datosGet.put("correo", correo.getText().toString());
            datosGet.put("contrasenia", pass.getText().toString());
            conexion.consumoGetParams(url, datosGet, new Utils.JsonResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Respuesta del servidor: " + response.toString());
                    try {
                        Boolean statusResponse = response.getBoolean("status");
                        if(statusResponse){
                            JSONObject objectUser = response.getJSONObject("usuario");
                            String id_usuario = objectUser.getString("documento");
                            String statusUser = objectUser.getString("estado");
                            String rolUser = objectUser.getString("rol");
                            String email = objectUser.getString("correo");
                            String nombre = objectUser.getString("nombre");
                            if(statusUser.equalsIgnoreCase("1")){
                                if(rolUser.equalsIgnoreCase("vendedor")){

                                    functions.iniciarNuevaActividad(InicioActivityVend.class);
                                    SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = archivo.edit();
                                    editor.putString("id_usuario", id_usuario);
                                    editor.putString("rol", rolUser);
                                    editor.putString("email", email);
                                    editor.putString("nombre", nombre);
                                    editor.apply();
                                }else{
                                    System.out.println("entrando a admin");
                                    functions.iniciarNuevaActividad(InicioActivity.class);
                                    SharedPreferences archivo = getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = archivo.edit();
                                    editor.putString("id_usuario", id_usuario);
                                    editor.putString("rol", rolUser);
                                    editor.putString("email", email);
                                    editor.putString("nombre", nombre);
                                    editor.apply();
                                }
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"Se encuentra inactivo", Toast.LENGTH_LONG).show();
                                ocultarGif();
                                info.setVisibility(View.VISIBLE);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Usuario no registrado en el sistema", Toast.LENGTH_LONG).show();
                            ocultarGif();
                            info.setVisibility(View.VISIBLE);
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
        }


    }
    public  void cargarGif(){
        Cargandi.setVisibility(View.VISIBLE);
        loading_screen.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(R.drawable.login).into(loading_screen);
    }
    public void ocultarGif(){
        Cargandi.setVisibility(View.INVISIBLE);

        loading_screen.setVisibility(View.INVISIBLE);

    }


}