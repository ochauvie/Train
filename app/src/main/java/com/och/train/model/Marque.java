package com.och.train.model;

import com.och.train.R;

public enum Marque implements IRefData {

    FLEISCHMANN(R.string.marque_fleischmann, 0),
    BRAWA(R.string.marque_brawa, 0),
    MINITRIX(R.string.marque_minitrix, 0),
    PECO(R.string.marque_peco, 0),
    ROCO(R.string.marque_roco, 0),
    FALLER(R.string.marque_faller, 0),
    AUTRE(R.string.marque_autre, 0);

    private final int label;
    private final int flag;

    Marque(int label, int flag) {
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
