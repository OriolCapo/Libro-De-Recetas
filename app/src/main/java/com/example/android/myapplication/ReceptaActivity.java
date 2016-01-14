package com.example.android.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.data.Fotos;
import com.example.android.myapplication.data.Utils;

public class ReceptaActivity extends FragmentActivity {

    private static final int REQUEST_CODE_EDIT_RECEPTA = 123;
    private TextView titol;
    private ImageView imatge;
    private ReceptaActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String nomRecepta;
    private ImageButton button_favourite;
    private boolean b = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
                if (b) {
                    button_favourite.setBackground(getResources().getDrawable(R.drawable.ic_star_border_white_18dp));
                    b = false;
                }
                else {
                    button_favourite.setBackground((getResources().getDrawable(R.drawable.ic_star_white_18dp)));
                    b = true;
                }
            }
        });
        Intent i = getIntent();
        int position = i.getIntExtra("titol", -1);
        if (position != -1) {
            GridViewAdapter adapter = new GridViewAdapter(this);
            nomRecepta = adapter.getThumbName(position);
            imatge.setImageBitmap(adapter.getThumbId(position));
        }
        else {
            String nomR = i.getStringExtra("nomRecepta");
            if (nomR != null) {
                nomRecepta = nomR;
                imatge.setImageBitmap(Fotos.loadImageFromStorage(this, nomR));
            }
        }
        titol.setText(Utils.llevaGuions(nomRecepta));
        imatge.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
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
                    fragment = new ReceptaSuggerimentsFragment(); // Fragment 3
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
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Ingredients";
                case 1:
                    return "Descripció";
                case 2:
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
