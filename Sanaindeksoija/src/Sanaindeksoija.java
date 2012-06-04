// Tietorakenteiden harjoitustyö, Nina Bärlund

import java.util.Scanner;
import java.io.*;

/**
 * Sanaindeksoijan käynnistysalgoritmi
 * 
 * @author  Nina Bärlund
 * @version 0.1
 * @since   29.5.2012
 */

public class Sanaindeksoija {

    private static Scanner lukija = new Scanner(System.in);
    
    private static Hakupuu trie;
    private static String[] tdstot;
    
    private static int mones;
    private static int riveja;
    private static int hakuja;
    private static int osumia;
    
/**
 * Metodi, joka tulostaa käyttäjälle tilaston tämän toimista: sisäänluettujen tiedostojen ja rivien, tehtyjen hakujen sekä saatujen osumien lukumäärän.
 * 
 */    
    private static void tulostaTilasto() {
        System.out.println("Tilasto:");
        System.out.println("  - sisäänluettuja tiedostoja "+mones+" kpl");
        if (mones > 0) {
            System.out.println("  - sisäänluettuja rivejä     "+riveja+" kpl, k.a per tiedosto "+(1.0*((100*riveja)/mones)/100));
            System.out.println("  - tehtyjä hakuja            "+hakuja+" kpl");
        }
        if (hakuja > 0)
            System.out.println("  - saatuja osumia yhteensä   "+osumia+" kpl, k.a per haku     "+(1.0*((100*osumia)/hakuja)/100));
    }
    
/**
 * Metodi, joka tarkistaa tiedoston löytymisen ja käynnistää luvun.
 * 
 * @param   tdsto   sisäänlukutiedoston nimi, tulee sijaita samassa hakemistossa ohjelman kanssa
 * 
 * @return  luku    totuusarvo, kertoo sisäänluvun onnistumisesta
 */    
    public static boolean lueSisaan(String tdsto) throws Exception {

        File tdstokahva = new File(tdsto);
        if (!tdstokahva.exists()) {
            return false;
        }
        riveja = riveja + trie.lueTdsto(tdstokahva, mones-1);
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
            if (lukija.hasNextInt()) {
                tiedLkm = lukija.nextInt();
                lukija.nextLine();
                tdstot = new String[tiedLkm];
                break;
            }
            else {
                lukija.nextLine();
                System.out.println("Antamasi tieto ei ole luku - yritä uudelleen.\n");
            }
        }
        if (tiedLkm <= 0) {
            System.out.println("Ohjelma päättyy.");
            return;
        }
        
        mones = 1;
        riveja = 0;
        hakuja = 0;
        osumia = 0;
        while (mones <= tiedLkm) {
            System.out.println("Anna "+mones+". sisäänluettavan tiedoston nimi:");
            String tdsto = lukija.nextLine();
            if (tdsto.isEmpty() && mones == 1) {
                tulostaTilasto();
                return;
            }
            if (tdsto.isEmpty())
                break;
            if (mones == 1)
                trie = new Hakupuu();
            boolean luku = lueSisaan(tdsto);
            if (luku) {
                tdstot[mones-1] = tdsto;
                mones++;
            }
            else
                System.out.println("Tiedostoa "+tdsto+" ei löydy.");
            

 
            
            
            
        }
    }
}
