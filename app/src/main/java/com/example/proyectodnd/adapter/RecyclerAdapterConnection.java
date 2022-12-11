package com.example.proyectodnd.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodnd.R;
import com.example.proyectodnd.controller.ClassActivity;
import com.example.proyectodnd.controller.MechanicsItemActivity;
import com.example.proyectodnd.controller.MonsterActivity;
import com.example.proyectodnd.controller.SpellActivity;
import com.example.proyectodnd.controller.SubracesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Este adaptador es usado para los recyclerViews que usan peticiones externas
public class RecyclerAdapterConnection extends RecyclerView.Adapter<RecyclerAdapterConnection.RecyclerHolder> {
    JSONArray list;
    //Recibe un JSONArray con el que creará los objetos del recyclerView
    public RecyclerAdapterConnection(JSONArray list){
        this.list = list;
    }
    //Metodo que recibe una lista filtrada por el searchbar y la asigna al recyclerView
    public void setFilteredList(JSONArray filteredList){
        list = filteredList;

        notifyDataSetChanged();
    }

    //Se crea la estructura de la lista a partir del layout custom_item_list
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_list_connection, parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    //Se asigna la informacion correspondiente a cada vista de cada celda

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        try {
            JSONObject jsonObject = (JSONObject) list.get(position);
            holder.title.setText(jsonObject.getString("name"));
            String url = jsonObject.getString("url");
            //Listener que comprueba la url del objeto tocado para lanzar
            //la actividad correspondiente
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(url.contains("/api/spells/")){
                        startListItemActivity(holder, SpellActivity.class, url);
                    } else if(url.contains("/api/monsters/")){
                        startListItemActivity(holder, MonsterActivity.class, url);
                    } else if(url.contains("/api/conditions/") || url.contains("/api/damage-types/") || url.contains("/api/magic-schools/")){
                        startListItemActivity(holder, MechanicsItemActivity.class, url);
                    } else if(url.contains("/api/classes/")){
                        startListItemActivity(holder, ClassActivity.class, url);
                    } else if(url.contains("/api/races/")){
                        startListItemActivity(holder, SubracesActivity.class, url);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.length();
    }

    //Lanza una actividad recibiendo por parametro el RecyclerHolder, la clase de la actividad, y la url
    public void startListItemActivity(RecyclerAdapterConnection.RecyclerHolder holder, Class activityClass, String url){
        Intent intent = new Intent(holder.itemView.getContext(), activityClass);
            if(url != null && url != ""){
                intent.putExtra("URL", url);
            }
            holder.itemView.getContext().startActivity(intent);
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView title;

        //enlaza la variable con la vista correspondiente recibiendo la vista del item como parametro
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.txTItleConnection);
        }
    }
}
