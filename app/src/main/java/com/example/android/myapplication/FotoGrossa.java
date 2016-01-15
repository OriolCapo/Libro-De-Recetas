package com.example.android.myapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.android.myapplication.data.Fotos;

public class FotoGrossa extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_grossa);

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageBitmap(Fotos.loadImageFromStorage(this, getIntent().getStringExtra("nomRecepta")));
    }
}
