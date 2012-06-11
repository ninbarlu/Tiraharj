package Logiikka;

// Tietorakenteiden harjoitustyö, Nina Bärlund

import java.util.Scanner;
import java.io.*;

/**
 * Luokka, joka toteuttaa Trie-hakupuun ja sen tarvitsemat toiminnot Sanaindeksoijaa varten
 * 
 * @author  Nina Bärlund
 * @version 1.1
 * @since   11.6.2012
 */
public class Hakupuu {
    
    private Node juuri;                                                         // hakupuun juurisolmu
    private String[] rivit;                                                     // hakupuuhun luetut tekstirivit
    private int vapaa;                                                          // seuraava vapaa tekstirivitaulukon paikka
    
    public Hakupuu() {                                                          // hakupuun konstruktori
        this.juuri = new Node();
        this.rivit = new String[100];                                           // tekstirivitaulukon aloituskoko 100
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
        Scanner syote = new Scanner(tdstokahva);                                // sisäänluettava tiedosto
        while (syote.hasNextLine()) {                                           // kunnes tiedoston sisältö loppuu
            rivi = syote.nextLine().trim();
            if (!rivi.isEmpty()) {                                              // ei käsitellä tyhjiä rivejä
                tallennaRivi(rivi);                                             // rivin tallennus tekstirivitaulukkoon
                rivilkm++;
                kasitteleRivi(rivi, tdstonro, rivilkm, vapaa-1);                // rivin käsittely eli tallennus hakupuuhun
            }
        }
        return rivilkm;                                                         // palauttaa käsiteltyjen rivien lukumäärän; jos tiedosto on tyhjä, palauttaa 0:n
    }

    
/**
 * Metodi, joka tallentaa luetun rivin hakutaulukkoon.
 * 
 * @param   rivi        sisäänlukutiedostosta luettu rivi
 */    
    private void tallennaRivi(String rivi) {
        String[] apu = {};
        if (vapaa == rivit.length) {                                            // rivit-taulukko täynnä, tuplataan tila
            apu = new String[2*rivit.length];
            for (int i = 0; i < rivit.length; i++)                              // kopioidaan jo tallennetut rivit uuteen taulukkoon
                apu[i] = rivit[i];
            rivit = apu;
        }
        rivit[vapaa] = rivi;                                                    // uuden rivin tallennus
        vapaa++;
    }
    
/**
 * Metodi, joka käsittelee luetun rivin tiedot ja tallentaa löytyvät merkkijonot hakupuuhun.
 * 
 * @param   rivi        sisäänlukutiedostosta luettu rivi
 * @param   tdstonro    sisäänluettavan tiedoston järjestysnumero (0-n)
 * @param   tdstonrivi  sisäänluettavan tiedoston käsiteltävän rivin järjestysnumero (1-n)
 * @param   rivinro     indeksi hakutaulukon soluun, johon rivi on tallennettu
 */    
    public void kasitteleRivi(String rivi, int tdstonro, int tdstonrivi, int rivinro) throws Exception { // aikatestausta varten public
        Node vanhempi = this.juuri;                                             // lähdetään liikkeelle juuresta
        boolean sananalku = true;                                               // merkitään seuraava löytyvä kelvollinen merkki sanan aloittavaksi
        
        for (int i = 0; i < rivi.length(); i++) {                               // käydään läpi kaikki rivin merkit
            if (rivi.charAt(i) == ' ') {                                        // tarkistetaan pois tyhjät merkit
                sananalku = true;                                               // merkitään seuraava löytyvä kelvollinen merkki sanan aloittavaksi
                continue;
            }
            if (!sananalku)                                                     // aloitetaan tallennus vain rivin ja sanojen alusta
                continue;
            
            if (selvitaKelpo(rivi.toLowerCase().charAt(i)) < 0)                 // merkki ei ole suomalaisen aakkoston kirjain tai numero
                continue;
                
            sananalku = false;                                                  // merkitään seuraava merkki keskellä sanaa olevaksi
            for (int j = i; j < rivi.length(); j++) {                           // tallennetaan loppurivi hakupuuhun, käydään läpi rivi sanan 1. merkistä lähtien
                char merkki = rivi.toLowerCase().charAt(j);                     // luetaan käsiteltävä merkki
                if (selvitaKelpo(merkki) < 0)                                   // merkki ei ole suomalaisen aakkoston kirjain tai numero
                    continue;
                int ind = selvitaInd((int)merkki);                              // haetaan merkkiä vastaava lapsitaulukon indeksiarvo
                if (vanhempi.getLapsi(ind) == null)
                    vanhempi.setLapsi(ind, new Node(merkki));                   // luodaan uusi lapsisolmu ja tallennetaan se vanhemman taulukkoon
                vanhempi = vanhempi.getLapsi(ind);                              // käsittelyyn merkkiä vastaava solmu lapsitaulukosta
                vanhempi.setRivi(tdstonro, tdstonrivi, rivinro);                // tallennetaan rivitieto käsiteltävän solmun linkitettyyn listaan
            }
            vanhempi = this.juuri;                                              // palataan hakupuun alkuun seuraavan sanan alkua varten    
        }                                                                       // käsiteltävän rivin loppu
    }
    
/**
 * Metodi, joka selvittää annetun merkin kelvollisuuden eli onko merkki hakupuuhun tallennettava.
 * 
 * @param   merkki      selvitettävä merkki
 * 
 * @return  arvo        selvitettävän merkin indeksi merkkijonossa kelvot; jos pienempi kuin 0, ei merkkiä löytynyt kelvollisten merkkien joukosta
 */        
    private int selvitaKelpo(char merkki) {
        String kelvot = "abcdefghijklmnopqrstuvwxyzåäö0123456789";              // hakupuuhun kelpaavat merkit
        return kelvot.indexOf(merkki);
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

/**
 * Metodi, joka etsii annetun merkkijonon hakupuusta.
 * 
 * @param   jono        etsittävä merkkijono
 * 
 * @return  arvo        selvitettävän merkin indeksi lapset-taulukossa
 */            
    public Lista etsiJono(String jono) {
        Node vanhempi = this.juuri;                                             // lähdetään liikkeelle juuresta
        for (int i = 0; i < jono.length(); i++) {
            char merkki = jono.toLowerCase().charAt(i);                         // luetaan käsiteltävä merkki
            if (selvitaKelpo(merkki) < 0)                                       // merkki ei ole suomalaisen aakkoston kirjain tai numero
                continue;
            int ind = selvitaInd((int)merkki);                                  // haetaan merkkiä vastaava lapsitaulukon indeksiarvo
            if (vanhempi.getLapsi(ind) == null)                                 // jos merkkiä ei löydy, ei haettua merkkijonoa löydy hakupuusta
                return null;
            vanhempi = vanhempi.getLapsi(ind);                                  // käsittelyyn merkkiä vastaava solmu lapsitaulukosta
        }
        return vanhempi.getLista();                                             // palautetaan haetun merkkijonon viimeisen merkin esiintymälista
    }

/**
 * Metodi, joka antaa halutun tallennetun tekstirivin hakupuusta.
 * 
 * @param   ind         tekstirivin indeksi
 * 
 * @return  merkkijono  tekstirivi annetun indeksin solussa
 */            
    public String getRivi(int i) {
        return rivit[i];
    }
    
/**
 * Metodi, joka antaa hakupuun juuren.
 * 
 * @return  node    hakupuun juurisolmu
 */            
    public Node getJuuri() {
        return this.juuri;
    }
/**
 * Hakupuun tulostuksen käynnistysmetodi.
 * 
 * @return  tulostettava hakupuu merkkijonomuodossa
 */        
    public String toString() {
        return this.toStringHelp(this.juuri, "");
    }

/**
 * Hakupuun tulostusmetodi.
 * 
 * @param   solmu       tulostuksessa oleva solmu
 * @param   sisennys    solmun tulostusasettelu
 * 
 * @return  str         tulostettava hakupuu merkkijonomuodossa
 */        
    private String toStringHelp(Node solmu, String sisennys) {                  // sovellettu Esa Junttilan tulostusmetodista http://www.cs.helsinki.fi/u/ejunttil/opetus/tiraharjoitus/treeprint.txt
	if (solmu == null) return "";
	else {
		String str = "";
		str += "\n" + sisennys + "--->" + solmu.getMerkki();
		for (int i = 0; i < 39; i++) {
			str += toStringHelp(solmu.getLapsi(i), sisennys + "     |");
		}
		str += "\n" + sisennys;

		return str;
	}
    }
}
