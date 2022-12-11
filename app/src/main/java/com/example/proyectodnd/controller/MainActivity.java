package com.example.proyectodnd.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.proyectodnd.R;
import com.example.proyectodnd.adapter.RecyclerAdapter;
import com.example.proyectodnd.model.ListItem;

import java.util.ArrayList;
import java.util.List;
//Ventana principal de la app, contiene un RecyclerView con diferentes opciones a elegir. Estas opciones vienen dadas
//por el metodo getList()
public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //se usara el adapter que no usa conexiones pues se le pasará una lista personalizada (metodo getList).
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configuracion del recyclerView,
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //Se elimina el titulo de la actionBar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }
    //Se crea un menu con con el diseño primary_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.primary_menu,menu);

        return true;
    }
    //Se gestiona lo que sucede al pulsar los elementos del menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();

        switch (itemId){
            //Al pulsar sobre el botón de preferencias se lanza la actividad correspondiente
            case R.id.item_preferences:
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    //Se sobrescribe el metodo que gestiona lo que sucede al pulsar el boton de atras
    //para añadir una alerta de que se cerrará la sesión
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to log out?").setTitle("Log out");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
    //devuelve la lista de items para el recyclerView
    public List<ListItem> getList(){
        List<ListItem> list = new ArrayList<>();

        list.add(new ListItem("Character Options", R.drawable.character_options));
        list.add(new ListItem("Mechanics", R.drawable.mechanics));
        list.add(new ListItem("Spells", R.drawable.spells));
        list.add(new ListItem("Monsters", R.drawable.monsters));

        return list;
    }
}