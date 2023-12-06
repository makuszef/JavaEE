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
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
/**
 *
 * @author humak
 */
@Data
@Table(name="TabelaA")
@IdClass(Akey.class)
@Entity
public class A implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_a;
    @Id
    private int id_a2;
    //jesli tutaj mapped by to klucz z A przejdzie do D
    @OneToOne //bez mapped by oba klucze przechodza cascade=CascadeType.ALL, orphanRemoval=true
    private D d;
    private int wiek;
    private String nazwisko;
    public A() { 
    }
    public A(int wiek, String nazwisko) {
        this.wiek = wiek;
        this.nazwisko = nazwisko;
        this.id_a2 = nazwisko.length();
    }
    public D getD() {
        return d;
    }
    public void setD(D d) {
        this.d = d;
        //d.setA(this);
    }
    public void addOrganizacja(D d) {
        this.d = d;
        d.setA(this);
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public int getWiek() {
        return wiek;
    }
    public void setWiek(int wiek) {
        this.wiek = wiek;
    }
    public Long getId_a() {
        return id_a;
    }

    public void setId_a(Long id) {
        this.id_a = id;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_a != null ? id_a.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof A)) {
            return false;
        }
        A other = (A) object;
        if ((this.id_a == null && other.id_a != null) || (this.id_a != null && !this.id_a.equals(other.id_a))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.lab1.A[ id=" + id_a + " ]";
    }
    
}
