package com.och.train.model;

import android.graphics.Color;

import com.och.train.R;

public enum Categorie implements IRefData  {

    LOCO(R.string.categorie_loco, Color.RED, R.drawable.loco),
    MARCHANDISE(R.string.categorie_marchandise,  Color.BLUE, R.drawable.wagon),
    VOITURE(R.string.categorie_voiture, Color.MAGENTA, R.drawable.voiture);

    private final int label;
    private final int flag;
    private final int logo;

    Categorie(int label, int flag, int logo) {
        this.label = label;
        this.flag = flag;
        this.logo = logo;
    }

    public int getLabel() {
        return label;
    }

    public int getFlag() {
        return flag;
    }

    public int getLogo() {
        return logo;
    }
}
