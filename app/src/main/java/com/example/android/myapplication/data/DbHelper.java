package com.example.android.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Oriolcapo on 12/01/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FeedReader.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES_RECEPTA =
            "CREATE TABLE IF NOT EXISTS " + ReceptaContracte.ReceptaEntry.TABLE_NAME + " (" +
                    ReceptaContracte.ReceptaEntry._ID + " INTEGER PRIMARY KEY," +
                    ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_ID + " TEXT,"  +
                    ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " TEXT," +
                    ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_DESCRIPTION + " TEXT," +
                    ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_SUGGERIMENTS + " TEXT" +
                    ")";

    private static final String SQL_CREATE_ENTRIES_INGREDIENT =
            "CREATE TABLE IF NOT EXISTS " + IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME + " (" +
                    IngredientReceptaContracte.IngredientReceptaEntry._ID + " INTEGER PRIMARY KEY," +
                    IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_INGREDIENT_RECEPTA_ID + " TEXT,"  +
                    IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME + " TEXT," +
                    IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME + " TEXT" +
                    ")";

    private static final String SQL_CREATE_ENTRIES_INGREDIENT_SUBSTITUT =
            "CREATE TABLE IF NOT EXISTS " + IngredientSubstitutContracte.IngredientSubstitutEntry.TABLE_NAME + " (" +
                    IngredientSubstitutContracte.IngredientSubstitutEntry._ID + " INTEGER PRIMARY KEY," +
                    IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_SUBSTITUT_ID + " TEXT,"  +
                    IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_RECEPTA_NAME + " TEXT," +
                    IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME + " TEXT," +
                    IngredientSubstitutContracte.IngredientSubstitutEntry.COLUMN_NAME_INGREDIENT_SUBSTITUT_NAME + " TEXT" +
                    ")";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_RECEPTA);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_INGREDIENT);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_INGREDIENT_SUBSTITUT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_RECEPTA);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_INGREDIENT);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_INGREDIENT_SUBSTITUT);
    }

    public final class ReceptaContracte {
        public ReceptaContracte() {}

        public abstract class ReceptaEntry implements BaseColumns {
            public static final String TABLE_NAME = "receptes";
            public static final String COLUMN_NAME_RECEPTA_ID = "id";
            public static final String COLUMN_NAME_RECEPTA_NAME = "nom";
            public static final String COLUMN_NAME_RECEPTA_DESCRIPTION = "descripcio";
            public static final String COLUMN_NAME_RECEPTA_SUGGERIMENTS = "suggeriments";
        }
    }

    public final class IngredientReceptaContracte {
        public IngredientReceptaContracte() {}

        public abstract class IngredientReceptaEntry implements BaseColumns {
            public static final String TABLE_NAME = "ingredientsRecepta";
            public static final String COLUMN_NAME_INGREDIENT_RECEPTA_ID = "id";
            public static final String COLUMN_NAME_RECEPTA_NAME = "nomRecepta";
            public static final String COLUMN_NAME_INGREDIENT_RECEPTA_NAME = "nom";
        }
    }

    public final class IngredientSubstitutContracte {
        public IngredientSubstitutContracte() {}

        public abstract class IngredientSubstitutEntry implements BaseColumns {
            public static final String TABLE_NAME = "ingredientsSubstituts";
            public static final String COLUMN_NAME_INGREDIENT_SUBSTITUT_ID = "id";
            public static final String COLUMN_NAME_RECEPTA_NAME = "nomRecepta";
            public static final String COLUMN_NAME_INGREDIENT_RECEPTA_NAME = "nomIngredient";
            public static final String COLUMN_NAME_INGREDIENT_SUBSTITUT_NAME = "nom";
        }
    }
}
