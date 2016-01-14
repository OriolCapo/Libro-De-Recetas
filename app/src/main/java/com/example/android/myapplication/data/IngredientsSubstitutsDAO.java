package com.example.android.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Oriolcapo on 13/01/2016.
 */
public class IngredientsSubstitutsDAO {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private Context context;

    public IngredientsSubstitutsDAO(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createIngredientSubstitut (String nomRecepta, String nomIngredient, String nom) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME, nomRecepta);
        values.put(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME, nomIngredient);
        values.put(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_SUBSTITUT_NAME, nom);
        long insertId = database.insert(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.TABLE_NAME, null, values);
        Toast.makeText(context, "id insertat = " + insertId, Toast.LENGTH_SHORT).show();
        close();
        return insertId;
    }

    public int deleteIngredientSubstitutsOfIngredient (String nomRecepta, String nomIngredient) {
        open();
        String whereclause = DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta +
                "' AND " + DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME + " = '" +
                nomIngredient + "'";
        int quants = database.delete(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.TABLE_NAME, whereclause, null);
        close();
        return quants;
    }

    public int deleteIngredientsSubstitutsOfRecepta (String nomRecepta) {
        open();
        String whereclause = DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta + "'";
        int quants = database.delete(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.TABLE_NAME, whereclause, null);
        close();
        return quants;
    }

    public ArrayList<String> getNomsIngredientsSubstitutsOfIngredient(String nomRecepta, String nomIngredient) {
        open();
        String whereclause = DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta +
                "' AND " + DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME + " = '" +
                nomIngredient + "'";
        Cursor cursor = database.query(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.TABLE_NAME, null,
                whereclause, null, null, null, null);
        ArrayList<String> nomsIngredientsSubstituts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                IngredientSubstitut i = cursorToIngredientSubstitut(cursor);
                nomsIngredientsSubstituts.add(i.getNomSubstitut());
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return nomsIngredientsSubstituts;
    }

    private IngredientSubstitut cursorToIngredientSubstitut(Cursor cursor) {
        String nomRecepta = cursor.getString(cursor.getColumnIndex(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME));
        String nomIngredient = cursor.getString(cursor.getColumnIndex(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_SUBSTITUT_NAME));
        String nom = cursor.getString(cursor.getColumnIndex(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_SUBSTITUT_NAME));
        IngredientSubstitut iS = new IngredientSubstitut(nomRecepta, nomIngredient, nom);
        return iS;
    }

    public void updateIngredientsSubstitutsOfRecepta(String oldNomRecepta, String nomRecepta) {
        open();
        String whereclause = DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + oldNomRecepta + "'";
        ContentValues nouRegistre = new ContentValues();
        nouRegistre.put(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME, nomRecepta);
        database.update(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.TABLE_NAME, nouRegistre, whereclause, null);
        close();
    }

    public void updateIngredientReceptaPerIngredientSubstitut (String nomRecepta, String nomIngredient, String nomSubstitut) {
        open();
        //canviam el nom de l'ingredient recepta a nomSubstitut per a que tots els antics substituts es conservin
        String whereclause = DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta +
                "' AND " + DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME + " = '" + nomIngredient + "'";
        ContentValues nouRegistre = new ContentValues();
        nouRegistre.put(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME, nomSubstitut);
        database.update(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.TABLE_NAME, nouRegistre, whereclause, null);
        //posem nomIngredient com a substitut de nomSubstitut
        whereclause = DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta +
                "' AND " + DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_SUBSTITUT_NAME + " = '" + nomSubstitut + "'";
        ContentValues nouRegistre2 = new ContentValues();
        nouRegistre2.put(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_SUBSTITUT_NAME, nomIngredient);
        database.update(DbHelper.IngredientSubstitutContracte.IngredientSubstitutEntry.TABLE_NAME, nouRegistre2, whereclause, null);
        close();
    }
}
