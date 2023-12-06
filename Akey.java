/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1;

import lombok.Data;

/**
 *
 * @author humak
 */
@Data
public class Akey {
    private Long id_a;
    private int id_a2;
    
@Override
    public int hashCode() {
        return (int) ((this.getId_a()== null
                ? 0 : this.getId_a().hashCode())
                % ( this.getId_a2()));
    }

    @Override
    public boolean equals(Object otherOb) {
        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof Akey)) {
            return false;
        }
        Akey other = (Akey) otherOb;
        return ((this.getId_a()== null
                ? other.getId_a() == null : this.getId_a()
                .equals(other.getId_a()))
                && (this.getId_a2() == other.getId_a2()));
    }

    @Override
    public String toString() {
        return "" + getId_a() + "-" + getId_a2();
    }
}
