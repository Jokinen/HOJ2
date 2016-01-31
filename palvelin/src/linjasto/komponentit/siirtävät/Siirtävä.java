package linjasto.komponentit.siirtävät;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;
import linjasto.osiot.Osio;
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
    protected int määrä;

    public Siirtävä(String tunnus, int v) {
        super(tunnus);
        VIRTAAMA = v;
    }

    public void käynnistä(Osio edellinenOsio, Osio seuraavaOsio, UUID käyttäjäId, int määrä) {
        this.edellinenOsio = edellinenOsio;
        this.seuraavaOsio = seuraavaOsio;
        this.käyttäjä = käyttäjäId;
        this.määrä = määrä;
        super.käynnistä();
    }

    public int haeVirtaama() {
        return VIRTAAMA;
    }
}
