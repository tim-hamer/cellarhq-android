package com.arcaexterna.Cellarhq;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by tim on 6/11/14.
 */
public class BeerDetailFragment extends DialogFragment {

    private Beer beer;

    public BeerDetailFragment(Beer beer) {
        this.beer = beer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beer_detail, container, false);

        EditText breweryField = (EditText)view.findViewById(R.id.breweryField);
        breweryField.setText(beer.brewery);

        EditText beerField = (EditText)view.findViewById(R.id.beerField);
        beerField.setText(beer.name);

        return view;
    }
}
