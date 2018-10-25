package com.och.train.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.och.train.R;
import com.och.train.model.Destination;

public class DestinationActivity extends AppCompatActivity {

    private EditText etDestination;
    private ActionBar actionBar;
    private Destination destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        etDestination = (EditText) findViewById(R.id.edDestination);

        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long destinationId = bundle.getLong(Destination.ID_DESTINATION);
            destination = Destination.load(Destination.class, destinationId);
            if (destination != null) {
                etDestination.setText(destination.getDestination());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_destination, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dest:
                if (onSave()) {
                    Intent listActivity = new Intent(getApplicationContext(), DestinationsActivity.class);
                    startActivity(listActivity);
                    finish();
                    return true;
                }
                return false;

            case R.id.action_close_dest:
                Intent listActivity = new Intent(getApplicationContext(), DestinationsActivity.class);
                startActivity(listActivity);
                finish();
                return true;
        }
        return false;
    }

    private boolean onSave() {
        Editable edDestination = etDestination.getText();
        if (edDestination==null || "".equals(edDestination.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.mandatory_destination), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (destination == null) {
                destination = new Destination();
            }
            destination.setDestination(edDestination.toString());
            destination.save();
            Toast.makeText(getBaseContext(), getString(R.string.destination_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
