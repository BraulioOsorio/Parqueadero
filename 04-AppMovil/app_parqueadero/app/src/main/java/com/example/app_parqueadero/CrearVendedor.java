package com.example.app_parqueadero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityCrearVendedorBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CrearVendedor extends DrawerBaseActivity {

    ActivityCrearVendedorBinding activityCrearVendedorBinding;
    Config dataConfig;
    Utils functions;
    EditText campo_documento;
    EditText campo_nombre;
    EditText campo_correo;
    EditText campo_contrasenia;

    Map<String, String> datosPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCrearVendedorBinding = activityCrearVendedorBinding.inflate(getLayoutInflater());
        setContentView(activityCrearVendedorBinding.getRoot());

        dataConfig = new Config(getApplicationContext());
        functions = new Utils(getApplicationContext());

        campo_documento = findViewById(R.id.campo_documento_vendedor);
        campo_nombre = findViewById(R.id.campo_nombre_vendedor);
        campo_correo = findViewById(R.id.campo_correo_vendedor);
        campo_contrasenia = findViewById(R.id.campo_contrasenia_vendedor);
    }

    public void crearVendedor(View view) {
        String documento = campo_documento.getText().toString();
        String nombre = campo_nombre.getText().toString();
        String correo = campo_correo.getText().toString();
        if(validarcorreo(correo)){
            String contrasenia = campo_contrasenia.getText().toString();

            datosPost = new HashMap<>();
            datosPost.put("documento", documento);
            datosPost.put("nombre", nombre);
            datosPost.put("correo", correo);
            datosPost.put("contrasenia", contrasenia);

            if (documento.isEmpty() || nombre.isEmpty() || correo.isEmpty() || contrasenia.isEmpty()) {
                Toast.makeText(getApplicationContext(), "POR FAVOR LLENAR TODOS LOS CAMPOS", Toast.LENGTH_LONG).show();
            } else {
                validarVendedor(datosPost);
            }
        }else{
            Toast.makeText(getApplicationContext(), "FORMATO DE CORREO INVALIDO", Toast.LENGTH_LONG).show();
        }


    }

    public void validarVendedor(Map<String, String> datosPost) {
        String endpoint = dataConfig.getEndPoint("/usuarios/findVendedor.php");
        functions.consumoPOST(endpoint, datosPost, new Utils.JsonResponseListenerPOST() {
            @Override
            public void onResponse(String response) {
                if (response.contains("false")) {
                    insertarVendedor(datosPost);
                } else {
                    Toast.makeText(getApplicationContext(), "Vendedor " + datosPost.get("documento") + " ya existe", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(String errorMessage) {
                System.out.println(errorMessage);
            }

        });
    }

    public void insertarVendedor(Map<String, String> datosPost) {
        String endpoint = dataConfig.getEndPoint("usuarios/Insert.php");
        functions.consumoPOST(endpoint, datosPost, new Utils.JsonResponseListenerPOST() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(jsonObject.getBoolean("status"));
                    if (jsonObject.getBoolean("status")) {
                        limpiarFormulario();
                        Toast.makeText(getApplicationContext(), "Vendedor creado correctamente :)", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error :( intenta más tarde", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
    public boolean validarcorreo(String correo) {
        boolean validar = false;
        int cont = 0;
        int contPuntos = 0;
        for (int i = 0; i < correo.length(); i++) {
            if (correo.charAt(i) == '@') {
                cont++;
            }
            if (cont == 1 && correo.charAt(i) == '.') {
                contPuntos++;
            }
        }
        if (cont == 1 && contPuntos == 2 || cont == 1 && contPuntos == 1) {
            validar = true;
        }
        return validar;
    }


    public void limpiarFormulario() {
        campo_documento.setText("");
        campo_nombre.setText("");
        campo_correo.setText("");
        campo_contrasenia.setText("");
    }
}