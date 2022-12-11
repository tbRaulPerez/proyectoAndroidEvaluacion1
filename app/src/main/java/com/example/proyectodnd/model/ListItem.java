package com.example.proyectodnd.model;
//Item que usarán los RecyclerView que no usen conexiones
public class ListItem {

    private String title;
    private int imgId;

    public ListItem(String title, int imgId){
        this.title = title;
        this.imgId = imgId;
    }

    public String getTitle(){
        return title;
    }

    public int getImgId(){
        return imgId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setImgId(int imgId){
        this.imgId = imgId;
    }

}
