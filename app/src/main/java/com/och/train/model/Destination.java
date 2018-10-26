package com.och.train.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Destination")
public class Destination extends Model {

    public static final String ID_DESTINATION = "ID_DESTINATION";

    @Column(name = "Destination", index = true)
    public String destination;

    @Column(name = "Longueur")
    public int longueur;

    @Column(name = "X")
    public int x;

    @Column(name = "Y")
    public int y;

    public Destination() {
        super();
    }

    public Destination(String destination, int longueur) {
        this.destination = destination;
        this.longueur = longueur;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
