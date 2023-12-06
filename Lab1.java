/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.lab1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author humak
 * 
 * 
 */
public class Lab1 {
    public static void dodaj(EntityManager em) {
        A a1 = new A(55, "Dana White");
        A a2 = new A(50, "Chatri Sityodtong");
        A a3 = new A(22, "Jon Sung");
        B b1 = new B();
        B b2 = new B();
        B b3 = new B("Arachion Olsztyn");
        //B b2 = new B();
        C c1 = new C();
        
        D d1 = new D();
        D d2 = new D();
        D d3 = new D("Bellator");
        C c2 = new C();
        C c3 = new C();
        C c4 = new C();
        b1.setNazwab("Czerwony Smok");
        b2.setNazwab("UFD Gym");
        //a1.addOrganizacja(d1);
        //a2.addOrganizacja(d2);
        c1.setCena(270);
        c1.setNazwaZawodnik("Conor");
        c2.setCena(400);
        c2.setNazwaZawodnik("Gamrot");
        c3.setCena(330);
        c3.setNazwaZawodnik("Mighty Mouse");
        c4.setCena(3130);
        c4.setNazwaZawodnik("Roberto Soldic");
        d1.setNazwaOrg("UFC");
        d2.setNazwaOrg("One FC");
        d1.addZawodnik(c2);
        d1.addZawodnik(c1);
        d2.addZawodnik(c4);
        d2.addZawodnik(c3);
        d1.addWlasciciel(a1);
        d2.addWlasciciel(a2);
        //d3.addWlasciciel(a3);
        //a3.addOrganizacja(d3);
        b1.addZawodnik(c2);
        b1.addZawodnik(c1);
        b1.addZawodnik(c3);
        b2.addZawodnik(c2);
        b2.addZawodnik(c4);
        em.getTransaction().begin();
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(b1);
        em.persist(b3);
        em.persist(c1);
        em.persist(d1);
        em.persist(d2);
        em.persist(d3);
        em.persist(b2);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        
        em.getTransaction().commit();
    }
    public static void main(String[] args) {
        //interfejs uzytkownika do wstawiana instacji Encji a b c d
        //System.out.println("Hello World!");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mydb");
        EntityManager em = emf.createEntityManager();
        CrudBean cb = new CrudBean();
        //dodaj(em);
    
        Object obj;
        Scanner myScanner = new Scanner(System.in);
        String nazwa;
        System.out.println("Main podaj znaki <CREATE/READ/UPDATE/DELETE> <A/B/C/D>");
        System.out.println("Remove - mozliwosc usuniecia relacji(podaj nazwy 2 encji np. ad) Update - mozliwosc dodania relacji(podaj nazwe 1 encji)");
        System.out.println("Dla Create i Read nie trzeba podawac klucza/kluczy");
        nazwa = myScanner.nextLine();
        String [] tab = nazwa.split(" ");
        if (nazwa.contains(" ") != true) {
            System.out.println("Koniec dzialania");
            em.close();
            emf.close();
            return;
        }
        if (tab.length == 1) {
            System.out.println("Koniec dzialania");
            em.close();
            emf.close();
            return;
        }
        cb.CoWTabeli(em, tab[1].toLowerCase());
        Integer my_id2 = null;
        Long my_id1 = null;
        if (tab[0].toLowerCase().equals("create") != true) {
            System.out.println("Podaj klucz/klucze <Id klucza> <Id klucza 2>");
            String klucze = myScanner.nextLine();
            String [] tabKlucze = klucze.split(" ");

            if (klucze.contains(" ")) {
                try {
                    my_id2 = Integer.valueOf(tabKlucze[1]);
                } catch(NumberFormatException e) {
                    System.out.println("Koniec dzialania zly klucz");
                    return;
                }
                   
            }
            else if(klucze.equals("")){
                System.out.println("Koniec dzialania");
                em.close();
                emf.close();
                return;
            }
            try {
                my_id1 = Long.valueOf(tabKlucze[0]);
            } catch(NumberFormatException e) {
                System.out.println("Koniec dzialania zly klucz");
                return;
            }
        }
        String tabela = tab[1].toLowerCase();
        if (tab[0].toLowerCase().equals("read")) {
            if (my_id2 == null)
                obj = cb.getObj(em, tabela, my_id1);
            else
                obj = cb.getObj(em, tabela, my_id1, my_id2);
        }
        else if (tab[0].toLowerCase().equals("delete")) {
            if (my_id2 == null)
                cb.remove(em, tabela, my_id1);
            else
                cb.remove(em, tabela, my_id1, my_id2);
        }
        else if (tab[0].toLowerCase().equals("update")) {
            if (my_id2 == null)
                cb.update(em, tabela, my_id1);
            else
                cb.update(em, tabela, my_id1, my_id2);
        }
        else if (tab[0].toLowerCase().equals("create")) {
            obj = cb.createObj(em, tabela);
        } 
        else {
            System.out.println("Nie ma takiej funkcji");
        }
        cb.CoWTabeli(em, tabela);
        em.close();
        emf.close();
    }
}
