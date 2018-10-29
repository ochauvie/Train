package com.och.train.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Plan")
public class Plan extends Model {

    @Column(name = "Reseau")

    private byte[] reseau;

    public Plan() {
    }

    public Plan(byte[] reseau) {
        this.reseau = reseau;
    }

    public byte[] getReseau() {
        return reseau;
    }

    public void setReseau(byte[] reseau) {
        this.reseau = reseau;
    }
}
