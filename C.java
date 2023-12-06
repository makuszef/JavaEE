/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//usunac zbedne anotacje
package com.mycompany.lab1;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author humak
 */
@Data
@Table(name="ZawodnikC")
@Entity
//@ManyToOne(cascade=REMOVE, mappedBy="c")
public class C implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_c;
    private int cena;
    private String nazwaZawodnik;
    @ManyToMany(mappedBy="clist")
    private List<B> blist = new ArrayList();
    @ManyToOne
    private D ds;
    public C(int cena, String nazwaZawodnik) {
        this.cena = cena;
        this.nazwaZawodnik = nazwaZawodnik;
    }
    public C() {
        
    }
    
    public void addKlub(B klub) {
        blist.add(klub);
        klub.getClist().add(this);
    }
    public D getDs() {
        return ds;
    }

    public void setDs(D ds) {
        this.ds = ds;
    }
    
    public List<B> getBlist() {
        return blist;
    }

    public void setBlist(List<B> blist) {
        this.blist = blist;
    }
    
    public Long getId_c() {
        return id_c;
    }

    public void setId(Long id_c) {
        this.id_c = id_c;
    }
    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_c != null ? id_c.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof C)) {
            return false;
        }
        C other = (C) object;
        if ((this.id_c == null && other.id_c != null) || (this.id_c != null && !this.id_c.equals(other.id_c))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.lab1.C[ id=" + id_c + " ]";
    }
    
}
