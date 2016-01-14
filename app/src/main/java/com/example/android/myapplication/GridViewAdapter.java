package com.example.android.myapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.data.Recepta;
import com.example.android.myapplication.data.ReceptesDAO;
import com.example.android.myapplication.data.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Oriolcapo on 02/01/2016.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private static LayoutInflater inflater=null;

    private Bitmap[] mThumbIds;
    private String[] mThumbNoms;

    private Bitmap loadImageFromStorage(String nom)
    {
        ContextWrapper cw = new ContextWrapper(mContext);
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

     private void omplmThumbIds() {
        ReceptesDAO dao = new ReceptesDAO(mContext);
        ArrayList<Recepta> list = dao.getAllReceptes();
        mThumbIds = new Bitmap[list.size()];
        mThumbNoms = new String[list.size()];
        for (int i=0; i<list.size(); i++) {
            Recepta rec = list.get(i);
            mThumbNoms[i] = rec.getNom();
            mThumbIds[i] = loadImageFromStorage(rec.getNom());
        }
    }


    public GridViewAdapter(Context c) {
        mContext = c;
        inflater = ( LayoutInflater )c.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        omplmThumbIds();
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Bitmap getThumbId(int position){
        return mThumbIds[position];
    }

    public String getThumbName(int position){
        return mThumbNoms[position];
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView a retornar
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.receptes_grid_item, null);
        holder.tv = (TextView)rowView.findViewById(R.id.textView_recepta);
        holder.img = (ImageView)rowView.findViewById(R.id.imageView_recepta);
        holder.tv.setText(Utils.llevaGuions(mThumbNoms[position]));
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.img.setImageBitmap(mThumbIds[position]);
        return rowView;
    }

    public void refresh() {
        omplmThumbIds();
        notifyDataSetChanged();
    }
}