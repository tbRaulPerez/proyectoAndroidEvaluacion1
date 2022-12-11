package com.example.proyectodnd.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;
//Clase  usuario, persistirá en la base de datos, por ello hereda de sugarRecord
public class User extends SugarRecord {
    @Unique
    private String userName;
    private String email;
    private  String password;

    public User(){
    }

    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getUserName(){
      return this.userName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //metodo que comprueba si existe en la base de datos el usuario que lo llama
    public Boolean exist(){
        Boolean exist =false;
        List<User> users = User.findWithQuery(User.class, "Select * from user where user_name = ?", this.getUserName());
        if (users.size() != 0 && users != null){
            exist = true;
        }

        return exist;
    }
}
