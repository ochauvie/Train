package com.och.train.adapter;

public class MaterielTag {
    private int position;
    private String origine;

    public MaterielTag(int position, String origine) {
        this.position = position;
        this.origine = origine;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }
}
