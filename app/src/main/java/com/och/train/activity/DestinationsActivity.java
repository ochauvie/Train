package com.och.train.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.och.train.R;
import com.och.train.adapter.DestinationAdapter;
import com.och.train.listener.DestinationListener;
import com.och.train.model.Destination;
import com.och.train.service.DestinationService;

import java.util.List;

public class DestinationsActivity extends AppCompatActivity implements MyDialogInterface.DialogReturn, DestinationListener {

    private ListView listView;
    List<Destination> destList;
    private DestinationAdapter destAdapter;
    private ActionBar actionBar;
    private MyDialogInterface myDialogInterface;
    private Destination currentDest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        myDialogInterface = new MyDialogInterface();
        myDialogInterface.setListener(this);

        destList = DestinationService.getAll();
        listView = (ListView)findViewById(android.R.id.list);

        // Creation et initialisation de l'Adapter
        destAdapter = new DestinationAdapter(this, destList, null, null);
        destAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        listView.setAdapter(destAdapter);
    }

    @Override
    public void onClick(Destination item, int position) {
        Intent myIntent = new Intent(getApplicationContext(), DestinationActivity.class);
        myIntent.putExtra(Destination.ID_DESTINATION, item.getId());
        startActivityForResult(myIntent, 0);
        finish();
    }

    @Override
    public void onDelete(Destination destination, int position) {
        if (destination != null) {

            if (DestinationService.isUsedInRame(destination)) {
                Toast.makeText(getBaseContext(), getString(R.string.destination_used_in_rame), Toast.LENGTH_LONG).show();
            } else{
                currentDest = destination;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setIcon(R.drawable.delete);
                builder.setTitle(destination.getDestination());
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDialogInterface.getListener().onDialogCompleted(true, "DELETE_DEST");
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDialogInterface.getListener().onDialogCompleted(false, "DELETE_DEST");
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_destinations, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dest:
                Intent myIntent = new Intent(getApplicationContext(), DestinationActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
                return true;

            case R.id.action_close_dests:
                finish();
                return true;

        }

        return false;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if ("DELETE_DEST".equals(type) && answer) {
            destList.remove(currentDest);
            currentDest.delete();
            destAdapter.notifyDataSetChanged();
        }
    }

}
