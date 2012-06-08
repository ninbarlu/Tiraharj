package Logiikka;

// Tietorakenteiden harjoitustyö, Nina Bärlund

/**
 * Luokka, joka toteuttaa linkitetyn listan Trie-hakupuun esiintymien tallettamista varten
 * 
 * @author  Nina Bärlund
 * @version 1.0
 * @since   8.6.2012
 */
public class Lista {
    
    private int tdsto;                                                          // esiintymän sisältävän sisäänlukutiedoston järjestysnumero (0-n)
    private int tdstonrivi;                                                     // esiintymän sisältävän tiedostonrivin järjestysnumero (1-n)
    private int hakurivi;                                                       // esiintymän sisältävä hakupuun tekstitaulukon rivinumero (0-n)
    private Lista seurLista;                                                    // listan solmua seuraava listan solmu
    
/**
 * Lista-luokan konstruktori.
 * 
 * @param   tdsto       sisäänlukutiedoston järjestysnumero (0-n)
 * @param   tdstonrivi  tiedostonrivin järjestysnumero (1-n)
 * @param   hakurivi    rivin järjestysnumero hakupuuhun luettujen rivien joukossa (0-n)
 */        
    public Lista(int tdsto, int tdstonrivi, int hakurivi) {
        this.tdsto = tdsto;
        this.tdstonrivi = tdstonrivi;
        this.hakurivi = hakurivi;
        this.seurLista = null;
    }
    
/**
 * Metodi, joka asettaa parametrina annetun solmun linkitetyn listan viimeiseksi.
 * 
 * @param   seur        uusi solmu
 */        
    public void setSeur(Lista seur) {
        this.seurLista = seur;                                                  // annettu solmu käsiteltävän solmun seuraajaksi
    }

/**
 * Metodi, joka antaa annetun solmun tiedostonumeron.
 * 
 * @return  tdsto       tiedoston järjestysnumero
 */        
    public int getTdsto() {
        return this.tdsto;
    }

/**
 * Metodi, joka antaa annetun solmun tiedoston rivinumeron.
 * 
 * @return  tdstonrivi  tiedostonrivin järjestysnumero
 */        
    public int getTdstonrivi() {
        return this.tdstonrivi;
    }
/**
 * Metodi, joka antaa annetun solmun hakurivin järjestysnumeron.
 * 
 * @return  hakurivi    hakurivin järjestysnumero
 */        
    public int getHakurivi() {
        return this.hakurivi;
    }

/**
 * Metodi, joka antaa annetun solmun seuraajan.
 * 
 * @return  seurLista   solmusta seuraava solmu
 */        
    public Lista getSeurLista() {
        return this.seurLista;
    }

}
