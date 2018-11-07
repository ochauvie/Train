package com.och.train.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.adapter.EnRouteAdapter;
import com.och.train.listener.EnRouteListener;
import com.och.train.model.CompositionRame;
import com.och.train.model.Destination;
import com.och.train.model.DestinationMaterielRame;
import com.och.train.model.Materiel;
import com.och.train.model.Rame;
import com.och.train.service.DestinationService;
import com.och.train.tools.PictureUtils;

import java.util.ArrayList;
import java.util.List;

public class EnRouteActivity extends AppCompatActivity implements EnRouteListener {

    private List<CompositionRame> compoList = new ArrayList<>();
    private List<DestinationMaterielRame> destList = new ArrayList<>();
    private EnRouteAdapter enRouteAdapter;
    private PopupWindow popupWindow;
    private ListView lvEnRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_route);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar !=null) {
            actionBar.setDisplayShowTitleEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long rameId = bundle.getLong(Rame.ID_RAME);
            Rame rame = Rame.load(Rame.class, rameId);
            compoList = rame.materiels();
            destList = DestinationService.getByRame(rame);
            actionBar.setTitle(rame.description);
        }

        lvEnRoute = findViewById(R.id.lvEnRoute);

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

    @Override
    public void onPopupMateriel(Materiel materiel) {
        if (popupWindow!=null) {
            popupWindow.dismiss();
        }
        LayoutInflater layoutInflater = (LayoutInflater) EnRouteActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup_destination,null);

        ImageButton buClose = customView.findViewById(R.id.buClose);
        TextView txDestination = customView.findViewById(R.id.txDestination);
        ImageView ivDestination = customView.findViewById(R.id.ivDestination);
        txDestination.setText(materiel.getDescription());
        if (materiel.getPhoto() != null) {
            ivDestination.setImageBitmap(PictureUtils.getImage(materiel.getPhoto()));
        }

        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(lvEnRoute, Gravity.CENTER, 0, 0);

        buClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onPopupDestinaion(DestinationMaterielRame destinationMaterielRame) {
        if (popupWindow!=null) {
            popupWindow.dismiss();
        }
        LayoutInflater layoutInflater = (LayoutInflater) EnRouteActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup_destination,null);

        ImageButton buClose = customView.findViewById(R.id.buClose);
        TextView txDestination = customView.findViewById(R.id.txDestination);
        ImageView ivDestination = customView.findViewById(R.id.ivDestination);
        txDestination.setText(destinationMaterielRame.getDestination().getDestination());
        if (destinationMaterielRame.getDestination().getPhoto() != null) {
            ivDestination.setImageBitmap(PictureUtils.getImage(destinationMaterielRame.getDestination().getPhoto()));
        }

        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(lvEnRoute, Gravity.CENTER, 0, 0);

        buClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_enroute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close_enroute:
                Intent listActivity = new Intent(getApplicationContext(), RamesActivity.class);
                startActivity(listActivity);
                finish();
                return true;
        }
        return false;
    }
}
