package linjasto.komponentit.siirtävät.ruuvikuljetin;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.siirtävät.Siirtävä;
import linjasto.osiot.Varastoiva;

import java.util.concurrent.TimeUnit;

/**
 * Ruuvikuljetin raaka-ainesiilojen täyttöön.
 * Siirtää raaka-ainetta 200 kiloa sekunnissa
 * Täyttö ei voi siirtää enempää kuin vastaanottavaan yksikköön mahtuu
 * Raaka-ainetta tulee lisää tehtaaseen yhdessä erässä 40 tonnia
 * Siilo tai keitin ei saa ylitäyttyä
 */
public class Ruuvikuljetin extends Siirtävä {

    public Ruuvikuljetin(String tunnus) {
        super(tunnus, 200);
    }

    @Override
    public void run() {
        int erä = 40000;
        Varastoiva osio = (Varastoiva) this.seuraavaOsio;
        while (super.käynnissä) {
            // Imitoidaan, että kuljetus kestää sekunnin
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int määrä = 0;
            if (erä >= VIRTAAMA) {
                määrä = VIRTAAMA;
            } else {
                määrä = erä;
            }
            int siirrettyMäärä = osio.vastaanota(määrä);
            erä = erä - siirrettyMäärä;
            if (erä == 0) {
                super.sammuta();
            }
        }
    }
}

