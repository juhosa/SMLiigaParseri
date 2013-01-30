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

    public String getViikonpaiva() {
        return viikonpaiva;
    }

    public void setViikonpaiva(String viikonpaiva) {
        this.viikonpaiva = viikonpaiva;
    }

    public int getPaiva() {
        return paiva;
    }

    public void setPaiva(int paiva) {
        this.paiva = paiva;
    }

    public int getKuukausi() {
        return kuukausi;
    }

    public void setKuukausi(int kuukausi) {
        this.kuukausi = kuukausi;
    }

    public int getVuosi() {
        return vuosi;
    }

    public void setVuosi(int vuosi) {
        this.vuosi = vuosi;
    }

    public ArrayList<Ottelu> getOttelut() {
        return ottelut;
    }

    public void setOttelut(ArrayList<Ottelu> ottelut) {
        this.ottelut = ottelut;
    }

    public boolean isEilenOliPelipaiva() {
        return eilenOliPelipaiva;
    }

    public void setEilenOliPelipaiva(boolean eilenOliPelipaiva) {
        this.eilenOliPelipaiva = eilenOliPelipaiva;
    }
    
    
}
