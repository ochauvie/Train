package com.och.train.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.och.train.R;
import com.och.train.data.InitData;
import com.och.train.model.CompositionRame;
import com.och.train.model.Destination;
import com.och.train.model.DestinationMaterielRame;
import com.och.train.model.Materiel;
import com.och.train.model.Rame;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ouvre la BD
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(Materiel.class, Rame.class, CompositionRame.class, Destination.class, DestinationMaterielRame.class);
        ActiveAndroid.initialize(config.create());

        setContentView(R.layout.activity_main);

        // Initialisation BDMateriels
        Button but0 = (Button) findViewById(R.id.buttonInitData);
        but0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onInitDb();
            }
        });
        //but0.setVisibility(View.INVISIBLE);

        // Materiels
        Button but1 = (Button) findViewById(R.id.buttonMateriels);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), MaterielsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        // Rames
        Button but2 = (Button) findViewById(R.id.buttonRames);
        but2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), RamesActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }
    private void onInitDb() {
        InitData.initMateriels();
        InitData.initRames();
        InitData.initDestinations();
    }
}
