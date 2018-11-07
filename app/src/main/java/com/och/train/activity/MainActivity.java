package com.och.train.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.och.train.model.Plan;
import com.och.train.model.Rame;


public class MainActivity extends AppCompatActivity  implements MyDialogInterface.DialogReturn {

    private MyDialogInterface myDialogInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ouvre la BD
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(Materiel.class, Rame.class, CompositionRame.class, Destination.class, DestinationMaterielRame.class, Plan.class);
        ActiveAndroid.initialize(config.create());

        setContentView(R.layout.activity_main);

        myDialogInterface = new MyDialogInterface();
        myDialogInterface.setListener(this);

        // Materiels
        Button but1 = findViewById(R.id.buttonMateriels);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), MaterielsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        // Rames
        Button but2 = findViewById(R.id.buttonRames);
        but2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), RamesActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        // Destinations
        Button but3 = findViewById(R.id.buttonDest);
        but3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), PlanActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_init_bdd:
                onInitDb();
                return true;
            case R.id.action_close_main:
                finish();
                return true;
        }
        return false;
    }

    private void onInitDb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.delete);
        builder.setTitle("Initialisation de la base de données");
        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDialogInterface.getListener().onDialogCompleted(true, "INIT_BD");
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDialogInterface.getListener().onDialogCompleted(false, "INIT_BD");
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if ("INIT_BD".equals(type) && answer) {
            InitData.deleteAll();
            InitData.initMateriels();
            InitData.initRames();
            InitData.initDestinations();
        }
    }
}
