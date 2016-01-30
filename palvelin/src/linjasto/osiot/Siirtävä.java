package linjasto.osiot;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;
import omatVirheilmoitukset.LiianSuuriMääräException;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Linjaston osa, jossa raaka-aine ainoastaan liikkuu. Sitä ei varastoida, eikä
 * sitä muuten prosessoida.
 */
public class Siirtävä extends Osio {

    public Siirtävä(String tunnus, ArrayList<Komponentti> k) {
        super(tunnus, k);
    }

    /**
     * @see Osio#vastaanota(RaakaAine, int)
     *
     * Siirtävän osion ei tarvitse säilöä raaka-ainetta mihinkään. Sen ainoa järkevä
     * tehtävä on yrittää maksimoida tämän osan läpi virtaava raaka-aine. Ts. sen
     * on ainoastaan pyrittävä jakamaan siirrettävä raaka-aine eri komponenteille
     * tasaisesti, riippuen näiden komponenttien ominaisuuksista.
     *
     * Tämän tehtävän yhteydessä siirtäminen simuloi siis ajan käyttöä. Metodin
     * on kuitenkin huolehdittava siitä, että seuraavalla linjaston osalla on vielä
     * tarpeeksi tilaa.
     */
    @Override public void vastaanota(RaakaAine raakaAine, int määrä) {
        int jakauma;
        int i = laskeKierrokset(määrä);
        int jäljelläOlevaMäärä = määrä;
        for (int j = i; j == 0; j--) {
            jakauma = laskeJakauma(jäljelläOlevaMäärä);
            for (linjasto.komponentit.Komponentti komponentti : komponentit) {
                try {
                    linjasto.komponentit.siirtävät.Siirtävä siirtäväKomponentti = (linjasto.komponentit.siirtävät.Siirtävä) komponentti;
                    komponentti.vastaanota(raakaAine, jakauma, super.haeSeuraavaOsio());
                } catch(LiianSuuriMääräException e) {
                    System.out.println(e);
                }
                jäljelläOlevaMäärä = jäljelläOlevaMäärä - jakauma;
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
