package linjasto.komponentit;

import apumäärittelyt.RaakaAine;
import linjasto.osiot.Osio;
import omatVirheilmoitukset.LiianSuuriMääräException;

public abstract class Komponentti implements Runnable {
    private Thread säie;
    private final String TUNNUS;
    private boolean käynnissä = false;
    private boolean onkoVarattu = false;

    public abstract void vastaanota(RaakaAine raakaAine, int määrä, Osio seuraavaOsio) throws LiianSuuriMääräException;

    public Komponentti(String t) {
        säie = new Thread(this);
        TUNNUS = t;
    }

    public String haeTunnus() {
        return TUNNUS;
    }

    public void käynnistä(String osionTunnus, String komponentinTunnus) {
        säie.start();
        run();

    }

    public void sammuta() {
        käynnissä = false;
        säie.interrupt();
    }

    public void run() {
        käynnissä = true;
        System.out.println("Komponentti käynnistetty");
    }
}
