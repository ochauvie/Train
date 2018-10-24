package com.och.train.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.och.train.R;
import com.och.train.adapter.EnRouteAdapter;
import com.och.train.listener.EnRouteListener;
import com.och.train.model.CompositionRame;
import com.och.train.model.DestinationMaterielRame;
import com.och.train.model.Rame;
import com.och.train.service.DestinationService;
import com.och.train.service.RameService;

import java.util.ArrayList;
import java.util.List;

public class EnRouteActivity extends AppCompatActivity implements EnRouteListener {


    private Rame rame;
    private List<CompositionRame> compoList = new ArrayList<>();
    private List<DestinationMaterielRame> destList = new ArrayList<>();
    private ListView lvEnRoute;
    private EnRouteAdapter enRouteAdapter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long rameId = bundle.getLong(Rame.ID_RAME);
            rame = Rame.load(Rame.class, rameId);
            compoList = rame.materiels();
            destList = DestinationService.getByRame(rame);
            actionBar.setTitle(rame.description);
        }

        lvEnRoute = (ListView)findViewById(R.id.lvEnRoute);

        // Creation et initialisation de l'Adapter
        enRouteAdapter = new EnRouteAdapter(this, compoList, destList);
        enRouteAdapter.addListener(this);
        lvEnRoute.setAdapter(enRouteAdapter);

    }

    @Override
    public void onEnRoute(CompositionRame compositionRame, boolean isChecked) {
        // Materiel attelé
        compositionRame.setMaterielDansRame(isChecked);
        compositionRame.save();
        enRouteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestAtteinte(DestinationMaterielRame destinationMaterielRame, boolean isChecked) {
        // Destination atteinte
        destinationMaterielRame.setDestinationAtteinte(isChecked);
        destinationMaterielRame.save();
        enRouteAdapter.notifyDataSetChanged();
    }
}
