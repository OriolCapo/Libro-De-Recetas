package com.example.android.myapplication.Data;

/**
 * Created by Oriolcapo on 13/01/2016.
 */
public class IngredientSubstitut {

    private String nomRecepta;
    private String nomIngredient;
    private String nomSubstitut;

    public IngredientSubstitut(String nomRecepta, String nomIngredient, String nomSubstitut) {
        this.nomRecepta = nomRecepta;
        this.nomIngredient = nomIngredient;
        this.nomSubstitut = nomSubstitut;
    }

    public String getNomSubstitut() {
        return nomSubstitut;
    }
}
