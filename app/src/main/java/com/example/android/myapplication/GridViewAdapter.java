package com.example.android.myapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.data.Fotos;
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
public class GridViewAdapter extends BaseAdapter{

    private ReceptesDAO rDAO;
    private ArrayList<String> receptes;
    private Context mContext;
    private LayoutInflater inflater=null;

    private Bitmap[] mThumbIds;
    private String[] mThumbNoms;


     private void omplmThumbIds() {
        mThumbIds = new Bitmap[receptes.size()];
        mThumbNoms = new String[receptes.size()];
        for (int i=0; i<receptes.size(); i++) {
            mThumbNoms[i] = receptes.get(i);
            mThumbIds[i] = Fotos.loadImageFromStorage(mContext, receptes.get(i));
        }
    }

    public GridViewAdapter(Context c) {
        mContext = c;
        rDAO = new ReceptesDAO(mContext);
        this.receptes = rDAO.getAllReceptesNames();
        inflater = ( LayoutInflater )c.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        omplmThumbIds();
    }

    @Override
    public int getCount() {
        return receptes.size();
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
        Holder holder=new Holder();
        View rowView;
        if (rDAO.getIfFavourite(mThumbNoms[position])) { rowView = inflater.inflate(R.layout.receptes_grid_item_favourite, null); }
        else { rowView = inflater.inflate(R.layout.receptes_grid_item, null); }
        holder.tv = (TextView)rowView.findViewById(R.id.textView_recepta);
        holder.img = (ImageView)rowView.findViewById(R.id.imageView_recepta);
        holder.tv.setText(Utils.llevaGuions(mThumbNoms[position]));
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.img.setImageBitmap(mThumbIds[position]);
        return rowView;
    }

    public void canviaSource(boolean b) {
        if (b) {
            receptes = rDAO.getReceptesPreferidesNames();
        }
        else {
            receptes = rDAO.getAllReceptesNames();
        }
        refresh();
    }

    public void refresh() {
        omplmThumbIds();
        notifyDataSetChanged();
    }
}