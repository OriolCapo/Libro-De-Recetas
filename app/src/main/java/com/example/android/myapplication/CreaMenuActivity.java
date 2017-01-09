package com.example.android.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.myapplication.data.MenuDAO;

import java.util.ArrayList;

public class CreaMenuActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText et_nomMenu;
    private EditText et_info;
    private MenuDAO menuDAO;
    private boolean mon,tue,wed,thu,fri,sat,sun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_menu);
        mon=tue=wed=thu=fri=sat=sun=false;
        menuDAO = new MenuDAO(this);
        et_nomMenu = (EditText) findViewById(R.id.editText_nomMenu);
        et_info = (EditText) findViewById(R.id.editText_menu_info);
    }

    @Override
    public void onClick(View view) {
        Button b;
        switch (view.getId()) {
            case R.id.button_mon:
                b = (Button) findViewById(R.id.button_mon);
                if(!mon) {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
                    mon=true;
                } else {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_lightgrey));
                    mon=false;
                }
                break;

            case R.id.button_tue:
                b = (Button) findViewById(R.id.button_tue);
                if(!tue) {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
                    tue=true;
                } else {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_lightgrey));
                    tue=false;
                }
                break;
            case R.id.button_wed:
                b = (Button) findViewById(R.id.button_wed);
                if(!wed) {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
                    wed=true;
                } else {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_lightgrey));
                    wed=false;
                }
                break;
            case R.id.button_thu:
                b = (Button) findViewById(R.id.button_thu);
                if(!thu) {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
                    thu=true;
                } else {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_lightgrey));
                    thu=false;
                }
                break;
            case R.id.button_fri:
                b = (Button) findViewById(R.id.button_fri);
                if(!fri) {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
                    fri=true;
                } else {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_lightgrey));
                    fri=false;
                }
                break;
            case R.id.button_sat:
                b = (Button) findViewById(R.id.button_sat);
                if(!sat) {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
                    sat=true;
                } else {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_lightgrey));
                    sat=false;
                }
                break;
            case R.id.button_sun:
                b = (Button) findViewById(R.id.button_sun);
                if(!sun) {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
                    sun=true;
                } else {
                    b.setBackground(getResources().getDrawable(R.drawable.round_button_lightgrey));
                    sun=false;
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == R.id.menu_item_afegirMenu_done) {
            String nomMenu = et_nomMenu.getText().toString();
            ArrayList<String> nomsMenus = menuDAO.getAllMenusNames();
            if (nomsMenus.contains(nomMenu)){
                Toast.makeText(this,"Ja existeix un menu amb aquest nom",Toast.LENGTH_LONG).show();
            } else if (nomMenu.equals("")) {
                Toast.makeText(this,"Introdueix el nom del menú",Toast.LENGTH_LONG).show();
            } else {
                String info = et_info.getText().toString();
                long id = menuDAO.createMenu(nomMenu,info);
                if (id != -1) {
                    Toast.makeText(this,"Menu creat correctament",Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(this,"Alguna cosa ha anat malament",Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
