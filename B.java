/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;

/**
 *
 * @author humak
 */
@Table(name="KlubB")
@Entity
public class B implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_b;
    private String nazwab;
    @ManyToMany
    private List<C> clist = new ArrayList();
    public B(String nazwab) {
        this.nazwab = nazwab;
    }
    public B() {
        
    }
    
    public List<C> getClist() {
        return clist;
    }

    public void setClist(List<C> clist) {
        this.clist = clist;
    }
    public void addZawodnik(C zawodnik) {
        this.getClist().add(zawodnik);
        zawodnik.getBlist().add(this);
    }
    public void removeZawodnik(C zawodnik) {
        this.getClist().remove(zawodnik);
        zawodnik.getBlist().remove(this);
    }
    public String getNazwab() {
        return nazwab;
    }

    public void setNazwab(String nazwab) {
        this.nazwab = nazwab;
    }
    public Long getId_b() {
        return id_b;
    }

    public void setId_b(Long id_b) {
        this.id_b = id_b;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_b != null ? id_b.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof B)) {
            return false;
        }
        B other = (B) object;
        if ((this.id_b == null && other.id_b != null) || (this.id_b != null && !this.id_b.equals(other.id_b))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.lab1.B[ id=" + id_b + " ]";
    }
    
}
