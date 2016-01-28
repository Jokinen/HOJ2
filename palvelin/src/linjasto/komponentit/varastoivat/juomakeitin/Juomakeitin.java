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


public class Juomakeitin extends Varastoiva, Thread {
    protected boolean juomakeitinVarattu;
    protected boolean juomakeitinKaynnissa;
    public String juomakeittimenVaraaja; // toteutus UUID:llä ja Stringillä nimi?

    public Juomakeitin() {
        super(2000);
    }

    public boolean onkoJuomakeitinVarattu() {
        return juomakeitinVarattu;
    }

    public boolean juomakeitinKaynnissa() {
        return juomakeitinKaynnissa;
    }
}