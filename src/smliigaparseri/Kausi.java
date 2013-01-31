/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smliigaparseri;

import java.util.ArrayList;

/**
 *
 * @author Käyttäjä
 */
public class Kausi {
    private int alku;
    private int loppu;
    private ArrayList<ArrayList<Ottelu>> kuukaudet;

    public Kausi(int alku, int loppu) {
        kuukaudet = new ArrayList<ArrayList<Ottelu>>();
        this.alku = alku;
        this.loppu = loppu;
    }

    public int getAlku() {
        return alku;
    }

    public void setAlku(int alku) {
        this.alku = alku;
    }

    public int getLoppu() {
        return loppu;
    }

    public void setLoppu(int loppu) {
        this.loppu = loppu;
    }

    public ArrayList<ArrayList<Ottelu>> getKuukaudet() {
        return kuukaudet;
    }

    public void setKuukaudet(ArrayList<ArrayList<Ottelu>> kuukaudet) {
        this.kuukaudet = kuukaudet;
    }
    
    
}
