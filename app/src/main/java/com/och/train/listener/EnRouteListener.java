package com.och.train.listener;


import com.och.train.model.CompositionRame;
import com.och.train.model.DestinationMaterielRame;

public interface EnRouteListener {

    void onEnRoute(CompositionRame item, boolean isCheked);
    void onDestAtteinte(DestinationMaterielRame item, boolean isChecked);

}
