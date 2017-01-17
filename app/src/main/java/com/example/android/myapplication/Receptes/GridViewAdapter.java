package com.example.android.myapplication.Receptes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.Data.Fotos;
import com.example.android.myapplication.Data.ReceptesDAO;
import com.example.android.myapplication.Data.Utils;
import com.example.android.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Oriolcapo on 02/01/2016.
 */
public class GridViewAdapter extends BaseAdapter{

    private ReceptesDAO rDAO;
    private Context mContext;
    private boolean esborrar;
    private LayoutInflater inflater=null;

    private ArrayList<Bitmap> mThumbFotos;
    private ArrayList<String> mThumbNoms;


     private void omplmThumbIds() {
         mThumbFotos.clear();
         for (int i=0; i<mThumbNoms.size(); i++) {
            mThumbFotos.add(Fotos.loadImageFromStorage(mContext, mThumbNoms.get(i)));
        }
    }

    public GridViewAdapter(Context c) {
        mContext = c;
        esborrar = false;
        rDAO = new ReceptesDAO(mContext);
        mThumbNoms = rDAO.getAllReceptesNames();
        mThumbFotos = new ArrayList<>();

        inflater = ( LayoutInflater )c.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        omplmThumbIds();
    }

    @Override
    public int getCount() {
        return mThumbNoms.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        if (esborrar) {
            rowView = inflater.inflate(R.layout.receptes_grid_item_delete, null);
            ImageButton b = (ImageButton)rowView.findViewById(R.id.button_delete_recepta);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDeleteDialog(position).show();
                }
            });
        }
        else {
            if (rDAO.getIfFavourite(mThumbNoms.get(position))) {
                rowView = inflater.inflate(R.layout.receptes_grid_item_favourite, null);
            } else {
                rowView = inflater.inflate(R.layout.receptes_grid_item, null);
            }
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, ReceptaActivity.class);
                    i.putExtra("nomRecepta", mThumbNoms.get(position));//Posición del elemento
                    mContext.startActivity(i);
                }
            });
            rowView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    getDeleteDialog(position).show();
                    return false;
                }
            });
        }
        holder.tv = (TextView)rowView.findViewById(R.id.textView_recepta);
        holder.img = (ImageView)rowView.findViewById(R.id.imageView_recepta);
        if (LlistatReceptesActivity.width < 700 && LlistatReceptesActivity.height < 700) {
            holder.tv.setTextSize(holder.tv.getTextSize()/2);
        }
        holder.tv.setText(Utils.llevaGuions(mThumbNoms.get(position)));
        //--------
        if (mContext.getResources().getConfiguration().orientation == 1) { //vertical
            holder.img.getLayoutParams().width = (LlistatReceptesActivity.height/3) - 25;
            holder.img.getLayoutParams().height = (LlistatReceptesActivity.height/3) - 25;
        }
        else {
            holder.img.getLayoutParams().width = LlistatReceptesActivity.width/4;
            holder.img.getLayoutParams().height = LlistatReceptesActivity.width/4;
        }
        holder.img.requestLayout();
        //--------
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.img.setImageBitmap(mThumbFotos.get(position));
        return rowView;
    }

    private AlertDialog getDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Estas segur que vols eliminar la recepta?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                rDAO.deleteReceptaByName(mThumbNoms.get(position));
                mThumbNoms.remove(position);
                mThumbFotos.remove(position);
                canviaAEsborrarFotos();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public void canviaAEsborrarFotos() {
        if (esborrar) { esborrar = false; }
        else { esborrar = true; }
        refresh();
    }

    public void canviaSource(boolean b) {
        if (b) {
            mThumbNoms = rDAO.getReceptesPreferidesNames();
        }
        else {
            mThumbNoms = rDAO.getAllReceptesNames();
        }
        refresh();
    }

    public void refresh() {
        omplmThumbIds();
        notifyDataSetChanged();
    }
}