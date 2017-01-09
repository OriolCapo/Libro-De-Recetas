package com.example.android.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Oriolcapo on 07/01/2017.
 */

public class MenuDAO {
    private final Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public MenuDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public long createMenu (String nomMenu, String info) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_MENU_NAME, nomMenu);
        values.put(DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_INFO, info);
        long insertId = database.insert(DbHelper.MenuContracte.MenuEntry.TABLE_NAME, null, values);
        close();
        return insertId;
    }

    public long createReceptaMenu (String nomMenu, String nomRecepta) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_MENU_NAME, nomMenu);
        values.put(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_RECEPTA_NAME, nomRecepta);
        long insertId = database.insert(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.TABLE_NAME, null, values);
        close();
        return insertId;
    }

    public ArrayList<String> getAllMenusNames () {
        open();
        ArrayList<String> menusNames = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.MenuContracte.MenuEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String nomMenu = cursor.getString(cursor.getColumnIndex(DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_MENU_NAME));
                if(!menusNames.contains(nomMenu)) menusNames.add(nomMenu);
            } while (cursor.moveToNext());
        }
        close();
        cursor.close();
        return menusNames;
    }

    public ArrayList<String> getNomsReceptesOfMenu (String nomMenu) {
        open();
        ArrayList<String> receptes = new ArrayList<>();
        String whereclause = DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "'";
        Cursor cursor = database.query(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.TABLE_NAME, null,whereclause, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String nomRecepta = cursor.getString(cursor.getColumnIndex(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_RECEPTA_NAME));
                if(!receptes.contains(nomRecepta)) receptes.add(nomRecepta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return receptes;
    }

    public String getInfo(String nomMenu) {
        open();
        String whereclause = DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "'";
        Cursor cursor = database.query(DbHelper.MenuContracte.MenuEntry.TABLE_NAME, null, whereclause, null, null, null, null);
        if (cursor.moveToFirst()){
            String info = cursor.getString(cursor.getColumnIndex(DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_INFO)).toString();
            if(info != null) return  info;
        }
        close();
        cursor.close();
        return null;
    }

    public void deleteMenu(String nomMenu) {
        open();
        String whereclause1 = DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "'";
        String whereclause2 = DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "'";
        int q1 = database.delete(DbHelper.MenuContracte.MenuEntry.TABLE_NAME, whereclause1, null);
        int q2 = database.delete(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.TABLE_NAME, whereclause2, null);
        if (q1 != -1 && q2 != -1) Toast.makeText(context,"Menu eliminat correctament",Toast.LENGTH_LONG).show();
        else Toast.makeText(context,"Alguna cosa ha sortit malament",Toast.LENGTH_LONG).show();
        close();
    }

    public void deleteReceptaMenu(String nomMenu, String nomRecepta) {
        open();
        String whereclause = DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "' AND " +
                DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta + "'";
        int quants = database.delete(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.TABLE_NAME, whereclause, null);
        close();
    }

    public boolean existReceptaMenu(String nomMenu, String nomRecepta) {
        open();
        boolean ret;
        String where = DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu +
                "' AND " + DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_RECEPTA_NAME + " = '" + nomRecepta + "'";
        System.out.println("ANTES DE LA QUERY");
        Cursor cursor = database.query(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.TABLE_NAME, null, where, null, null, null, null);
        System.out.println("DESPUES DE LA QUERY");
        if (cursor.moveToFirst()) ret = true;
        else ret = false;
        close();
        return ret;
    }
}
