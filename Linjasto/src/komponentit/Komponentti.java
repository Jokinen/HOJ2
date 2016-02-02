package komponentit;

import java.util.UUID;

// Siirtävä- ja Varastoiva-luokat perivät Komponentti-luokan

public abstract class Komponentti implements Runnable {
    private final String TUNNUS;
    protected volatile boolean käynnissä = false;

    protected Komponentti(String t) {
        TUNNUS = t;
    }

    public String haeTunnus() {
        return TUNNUS;
    }

    // Käynnistää komponentin
    public void käynnistä() {
        if (!käynnissä) {
            käynnissä = true;
            Thread säie = new Thread(this);
            säie.start();
        } else {
            System.err.println("Komponenttia '" + TUNNUS + "' yritettiin käynnistää uudelleen kun se oli vielä käynnissä.");
        }
    }

    // Komponentin sammuttaminen
    protected void sammuta() {
        käynnissä = false;
    }

    // Palauttaa tiedon siitä, onko jokin komponentti käynnissä
    public boolean onkoKäynnissä() {
        return käynnissä;
    }

    public void run() {
        System.out.println("Komponentti '" + TUNNUS +"' käynnistetty");
    }
}
