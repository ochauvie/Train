package com.och.train.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.och.train.model.Categorie;
import com.och.train.model.Materiel;

import java.util.List;

public class MaterielService {

    public static List<Materiel> getAll() {
        return new Select()
                .from(Materiel.class)
                .orderBy("categorie, description ASC")
                .execute();
    }

    public static List<Materiel> findByCategorie(Categorie categorie) {
        return new Select()
                .from(Materiel.class)
                .where("categorie = ?", categorie.name())
                .orderBy("description ASC")
                .execute();
    }

    public static Materiel getById(long id) {
        return new Select()
                .from(Materiel.class)
                .where("Id = ?", id)
                .executeSingle();
    }


    public static void deleteAll() {
        new Delete().from(Materiel.class)
                .execute();
    }



}
