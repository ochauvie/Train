package com.och.train.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.och.train.R;
import com.och.train.adapter.MaterielInRameAdapter;
import com.och.train.listener.MyDragListener;
import com.och.train.model.Categorie;
import com.och.train.model.CompositionRame;
import com.och.train.model.Materiel;
import com.och.train.model.Rame;
import com.och.train.service.MaterielService;

import java.util.ArrayList;
import java.util.List;

public class RameActivity extends AppCompatActivity implements MyDialogInterface.DialogReturn{

    private EditText etDescription;
    private ListView listViewMateriel, listViewComposition;
    private MyDialogInterface myDialogInterface;
    private ActionBar actionBar;

    private Rame rame = null;
    private List<Materiel> catalogue = MaterielService.getAll();
    private List<Materiel> composition = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rame);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        myDialogInterface = new MyDialogInterface();
        myDialogInterface.setListener(this);

        etDescription = (EditText) findViewById(R.id.edDescription);
        listViewMateriel = (ListView) findViewById(R.id.listMateriel);
        listViewComposition = (ListView) findViewById(R.id.listComposition);

        initView();

        MaterielInRameAdapter adapterComposition= new MaterielInRameAdapter(this, composition, true, MaterielInRameAdapter.ORIGINE_COMPOSITION, true, null);
        MaterielInRameAdapter adapterMateriel= new MaterielInRameAdapter(this, catalogue, false, MaterielInRameAdapter.ORIGINE_CATALOGUE, true, null);

        listViewComposition.setAdapter(adapterComposition);
        listViewMateriel.setAdapter(adapterMateriel);

        listViewComposition.setOnDragListener(new MyDragListener(adapterComposition, adapterMateriel));
        listViewMateriel.setOnDragListener(new MyDragListener(adapterMateriel, adapterComposition));

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long rameId = bundle.getLong(Rame.ID_RAME);
            rame = Rame.load(Rame.class, rameId);
            if (rame != null) {
                etDescription.setText(rame.getDescription());
                if (rame.materiels() != null) {
                    List<CompositionRame> currentCompo = rame.materiels();
                    for (CompositionRame compo : currentCompo) {
                        Materiel materiel = compo.getMateriel();
                        composition.add(materiel);

                        // Supprime du catalogue
                        for (Materiel item : catalogue) {
                            if (item.getId().equals(materiel.getId())) {
                                catalogue.remove(item);
                                break;
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_rame:
                if (onSave()) {
                    Intent listActivity = new Intent(getApplicationContext(), RamesActivity.class);
                    startActivity(listActivity);
                    finish();
                    return true;
                }
                return false;

            case R.id.action_close_rame:
                Intent listActivity = new Intent(getApplicationContext(), RamesActivity.class);
                startActivity(listActivity);
                finish();
                return true;

            case R.id.action_delete_rame:
                onDelete();
                return false;
        }

        return false;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if ("rame".equals(type) && answer && rame!=null) {
            for (CompositionRame compo : rame.materiels()) {
                compo.delete();
            }
            rame.delete();
            Toast.makeText(getBaseContext(), getString(R.string.rame_delete), Toast.LENGTH_LONG).show();
            Intent listActivity = new Intent(getApplicationContext(), RamesActivity.class);
            startActivity(listActivity);
            finish();
        }
    }

    private void onDelete() {
        if (rame != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(rame.getDescription());
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myDialogInterface.getListener().onDialogCompleted(true, "rame");
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myDialogInterface.getListener().onDialogCompleted(false, "rame");
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private boolean onSave() {
        Editable edDescription = etDescription.getText();
        boolean hasLoco = false;
        for (Materiel materiel:composition) {
            if (Categorie.LOCO.equals(materiel.getCategorie())) {
                hasLoco= true;
                break;
            }
        }
        if (edDescription==null || "".equals(edDescription.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.mandatory_description), Toast.LENGTH_LONG).show();
            return false;
        } else if (!hasLoco) {
            Toast.makeText(getBaseContext(), getString(R.string.mandatory_loco), Toast.LENGTH_LONG).show();
            return false;
        } else {
            try {
                ActiveAndroid.beginTransaction();
                if (rame == null) {
                    rame = new Rame();
                } else {
                    for (CompositionRame compo : rame.materiels()) {
                        compo.delete();
                    }
                }
                rame.setDescription(edDescription.toString());
                rame.save();
              for (Materiel materiel:composition) {
                  CompositionRame compo = new CompositionRame(rame, materiel);
                  compo.save();
                }
                ActiveAndroid.setTransactionSuccessful();
                Toast.makeText(getBaseContext(), getString(R.string.rame_save), Toast.LENGTH_LONG).show();
            }

            finally {
                ActiveAndroid.endTransaction();
            }
        }
        return true;

    }
}
