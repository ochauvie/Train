package com.och.train.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CompositionRame")
public class CompositionRame extends Model {

    public static final String ID_COMPO = "ID_COMPO";

    @Column(name = "Rame", index = true)
    public Rame rame;

    @Column(name = "Materiel", index = true)
    public Materiel materiel;

      @Column(name = "Position")
    public int position;


    public CompositionRame() {
    }

    public CompositionRame(Rame rame, Materiel materiel) {
        this.rame = rame;
        this.materiel = materiel;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public Rame getRame() {
        return rame;
    }

    public int getPosition() {
        return position;
    }
}
