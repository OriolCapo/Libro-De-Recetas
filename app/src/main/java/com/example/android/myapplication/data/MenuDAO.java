package com.example.android.myapplication.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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


    //----------------------------------------------------------------------------------------------------------------
    //  Funcions de Menu
    //----------------------------------------------------------------------------------------------------------------

    public long createMenu (String nomMenu, String info) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_MENU_NAME, nomMenu);
        values.put(DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_INFO, info);
        long insertId = database.insert(DbHelper.MenuContracte.MenuEntry.TABLE_NAME, null, values);
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
        String whereclause2 = DbHelper.DiaMenuContracte.DiaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "'";
        String whereclause3 = DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "'";
        int q1 = database.delete(DbHelper.MenuContracte.MenuEntry.TABLE_NAME, whereclause1, null);
        int q2 = database.delete(DbHelper.DiaMenuContracte.DiaMenuEntry.TABLE_NAME, whereclause2, null);
        int q3 = database.delete(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.TABLE_NAME, whereclause3, null);
        if (q1 != -1 && q2 != -1 && q3 != -1) Toast.makeText(context,"Menu eliminat correctament",Toast.LENGTH_LONG).show();
        else Toast.makeText(context,"Alguna cosa ha sortit malament",Toast.LENGTH_LONG).show();
        close();
    }

    public int updateMenu (String oldName, String newName, String newInfo) {
        open();
        String whereclause = DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_MENU_NAME + " = '" + oldName + "'";
        ContentValues nouRegistre = new ContentValues();
        nouRegistre.put(DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_MENU_NAME, newName);
        nouRegistre.put(DbHelper.MenuContracte.MenuEntry.COLUMN_NAME_INFO, newInfo);
        int quants = database.update(DbHelper.MenuContracte.MenuEntry.TABLE_NAME, nouRegistre, whereclause, null);

        nouRegistre = new ContentValues();
        nouRegistre.put(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.COLUMN_NAME_MENU_NAME, newName);
        database.update(DbHelper.ReceptaMenuContracte.ReceptaMenuEntry.TABLE_NAME, nouRegistre, whereclause, null);

        nouRegistre = new ContentValues();
        nouRegistre.put(DbHelper.DiaMenuContracte.DiaMenuEntry.COLUMN_NAME_MENU_NAME, newName);
        database.update(DbHelper.DiaMenuContracte.DiaMenuEntry.TABLE_NAME, nouRegistre, whereclause, null);

        return quants;
    }


    //----------------------------------------------------------------------------------------------------------------
    //  Funcions de Dia-Menu
    //----------------------------------------------------------------------------------------------------------------

    public long createDiaMenu (String nomMenu, String nomDia) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.DiaMenuContracte.DiaMenuEntry.COLUMN_NAME_MENU_NAME, nomMenu);
        values.put(DbHelper.DiaMenuContracte.DiaMenuEntry.COLUMN_NAME_DIA_MENU_NAME, nomDia);
        long insertId = database.insert(DbHelper.DiaMenuContracte.DiaMenuEntry.TABLE_NAME, null, values);
        close();
        return insertId;
    }

    public ArrayList<String> getDiesOfMenu (String nomMenu) {
        open();
        ArrayList<String> dies = new ArrayList<>();
        String whereclause = DbHelper.DiaMenuContracte.DiaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "'";
        Cursor cursor = database.query(DbHelper.DiaMenuContracte.DiaMenuEntry.TABLE_NAME, null,whereclause, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String nomRecepta = cursor.getString(cursor.getColumnIndex(DbHelper.DiaMenuContracte.DiaMenuEntry.COLUMN_NAME_DIA_MENU_NAME));
                if(!dies.contains(nomRecepta)) dies.add(nomRecepta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return dies;
    }

    public void deleteDiaMenu(String nomMenu, String nomDia) {
        open();
        String whereclause = DbHelper.DiaMenuContracte.DiaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "' AND " +
                DbHelper.DiaMenuContracte.DiaMenuEntry.COLUMN_NAME_DIA_MENU_NAME + " = '" + nomDia + "'";
        String whereclause2 = DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "' AND " +
                DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_DIA_NAME + " = '" + nomDia + "'";
        database.delete(DbHelper.DiaMenuContracte.DiaMenuEntry.TABLE_NAME, whereclause, null);
        database.delete(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.TABLE_NAME, whereclause2, null);
        close();
    }

    //----------------------------------------------------------------------------------------------------------------
    //  Funcions de Recepta-Dia-Menu
    //----------------------------------------------------------------------------------------------------------------

    public long createReceptaDiaMenu (String nomMenu, String nomDia, String nomMoment, String nomRecepta) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_MENU_NAME,nomMenu);
        values.put(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_DIA_NAME,nomDia);
        values.put(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_MOMENT,nomMoment);
        values.put(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_RECEPTA_DIA_NAME,nomRecepta);
        long insertId = database.insert(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.TABLE_NAME, null, values);
        close();
        return insertId;
    }

    public void deleteReceptaDiaMenu(String nomMenu, String nomDia, String nomRecepta) {
        open();
        String whereclause = DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "' AND " +
                DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_DIA_NAME + " = '" + nomDia + "' AND " +
                DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_RECEPTA_DIA_NAME + " = '" + nomRecepta + "'";
        database.delete(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.TABLE_NAME, whereclause, null);
        close();
    }

    public ArrayList<String> getNomsReceptesDiaMenu(String nomMenu, String nomDia) {
        open();
        ArrayList<String> receptes = new ArrayList<>();
        String whereclause = DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "' AND " +
                DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_DIA_NAME + " = '" + nomDia + "'";
        Cursor cursor = database.query(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.TABLE_NAME, null,whereclause, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String nomRecepta = cursor.getString(cursor.getColumnIndex(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_RECEPTA_DIA_NAME));
                receptes.add(nomRecepta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return receptes;
    }

    public ArrayList<String> getNomsReceptesMoment (String nomMenu, String nomDia, String nomMoment) {
        open();
        ArrayList<String> receptes = new ArrayList<>();
        String whereclause = DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_MENU_NAME + " = '" + nomMenu + "' AND " +
                DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_DIA_NAME + " = '" + nomDia + "' AND " +
                DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_MOMENT + " = '" + nomMoment + "'";
        Cursor cursor = database.query(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.TABLE_NAME, null,whereclause, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String nomRecepta = cursor.getString(cursor.getColumnIndex(DbHelper.ReceptaDiaMenuContracte.ReceptaDiaMenuEntry.COLUMN_NAME_RECEPTA_DIA_NAME));
                receptes.add(nomRecepta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return receptes;
    }

}
