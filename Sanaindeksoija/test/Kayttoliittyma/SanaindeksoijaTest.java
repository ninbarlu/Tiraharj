// Tietorakenteiden harjoitustyö, Nina Bärlund
package Kayttoliittyma;

import Kayttoliittyma.Sanaindeksoija;
import Logiikka.Hakupuu;
import Logiikka.Lista;
import Logiikka.Node;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

/**
 * Sanaindeksoijan yksikkötestaus
 * 
 * @author Nina Bärlund
 */
public class SanaindeksoijaTest {
    
    private static Hakupuu trie;
    private static Node juuri;
    
    public SanaindeksoijaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        trie = new Hakupuu();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void sisaanlukuOnnistuu() throws Exception {
        assertEquals(393, trie.lueTdsto(new File("rautatiekpl1.txt"), 1));
    }

    @Test
    public void tdstoEiLoydy() throws Exception {
        assertEquals(false, Sanaindeksoija.lueSisaan("hoponlopon.txt"));
    }

    @Test
    public void jonoLoytyy() {
        Lista eka = trie.etsiJono("akka");
        assertEquals(1, eka.getTdsto());
        assertEquals(191, eka.getTdstonrivi());
        assertEquals(190, eka.getHakurivi());
    }

    @Test
    public void jonoEiLoydy() {
        assertEquals(null, trie.etsiJono("omena"));
    }

    @Test
    public void riviLoytyy() {
        assertEquals("Kirj. Juhani Aho 1884.", trie.getRivi(2));
    }

    @Test
    public void juuriLoytyy() {
        juuri = trie.getJuuri();
        assertNotNull(juuri);
        assertEquals(' ', juuri.getMerkki());
    }
    
    @Test
    public void lapsiLoytyy() {
        Node lapsi = juuri.getLapsi(10);
        assertNotNull(lapsi);
        assertEquals('a', lapsi.getMerkki());
    }
    
    @Test
    public void lapsiPuuttuu() {
        assertEquals(null, juuri.getLapsi(0));
    }
    
    @Test
    public void lapsiTallentuu() {
        juuri.setLapsi(0, new Node('0'));
        Node lapsi = juuri.getLapsi(0);
        assertEquals('0', lapsi.getMerkki());
    }
    
    @Test
    public void lapsenListaLoytyy() {
        Node lapsi = juuri.getLapsi(10);
        assertNotNull(lapsi.getLista());
    }
    
    @Test
    public void lapsenListaPuuttuu() {
        Node lapsi = juuri.getLapsi(0);
        assertEquals(null, lapsi.getLista());
    }
    
    @Test
    public void ekaListaTallentuu() {
        Node lapsi = juuri.getLapsi(0);
        lapsi.setRivi(1, 3, 2);
        assertNotNull(lapsi.getLista());
    }
    
    @Test
    public void tokaListaTallentuuOikein() {
        Node lapsi = juuri.getLapsi(0);
        lapsi.setRivi(1, 4, 3);
        Lista toka = lapsi.getLista().getSeurLista();
        assertNotNull(toka);
        assertEquals(1, toka.getTdsto());
        assertEquals(4, toka.getTdstonrivi());
        assertEquals(3, toka.getHakurivi());
    }
}
