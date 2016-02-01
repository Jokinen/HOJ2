package linjasto.komponentit.varastoivat.juomakeitin;

import linjasto.komponentit.varastoivat.Varastoiva;

/**
 * kenelle varattu
 * tilavuus 10000 litraa vettä ja 2000 kiloa raaka-ainetta
 * prosessoi juomaa 20 sekuntia
 * sisältää veden täytön ohjauksen, yksinkertaisuuden vuoksi oletetaan automaattisesti täyttyvän
 *  maksimitilavuuteen kun prosessin käynnistää
 * yhtä keitintä voi täyttää samanaikaisesti vain yksi kuljetin ja sitä voi tyhjentää vain yksi pumppu
 */


public class Juomakeitin extends Varastoiva {
    private int vettä = 10000;
    private boolean keitetty = false;
    private int juomaa = 0;

    public Juomakeitin(String tunnus) {
        super(tunnus, 2000);
    }

    @Override
    public void run() {
        if (!keitetty) {
            // Imitoidaan, että kuljetus kestää 20 sekuntia
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Juoma on valmis!
            this.juomaa = super.haeTäyttöaste() + vettä;

            keitetty = true;

            sammuta();
        } else {
            System.out.println("Jo prosessoitua juomaa yritettiinprosessoida uudestaan");
        }
    }

    @Override
    public int siirrä(int määrä) {
        int siirretty = 0;
        if (keitetty) {
            juomaa -= määrä;
            siirretty = määrä;
        } else {
            System.out.println("Juomakeittimestä yritettiin siirtää juomaa, mutta juomaa ei ole vielä keitetty.");
        }
        return siirretty;
    }

    @Override
    public int haeTäyttöaste() {
        int täyttöAste;
        if (!keitetty) {
            täyttöAste = super.haeTäyttöaste();
        } else {
            täyttöAste = juomaa;
        }
        return täyttöAste;
    }

}