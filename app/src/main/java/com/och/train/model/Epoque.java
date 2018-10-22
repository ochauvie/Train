package com.och.train.model;

import com.och.train.R;

public enum Epoque implements IRefData {

    I(R.string.epoque_1, 0),
    II(R.string.epoque_2, 0),
    III(R.string.epoque_3, 0),
    IV(R.string.epoque_4, 0),
    V(R.string.epoque_5, 0),
    VI(R.string.epoque_6, 0);

    private final int label;
    private final int flag;

    Epoque(int label, int flag) {
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
