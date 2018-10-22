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

    public static void initMateriels() {

        ActiveAndroid.beginTransaction();
        try {

            MaterielService.deleteAll();

            Materiel materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.LOCO, Propulsion.VAPEUR, "Loco vapeur");
            materiel.setCompanie("Chemin de fer du Sud");
            materiel.setReference("V001");
            materiel.setLongueur(5);
            materiel.setQuantite(1);
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.MARCHANDISE, Propulsion.AUCUNE, "Tombereau bas");
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.MARCHANDISE, Propulsion.AUCUNE, "Tombereau haut");
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.MARCHANDISE, Propulsion.AUCUNE, "Transport bière");
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.MARCHANDISE, Propulsion.AUCUNE, "Transport fermé");
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 1");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 2");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 3");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 4");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 5");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 6");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 7");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 8");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 9");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 10");
            materiel.save();

            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 11");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 12");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 13");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 14");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 15");
            materiel.save();


            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 16");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 17");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 18");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 19");
            materiel.save();
            materiel = new Materiel(Echelle.N, Epoque.III, Marque.FLEISCHMANN,
                    Categorie.VOITURE, Propulsion.AUCUNE, "Voiture 20");
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

            RameService.deleteAllRames();

            Rame rame = new Rame("Rame de test 1");
            rame.save();

            rame = new Rame("Rame marchandise tombereau");
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

            DestinationService.deleteAll();

            Destination destination = new Destination("Gare 1");
            destination.save();
            destination = new Destination("Gare 2");
            destination.save();
            destination = new Destination("Halle marchandises 1");
            destination.save();
            destination = new Destination("Halle marchandises 2");
            destination.save();
            destination = new Destination("Dépot 1");
            destination.save();
            destination = new Destination("Dépot 2");
            destination.save();

            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }

    }

}