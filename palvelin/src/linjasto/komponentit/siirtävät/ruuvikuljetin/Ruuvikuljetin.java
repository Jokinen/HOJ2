package linjasto.komponentit.siirtävät.ruuvikuljetin;

import linjasto.komponentit.siirtävät.Siirtävä;

import java.util.concurrent.TimeUnit;

/**
 * Ruuvikuljetin raaka-ainesiilojen täyttöön.
 * Siirtää raaka-ainetta 200 kiloa sekunnissa
 * Täyttö ei voi siirtää enempää kuin vastaanottavaan yksikköön mahtuu
 * Raaka-ainetta tulee lisää tehtaaseen yhdessä erässä 40 tonnia
 * Siilo tai keitin ei saa ylitäyttyä
 */
public class Ruuvikuljetin extends Siirtävä {

    public Ruuvikuljetin() {
        super(200);
    }

    public void siirrä(int siirrettäväMäärä) {
        if (siirrettäväMäärä > 200) {
            // siirron toteutus tähän
            TimeUnit.SECONDS.sleep(1);
            int jäljelläOlevaMäärä = siirrettäväMäärä - 200;
            siirrä(jäljelläOlevaMäärä);
        } else {
            // siirron toteutus tähän
        }
    }

}

