package com.och.train.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.och.train.model.CompositionRame;
import com.och.train.model.Materiel;
import com.och.train.model.Rame;

import java.util.List;

public class RameService {

    public static List<Rame> getAll() {
        return new Select()
                .from(Rame.class)
                .orderBy("description ASC")
                .execute();
    }

    public static void deleteAllRames() {
        new Delete().from(Rame.class)
                .execute();
    }

    public static boolean isUsedInRame(Materiel materiel) {
        List<Materiel> list = new Select()
                .from(CompositionRame.class)
                .where("Materiel = ?", materiel.getId())
                .execute();
        if (list!=null && list.size()>0 ){
            return true;
        }
        return false;
    }

}
