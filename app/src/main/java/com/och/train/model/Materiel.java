package com.och.train.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Materiel")
public class Materiel extends Model {

    public static final String ID_MATERIEL = "ID_MATERIEL";

    @Column(name = "Categorie", index = true)
    public Categorie categorie;

    @Column(name = "Propulsion")
    public Propulsion propulsion;

    @Column(name = "Echelle")
    public Echelle echelle;

    @Column(name = "Epoque")
    public Epoque epoque;

    @Column(name = "Marque")
    public Marque marque;


    @Column(name = "Longueur")
    public int longueur;

    @Column(name = "Quantite")
    public int quantite;

    @Column(name = "Description")
    public String description;

    @Column(name = "Companie")
    public String companie;

    @Column(name = "Reference")
    public String reference;

    @Column(name = "Photo")
    private byte[] photo;

    public Materiel() {
        super();
    }

    public Materiel(Echelle echelle, Epoque epoque, Marque marque, Categorie categorie, Propulsion propulsion, String description) {
        this.echelle = echelle;
        this.epoque = epoque;
        this.marque = marque;
        this.categorie = categorie;
        this.propulsion = propulsion;
        this.description = description;
    }

    public Echelle getEchelle() {
        return echelle;
    }

    public void setEchelle(Echelle echelle) {
        this.echelle = echelle;
    }

    public Epoque getEpoque() {
        return epoque;
    }

    public void setEpoque(Epoque epoque) {
        this.epoque = epoque;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Propulsion getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(Propulsion propulsion) {
        this.propulsion = propulsion;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
