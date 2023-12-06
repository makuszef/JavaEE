/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author humak
 */
@Table(name="OgranizacjaD")
@Entity
public class D implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_d;
    @OneToOne(mappedBy="d", cascade=CascadeType.REMOVE) //klucz z d przechodzi do mozna usunac instancje A
    private A a;
    private String nazwaOrg;
    @OneToMany(mappedBy="ds") //sparwdzic mapped
    private List<C> ctabs = new ArrayList();
    public D(String nazwaOrg) {
        this.nazwaOrg = nazwaOrg;
    }
    public D() {   
    }
    public A getA() {
        return a;
    }
    public void setA(A a) {
        this.a = a;
        //a.setD(this);
    }
    public void addWlasciciel(A a) {
        this.a = a;
        a.setD(this);
    }
    public void setCtabs(List<C> ctabs) {
        this.ctabs = ctabs;
    }
    
    public List<C> getCtabs() {
        return ctabs;
    }
    public Long getId_d() {
        return id_d;
    }

    public void setId_d(Long id) {
        this.id_d = id;
    }
    public String getNazwaOrg() {
        return nazwaOrg;
    }

    public void setNazwaOrg(String t) {
        this.nazwaOrg = t;
    }
    public void addZawodnik(C zawodnik) {
        this.getCtabs().add(zawodnik);
        zawodnik.setDs(this);
    }
    public void removeZawodnik(C zawodnik) {
        this.getCtabs().remove(zawodnik);
        zawodnik.setDs(null);
    }
    public void usunWszystkichZawodnikow() {
        for (C zawodnik: this.getCtabs()) {
            zawodnik.setDs(null);
        }
    }
    public void usunWszystkichZawodnikow(EntityManager em) {
        em.getTransaction().begin();
        for (C zawodnik: this.getCtabs()) {
            zawodnik.setDs(null);
            em.merge(zawodnik);
        }
        this.ctabs = null;
        em.merge(this);
        em.getTransaction().commit();
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_d != null ? id_d.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof D)) {
            return false;
        }
        D other = (D) object;
        if ((this.id_d == null && other.id_d != null) || (this.id_d != null && !this.id_d.equals(other.id_d))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.lab1.D[ id=" + id_d + " ]";
    }
    
}
