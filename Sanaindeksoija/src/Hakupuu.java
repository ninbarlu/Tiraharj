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
    
    private Node juuri;
    private String[] rivit;
    private int vapaa;
    
    private String kelvot = "abcdefghijklmnopqrstuvwxyzåäö0123456789";
    
    public Hakupuu() {
        this.juuri = new Node();
        this.rivit = new String[100];
        this.vapaa = 0;
    }
    
/**
 * Metodi, joka lukee annetun tiedoston läpi ja käynnistää rivien käsittelyn.
 * 
 * @param   tdstokahva  sisäänlukutiedoston nimi, tulee sijaita samassa hakemistossa ohjelman kanssa
 * @param   tdstonro    sisäänluettavan tiedoston järjestysnumero (0-n)
 * 
 * @return  rivilkm     sisäänlukutiedostosta luettujen rivien lukumäärä
 */    
    public int lueTdsto(File tdstokahva, int tdstonro) throws Exception {

        int rivilkm = 0;
        String rivi;
        Scanner syote = new Scanner(tdstokahva);
        while (syote.hasNextLine()) {
            rivi = syote.nextLine().trim();
            tallennaRivi(rivi);
            kasitteleRivi(rivi, tdstonro, vapaa-1);
            rivilkm++;
        }
        return rivilkm;
    }

    
/**
 * Metodi, joka tallentaa luetun rivin hakutaulukkoon.
 * 
 * @param   rivi        sisäänlukutiedostosta luettu rivi
 */    
    private void tallennaRivi(String rivi) {
        String[] apu = {};
        if (vapaa == rivit.length) {                                              // rivit-taulukko täynnä, tuplataan tila
            apu = new String[2*rivit.length];
            for (int i = 0; i < rivit.length; i++)
                apu[i] = rivit[i];
            rivit = apu;
        }
        rivit[vapaa] = rivi;
        vapaa++;
    }
    
/**
 * Metodi, joka käsittelee luetun rivin tiedot ja tallentaa löytyvät merkkijonot hakupuuhun.
 * 
 * @param   rivi        sisäänlukutiedostosta luettu rivi
 * @param   tdstonro    sisäänluettavan tiedoston järjestysnumero (0-n)
 */    
    private void kasitteleRivi(String rivi, int tdstonro, int rivinro) throws Exception {
        Node vanhempi = this.juuri;
        int ind;
        boolean sananalku = true;
        for (int i = 0; i < rivi.length(); i++) {
            if (rivi.charAt(i) == ' ') {                                        // tarkistetaan pois tyhjät merkit, tarkoittavat sanan alkamista seuraavasta merkistä
                sananalku = true;
                continue;
            }
            if (!sananalku)                                                     // aloitetaan tallennus vain rivin ja sanojen alusta
                continue;
            
            if (kelvot.indexOf(rivi.toLowerCase().charAt(i)) < 0)               // merkki ei ole suomalaisen aakkoston kirjain tai numero
                continue;
                
            sananalku = false;
            for (int j = i; j < rivi.length(); j++) {                           // tallennetaan loppurivi hakupuuhun
                ind = selvitaInd((int)rivi.toLowerCase().charAt(i));    
                if (vanhempi.getLapsi(ind) == null) {
                    // jatka tästä: uuden solmun luonti, olemassa olevan solmun käsittely, listaan tallennus
                }
            }
                
            
            
        }
    }
    
/**
 * Metodi, joka selvittää annetun merkin sijainnin solmun lapset-taulukossa.
 * 
 * @param   arvo        selvitettävän merkin Unicode-arvo
 * 
 * @return  arvo        selvitettävän merkin indeksi lapset-taulukossa
 */        
    private int selvitaInd(int arvo) {
        if (arvo < 60)                                                          // numeraalit
            return arvo-48;                                                     // sijoitetaan taulukon soluihin 0-9
        if (arvo < 130)                                                         // kirjaimet a-z
            return arvo-87;                                                     // sijoitetaan taulukon soluihin 10-35
        if (arvo == 228)                                                        // kirjain ä
            return 37;                                                          // sijoitetaan soluun 37
        if (arvo == 246)                                                        // kirjain ö
            return 38;                                                          // sijoitetaan soluun 38
        return 36;                                                              // kirjain å sijoitetaan soluun 36
    }

}
