package com.och.train.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.listener.DestinationEnRouteListener;
import com.och.train.model.DestinationMaterielRame;

import java.util.ArrayList;
import java.util.List;

public class DestinationEnRouteAdapter extends BaseAdapter {

    private List<DestinationMaterielRame> destList;
    private LayoutInflater mInflater;
    private List<DestinationEnRouteListener> destListeners = new ArrayList<>();

    public DestinationEnRouteAdapter(Context mContext, List<DestinationMaterielRame> destList) {
        this.destList = destList;
        mInflater = LayoutInflater.from(mContext);
    }

    public DestinationEnRouteAdapter() {
        super();
    }

    public void addListener(DestinationEnRouteListener aListener) {
        destListeners.add(aListener);
    }

    @Override
    public int getCount() {
        if (destList!=null) {
            return destList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (destList!=null) {
            return destList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.item_destenroute, parent, false);
        TextView tv_destination = layoutItem.findViewById(R.id.tvDest);
        CheckBox cbAtteinte = layoutItem.findViewById(R.id.cbAtteinte);

        // Renseignement des valeurs
        DestinationMaterielRame current = destList.get(position);
        tv_destination.setText(current.getDestination().getDestination() + " (" + current.getDestination().getLongueur() + " cm)");
        cbAtteinte.setChecked(current.isDestinationAtteinte());

        if (current.isDestinationAtteinte()) {
            tv_destination.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tv_destination.setTypeface(Typeface.DEFAULT);
        }

        // On memorise la position  dans le composant textview
        layoutItem.setTag(position);
        tv_destination.setTag(position);
        cbAtteinte.setTag(position);

        cbAtteinte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                Integer position = (Integer) v.getTag();
                sendListenerDestAtteinte(destList.get(position), v.isChecked());
            }
        });

        tv_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToPopupDestination(destList.get(position));
            }
        });

        return layoutItem;
    }

    private void sendListenerDestAtteinte(DestinationMaterielRame item, boolean isChecked) {
        if (destListeners != null) {
            for (int i = destListeners.size() - 1; i >= 0; i--) {
                destListeners.get(i).onDestAtteinte(item, isChecked);
            }
        }
    }

    private void sendListenerToPopupDestination(DestinationMaterielRame item) {
        if (destListeners != null) {
            for (int i = destListeners.size() - 1; i >= 0; i--) {
                destListeners.get(i).onPopupDestinaion(item);
            }
        }
    }

}
