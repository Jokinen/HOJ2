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
    protected Osio seuraavaOsio;
    protected UUID käyttäjä;

    public Siirtävä(String tunnus, int v) {
        super(tunnus);
        VIRTAAMA = v;
    }

    public void käynnistä(Osio osio, UUID käyttäjäId) {
        this.seuraavaOsio = osio;
        this.käyttäjä = käyttäjäId;
        super.käynnistä();
    }

    public int haeVirtaama() {
        return VIRTAAMA;
    }
}
