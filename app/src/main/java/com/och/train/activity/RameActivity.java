package com.och.train.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.och.train.R;
import com.och.train.adapter.MaterielInRameAdapter;
import com.och.train.listener.MaterielInRameListener;
import com.och.train.listener.MyDragListener;
import com.och.train.model.Categorie;
import com.och.train.model.CompositionRame;
import com.och.train.model.Destination;
import com.och.train.model.DestinationMaterielRame;
import com.och.train.model.Materiel;
import com.och.train.model.Rame;
import com.och.train.service.DestinationService;
import com.och.train.service.MaterielService;

import java.util.ArrayList;
import java.util.List;

public class RameActivity extends AppCompatActivity implements MyDialogInterface.DialogReturn, MaterielInRameListener {

    private EditText etDescription;
    private TextView tvComposition;
    private ListView listViewMateriel, listViewComposition;
    private MyDialogInterface myDialogInterface;

    private Rame rame = null;
    private Integer longeur = 0;
    private List<DestinationMaterielRame> rameDestsList = new ArrayList<>();
    private List<Materiel> catalogue = MaterielService.getAll();
    private List<Materiel> composition = new ArrayList<>();
    private Materiel currentMateriel = null;
    private Destination currentDestination = null;
    private MaterielInRameAdapter adapterComposition, adapterMateriel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rame);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDialogInterface = new MyDialogInterface();
        myDialogInterface.setListener(this);

        etDescription = (EditText) findViewById(R.id.edDescription);
        tvComposition = (TextView) findViewById(R.id.tvComposition);
        listViewMateriel = (ListView) findViewById(R.id.listMateriel);
        listViewComposition = (ListView) findViewById(R.id.listComposition);

        initView();

        adapterComposition= new MaterielInRameAdapter(this, composition, true, MaterielInRameAdapter.ORIGINE_COMPOSITION, true, null, rameDestsList);
        adapterComposition.addListener(this);
        adapterMateriel= new MaterielInRameAdapter(this, catalogue, false, MaterielInRameAdapter.ORIGINE_CATALOGUE, true, null, null);

        listViewComposition.setAdapter(adapterComposition);
        listViewMateriel.setAdapter(adapterMateriel);

        listViewMateriel.setOnDragListener(new MyDragListener(adapterMateriel, adapterComposition, this));
        listViewComposition.setOnDragListener(new MyDragListener(adapterComposition, adapterMateriel, this));

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
                        longeur = longeur + materiel.getLongueur();
                        rameDestsList.addAll(DestinationService.getByRameMateriel(rame, materiel));

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
        if (rame == null)  {
            rame = new Rame();
        }
        tvComposition.setText(getString(R.string.rame_composition) + " (" + longeur + " cm)");
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
            try {
                ActiveAndroid.beginTransaction();
                for (CompositionRame compo : rame.materiels()) {
                    DestinationService.deleteDestinationByRameMateriel(rame, compo.getMateriel());
                    compo.delete();
                }
                rame.delete();
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
            Toast.makeText(getBaseContext(), getString(R.string.rame_delete), Toast.LENGTH_LONG).show();
            Intent listActivity = new Intent(getApplicationContext(), RamesActivity.class);
            startActivity(listActivity);
            finish();
        } else if ("ADD_DEST".equals(type) && answer) {
            addDestToRame();
            adapterComposition.notifyDataSetChanged();
        } else if ("DELETE_DEST".equals(type) && answer) {
            deleteDestToRame();
            adapterComposition.notifyDataSetChanged();
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
                if (rame.getId()!=null) {
                    for (CompositionRame compo : rame.materiels()) {
                        compo.delete();
                    }
                }
                rame.setDescription(edDescription.toString());
                rame.save();

                for (Materiel materiel:composition) {
                  CompositionRame compo = new CompositionRame(rame, materiel);
                  compo.save();
                  DestinationService.deleteDestinationByRameMateriel(rame, materiel);
                }
                for (DestinationMaterielRame destination:rameDestsList) {
                    DestinationMaterielRame newDest = new DestinationMaterielRame(rame, destination.getMateriel(), destination.getDestination());
                    newDest.save();
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

    @Override
    public void onDeleteDest(Materiel materiel, Destination destination, int position) {
        currentMateriel = materiel;
        currentDestination = destination;
        if (rame != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle("Supprimer la destination " + destination.getDestination() + " ?");
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

    @Override
    public void onClickAddDest(Materiel materiel, int position) {
        currentMateriel = materiel;
        currentDestination = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        //builder.setIcon(R.drawable.victoire);
        builder.setTitle("Destination pour " + materiel.getDescription());

        final List<Destination> destList = DestinationService.getAll();
        final String[] dests = new String[destList.size()];
        for (int i = 0; i < destList.size(); i++) {
            dests[i] = destList.get(i).getDestination();
        }
        builder.setSingleChoiceItems(dests, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String destDsecription = dests[which];
                        for (Destination d : destList) {
                            if (d.getDestination().equals(destDsecription)) {
                                currentDestination = d;
                                break;
                            }
                        }
                    }
                });

        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(R.string.action_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDialogInterface.getListener().onDialogCompleted(true, "ADD_DEST");
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.action_fermer, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDialogInterface.getListener().onDialogCompleted(false, "CANCEL_DEST");
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void addDestToRame() {
        if (currentMateriel != null & currentDestination != null) {
            DestinationMaterielRame destinationMaterielRame = new DestinationMaterielRame(rame, currentMateriel, currentDestination);
            rameDestsList.add(destinationMaterielRame);
        }
    }

    private void deleteDestToRame() {
        if (currentMateriel != null & currentDestination != null) {
            for (DestinationMaterielRame rameDest:rameDestsList) {
                if (rameDest.getMateriel().equals(currentMateriel) && rameDest.getDestination().equals(currentDestination)) {
                    rameDestsList.remove(rameDest);
                    break;
                }
            }
        }
    }

    @Override
    public void onChangeMateriel(Materiel materiel) {
        // TODO
        longeur = 0;
        for (Materiel item:composition) {
            longeur = longeur + item.getLongueur();
        }
        tvComposition.setText(getString(R.string.rame_composition) + " (" + longeur + " cm)");
    }

}
