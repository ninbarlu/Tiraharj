package Logiikka;


import Logiikka.Lista;

// Tietorakenteiden harjoitustyö, Nina Bärlund

/**
 * Luokka, joka toteuttaa Trie-hakupuun solmut Sanaindeksoijaa varten
 * 
 * @author  Nina Bärlund
 * @version 1.0
 * @since   8.6.2012
 */
public class Node {
    
    private char merkki;                                                        // solmun merkki = olion avaintieto
    private Lista ekaEsitys;                                                    // esiintymät sisältävän linkitetyn listan ensimmäinen olio
    private Lista vikaEsitys;                                                   // esiintymät sisältävän linkitetyn listan viimeinen olio
    private Node[] lapset;                                                      // solmun lapsisolmut sisältävä taulukko
    
/**
 * Node-luokan konstruktori.
 * 
 * @param   merkki      luotavan solmun avain
 */        
    public Node(char merkki) {
        this.merkki = merkki;
        this.ekaEsitys = null;
        this.vikaEsitys = null;
        this.lapset = new Node[39];                                             // hyväksyttäviä merkkejä 39 kpl
    }
    
/**
 * Node-luokan parametriton konstruktori, perustaa hakupuun juurisolmun.
 * 
 */        
    public Node() {
        this(' ');                                                              // käynnistää parametrillisen konstruktorin
    }
    
    
 /**
 * Metodi, joka palauttaa pyydetyssä lapset-taulukon alkiossa olevan solmun.
 * 
 * @param   i           pyydetyn solmun sijainnin osoittava indeksi
 * 
 * @return  lapset[i]   pyydetty solmu
 */        
   public Node getLapsi(int i) {
        return lapset[i];                                                       // palauttaa annetun taulukon solun sisällön (solmu tai null)
    }
    
/**
 * Metodi, joka tallentaa annetun solmun lapset-taulukkoon.
 * 
 * @param   i           annetun solmun tallennuspaikan osoittava indeksi
 * @param   lapsi       annettu solmu
 */        
    public void setLapsi(int i, Node lapsi) {
        lapset[i] = lapsi;                                                      // asettaa annetun solmun taulukon annettuun soluun
    }
    
/**
 * Metodi, joka tallentaa solmuun sen esiintymät.
 * 
 * @param   tdsto       esiintymän sisältävän tiedoston järjestysnumero (0-n)
 * @param   tdstonrivi  esiintymän sisältävän tiedostonrivin järjestysnumero (1-n)
 * @param   hakurivi    esiintymän sisältävä hakupuun rivitaulukon rivinumero (0-n) 
 */        
    public void setRivi(int tdsto, int tdstonrivi, int hakurivi) {
        Lista uusi = new Lista(tdsto, tdstonrivi, hakurivi);                    // luo uuden esiintymärivin olion
        if (ekaEsitys == null) {                                                // jos ensimmäinen esiintymä käsiteltävälle solmulle
            ekaEsitys = uusi;
            vikaEsitys = uusi;
        } else {                                                                // jos solmulla jo aiempia esiintymiä
            vikaEsitys.setSeur(uusi);                                           // edelliseen viimeiseen viittaus uuteen solmuun
            vikaEsitys = uusi;                                                  // uusi solmu linkitetyn listan viimeiseksi
        }
    }
    
/**
 * Metodi, joka palauttaa annetun solmun avaimen eli merkin.
 * 
 * @return  merkki      pyydetty solmun avaimen arvo
 */        
    public char getMerkki() {
        return this.merkki;                                                     // palauttaa annetun solmun merkin
    }
    
/**
 * Metodi, joka palauttaa annetun solmun linkitetyn listan.
 * 
 * @return  lista       linkitetyn listan ensimmäinen solmu
 */        
    public Lista getLista() {
        return this.ekaEsitys;                                                  // palauttaa annetun solmun linkitetyn listan ensimmäisen solmun
    }
}
