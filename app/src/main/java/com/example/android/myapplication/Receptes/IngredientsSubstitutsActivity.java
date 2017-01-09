package com.example.android.myapplication.Receptes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.myapplication.Receptes.FragmentsRecepta.ReceptaIngredientsFragment;
import com.example.android.myapplication.Data.IngredientsReceptesDAO;
import com.example.android.myapplication.Data.IngredientsSubstitutsDAO;
import com.example.android.myapplication.R;

import java.util.ArrayList;

public class IngredientsSubstitutsActivity extends AppCompatActivity {

    public static final String PARAM_NOM_INGREDIENT_SUBS = "nomIngredient";
    private ListView listView;
    private ArrayList<String> ingredientsSubstitutsToShow;
    private ArrayAdapter<String> adapter;
    private String nomRecepta;
    private String nomIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_substituts);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        listView = (ListView) findViewById(R.id.list_view);
        Intent intent = getIntent();
        nomRecepta = intent.getStringExtra(ReceptaIngredientsFragment.PARAM_NOM_RECEPTA);
        nomIngredient = intent.getStringExtra(ReceptaIngredientsFragment.PARAM_NOM_INGREDIENT);
        ingredientsSubstitutsToShow = new IngredientsSubstitutsDAO(this).getNomsIngredientsSubstitutsOfIngredient(nomRecepta,nomIngredient);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingredientsSubstitutsToShow);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nomIngredientNou = adapter.getItem(i); //nom ingredient
                IngredientsReceptesDAO irDAO = new IngredientsReceptesDAO(getApplicationContext());
                IngredientsSubstitutsDAO isDAO = new IngredientsSubstitutsDAO(getApplicationContext());
                irDAO.updateIngredient(nomRecepta, nomIngredient, nomIngredientNou);
                isDAO.updateIngredientReceptaPerIngredientSubstitut(nomRecepta, nomIngredient, nomIngredientNou);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
