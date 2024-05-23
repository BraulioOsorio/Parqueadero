package com.example.app_parqueadero;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.app_parqueadero.databinding.ActivityEditarParqueaderoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditarParqueadero extends DrawerBaseActivity {
    ActivityEditarParqueaderoBinding activityEditarParqueaderoBinding;
    Config dataConfig;
    Utils functions;
    EditText update_nombre_parqueadero,update_direccion_parqueadero,update_buscar;
    TextView etq_estado,etq_idP;
    Button btn_estado;
    ImageView img_buscar_update;
    String firtsStatus,firtsNameP,firtsDireccion = "";
    LinearLayout Cargandi,info;
    ImageView loading_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditarParqueaderoBinding = activityEditarParqueaderoBinding.inflate(getLayoutInflater());
        setContentView(activityEditarParqueaderoBinding.getRoot());

        dataConfig = new Config(getApplicationContext());
        functions = new Utils(getApplicationContext());

        update_nombre_parqueadero = findViewById(R.id.update_nombre_parqueadero);
        update_direccion_parqueadero = findViewById(R.id.update_direccion_parqueadero);
        etq_estado = findViewById(R.id.etq_estado);
        btn_estado = findViewById(R.id.btn_estado);
        update_buscar = findViewById(R.id.update_buscar);
        img_buscar_update = findViewById(R.id.img_buscar_update);
        etq_idP = findViewById(R.id.etq_idP);
        info = findViewById(R.id.info);
        Cargandi = findViewById(R.id.Cargandi);
        loading_screen = findViewById(R.id.loading_screen);
        btn_estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataStatus(btn_estado.getText().toString());
            }
        });
    }

    public void buscarParqueaderoUpdate(View view){
        info.setVisibility(View.INVISIBLE);
        cargarGif();

        String nombreParqueadero = update_buscar.getText().toString();
        if (!nombreParqueadero.equalsIgnoreCase("")){
            String url = dataConfig.getEndPoint("/parqueaderos/BuscarParqueadero.php");
            Map<String,String> datosPost = new HashMap<>();
            datosPost.put("nombre",nombreParqueadero);
            functions.consumoPOST(url,datosPost, new Utils.JsonResponseListenerPOST() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject datosJson = new JSONObject(response);
                        JSONArray parqueadero = datosJson.getJSONArray("parqueadero");
                        if (parqueadero.isNull(0)){
                            Toast.makeText(getApplicationContext(),"Parqueadero no encontrado", Toast.LENGTH_SHORT).show();
                            cleanData();
                            ocultarGif();
                            info.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(getApplicationContext(),"Parqueadero encontrado con exito", Toast.LENGTH_SHORT).show();
                            mostrarParqueadero(parqueadero);
                            info.setVisibility(View.VISIBLE);
                            ocultarGif();
                        }
                    }catch (Exception e){
                        cargarGif();
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(String errorMessage) {
                    cargarGif();

                    System.out.println(errorMessage);
                }
            });
        }else{
            ocultarGif();
            info.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Ingrese el nombre del parqueadero", Toast.LENGTH_LONG).show();
        }

    }

    public void mostrarParqueadero(JSONArray arrayJSON) throws JSONException {

        JSONObject parking = arrayJSON.getJSONObject(0);
        update_nombre_parqueadero.setText(parking.getString("nombre"));
        update_direccion_parqueadero.setText(parking.getString("direccion"));
        etq_idP.setText(parking.getString("id_parqueadero"));
        firtsDireccion = parking.getString("direccion");
        firtsNameP = parking.getString("nombre");
        String estado = parking.getString("estado");
        if (estado.equalsIgnoreCase("1")){
            changeStatus("Activo",Color.GREEN,"Desactivar",Color.RED);
            firtsStatus = "Activo";
        }else{
            changeStatus("Inactivo",Color.RED,"Activar",Color.GREEN);
            firtsStatus = "Inactivo";
        }
    }

    public void cleanData(){
        update_direccion_parqueadero.setText("");
        update_nombre_parqueadero.setText("");
        etq_estado.setText("Estado");
        etq_estado.setTextColor(Color.BLACK);
        btn_estado.setText("Estado");
        etq_idP.setText("");
        btn_estado.setBackgroundColor(Color.parseColor("#0461e1"));
    }

    public void setDataStatus(String status){
        if (status.equalsIgnoreCase("activar")){
            changeStatus("Activo",Color.GREEN,"Desactivar",Color.RED);
        } else if (status.equalsIgnoreCase("desactivar")) {
            changeStatus("Inactivo",Color.RED,"Activar",Color.GREEN);
        }
    }

    public void changeStatus(String etq_status,int colorEtq,String btnText,int colorBtn){
        etq_estado.setText(etq_status);
        etq_estado.setTextColor(colorEtq);
        btn_estado.setText(btnText);
        btn_estado.setBackgroundColor(colorBtn);
    }

    public String getStatus(){
        String status = "";
        if (etq_estado.getText().toString().equalsIgnoreCase("Activo")){
            status = "1";
        }else if(etq_estado.getText().toString().equalsIgnoreCase("Inactivo")){
            status = "0";
        }
        return status;
    }

    public void updateParking(View view){
        if (etq_idP.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(),"Busque un parqueadero para actualizarlo", Toast.LENGTH_LONG).show();
        }else{
            if ( update_nombre_parqueadero.getText().toString().equalsIgnoreCase(firtsNameP) && update_direccion_parqueadero.getText().toString().equalsIgnoreCase(firtsDireccion) && etq_estado.getText().toString().equalsIgnoreCase(firtsStatus) ){
                Toast.makeText(getApplicationContext(),"No hay nada que actualizar", Toast.LENGTH_LONG).show();
            }else{
                if (!update_direccion_parqueadero.getText().toString().equalsIgnoreCase("") && !update_nombre_parqueadero.getText().toString().equalsIgnoreCase("") ){
                    String url = dataConfig.getEndPoint("/parqueaderos/UpdateParking.php");
                    Map<String,String> datosPost = new HashMap<>();
                    datosPost.put("id_parqueadero",etq_idP.getText().toString());
                    datosPost.put("nombre",update_nombre_parqueadero.getText().toString());
                    datosPost.put("direccion",update_direccion_parqueadero.getText().toString());
                    datosPost.put("estado",getStatus());

                    functions.consumoPOST(url, datosPost, new Utils.JsonResponseListenerPOST() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            cleanData();
                            Toast.makeText(getApplicationContext(),"Actualizado exitosamente!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            System.out.println(errorMessage);
                            cleanData();
                            Toast.makeText(getApplicationContext(),"Error al actualizar", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Los campos no deben estar vacios", Toast.LENGTH_LONG).show();
                }
            }

        }
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