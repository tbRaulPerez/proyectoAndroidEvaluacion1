package com.example.proyectodnd.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.proyectodnd.R;
import com.example.proyectodnd.adapter.RecyclerAdapterConnection;
import com.example.proyectodnd.io.ConnectDnd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//Actividad que maneja todas las pestañas que usan un RecyclerView de items
//recibidos por una conexion externa
public class ConnectionListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapterConnection recyclerAdapter;
    JSONObject objetoJson;
    JSONArray arrayJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_list);

        recyclerView = findViewById(R.id.rvList);

        //Se dejará de mostrar el nombre de la app en el actionBar además de mostrar un boton de atrás.
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //Se recoge el extra que contiene la url de la petición y se crea
        // un objeto Connections (hereda de AsyncTask) ejecutandolo, pues es una operacion con un tiempo de respuesta largo.
        Intent i = getIntent();
        new Connections().execute(i.getStringExtra("URL"));
    }

    //Se crea un menu que usara el menu_with_searchbar xml
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_with_searchbar, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //se aplica un queryTextListener a la barra de busqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            //Se sobrescribe el metodo que gestiona cuando el texto en el searchbar cambia y se
            //llama al metodo search
            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return false;
            }
        });

        return true;
    }

    //metodo que gestiona lo que ocurre al pulsar el boton de atrás
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //metodo que realiza la busqueda de la barra de busqueda, recibe por parametro el texto
    //que contiene la barra.
    public void search(String text){
        JSONArray filteredList = new JSONArray();
        JSONObject jsonItem;
        //se recorre la lista inicial que muestra el recycler (la que contiene todos los items)
        //comprobando si contiene la cadena que se busca para añadirlo a una nueva lista filtrada
        for( int i = 0; i < arrayJson.length(); i++){
            try {
                jsonItem = arrayJson.getJSONObject(i);
                if(jsonItem.getString("name").toLowerCase().contains(text.toLowerCase())){
                    filteredList.put(jsonItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Se comprueba que la lista filtrada tenga resultados y se llama al metodo del adapter setFilteredList que
        //la aplicará al recyclerView
        if(filteredList.length() == 0 || filteredList == null){
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }else {
            recyclerAdapter.setFilteredList(filteredList);
        }
    }



    //Esta clase realiza las conexiones de forma asincrona y posteriormente interpreta el
    // resultado JSON asignando la informacion a su vista correspondiente
    private class Connections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ConnectDnd.getRequest(strings[0]);
        }
        @Override
        protected void onPostExecute(String s){
            if(s != null){
                try {
                    objetoJson = new JSONObject(s);
                    arrayJson = objetoJson.getJSONArray("results");
                    recyclerAdapter = new RecyclerAdapterConnection(arrayJson);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(ConnectionListActivity.this);

                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(layoutManager);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(ConnectionListActivity.this, "there was a problem while loading data", Toast.LENGTH_LONG).show();
            }
        }
    }
}