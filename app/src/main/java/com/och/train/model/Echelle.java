package com.och.train.model;

import com.och.train.R;

public enum Echelle implements IRefData {

    Z(R.string.echelle_Z, 0),
    N(R.string.echelle_N, 0),
    HO(R.string.echelle_HO, 0);

    private final int label;
    private final int flag;

    Echelle(int label, int flag) {
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
