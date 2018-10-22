package com.och.train.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.och.train.model.Destination;
import com.och.train.model.DestinationMaterielRame;
import com.och.train.model.Materiel;
import com.och.train.model.Rame;

import java.util.List;

public class DestinationService {

    public static List<Destination> getAll() {
        return new Select()
                .from(Destination.class)
                .orderBy("destination ASC")
                .execute();
    }

    public static void deleteAll() {
        new Delete().from(Destination.class)
                .execute();
    }

    public static List<Destination> getByRameMateriel(Rame rame, Materiel materiel) {
        return new Select()
                .from(DestinationMaterielRame.class)
                .where("rame = ?", rame.getId())
                .where("materiel = ?", materiel.getId())
                .execute();
    }


}
