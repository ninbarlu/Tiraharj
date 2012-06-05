package Logiikka;

// Tietorakenteiden harjoitustyö, Nina Bärlund

/**
 * Luokka, joka toteuttaa linkitetyn listan Trie-hakupuun esiintymien tallettamista varten
 * 
 * @author  Nina Bärlund
 * @version 0.1
 * @since   4.6.2012
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
    
}
