package com.och.train.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.listener.MaterielListener;
import com.och.train.model.Categorie;
import com.och.train.model.Materiel;

import java.util.ArrayList;
import java.util.List;

public class MaterialAdapter extends BaseAdapter {

    private List<Materiel> materielList;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<MaterielListener> listeners = new ArrayList<>();

    public MaterialAdapter(Context mContext, List<Materiel> materielList) {
        this.mContext = mContext;
        this.materielList = materielList;
        mInflater = LayoutInflater.from(mContext);
    }

    public MaterialAdapter() {
        super();
    }

    public void addListener(MaterielListener aListener) {
        listeners.add(aListener);
    }

    private void sendListenerToUpdate(Materiel item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClick(item, position);
        }
    }

    @Override
    public int getCount() {
        if (materielList!=null) {
            return materielList.size();
        }
        return 0;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (materielList!=null) {
            return materielList.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.item_materiel, parent, false);
        ImageView iv_categorie = (ImageView)layoutItem.findViewById(R.id.iVCategorie);
        TextView tv_epoque = (TextView)layoutItem.findViewById(R.id.epoque);
        TextView tv_description = (TextView)layoutItem.findViewById(R.id.description);

        // Renseignement des valeurs
        Materiel current = materielList.get(position);
        iv_categorie.setImageDrawable(ContextCompat.getDrawable(mContext, current.getPropulsion().getFlag()));

        tv_epoque.setText(current.getEpoque().getLabel());
        tv_description.setText(current.getDescription());

        // On memorise la position  dans le composant textview
        layoutItem.setTag(position);
        layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on recupere la position de Site"
                Integer position = (Integer) v.getTag();
                //On previent les listeners qu'il y a eu un clic sur le tank.
                sendListenerToUpdate(materielList.get(position), position);
                }
            });

        return layoutItem;
    }
}
