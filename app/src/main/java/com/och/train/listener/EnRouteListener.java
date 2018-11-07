package com.och.train.listener;


import com.och.train.model.CompositionRame;
import com.och.train.model.DestinationMaterielRame;
import com.och.train.model.Materiel;

public interface EnRouteListener {

    void onEnRoute(CompositionRame item, boolean isCheked);
    void onDestAtteinte(DestinationMaterielRame item, boolean isChecked);
    void onPopupMateriel(Materiel materiel);
    void onPopupDestinaion(DestinationMaterielRame destinationMaterielRame);

}
