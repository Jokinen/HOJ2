package linjasto.osiot;

import linjasto.komponentit.Komponentti;

import java.util.ArrayList;

/**
 * Linjaston osa, johon raaka-ainetta varastoidaan jotain tarkoitusta varten. Toisin
 * sanoen, tähän osaan kuuluvissa komponenteissa raaka-aineen voidaan miettiä saavuttavan
 * jonkinlaisen stabiilin tilan.
 */
public class Varastoiva extends Osio {
    /*
    ArrayListin kohdeluokkaan viitataan tässä pakettipolulla, sillä luokka
    samanniminen kuin tämä luokka. Pakettien sisäisen selkeän nimeämisen
    säilyttämiseksi kumpaakaan luokkaa ei nimetty uudelleen.
    */
    private ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit;

    public Varastoiva(String t, String e, String s, ArrayList<linjasto.komponentit.varastoivat.Varastoiva> k) {
        super(t, e, s);
        komponentit = k;
    }

    /**
     * @see Osio (String, boolean, String)
     */
    public Varastoiva(String t, boolean a, String s, ArrayList<linjasto.komponentit.varastoivat.Varastoiva> k) {
        super(t, a, s);
        komponentit = k;
    }

    /**
     * @see Osio (String, String, boolean)
     */
    public Varastoiva(String t, String e, boolean a, ArrayList<linjasto.komponentit.varastoivat.Varastoiva> k) {
        super(t, e, a);
        komponentit = k;
    }
}
