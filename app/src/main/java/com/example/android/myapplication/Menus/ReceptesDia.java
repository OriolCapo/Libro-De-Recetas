package com.example.android.myapplication.Menus;

import com.example.android.myapplication.Data.Recepta;

import java.util.ArrayList;

/**
 * Created by Oriolcapo on 10/01/2017.
 */

public class ReceptesDia {
    private String nomDia;
    private ArrayList<String> receptes;

    public ArrayList<String> getReceptes() {
        return receptes;
    }

    public void setReceptes(ArrayList<String> receptes) {
        this.receptes = receptes;
    }

    public String getNomDia() {
        return nomDia;
    }

    public void setNomDia(String nomDia) {
        this.nomDia = nomDia;
    }

}
