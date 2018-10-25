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
import com.och.train.listener.DestinationListener;
import com.och.train.listener.MaterielInRameListener;
import com.och.train.model.Destination;
import com.och.train.model.Materiel;

import java.util.ArrayList;
import java.util.List;

public class DestinationAdapter extends BaseAdapter {

    private List<Destination> destList;
    private Materiel materiel;
    private LayoutInflater mInflater;
    private List<MaterielInRameListener> listeners = new ArrayList<>();
    private List<DestinationListener> destListeners = new ArrayList<>();


    public DestinationAdapter(Context mContext, List<Destination> destList, Materiel materiel, List<MaterielInRameListener> listenerList) {
        this.destList = destList;
        mInflater = LayoutInflater.from(mContext);
        listeners = listenerList;
        this.materiel = materiel;
    }

    public DestinationAdapter() {
        super();
    }

    public void addListener(DestinationListener aListener) {
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

        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.item_destination, parent, false);
        TextView tv_destination = (TextView)layoutItem.findViewById(R.id.tvDestination);
        ImageButton btDelete = (ImageButton)layoutItem.findViewById(R.id.btDelete);

        // Renseignement des valeurs
        Destination current = destList.get(position);
        tv_destination.setText(current.getDestination());

        // On memorise la position  dans le composant textview
        layoutItem.setTag(position);
        btDelete.setTag(position);
        tv_destination.setTag(position);

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToOnDelete(destList.get(position), position);
            }
        });

        tv_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToUpdate(destList.get(position), position);
            }
        });

        return layoutItem;
    }

    private void sendListenerToOnDelete(Destination item, int position) {
        if (listeners!=null) {
            for (int i = listeners.size() - 1; i >= 0; i--) {
                listeners.get(i).onDelete(materiel, item, position);
            }
        }
        if (destListeners != null) {
            for (int i = destListeners.size() - 1; i >= 0; i--) {
                destListeners.get(i).onDelete(item, position);
            }
        }
    }
    private void sendListenerToUpdate(Destination item, int position) {
        if (destListeners != null) {
            for (int i = destListeners.size() - 1; i >= 0; i--) {
                destListeners.get(i).onClick(item, position);
            }
        }
    }
}
