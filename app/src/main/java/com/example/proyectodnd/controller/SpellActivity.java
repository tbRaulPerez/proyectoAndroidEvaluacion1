package com.example.proyectodnd.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectodnd.R;
import com.example.proyectodnd.io.ConnectDnd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

//Actividad que detalla los items de tipo spell
public class SpellActivity extends AppCompatActivity {

    private TextView txSpellName;
    private TextView txRitual;
    private TextView txRange;
    private TextView txDuration;
    private TextView txConcentration;
    private TextView txLevel;
    private TextView txDesc;
    private TextView txCastingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);

        txSpellName = findViewById(R.id.txSpellName);
        txRitual = findViewById(R.id.txRitual);
        txRange = findViewById(R.id.txRange);
        txDuration = findViewById(R.id.txDuration);
        txConcentration = findViewById(R.id.txConcentration);
        txLevel = findViewById(R.id.txLevel);
        txCastingTime = findViewById(R.id.txCastingTime);
        txDesc = findViewById(R.id.txDesc);

        //Se dejará de mostrar el nombre de la app en el actionBar además se mostrará un boton de atrás.
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //Se recoge el extra que contiene la url del item del RecyclerView que se pulsó
        Intent i = getIntent();
        String msg = i.getStringExtra("URL");
        //Se crea un objeto Connections (hereda de AsyncTask) y se ejecuta pues es una operacion con un tiempo de respuesta largo.
        new Connections().execute(msg);
    }

    //gestiona lo que ocurre al pulsar el boton de atrás
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Esta clase realiza las conexiones de forma asincrona y posteriormente interpreta el
    // resultado JSON asignando la informacion a su vista correspondiente
    private class Connections extends AsyncTask<String, Void, String> {
        //realiza las conexiones en un hilo secundario
        @Override
        protected String doInBackground(String... strings) {
            return ConnectDnd.getRequest(strings[0]);
        }
        //asigna la informacion a la vista corespondiente. cada operacion se realiza
        //en un try por separado por si se da el caso de que uno fallase, asi no afecta
        //al resto
        @Override
        protected void onPostExecute(String s){
            if(s != null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    try {
                        txSpellName.setText(jsonObject.getString("name"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try {
                        txRange.setText("Range: " + jsonObject.getString("range"));
                    }catch (JSONException e){
                    }

                    try {
                        if(jsonObject.getBoolean("ritual")){
                            txRitual.setText("Ritual: yes");
                        }else{
                            txRitual.setText("Ritual: no");
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try {
                        if(jsonObject.getBoolean("concentration")){
                            txConcentration.setText("Requires concentration: yes");
                        }else{
                            txConcentration.setText("Requires concentration: no");
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try {
                        txLevel.setText("Level: " + jsonObject.getInt("level"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try {
                        txCastingTime.setText("Casting time" + jsonObject.getString("casting_time"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try {
                        txDuration.setText("Duration: " + jsonObject.getString("duration"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("desc");
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0; i < jsonArray.length(); i++){
                            sb.append(jsonArray.getString(i) + "\n");
                        }
                        txDesc.setText(sb);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}