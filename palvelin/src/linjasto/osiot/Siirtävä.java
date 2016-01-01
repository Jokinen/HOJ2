package linjasto.osiot;

import apumäärittelyt.RaakaAine;

import java.util.ArrayList;

/**
 * Linjaston osa, jossa raaka-aine ainoastaan liikkuu. Sitä ei varastoida, eikä
 * sitä muuten prosessoida.
 */
public class Siirtävä extends Osio {
    /*
    ArrayListin kohdeluokkaan viitataan tässä pakettipolulla, sillä luokka
    samanniminen kuin tämä luokka. Pakettien sisäisen selkeän nimeämisen
    säilyttämiseksi kumpaakaan luokkaa ei nimetty uudelleen.
    */
    private ArrayList<linjasto.komponentit.siirtävät.Siirtävä> komponentit;

    public Siirtävä(String t, String e, String s, ArrayList<linjasto.komponentit.siirtävät.Siirtävä> k) {
        super(t, e, s);
        komponentit = k;
    }

    /**
     * @see Osio#Osio(String, boolean, String)
     */
    public Siirtävä(String t, boolean a, String s, ArrayList<linjasto.komponentit.siirtävät.Siirtävä> k) {
        super(t, a, s);
        komponentit = k;
    }

    /**
     * @see Osio#Osio(String, String, boolean)
     */
    public Siirtävä(String t, String e, boolean a, ArrayList<linjasto.komponentit.siirtävät.Siirtävä> k) {
        super(t, e, a);
        komponentit = k;
    }

    /**
     * @see Osio#vastaanota(RaakaAine, int)
     *
     * Siirtävän osion ei tarvitse säilöä raaka-ainetta mihinkään. Sen ainoa järkevä
     * tehtävä on yrittää maksimoida tämän osan läpi virtaava raaka-aine. Ts. sen
     * on ainoastaan pyrittävä jakamaan siirrettävä raaka-aine eri komponenteille
     * tasaisesti, riippuen näiden komponenttien ominaisuuksista.
     */
    @Override public void vastaanota(RaakaAine raakaAine, int määrä) {
        int jakauma;
        int i = laskeKierrokset(määrä);
        int jäljelläOlevaMäärä = määrä;
        for (int j = i; j == 0; j--) {
            jakauma = laskeJakauma(jäljelläOlevaMäärä);
            for (linjasto.komponentit.siirtävät.Siirtävä komponentti : komponentit) {
                komponentti.vastaanota(raakaAine, jakauma);
                jäljelläOlevaMäärä = jäljelläOlevaMäärä - jakauma;
                komponentti.siirrä(raakaAine, jakauma);
            }
        }
    }

    /**
     * Palauttaa luokan kaikkien komponenttien yhteen lasketun VIRTAAMAN.
     *
     * @.post   FORALL(komponentti : komponentit)
     *              RETURN = RETURN + komponentti.haeVirtaama()
     */
    private int kokoVirtaama() {
        // TODO: implement
        return 1;
    }

    /**
     * Laskee kuinka monta kierrosta täytyy suorittaa, jotta kaikki siirrettävä
     * raaka-aine saadaan siirretyksi.
     *
     * @param määrä kaiken siirrettävän raaka-aineen määrä
     * @.post RETURN = määrä / kokoVirtaama()
     */
    private int laskeKierrokset(int määrä) {
        // TODO: implement
        return 1;
    }

    /**
     * Laskee kuinka paljon raaka-ainetta kukin komponentti siirtää määrällä
     * "määrä".
     *
     * @param määrä jäljellä olevan siirrettävän raaka-aineen määrä
     * @.post   if (määrä < kokoVirtaama())
     *              RETURN = määrä / komponentit.size()
     *          else
     *              RETURN = kokoVirtaama() / komponentit.size()
     */
    private int laskeJakauma(int määrä) {
        // TODO: implement
        return 1;
    }
}
