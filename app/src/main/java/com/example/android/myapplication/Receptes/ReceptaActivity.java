package com.example.android.myapplication.Receptes;

import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.Receptes.FragmentsRecepta.ReceptaDescripcioFragment;
import com.example.android.myapplication.Receptes.FragmentsRecepta.ReceptaFotosFragment;
import com.example.android.myapplication.Receptes.FragmentsRecepta.ReceptaIngredientsFragment;
import com.example.android.myapplication.Receptes.FragmentsRecepta.ReceptaSuggerimentsFragment;
import com.example.android.myapplication.Data.Fotos;
import com.example.android.myapplication.Data.ReceptesDAO;
import com.example.android.myapplication.R;

public class ReceptaActivity extends FragmentActivity {

    private static final int REQUEST_CODE_EDIT_RECEPTA = 123;
    private TextView titol;
    private ImageView imatge;
    private ReceptaActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String nomRecepta;
    private ImageButton button_favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepta);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById(R.id.imageButton_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity();
            }
        });
        titol = (TextView) findViewById(R.id.titol);
        imatge = (ImageView)findViewById(R.id.img1);
        button_favourite = (ImageButton) findViewById(R.id.imageButton_favourite);
        findViewById(R.id.imageButton_favourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceptesDAO rDAO = new ReceptesDAO(getApplicationContext());
                if (rDAO.getIfFavourite(nomRecepta)) {
                    button_favourite.setImageResource(R.drawable.ic_star_border_white_18dp);
                    rDAO.updateReceptaFavourite(nomRecepta, "no");
                }
                else {
                    button_favourite.setImageResource(R.drawable.ic_star_white_18dp);
                    rDAO.updateReceptaFavourite(nomRecepta, "si");
                }
            }
        });
        Intent i = getIntent();
        String nomR = i.getStringExtra("nomRecepta");
        if (nomR != null) {
            nomRecepta = nomR;
            imatge.setImageBitmap(Fotos.loadImageFromStorage(this, nomR));
        }
        ReceptesDAO rDAO = new ReceptesDAO(this);
        if (rDAO.getIfFavourite(nomRecepta)) button_favourite.setImageResource(R.drawable.ic_star_white_18dp);
        titol.setText(nomRecepta.replace('_',' '));
        imatge.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);

        imatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceptaActivity.this, FotoGrossaActivity.class);
                intent.putExtra("nomRecepta", nomRecepta);
                startActivity(intent);
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (getResources().getConfiguration().orientation == 1) {       //vertical
            imatge.getLayoutParams().width = width;
            imatge.getLayoutParams().height = height/2;
        }
        else {                                                          //horitzontal
            imatge.getLayoutParams().width = LlistatReceptesActivity.height/2;
            imatge.getLayoutParams().height = LlistatReceptesActivity.height/3;
        }
        imatge.requestLayout();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_RECEPTA && resultCode == RESULT_OK) {
            nomRecepta = data.getStringExtra("nomRecepta");
            Intent i = new Intent(this, ReceptaActivity.class);
            i.putExtra ("nomRecepta",nomRecepta);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new Fragment();
            Bundle args = new Bundle();
            switch (position) {
                case 0:
                    fragment = new ReceptaIngredientsFragment(); // Fragment 1
                    break;
                case 1:
                    fragment = new ReceptaDescripcioFragment(); // Fragment 2
                    break;
                case 2:
                    fragment = new ReceptaFotosFragment(); // Fragment 3
                    break;
                case 3:
                    fragment = new ReceptaSuggerimentsFragment();
                    break;
                default:
                    break;
            }
            args.putString("Recepta", nomRecepta);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Total de páginas
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Ingredients";
                case 1:
                    return "Descripció";
                case 2:
                    return "Passos/Fotos";
                case 3:
                    return "Suggeriments";
            }
            return null;
        }


    }

    private void startEditActivity() {
        Intent i = new Intent(this, EditReceptaActivity.class);
        i.putExtra("nomRecepta", nomRecepta);
        startActivityForResult(i, REQUEST_CODE_EDIT_RECEPTA);
    }
}
