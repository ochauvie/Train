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

    public static void deleteAllDestination() {
        new Delete().from(Destination.class)
                .execute();
    }

    public static void deleteAllDestinationMaterielRame() {
        new Delete().from(DestinationMaterielRame.class)
                .execute();
    }

    public static List<DestinationMaterielRame> getByRame(Rame rame) {
        return new Select()
                .from(DestinationMaterielRame.class)
                .where("rame = ?", rame.getId())
                .execute();
    }

    public static List<DestinationMaterielRame> getByRameMateriel(Rame rame, Materiel materiel) {
        return new Select()
                .from(DestinationMaterielRame.class)
                .where("rame = ?", rame.getId())
                .where("materiel = ?", materiel.getId())
                .execute();
    }

    public static void deleteDestinationByRameMateriel(Rame rame, Materiel materiel) {
        new Delete().from(DestinationMaterielRame.class)
                .where("rame = ?", rame.getId())
                .where("materiel = ?", materiel.getId())
                .execute();
    }

    public static boolean isUsedInRame(Destination destination) {
        List<Materiel> list = new Select()
                .from(DestinationMaterielRame.class)
                .where("Destination = ?", destination.getId())
                .execute();
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

}
