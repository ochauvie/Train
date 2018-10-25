package com.och.train.model;

import com.och.train.R;

public enum Propulsion implements IRefData {

    DIESEL(R.string.propulsion_diesel, R.drawable.loco_diesel),
    VAPEUR(R.string.propulsion_vapeur, R.drawable.loco_vapeur),
    ELECTRIQUE(R.string.propulsion_electrique, R.drawable.loco_electrique),

    ISOTHERME(R.string.wagon_isotherme, R.drawable.refigerant),
    REFIRIGERANT(R.string.wagon_refirigerant, R.drawable.refigerant),
    CALORIFIQUE(R.string.wagon_calorifique, R.drawable.refigerant),
    COUVERT(R.string.wagon_couvert, R.drawable.couvert),
    PLAT(R.string.wagon_plat, R.drawable.plat),
    CITERNE(R.string.wagon_citerne, R.drawable.citerne),
    SILO(R.string.wagon_silo, R.drawable.silo),
    TOMBEREAU(R.string.wagon_tombereau, R.drawable.tomberau),
    PORTE_AUTO(R.string.wagon_porteauto, R.drawable.porteauto),
    BESTIAUX(R.string.wagon_bestiaux, R.drawable.bestiaux),
    INTERMODAL(R.string.wagon_intermodal, R.drawable.intermodal),
    RESTAURANT(R.string.voiture_restaurant, R.drawable.restaurant),
    LIT(R.string.voiture_lit, R.drawable.lit),
    VOITURE(R.string.voiture_voiture, R.drawable.voiture),
    AUCUNE(R.string.propulsion_aucune, R.drawable.materiels);

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
