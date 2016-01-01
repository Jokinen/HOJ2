package linjasto.komponentit.siirtävät.ruuvikuljetin;

import linjasto.komponentit.siirtävät.Siirtävä;

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

}

