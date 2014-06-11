package com.arcaexterna.Cellarhq;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tim on 6/4/14.
 */
public class CellarActivity extends Activity {

    final List<Beer> beers = new ArrayList<Beer>();
    ListView cellarList;
    SimpleAdapter cellarAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cellar);

        cellarList = (ListView) findViewById(R.id.cellarList);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("beerDetail");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                BeerDetailFragment beerDetail = new BeerDetailFragment(beers.get(position));
                beerDetail.show(ft, "beerDetail");
            }
        };

        cellarList.setOnItemClickListener(clickListener);

        getBeersInCellar();
    }

    private List<HashMap<String, String>> beersForView(List<Beer> beers){
        List<HashMap<String, String>> viewData = new ArrayList<HashMap<String, String>>();

        for (Beer beer : beers) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("line1", beer.name);
            map.put("line2", beer.brewery);
            viewData.add(map);
        }

        return viewData;
    }

    private void getBeersInCellar() {

        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... urls) {
                try {
                    Document doc = Jsoup.connect(urls[0]).get();
                    Element table = doc.getElementById("cellar-listing").child(1);
                    Elements tableRows = table.getElementsByTag("tr");

                    for (Element tableRow : tableRows) {
                        String brewery = tableRow.getElementsByAttributeValue("class", "brewery").get(0).getElementsByTag("a").get(0).text();
                        String beerName = tableRow.getElementsByAttributeValue("class", "beer").get(0).getElementsByTag("a").get(0).text();
                        Beer beer = new Beer();
                        beer.brewery = brewery;
                        beer.name = beerName;
                        beers.add(beer);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                cellarLoaded();
            }
        };
        task.execute("http://www.cellarhq.com/cellar/hansmoleman");
    }

    private void cellarLoaded() {
        cellarAdapter = new SimpleAdapter(this,
                beersForView(beers),
                android.R.layout.two_line_list_item,
                new String[]{"line1", "line2"},
                new int[]{android.R.id.text1, android.R.id.text2});
        cellarList.setAdapter(cellarAdapter);
    }
}