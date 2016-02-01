package linjasto.komponentit;

import java.util.UUID;

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
        if (!käynnissä) {
            käynnissä = true;
            säie = new Thread(this);
            säie.start();
        } else {
            System.err.println("Komponenttia '" + TUNNUS + "' yritettiin käynnistää uudelleen kun se oli vielä käynnissä.");
        }
    }

    public void sammuta() {
        käynnissä = false;
    }

    public boolean onkoKäynnissä() {
        return käynnissä;
    }

    public boolean vapauta(UUID käyttäjäId) {
        return true;
    }

    public void run() {
        System.out.println("Komponentti '" + TUNNUS +"' käynnistetty");
    }
}
