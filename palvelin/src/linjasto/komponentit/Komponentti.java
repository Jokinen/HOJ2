package linjasto.komponentit;

import apumäärittelyt.RaakaAine;
import linjasto.osiot.Osio;
import omatVirheilmoitukset.LiianSuuriMääräException;

public abstract class Komponentti implements Runnable {
    private Thread säie;

    public abstract void vastaanota(RaakaAine raakaAine, int määrä, Osio seuraavOsio) throws LiianSuuriMääräException;

    public Komponentti() {
        säie = new Thread(this);
        säie.start();
    }

    public void run() {

    }
}
