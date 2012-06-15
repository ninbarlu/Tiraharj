package Kayttoliittyma;

// Tietorakenteiden harjoitustyö, Nina Bärlund

import Logiikka.Hakupuu;
import Logiikka.Lista;
import java.util.Scanner;
import java.io.*;

/**
 * Sanaindeksoija. Luokka toteuttaa annetuista tekstitiedostoista Trie-hakupuun, jonka avulla käyttäjä voi etsiä haluamansa merkkijonon
 * tekstitiedostojen riveiltä. Saatua hakutulosta voi tarkentaa. Luokka tulostaa haluttaessa hakupuun sisäänluvun jälkeen,
 * kunkin haun lopputuloksen sekä tilaston hausta ohjelman päättyessä.
 * 
 * @author  Nina Bärlund
 * @version 1.1
 * @since   11.6.2012
 * 
 */
public class Sanaindeksoija {

    private static Scanner lukija = new Scanner(System.in);
    
/**
 * Trie-hakupuu
 */
    private static Hakupuu trie;                                                // Sanaindeksoijan hakupuu
/**
 * sisäänlukutiedostojen nimet sisältävä taulukko
 */
    private static String[] tdstot = {"JUnit"};                                 // sisältää sisäänluettujen tiedostojen nimet; oletusarvo testausta varten
/**
 * voimassa olevan hakutuloksen sisältävän linkitetyn listan ensimmäinen solmu
 */
    private static Lista hakutulos;                                             // käynnissä olevan haun tuloksen sisältävä linkitetty lista
    
/**
 * sisäänlukutiedoston järjestysnumero 0-n
 */
    private static int mones;                                                   // käsittelyssä olevan tiedoston järjestysnumero
/**
 * laskuri: sisäänluettujen rivien lukumäärä
 */
    private static int riveja;                                                  // tilasto: käsiteltyjen rivien lukumäärä
/**
 * laskuri: tehtyjen hakujen lukumäärä
 */
    private static int hakuja;                                                  // tilasto: tehtyjen hakujen lukumäärä
/**
 * laskuri: tehtyjen alahakujen (haun tarkentaminen) lukumäärä
 */
    private static int alahakuja;                                               // tilasto: tehtyjen tulossupistusten lukumäärä
/**
 * laskuri: hakujen osumien (tarkennetun haun jälkeen) lukumäärä
 */
    private static int osumia;                                                  // tilasto: hakujen osumat yhteensä
    
/**
 * Metodi, joka tulostaa käyttäjälle tilaston tämän toimista: sisäänluettujen tiedostojen ja rivien, tehtyjen hakujen sekä saatujen osumien lukumäärän.
 * 
 */    
    private static void tulostaTilasto() {
        System.out.println("Tilasto:");
        System.out.println("  - sisäänluettuja tiedostoja "+(mones-1)+" kpl");
        if (mones-1 > 0) {
            System.out.println("  - sisäänluettuja rivejä     "+riveja+" kpl, k.a per tiedosto "+(1.0*((100*riveja)/(mones-1))/100));
            System.out.println("  - tehtyjä hakuja            "+hakuja+" kpl");
        }
        if (hakuja > 0) {
            System.out.println("  - tehtyjä alahakuja         "+alahakuja+" kpl, k.a per haku "+(1.0*((100*alahakuja)/hakuja)/100));
            System.out.println("  - saatuja osumia yhteensä   "+osumia+" kpl, k.a per haku "+(1.0*((100*osumia)/hakuja)/100));
        }
    }
    
/**
 * Metodi, joka tarkistaa tiedoston löytymisen ja käynnistää luvun.
 * 
 * @param   tdsto   sisäänluettavan tiedoston nimi, tulee sijaita samassa hakemistossa ohjelman kanssa
 * 
 * @return  luku    totuusarvo, kertoo sisäänluvun onnistumisesta
 * 
 * @see     Logiikka.Hakupuu#lueTdsto(java.io.File, int) 
 * 
 */    
    public static boolean lueSisaan(String tdsto) throws Exception {            // testausta varten public
        File tdstokahva = new File(tdsto);
        if (!tdstokahva.exists()) {                                             // tarkistaa annetun tiedoston löytymisen
            return false;
        }
        riveja = riveja + trie.lueTdsto(tdstokahva, mones-1);                   // käynnistää tiedoston sisäänluvun, palauttaa luettujen rivien lukumäärän
        return true;
    }

/**
 * Hakumetodi, joka hakee annetun merkkijonon hakupuusta ja tulostaa käyttäjälle löytyneet osumat.
 * 
 * @param   vast    käyttäjän antama haettava merkkijono
 * @param   alahaku totuusarvo, joka ohjaa tekemään hakutulosten vertailun, kun kyseessä on alahaku
 * 
 * @see     Logiikka.Hakupuu#etsiJono(java.lang.String)
 * @see     Logiikka.Hakupuu#getRivi(int)
 * @see     Logiikka.Lista#getTdsto()
 * @see     Logiikka.Lista#getTdstonrivi()
 * @see     Logiikka.Lista#getHakurivi()
 * @see     Logiikka.Lista#getSeurLista()
 * 
 */    
    private static int teeHaku(String vast, boolean alahaku) {
        Lista lista = trie.etsiJono(vast);                                      // etsitään hakupuusta annetun merkkijonon viimeinen merkki
        if (lista == null) {                                                    // haettua merkkijonoa ei löytynyt
            System.out.println("Merkkijonoa "+vast+" ei löytynyt hakupuusta.\n");
            return 0;
        }
        System.out.println();
        int lask = 0;                                                           // osumalaskuri
        if (!alahaku)                                                           // ns. eka haku, tehty koko aineistoon
            hakutulos = lista;
        else                                                                    // alahaku, supistaa tulosta edellisestä hakutuloksesta
            hakutulos = teeVertailu(lista);
        Lista haku = hakutulos;                                                 // hakutuloksen käsiteltävä solmu
        while (haku != null) {
            System.out.println(tdstot[haku.getTdsto()]+", rivi "+haku.getTdstonrivi()+": "+trie.getRivi(haku.getHakurivi()));
            lask++;
            haku = haku.getSeurLista();                                         // haetaan seuraava esitys
        }
        System.out.println("yhteensä "+lask+" osumaa\n");                       // merkkijonojen osumien lukumäärä
        return lask;
    }

/**
 * Metodi, joka toteuttaa tarkentavan alahaun olemassa olevalle hakutulokselle. 
 * Metodi vertailee aiemman hakutuloksen tietoja uuteen hakutulokseen ja poimii tarkennettuun hakutulokseen ne linkitetyn listan
 * solmut, jotka löytyvät molemmista listoista.
 * 
 * @param   lista   käyttäjän tekemän uuden haun hakutulos
 * 
 * @return  tulos   tarkennetun haun lopputulos
 *
 * @see     Logiikka.Lista#getTdsto()
 * @see     Logiikka.Lista#getTdstonrivi()
 * @see     Logiikka.Lista#getHakurivi()
 * @see     Logiikka.Lista#getSeurLista()
 * @see     Logiikka.Lista#setSeur(Logiikka.Lista)
 * 
 */    
    private static Lista teeVertailu(Lista lista) {
        Lista haku = hakutulos;                                                 // käsiteltävä nykyisen hakutuloksen solmu
        Lista tulos = null;                                                     // supistetun hakutuloksen 1. solmu
        Lista vikatulos = null;                                                 // supistetun hakutuloksen viimeinen solmu
        while (haku != null && lista != null) {                                 // päättyy, kun jompikumpi lista päättyy
            if (haku.getTdsto() < lista.getTdsto()) {                           // hakutuloksen solmu aiempi kuin supistushaun solmu
                haku = haku.getSeurLista();                                     // seuraavaan nykyisen hakutuloksen solmuun
                continue;
            }
            if (haku.getTdsto() > lista.getTdsto()) {                           // supistushaun solmu aiempi kuin hakutuloksen solmu
                lista = lista.getSeurLista();                                   // seuraavaan supistushaun solmuun
                continue;
            }
            if (haku.getTdstonrivi() < lista.getTdstonrivi()) {                 // hakutuloksen solmu aiempi kuin supistushaun solmu
                haku = haku.getSeurLista();                                     // seuraavaan nykyisen hakutuloksen solmuun
                continue;
            }
            if (haku.getTdstonrivi() > lista.getTdstonrivi()) {                 // supistushaun solmu aiempi kuin hakutuloksen solmu
                lista = lista.getSeurLista();                                   // seuraavaan supistushaun solmuun
                continue;
            }                                                                   // supistushaun tieto löytyy hakutuloksessa olevalta riviltä
            if (tulos == null) {                                                // jos 1. löydetty täsmäävä, tehdään uuden tuloksen aloitussolmu
                tulos = new Lista(haku.getTdsto(), haku.getTdstonrivi(), haku.getHakurivi());
                vikatulos = tulos;
            }
            else {                                                              // tehdään seuraava solmu uuteen hakutulokseen
                vikatulos.setSeur(new Lista(haku.getTdsto(), haku.getTdstonrivi(), haku.getHakurivi()));
                vikatulos = vikatulos.getSeurLista();
            }
            haku = haku.getSeurLista();                                         // seuraavaan nykyisen hakutuloksen solmuun
            lista = lista.getSeurLista();                                       // seuraavaan supistushaun solmuun
        }
        return tulos;                                                           // palautetaan uuden hakutuloksen aloitussolmu
    }
    
/**
 * Sanaindeksoijan main-algoritmi
 * 
 */
    public static void main(String[] args) throws Exception {

        System.out.println("Tervetuloa Sanaindeksoijaan!");
        System.out.println("Anna sisäänluettavien tiedostojen lukumäärä:");
        int tiedLkm = 0;
        while (true) {
            if (lukija.hasNextInt()) {                                          // kelvollinen arvo
                tiedLkm = lukija.nextInt();
                lukija.nextLine();
                tdstot = new String[tiedLkm];                                   // perustetaan taulukko tiedostojen nimille
                break;
            }
            else {                                                              // kelvoton arvo
                lukija.nextLine();
                System.out.println("Antamasi tieto ei ole luku - yritä uudelleen.\n");
            }
        }
        if (tiedLkm <= 0) {                                                     // jos tiedostojen lkm:ksi annetaan 0
            System.out.println("Ohjelma päättyy.");
            return;
        }
        
        // sisäänlukutiedostojen luku hakupuuhun
        mones = 1;
        riveja = 0;
        hakuja = 0;
        alahakuja = 0;
        osumia = 0;
        while (mones <= tiedLkm) {
            System.out.println("Anna "+mones+". sisäänluettavan tiedoston nimi:");
            String vast = lukija.nextLine();
            if (vast.isEmpty() && mones == 1) {                                 // jos 1. tiedoston nimeksi annetaan tyhjä, päätetään ohjelma
                tulostaTilasto();                                               // tulostetaan tilastotiedot
                return;
            }
            if (vast.isEmpty())                                                 // jos tiedoston nimeksi annetaan tyhjä, siirrytään hakutoimintoon
                break;
            if (mones == 1)                                                     // kun 1. sisäänluku, perustetaan hakupuu
                trie = new Hakupuu();
            boolean luku = lueSisaan(vast);                                     // tiedoston sisäänlukuun
            if (luku) {                                                         // jos tiedosto löytyi
                tdstot[mones-1] = vast;                                         // tiedoston nimi talteen
                mones++;
                System.out.println("Tulostetaanko hakupuu? K = kyllä");
                if (lukija.nextLine().equalsIgnoreCase("K")) 
                    System.out.println(trie);                                   // tähänastisen hakupuun tulostus
            }
            else                                                                // jos tiedostoa ei löydy
                System.out.println("Tiedostoa "+vast+" ei löydy.");
        }
        
        // hakutoiminnot
        System.out.println("Siirrytään hakutoimintoon.\n");
        while (true) {                                                          // kunnes haetaan tyhjää
            System.out.println("Aloitetaan uusi haku. Tyhjä päättää hakutoiminnon.");
            System.out.println("Anna haettava merkkijono:");
            String vast = lukija.nextLine().trim();
            if (vast.isEmpty())
                break;
            hakuja++;                                                           // lasketaan vain koko aineistosta tehtävät haut
            int lask = 0;
            boolean alahaku = false;
            while (true) {
                lask = teeHaku(vast, alahaku);                                  // hakee merkkijonon, ns. eka haku = lopputulos alkuperäinen linkitetty lista
                if (lask == 0)                                                  // jos hakutulos on tyhjä, aloitetaan seur haku koko materiaalista
                    break;
                System.out.println("Jos haluat tarkentaa hakua, anna uusi merkkijono. Tyhjä päättää tarkennuksen.");
                vast = lukija.nextLine().trim();
                if (vast.isEmpty())
                    break;
                alahakuja++;
                alahaku = true;
            }
            osumia = osumia + lask;
        }
        
        // tilaston tulostaminen
        tulostaTilasto();                                                       // tulostetaan tilastotiedot ohjelman päätteeksi
    }
}
