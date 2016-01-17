package com.example.android.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.IOException;

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
                    ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_SUGGERIMENTS + " TEXT," +
                    ReceptaContracte.ReceptaEntry.COLUMN_NAME_FAVOURITE + " TEXT" +
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
    private final Context context;

    //----- inserts receptes
    private String[] nomsReceptes = {
        "pulpo_gallega",
        "espaguetti_carbonara",
        "solomillo_a_la_pimienta",
        "paella"
    };

    private String[] descripcions = {

        "1-Primero debemos ablandar el pulpo: lo congelamos durante dos días, y un día antes de cocinarlo lo descongelamos en la nevera puesto sobre una fuente para escurrir los jugos.\n" +
                "2-El segundo paso es poner una cazuela con agua y una cebolla y cuando rompa a hervir añadimos el pulpo. Lo cogemos por la cabeza y lo metemos y sacamos de la cazuela tres veces antes de soltarlo definitivamente, lo asustamos.\n" +
                "3-Lo dejamos al fuego unos 50 minutos, revisamos que el pulpo está bien cocido, probamos de pincharlo y notarlo blando.\n" +
                "4-Una vez cocido lo retiramos del fuego, tapamos la cazuela y lo dejamos reposar 15 minutos.\n" +
                "5-En la misma agua de cocer el pulpo cocemos las patatas, previamente peladas y cortadas en rodajas. Mientras se cuecen, cortamos el pulpo en trozos no demasiado gruesos.\n" +
                "6-Cuando estén hervidas las patatas, las sacamos y las colocamos en el plato. Encima de ellas, ponemos los trozos de pulpo.\n" +
                "7-Finalmente, aliñamos el pulpo con aceite de oliva y el pimentón dulce, el pimentón picante y abundante sal gruesa.",

        "Comenzaremos preparando la pasta antes de nada, para lo que vamos a poner a calentar una olla con abundante agua, y cuando comience a hervir agregamos una pizca de sal y un chorrito de aceite de oliva y los espaguetis, y los vamos a dejar cocinar durante unos 12-13 minutos, bajando un poco la intensidad del fuego para que los borbotones de agua no se salgan del recipiente al hervir. Pasado ese tiempo los espaguetis deben estar en su punto, así que los vertemos en un escurridor para pasta y dejamos se escurra todo el agua sobrante. \n" +
                "Mientras se cuecen los espaguetis podemos aprovechar para preparar el resto de ingredientes que vamos a necesitar en la receta. Pelamos los dientes de ajo y los cortamos en rodajas que sean más bien finas. Cortaremos también la panceta en tiras, a menos que ya las hayamos comprado así, aunque a nosotros nos gusta más comprarlas en lonchas y luego cortarlas, pero compra la que prefieras. Y respecto a los huevos, es mejor que no estén fríos de la nevera, así que es interesante dejarlos fuera de la misma cuando empecemos a preparar esta receta. \n" +
                "A continuación ponemos a calentar una sartén de tamaño adecuado con un poco de aceite de oliva virgen extra, para dorar en ella los ajos laminados, mejor a fuego medio para evitar que se quemen. Una vez los ajos estén bien salteados por todos lados, los sacamos de la sartén y dejamos reservado para después. En esa misma sartén vamos a saltear la panceta que habíamos cortado antes, hasta que quede levemente frita por todos lados, pero sin que llegue a quedar frita del todo y quede crujiente. Cuando esté lista, apagamos el fuego y apartamos la panceta junto al ajo que habíamos salteado antes. \n" +
                "Lo siguiente que haremos será batir los huevos que teníamos fuera de la nevera. Podemos hacerlo batiendo los huevos enteros o bien usando solo las yemas de los mismos, que es como se hace en algunas zonas de Italia de forma tradicional. Haz la opción que más te guste, pero ten en cuenta que esta segunda opción hace que el sabor a huevo sea más intenso a la hora de servir el plato de espaguetis a la carbonara. Una vez tengamos los huevos batidos, o sus yemas, agregamos el queso parmesano rallado y sal y pimienta negra molida al gusto, y volvemos a batir para mezclar bien. \n" +
                "Y ahora vamos a acabar de preparar el plato, para lo que echamos en la sartén que habíamos usado antes los espaguetis bien escurridos, junto a la panceta y los ajos, y lo salteamos todo a fuego medio durante un par de minutos, para que la pasta tome algo de sabor y los ingredientes se calienten bien. Servimos a continuación en los platos individuales, y por encima agregamos parte de la mezcla de huevos batidos y queso que habíamos preparado, para que con el calor de la pasta el huevo no se cuaje del todo y quede cremoso. Y a disfrutar de esta tradicional receta de espaguetis a la carbonara, que esperamos que os guste.",

        "1-Machacar los granos de pimienta blanca y negra. Distruibuirlos sobre cada solomillo y añadir sal gorda.\n" +
                "2-Calentar el aceite en un sartén y dorar los solomillos durante 3-7 minutos según el gusto (poco hecho, al punto o muy hecho)\n" +
                "3-Retirar los solomillos y reservarlos.\n" +
                "4-Verter en el sarten el caldo y el cognac y reducir al fuego.\n" +
                "5-Añadir la nata y los granos de pimienta verde. Dejar reducir la salsa a la mitad.\n" +
                "6-Colocar los solomillos en la sartén para que cojan sabor. Servir.\n",

        "1-Primero de todo necesitamos una paella grande, en la que vamos a calentar el aceite. Mientras, pelaremos y cortaremos la cebolla para luego proceder a picarla. También podéis utilizar cebolla congelada que ya viene picada y de ese modo ahorraros el tener que haceros vosotros.\n" +
                "2-Con el aceite ya caliente, echamos la cebolla picada, junto con los 3 dientes de ajo, y esperamos a que se doren. Eso le llevará unos minutos. Ya con la cebolla y los dientes de ajo dorados, procederemos a echar los trozos del calamar, 600 gr. más o menos, para que con el aceite se vayan haciendo poco a poco y aproveche el sabor de la cebolla también.\n" +
                "3-Cuando veamos que el calamar está más o menos hecho, añadimos 5 cucharadas de tomate triturado, que como pasa con la cebolla podemos prepararlo nosotros mismos o comprarlo ya triturado. Con una cuchara de palo lo removemos todo bien para que el tomate se mezcle bien en el aceite y así se adhiera al calamar. Pasados unos cinco minutos más o menos, añadimos el pimentón y lo volvemos a mezclar todo bien.\n" +
                "4-Con el pimentón ya mezclado con lo demás, echamos 400 gr. de arroz, volvemos a removerlo todo bien y después el azafrán. Ahora lo rehogamos todo durante un par de minutos y es cuando debemos echar 1 litro de caldo de pescado.\n" +
                "5-Lo dejamos un rato a fuego medio para que el caldo se vaya reduciendo. Mientras podemos echar el azafrán para darle color al arroz, un poco de sal y, si nos gusta con más sabor a pescado, podemos echarle una pastilla. Pero eso ya es a gusto de cada uno.\n" +
                "6-Al ver que el arroz empieza a asomar, es la señal para echar las cigalas y las gambas y dejamos que todo se cocine durante unos 20 minutos a fuego medio con la tapa puesta para que se consuma mejor el caldo, removiéndolo de vez en cuando para que no se pegue. Si se nos queda sin caldo rápidamente, podemos echar un poco más de caldo de pescado.\n" +
                "7-Pasado los 20 minutos solo tenemos que dejar el arroz reposar un par de minutos y está listo para comer."
    };

    private void InsertsReceptes(SQLiteDatabase db) {
        for (int i=0; i<nomsReceptes.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_NAME, nomsReceptes[i]);
            values.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_DESCRIPTION, descripcions[i]);
            values.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_RECEPTA_SUGGERIMENTS, " ");
            values.put(DbHelper.ReceptaContracte.ReceptaEntry.COLUMN_NAME_FAVOURITE, "no");
            db.insert(DbHelper.ReceptaContracte.ReceptaEntry.TABLE_NAME, null, values);
        }
    }

    public void inputs() throws IOException {
        for (int i = 0; i < nomsReceptes.length; i++) {
            String rec = nomsReceptes[i];
            int id = context.getResources().getIdentifier(rec, "drawable",
                    context.getPackageName());
            Bitmap img = BitmapFactory.decodeResource(context.getResources(), id);
            Fotos.saveToInternalSorage(context, img, rec);
        }
    }

    //----- inserts ingredientreceptes

    private Integer[] nombreIngredients = {
            7, 8, 9, 13
    };

    private String[] ingredientsReceptes = {
            "1 pulpo de 2 kilos", "medio kilo de patatas", "1 cebolla", "pimentón dulce", "pimentón picante", "aceite de oliva", "sal gruesa",
            "250 gramos de espaguetis", "150 gramos de panceta fresca", "150 gramos de queso parmesano", "4 huevos grandes", "4 dientes de ajo", "Aceite de oliva virgen extra", "Sal", "Pimienta negra molida",
            "4 Unidades de Solomillos de Ternera","Medio vaso de Caldo", "2 Cuchara sopera de Pimienta verde en grano", "1 Cuchara sopera de Pimienta blanca en grano", "1 Cuchara sopera de Pimienta negra en grano", "6 Cuchara sopera de Nata líquida", "1 Vaso pequeño de Cognac", "1 Cuchara sopera de Aceite", "1 Pizca de Sal gorda",
            "Aceite de oliva", "3 dientes de ajo", "400 gr. de arroz", "600 gr. de calamares", "Azafrán", "1 litro de caldo de pescado", "4 cigalas", "Colorante", "8 gambas", "Pimentón", "5 cucharadas de tomate triturado", "Cebolla", "Sal"
    };

    private void InsertsIngredientsReceptes(SQLiteDatabase db) {
        int k = 0;
        for (int i=0; i<nomsReceptes.length; i++) {
            for (int j=0; j<nombreIngredients[i]; ++j) {
                ContentValues values = new ContentValues();
                values.put(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_INGREDIENT_RECEPTA_NAME, ingredientsReceptes[k+j]);
                values.put(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.COLUMN_NAME_RECEPTA_NAME, nomsReceptes[i]);
                db.insert(DbHelper.IngredientReceptaContracte.IngredientReceptaEntry.TABLE_NAME, null, values);
            }
            k+=nombreIngredients[i];
        }
    }

    //-----

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_RECEPTA);
        InsertsReceptes(sqLiteDatabase);
        try {
            inputs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_INGREDIENT);
        InsertsIngredientsReceptes(sqLiteDatabase);
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
            public static final String COLUMN_NAME_FAVOURITE = "preferida";
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
