package linjasto.komponentit.siirtävät;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;
import linjasto.osiot.Osio;
import linjasto.osiot.Varastoiva;
import omatVirheilmoitukset.LiianSuuriMääräException;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Komponentti, joka siirtää.
 */
public abstract class Siirtävä extends Komponentti {
    protected final int VIRTAAMA;
    protected Osio edellinenOsio;
    protected Osio seuraavaOsio;
    protected UUID käyttäjä;
    protected int erä;

    public Siirtävä(String tunnus, int v) {
        super(tunnus);
        VIRTAAMA = v;
    }

    public void käynnistä(Osio edellinenOsio, Osio seuraavaOsio, UUID käyttäjäId, int määrä) {
        this.edellinenOsio = edellinenOsio;
        this.seuraavaOsio = seuraavaOsio;
        this.käyttäjä = käyttäjäId;
        this.erä = määrä;
        super.käynnistä();
    }

    public int haeVirtaama() {
        return VIRTAAMA;
    }

    @Override
    public void run() {
        if (edellinenOsio == null && seuraavaOsio != null) {
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
                if (erä >= VIRTAAMA) {
                    määrä = VIRTAAMA;
                } else {
                    määrä = erä;
                }
                int siirrettyMäärä = osio.vastaanota(määrä, käyttäjä, super.haeTunnus());

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

                int haettuMäärä = edellinenOsio.siirrä(määrä, käyttäjä, super.haeTunnus());

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

                int haettuMäärä = edellinenOsio.siirrä(määrä, käyttäjä, super.haeTunnus());
                int siirrettyMäärä = 0;
                while (haettuMäärä != siirrettyMäärä) {
                    siirrettyMäärä += seuraavaOsio.vastaanota(haettuMäärä, käyttäjä, super.haeTunnus());
                }

                erä = erä - siirrettyMäärä;

                if (erä == 0) {
                    super.sammuta();
                }
            }
        }
    }
}
