package com.example.app_parqueadero.core;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_parqueadero.DetalleParqueadero;
import com.example.app_parqueadero.InicioActivity;
import com.example.app_parqueadero.InicioActivityVend;
import com.example.app_parqueadero.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    Config dataConfig;
    Context contexto;
    public Utils(Context context){
        this.contexto = context;
        dataConfig = new Config(contexto);
    }

    public void iniciarNuevaActividad(Class<?> destinoActividad){
        Intent intent = new Intent(contexto, destinoActividad);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // Add this line to set the new task flag
        contexto.startActivity(intent);
    }

    public void validarSesion(Login login){
        SharedPreferences archivo = contexto.getSharedPreferences("data_usuario", Context.MODE_PRIVATE);
        String emailUsuario = archivo.getString("email", null);
        String idUsuario = archivo.getString("id_usuario", null);
        String rol = archivo.getString("rol", null);

        if(idUsuario != null && emailUsuario != null){
            if (rol.equalsIgnoreCase("admin")){
                iniciarNuevaActividad(InicioActivity.class);
            } else {
                iniciarNuevaActividad(InicioActivityVend.class);
            }
            login.finish();
        }

    }

    public  void cerrarSesion(){
        SharedPreferences archivo = contexto.getSharedPreferences("data_usuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = archivo.edit();
        editor.clear();
        editor.apply();
    }


    public void consumoPOST(String endpoint,Map<String, String> params,final JsonResponseListenerPOST listener){
        System.out.println("Iniciando consumo POST");
        RequestQueue queue = Volley.newRequestQueue(contexto);
        String url = endpoint;

        StringRequest solicitud =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    if (listener != null) {
                        listener.onResponse(response);
                    }

                } catch (Exception e) {
                    System.out.println("El servidor POST responde con un error:");
                    System.out.println(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("El servidor POST responde con un error:");
                System.out.println(error.getMessage());
                if (listener != null) {
                    listener.onError(error.getMessage());
                }
            }
        }){
            protected Map<String, String> getParams(){

                return params;
            }
        };

        queue.add(solicitud);
    }


    public interface JsonResponseListener {
        void onResponse(JSONObject response);
        void onError(String errorMessage);
    }

    public interface JsonResponseListenerPOST {
        void onResponse(String response);
        void onError(String errorMessage);
    }

    public void consumoGetParams(String baseUrl, Map<String, String> parametros, final JsonResponseListener listener) {
        System.out.println("Iniciando consumo GET");
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        if (parametros != null && !parametros.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : parametros.entrySet()) {
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }

        String url = urlBuilder.toString();

        RequestQueue queue = Volley.newRequestQueue(contexto);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("El servidor responde OK");
                if (listener != null) {
                    listener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("El servidor responde con un error:");
                System.out.println(error.getMessage());
                if (listener != null) {
                    listener.onError(error.getMessage());
                }
            }
        });

        queue.add(solicitud);
    }

    public void consumoGet(String url, final JsonResponseListener listener){
        System.out.println("Iniciando consumo");

        RequestQueue queue = Volley.newRequestQueue(contexto);


        JsonObjectRequest solicitud =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("El servidor responde OK");
                if (listener != null) {
                    listener.onResponse(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("El servidor responde con un error:");
                System.out.println(error.getMessage());
                if (listener != null) {
                    listener.onError(error.getMessage());
                }
            }
        });

        queue.add(solicitud);
    }


}
