package linjasto.komponentit.varastoivat.juomakeitin;

import apumäärittelyt.RaakaAine;
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
    private String juomanNimi;

    public Juomakeitin(String tunnus) {
        super(tunnus, 2000);
    }

    @Override
    public void run() {
        if (!keitetty && super.haeTäyttöaste() > 0 && super.haeVarattu()) {
            // Imitoidaan, että kuljetus kestää 20 sekuntia
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Juoma on valmis!
            this.juomaa = super.haeTäyttöaste() + vettä;
            juomanNimi = super.haeRaakaAine().haeJuomanNimi();

            keitetty = true;

        } else if (keitetty) {
            System.err.println("Juomakeitin '" + super.haeTunnus() + "' yritettiin käynnistää, vaikka se sisältää jo prosessoitua juomaa.");
            sammuta();
        } else if (super.haeTäyttöaste() <= 0) {
            System.err.println("Juomakeitin '" + super.haeTunnus() + "' yritettiin käynnistää, vaikka sille ei ole siirretty lainkaan raaka-ainetta.");
        } else if (!super.haeVarattu()) {
            System.err.println("Juomakeitin '" + super.haeTunnus() + "' yritettiin käynnistää varaamattomana.");
        }
        sammuta();
    }

    @Override
    public int siirrä(int määrä, RaakaAine raakaAine) {
        int siirretty = 0;
        if (keitetty) {
            juomaa -= määrä;
            siirretty = määrä;

            if (juomaa == 0) {
                super.asetaTäyttöaste(0);
                keitetty = false;
                juomanNimi = "";
            }
        } else {
            System.err.println("Juomakeittimestä yritettiin siirtää juomaa, mutta juomaa ei ole vielä keitetty.");
        }
        return siirretty;
    }

    @Override
    public int vastaanota(int määrä, RaakaAine raakaAine) {
        int siirretty = 0;
        if (!keitetty) {
            siirretty += super.vastaanota(määrä, raakaAine);
        } else {
            System.err.println("Juomakeittimestä yritettiin siirtää raaka-ainetta, kun siellä oli jo juomaa.");
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