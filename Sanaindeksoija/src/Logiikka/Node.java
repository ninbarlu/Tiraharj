package Logiikka;

// Tietorakenteiden harjoitustyö, Nina Bärlund

/**
 * Luokka, joka toteuttaa Trie-hakupuun solmut Sanaindeksoijaa varten.
 * 
 * @author  Nina Bärlund
 * @version 1.1
 * @since   11.6.2012
 * 
 */
public class Node {
    
/**
 * hakupuusolmun avain: merkki
 */
    private char merkki;                                                        // solmun merkki = olion avaintieto
/**
 * linkitetyn listan ensimmäinen solmu, lista sisältää tiedon niistä sisäänlukutiedostojen riveistä, joissa hakupuusta
 * tähän asti luettu merkkijono esiintyy
 */
    private Lista ekaEsitys;                                                    // esiintymät sisältävän linkitetyn listan ensimmäinen olio
/**
 * linkitetyn listan viimeinen solmu
 */
    private Lista vikaEsitys;                                                   // esiintymät sisältävän linkitetyn listan viimeinen olio
/**
 * hakupuusolmun lapsisolmut
 */
    private Node[] lapset;                                                      // solmun lapsisolmut sisältävä taulukko
    
/**
 * Node-luokan konstruktori.
 * 
 * @param   merkki      luotavan solmun avain
 * 
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
 * 
 */        
   public Node getLapsi(int i) {
        return lapset[i];                                                       // palauttaa annetun taulukon solun sisällön (solmu tai null)
    }
    
/**
 * Metodi, joka tallentaa annetun solmun lapset-taulukkoon.
 * 
 * @param   i           annetun solmun tallennuspaikan osoittava indeksi
 * @param   lapsi       annettu solmu
 * 
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
 * 
 * @see     Logiikka.Lista#getTdsto()
 * @see     Logiikka.Lista#getTdstonrivi()
 * @see     Logiikka.Lista#setSeur(Logiikka.Lista)
 * 
 */        
    public void setRivi(int tdsto, int tdstonrivi, int hakurivi) {
        Lista uusi;
        if (ekaEsitys == null) {                                                // jos ensimmäinen esiintymä käsiteltävälle solmulle
            uusi = new Lista(tdsto, tdstonrivi, hakurivi);                      // luo uuden esiintymärivin olion
            ekaEsitys = uusi;
            vikaEsitys = uusi;
        } else                                                                  // jos solmulla jo aiempia esiintymiä
            if (tdsto != vikaEsitys.getTdsto() || tdstonrivi != vikaEsitys.getTdstonrivi()) { // ei tallenneta kahteen kertaan samaa solmua listaan
                uusi = new Lista(tdsto, tdstonrivi, hakurivi);                  // luo uuden esiintymärivin olion
                vikaEsitys.setSeur(uusi);                                       // edelliseen viimeiseen viittaus uuteen solmuun
                vikaEsitys = uusi;                                              // uusi solmu linkitetyn listan viimeiseksi
            }
    }
    
/**
 * Metodi, joka palauttaa annetun solmun avaimen eli merkin.
 * 
 * @return  merkki      pyydetty solmun avaimen arvo
 * 
 */        
    public char getMerkki() {
        return this.merkki;                                                     // palauttaa annetun solmun merkin
    }
    
/**
 * Metodi, joka palauttaa annetun solmun linkitetyn listan.
 * 
 * @return  lista       linkitetyn listan ensimmäinen solmu
 * 
 */        
    public Lista getLista() {
        return this.ekaEsitys;                                                  // palauttaa annetun solmun linkitetyn listan ensimmäisen solmun
    }
}
