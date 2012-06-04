// Tietorakenteiden harjoitustyö, Nina Bärlund

/**
 * Luokka, joka toteuttaa Trie-hakupuun solmut Sanaindeksoijaa varten
 * 
 * @author  Nina Bärlund
 * @version 0.1
 * @since   4.6.2012
 */
public class Node {
    
    private char kirj;
    private Lista ekaEsitys;
    private Lista vikaEsitys;
    private Node[] lapset;
    
    public Node() {
        this.kirj = ' ';
        this.ekaEsitys = null;
        this.vikaEsitys = null;
        this.lapset = new Node[39];
    }
    
    public Node getLapsi(int i) {
        return lapset[i];
    }
}
