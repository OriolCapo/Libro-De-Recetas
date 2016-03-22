package com.example.android.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myapplication.data.Recepta;
import com.example.android.myapplication.data.ReceptesDAO;

/**
 * Created by Oriolcapo on 22/01/2016.
 */
public class ReceptaFotosFragment extends android.support.v4.app.Fragment {

    private String nomRecepta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fotos_recepta, container,
                false);

        Bundle args = this.getArguments();
        nomRecepta = args.getString("Recepta");
        /*TV_Descr = (TextView) rootView.findViewById(R.id.TV_Descr);
        ReceptesDAO rDAO = new ReceptesDAO(rootView.getContext());
        Recepta recepta = rDAO.getReceptaByNom(nomRecepta);
        TV_Descr.setText(recepta.getDescripcio());*/

        return rootView;
    }

}
