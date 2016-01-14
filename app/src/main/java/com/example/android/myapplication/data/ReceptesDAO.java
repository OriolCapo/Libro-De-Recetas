package com.example.android.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Oriolcapo on 12/01/2016.
 */
public class ReceptesDAO {
    private final Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public ReceptesDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createRecepta (String nom, String descripcio, String suggeriments) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_NAME, nom);
        values.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_DESCRIPTION, descripcio);
        values.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_SUGGERIMENTS, suggeriments);
        long insertId = database.insert(DbHelper.ReceptaContracte.ReceptaEntry.TABLE_NAME, null, values);
        close();
        return insertId;
    }

    public int deleteAllReceptes () {
        open();
        int quants = database.delete(DbHelper.ReceptaContracte.ReceptaEntry.TABLE_NAME, "1", null);
        close();
        return quants;
    }

    public int deleteReceptaByName (String name) {
        open();
        String whereclause = DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + name + "'";
        int quants = database.delete(DbHelper.ReceptaContracte.ReceptaEntry.TABLE_NAME, whereclause, null);
        close();
        return quants;
    }

    public int updateRecepta (String oldName, String name, String descripcio, String suggeriments) {
        open();
        String whereclause = DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + oldName + "'";
        ContentValues nouRegistre = new ContentValues();
        nouRegistre.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_NAME, name);
        nouRegistre.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_DESCRIPTION, descripcio);
        nouRegistre.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_SUGGERIMENTS, suggeriments);
        int quants = database.update(DbHelper.ReceptaContracte.ReceptaEntry.TABLE_NAME, nouRegistre, whereclause, null);
        close();
        IngredientsReceptesDAO irDAO = new IngredientsReceptesDAO(context);
        irDAO.updateNameOfRecepta(oldName, name);
        IngredientsSubstitutsDAO isDAO = new IngredientsSubstitutsDAO(context);
        isDAO.updateIngredientsSubstitutsOfRecepta(oldName, name);
        return quants;
    }

    public Recepta getRecepta(long id) {
        open();
        Cursor cursor = database.query(DbHelper.ReceptaContracte.ReceptaEntry.TABLE_NAME, null,
                DbHelper.ReceptaContracte.ReceptaEntry._ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        Recepta recepta = cursorToRecepta(cursor);
        cursor.close();
        close();
        return recepta;
    }

    public Recepta getReceptaByNom(String nomRecepta) {
        open();
        Cursor cursor = database.query(DbHelper.ReceptaContracte.ReceptaEntry.TABLE_NAME, null,
                DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta + "'", null, null, null, null);
        Recepta recepta = new Recepta();
        if (cursor.moveToFirst()) recepta = cursorToRecepta(cursor);
        cursor.close();
        close();
        return recepta;
    }

    private Recepta cursorToRecepta(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(DbHelper.ReceptaContracte.ReceptaEntry._ID));
        String nom = cursor.getString(cursor.getColumnIndex(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_NAME));
        String descr = cursor.getString(cursor.getColumnIndex(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_DESCRIPTION));
        String sugg = cursor.getString(cursor.getColumnIndex(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_SUGGERIMENTS));
        Recepta recepta = new Recepta(id, nom, descr, sugg);
        return recepta;
    }

    public ArrayList<Recepta> getAllReceptes() {
        open();
        ArrayList<Recepta> allRec = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.ReceptaContracte.ReceptaEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Recepta recepta = cursorToRecepta(cursor);
                allRec.add(recepta);
            } while (cursor.moveToNext());
        }
        close();
        return allRec;
    }
}
