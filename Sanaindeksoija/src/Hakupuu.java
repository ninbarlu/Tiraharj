// Tietorakenteiden harjoitustyö, Nina Bärlund

import java.util.Scanner;
import java.io.*;

/**
 * Luokka, joka toteuttaa Trie-hakupuun ja sen tarvitsemat toiminnot Sanaindeksoijaa varten
 * 
 * @author  Nina Bärlund
 * @version 0.1
 * @since   29.5.2012
 */
public class Hakupuu {
    
    public Hakupuu() {
        
    }
    
/**
 * Metodi, joka lukee annetun tiedoston läpi ja käynnistää rivien käsittelyn.
 * 
 * @param   tdsto   sisäänlukutiedoston nimi, tulee sijaita samassa hakemistossa ohjelman kanssa
 * 
 * @return  rivilkm sisäänlukutiedostosta luettujen rivien lukumäärä
 */    
    public int lueTdsto(File tdstokahva) throws Exception {

        int rivilkm = 0;
        Scanner syote = new Scanner(tdstokahva);
        while (syote.hasNextLine()) {
            kasitteleRivi(new Scanner(syote.nextLine()));
            rivilkm++;
        }
        return rivilkm;
    }

/**
 * Metodi, joka käsittelee luetun rivin tiedot ja tallentaa löytyvät merkkijonot hakupuuhun.
 * 
 * @param   rivi    sisäänlukutiedostosta luettu rivi
 */    
    private void kasitteleRivi(Scanner rivi) throws Exception {

    }

}
