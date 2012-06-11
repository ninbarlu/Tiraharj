// Tietorakenteiden harjoitustyö, Nina Bärlund
package Logiikka;

import java.io.File;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ninbarlu
 */
public class HakupuuTest {

    public HakupuuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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

/*
    @Test
    public void testLueTdsto() throws Exception {
        System.out.println("lueTdsto");
        File tdstokahva = null;
        int tdstonro = 0;
        Hakupuu instance = new Hakupuu();
        int expResult = 0;
        int result = instance.lueTdsto(tdstokahva, tdstonro);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testEtsiJono() {
        System.out.println("etsiJono");
        String jono = "";
        Hakupuu instance = new Hakupuu();
        Lista expResult = null;
        Lista result = instance.etsiJono(jono);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRivi() {
        System.out.println("getRivi");
        int i = 0;
        Hakupuu instance = new Hakupuu();
        String expResult = "";
        String result = instance.getRivi(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetJuuri() {
        System.out.println("getJuuri");
        Hakupuu instance = new Hakupuu();
        Node expResult = null;
        Node result = instance.getJuuri();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Hakupuu instance = new Hakupuu();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    @Test
    public void testKoko() throws Exception {
        Hakupuu puu = new Hakupuu();
        long alku = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String rivi = "";
            for (int j = 0; j < 10; j++) {
                rivi = rivi + " " + i + j;
            }
            puu.kasitteleRivi(rivi, 1, i + 1, i);
        }
        long loppu = System.currentTimeMillis();
        System.out.println("lisäys 1000 rivillä: " + (loppu - alku) + "ms");
        alku = System.currentTimeMillis();
        puu.etsiJono("9999");
        loppu = System.currentTimeMillis();
        System.out.println("haku 1000 rivillä: " + (loppu - alku) + "ms");

        puu = new Hakupuu();
        alku = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            String rivi = "";
            for (int j = 0; j < 10; j++) {
                rivi = rivi + " " + i + j;
            }
            puu.kasitteleRivi(rivi, 1, i + 1, i);
        }
        for (int i = 0; i < 20000; i++) {
            String rivi = "";
            for (int j = 0; j < 10; j++) {
                rivi = rivi + " " + i + j;
            }
            puu.kasitteleRivi(rivi, 2, i + 1, i);
        }
        for (int i = 0; i < 20000; i++) {
            String rivi = "";
            for (int j = 0; j < 10; j++) {
                rivi = rivi + " " + i + j;
            }
            puu.kasitteleRivi(rivi, 3, i + 1, i);
        }
        loppu = System.currentTimeMillis();
        System.out.println("lisäys 60000 rivillä: " + (loppu - alku) + "ms");
        alku = System.currentTimeMillis();
        
        Lista tulos = puu.etsiJono("199999");
        assertNotNull(tulos);
        loppu = System.currentTimeMillis();
        System.out.println("haku 60000 rivillä: " + (loppu - alku) + "ms");


    }
}
