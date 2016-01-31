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
    public Juomakeitin(String tunnus) {
        super(tunnus, 2000);
    }
}