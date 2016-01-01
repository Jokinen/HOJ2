package linjasto.osiot;

import apumäärittelyt.RaakaAine;

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
    private final ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit;

    public Varastoiva(String t, Osio e, Osio s, ArrayList<linjasto.komponentit.varastoivat.Varastoiva> k) {
        super(t, e, s);
        komponentit = k;
    }

    /**
     * @see Osio (String, boolean, String)
     */
    public Varastoiva(String t, boolean a, Osio s, ArrayList<linjasto.komponentit.varastoivat.Varastoiva> k) {
        super(t, a, s);
        komponentit = k;
    }

    /**
     * @see Osio (String, String, boolean)
     */
    public Varastoiva(String t, Osio e, boolean a, ArrayList<linjasto.komponentit.varastoivat.Varastoiva> k) {
        super(t, e, a);
        komponentit = k;
    }

    /**
     * @see Osio#vastaanota(RaakaAine, int)
     */
    public void vastaanota(RaakaAine raakaAine, int määrä) {}

    /**
     * Varastoivalle osiolle ominainen komponentti. Varastoitunutta ainetta on
     * kyettävä siirtämään myös ulos varastosta.
     *
     * @param raakaAine vastaanotettava RaakaAine
     * @param määrä     vastaanotettavan RaakaAineen määrä
     */
    public void siirrä(RaakaAine raakaAine, int määrä) {}
}
