package com.och.train.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Rame")
public class Rame extends Model {

    public static final String ID_RAME = "ID_RAME";

    @Column(name = "Description")
    public String description;

    public List<CompositionRame> materiels() {
        return getMany(CompositionRame.class, "Rame");
    }

    public boolean isUsedInRame(Materiel materiel) {
        if (materiels() !=null) {
            for (CompositionRame item:materiels()) {
                if (item.getMateriel().getId().equals(materiel.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Rame() {
        super();
    }

    public Rame(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
