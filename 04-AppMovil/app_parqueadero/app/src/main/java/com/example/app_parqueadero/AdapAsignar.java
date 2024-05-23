package com.example.app_parqueadero;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_parqueadero.core.Config;
import com.example.app_parqueadero.core.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdapAsignar extends RecyclerView.Adapter<AdapAsignar.ViewHolder> {

    JSONArray parqueaderos;
    public AdapAsignar(JSONArray parqueaderos){
        this.parqueaderos = parqueaderos;

    }

    @NonNull
    @Override
    public AdapAsignar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_asignar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapAsignar.ViewHolder holder, int position) {
        try {
            JSONObject objectParqueadero = parqueaderos.getJSONObject(position);
            holder.PrintDataParking(objectParqueadero);
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return parqueaderos.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Utils functions;
        Config dataConfig;
        TextView etq_parqueadero_asig;
        TextView etq_direccion_asig;
        TextView etq_cant_asig;
        Button btn_card_asig;
        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etq_cant_asig = itemView.findViewById(R.id.etq_cant_asig);
            etq_direccion_asig = itemView.findViewById(R.id.etq_direccion_asig);
            etq_parqueadero_asig = itemView.findViewById(R.id.etq_parqueadero_asig);
            btn_card_asig = itemView.findViewById(R.id.btn_card_asig);
            context = itemView.getContext();
            functions = new Utils(itemView.getContext());
            dataConfig = new Config(itemView.getContext());
        }

        public void PrintDataParking(JSONObject parking){
            try {
                String id_parqueadero = parking.getString("id_parqueadero");
                String endpoint = dataConfig.getEndPoint("/parqueaderos/selectCantVend.php");
                Map<String,String> dataPost = new HashMap<>();
                dataPost.put("id_parqueadero",id_parqueadero);
                functions.consumoPOST(endpoint, dataPost, new Utils.JsonResponseListenerPOST() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            String cantVendedores = objectResponse.getString("cantVendedores");
                            etq_cant_asig.setText(cantVendedores);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        System.out.println(errorMessage);
                    }
                });
                String nombre_parqueadero = parking.getString("nombre");
                String direccion = parking.getString("direccion");
                etq_parqueadero_asig.setText(nombre_parqueadero);
                etq_direccion_asig.setText(direccion);
                btn_card_asig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AsignarParqueadero.class);
                        intent.putExtra("id_parqueadero",id_parqueadero);
                        intent.putExtra("nombre",nombre_parqueadero);
                        context.startActivity(intent);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
