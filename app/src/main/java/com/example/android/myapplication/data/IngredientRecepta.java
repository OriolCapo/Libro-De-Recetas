package com.example.android.myapplication.data;

/**
 * Created by Oriolcapo on 12/01/2016.
 */
public class IngredientRecepta {

    private long id;
    private String nomRecepta;
    private String nom;

    public IngredientRecepta(long id, String nomRecepta, String nom) {
        this.id = id;
        this.nomRecepta = nomRecepta;
        this.nom = nom;
    }

    public long getId() {
        return id;
    }

    public String getNomRecepta() {
        return nomRecepta;
    }

    public String getNom() {
        return nom;
    }
}
