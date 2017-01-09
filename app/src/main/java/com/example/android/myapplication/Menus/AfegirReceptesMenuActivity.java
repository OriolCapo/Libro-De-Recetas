package com.example.android.myapplication.Menus;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.android.myapplication.Data.MenuDAO;
import com.example.android.myapplication.Data.ReceptesDAO;
import com.example.android.myapplication.R;

import java.util.ArrayList;

public class AfegirReceptesMenuActivity extends AppCompatActivity {

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

            final CheckBox checkBox = new CheckBox(this);
            checkBox.setText(nomR.replace("_"," "));
            boolean hi_es = receptesMenu.contains(nomR);
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
