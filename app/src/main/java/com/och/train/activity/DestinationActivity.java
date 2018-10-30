package com.och.train.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.och.train.R;
import com.och.train.model.Destination;
import com.och.train.tools.PictureUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DestinationActivity extends AppCompatActivity {

    private static final int REQUEST_PHOTO = 1;
    private EditText etDestination, etLongeur;
    private ImageView idPhoto;
    private Destination destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etDestination = findViewById(R.id.edDestination);
        etLongeur = findViewById(R.id.etLongeur);
        idPhoto = findViewById(R.id.idPhoto);

        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long destinationId = bundle.getLong(Destination.ID_DESTINATION);
            destination = Destination.load(Destination.class, destinationId);
            if (destination != null) {
                etDestination.setText(destination.getDestination());
                etLongeur.setText(String.valueOf(destination.getLongueur()));
                if (destination.getPhoto() != null) {
                    idPhoto.setImageBitmap(PictureUtils.getImage(destination.getPhoto()));
                }
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
            case R.id.action_pick_photo:
                selectPhoto();
                return true;
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
        Editable edLongueur = etLongeur.getText();
        if (edDestination==null || "".equals(edDestination.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.mandatory_destination), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (destination == null) {
                destination = new Destination();
            }
            destination.setDestination(edDestination.toString());
            destination.setLongueur(Integer.valueOf(edLongueur.toString()));

            idPhoto.buildDrawingCache();
            Bitmap imageBitmap = idPhoto.getDrawingCache();
            if (imageBitmap != null) {
                destination.setPhoto(PictureUtils.getBitmapAsByteArray(imageBitmap));
            }

            destination.save();
            Toast.makeText(getBaseContext(), getString(R.string.destination_save), Toast.LENGTH_LONG).show();
        }
        return true;
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
                selectedImage = PictureUtils.rotateImageIfRequired(selectedImage, DestinationActivity.this, imageUri);
                Drawable drawable = new BitmapDrawable(DestinationActivity.this.getResources(), selectedImage);
                idPhoto.setImageDrawable(drawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(DestinationActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(DestinationActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
}
