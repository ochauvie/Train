package com.och.train.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.listener.MaterielInRameListener;
import com.och.train.listener.MyTouchListener;
import com.och.train.model.Categorie;
import com.och.train.model.Materiel;

import java.util.ArrayList;
import java.util.List;

public class MaterielInRameAdapter  extends BaseAdapter {

    public static final String ORIGINE_CATALOGUE = "CATALOGUE";
    public static final String ORIGINE_COMPOSITION = "COMPOSITION";

    private List<Materiel> materielList;
    private Context mContext;
    private boolean dragDrop;
    private boolean withDestination;
    private LayoutInflater mInflater;
    private List<MaterielInRameListener> listeners = new ArrayList<>();
    private String origine;
    private String nomRame;

    public MaterielInRameAdapter(Context mContext, List<Materiel> materielList, boolean withDestination, String origine, boolean dragDrop, String nomRame) {
        this.materielList = materielList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.withDestination = withDestination;
        this.origine = origine;
        this.dragDrop = dragDrop;
        this.nomRame = nomRame;
    }

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(MaterielInRameListener aListener) {
        listeners.add(aListener);
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

    public List<Materiel> getMaterielList() {
        return materielList;
    }

    public String getItemOrigine() {
        return this.origine;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.item_materiel_in_rame, parent, false);
        TextView tv_categorie = (TextView) layoutItem.findViewById(R.id.categorie);
        TextView tv_description = (TextView)layoutItem.findViewById(R.id.description);

        // Renseignement des valeurs
        Materiel materiel = materielList.get(position);

        tv_categorie.setText(materiel.getCategorie().getLabel());
        tv_categorie.setTextColor(materiel.getCategorie().getFlag());
        tv_description.setText(materiel.getDescription());

        if (withDestination) {
            // TODO : ajout bt add/delete destination et liste destinations
        }

        // On memorise la position  dans le composant textview
        MaterielTag tagMateriel = new MaterielTag(position, this.origine);
        layoutItem.setTag(tagMateriel);

        // Drag
        if (dragDrop) {
            layoutItem.setOnTouchListener(new MyTouchListener());
        }

        return layoutItem;

    }
}
