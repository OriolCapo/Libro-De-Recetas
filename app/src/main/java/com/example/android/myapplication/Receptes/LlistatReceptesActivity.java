package com.example.android.myapplication.Receptes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.myapplication.Data.MenuDAO;
import com.example.android.myapplication.Menus.CreaMenuActivity;
import com.example.android.myapplication.Menus.LlistatMenusActivity;
import com.example.android.myapplication.Menus.MenuActivity;
import com.example.android.myapplication.Proves;
import com.example.android.myapplication.R;

import java.util.ArrayList;

public class LlistatReceptesActivity extends AppCompatActivity implements View.OnClickListener {

    private GridViewAdapter imageAdapter;
    private ImageButton Button_Afegir_Recepta;
    private GridView gridview;

    private boolean favourite;
    private ListView slide_list_menu;
    private DrawerLayout drawerLayout;
    public static int width;
    public static int height;
    private ArrayList<String> opciones2;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_receptes);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        favourite = false;
        Button_Afegir_Recepta = (ImageButton) findViewById(R.id.Button_Afegir_Recepta);
        Button_Afegir_Recepta.setOnClickListener(this);
        gridview = (GridView) findViewById(R.id.gridView);

        imageAdapter = new GridViewAdapter(this);
        gridview.setAdapter(imageAdapter);


        /** Navigation view Code **/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        TextView tv = new TextView(this);
        tv.setText("hola");

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView mNavView = (NavigationView)findViewById(R.id.navview);

        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.esborrar_recepta:
                        imageAdapter.canviaAEsborrarFotos();
                        break;

                    case R.id.anar_a_menus:
                        startActivity(new Intent(LlistatReceptesActivity.this, LlistatMenusActivity.class));
                        break;

                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
/*
        slide_list_menu = (ListView) findViewById(R.id.list_view34);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actualitza_barra_lateral();

        slide_list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    imageAdapter.canviaAEsborrarFotos();
                    drawerLayout.closeDrawers();
                } else if (i == 1) {
                    startActivity(new Intent(LlistatReceptesActivity.this, CreaMenuActivity.class));
                } else {
                    Intent intent = new Intent(LlistatReceptesActivity.this, MenuActivity.class);
                    intent.putExtra("nomMenu",opciones2.get(i));
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                }
            }
        });
        slide_list_menu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 1){
                    getDeleteDialog(opciones2.get(i)).show();
                }
                return false;
            }
        });*/
        //_______________________
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        Log.d("width", Integer.toString(width));
        Log.d("height", Integer.toString(height));
        if (getResources().getConfiguration().orientation != 1) {
            gridview.setNumColumns(4);
        }
    }

    private AlertDialog getDeleteDialog(final String nomMenu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estas segur que vols eliminar aquest menú?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MenuDAO menuDAO = new MenuDAO(LlistatReceptesActivity.this);
                menuDAO.deleteMenu(nomMenu);
                //actualitza_barra_lateral();
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

    @Override
    protected void onResume() {
        super.onResume();
        //actualitza_barra_lateral();
        imageAdapter.canviaSource(favourite);
    }

    private void actualitza_barra_lateral(){
        MenuDAO menuDAO = new MenuDAO(this);
        opciones2 = new ArrayList<>();
        opciones2.add("Eliminar receptes");
        opciones2.add("Afegir Menu");
        ArrayList<String> menus = menuDAO.getAllMenusNames();
        for (int j=0; j<menus.size(); ++j) opciones2.add(menus.get(j));

        slide_list_menu.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                opciones2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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