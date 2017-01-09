package com.example.android.myapplication.Receptes.FragmentsRecepta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.Data.Recepta;
import com.example.android.myapplication.Data.ReceptesDAO;

/**
 * Created by Oriolcapo on 12/01/2016.
 */
public class ReceptaSuggerimentsFragment extends android.support.v4.app.Fragment {

    private String nomRecepta;
    private TextView TV_Sug;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_suggeriments_recepta, container,
                false);

        Bundle args = this.getArguments();
        nomRecepta = args.getString("Recepta");
        TV_Sug = (TextView) rootView.findViewById(R.id.textView10);
        ReceptesDAO rDAO = new ReceptesDAO(rootView.getContext());
        Recepta recepta = rDAO.getReceptaByNom(nomRecepta);
        TV_Sug.setText(recepta.getSuggeriments());
        return rootView;
    }
}
