package com.example.android.myapplication.Data;

/**
 * Created by Oriolcapo on 12/01/2016.
 */
public class Recepta {

    private long id;
    private String nom;
    private String descripcio;
    private String suggeriments;

    public Recepta () {

    }

    public Recepta (String nom) {
        this.nom = nom;
    }

    public Recepta(String nom, String descripcio, String suggeriments) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.suggeriments = suggeriments;
    }

    public Recepta(long id, String nom, String descripcio, String suggeriments) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.suggeriments = suggeriments;
    }

    public long getId() {

        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getSuggeriments() {
        return suggeriments;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setSuggeriments(String suggeriments) {
        this.suggeriments = suggeriments;
    }
}
