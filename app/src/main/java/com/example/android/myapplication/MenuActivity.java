package com.example.android.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.data.MenuDAO;
import com.example.android.myapplication.data.Recepta;

import java.util.ArrayList;

public class MenuActivity extends ActionBarActivity implements View.OnClickListener {

    private ListView lvnomsReceptes;
    private TextView tvNomMenu;
    private String nomMenu;
    private MenuDAO menuDAO;
    private TextView tv_infoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        menuDAO = new MenuDAO(this);
        lvnomsReceptes = (ListView) findViewById(R.id.listViewNomsMenus);
        tvNomMenu = (TextView) findViewById(R.id.textViewTitolMenu);
        tv_infoMenu = (TextView) findViewById(R.id.tv_Info);
        Intent intent = getIntent();
        nomMenu = intent.getStringExtra("nomMenu");
        if (nomMenu != null) {
            SpannableString content = new SpannableString(nomMenu);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvNomMenu.setText(content);
            actualitza_listView();
            String info = menuDAO.getInfo(nomMenu);
            info = info.substring(0,1).toUpperCase() + info.substring(1);
            tv_infoMenu.setText(info);

            lvnomsReceptes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String nomR = (String) lvnomsReceptes.getItemAtPosition(i);
                    nomR = nomR.substring(0,1).toLowerCase() + nomR.substring(1);
                    menuDAO.deleteReceptaMenu(nomMenu,nomR.replace(" ","_"));
                    actualitza_listView();
                    return false;
                }
            });

            lvnomsReceptes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String nomR = ((String) lvnomsReceptes.getItemAtPosition(i));
                    nomR = nomR.substring(0,1).toLowerCase() + nomR.substring(1);
                    Intent intent = new Intent(MenuActivity.this,ReceptaActivity.class);
                    intent.putExtra("nomRecepta",nomR);
                    startActivity(intent);
                }
            });
        }

    }

    private void actualitza_listView() {
        ArrayList<String> nomReceptes = menuDAO.getNomsReceptesOfMenu(nomMenu);
        for (int i=0;i<nomReceptes.size();++i) {
            String nomR = nomReceptes.get(i).replace("_"," ");
            nomR = nomR.substring(0,1).toUpperCase() + nomR.substring(1);
            nomReceptes.set(i,nomR);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nomReceptes);
        lvnomsReceptes.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_afegir_recepta_menu:
                Intent intent = new Intent(this, AfegirReceptesMenuActivity.class);
                intent.putExtra("nomMenu",nomMenu);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        actualitza_listView();
        super.onResume();
    }
}
