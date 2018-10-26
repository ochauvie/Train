package com.och.train.listener;


import com.och.train.model.Destination;
import com.och.train.model.Materiel;

public interface MaterielInRameListener {

    void onAddDest(Materiel item);

    void onDeleteDest(Materiel materiel, Destination destination);

    void onChangeMateriel();


}
