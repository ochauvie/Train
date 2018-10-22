package com.och.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.och.train.R;
import com.och.train.adapter.RameAdapter;
import com.och.train.listener.RameListener;
import com.och.train.model.Rame;
import com.och.train.service.RameService;

import java.util.List;

public class RamesActivity extends AppCompatActivity implements RameListener {

    private ListView listView;
    List<Rame> rameList;
    private RameAdapter rameAdapter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rames);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        rameList = RameService.getAll();
        listView = (ListView)findViewById(android.R.id.list);

        // Creation et initialisation de l'Adapter
        rameAdapter = new RameAdapter(this, rameList);
        rameAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        listView.setAdapter(rameAdapter);
    }

    @Override
    public void onClick(Rame item, int position) {
        Intent myIntent = new Intent(getApplicationContext(), RameActivity.class);
        myIntent.putExtra(Rame.ID_RAME, item.getId());
        startActivityForResult(myIntent, 0);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rames, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_rame:
                Intent myIntent = new Intent(getApplicationContext(), RameActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
                return true;

            case R.id.action_close_rames:
                finish();
                return true;

        }

        return false;
    }
}
