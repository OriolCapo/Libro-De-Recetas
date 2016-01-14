package com.example.android.myapplication;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.example.android.myapplication.data.IngredientRecepta;
import com.example.android.myapplication.data.IngredientsReceptesDAO;
import com.example.android.myapplication.data.Recepta;
import com.example.android.myapplication.data.ReceptesDAO;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

/*    public void test_insert_recepta() {
        ReceptesDAO rDAO = new ReceptesDAO(getContext());
        long id = rDAO.createRecepta("paella","","");
        Log.d("Insertar Recepta ", Long.toString(id));
        assertTrue(id != -1 && id > 0);
    }
/*
    public void test_get_recepta() {
        ReceptesDAO rDAO = new ReceptesDAO(getContext());
        String nomRecepta = rDAO.getRecepta(1).getNom();
        Log.d("nom ingredient ", nomRecepta);
        assertTrue(nomRecepta.equals("pipes"));
    }

    public void test_delete_all_receptes() {
        ReceptesDAO rDAO = new ReceptesDAO(getContext());
        int quants = rDAO.deleteAllReceptes();
        Log.d("elem eliminats", Integer.toString(quants));
        assertTrue(quants != 0);
    }

    public void test_delete_recepta() {
        ReceptesDAO rDAO = new ReceptesDAO(getContext());
        int quants = rDAO.deleteReceptaByName("pipes");
        Log.d("elem eliminats", Integer.toString(quants));
        assertTrue(quants != 0);
    }

    public void test_insert_ingredient() {
        ReceptesDAO rDAO = new ReceptesDAO(getContext());
        IngredientsReceptesDAO irDAO = new IngredientsReceptesDAO(getContext());
        long idR = rDAO.getRecepta(1).getId();
        long idIR = irDAO.createIngredientsRecepta(idR,"sal");
        Log.d("id IR creat", Long.toString(idIR));
        assertTrue(idIR != -1 && idIR > 0);
    }

    public void test_get_ingredient_recepta() {
        IngredientsReceptesDAO irDAO = new IngredientsReceptesDAO(getContext());
        IngredientRecepta ingrRec = irDAO.getIngredientReceptaByName(1,"sal");
        Log.d("nom IR 1", ingrRec.getNom());
        assertTrue(ingrRec.getNom().equals("sal"));
    }

    public void test_delete_ingredients_from_recepta() {
        IngredientsReceptesDAO irDAO = new IngredientsReceptesDAO(getContext());
        int quants = irDAO.deleteIngredientsOfRecepta(1);
        Log.d("elem eliminats", Integer.toString(quants));
        assertTrue(quants == 0);
    }

    public void test_get_all_receptes() {
        ReceptesDAO rDAO = new ReceptesDAO(getContext());
        ArrayList<Recepta> list = rDAO.getAllReceptes();
        assertTrue(list != null);
    }*/

}