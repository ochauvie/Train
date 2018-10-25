package com.och.train.model;

import com.och.train.R;

public enum Propulsion implements IRefData {

    DIESEL(R.string.propulsion_diesel, R.drawable.loco_diesel),
    VAPEUR(R.string.propulsion_vapeur, R.drawable.loco_vapeur),
    ELECTRIQUE(R.string.propulsion_electrique, R.drawable.loco_electrique),
    AUCUNE(R.string.propulsion_aucune, 0);

    private final int label;
    private final int flag;

    Propulsion(int label, int flag) {
        this.label = label;
        this.flag = flag;
    }

    public int getLabel() {
        return label;
    }

    public int getFlag() {
        return flag;
    }
}
