package Logiikka;

// Tietorakenteiden harjoitustyö, Nina Bärlund

import Logiikka.Hakupuu;
import java.util.Scanner;
import java.io.*;

/**
 * Sanaindeksoijan käynnistysalgoritmi
 * 
 * @author  Nina Bärlund
 * @version 1.0
 * @since   8.6.2012
 */
public class Sanaindeksoija {

    private static Scanner lukija = new Scanner(System.in);
    
    private static Hakupuu trie;                                                // Sanaindeksoijan hakupuu
    private static String[] tdstot = {"JUnit"};                                 // sisältää sisäänluettujen tiedostojen nimet; oletusarvo testausta varten
    
    private static int mones;                                                   // käsittelyssä olevan tiedoston järjestysnumero
    private static int riveja;                                                  // tilasto: käsiteltyjen rivien lukumäärä
    private static int hakuja;                                                  // tilasto: tehtyjen hakujen lukumäärä
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
        if (hakuja > 0)
            System.out.println("  - saatuja osumia yhteensä   "+osumia+" kpl, k.a per haku "+(1.0*((100*osumia)/hakuja)/100));
    }
    
/**
 * Metodi, joka tarkistaa tiedoston löytymisen ja käynnistää luvun.
 * 
 * @param   tdsto   sisäänluettavan tiedoston nimi, tulee sijaita samassa hakemistossa ohjelman kanssa
 * 
 * @return  luku    totuusarvo, kertoo sisäänluvun onnistumisesta
 */    
    public static boolean lueSisaan(String tdsto) throws Exception {

        File tdstokahva = new File(tdsto);
        if (!tdstokahva.exists()) {                                             // tarkistaa annetun tiedoston löytymisen
            return false;
        }
        riveja = riveja + trie.lueTdsto(tdstokahva, mones-1);                   // käynnistää tiedoston sisäänluvun, palauttaa luettujen rivien lukumäärän
        return true;
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
        System.out.println("Siirrytään hakutoimintoon. Tyhjä päättää haun.");
        while (true) {                                                          // kunnes haetaan tyhjää
            System.out.println("Anna haettava merkkijono:");
            String vast = lukija.nextLine().trim();
            if (vast.isEmpty())
                break;
            hakuja++;
            Lista lista = trie.etsiJono(vast);                                  // etsitään hakupuusta annetun merkkijonon viimeinen merkki
            if (lista == null) {                                                // haettua merkkijonoa ei löytynyt
                System.out.println("Merkkijonoa "+vast+" ei löytynyt hakupuusta.\n");
                continue;
            }
            System.out.println();
            int lask = 0;                                                       // osumalaskuri
            while (lista != null) {
                System.out.println(tdstot[lista.getTdsto()]+", rivi "+lista.getTdstonrivi()+": "+trie.getRivi(lista.getHakurivi()));
                lask++;
                lista = lista.getSeurLista();                                   // haetaan seuraava esitys
            }
            System.out.println("yhteensä "+lask+" osumaa\n");                   // merkkijonojen osumien lukumäärä
            osumia = osumia + lask;
        }
        
        // tilaston tulostaminen
        tulostaTilasto();                                                       // tulostetaan tilastotiedot ohjelman päätteeksi
    }
}
