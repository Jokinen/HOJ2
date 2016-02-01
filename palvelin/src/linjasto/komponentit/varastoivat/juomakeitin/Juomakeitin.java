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

            super.sammuta();
        } else {
            System.out.print("Jo prosessoitua juomaa yritettiinprosessoida uudestaan");
        }
    }

    public int siirrä(int määrä) {
        int juomaaAluksi = 0;
        if (keitetty) {
            juomaa = juomaa - määrä;
        } else {
            System.out.print("Juomakeittimestä yritettiin siirtää juomaa, mutta juomaa ei ole vielä keitetty.");
        }
        return juomaaAluksi - juomaa;
    }
}