package com.example.proyectodnd.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import io.easyprefs.Prefs;

//clase con funciones auxiliares para poder llamarlas desde diferentes actividades.
public class Functions {
    //Aplica el thema seleccionado en la ventana de preferencias
    public static void applyTheme(){
        if(Prefs.read().content("dark_theme", false)){
            System.out.println("entra");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
