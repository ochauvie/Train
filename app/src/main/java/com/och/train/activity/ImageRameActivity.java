package com.och.train.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.och.train.R;
import com.och.train.model.CompositionRame;
import com.och.train.model.Rame;
import com.och.train.tools.PictureUtils;
import com.och.train.tools.Utils;

public class ImageRameActivity extends AppCompatActivity {

    private Rame rame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_rame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout layout = findViewById(R.id.imgs);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long rameId = bundle.getLong(Rame.ID_RAME);
            rame = Rame.load(Rame.class, rameId);

            if (rame.materiels() != null) {
                setTitle(rame.getDescription());
                for (CompositionRame compo:rame.materiels()) {
                    byte[] photo = compo.getMateriel().getPhoto();
                    ImageView img = new ImageView(this);
                    img.setId(ViewCompat.generateViewId());
                    img.setLayoutParams(new RelativeLayout.LayoutParams(Utils.dpToPx(this, 128), Utils.dpToPx(this, 128)));
                    if (photo != null) {
                        img.setImageBitmap(PictureUtils.getImage(photo));
                    } else {
                        img.setImageDrawable(ContextCompat.getDrawable(ImageRameActivity.this, R.drawable.compo));
                    }
                    layout.addView(img);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_rame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close_image_rame:
                finish();
                return true;
        }
        return false;
    }

}
