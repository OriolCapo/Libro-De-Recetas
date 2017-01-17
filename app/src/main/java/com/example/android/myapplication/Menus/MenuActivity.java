package com.example.android.myapplication.Menus;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Data.MenuDAO;
import com.example.android.myapplication.Data.Recepta;
import com.example.android.myapplication.Data.ReceptesDAO;
import com.example.android.myapplication.R;
import com.example.android.myapplication.Receptes.ReceptaActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    public static final String CODI_NOM = "nomMenu";
    public static String CODI_INFO = "info";
    private TextView tvNomMenu;
    private String nomMenu;
    private MenuDAO menuDAO;
    private TextView tv_infoMenu;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        menuDAO = new MenuDAO(this);
        tvNomMenu = (TextView) findViewById(R.id.textViewTitolMenu);
        tv_infoMenu = (TextView) findViewById(R.id.tv_Info);
        Intent intent = getIntent();
        nomMenu = intent.getStringExtra("nomMenu");
        if (nomMenu != null) {
            final SpannableString content = new SpannableString(nomMenu);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvNomMenu.setText(content);
            String info = menuDAO.getInfo(nomMenu);
            tv_infoMenu.setText(info);

            // Obtener el Recycler
            recycler = (RecyclerView) findViewById(R.id.recycler);
            recycler.setHasFixedSize(false);

            // Usar un administrador para LinearLayout
            lManager = new LinearLayoutManager(this);
            recycler.setLayoutManager(lManager);

            // Crear un nuevo adaptador
            adapter = new ReceptesDiaAdapter(this,nomMenu);
            recycler.setAdapter(adapter);
        }
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
            startActivityForResult(intent,ModificaMenuActivity.REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ModificaMenuActivity.REQUEST_CODE && resultCode == RESULT_OK && data != null){
            nomMenu = data.getStringExtra(CODI_NOM);
            String nouInfo = data.getStringExtra(CODI_INFO);
            final SpannableString content = new SpannableString(nomMenu);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvNomMenu.setText(content);
            tv_infoMenu.setText(nouInfo);

            actualitza_recycler();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        actualitza_recycler();
        super.onResume();
    }

    private void actualitza_recycler() {
        adapter = new ReceptesDiaAdapter(this,nomMenu);
        recycler.setAdapter(adapter);
    }
}
