package com.example.android.myapplication.Menus;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.myapplication.Data.MenuDAO;
import com.example.android.myapplication.Data.Recepta;
import com.example.android.myapplication.Data.ReceptesDAO;
import com.example.android.myapplication.R;
import com.example.android.myapplication.Receptes.ReceptaActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lvnomsReceptes;
    private ListView lvdiesMenus;
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
        lvdiesMenus = (ListView) findViewById(R.id.listViewDiesMenu);
        tvNomMenu = (TextView) findViewById(R.id.textViewTitolMenu);
        tv_infoMenu = (TextView) findViewById(R.id.tv_Info);
        Intent intent = getIntent();
        nomMenu = intent.getStringExtra("nomMenu");
        if (nomMenu != null) {
            final SpannableString content = new SpannableString(nomMenu);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvNomMenu.setText(content);
            actualitza_listView();
            String info = menuDAO.getInfo(nomMenu);
            tv_infoMenu.setText(info);

            lvnomsReceptes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String nomR = (String) lvnomsReceptes.getItemAtPosition(i);
                    menuDAO.deleteReceptaMenu(nomMenu,nomR.replace(" ","_"));
                    actualitza_listView();
                    return false;
                }
            });

            lvnomsReceptes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String nomR = ((String) lvnomsReceptes.getItemAtPosition(i));
                    ReceptesDAO receptesDAO = new ReceptesDAO(MenuActivity.this);
                    Recepta recepta = receptesDAO.getReceptaByNom(nomR);
                    if (recepta == null) nomR = nomR.replace(" ","_");
                    Intent intent = new Intent(MenuActivity.this,ReceptaActivity.class);
                    intent.putExtra("nomRecepta",nomR);
                    startActivity(intent);
                }
            });

            ArrayList<String> diesMenu = menuDAO.getDiesOfMenu(nomMenu);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, diesMenu);
            lvdiesMenus.setAdapter(adapter);
        }

    }

    private void actualitza_listView() {
        ArrayList<String> nomReceptes = menuDAO.getNomsReceptesOfMenu(nomMenu);
        for (int i=0;i<nomReceptes.size();++i) {
            String nomR = nomReceptes.get(i).replace("_"," ");
            nomReceptes.set(i,nomR);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nomReceptes);
        lvnomsReceptes.setAdapter(adapter);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, menuDAO.getDiesOfMenu(nomMenu));
        lvdiesMenus.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == R.id.menu_item_modify_menu) {
            Intent intent = new Intent(MenuActivity.this,ModificaMenuActivity.class);
            intent.putExtra("nomMenu",nomMenu);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
