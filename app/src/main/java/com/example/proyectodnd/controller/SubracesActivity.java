package com.example.proyectodnd.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.proyectodnd.R;
import com.example.proyectodnd.io.ConnectDnd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//Actividad que detalla los items de subrace (Dentro de character options)
public class SubracesActivity extends AppCompatActivity {

    private TextView txRaceName;
    private TextView txRaceSpeed;
    private TextView txAge;
    private TextView txRaceSize;
    private TextView txStartingProficiencies;
    private TextView txRaceLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subraces_activity);

        txRaceName = findViewById(R.id.txRaceName);
        txRaceSpeed = findViewById(R.id.txRaceSpeed);
        txAge = findViewById(R.id.txAge);
        txRaceSize = findViewById(R.id.txRaceSize);
        txStartingProficiencies = findViewById(R.id.txStartingProficiencies);
        txRaceLanguages = findViewById(R.id.txRaceLanguages);

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
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                StringBuilder sb = null;

                try {
                    jsonObject = new JSONObject(s);

                    try{
                        txRaceName.setText(jsonObject.getString("name"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try{
                        txRaceSpeed.setText(jsonObject.getInt("speed") + "");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try{
                        txAge.setText(jsonObject.getString("age"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try{
                        txRaceSize.setText(jsonObject.getString("size"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try{
                        sb = new StringBuilder();
                        jsonArray = jsonObject.getJSONArray("starting_proficiencies");
                        for(int i = 0; i < jsonArray.length(); i++){
                            sb.append(jsonArray.getJSONObject(i).getString("name"));
                        }
                        txStartingProficiencies.setText(sb);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    try{
                        txRaceLanguages.setText(jsonObject.getString("language_desc"));
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