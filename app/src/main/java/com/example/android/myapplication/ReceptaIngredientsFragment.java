package com.example.android.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.myapplication.data.IngredientsReceptesDAO;
import java.util.ArrayList;

/**
 * Created by Oriolcapo on 12/01/2016.
 */
public class ReceptaIngredientsFragment extends android.support.v4.app.Fragment {

    public static final String PARAM_NOM_RECEPTA = "nomRecepta";
    public static final String PARAM_NOM_INGREDIENT = "nomIngredient";
    private static final int REQUEST_CODE_INGREDIENTS_SUBSTITUTS = 666;
    private ListView listIngredients;
    private String nomRecepta;
    private View rootView;
    private String item;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> ingredientsToShow;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ingredients_recepta, container,
                false);

        Bundle args = this.getArguments();
        nomRecepta = args.getString("Recepta");
        listIngredients = (ListView) rootView.findViewById(R.id.listIngredients);
        ingredientsToShow = new IngredientsReceptesDAO(rootView.getContext()).getNomsIngredientsOfRecepta(nomRecepta);
        adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, ingredientsToShow);
        listIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nomIngredient = adapter.getItem(i); //nom ingredient
                item = nomIngredient;
                Intent intent = new Intent(rootView.getContext(), IngredientsSubstitutsActivity.class);
                intent.putExtra(PARAM_NOM_RECEPTA, nomRecepta);
                intent.putExtra(PARAM_NOM_INGREDIENT, nomIngredient);
                startActivityForResult(intent, REQUEST_CODE_INGREDIENTS_SUBSTITUTS);
            }
        });
        listIngredients.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INGREDIENTS_SUBSTITUTS && resultCode == Activity.RESULT_OK) {
            String ingredient = data.getStringExtra(IngredientsSubstitutsActivity.PARAM_NOM_INGREDIENT_SUBS);
            adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, ingredientsToShow);
            listIngredients.setAdapter(adapter);
            
        }
    }
}
