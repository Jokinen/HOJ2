package linjasto.osiot;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

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
     * Metodi, joka vastaanottaa osiolle raaka-ainetta, huolehtien sen tasaisesta
     * jakautumisesta osion komponenteille.
     *
     * @param määrä     vastaanotettavan RaakaAineen määrä
     */
    public int vastaanota(int määrä, UUID käyttäjäId) {
        int siirretty = 0;

        linjasto.komponentit.varastoivat.Varastoiva komponentti = haeVastaanottava(määrä, käyttäjäId);

        if (komponentti != null) {
            if (komponentti.tilaaJäljellä() < määrä) {
                siirretty += komponentti.vastaanota(komponentti.tilaaJäljellä());
            } else {
                siirretty += komponentti.vastaanota(määrä);
            }
        }
        return siirretty;
    }

    public int siirrä(int määrä, UUID käyttäjäId) {
        int siirretty = 0;
        linjasto.komponentit.varastoivat.Varastoiva komponentti = haeSiirtävä(määrä, käyttäjäId);
        if (komponentti != null) {
            if (komponentti.tavaraaJäljellä() < määrä) {
                siirretty += komponentti.siirrä(komponentti.tilaaJäljellä());
                siirretty += siirrä(määrä - komponentti.tilaaJäljellä(), käyttäjäId);
            } else {
                komponentti.siirrä(määrä);
                siirretty += määrä;
            }
        }
        return siirretty;
    }

    private linjasto.komponentit.varastoivat.Varastoiva haeVastaanottava(int määrä, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVaratut(käyttäjäId);

        linjasto.komponentit.varastoivat.Varastoiva komp = null;
        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTäynnä()) {
                komp = komponentti;
                break;
            }
        }
        return komp;
    }

    private linjasto.komponentit.varastoivat.Varastoiva haeSiirtävä(int määrä, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVaratut(käyttäjäId);

        linjasto.komponentit.varastoivat.Varastoiva komp = null;
        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTyhjä()) {
                komp = komponentti;
                break;
            }
        }
        return komp;
    }

    public int haeSiirettäväMäärä(UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVaratut(käyttäjäId);
        int määrä = 0;

        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTyhjä()) {
                määrä = komponentti.haeTäyttöaste();
                break;
            }
        }
        return määrä;
    }

    private ArrayList<linjasto.komponentit.varastoivat.Varastoiva> haeVaratut(UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = new ArrayList<>();
        for (Komponentti komp : this.komponentit) {
            linjasto.komponentit.varastoivat.Varastoiva komponentti = (linjasto.komponentit.varastoivat.Varastoiva) komp;
            if (komponentti.haeVarattu() && käyttäjäId.equals(komponentti.haeKäyttäjä())) {
                komponentit.add(komponentti);
            }
        }
        return komponentit;
    }
}
