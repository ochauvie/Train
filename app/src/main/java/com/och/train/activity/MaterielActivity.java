package com.och.train.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.och.train.R;
import com.och.train.adapter.IDataSpinnerAdapter;
import com.och.train.model.Categorie;
import com.och.train.model.Echelle;
import com.och.train.model.Epoque;
import com.och.train.model.IRefData;
import com.och.train.model.Marque;
import com.och.train.model.Materiel;
import com.och.train.model.Propulsion;
import com.och.train.service.RameService;
import com.och.train.tools.PictureUtils;
import com.och.train.tools.SpinnerTool;
import com.och.train.tools.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MaterielActivity extends AppCompatActivity implements MyDialogInterface.DialogReturn {

    private static final int REQUEST_PHOTO = 1;
    private Spinner spCategorie, spEchelle, spEpoque, spPropulsion, spMarque;
    private EditText etDescription, etCompagnie, etReference, etLongueur, etQuantite;
    private ImageView idPhoto;
    private Materiel materiel = null;
    private MyDialogInterface myDialogInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etDescription = findViewById(R.id.etDescription);
        etCompagnie = findViewById(R.id.etCompagnie);
        etReference = findViewById(R.id.etReference);
        etLongueur = findViewById(R.id.etLongueur);
        etQuantite = findViewById(R.id.etQuantite);

        spCategorie = findViewById(R.id.spCategorie);
        spEchelle = findViewById(R.id.spEchelle);
        spEpoque = findViewById(R.id.spEpoque);
        spPropulsion = findViewById(R.id.spPropulsion);
        spMarque = findViewById(R.id.spMarque);
        loadSpinners();

        idPhoto = findViewById(R.id.idPhoto);

        myDialogInterface = new MyDialogInterface();
        myDialogInterface.setListener(this);
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            long materielId = bundle.getLong(Materiel.ID_MATERIEL);
            materiel = Materiel.load(Materiel.class, materielId);
            if (materiel != null) {
                etDescription.setText(materiel.getDescription());
                etCompagnie.setText(materiel.getCompanie());
                etReference.setText(materiel.getReference());
                etLongueur.setText(String.valueOf(materiel.getLongueur()));
                etQuantite.setText(String.valueOf(materiel.getQuantite()));
                SpinnerTool.SelectSpinnerItemByValue(spCategorie, materiel.getCategorie().name());
                SpinnerTool.SelectSpinnerItemByValue(spEchelle, materiel.getEchelle().name());
                SpinnerTool.SelectSpinnerItemByValue(spEpoque, materiel.getEpoque().name());
                SpinnerTool.SelectSpinnerItemByValue(spPropulsion, materiel.getPropulsion().name());
                SpinnerTool.SelectSpinnerItemByValue(spMarque, materiel.getMarque().name());
                if (materiel.getPhoto() != null) {
                    idPhoto.setImageBitmap(PictureUtils.getImage(materiel.getPhoto()));
                }
            }
        }
    }

    private boolean onSave() {
        Editable edDescription = etDescription.getText();
        Editable edCompagnie = etCompagnie.getText();
        Editable edReference = etReference.getText();
        if (edDescription==null || "".equals(edDescription.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.mandatory_description), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (materiel == null) {
                materiel = new Materiel();
            }
            materiel.setCategorie((Categorie) spCategorie.getSelectedItem());
            materiel.setEpoque((Epoque) spEpoque.getSelectedItem());
            materiel.setEchelle((Echelle) spEchelle.getSelectedItem());
            materiel.setMarque((Marque) spMarque.getSelectedItem());
            materiel.setPropulsion((Propulsion) spPropulsion.getSelectedItem());
            materiel.setDescription(edDescription.toString());
            materiel.setCompanie(edCompagnie.toString());
            materiel.setReference(edReference.toString());
            try {
                materiel.setLongueur(Integer.valueOf(etLongueur.getText().toString()));
            } catch (NumberFormatException e) {
                materiel.setLongueur(0);
            }
            try {
                materiel.setQuantite(Integer.valueOf(etQuantite.getText().toString()));
            } catch (NumberFormatException e) {
                materiel.setQuantite(1);
            }
            idPhoto.buildDrawingCache();
            Bitmap imageBitmap = idPhoto.getDrawingCache();
            if (imageBitmap != null) {
                materiel.setPhoto(PictureUtils.getBitmapAsByteArray(imageBitmap));
            }
            materiel.save();
            Toast.makeText(getBaseContext(), getString(R.string.materiel_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_materiel, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemD = menu.findItem(R.id.action_delete_materiel);
        MenuItem itemS = menu.findItem(R.id.action_add_materiel);
        if (materiel != null) {
            Utils.enableItem(itemD);
            Utils.enableItem(itemS);
        } else {
            Utils.disableItem(itemD);
            Utils.enableItem(itemS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_materiel:
                if (onSave()) {
                    Intent listActivity = new Intent(getApplicationContext(), MaterielsActivity.class);
                    startActivity(listActivity);
                    finish();
                    return true;
                }
                return false;

                case R.id.action_pick_photo:
                selectPhoto();
                return true;

            case R.id.action_close_materiel:
                Intent listActivity = new Intent(getApplicationContext(), MaterielsActivity.class);
                startActivity(listActivity);
                finish();
                return true;

            case R.id.action_delete_materiel:
                onDelete();
                return false;
        }
        return false;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if ("materiel".equals(type) && answer && materiel!=null) {
            materiel.delete();
            Toast.makeText(getBaseContext(), getString(R.string.materiel_delete), Toast.LENGTH_LONG).show();
            Intent listActivity = new Intent(getApplicationContext(), MaterielsActivity.class);
            startActivity(listActivity);
            finish();
        }
    }

    private void onDelete() {
        if (materiel != null) {
            if (RameService.isUsedInRame(materiel)) {
                Toast.makeText(getBaseContext(), getString(R.string.materiel_used_in_rame), Toast.LENGTH_LONG).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setIcon(R.drawable.delete);
                builder.setTitle(materiel.getDescription());
                builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDialogInterface.getListener().onDialogCompleted(true, "materiel");
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDialogInterface.getListener().onDialogCompleted(false, "materiel");
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    private void loadSpinners() {
        ArrayList<IRefData> list = new ArrayList<>();
        Collections.addAll(list, Categorie.values());
        spCategorie.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.spinner));

        list = new ArrayList<>();
        Collections.addAll(list, Echelle.values());
        spEchelle.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.spinner));

        list = new ArrayList<>();
        Collections.addAll(list, Epoque.values());
        spEpoque.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.spinner));

        list = new ArrayList<>();
        Collections.addAll(list, Propulsion.values());
        spPropulsion.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.spinner));

        list = new ArrayList<>();
        Collections.addAll(list, Marque.values());
        spMarque.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.spinner));
    }

    private void selectPhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImage = PictureUtils.rotateImageIfRequired(selectedImage, MaterielActivity.this, imageUri);
                Drawable drawable = new BitmapDrawable(MaterielActivity.this.getResources(), selectedImage);
                idPhoto.setImageDrawable(drawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MaterielActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MaterielActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

}
