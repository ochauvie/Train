package com.och.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.listener.MyTouchListener;
import com.och.train.listener.PlanDragListener;
import com.och.train.listener.PlanListener;
import com.och.train.model.Destination;
import com.och.train.service.DestinationService;
import com.och.train.tools.Utils;

import java.util.List;

public class PlanActivity extends AppCompatActivity implements PlanListener {

    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout = findViewById(R.id.cvDests);
        layout.setOnDragListener(new PlanDragListener(this));

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
        }
        return false;
    }
}
