package smliigaparseri;

/**
 *
 * @author juho
 */
public class Ottelu {
    private int pelinNumero;
    private String viikonpaiva;
    private int paiva;
    private int kuukausi;
    private int vuosi;
    private int numero;
    private String aika;
    private String koti;
    private String vieras;
    private int kodinMaalit;
    private int vieraanMaalit;
    private String jaVaiVL;
    private int yleiso;
    private boolean eilenOliPelipaiva;

    public Ottelu() {
    }

    public int getPelinNumero() {
        return pelinNumero;
    }

    public void setPelinNumero(int pelinNumero) {
        this.pelinNumero = pelinNumero;
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String kello) {
        this.aika = kello;
    }

    public String getKoti() {
        return koti;
    }

    public void setKoti(String koti) {
        this.koti = koti;
    }

    public String getVieras() {
        return vieras;
    }

    public void setVieras(String vieras) {
        this.vieras = vieras;
    }

    public int getKodinMaalit() {
        return kodinMaalit;
    }

    public void setKodinMaalit(int kodinMaalit) {
        this.kodinMaalit = kodinMaalit;
    }

    public int getVieraanMaalit() {
        return vieraanMaalit;
    }

    public void setVieraanMaalit(int vieraanMaalit) {
        this.vieraanMaalit = vieraanMaalit;
    }

    public String getJaVaiVL() {
        return jaVaiVL;
    }

    public void setJaVaiVL(String jaVaiVL) {
        this.jaVaiVL = jaVaiVL;
    }

    public int getYleiso() {
        return yleiso;
    }

    public void setYleiso(int yleiso) {
        this.yleiso = yleiso;
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

    public boolean isEilenOliPelipaiva() {
        return eilenOliPelipaiva;
    }

    public void setEilenOliPelipaiva(boolean eilenOliPelipaiva) {
        this.eilenOliPelipaiva = eilenOliPelipaiva;
    }
    
    
    
}
