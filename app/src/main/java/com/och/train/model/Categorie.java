package com.och.train.model;

import android.graphics.Color;

import com.och.train.R;

public enum Categorie implements IRefData  {

    LOCO(R.string.categorie_loco, Color.RED),
    MARCHANDISE(R.string.categorie_marchandise,  Color.BLUE),
    VOITURE(R.string.categorie_voiture, Color.MAGENTA);

    private final int label;
    private final int flag;

    Categorie(int label, int flag) {
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
