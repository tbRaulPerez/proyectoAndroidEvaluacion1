package com.example.proyectodnd.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.proyectodnd.R;

import io.easyprefs.Prefs;
//Actividad de la pestaña de preferencias. Las preferencias se gestionaran con
//la libreria easyPrefs que simplifica su manejo
public class PreferencesActivity extends AppCompatActivity {

    private Switch darkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        darkTheme = findViewById(R.id.swDarkTheme);
        //Al pulsar sobre el switch darkTheme se guardará su estado en las preferencias y se aplicará el tema a la app
        darkTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //la libreria guarda la preferecias mediante el metodo write con una clave con un valor asociado
                //que se especifican en el metodo content(key, value).
                Prefs.write().content("dark_theme", b).commit();
                Functions.applyTheme();

            }
        });
    }

}