package com.example.android.myapplication.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Oriolcapo on 12/01/2016.
 */
public class Fotos {

    public static String saveToInternalSorage(Context context, Bitmap bitmapImage, String nom) throws IOException {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("Receptes", Context.MODE_PRIVATE);
        nom += ".jpg";
        File mypath=new File(directory,nom);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(Context context, String nom)
    {
        ContextWrapper cw = new ContextWrapper(context);
        String path = cw.getDir("Receptes", Context.MODE_PRIVATE).getAbsolutePath();
        try {
            File f=new File(path, nom + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
