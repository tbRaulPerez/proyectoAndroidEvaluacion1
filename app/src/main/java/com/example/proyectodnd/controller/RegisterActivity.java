package com.example.proyectodnd.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodnd.R;
import com.example.proyectodnd.model.User;
import com.orm.SugarContext;

import java.util.List;

//Actividad que gestiona el registro de usuarios en la base de datos, se usará la libreria SugarORM
public class RegisterActivity extends AppCompatActivity {


    private EditText etUserName;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etConfirmPassword;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUserName = findViewById(R.id.etRegisterUserName);
        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPass);
        etConfirmPassword = findViewById(R.id.etRegisterConfirmPass);
        btRegister = findViewById(R.id.btRegister);

        //Al pulsar sobre el boton de registrar, se validan todos los campos
        // y después se comprueba si el usuario ya existe. Si es nuevo, se
        //guardará en la base de datos
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmptyFields()){
                    Toast.makeText(RegisterActivity.this, "You must fill all the fields", Toast.LENGTH_LONG).show();
                }else {
                    String username = etUserName.getText().toString();
                    String email = etEmail.getText().toString();
                    String pass = etPassword.getText().toString();
                    String confirmPass = etConfirmPassword.getText().toString();
                    if(email.contains("@")){
                        if(pass.length() >= 4){
                            if(pass.equals(confirmPass)){
                                User user = new User(username, email, pass);
                                if(user.exist() == false){
                                    user.save();
                                    Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "This user already exist", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(RegisterActivity.this, "Confirm password is not the same", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "Password must have at least 4 characters", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Invalid email adress", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    //Comprueba que los campos no estan vacíos
    private boolean checkEmptyFields() {
        Boolean isEmpty = false;
        if(etUserName.getText().toString().equals("")
        || etEmail.getText().toString().equals("")
        || etPassword.getText().toString().equals("")
        || etConfirmPassword.getText().toString().equals("")){
            isEmpty = true;
        }
        return isEmpty;
    }

    private boolean checkPassword(){
     return false;
    }

}