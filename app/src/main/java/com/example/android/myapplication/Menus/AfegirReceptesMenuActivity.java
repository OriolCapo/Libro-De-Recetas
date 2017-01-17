package com.example.android.myapplication.Menus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Data.MenuDAO;
import com.example.android.myapplication.Data.ReceptesDAO;
import com.example.android.myapplication.R;

import java.util.ArrayList;

public class AfegirReceptesMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private MenuDAO menuDAO;
    private ReceptesDAO receptesDAO;
    private String nomMenu, nomDia;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_receptes_menu);
        context = this;
        receptesDAO = new ReceptesDAO(this);
        menuDAO = new MenuDAO(this);

        Intent intent = getIntent();
        nomMenu = intent.getStringExtra("nomMenu");
        nomDia = intent.getStringExtra("nomDia");

        ArrayList<String> receptesBerenar = menuDAO.getNomsReceptesMoment(nomMenu, nomDia, "berenar");
        LinearLayout layout_berenar = (LinearLayout) AfegirReceptesMenuActivity.this.findViewById(R.id.layout_berenar);
        ompl_info(receptesBerenar,layout_berenar);

        ArrayList<String> receptesDinar = menuDAO.getNomsReceptesMoment(nomMenu, nomDia, "dinar");
        LinearLayout layout_dinar = (LinearLayout) AfegirReceptesMenuActivity.this.findViewById(R.id.layout_dinar);
        ompl_info(receptesDinar,layout_dinar);

        ArrayList<String> receptesBereneta = menuDAO.getNomsReceptesMoment(nomMenu, nomDia, "bereneta");
        LinearLayout layout_bereneta = (LinearLayout) AfegirReceptesMenuActivity.this.findViewById(R.id.layout_bereneta);
        ompl_info(receptesBereneta,layout_bereneta);

        ArrayList<String> receptesSopar = menuDAO.getNomsReceptesMoment(nomMenu, nomDia, "sopar");
        LinearLayout layout_sopar = (LinearLayout) AfegirReceptesMenuActivity.this.findViewById(R.id.layout_sopar);
        ompl_info(receptesSopar,layout_sopar);
    }

    private void ompl_info(ArrayList<String> nomsReceptes, final LinearLayout layout) {
        for (int i=0; i<nomsReceptes.size(); ++i) {
            final String nomR = nomsReceptes.get(i);
            LayoutInflater inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.nova_recepta_ingredient_item, null);
            TextView tv = (TextView)rowView.findViewById(R.id.TextView_Ingredient);
            tv.setText("- " + nomR);
            tv.setTextSize(20);
            tv.setTextColor(Color.BLACK);

            ImageButton ib = (ImageButton)rowView.findViewById(R.id.Button_Eliminar_Ingredient);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.removeView(rowView);
                    menuDAO.deleteReceptaDiaMenu(nomMenu,nomDia,nomR);
                }
            });
            int childCount = layout.getChildCount();
            layout.addView(rowView,childCount);
        }
    }

    private AlertDialog getAddReceptaDialog(final String nomMenjar) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona la recepta que vols afegir");

        final ArrayList<String> receptes = receptesDAO.getAllReceptesNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, receptes);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String nomR = receptes.get(which);
                final LinearLayout ll;
                if(nomMenjar.equals("berenar")) ll = (LinearLayout) AfegirReceptesMenuActivity.this.findViewById(R.id.layout_berenar);
                else if(nomMenjar.equals("dinar")) ll = (LinearLayout) AfegirReceptesMenuActivity.this.findViewById(R.id.layout_dinar);
                else if(nomMenjar.equals("bereneta")) ll = (LinearLayout) AfegirReceptesMenuActivity.this.findViewById(R.id.layout_bereneta);
                else ll = (LinearLayout) AfegirReceptesMenuActivity.this.findViewById(R.id.layout_sopar);

                LayoutInflater inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.nova_recepta_ingredient_item, null);
                TextView tv = (TextView)rowView.findViewById(R.id.TextView_Ingredient);
                tv.setText("- " + nomR);
                tv.setTextSize(20);
                tv.setTextColor(Color.BLACK);

                ImageButton ib = (ImageButton)rowView.findViewById(R.id.Button_Eliminar_Ingredient);
                ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll.removeView(rowView);
                        menuDAO.deleteReceptaDiaMenu(nomMenu,nomDia,nomR);
                    }
                });
                int childCount = ll.getChildCount();
                ll.addView(rowView,childCount);
                menuDAO.createReceptaDiaMenu(nomMenu,nomDia,nomMenjar,nomR);
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_afegir_receptes_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_afegirReceptaMenu_done) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_afegir_berenar:
                getAddReceptaDialog("berenar").show();
                break;

            case R.id.button_afegir_dinar:
                getAddReceptaDialog("dinar").show();
                break;

            case R.id.button_afegir_bereneta:
                getAddReceptaDialog("bereneta").show();
                break;

            case R.id.button_afegir_sopar:
                getAddReceptaDialog("sopar").show();
                break;
        }
    }
}
