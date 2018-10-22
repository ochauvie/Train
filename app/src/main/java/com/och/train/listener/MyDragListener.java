package com.och.train.listener;

import android.view.DragEvent;
import android.view.View;

import com.och.train.adapter.MaterielInRameAdapter;
import com.och.train.adapter.MaterielTag;
import com.och.train.model.Materiel;

public class MyDragListener implements View.OnDragListener {

        private MaterielInRameAdapter adapterDestination;
        private MaterielInRameAdapter adapterOrigine;
        private float oldY;

    public MyDragListener(MaterielInRameAdapter adapterDestination, MaterielInRameAdapter adapterOrigine) {
            this.adapterDestination = adapterDestination;
            this.adapterOrigine = adapterOrigine;
        }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                oldY = event.getY();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                View view = (View) event.getLocalState();
                MaterielTag tagTank = (MaterielTag) view.getTag();
                int position = tagTank.getPosition();
                String origine = tagTank.getOrigine();

                // Dag and drop dans des listes différentes
                if (!adapterDestination.getItemOrigine().equals(origine)) {
                    Materiel currentMaterial = (Materiel)adapterOrigine.getItem(position);

                    adapterDestination.getMaterielList().add(currentMaterial);
                    adapterOrigine.getMaterielList().remove(position);

                    adapterDestination.notifyDataSetChanged();
                    adapterOrigine.notifyDataSetChanged();

                    // Dag and drop dans la même liste: on monte ou on dessend d'une case
                } else {
                    Materiel currentMaterial = (Materiel)adapterDestination.getItem(position);
                    // Drag down
                    if (event.getY()>oldY) {
                        // Not the last

                        if (position<adapterDestination.getMaterielList().size()-1) {
                            Materiel oldMateriel = (Materiel) adapterDestination.getItem(position + 1);
                            adapterDestination.getMaterielList().set(position + 1, currentMaterial);
                            adapterDestination.getMaterielList().set(position, oldMateriel);
                        }

                        // Drag up

                    } else if (event.getY()<oldY) {

                        // Not the first

                        if (position>0) {
                            Materiel oldMateriel = (Materiel) adapterDestination.getItem(position - 1);
                            adapterDestination.getMaterielList().set(position - 1, currentMaterial);
                            adapterDestination.getMaterielList().set(position, oldMateriel);
                        }
                    }
                    adapterDestination.notifyDataSetChanged();
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:

            default:
                break;
        }

        return true;
    }
}
