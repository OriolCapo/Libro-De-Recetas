package com.example.android.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.android.myapplication.data.Recepta;
import com.example.android.myapplication.data.ReceptesDAO;

import java.util.ArrayList;

public class LlistatReceptesActivity extends ActionBarActivity implements View.OnClickListener {

    private GridViewAdapter imageAdapter;
    private ImageButton Button_Afegir_Recepta;
    private GridView gridview;
    private EditText ET_cercador;
    private boolean favourite;

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
        ArrayList<String> receptes = new ReceptesDAO(this).getAllReceptesNames();
        imageAdapter = new GridViewAdapter(this, receptes);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(LlistatReceptesActivity.this, ReceptaActivity.class);
                i.putExtra("nomRecepta", imageAdapter.getThumbName(position));//Posición del elemento
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageAdapter.refresh();
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
            //startActivity(new Intent(this,Lala.class));
        }
        else if (id == R.id.menu_item_favourite) {
            if (!favourite) {
                //imageAdapter = new GridViewAdapter(this, receptes);
                item.setIcon(getResources().getDrawable(R.drawable.ic_star_white_18dp));
                favourite = true;
            }
            else {
                //imageAdapter = new GridViewAdapter(this, receptes);
                item.setIcon(getResources().getDrawable(R.drawable.ic_star_border_white_18dp));
                favourite = false;
            }
            gridview.setAdapter(imageAdapter);
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