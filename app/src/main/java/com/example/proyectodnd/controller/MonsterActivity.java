package com.example.proyectodnd.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectodnd.R;
import com.example.proyectodnd.io.ConnectDnd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

//Actividad que detalla los items de tipo monster
public class MonsterActivity extends AppCompatActivity {
    private ImageView imgView;
    private CircularProgressDrawable progressDrawable;
    private TextView txName;
    private TextView txHitpoints;
    private TextView txArmorClass;
    private TextView txType;
    private TextView txSize;
    private TextView txSpeed;
    private TextView txChallengeRating;
    private TextView txAbilityScores;
    private TextView txLanguages;
    private TextView txSenses;
    private TextView txProficiencies;
    private TextView txSpecialAbilities;
    private TextView txActions;
    private TextView txLegendaryActions;
    private JSONObject objetoJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster);

        imgView = findViewById(R.id.imgView);

        //configuracion del drawable que se muestra mietras carga la imagen
        progressDrawable = new CircularProgressDrawable(this);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setStyle(CircularProgressDrawable.DEFAULT);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.start();

        txName = findViewById(R.id.txName);
        txHitpoints = findViewById(R.id.txHitPoints);
        txArmorClass = findViewById(R.id.txArmorClass);
        txType = findViewById(R.id.txType);
        txSize = findViewById(R.id.txSize);
        txSpeed = findViewById(R.id.txSpeed);
        txAbilityScores = findViewById(R.id.txAbilityScores);
        txChallengeRating = findViewById(R.id.txChallengeRating);
        txLanguages = findViewById(R.id.txLanguages);
        txSenses = findViewById(R.id.txSenses);
        txProficiencies = findViewById(R.id.txProficiencies);
        txSpecialAbilities = findViewById(R.id.txSpecialAbilities);
        txActions = findViewById(R.id.txActions);
        txLegendaryActions = findViewById(R.id.txLegendaryActions);

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
                    objetoJson = new JSONObject(s);
                    StringBuilder sb = null;
                    JSONArray jsonArray = null;
                    try {
                        //Se usa la libreria glide para recoger la imagen mediante su url. NO TODOS LOS OBJETOS TIENEN IMAGENES.
                        Glide.with(MonsterActivity.this).load("https://www.dnd5eapi.co" + objetoJson.getString("image")).placeholder(progressDrawable).error(R.drawable.logo).into(imgView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        txName.setText(objetoJson.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        txHitpoints.setText("Hit points: " + objetoJson.getString("hit_points"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        txArmorClass.setText("Armor class: " + objetoJson.getInt("armor_class"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        txSize.setText("Size: " + objetoJson.getString("size"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        txType.setText("Type: " + objetoJson.getString("type"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        sb = new StringBuilder();
                        JSONObject speed = objetoJson.getJSONObject("speed");
                        Iterator<String > itSpeed = speed.keys();
                        while (itSpeed.hasNext()){
                            String key = itSpeed.next();
                            sb.append("\n" + key + ": " + speed.getString(key));
                        }
                        txSpeed.setText("Speed: " + sb);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        txChallengeRating.setText("CR: " + objetoJson.getInt("challenge_rating"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        txAbilityScores.setText("Strength: " + objetoJson.getInt("strength") +
                                "\nDexterity: " + objetoJson.getInt("dexterity") +
                                "\nConstitution: " + objetoJson.getInt("constitution") +
                                "\nIntelligence: " + objetoJson.getInt("intelligence") +
                                "\nWisdom: " + objetoJson.getInt("wisdom") +
                                "\nCharisma: " + objetoJson.getInt("charisma"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        txLanguages.setText(objetoJson.getString("languages"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        sb = new StringBuilder();
                        JSONObject senses = objetoJson.getJSONObject("senses");
                        Iterator<String> itSenses = senses.keys();
                        while(itSenses.hasNext()){
                            String key = itSenses.next();
                            sb.append(key.replace("_", " ") + ": " + senses.getString(key) + "\n");
                        }
                        txSenses.setText(sb);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        sb = new StringBuilder();
                        jsonArray = new JSONArray(objetoJson.getString("proficiencies"));
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONObject proficiency = new JSONObject(object.getString("proficiency"));

                            sb.append(proficiency.getString("name") + "  " + object.getString("value") + "\n");
                        }
                        txProficiencies.setText(sb);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        sb = new StringBuilder();
                        jsonArray =  new JSONArray(objetoJson.getString("special_abilities"));
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject specialAbility = jsonArray.getJSONObject(i);
                            sb.append(specialAbility.getString("name") + ": " + specialAbility.getString("desc") + "\n\n");
                        }
                        txSpecialAbilities.setText(sb);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        sb = new StringBuilder();
                        jsonArray = new JSONArray(objetoJson.getString("actions"));
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject action = jsonArray.getJSONObject(i);
                            sb.append(action.getString("name") + ": " + action.getString("desc") + "\n\n");
                        }
                        txActions.setText(sb);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        sb = new StringBuilder();
                        jsonArray = new JSONArray(objetoJson.getString("legendary_actions"));
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject legendaryAction = jsonArray.getJSONObject(i);
                            sb.append(legendaryAction.getString("name") + ": " + legendaryAction.getString("desc") + "\n\n");
                        }
                        txLegendaryActions.setText(sb);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    Toast.makeText(MonsterActivity.this, "there was a problem while loading data", Toast.LENGTH_LONG).show();

                }

            }else{
                Toast.makeText(MonsterActivity.this, "there was a problem while loading data", Toast.LENGTH_LONG).show();
            }
        }
    }
}