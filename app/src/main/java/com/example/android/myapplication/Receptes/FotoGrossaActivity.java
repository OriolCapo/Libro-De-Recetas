package com.example.android.myapplication.Receptes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.android.myapplication.Data.Fotos;
import com.example.android.myapplication.R;

public class FotoGrossaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_grossa);

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageBitmap(Fotos.loadImageFromStorage(this, getIntent().getStringExtra("nomRecepta")));
    }
}
