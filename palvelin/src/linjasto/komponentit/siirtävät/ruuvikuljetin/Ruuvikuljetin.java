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
        if (super.edellinenOsio == null) {
            // Edellinen osio on null, niin silloin tämä kuljetin on aloittava kuljetin
            Varastoiva osio = (Varastoiva) this.seuraavaOsio;
            while (super.käynnissä) {
                // Imitoidaan, että kuljetus kestää sekunnin
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int määrä = 0;
                if (super.erä >= VIRTAAMA) {
                    määrä = VIRTAAMA;
                } else {
                    määrä = erä;
                }
                int siirrettyMäärä = osio.vastaanota(määrä, super.käyttäjä);
                super.erä = super.erä - siirrettyMäärä;
                if (super.erä == 0) {
                    super.sammuta();
                }
            }
        } else {
            Varastoiva edellinenOsio = (Varastoiva) super.edellinenOsio;
            Varastoiva seuraavaOsio = (Varastoiva) super.seuraavaOsio;
            while (super.käynnissä) {

                // Imitoidaan, että kuljetus kestää sekunnin
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int määrä = 0;
                if (super.erä >= VIRTAAMA) {
                    määrä = VIRTAAMA;
                } else {
                    määrä = erä;
                }

                int haettuMäärä = edellinenOsio.siirrä(määrä, super.käyttäjä);
                int siirrettyMäärä = 0;
                while (haettuMäärä != siirrettyMäärä) {
                    siirrettyMäärä += seuraavaOsio.vastaanota(haettuMäärä, super.käyttäjä);
                }

                super.erä = super.erä - siirrettyMäärä;

                if (super.erä == 0) {
                    super.sammuta();
                }
            }
        }
    }
}

