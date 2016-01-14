package com.example.android.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.myapplication.data.IngredientsSubstitutsDAO;

import java.util.ArrayList;

public class EditIngredientsSubstitutsActivity extends ActionBarActivity implements View.OnClickListener {

    private LinearLayout ll;
    private Button addIngr;
    private EditText ET_nomIngrSubs;
    private ArrayList<String> ingrOnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients_substituts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ingrOnLayout = new ArrayList<>();
        ll = (LinearLayout) findViewById(R.id.LLingr2);
        addIngr = (Button) findViewById(R.id.addIngr2);
        addIngr.setOnClickListener(this);
        ET_nomIngrSubs = (EditText) findViewById(R.id.editText2);

        String nomRecepta = getIntent().getStringExtra(EditReceptaActivity.PARAM_RECEPTA_NAME);
        if (nomRecepta != null) {
            String nomIngredient = getIntent().getStringExtra(EditReceptaActivity.PARAM_INGREDIENT_RECEPTA);
            ingrOnLayout = new IngredientsSubstitutsDAO(this).getNomsIngredientsSubstitutsOfIngredient(nomRecepta,nomIngredient);
        }
        else {
            ArrayList<String> ingr = getIntent().getStringArrayListExtra(EditReceptaActivity.PARAM_ENVIAR_LLISTA_INGREDIENTS_SUBSTITUTS);
            if (ingr != null) ingrOnLayout = ingr;
        }
        showIngredientsList();
    }

    private void showIngredientsList() {
        buidaLlistaIngredients();
        int quants = ll.getChildCount();
        LayoutInflater inflater = ( LayoutInflater )this.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        for (int i=0; i<ingrOnLayout.size(); ++i) {
            rowView = inflater.inflate(R.layout.nova_recepta_ingredient_item,null);
            final TextView textV = (TextView)rowView.findViewById(R.id.TextView_Ingredient);
            textV.setText(ingrOnLayout.get(i));
            ImageButton eliminarEntrada = (ImageButton)rowView.findViewById(R.id.Button_Eliminar_Ingredient);
            eliminarEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingrOnLayout.remove(textV.getText().toString());
                    showIngredientsList();
                }
            });
            ll.addView(rowView,quants-1);
        }
    }

    private void buidaLlistaIngredients() {
        ll.removeAllViews();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addIngr2) {
            String nouIngr = ET_nomIngrSubs.getText().toString();
            ingrOnLayout.add(nouIngr);
            showIngredientsList();
            ET_nomIngrSubs.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_ingredients_substituts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_done2:
                Intent intent = new Intent();
                intent.putStringArrayListExtra(EditReceptaActivity.PARAM_REBRE_LLISTA_INGREDIENTS_SUBSTITUTS, ingrOnLayout);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
