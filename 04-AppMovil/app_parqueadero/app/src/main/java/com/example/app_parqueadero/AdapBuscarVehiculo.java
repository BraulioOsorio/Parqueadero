package com.example.app_parqueadero;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdapBuscarVehiculo extends RecyclerView.Adapter<AdapBuscarVehiculo.ViewHolder>{

    JSONArray vehiculos;
    public  AdapBuscarVehiculo(JSONArray vehiculos){
        this.vehiculos = vehiculos;
    }
    @NonNull
    @Override
    public AdapBuscarVehiculo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_vehiculos, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapBuscarVehiculo.ViewHolder holder, int position) {
        try {
            JSONObject vehiculo = vehiculos.getJSONObject(position);
            holder.printDatosVehiculo(vehiculo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return vehiculos.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView etq_placa;
        TextView etq_propietario;
        TextView etq_tipo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etq_placa = itemView.findViewById(R.id.etq_placa);
            etq_propietario = itemView.findViewById(R.id.etq_propietario);
            etq_tipo = itemView.findViewById(R.id.etq_tipo);
        }

        public  void printDatosVehiculo(JSONObject vehiculo){
            try {
                System.out.println(vehiculo.toString());
                etq_placa.setText(vehiculo.getString("placa"));
                etq_propietario.setText(vehiculo.getString("propietario"));
                etq_tipo.setText(vehiculo.getString("tipo"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
