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
    protected final int VIRTAAMA;
    protected Osio seuraavaOsio;

    public Siirtävä(String tunnus, int v) {
        super(tunnus);
        VIRTAAMA = v;
    }

    public void käynnistä(Osio osio) {
        this.seuraavaOsio = osio;
        super.käynnistä();
    }

    public int haeVirtaama() {
        return VIRTAAMA;
    }
}
