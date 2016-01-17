package com.example.android.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.myapplication.data.ReceptesDAO;

import java.util.ArrayList;

public class LlistatReceptesActivity extends ActionBarActivity implements View.OnClickListener {

    private GridViewAdapter imageAdapter;
    private ImageButton Button_Afegir_Recepta;
    private GridView gridview;
    private EditText ET_cercador;
    private boolean favourite;
    private ListView slide_list_menu;
    private DrawerLayout drawerLayout;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_receptes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        favourite = false;
        ET_cercador = (EditText) findViewById(R.id.editText3);
        Button_Afegir_Recepta = (ImageButton) findViewById(R.id.Button_Afegir_Recepta);
        Button_Afegir_Recepta.setOnClickListener(this);
        gridview = (GridView) findViewById(R.id.gridView);

        imageAdapter = new GridViewAdapter(this);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(LlistatReceptesActivity.this, ReceptaActivity.class);
                i.putExtra("nomRecepta", imageAdapter.getThumbName(position));//Posición del elemento
                startActivity(i);
            }
        });

        slide_list_menu = (ListView) findViewById(R.id.list_view34);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final String[] opciones = { "Eliminar receptes", "Mostrar receptes preferides", "Cercar recepta per nom" };

        slide_list_menu.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                opciones));

        slide_list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    imageAdapter.canviaAEsborrarFotos();
                }
                if (i == 1) {
                    MenuItem item = menu.findItem(R.id.menu_item_favourites);
                    if (!favourite) {
                        item.setIcon(getResources().getDrawable(R.drawable.ic_star_white_18dp));
                        favourite = true;
                        imageAdapter.canviaSource(favourite);
                    }
                    else {
                        item.setIcon(getResources().getDrawable(R.drawable.ic_star_border_white_18dp));
                        favourite = false;
                        imageAdapter.canviaSource(favourite);
                    }
                }
                drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageAdapter.canviaSource(favourite);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_llistat_receptes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this,Proves.class));
        }

        if (id == R.id.menu_item_favourites) {
            if (!favourite) {
                item.setIcon(getResources().getDrawable(R.drawable.ic_star_white_18dp));
                favourite = true;
                imageAdapter.canviaSource(favourite);
            }
            else {
                item.setIcon(getResources().getDrawable(R.drawable.ic_star_border_white_18dp));
                favourite = false;
                imageAdapter.canviaSource(favourite);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Button_Afegir_Recepta:
                startActivity(new Intent(this,EditReceptaActivity.class));
                break;
        }
    }
}