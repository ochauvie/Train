package com.och.train.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.listener.RameListener;
import com.och.train.model.Rame;

import java.util.ArrayList;
import java.util.List;

public class RameAdapter extends BaseAdapter {

    private List<Rame> rameList;
    private LayoutInflater mInflater;
    private List<RameListener> listeners = new ArrayList<>();


    public RameAdapter(Context mContext, List<Rame> rameList) {
        this.rameList = rameList;
        mInflater = LayoutInflater.from(mContext);
    }

    public RameAdapter() {
        super();
    }

    public void addListener(RameListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToUpdate(Rame item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClick(item, position);
        }
    }

    private void sendListenerToEnRoute(Rame item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onEnRoute(item, position);
        }
    }

    @Override
    public int getCount() {
        if (rameList!=null) {
            return rameList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (rameList!=null) {
            return rameList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.item_rame, parent, false);
        TextView tv_description = (TextView)layoutItem.findViewById(R.id.description);
        TextView tv_nombre = (TextView)layoutItem.findViewById(R.id.nombre);
        ImageButton ivSifflet = (ImageButton)layoutItem.findViewById(R.id.ivSifflet);

        // Renseignement des valeurs
        Rame current = rameList.get(position);
        tv_description.setText(current.getDescription());
        tv_nombre.setText(String.valueOf( current.materiels()!=null?current.materiels().size():0));

        // On memorise la position  dans le composant textview
        layoutItem.setTag(position);
        ivSifflet.setTag(position);
        layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position de Site"
                Integer position = (Integer) v.getTag();
                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToUpdate(rameList.get(position), position);
                }
            });

        ivSifflet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToEnRoute(rameList.get(position), position);
            }
        });

        return layoutItem;
    }
}
