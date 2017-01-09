package com.example.android.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.myapplication.data.MenuDAO;

import java.util.ArrayList;

public class LlistatMenusActivity extends ActionBarActivity {

    private ListView lv_nomsMenus;
    private MenuDAO menuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_menus);
        menuDAO = new MenuDAO(this);
        actualitza_listView();

        lv_nomsMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nomR = (String) lv_nomsMenus.getItemAtPosition(i);
                Intent intent = new Intent(LlistatMenusActivity.this,MenuActivity.class);
                intent.putExtra("nomMenu",nomR);
                startActivity(intent);
            }
        });

        lv_nomsMenus.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nomMenu = (String) lv_nomsMenus.getItemAtPosition(i);
                getDeleteDialog(nomMenu).show();
                return false;
            }
        });
    }

    private AlertDialog getDeleteDialog(final String nomMenu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LlistatMenusActivity.this);
        builder.setTitle("Estas segur que vols eliminar aquest menú?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                menuDAO.deleteMenu(nomMenu);
                actualitza_listView();
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

    private void actualitza_listView() {
        lv_nomsMenus = (ListView) findViewById(R.id.listViewNomsMenus2);
        ArrayList<String> nomsMenus = menuDAO.getAllMenusNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomsMenus);
        lv_nomsMenus.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_llistat_menus,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_add_menu) {
            startActivity(new Intent(this,CreaMenuActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        actualitza_listView();
        super.onResume();
    }
}
