package com.example.proyectodnd.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyectodnd.R;
import com.example.proyectodnd.adapter.RecyclerAdapter;
import com.example.proyectodnd.model.ListItem;

import java.util.ArrayList;
import java.util.List;
//Actividad que maneja la pestaña Mechanichs
public class MechanicsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //se usara el adapter que no usa conexiones pues se le pasará una lista personalizada (metodo getList).
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics);

        //configuracion del recyclerView.
        recyclerView = findViewById(R.id.rvMechanics);
        recyclerAdapter = new RecyclerAdapter(getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //Se dejara de mostrar el nombre de la app en el actionBar además de mostrar un boton de atrás.
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    //Gestiona lo que se hace al pulsar el boton back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Devuelve una lista con los items que se desean mostrar en el recyclerView
    public List<ListItem> getList() {
        List<ListItem> list = new ArrayList<>();

        list.add(new ListItem("Conditions", R.drawable.conditions));
        list.add(new ListItem("Damage types", R.drawable.damage_types));
        list.add(new ListItem("Magic schools", R.drawable.magic_schools));

        return list;
    }
}