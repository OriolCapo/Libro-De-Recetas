package com.example.android.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.myapplication.data.MenuDAO;
import com.example.android.myapplication.data.Recepta;
import com.example.android.myapplication.data.ReceptesDAO;

import java.util.ArrayList;

public class AfegirReceptesMenuActivity extends ActionBarActivity {

    private MenuDAO menuDAO;
    private ReceptesDAO receptesDAO;
    private String nomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_receptes_menu);
        LinearLayout view = (LinearLayout) findViewById(R.id.activity_afegir_receptes_menu);

        receptesDAO = new ReceptesDAO(this);
        menuDAO = new MenuDAO(this);
        ArrayList<String> nomsReceptes = receptesDAO.getAllReceptesNames();

        Intent intent = getIntent();
        nomMenu = intent.getStringExtra("nomMenu");
        ArrayList<String> receptesMenu = menuDAO.getNomsReceptesOfMenu(nomMenu);
        for (int i=0; i<nomsReceptes.size(); ++i) {
            String nomR = nomsReceptes.get(i);
            String nomR2 = nomR.replace(" ","_");
            final CheckBox checkBox = new CheckBox(this);
            checkBox.setText(nomR);
            boolean hi_es = receptesMenu.contains(nomR2);
            if (hi_es) checkBox.setChecked(true);
            view.addView(checkBox,1);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nomR = checkBox.getText().toString();
                    String nomR2 = nomR.replace(" ","_");
                    if (((CheckBox)view).isChecked()) {
                        menuDAO.createReceptaMenu(nomMenu,nomR2);
                    } else {
                        menuDAO.deleteReceptaMenu(nomMenu,nomR2);
                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_afegir_receptes_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_afegirReceptaMenu_done) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
