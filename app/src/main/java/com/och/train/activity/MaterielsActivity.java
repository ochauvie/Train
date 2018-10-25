package com.och.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.och.train.R;
import com.och.train.adapter.MaterialAdapter;
import com.och.train.listener.MaterielListener;
import com.och.train.model.Materiel;
import com.och.train.service.MaterielService;

import java.util.List;

public class MaterielsActivity extends AppCompatActivity implements MaterielListener {

    private ListView listView;
    List<Materiel> materielList;
    private MaterialAdapter materialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiels);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materielList = MaterielService.getAll();
        listView = (ListView)findViewById(android.R.id.list);

        // Creation et initialisation de l'Adapter
        materialAdapter = new MaterialAdapter(this, materielList);
        materialAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        listView.setAdapter(materialAdapter);
    }

    @Override
    public void onClick(Materiel item, int position) {
        Intent myIntent = new Intent(getApplicationContext(), MaterielActivity.class);
        myIntent.putExtra(Materiel.ID_MATERIEL, item.getId());
        startActivityForResult(myIntent, 0);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_materiels, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_materiel:
                Intent myIntent = new Intent(getApplicationContext(), MaterielActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
                return true;

            case R.id.action_close_materiels:
                finish();
                return true;
       }
        return false;
    }
}
