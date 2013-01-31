package smliigaparseri;

import java.io.BufferedWriter;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 *
 * @author juho
 */
public class SMLiigaParseri {

    /**
     * @param args the command line arguments
     */
    public static int alku;
    public static int loppu;
    
    public static Date pvm = Calendar.getInstance().getTime();
    public static Calendar caltmp = new GregorianCalendar();
        
    
    public static void main(String[] args) throws IOException, ParseException {
        caltmp.setTime(pvm);
        // Tehdään kaudet jotka haetaan
        ArrayList<Kausi> kaudet = new ArrayList<Kausi>();
        kaudet.add(new Kausi(0, 1));
        kaudet.add(new Kausi(1, 2));
        kaudet.add(new Kausi(2, 3));
        kaudet.add(new Kausi(3, 4));
        kaudet.add(new Kausi(4, 5));
        kaudet.add(new Kausi(5, 6));
        kaudet.add(new Kausi(6, 7));
        kaudet.add(new Kausi(7, 8));
        kaudet.add(new Kausi(8, 9));
        kaudet.add(new Kausi(9, 10));
        kaudet.add(new Kausi(10, 11));
        kaudet.add(new Kausi(11, 12));
        
        // Haetaan kausien tiedot
        for(Kausi kausi : kaudet) {
            alku = kausi.getAlku();
            loppu = kausi.getLoppu();
            kausi.setKuukaudet((haeKaudenInfot(alku,loppu)));
        }
        
        // Kirjotellaan tiedostoon jutut
        System.out.println("Aloitetaan kirjoitus");
        for(Kausi kausi : kaudet) {
            writeFile("kausi" + String.format("%02d", kausi.getAlku()) + "-" 
                    + String.format("%02d", kausi.getLoppu()) + ".txt", kausi);
            System.out.println("Kausi kirjoitettu");
        }
        System.out.println("Kirjoitus tehty");
    }

    private static ArrayList<ArrayList<Ottelu>> haeKaudenInfot(int alku, int loppu) throws IOException, ParseException {
        String url = "http://www.sm-liiga.fi/ottelut.html?b=rs&s=" 
                + String.format("%02d", alku) + "-" + String.format("%02d", loppu);
        
        System.out.println("Hakee sivua " + url + " ...");
        
        Document doc = Jsoup.connect(url).get();
        Elements divs = doc.select("#content");
        
        Elements colleft = divs.select(".colLeftWide");
        
        // tables sisältää joka kuukauden pelit omissa table-elementeissään
        Elements tables = colleft.select(".dataTableDark");
        
        ArrayList<ArrayList<Ottelu>> kuukaudet = new ArrayList<ArrayList<Ottelu>>();
        
        int tmp = 0;
        for(Element table : tables) {
            kuukaudet.add(kasitteleKuukausi(table));
//            tmp++;
//            if(tmp == 2) {
//                break;
//            }
        }
        System.out.println("Kausi " + String.format("%02d", alku) + "-" 
                + String.format("%02d", loppu) + " käsitelty");
        System.out.println("Pelikuukausia: " + kuukaudet.size());
        for(ArrayList<Ottelu> ottelu : kuukaudet) {
            System.out.println("Otteluita kuukaudessa: " + ottelu.size());
        }
        System.out.println();
        
        return kuukaudet;
        
    }

    private static ArrayList<Ottelu> kasitteleKuukausi(Element table) throws IOException, ParseException {
        //System.out.println(table.html());
        
        Element tbody = table.child(0);
        
        ArrayList<Ottelu> ottelut = new ArrayList<Ottelu>();
        
        String paivaysStr = "";
//        Date pvm = Calendar.getInstance().getTime();
//        Calendar caltmp = new GregorianCalendar();
//        caltmp.setTime(pvm);
        boolean perakkainenpelipaiva = false;
        // Käsitellään rivit. Ei oteta ensimmäistä, se on vain otsikkoja
        for(int i = 1; i < tbody.children().size(); i++) {
            String tmpPaiv = tbody.child(i).child(1).text();
   
            Date paivays = null;
            //System.out.println("tmppaiv" + tmpPaiv);
            // Jos päivämäärä on vaihtunut
            if(!paivaysStr.equals(tmpPaiv) && !tmpPaiv.equals("")) {
                
                if(Integer.parseInt(tmpPaiv.substring(6,8)) <= 12 && Integer.parseInt(tmpPaiv.substring(6,8)) >= 9) {
                    tmpPaiv += String.format("%02d", alku);
                }
                else {
                    tmpPaiv += String.format("%02d", loppu);
                }
                
                Calendar cal = new GregorianCalendar();
                Locale suomi = new Locale("suomi", "FI");
                paivays = new SimpleDateFormat("dd.MM.yy", suomi).parse(tmpPaiv.substring(3));
                cal.setTime(paivays);
                //System.out.println("saakeli: " +(caltmp.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)));
//                System.out.println("CAL day of year: " + cal.get(Calendar.DAY_OF_YEAR));
//                System.out.println("CALTMP day of year: " + caltmp.get(Calendar.DAY_OF_YEAR));
                if(cal.get(Calendar.DAY_OF_YEAR) - caltmp.get(Calendar.DAY_OF_YEAR) == 1) {
                    perakkainenpelipaiva = true;
                }
                else {
                    perakkainenpelipaiva = false;
                }
                pvm = paivays;
                caltmp.setTime(pvm);
                //System.out.println(cal.get(Calendar.DAY_OF_WEEK)+ " " + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.YEAR));
                paivaysStr = tmpPaiv;
            }
            ottelut.add(kasitteleRivi(tbody.child(i), paivaysStr, perakkainenpelipaiva));
            //System.out.println("Rivi kasitelty ja lisatty. i: " + i);
        }
       
        return ottelut;
    }

    private static Ottelu kasitteleRivi(Element row, String pvm, boolean perakkainen) {
        
        Ottelu ottelu = new Ottelu();
        
//        String attrID = row.attr("id");
//        System.out.println("attrID: " + attrID);
        
        Elements solut = row.select("td");
        
        //Element solu = solut.get(0);
        Element solu = row.child(0);
        int pelinumero = Integer.parseInt(solu.select("span").text());
//        System.out.println("numero: " + pelinumero);
        
        // Ykkösestä date Tämäpäs tuleekin muualta
//        solu = row.child(1);
//        String pvm = solu.text();
//        System.out.println("pvm: " + pvm);
//        System.out.println("Eilen oli pelipaiva: " + perakkainen);
        // Karsitaan pvm sopiviin osiin
        String viikonpaiva = pvm.substring(0,2);
//        System.out.println("Päivä: " + viikonpaiva);
        
        String pvmTmp = pvm.substring(3);
        //System.out.println("pvmTmp: " + pvmTmp);
        int paiva = Integer.parseInt(pvmTmp.substring(0,2));
        int kuukausi = Integer.parseInt(pvmTmp.substring(3,5));
//        System.out.println("paiva:" + paiva + " kuukausi: " + kuukausi);
        int vuosi;
        if(kuukausi <= 12 && kuukausi >= 9) {
            vuosi = alku;
        }
        else {
            vuosi = loppu;
        }
        
        
        // Kakkosesta kello
        solu = row.child(2);
        String kello = solu.text();
//        System.out.println("kello: " + kello);
        
        // Kolmosesta joukkueet
        solu = row.child(3);
        String joukkueet = solu.text();
//        System.out.println("Joukkueet: " + joukkueet);
        String[] joukTmp = joukkueet.split("-");
        //System.out.println(joukTmp[0].trim() + " " + joukTmp[1].trim());
        String koti = joukTmp[0].trim();
        String vieras = joukTmp[1].trim();
        
        // Nelosesta tulokset
        solu = row.child(4);
        String tulos = solu.text();
//        System.out.println("tulos: " + tulos);
        String[] tulTmp = tulos.split("-");
        int kotimaalit = Integer.parseInt(tulTmp[0]);
        int vierasmaalit = Integer.parseInt(tulTmp[1]);
//        System.out.println("kotimaalit: " + kotimaalit + " vierasmaalit: " 
//                + vierasmaalit);
        
        // Vitosesta JA/VL
        solu = row.child(5);
        String javl = solu.text();
//        System.out.println("JA/VL: " + javl);
        
        // vika TD yleisö
        int yleiso = Integer.parseInt(solut.get(solut.size()-1).text());
//        System.out.println("yleiso: " + yleiso);
        
        ottelu.setPelinNumero(pelinumero);
        ottelu.setViikonpaiva(viikonpaiva);
        ottelu.setPaiva(paiva);
        ottelu.setKuukausi(kuukausi);
        ottelu.setVuosi(vuosi);
        ottelu.setAika(kello);
        ottelu.setKoti(koti);
        ottelu.setVieras(vieras);
        ottelu.setKodinMaalit(kotimaalit);
        ottelu.setVieraanMaalit(vierasmaalit);
        ottelu.setJaVaiVL(javl);
        ottelu.setYleiso(yleiso);
        ottelu.setEilenOliPelipaiva(perakkainen);
        
        return ottelu;
    }
    
    public static void writeFile(String name, Kausi kausi) throws IOException{
        Path path = Paths.get(name);
        try(BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.ISO_8859_1)) {
            for(ArrayList<Ottelu> kuukaudet : kausi.getKuukaudet()) {
                for(Ottelu ottelu : kuukaudet) {
                    StringBuilder item = new StringBuilder();
                    item.append(ottelu.getPelinNumero());
                    item.append(";");
                    item.append(ottelu.getViikonpaiva());
                    item.append(";");
                    item.append(ottelu.getPaiva());
                    item.append(";");
                    item.append(ottelu.getKuukausi());
                    item.append(";");
                    item.append(String.format("%02d", ottelu.getVuosi()));
                    item.append(";");
                    item.append(ottelu.isEilenOliPelipaiva());
                    item.append(";");
                    item.append(ottelu.getKoti());
                    item.append(";");
                    item.append(ottelu.getVieras());
                    item.append(";");
                    item.append(ottelu.getKodinMaalit());
                    item.append(";");
                    item.append(ottelu.getVieraanMaalit());
                    item.append(";");
                    item.append(ottelu.getJaVaiVL());
                    item.append(";");
                    item.append(ottelu.getYleiso());
                    
                    writer.write(item.toString());
                    writer.newLine();
                }
            }
        }
    }
}
