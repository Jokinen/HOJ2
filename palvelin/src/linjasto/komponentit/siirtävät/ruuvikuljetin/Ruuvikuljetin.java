package linjasto.komponentit.siirtävät.ruuvikuljetin;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.siirtävät.Siirtävä;
import linjasto.osiot.Osio;
import omatVirheilmoitukset.LiianSuuriMääräException;

import java.util.concurrent.TimeUnit;

/**
 * Ruuvikuljetin raaka-ainesiilojen täyttöön.
 * Siirtää raaka-ainetta 200 kiloa sekunnissa
 * Täyttö ei voi siirtää enempää kuin vastaanottavaan yksikköön mahtuu
 * Raaka-ainetta tulee lisää tehtaaseen yhdessä erässä 40 tonnia
 * Siilo tai keitin ei saa ylitäyttyä
 */
public class Ruuvikuljetin extends Siirtävä {
    private static final int VIRTAAMA = 200;                 // per sekunti

    // konstruktori
    public Ruuvikuljetin() {}

    /**
     * Hakumetodi luokan staattiselle ominaisuudelle VIRTAAMA.
     *
     * @return  VIRTAAMA
     */
    public int haeVirtaama() {
        return VIRTAAMA;
    }

    /**
     * Luokka simuloi virtausnopeutta odottamalla jokaisen vastaanotetun
     * raaka-aine-erän yhteydessä.
     *
     * @param raakaAine siirrettävä raaka-aine
     * @param määrä     siirrettävän raaka-aineen määrä
     *                  määrä <= haeVirtaama()
     * @param osio      osio, jolle halutaan siirtää raakaAinetta
     * @throws  LiianSuuriMääräException
     *          when(määrä > haeVirtaama())
     */
    public void vastaanota(RaakaAine raakaAine, int määrä, Osio osio) throws LiianSuuriMääräException {
        if (määrä > haeVirtaama())
            throw new LiianSuuriMääräException();
        try {
            TimeUnit.SECONDS.sleep(1);
            osio.vastaanota(raakaAine, määrä);
        } catch(java.lang.InterruptedException e) {
            System.out.println(e);
        }
    }

}

