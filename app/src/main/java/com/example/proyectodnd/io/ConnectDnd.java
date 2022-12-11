package com.example.proyectodnd.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//Clase que maneja las conexiones(peticiones)
public class ConnectDnd {
    //startpoint de la api
    private static final String URL_BASE = "https://www.dnd5eapi.co";

    //metodo que recibe el endpoint de la conexion y devuelve el resultado de la petición
    public static String getRequest(String endpoint){
        HttpURLConnection http = null;
        String contenido = null;
        try {
            //Se completa la url con el endpoint
            URL url = new URL(URL_BASE + endpoint);
            //Se configura el request
            http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            //Si el codigo de respuesta es correcto, se lee el stream de la petición
            //y se añade a un Stringbuilder. Posteriormente se asignara el contendio del
            //StringBuilder a la variable contenido(sera la que devuelva el metodo)
             if(http.getResponseCode() == HttpURLConnection.HTTP_OK){
                 StringBuilder sb = new StringBuilder();

                 BufferedReader reader = new BufferedReader( new InputStreamReader(http.getInputStream()));
                 String line;
                 while((line = reader.readLine()) != null){
                     sb.append(line);
                 }
                 contenido = sb.toString();
                 reader.close();
             }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(http != null){
                http.disconnect();
            }
        }
        return contenido;
    }
}
