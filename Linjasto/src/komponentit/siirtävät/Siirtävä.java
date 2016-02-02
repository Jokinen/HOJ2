package komponentit.siirtävät;

import apuluokat.RaakaAine;
import komponentit.Komponentti;
import osiot.Osio;
import osiot.Varastoiva;

import java.util.UUID;

/**
 * Komponentit, jotka siirtävät tavaraa, perivät tämän luokan.
 */

public abstract class Siirtävä extends Komponentti {
    private final int VIRTAAMA;
    private Osio edellinenOsio;
    private Osio seuraavaOsio;
    private UUID käyttäjä;
    private int erä;
    private RaakaAine raakaAine;

    public Siirtävä(String tunnus, int v) {
        super(tunnus);
        VIRTAAMA = v;
    }

    // Siirron toteutus
    public void käynnistä(Osio edellinenOsio, Osio seuraavaOsio, UUID käyttäjäId, int määrä, RaakaAine raakaAine) {
        this.edellinenOsio = edellinenOsio;
        this.seuraavaOsio = seuraavaOsio;
        this.käyttäjä = käyttäjäId;
        this.erä = määrä;
        this.raakaAine = raakaAine;
        super.käynnistä();
    }

    @Override
    public synchronized void run() {
        if (edellinenOsio == null && seuraavaOsio != null) {
            // Edellinen osio on null, niin silloin tämä kuljetin on aloittava kuljetin
            Varastoiva osio = (Varastoiva) this.seuraavaOsio;
            while (super.käynnissä) {
                // Imitoidaan, että kuljetus kestää sekunnin
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int määrä;
                if (erä >= VIRTAAMA) {
                    määrä = VIRTAAMA;
                } else {
                    määrä = erä;
                }
                int siirrettyMäärä = osio.vastaanota(määrä, erä, käyttäjä, super.haeTunnus(), this.raakaAine);

                erä = erä - siirrettyMäärä;

                if (erä == 0) {
                    super.sammuta();
                }
            }
        } else if (edellinenOsio != null && seuraavaOsio == null) {
            Varastoiva edellinenOsio = (Varastoiva) this.edellinenOsio;
            while (käynnissä) {

                // Imitoidaan, että kuljetus kestää sekunnin
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int määrä;
                if (erä >= VIRTAAMA) {
                    määrä = VIRTAAMA;
                } else {
                    määrä = erä;
                }

                int haettuMäärä = edellinenOsio.siirrä(määrä, erä, käyttäjä, super.haeTunnus(), this.raakaAine);

                erä = erä - haettuMäärä;

                if (erä == 0) {
                    super.sammuta();
                }
            }
        } else if (edellinenOsio != null) {
            Varastoiva edellinenOsio = (Varastoiva) this.edellinenOsio;
            Varastoiva seuraavaOsio = (Varastoiva) this.seuraavaOsio;
            while (käynnissä) {

                // Imitoidaan, että kuljetus kestää sekunnin
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int määrä;
                if (erä >= VIRTAAMA) {
                    määrä = VIRTAAMA;
                } else {
                    määrä = erä;
                }

                int haettuMäärä = edellinenOsio.siirrä(määrä, erä, käyttäjä, super.haeTunnus(), this.raakaAine);
                int siirrettyMäärä = 0;
                while (haettuMäärä != siirrettyMäärä) {
                    siirrettyMäärä += seuraavaOsio.vastaanota(haettuMäärä, erä, käyttäjä, super.haeTunnus(), this.raakaAine);
                }

                erä = erä - siirrettyMäärä;

                if (erä == 0) {
                    super.sammuta();
                }
            }
        }
    }
}
