package com.example.test_ttokshow;

import android.app.Application;

public class Barcode extends Application {
    private String Barcode;

    public String getID(){
        return Barcode;
    }

    public void setID(String id){
        this.Barcode = id;
    }


}
