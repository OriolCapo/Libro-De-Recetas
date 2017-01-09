package com.example.android.myapplication.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Oriolcapo on 12/01/2016.
 */
public class IngredientsReceptesDAO {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private Context context;

    public IngredientsReceptesDAO(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createIngredientsRecepta (String nomRecepta, String nom) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME, nom);
        values.put(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME, nomRecepta);
        long insertId = database.insert(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME, null, values);
        close();
        return insertId;
    }

    public int deleteIngredientsOfRecepta (String nomRecepta) {
        open();
        String whereclause = DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta + "'";
        int quants = database.delete(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME, whereclause, null);
        close();
        IngredientsSubstitutsDAO isDAO = new IngredientsSubstitutsDAO(context);
        isDAO.deleteIngredientsSubstitutsOfRecepta(nomRecepta);
        return quants;
    }

    public IngredientRecepta getIngredientReceptaByName(String nomRecepta, String nomIngredient) {
        open();
        String whereclause = DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta +
                "' AND " + DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME + " = '" + nomIngredient + "'";
        Cursor cursor = database.query(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME, null,
                whereclause, null, null, null, null);
        cursor.moveToFirst();
        IngredientRecepta ingrRec = cursorToIngredientRecepta(cursor);
        cursor.close();
        close();
        return ingrRec;
    }

    public ArrayList<IngredientRecepta> getIngredientsOfRecepta(String nomRecepta) {
        open();
        String whereclause = DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta + "'";
        Cursor cursor = database.query(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME, null,
                whereclause, null, null, null, null);
        ArrayList<IngredientRecepta> ingredientsReceptes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ingredientsReceptes.add(cursorToIngredientRecepta(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return ingredientsReceptes;
    }

    private IngredientRecepta cursorToIngredientRecepta(Cursor cursor) {
        long idI = cursor.getLong(cursor.getColumnIndex(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry._ID));
        String nomRecepta = cursor.getString(cursor.getColumnIndex(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME));
        String nom = cursor.getString(cursor.getColumnIndex(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME));
        IngredientRecepta iR = new IngredientRecepta(idI, nomRecepta, nom);
        return iR;
    }

    public ArrayList<String> getNomsIngredientsOfRecepta(String nomRecepta) {
        open();
        String whereclause = DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta + "'";
        Cursor cursor = database.query(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME, null,
                whereclause, null, null, null, null);
        ArrayList<String> nomsIngredientsReceptes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                IngredientRecepta i = cursorToIngredientRecepta(cursor);
                nomsIngredientsReceptes.add(i.getNom());
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return nomsIngredientsReceptes;
    }

    public void updateNameOfRecepta(String oldNomRecepta, String nomRecepta) {
        open();
        String whereclause = DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + oldNomRecepta + "'";
        ContentValues nouRegistre = new ContentValues();
        nouRegistre.put(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME, nomRecepta);
        database.update(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME, nouRegistre, whereclause, null);
        close();
    }

    public void updateIngredient(String nomRecepta, String oldIngredient, String newIngredient) {
        open();
        String whereclause = DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta +
                "' AND " + DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME + " = '" + oldIngredient + "'";
        ContentValues nouRegistre = new ContentValues();
        nouRegistre.put(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME, newIngredient);
        database.update(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME, nouRegistre, whereclause, null);
        close();
    }
}
