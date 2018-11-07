package com.och.train.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.och.train.R;
import com.och.train.listener.MyTouchListener;
import com.och.train.listener.PlanDragListener;
import com.och.train.listener.PlanListener;
import com.och.train.model.Destination;
import com.och.train.model.Plan;
import com.och.train.service.DestinationService;
import com.och.train.tools.PictureUtils;
import com.och.train.tools.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PlanActivity extends AppCompatActivity implements PlanListener {

    private static final int REQUEST_PLAN_SELECT = 1;
    private ConstraintLayout layout;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout = findViewById(R.id.cvDests);
        layout.setOnDragListener(new PlanDragListener(this));

        Plan plan = DestinationService.getPlan();
        if (plan != null) {
            layout.setBackground(new BitmapDrawable(PlanActivity.this.getResources(), PictureUtils.getImage(plan.getReseau())));
        }

        List<Destination> dests = DestinationService.getAll();
        if (dests != null) {
            for (Destination dest:dests) {
                RelativeLayout reLayout = new RelativeLayout(this);
                reLayout.setTag(dest);
                reLayout.setId(ViewCompat.generateViewId());

                    ImageView imgDest = new ImageView(this);
                    imgDest.setId(ViewCompat.generateViewId());
                    imgDest.setImageResource(R.drawable.destination);
                    imgDest.setTag(dest);
                    imgDest.setLayoutParams(new RelativeLayout.LayoutParams(Utils.dpToPx(this, 24), Utils.dpToPx(this, 24)));

                    TextView txView = new TextView(this);
                    txView.setText(dest.getDestination());
                    txView.setId(ViewCompat.generateViewId());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 100, 0, 0);
                    txView.setLayoutParams(params);

                reLayout.addView(imgDest);
                reLayout.addView(txView);

                layout.addView(reLayout,0);
                ConstraintSet set = new ConstraintSet();
                set.clone(layout);
                set.connect(reLayout.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, dest.getY());
                set.connect(reLayout.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT, dest.getX());
                set.applyTo(layout);

                reLayout.setOnTouchListener(new MyTouchListener());

                imgDest.setTag(dest);
                imgDest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickDestination(v);
                    }
                });
            }
        }
    }

    @Override
    public void onMoveDestination(View view, float X, float Y) {
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);
        set.connect(view.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, Math.round(Y));
        set.connect(view.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT, Math.round(X));
        set.applyTo(layout);

        Destination dest = (Destination) view.getTag();
        dest.setX(Math.round(X));
        dest.setY(Math.round(Y));
        dest.save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close_plan:
                finish();
                return true;
            case R.id.action_destinations:
                Intent myIntent = new Intent(getApplicationContext(), DestinationsActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
                return true;
            case R.id.action_pick_reseau:
                selectPlanPicture();
                return true;
        }
        return false;
    }

    private void selectPlanPicture() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_PLAN_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLAN_SELECT && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImage = PictureUtils.rotateImageIfRequired(selectedImage, PlanActivity.this, imageUri);
                Drawable drawable = new BitmapDrawable(PlanActivity.this.getResources(), selectedImage);
                layout.setBackground(drawable);
                DestinationService.deleteAllPlans();
                Plan plan = new Plan(PictureUtils.getBitmapAsByteArray(selectedImage));
                plan.save();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PlanActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(PlanActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onClickDestination(View v) {
        if (popupWindow!=null) {
            popupWindow.dismiss();
        }
        Destination dest = (Destination) v.getTag();
        LayoutInflater layoutInflater = (LayoutInflater) PlanActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup_destination,null);

        ImageButton buClose = customView.findViewById(R.id.buClose);
        TextView txDestination = customView.findViewById(R.id.txDestination);
        ImageView ivDestination = customView.findViewById(R.id.ivDestination);
        txDestination.setText(dest.getDestination());
        if (dest.getPhoto() != null) {
            ivDestination.setImageBitmap(PictureUtils.getImage(dest.getPhoto()));
        }

        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        buClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
