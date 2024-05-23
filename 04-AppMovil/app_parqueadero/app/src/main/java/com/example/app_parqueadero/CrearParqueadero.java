package com.example.app_parqueadero;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.CountDownLatch;
import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;
import com.example.app_parqueadero.databinding.ActivityCrearParqueaderoBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CrearParqueadero extends DrawerBaseActivity {
    ActivityCrearParqueaderoBinding activityCrearParqueaderoBinding;
    EditText campo_nombre_parqueadero;
    EditText campo_direccion_parqueadero;
    Utils functions;
    Config dataConfig;
    Map<String,String> datosPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCrearParqueaderoBinding = activityCrearParqueaderoBinding.inflate(getLayoutInflater());
        setContentView(activityCrearParqueaderoBinding.getRoot());

        campo_nombre_parqueadero = findViewById(R.id.campo_nombre_parqueadero);
        campo_direccion_parqueadero = findViewById(R.id.campo_direccion_parqueadero);

        functions = new Utils(getApplicationContext());
        dataConfig = new Config(getApplicationContext());

    }

    public void createParqueadero(View v){
        String campo_nombre = campo_nombre_parqueadero.getText().toString();
        String campo_direccion = campo_direccion_parqueadero.getText().toString();

        if (campo_nombre.equalsIgnoreCase("") && campo_direccion.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "TODOS LOS CAMPOS SON OBLIGATORIOS!!", Toast.LENGTH_LONG).show();
        } else {
            // Lanzar AsyncTask para realizar la operación de red
            new CheckParkingExistenceTask().execute(campo_nombre);
        }
    }

    private class CheckParkingExistenceTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String nombre = params[0];
            final Boolean[] verify = {null};

            String url = dataConfig.getEndPoint("/parqueaderos/BuscarParqueadero.php");
            Map<String, String> datosPost = new HashMap<>();
            datosPost.put("nombre", nombre);

            CountDownLatch latch = new CountDownLatch(1);

            functions.consumoPOST(url, datosPost, new Utils.JsonResponseListenerPOST() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject datosJson = new JSONObject(response);
                        JSONArray parqueadero = datosJson.getJSONArray("parqueadero");
                        verify[0] = !parqueadero.isNull(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();  // Indicar que la operación ha terminado
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    System.out.println(errorMessage);
                    latch.countDown();  // Indicar que la operación ha terminado incluso en caso de error
                }
            });

            try {
                latch.await();  // Esperar a que la operación de red se complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return verify[0];
        }

        @Override
        protected void onPostExecute(Boolean existParking) {
            if (existParking != null && existParking) {
                Toast.makeText(getApplicationContext(), "Ya existe un parqueadero registrado como: " + campo_nombre_parqueadero.getText().toString(), Toast.LENGTH_LONG).show();
            } else {

                datosPost = new HashMap<>();
                datosPost.put("nombre",campo_nombre_parqueadero.getText().toString());
                datosPost.put("direccion",campo_direccion_parqueadero.getText().toString());

                String endpoint = dataConfig.getEndPoint("/parqueaderos/Insert.php");

                functions.consumoPOST(endpoint, datosPost, new Utils.JsonResponseListenerPOST() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            Boolean status = responseJSON.getBoolean("status");
                            if (status){
                                campo_nombre_parqueadero.setText("");
                                campo_direccion_parqueadero.setText("");
                                Toast.makeText(getApplicationContext(),"PARQUEADERO CREADO CON EXITO!!", Toast.LENGTH_LONG).show();
                            }else{
                                campo_nombre_parqueadero.setText("");
                                campo_direccion_parqueadero.setText("");
                                Toast.makeText(getApplicationContext(),"Error verifique los datos", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        System.out.println(response);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        System.out.println(errorMessage);
                    }
                });
            }
        }
    }

}