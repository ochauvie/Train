package com.och.train.data;

import com.activeandroid.ActiveAndroid;
import com.och.train.model.Categorie;
import com.och.train.model.Destination;
import com.och.train.model.Echelle;
import com.och.train.model.Epoque;
import com.och.train.model.Marque;
import com.och.train.model.Materiel;
import com.och.train.model.Propulsion;
import com.och.train.model.Rame;
import com.och.train.service.DestinationService;
import com.och.train.service.MaterielService;
import com.och.train.service.RameService;

public class InitData {

    public static void deleteAll() {
        ActiveAndroid.beginTransaction();
        try {
            DestinationService.deleteAllDestinationMaterielRame();
            DestinationService.deleteAllDestination();
            RameService.deleteAllCompositionRame();
            RameService.deleteAllRames();
            MaterielService.deleteAll();
            DestinationService.deleteAllPlans();
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static void initMateriels() {
        ActiveAndroid.beginTransaction();
        try {
           Materiel materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.LOCO, Propulsion.VAPEUR, "Petite locomotive vapeur");
            materiel.setCompanie("SNCF");
            materiel.setReference("933602");
            materiel.setLongueur(5);
            materiel.setQuantite(1);
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.MARCHANDISE, Propulsion.TOMBEREAU, "Tombereau bas");
            materiel.setCompanie("SNCF");
            materiel.setReference("933602");
            materiel.setLongueur(5);
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.MARCHANDISE, Propulsion.TOMBEREAU, "Tombereau haut");
            materiel.setCompanie("SNCF");
            materiel.setReference("933602");
            materiel.setLongueur(5);
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.BRAWA,
                    Categorie.MARCHANDISE, Propulsion.COUVERT, "Marchandises G10 Palmin");
            materiel.setReference("67405");
            materiel.setCompanie("DB");
            materiel.setLongueur(5);
            materiel.setQuantite(1);
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.MARCHANDISE, Propulsion.REFIRIGERANT, "Réfrigérant Brouwerij Oranjeboom");
            materiel.setReference("834108");
            materiel.setCompanie("DB");
            materiel.setLongueur(5);
            materiel.setQuantite(1);
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.BRAWA,
                    Categorie.MARCHANDISE, Propulsion.CITERNE, "Citerne J.A.F.");
            materiel.setReference("67526");
            materiel.setCompanie("DB");
            materiel.setLongueur(5);
            materiel.setQuantite(1);
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.MARCHANDISE, Propulsion.CITERNE, "Citerne Marcel Rouanet");
            materiel.setReference("5430 FB");
            materiel.setCompanie("SNCF");
            materiel.setLongueur(5);
            materiel.setQuantite(1);
            materiel.save();

            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static void initRames() {
        ActiveAndroid.beginTransaction();
        try {
            Rame rame = new Rame("Rame marchandises");
            rame.save();

            rame = new Rame("Rame tombereaux");
            rame.save();

            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static void initDestinations() {

        ActiveAndroid.beginTransaction();
        try {
            Destination destination = new Destination("Gare", 30);
            destination.save();
            destination = new Destination("Halle marchandises", 20);
            destination.save();
            destination = new Destination("Usine", 20);
            destination.save();
            destination = new Destination("Pont tournant", 10);
            destination.save();
            destination = new Destination("Atelier", 20);
            destination.save();
            destination = new Destination("Dépot Sud 1", 20);
            destination.save();
            destination = new Destination("Dépot Sud 2", 20);
            destination.save();

            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }

}