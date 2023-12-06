/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author humak
 */
// dodac mozliwosc dodawania relacji miedzy instancjami
public class CrudBean {
    
    public void CoWTabeli(EntityManager em, String val) {
        if (val.toLowerCase().equals("c")) {
            List<C> wynik1 = em.createQuery("SELECT c FROM C c")
                .getResultList();
            for(C zawodnik : wynik1) {
                System.out.println("Id Zawodnika: " + zawodnik.getId_c() + " Zawodnik :" + zawodnik.getNazwaZawodnik() + " Cena :" + zawodnik.getCena());
            }
        }
        else if (val.toLowerCase().equals("a")) {
            List<A> wynik1 = em.createQuery("SELECT a FROM A a")
                .getResultList();
            for(A wlasciciel : wynik1) {
                System.out.println("Wlascicel Id_a:" + wlasciciel.getId_a() + " Id_a2 " + wlasciciel.getId_a2() + " Nazwisko " + wlasciciel.getNazwisko() + " Wiek " + wlasciciel.getWiek());
            }
        }
        else if (val.toLowerCase().equals("cb") || val.toLowerCase().equals("bc")) {
            CoWTabeli(em, "c");
            CoWTabeli(em, "b");
        }
        else if (val.toLowerCase().equals("cd") || val.toLowerCase().equals("dc")) {
            CoWTabeli(em, "c");
            CoWTabeli(em, "d");
        }
        else if (val.toLowerCase().equals("ad") || val.toLowerCase().equals("da")) {
            CoWTabeli(em, "a");
            CoWTabeli(em, "d");
        }
        else if (val.toLowerCase().equals("b")) {
            List<B> wynik1 = em.createQuery("SELECT b FROM B b")
                .getResultList();
            for(B klub : wynik1) {
                System.out.println("klub Id " + klub.getId_b() + " Nazwa " + klub.getNazwab());
            }
        }
        else if (val.toLowerCase().equals("d")) {
            List<D> wynik1 = em.createQuery("SELECT d FROM D d")
                .getResultList();
            for(D org : wynik1) {
                System.out.println("Organizacja Id " + org.getId_d() + " Nazwa " + org.getNazwaOrg());
            }
        }
        
        
    }
    public List<C> ZawodnicyTansiNiz(EntityManager em, int val) {
        System.out.println("Zapytanie Tansi Niz");
        List<C> wynik1 = em.createQuery("SELECT c FROM C c WHERE c.cena <= :val")
                .setParameter("val", val)
                .getResultList();
        for(C zawodnik : wynik1) {
            System.out.println("Zawodnik :" + zawodnik.getNazwaZawodnik() + " Cena :" + zawodnik.getCena());
        }
        return wynik1;
    }
    public List<Object []> ZawodnicyOrganizacji(EntityManager em, String org) {
        System.out.println("Zapytanie ZawodnicyOrganizacji");
        List<Object []> wynik2 = em.createQuery("SELECT c, d FROM D d JOIN d.ctabs c WHERE d.nazwaOrg LIKE :org")
                .setParameter("org", org)
                .getResultList();
        for(Object[] result : wynik2) {
            C zawodnik = (C) result[0];
            D organizacja = (D) result[1];
            System.out.println(organizacja.getNazwaOrg() + " Zawodnik :" + zawodnik.getNazwaZawodnik() + " Cena :" + zawodnik.getCena());
        }
        return wynik2;
    }
    public List<Object []> ZawodnicyKlubu(EntityManager em, String nazwaklubu) {
        System.out.println("Zapytanie ZawodnicyKlubu");
        List<Object []> wynik3 = em.createQuery("SELECT c, b FROM B b JOIN b.clist c WHERE b.nazwab LIKE :klub")
                .setParameter("klub", nazwaklubu)
                .getResultList();
        for(Object[] result : wynik3) {
            C zawodnik = (C) result[0];
            B klub = (B) result[1];
            System.out.println(klub.getNazwab() + " Zawodnik :" + zawodnik.getNazwaZawodnik() + " Cena :" + zawodnik.getCena());
        }
        return wynik3;
    }
    public void update(EntityManager em, String encja, Long id, int id2) {
        System.out.println("Metoda updatowania");
        Scanner myScanner = new Scanner(System.in);
        String nazwa;
        String val;
        if (encja.toLowerCase().equals("a")) {
            CoWTabeli(em, encja.toLowerCase());
            Akey akey = new Akey();
            akey.setId_a(id);
            akey.setId_a2(id2);
            A obj = em.find(A.class, akey);
            if (obj == null) {
                System.out.println("Nie ma instancji A o takich kluczach");
                return;
            }
            execGetters(obj);
            System.out.println("Podaj nazwe atrybutu do zmiany");
            nazwa = myScanner.nextLine();
            if (!getAttList(obj).contains(nazwa)) {
                System.out.println("Nie ma takiego atrybutu");
                return;
            }
                
            if (nazwa.toLowerCase().equals("nazwisko")) {
                System.out.println("Podaj wartosc atrybutu do zmiany");
                val = myScanner.nextLine();
                obj.setNazwisko(val);
                System.out.println("Zmieniono");
            }
            else if (nazwa.toLowerCase().equals("wiek")) {
                System.out.println("Podaj wartosc atrybutu do zmiany");
                val = myScanner.nextLine();
                obj.setWiek(Integer.valueOf(val));
                System.out.println("Zmieniono");
            }
            else if (nazwa.toLowerCase().equals("d")) {
                CoWTabeli(em, "d");
                System.out.println("Podaj wartosc klucza instancji encji d");
                val = myScanner.nextLine();
                D objD = em.find(D.class, Long.valueOf(val));
                if (objD == null) {
                    System.out.println("Nie ma instancji D o takim kluczu");
                    return;
                }
                if (objD.getA() == null) {
                    System.out.println("dodaje relacje do instancji d");
                    obj.setD(objD);
                }
                else {
                    System.out.println("instancja D jest w realcji z instancja A");
                    return;
                }
            }
            else 
                return;
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        }   
    }
    
    public void update(EntityManager em, String encja, Long id) {
        System.out.println("Metoda updatowania");
        Scanner myScanner = new Scanner(System.in);
        String nazwa;
        String val;
        
        if (encja.toLowerCase().equals("b")) {
            CoWTabeli(em, encja.toLowerCase());
            B obj = em.find(B.class, id);
            if (obj == null) {
                System.out.println("Nie ma instancji B o takim kluczu");
                return;
            }
            execGetters(obj);
            System.out.println("Podaj nazwe atrybutu do zmiany");
            nazwa = myScanner.nextLine();
            if (!getAttList(obj).contains(nazwa)) {
                System.out.println("Nie ma takiego atrybutu");
                return;
            }
            if (!nazwa.toLowerCase().equals("clist")) {
                System.out.println("Podaj wartosc atrybutu do zmiany");
                val = myScanner.nextLine(); 
            }
            val = "";
            if (nazwa.toLowerCase().equals("nazwab")) {
                obj.setNazwab(val);
                System.out.println("Zmieniono");
            }
            else if (nazwa.toLowerCase().equals("clist")) {
                CoWTabeli(em, "c");
                System.out.println("Podaj wartosc klucza instancji encji c");
                val = myScanner.nextLine();
                C objC = em.find(C.class, Long.valueOf(val));
                if (objC == null) {
                    System.out.println("Nie ma instancji C o takim kluczu");
                    return;
                }
                if (obj.getClist() != null && obj.getClist().contains(objC)) {
                    System.out.println("Instancja b jest juz w relacji z instancja c");
                    return;
                }
                obj.getClist().add(objC);
                System.out.println("Zmieniono relacje");
            }
            else 
                return;
            
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        }
        else if (encja.toLowerCase().equals("c")) {
            CoWTabeli(em, encja.toLowerCase());
            C obj = em.find(C.class, id);
            if (obj == null) {
                System.out.println("Nie ma instancji C o takim kluczu");
                return;
            }
                
            execGetters(obj);
            System.out.println("Podaj nazwe atrybutu do zmiany");
            nazwa = myScanner.nextLine();
            if (!getAttList(obj).contains(nazwa)) {
                System.out.println("Nie ma takiego atrybutu");
                return;
            }
            val = "";
            if (!nazwa.toLowerCase().equals("blist") && !nazwa.toLowerCase().equals("ds")) {
                System.out.println("Podaj wartosc atrybutu do zmiany");
                val = myScanner.nextLine(); 
            }
            if (nazwa.toLowerCase().equals("nazwazawodnik")) {
                obj.setNazwaZawodnik(val);
            }
            else if (nazwa.toLowerCase().equals("cena")) {
                obj.setCena(Integer.parseInt(val));
                System.out.println("Zmieniono");
            }
            else if (nazwa.toLowerCase().equals("blist")) {
                CoWTabeli(em, "b");
                System.out.println("Podaj wartosc klucza instancji encji b");
                val = myScanner.nextLine();
                B objB = em.find(B.class, Long.valueOf(val));
                if (objB == null) {
                    System.out.println("Nie ma instancji b o takim kluczu");
                    return;
                }
                    
                if (obj.getBlist() != null && obj.getBlist().contains(objB)) {
                    System.out.println("Instancja b jest juz w relacji z instancja c");
                    return;
                }
                obj.addKlub(objB);
                System.out.println("Zmieniono relacje");
            }
            else if (nazwa.toLowerCase().equals("ds")) {
                CoWTabeli(em, "d");
                System.out.println("Podaj wartosc klucza instancji encji d");
                val = myScanner.nextLine();
                D objD = em.find(D.class, Long.valueOf(val));
                if (objD == null) {
                    System.out.println("Nie ma instancji D o takim kluczu");
                    return;
                }
                
                if (obj.getDs() != null && obj.getDs().equals(objD)) {
                    System.out.println("Instancja b jest juz w relacji z instancja D");
                    return;
                }
                obj.setDs(objD);
                System.out.println("Zmieniono relacje");
            }
            else 
                return;
            
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        }
        else if (encja.toLowerCase().equals("d")) {
            CoWTabeli(em, encja.toLowerCase());
            D obj = em.find(D.class, id);
            if (obj == null) {
                System.out.println("Nie ma instancji D o takim kluczu");
                return;
            }
            execGetters(obj);
            System.out.println("Podaj nazwe atrybutu do zmiany");
            nazwa = myScanner.nextLine();
            if (!getAttList(obj).contains(nazwa)) {
                System.out.println("Nie ma takiego atrybutu");
                return;
            }
            val = "";
            if (!nazwa.toLowerCase().equals("a") && !nazwa.toLowerCase().equals("ctabs")) {
                System.out.println("Podaj wartosc atrybutu do zmiany");
                val = myScanner.nextLine(); 
            }
            if (nazwa.toLowerCase().equals("nazwaorg")) {
                obj.setNazwaOrg(val);
                System.out.println("Zmieniono");
            }
            else if (nazwa.toLowerCase().equals("a")) {
                CoWTabeli(em, "a");
                System.out.println("Podaj wartosc klucza instancji encji a");
                System.out.println("Podaj klucz/klucze <Id klucza> <Id klucza 2>");
                String klucze = myScanner.nextLine();
                String [] tabKlucze = klucze.split(" ");
                Integer my_id2 = null;
                Long my_id1 = null;
                if(klucze.equals("") || !klucze.contains(" ")){
                    System.out.println("Koniec dzialania");
                    return;
                }
                try {
                    my_id1 = Long.valueOf(tabKlucze[0]);
                    my_id2 = Integer.valueOf(tabKlucze[1]);
                } catch(NumberFormatException e) {
                    System.out.println("Bledne wprowadzone klucze");
                    return;
                }
                Akey akey = new Akey();
                akey.setId_a(my_id1);
                akey.setId_a2(my_id2);
                A objA = em.find(A.class, akey);
                if (objA == null) {
                    System.out.println("Nie ma instancji a o takich kluczach");
                    return;
                }
                if (obj.getA() != null || objA.getD() != null) {
                    System.out.println("Instancja b jest juz w relacji z instancja a");
                    return;
                }
                obj.addWlasciciel(objA);
                System.out.println("Zmieniono relacje");
            }
            else if (nazwa.toLowerCase().equals("ctabs")) {
                CoWTabeli(em, "c");
                System.out.println("Podaj wartosc klucza instancji encji C");
                val = myScanner.nextLine();
                C objC = em.find(C.class, Long.valueOf(val));
                if (objC == null) {
                    System.out.println("Nie ma instancji C o takim kluczu");
                    return;
                }
                if (obj.getCtabs().contains(objC)) {
                    System.out.println("Instancja D jest juz w relacji z instancja C");
                    return;
                }
                obj.addZawodnik(objC);
                System.out.println("Zmieniono relacje");
            }
            else 
                return;
            
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        }
        else {
            System.out.println("Nie ma takiego typu");
        }
        
    }
    // mozna usunac instancje a
    public void remove(EntityManager em, String encja, Long id) {
        System.out.println("Metoda usuwania");
        Scanner myScanner = new Scanner(System.in);
        Long id_1;
        Long id_2;
        encja = encja.toLowerCase();
        if (encja.equals("bc") || encja.equals("cb")) {     //usuwanie relacji c z b
            CoWTabeli(em, "b");
            CoWTabeli(em, "c");
            System.out.println("Podaj id zawodnika");
            id_1 = myScanner.nextLong();
            System.out.println("Podaj id klubu");
            id_2 = myScanner.nextLong();
            B b = em.find(B.class, id_2);
            C c = em.find(C.class, id_1);
            if (c == null || b == null) {
                System.out.println("Nie istnieje instancja c lub b");
                return;
            }
            b.getClist().remove(c);
            try {
            em.getTransaction().begin();
            em.merge(b);
            em.getTransaction().commit();
            } catch (PersistenceException e) {
                System.out.println("Blad \n" + e.getMessage());
            }
            System.out.println("Usunieto relacje");
        }
        else if (encja.equals("cd") || encja.equals("dc")) {     //usuwanie relacji c z d
            CoWTabeli(em, "d");
            CoWTabeli(em, "c");
            System.out.println("Podaj id zawodnika");
            id_1 = myScanner.nextLong();
            System.out.println("Podaj id organizacji");
            id_2 = myScanner.nextLong();
            D d = em.find(D.class, id_2);
            C c = em.find(C.class, id_1);
            if (c == null || d == null) {
                System.out.println("Nie istnieje instancja c lub d");
                return;
            }
            c.setDs(null);              //dla one to many trzeba mergowac i nullowac obie encje
            d.getCtabs().remove(c);
            try {
            em.getTransaction().begin();
            em.merge(c);
            em.merge(d);
            em.getTransaction().commit();
            } catch (PersistenceException e) {
                System.out.println("Blad \n" + e.getMessage());
            }
            System.out.println("Usunieto relacje");
        }
        else if (encja.equals("b")) {     
            CoWTabeli(em, "b");
            B obj = em.find(B.class, id);
            if (obj != null) {
                if (obj.getClist().isEmpty()) {     //brak powizania z tabela C
                   System.out.println("Brak relacji z instancjami encji c mozna usunac");
                   try {
                    em.getTransaction().begin();
                    em.remove(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                    } 
                }
                else {
                    System.out.println("Nie mozna usunac instancji encji B poniewaz jest w relacji z instancjami encji C");
                    /*
                    obj.setClist(null);
                    try {
                    em.getTransaction().begin();
                    em.merge(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    } 
                    try {
                    em.getTransaction().begin();
                    em.remove(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    } 
                    */
                }
                
            }
                
            else 
                System.out.println("Nieprawidlowy klucz");
        }
        else if (encja.equals("c")) {     //usuwanie instancji c
            CoWTabeli(em, "c");
            C obj = em.find(C.class, id);
            if (obj != null) {
                if (obj.getBlist().isEmpty()) {     //brak powizania z tabela C
                   System.out.println("Brak relacji z instancjami encji B mozna usunac");
                   try {
                    em.getTransaction().begin();
                    em.remove(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    } 
                }
                else {
                    System.out.println("Istnieje relacja z instancjami encji B nie mozna usunac");
                    /*
                    obj.setBlist(null);
                    try {
                    em.getTransaction().begin();
                    em.merge(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    } 
                    try {
                    em.getTransaction().begin();
                    em.remove(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    } 
                    */
                }
                
            }
                
            else 
                System.out.println("Nieprawidlowy klucz");
        }
        else if (encja.equals("d")) {
            CoWTabeli(em, encja);
            
            D obj = em.find(D.class, id);
            if (obj != null) {
                if (obj.getCtabs().isEmpty() && obj.getA() == null) {     
                   System.out.println("Brak relacji z instancjami encji C i A mozna usunac");
                   try {
                    em.getTransaction().begin();
                    em.remove(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    } 
                }
                else if (obj.getCtabs().isEmpty() && obj.getA() != null) {     //kaskadowe usuwanie a i d
                    System.out.println("Instancja D jest w relacji z instancja a usuwam instancje a i d");
                    try {
                        A objA = obj.getA();
                        em.getTransaction().begin();
                        em.remove(objA);
                        em.remove(obj);
                        em.getTransaction().commit();
                        System.out.println("Usunieto");
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    } 
                }
                else if (!obj.getCtabs().isEmpty() && obj.getA() != null) {     //relacje z a i c
                    System.out.println("Instancja D jest w relacji z instancja a i c chcesz usunac instancje a i d? Yes/No");
                    String odp = myScanner.nextLine();
                    if (odp.toLowerCase().equals("yes")) {
                        try {
                            A objA = obj.getA();
                            obj.usunWszystkichZawodnikow(em);
                            em.getTransaction().begin();
                            em.remove(objA);
                            em.remove(obj);
                            em.getTransaction().commit();
                            System.out.println("Usunieto");
                        } catch (PersistenceException e) {
                            System.out.println("Blad \n" + e.getMessage());
                            //em.getTransaction().rollback(); // mozna wyrzucic
                        }
                    }
                     
                }
                else {
                    System.out.println("Istnieje relacja z instancjami encji C lub A nie mozna usunac");
                    /*
                    obj.usunWszystkichZawodnikow();
                    try {
                    em.getTransaction().begin();
                    em.merge(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    } 
                    try {
                    em.getTransaction().begin();
                    em.remove(obj);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    }
                    */
                }
                
            }
                
            else 
                System.out.println("Nieprawidlowy klucz");
        }
        
    }
    //kaskadowe usuwanie a i d
    public void remove(EntityManager em, String encja, Long id, int id2) {
        System.out.println("Metoda usuwania");
        encja = encja.toLowerCase();
        if (encja.equals("a")) {
            CoWTabeli(em, encja);
            Akey akey = new Akey();
            akey.setId_a(id);
            akey.setId_a2(id2);
            A obj = em.find(A.class, akey);
            if (obj == null) {
                System.out.println("Nie ma takiej instancji a");
                return;
            }
            if (obj.getD() != null) {
                System.out.println("Instancja a jest w relacji z d jesli d nie jest w relacji z c to zostanie usunieta instancja a i d");
                if (obj.getD().getCtabs().isEmpty()) {
                    System.out.println("Instancja d nie jest w relacji z instancjami c usuwam a i d");
                    try {
                    em.getTransaction().begin();
                    D d = obj.getD();
                    em.remove(obj);
                    em.remove(d);
                    em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        System.out.println("Blad \n" + e.getMessage());
                        //em.getTransaction().rollback(); // mozna wyrzucic
                    }
                }
                else {
                    System.out.println("Instancja d jest w relacji z instancjami c nie mozna usunac instancji a i d");
                    System.out.println("Jesli chcesz usunac tylko instancje a Yes/No");
                    Scanner myScanner = new Scanner(System.in);
                    String odp = myScanner.nextLine();
                    odp = odp.toLowerCase();
                    if (odp.equals("yes")) {
                        em.getTransaction().begin();
                        em.remove(obj);
                        em.getTransaction().commit();
                    }
                }
                
            }
            else {
                System.out.println("Usuwam instancje a");
                em.getTransaction().begin();
                em.remove(obj);
                em.getTransaction().commit();
            }
            
        }
        else if (encja.equals("ad") || encja.equals("da")) {     //usuwanie relacji a z d
            System.out.println("Usuwanie relacji miedzy a i d");
            CoWTabeli(em, "d");
            CoWTabeli(em, "a");

            Akey akey = new Akey();
            akey.setId_a(id);
            akey.setId_a2(id2);
            A a = em.find(A.class, akey);
            if (a == null) {
                System.out.println("Nie ma takiej instancji a");
                return;
            }
            a.setD(null);
            try {
            em.getTransaction().begin();
            em.merge(a);
            em.getTransaction().commit();
            } catch (PersistenceException e) {
                System.out.println("Blad \n" + e.getMessage());
                //em.getTransaction().rollback(); // mozna wyrzucic
            }
            System.out.println("Usunieto relacje a z d");
        }
    }
    public Object createObj(EntityManager em, String encja) {
        encja = encja.toLowerCase();
        System.out.println("Metoda Tworzenia");
        Scanner myScanner = new Scanner(System.in);
        String nazwa;
        String val;
        if (encja.equals("a")) {
            System.out.println("Podaj nazwe wlasciciela");
            nazwa = myScanner.nextLine();
            System.out.println("Podaj wiek wlasciciela");
            val = myScanner.nextLine();
            A obj = new A(Integer.valueOf(val), nazwa);
            persistObj(em, obj);
            return obj;
        }
        else if (encja.equals("b")) {
            System.out.println("Podaj nazwe klubu");
            nazwa = myScanner.nextLine();
            B obj = new B(nazwa);
            persistObj(em, obj);
            return obj;
        }
        else if (encja.equals("c")) {
            System.out.println("Podaj nazwe zawodnika");
            nazwa = myScanner.nextLine();
            System.out.println("Podaj cene zawodnika");
            val = myScanner.nextLine();
            C obj = new C(Integer.valueOf(val), nazwa);
            persistObj(em, obj);
            return obj;
        }
        else if (encja.equals("d")) {
            System.out.println("Podaj nazwe organizacji");
            nazwa = myScanner.nextLine();
            D obj = new D(nazwa);
            persistObj(em, obj);
            return obj;
        }
        return null;
    }
    public Object getObj(EntityManager em, String encja, Long id, int id2) {
        encja = encja.toLowerCase();
        System.out.println("Metoda zwracania instancji/czytania");
        if (encja.equals("a")) {
            Akey akey = new Akey();
            akey.setId_a(id);
            akey.setId_a2(id2);
            A obj = em.find(A.class, akey);
            if (obj == null) {
                System.out.println("Nie ma takiej instancji");
            }
            return obj;
        }
        return null;
    }
    public Object getObj(EntityManager em, String encja, Long id) {
        System.out.println("Metoda zwracania instancji/czytania");
        encja = encja.toLowerCase();
        if (encja.equals("b")) {
            B obj = em.find(B.class, id);
            if (obj == null) {
                System.out.println("Nie ma takiej instancji");
            }
            return obj;
        }
        else if (encja.equals("c")) {
            C obj = em.find(C.class, id);
            if (obj == null) {
                System.out.println("Nie ma takiej instancji");
            }
            return obj;
        }
        else if (encja.equals("d")) {
            D obj = em.find(D.class, id);
            if (obj == null) {
                System.out.println("Nie ma takiej instancji");
            }
            return obj;
        }
        else {
            return null;
        }
    }
    //metody pomocnicze
    public void persistObj(EntityManager em, Object obj) {
        if (obj != null) {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        }
    }
    public void execGetters(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        System.out.println("Dostepne metody");
        for (Method method: methods) {
            if(method.getName().startsWith("get") && method.getParameterCount() == 0) {
                try {
                    System.out.println(method.getName() + ": " + method.invoke(obj));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public List<String> getAttList(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        List<String> wynik = new ArrayList();
        for (Method method: methods) {
            if(method.getName().startsWith("get") && method.getParameterCount() == 0) {
                try {
                   wynik.add(method.getName().substring(3).toLowerCase());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return wynik;
    }
}
