package com.example.android.myapplication.Menus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Data.MenuDAO;
import com.example.android.myapplication.R;
import com.example.android.myapplication.Receptes.ReceptaActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oriolcapo on 10/01/2017.
 */
public class ReceptesDiaAdapter extends RecyclerView.Adapter<ReceptesDiaAdapter.BookViewHolder> {

    private Context context;
    private String nomMenu;
    ArrayList<ReceptesDia> items;
    private View v;

    public class BookViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_nomDia;
        public ListView lv_receptes;
        public ImageButton b_modifica_recptes_dia;

        public BookViewHolder(View itemView) {
            super(itemView);
            tv_nomDia = (TextView) itemView.findViewById(R.id.card_nomDia);
            lv_receptes = (ListView) itemView.findViewById(R.id.card_listview_receptes_dia);
            b_modifica_recptes_dia = (ImageButton) itemView.findViewById(R.id.button_modifica_receptes_dia);
        }
    }

    public ReceptesDiaAdapter(Context context, String nomMenu){
        this.context = context;
        this.nomMenu = nomMenu;
        MenuDAO menuDAO = new MenuDAO(context);
        ArrayList<String> dies = menuDAO.getDiesOfMenu(nomMenu);
        items = new ArrayList<>();
        for (int i=0; i<dies.size(); ++i){
            ReceptesDia receptesDia = new ReceptesDia();
            receptesDia.setNomDia(dies.get(i));
            receptesDia.setReceptes(menuDAO.getNomsReceptesDiaMenu(nomMenu,dies.get(i)));
            items.add(receptesDia);
        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_recycler, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, final int position) {

        holder.tv_nomDia.setText(items.get(position).getNomDia());
        List<String> receptes = items.get(position).getReceptes();
        ListAdapter adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, receptes);
        for (int i=0; i<receptes.size(); ++i) receptes.set(i,receptes.get(i));
        holder.lv_receptes.setAdapter(adapter);

        holder.b_modifica_recptes_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AfegirReceptesMenuActivity.class);
                intent.putExtra("nomMenu",nomMenu);
                intent.putExtra("nomDia",items.get(position).getNomDia());
                context.startActivity(intent);
            }
        });

        int totalH = 0;
        View view = null;
        int dWidth = View.MeasureSpec.makeMeasureSpec(holder.lv_receptes.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i=0; i<adapter.getCount(); ++i){
            view = adapter.getView(i,view,holder.lv_receptes);
            if(i==0) view.setLayoutParams(new ViewGroup.LayoutParams(dWidth,RecyclerView.LayoutParams.WRAP_CONTENT));
            view.measure(dWidth,View.MeasureSpec.UNSPECIFIED);
            totalH += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = holder.lv_receptes.getLayoutParams();
        params.height = totalH + (holder.lv_receptes.getDividerHeight() * (adapter.getCount()-1));
        holder.lv_receptes.setLayoutParams(params);

        holder.lv_receptes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nomRecepta = holder.lv_receptes.getItemAtPosition(position).toString();
                Intent intent = new Intent(context, ReceptaActivity.class);
                intent.putExtra("nomRecepta",nomRecepta);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
