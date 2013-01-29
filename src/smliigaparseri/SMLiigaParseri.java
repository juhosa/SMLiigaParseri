package smliigaparseri;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 *
 * @author juho
 */
public class SMLiigaParseri {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Puuhaa jotenkin lista et mita kautta haetaan
        
        haeKaudenInfot(00,01);
    }

    private static void haeKaudenInfot(int alku, int loppu) throws IOException {
        String url = "http://www.sm-liiga.fi/ottelut.html?b=rs&s=" 
                + String.format("%02d", alku) + "-" + String.format("%02d", loppu);
        
        System.out.println("Hakee sivua " + url + "...");
        
        Document doc = Jsoup.connect(url).get();
        Elements divs = doc.select("#content");
        
        Elements colleft = divs.select(".colLeftWide");
        
        // tables sisältää joka kuukauden pelit omissa table-elementeissään
        Elements tables = colleft.select(".dataTableDark");
        
        for(Element table : tables) {
            kasitteleKuukausi(table);
            break;
        }
        
        //System.out.println(tables.get(0).html());
        
        
    }

    private static void kasitteleKuukausi(Element table) throws IOException {
        //System.out.println(table.html());
        //Elements tbody = table.getElementsByTag("tbody");
        Element tbody = table.child(0);
        Elements rivit = tbody.select("tr");
        
        /*
         * Luo pelipäivä
         */
        
        // Käsitellään rivit. Ei oteta ensimmäistä, se on vain otsikkoja
        for(int i = 2; i < tbody.children().size(); i++) { // rivit.size()
            //System.out.println(rivit.get(i).html());
            //System.out.println(rivit.get(i).child(7));
            kasitteleRivi(tbody.child(i)); // rivit.get(i)
            break;
        }
        
        
    }

    private static void kasitteleRivi(Element row) {
        String attrID = row.attr("id");
        System.out.println("attrID: " + attrID);
        
        Elements solut = row.select("td");
        
        //Element solu = solut.get(0);
        Element solu = row.child(0);
        String pelinumero = solu.select("span").text();
        System.out.println("numero: " + pelinumero);
        
        // Ykkösestä date
        solu = row.child(1);
        String pvm = solu.text();
        System.out.println("pvm: " + pvm);
        
        // Kakkosesta kello
        solu = row.child(2);
        String kello = solu.text();
        System.out.println("kello: " + kello);
        
        // Kolmosesta joukkueet
        solu = row.child(3);
        String joukkueet = solu.text();
        System.out.println("Joukkueet: " + joukkueet);
        
        // Nelosesta tulokset
        solu = row.child(4);
        String tulos = solu.text();
        System.out.println("tulos: " + tulos);
        
        // Vitosesta JA/VL
        solu = row.child(5);
        String javl = solu.text();
        System.out.println("JA/VL: " + javl);
        
        // vika TD yleisö
        String yleiso = solut.get(solut.size()-1).text();
        System.out.println("yleiso: " + yleiso);
    }
}
