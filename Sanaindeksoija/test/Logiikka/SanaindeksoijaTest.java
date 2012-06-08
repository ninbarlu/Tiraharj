// Tietorakenteiden harjoitustyö, Nina Bärlund
package Logiikka;

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
        File tdstokahva = new File("rautatiekpl1.txt");
        int riveja = trie.lueTdsto(tdstokahva, 1);
        assertEquals(578, riveja);
    }

    @Test
    public void sisaanlukuEiOnnistu() throws Exception {
        boolean luku = Sanaindeksoija.lueSisaan("hoponlopon.txt");
        assertEquals(false, luku);
    }

    @Test
    public void jonoLoytyy() {
        Lista eka = trie.etsiJono("akka");
        assertEquals(1, eka.getTdsto());
        assertEquals(269, eka.getTdstonrivi());
        assertEquals(268, eka.getHakurivi());
    }

    @Test
    public void jonoEiLoydy() {
        Lista eka = trie.etsiJono("omena");
        assertEquals(null, eka);
    }

}
