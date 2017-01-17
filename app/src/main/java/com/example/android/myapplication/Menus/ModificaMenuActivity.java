package com.example.android.myapplication.Menus;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.myapplication.Data.MenuDAO;
import com.example.android.myapplication.R;

import java.util.ArrayList;

public class ModificaMenuActivity extends AppCompatActivity implements View.OnClickListener{

    public static int REQUEST_CODE = 666;
    private EditText et_nomMenu;
    private EditText et_info;
    private String nomMenu;
    private MenuDAO menuDAO;
    private boolean mon,tue,wed,thu,fri,sat,sun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_menu);
        mon=tue=wed=thu=fri=sat=sun=false;
        menuDAO = new MenuDAO(this);
        Intent intent = getIntent();
        nomMenu = intent.getStringExtra("nomMenu").toString();
        if (nomMenu!=null){
            et_nomMenu = (EditText) findViewById(R.id.editText_nomMenu);
            et_nomMenu.setText(nomMenu);
            et_info = (EditText) findViewById(R.id.editText_menu_info);
            et_info.setText(menuDAO.getInfo(nomMenu));
            ArrayList<String> diesMenu = menuDAO.getDiesOfMenu(nomMenu);

            Button b_mon = (Button) findViewById(R.id.button_mon);
            Button b_tue = (Button) findViewById(R.id.button_tue);
            Button b_wed = (Button) findViewById(R.id.button_wed);
            Button b_thu = (Button) findViewById(R.id.button_thu);
            Button b_fri = (Button) findViewById(R.id.button_fri);
            Button b_sat = (Button) findViewById(R.id.button_sat);
            Button b_sun = (Button) findViewById(R.id.button_sun);

            if(diesMenu.contains("monday")) {
                mon=true;
                b_mon.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
            }
            if(diesMenu.contains("tuesday")) {
                tue=true;
                b_tue.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
            }
            if(diesMenu.contains("wednesday")) {
                wed=true;
                b_wed.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
            }
            if(diesMenu.contains("thursday")) {
                thu=true;
                b_thu.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
            }
            if(diesMenu.contains("friday")) {
                fri=true;
                b_fri.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
            }
            if(diesMenu.contains("saturday")) {
                sat=true;
                b_sat.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
            }
            if(diesMenu.contains("sunday")) {
                sun=true;
                b_sun.setBackground(getResources().getDrawable(R.drawable.round_button_darkgrey));
            }
        }

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
        getMenuInflater().inflate(R.menu.menu_modifica_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == R.id.menu_item_modificaMenu_done) {
            String newNom = et_nomMenu.getText().toString();
            if (newNom.equals("")) {
                Toast.makeText(this,"Introdueix el nom del menú",Toast.LENGTH_LONG).show();
            } else {
                String info = et_info.getText().toString();
                int quants = menuDAO.updateMenu(nomMenu,newNom,info);
                if (quants > 0) {
                    Toast.makeText(this,"Menu modificat correctament",Toast.LENGTH_LONG).show();
                    if(mon) menuDAO.createDiaMenu(newNom,"monday");
                    else menuDAO.deleteDiaMenu(nomMenu,"monday");
                    if(tue) menuDAO.createDiaMenu(newNom,"tuesday");
                    else menuDAO.deleteDiaMenu(nomMenu,"tuesday");
                    if(wed) menuDAO.createDiaMenu(newNom,"wednesday");
                    else menuDAO.deleteDiaMenu(nomMenu,"wednesday");
                    if(thu) menuDAO.createDiaMenu(newNom,"thursday");
                    else menuDAO.deleteDiaMenu(nomMenu,"thursday");
                    if(fri) menuDAO.createDiaMenu(newNom,"friday");
                    else menuDAO.deleteDiaMenu(nomMenu,"friday");
                    if(sat) menuDAO.createDiaMenu(newNom,"saturday");
                    else menuDAO.deleteDiaMenu(nomMenu,"saturday");
                    if(sun) menuDAO.createDiaMenu(newNom,"sunday");
                    else menuDAO.deleteDiaMenu(nomMenu,"sunday");
                }
                else Toast.makeText(this,"Alguna cosa ha sortit malament",Toast.LENGTH_LONG).show();
                Intent output  = new Intent();
                output.putExtra(MenuActivity.CODI_NOM, newNom);
                output.putExtra(MenuActivity.CODI_INFO, info);
                setResult(RESULT_OK, output);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
