package com.och.train.listener;


import com.och.train.model.Destination;
import com.och.train.model.Materiel;

public interface MaterielInRameListener {

    void onClickAddDest(Materiel item, int position);

    void onDeleteDest(Materiel materiel, Destination destination, int position);

    void onChangeMateriel(Materiel materiel);


}
