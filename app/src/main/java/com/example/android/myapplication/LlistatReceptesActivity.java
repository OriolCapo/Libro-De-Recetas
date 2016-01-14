package com.example.android.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

public class LlistatReceptesActivity extends ActionBarActivity implements View.OnClickListener {

    private GridViewAdapter imageAdapter;
    private ImageButton Button_Afegir_Recepta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_receptes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button_Afegir_Recepta = (ImageButton) findViewById(R.id.Button_Afegir_Recepta);
        Button_Afegir_Recepta.setOnClickListener(this);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        imageAdapter = new GridViewAdapter(this);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(LlistatReceptesActivity.this, ReceptaActivity.class);
                i.putExtra("titol", position);//Posición del elemento
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
            startActivity(new Intent(this,IngredientsSubstitutsActivity.class));
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
