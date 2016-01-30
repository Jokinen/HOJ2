package linjasto.osiot;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Linjaston osa, johon raaka-ainetta varastoidaan jotain tarkoitusta varten. Toisin
 * sanoen, tähän osaan kuuluvissa komponenteissa raaka-aineen voidaan miettiä saavuttavan
 * jonkinlaisen stabiilin tilan.
 */
public class Varastoiva extends Osio {

    public Varastoiva(String tunnus, ArrayList<Komponentti> k) {
        super(tunnus, k);
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
