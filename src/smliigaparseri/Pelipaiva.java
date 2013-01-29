package smliigaparseri;

import java.util.ArrayList;

/**
 *
 * @author juho
 */
public class Pelipaiva {
    private String viikonpaiva;
    private int paiva;
    private int kuukausi;
    private int vuosi;
    private ArrayList<Ottelu> ottelut;
    private boolean eilenOliPelipaiva;

    public Pelipaiva(int paiva, int kuukausi, int vuosi) {
        ottelut = new ArrayList<Ottelu>();
        this.paiva = paiva;
        this.kuukausi = kuukausi;
        this.vuosi = vuosi;
    }
    
    
}
