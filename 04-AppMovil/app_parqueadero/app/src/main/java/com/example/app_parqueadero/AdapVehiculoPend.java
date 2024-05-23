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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdapVehiculoPend extends RecyclerView.Adapter<AdapVehiculoPend.ViewHolder>{

    JSONArray ticketPending;
    int size;
    public  AdapVehiculoPend(JSONArray ticketPending,int size){
        this.ticketPending = ticketPending;
        this.size = size;
    }
    @NonNull
    @Override
    public AdapVehiculoPend.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_vehiculo_pend, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapVehiculoPend.ViewHolder holder, int position) {
        try {
            JSONObject ticket = ticketPending.getJSONObject(position);
            holder.imprimirDatosTicket(ticket);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView etq_placa_pend;
        TextView etq_hora_ingreso;
        TextView etq_tiempo_transcu;
        Button btn_salida;
        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etq_placa_pend = itemView.findViewById(R.id.etq_placa_pend);
            etq_hora_ingreso = itemView.findViewById(R.id.etq_hora_ingreso);
            etq_tiempo_transcu = itemView.findViewById(R.id.etq_tiempo_transcu);
            btn_salida = itemView.findViewById(R.id.btn_salida);
            context = itemView.getContext();
        }

        public void imprimirDatosTicket(JSONObject ticket){
            try {
                etq_hora_ingreso.setText(ticket.getString("hora_ingreso"));
                etq_placa_pend.setText(ticket.getString("placa"));
                etq_tiempo_transcu.setText(ticket.getString("tiempo_transcurrido"));

                btn_salida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TicketSalida.class);
                        intent.putExtra("placa",etq_placa_pend.getText().toString());
                        context.startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
