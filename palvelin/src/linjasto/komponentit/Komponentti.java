package linjasto.komponentit;

import apumäärittelyt.RaakaAine;
import linjasto.osiot.Osio;
import omatVirheilmoitukset.LiianSuuriMääräException;

public abstract class Komponentti implements Runnable {
    protected Thread säie;
    private final String TUNNUS;
    protected volatile boolean käynnissä = false;

    public Komponentti(String t) {
        TUNNUS = t;
    }

    public String haeTunnus() {
        return TUNNUS;
    }

    public void käynnistä() {
        käynnissä = true;
        säie = new Thread(this);
        säie.start();
    }

    public void sammuta() {
        käynnissä = false;
    }

    public boolean onkoKäynnissä() {
        return käynnissä;
    }

    public void run() {
        System.out.println("Komponentti '" + TUNNUS +"' käynnistetty");
    }
}
