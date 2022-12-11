package com.example.proyectodnd.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectodnd.R;
import com.example.proyectodnd.model.User;
import com.orm.SugarContext;

import java.util.List;

import io.easyprefs.Prefs;
//Actividad que gestiona el login
public class LoginActivity extends AppCompatActivity {

    private ImageView imgLogin;
    private EditText etUserName;
    private EditText etPassword;
    private TextView txCreateAccount;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Prefs.initializeApp(this);
        setContentView(R.layout.activity_login);
        //Como esta es la primera actividad de la app, se aplicará
        //el tema seleccionado en las preferencias cuando se crea la misma
        Functions.applyTheme();

        //Se inicializa la libreria externa SugarORM, que facilita
        //la persistencia de objetos. Se usará para el sistema de usuarios
        SugarContext.init(this);

        imgLogin = findViewById(R.id.imgLogin);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        txCreateAccount = findViewById(R.id.txCreateAcccount);
        btLogin = findViewById(R.id.btLogin);

        //Al pulsar en el boton login, se recogen los datos de los campos y se validan
        //para despues lanzar la ventana principal de la app
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = User.find(User.class, "user_name = ?", etUserName.getText().toString());
                if(users.size() != 0 && users != null){
                    if(users.get(0).getPassword().equals(etPassword.getText().toString())){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        etUserName.setText("");
                        etPassword.setText("");
                        startActivity(i);
                    }else{
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Al pulsar sobre crear una nueva cuenta, se lanza la actividad RegisterActivity
        txCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(i);
            }
        });

    }
    //Se cierra la conexion a la base de datos al terminar la actividad
    @Override
    public void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }
}