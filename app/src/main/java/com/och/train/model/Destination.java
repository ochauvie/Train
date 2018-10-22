package com.och.train.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Destination")
public class Destination extends Model {

    public static final String ID_DESTINATION = "ID_DESTINATION";

    @Column(name = "Nom", index = true)
    public String destination;


    public Destination() {
        super();
    }

    public Destination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }
}
