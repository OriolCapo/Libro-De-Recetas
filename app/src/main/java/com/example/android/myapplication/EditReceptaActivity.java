package com.example.android.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Widget.Widget;
import com.example.android.myapplication.data.Fotos;
import com.example.android.myapplication.data.IngredientsReceptesDAO;
import com.example.android.myapplication.data.IngredientsSubstitutsDAO;
import com.example.android.myapplication.data.PhotoUtils;
import com.example.android.myapplication.data.Recepta;
import com.example.android.myapplication.data.ReceptesDAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditReceptaActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String PARAM_INGREDIENT_RECEPTA = "nomIngredient";
    public static final int REQUEST_CODE_EDIT_SUBSTITUTS = 1111;
    public static final String PARAM_REBRE_LLISTA_INGREDIENTS_SUBSTITUTS = "llistaIngredientsSubstitutsRebuts";
    public static final String PARAM_RECEPTA_NAME = "nomRecepta";
    public static final String PARAM_ENVIAR_LLISTA_INGREDIENTS_SUBSTITUTS = "llistaIngredientsSubstitutsEnviats";
    private Uri mImageUri;
    private static final int REQUEST_CODE_SELECT_IMAGE = 1020,
            REQUEST_CODE_SELECT_FROM_CAMERA = 1040;
    private PhotoUtils photoUtils;
    private Button addIngr;
    private Bitmap Foto;
    private EditText ET_nomR, ET_desc, ET_sugg, ET_nouIngr;
    private ImageButton photoButton;
    private ImageView photoViewer;
    private boolean modificar;
    private ArrayList<String> ingrOnLayout;
    private Recepta recepta;
    private String oldName;
    private AlertDialog _photoDialog;
    private String nomIngredientToEdit;
    private HashMap<String, ArrayList<String>> Ingredients_Substituts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recepta);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        photoUtils = new PhotoUtils(this);
        addIngr = (Button) findViewById(R.id.addIngr);
        addIngr.setOnClickListener(this);
        ET_nomR = (EditText) findViewById(R.id.editText6);
        ET_desc = (EditText) findViewById(R.id.editText8);
        ET_sugg = (EditText) findViewById(R.id.editText9);
        ET_nouIngr = (EditText) findViewById(R.id.editText);
        photoButton = (ImageButton) findViewById(R.id.imageButton);
        photoViewer = (ImageView) findViewById(R.id.imageView);
        Ingredients_Substituts = new HashMap<>();
        getPhotoDialog();
        setPhotoButton();
        Foto = ((BitmapDrawable) getResources().getDrawable(R.drawable.empty)).getBitmap();
        ingrOnLayout = new ArrayList<>();
        modificar = false;
        recepta = new Recepta();
        Intent intent = getIntent();
        String nomR = intent.getStringExtra("nomRecepta");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (getResources().getConfiguration().orientation == 1) {       //vertical
            photoViewer.getLayoutParams().width = width;
            photoViewer.getLayoutParams().height = height/2;
        }
        else {                                                          //horitzontal
            photoViewer.getLayoutParams().width = LlistatReceptesActivity.height/2;
            photoViewer.getLayoutParams().height = LlistatReceptesActivity.height/3;
        }
        photoViewer.requestLayout();
        if (nomR != null) {
            modificar = true;
            oldName = nomR;
            omplInfo(nomR);
        }
    }

    private void omplInfo(String nomR) {
        ReceptesDAO rDAO = new ReceptesDAO(this);
        recepta = rDAO.getReceptaByNom(nomR);
        ET_nomR.setText(recepta.getNom());          //modificam nom
        ET_desc.setText(recepta.getDescripcio());   //modificam descripcio
        ET_sugg.setText(recepta.getSuggeriments()); //modificam suggeriments
        IngredientsReceptesDAO irDAO = new IngredientsReceptesDAO(this);
        ingrOnLayout = irDAO.getNomsIngredientsOfRecepta(recepta.getNom());
        IngredientsSubstitutsDAO isDAO = new IngredientsSubstitutsDAO(this);
        for (int i=0; i<ingrOnLayout.size(); i++) {
            ArrayList<String> ingr = isDAO.getNomsIngredientsSubstitutsOfIngredient(recepta.getNom(), ingrOnLayout.get(i));
            Ingredients_Substituts.put(ingrOnLayout.get(i), ingr);
        }
        showIngredientsList();
        Foto = Fotos.loadImageFromStorage(this, recepta.getNom());
        photoViewer.setImageBitmap(Foto);
    }

    private void showIngredientsList() {
        buidaLlistaIngredients();
        LinearLayout l = (LinearLayout) findViewById(R.id.LLingr);
        int quants = l.getChildCount();
        LayoutInflater inflater = ( LayoutInflater )this.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        for (int i=0; i<ingrOnLayout.size(); ++i) {
            rowView = inflater.inflate(R.layout.nova_recepta_ingredient_item,null);
            final TextView textV = (TextView)rowView.findViewById(R.id.TextView_Ingredient);
            textV.setText(ingrOnLayout.get(i));
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nomIngredientToEdit = textV.getText().toString();
                    Intent i = new Intent (getApplicationContext(), EditIngredientsSubstitutsActivity.class);
                    ArrayList<String> ingredientsPerPassar = Ingredients_Substituts.get(nomIngredientToEdit);
                    if (ingredientsPerPassar != null) i.putStringArrayListExtra(PARAM_ENVIAR_LLISTA_INGREDIENTS_SUBSTITUTS, ingredientsPerPassar);
                    startActivityForResult(i, REQUEST_CODE_EDIT_SUBSTITUTS);
                }
            });
            ImageButton eliminarEntrada = (ImageButton)rowView.findViewById(R.id.Button_Eliminar_Ingredient);
            eliminarEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingrOnLayout.remove(textV.getText().toString());
                    showIngredientsList();
                }
            });
            l.addView(rowView, quants - 1);
        }
    }

    private void buidaLlistaIngredients() {
        LinearLayout l = (LinearLayout) findViewById(R.id.LLingr);
        l.removeAllViews();
    }

    private void setPhotoButton(){
        photoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!getPhotoDialog().isShowing() && !isFinishing())
                    getPhotoDialog().show();
            }
        });
    }

    private AlertDialog getPhotoDialog() {
        if (_photoDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Photo Source");
            builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    File photo = null;
                    try {
                        // place where to store camera taken picture
                        photo = PhotoUtils.createTemporaryFile("picture", ".jpg", EditReceptaActivity.this);
                        photo.delete();
                    } catch (Exception e) {
                        Log.v(getClass().getSimpleName(),
                                "Can't create file to take picture!");
                    }
                    mImageUri = Uri.fromFile(photo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(intent, REQUEST_CODE_SELECT_FROM_CAMERA);

                }

            });
            builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, REQUEST_CODE_SELECT_IMAGE);
                }

            });
            _photoDialog = builder.create();

        }
        return _photoDialog;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageUri != null)
            outState.putString("Uri", mImageUri.toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("Uri")) {
            mImageUri = Uri.parse(savedInstanceState.getString("Uri"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            getImage(mImageUri);
        }
        else if (requestCode == REQUEST_CODE_SELECT_FROM_CAMERA && resultCode == RESULT_OK) {
            getImage(mImageUri);
        }
        else if (requestCode == REQUEST_CODE_EDIT_SUBSTITUTS && resultCode == RESULT_OK) {
            if (Ingredients_Substituts.containsKey(nomIngredientToEdit)) {
                Ingredients_Substituts.remove(nomIngredientToEdit);
            }
            ArrayList<String> ingredientsSubstitut = data.getStringArrayListExtra(PARAM_REBRE_LLISTA_INGREDIENTS_SUBSTITUTS);
            if (ingredientsSubstitut != null) {
                Ingredients_Substituts.put(nomIngredientToEdit, ingredientsSubstitut);
            }

        }
    }

    public void getImage(Uri uri) {
        Bitmap bounds = photoUtils.getImage(uri);
        if (bounds != null) {
            setImage(bounds);
        }
    }

    private void setImage(Bitmap bitmap){
        photoViewer.setImageBitmap(bitmap);
        Foto = bitmap;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addIngr:
                String nouIngr = ET_nouIngr.getText().toString();
                ingrOnLayout.add(nouIngr);
                showIngredientsList();
                ET_nouIngr.setText("");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_recepta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_done:
                recepta.setNom(ET_nomR.getText().toString());
                recepta.setDescripcio(ET_desc.getText().toString());
                recepta.setSuggeriments(ET_sugg.getText().toString());
                ReceptesDAO rDAO = new ReceptesDAO(this);
                if (!recepta.getNom().equals("")) {
                    if (modificar) {
                        rDAO.updateRecepta(oldName, recepta.getNom(), recepta.getDescripcio(), recepta.getSuggeriments());
                    } else {
                        rDAO.createRecepta(recepta.getNom(), recepta.getDescripcio(), recepta.getSuggeriments());
                    }
                    modificaIngredients(recepta.getNom());
                    Fotos.eliminaFoto(recepta.getNom(), getApplicationContext());
                    try {
                        Fotos.saveToInternalSorage(this, Foto, recepta.getNom());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!modificar) {
                        Intent i = new Intent(this, ReceptaActivity.class);
                        i.putExtra("nomRecepta", recepta.getNom());
                        startActivity(i);
                    } else {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("nomRecepta", recepta.getNom());
                        setResult(Activity.RESULT_OK, returnIntent);
                        Widget.class.notify();
                    }
                    finish();
                }
                else {
                    Toast.makeText(this, "Introdueix un títol",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void modificaIngredients(String nomRecepta) {
        IngredientsReceptesDAO irDAO = new IngredientsReceptesDAO(this);
        IngredientsSubstitutsDAO isDAO = new IngredientsSubstitutsDAO(this);
        irDAO.deleteIngredientsOfRecepta(nomRecepta);
        isDAO.deleteIngredientsSubstitutsOfRecepta(nomRecepta);
        LinearLayout l = (LinearLayout) findViewById(R.id.LLingr);
        for (int i = 0; i < ingrOnLayout.size(); i++) {
            String nomIngr = ingrOnLayout.get(i);
            irDAO.createIngredientsRecepta(nomRecepta,nomIngr);

            if (Ingredients_Substituts.containsKey(nomIngr)) {
                ArrayList<String> ingrSubs = Ingredients_Substituts.get(nomIngr);
                for (int j = 0; j < ingrSubs.size(); j++) {
                    isDAO.createIngredientSubstitut(nomRecepta, nomIngr, ingrSubs.get(j));
                }
            }
        }

    }
}
