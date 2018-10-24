package com.och.train.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DestinationRame")
public class DestinationMaterielRame extends Model {

    public static final String ID_DESTRAME = "ID_DESTRAME";

    @Column(name = "Rame", index = true)
    public Rame rame;

    @Column(name = "Materiel", index = true)
    public Materiel materiel;

    @Column(name = "Destination", index = true)
    public Destination destination;

    @Column(name = "DestinationAtteinte")
    public boolean destinationAtteinte;


    public DestinationMaterielRame() {
    }

    public DestinationMaterielRame(Rame rame, Materiel materiel, Destination destination) {
        this.rame = rame;
        this.materiel = materiel;
        this.destination = destination;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public Rame getRame() {
        return rame;
    }

    public Destination getDestination() {
        return destination;
    }

    public boolean isDestinationAtteinte() {
        return destinationAtteinte;
    }

    public void setDestinationAtteinte(boolean destinationAtteinte) {
        this.destinationAtteinte = destinationAtteinte;
    }
}
