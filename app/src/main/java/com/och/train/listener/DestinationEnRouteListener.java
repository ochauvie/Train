package com.och.train.listener;


import com.och.train.model.DestinationMaterielRame;

public interface DestinationEnRouteListener {

    void onDestAtteinte(DestinationMaterielRame item, boolean isChecked);
    void onPopupDestinaion(DestinationMaterielRame item);

}
