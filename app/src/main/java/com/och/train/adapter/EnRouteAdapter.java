package com.och.train.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.listener.DestinationEnRouteListener;
import com.och.train.listener.EnRouteListener;
import com.och.train.model.CompositionRame;
import com.och.train.model.DestinationMaterielRame;
import com.och.train.model.Materiel;
import com.och.train.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class EnRouteAdapter extends BaseAdapter implements DestinationEnRouteListener{

    private List<CompositionRame> compositionRameList;
    private List<DestinationMaterielRame> destRameList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<EnRouteListener> listeners = new ArrayList<>();

    public EnRouteAdapter(Context mContext, List<CompositionRame> compositionRameList, List<DestinationMaterielRame> destRameList) {
        this.compositionRameList = compositionRameList;
        this.destRameList = destRameList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(EnRouteListener aListener) {
        listeners.add(aListener);
    }

    @Override
    public int getCount() {
        if (compositionRameList!=null) {
            return compositionRameList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (compositionRameList!=null) {
            return compositionRameList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.item_enroute, parent, false);
        TextView stCategorie =  layoutItem.findViewById(R.id.stCategorie);
        TextView stDescription = layoutItem.findViewById(R.id.stDescription);
        CheckBox cbEnRoute = layoutItem.findViewById(R.id.cbEnRoute);
        ListView listDest = layoutItem.findViewById(R.id.listCompo);
        ImageView iVCategorie = layoutItem.findViewById(R.id.iVCategorie);

        // Renseignement des valeurs
        CompositionRame compo = compositionRameList.get(position);

        stCategorie.setText(compo.getMateriel().getCategorie().getLabel());
        stCategorie.setTextColor(compo.getMateriel().getCategorie().getFlag());
        stDescription.setText(compo.getMateriel().getDescription());
        cbEnRoute.setChecked(compo.isMaterielDansRame());

        if (compo.isMaterielDansRame()) {
            stDescription.setBackgroundColor(Color.TRANSPARENT);
        } else {
            stDescription.setBackgroundColor(Color.LTGRAY);
        }
        iVCategorie.setImageDrawable(ContextCompat.getDrawable(mContext, compo.getMateriel().getPropulsion().getFlag()));

        List<DestinationMaterielRame> currentDest = new ArrayList<>();
        for (DestinationMaterielRame dest:destRameList) {
            if (dest.getMateriel().equals(compo.getMateriel())) {
                currentDest.add(dest);
            }
        }

        if (currentDest.size()>0) {
            listDest.setDividerHeight(0);
            listDest.setDivider(null);
            DestinationEnRouteAdapter destinationEnRouteAdapter = new DestinationEnRouteAdapter(parent.getContext(), currentDest);
            destinationEnRouteAdapter.addListener(this);
            listDest.setAdapter(destinationEnRouteAdapter);

            ViewGroup.LayoutParams params = listDest.getLayoutParams();
            params.height = Utils.dpToPx(mContext,(30 * currentDest.size()) + 10);
            listDest.setLayoutParams(params);

            destinationEnRouteAdapter.notifyDataSetChanged();
          } else {
            layoutItem.removeView(listDest);
        }

        // On memorise la position  dans le composant textview
        layoutItem.setTag(position);
        cbEnRoute.setTag(position);
        stDescription.setTag(position);

        cbEnRoute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                Integer position = (Integer) v.getTag();
                sendListenerToOnEnRoute(compositionRameList.get(position), v.isChecked());
            }
        });

        stDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListenerToPopupMateriel(compositionRameList.get(position).getMateriel());
            }
        });

        return layoutItem;
    }

    private void sendListenerToOnEnRoute(CompositionRame item, boolean isChecked) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onEnRoute(item, isChecked);
        }
    }

    private void sendListenerToPopupMateriel(Materiel item) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onPopupMateriel(item);
        }
    }

    @Override
    public void onDestAtteinte(DestinationMaterielRame item, boolean isChecked) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onDestAtteinte(item, isChecked);
        }
    }


    @Override
    public void onPopupDestinaion(DestinationMaterielRame item) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onPopupDestinaion(item);
        }
    }
}
