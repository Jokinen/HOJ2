package linjasto.komponentit.siirtävät;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;
import linjasto.osiot.Osio;
import omatVirheilmoitukset.LiianSuuriMääräException;

import java.util.concurrent.TimeUnit;

/**
 * Komponentti, joka siirtää.
 */
public abstract class Siirtävä extends Komponentti {
    private final int VIRTAAMA;

    public Siirtävä(String tunnus, int v) {
        super(tunnus);
        VIRTAAMA = v;
    }

    /**
     * Luokka simuloi virtausnopeutta odottamalla jokaisen vastaanotetun
     * raaka-aine-erän yhteydessä.
     *
     * @param raakaAine siirrettävä raaka-aine
     * @param määrä     siirrettävän raaka-aineen määrä
     *                  määrä <= haeVirtaama()
     * @param osio      osio, jolle halutaan siirtää raakaAinetta
     */
    public void vastaanota(RaakaAine raakaAine, int määrä, Osio osio) {
        try {
            TimeUnit.SECONDS.sleep(1);
            osio.vastaanota(raakaAine, määrä);
        } catch(java.lang.InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Hakumetodi luokan ominaisuudelle VIRTAAMA.
     *
     * @.post RETURN = VIRTAAMA
     */
    public int haeVirtaama() {
        return VIRTAAMA;
    }
}
