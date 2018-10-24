package com.och.train.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.listener.MaterielInRameListener;
import com.och.train.listener.MyTouchListener;
import com.och.train.model.Destination;
import com.och.train.model.DestinationMaterielRame;
import com.och.train.model.Materiel;
import com.och.train.tools.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaterielInRameAdapter  extends BaseAdapter {

    public static final String ORIGINE_CATALOGUE = "CATALOGUE";
    public static final String ORIGINE_COMPOSITION = "COMPOSITION";

    private List<Materiel> materielList;
    private List<DestinationMaterielRame> rameDestsList;
    private Context mContext;
    private boolean dragDrop;
    private boolean withDestination;
    private LayoutInflater mInflater;
    private List<MaterielInRameListener> listeners = new ArrayList<>();
    private String origine;
    private String nomRame;

    public MaterielInRameAdapter(Context mContext, List<Materiel> materielList, boolean withDestination, String origine, boolean dragDrop, String nomRame, List<DestinationMaterielRame> rameDestsList) {
        this.materielList = materielList;
        this.rameDestsList = rameDestsList;
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
        ImageView btAdd = (ImageView)layoutItem.findViewById(R.id.btAdd);
        ListView listDest = (ListView)layoutItem.findViewById(R.id.listDest);

        // Renseignement des valeurs
        Materiel materiel = materielList.get(position);

        tv_categorie.setText(materiel.getCategorie().getLabel());
        tv_categorie.setTextColor(materiel.getCategorie().getFlag());
        tv_description.setText(materiel.getDescription());

            if (withDestination) {
            List currentDest = new ArrayList<Destination>();
            for (DestinationMaterielRame dest:rameDestsList) {
                if (dest.getMateriel().equals(materiel))
                {
                    currentDest.add(dest.getDestination());
                }
            }

            if (currentDest.size()>0) {
                listDest.setDividerHeight(0);
                listDest.setDivider(null);
                DestinationAdapter destinationAdapter = new DestinationAdapter(parent.getContext(), currentDest, materiel, listeners);
                listDest.setAdapter(destinationAdapter);

                ViewGroup.LayoutParams params = listDest.getLayoutParams();
                params.height = Utils.dpToPx(mContext, (20 * currentDest.size()) + 10);
                listDest.setLayoutParams(params);

                destinationAdapter.notifyDataSetChanged();

            } else {
                layoutItem.removeView(listDest);
            }
            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterielTag tag = (MaterielTag) v.getTag();
                    sendListenerToAddDest(materielList.get(tag.getPosition()), tag.getPosition());
                }
            });

        } else {
            layoutItem.removeView(btAdd);
            layoutItem.removeView(listDest);
        }

        // On memorise la position  dans le composant textview
        MaterielTag tagMateriel = new MaterielTag(position, this.origine);
        layoutItem.setTag(tagMateriel);
        btAdd.setTag(tagMateriel);

        // Drag
        if (dragDrop) {
            layoutItem.setOnTouchListener(new MyTouchListener());
        }

        return layoutItem;

    }
    private void sendListenerToAddDest(Materiel item, int position) {
        for(int i = listeners.size()-1; i >= 0; i--) {
            listeners.get(i).onClickAddDest(item, position);
        }
    }


}
