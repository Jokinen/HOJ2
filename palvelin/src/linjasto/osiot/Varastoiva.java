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
    public int vastaanota(int määrä, UUID käyttäjäId, String komponentinTunnus) {
        int siirretty = 0;

        linjasto.komponentit.varastoivat.Varastoiva komponentti = haeVastaanottava(komponentinTunnus, käyttäjäId);
        if (komponentti != null) {
            komponentti.varaaTäyttö(komponentinTunnus);
            if (komponentti.tilaaJäljellä() < määrä) {
                siirretty += komponentti.vastaanota(komponentti.tilaaJäljellä());
                komponentti.vapautaSiirrosta();
            } else {
                siirretty += komponentti.vastaanota(määrä);
            }
            if (komponentti.onTäynnä()) {
                komponentti.vapautaSiirrosta();
            }
        }
        return siirretty;
    }

    public int siirrä(int määrä, UUID käyttäjäId, String komponentinTunnus) {
        int siirretty = 0;
        linjasto.komponentit.varastoivat.Varastoiva komponentti = haeSiirtävä(komponentinTunnus, käyttäjäId);
        if (komponentti != null) {
            komponentti.varaaTyhjennys(komponentinTunnus);
            if (komponentti.tavaraaJäljellä() < määrä) {
                siirretty += komponentti.siirrä(komponentti.tilaaJäljellä());
            } else {
                komponentti.siirrä(määrä);
                siirretty += määrä;
            }
            if (komponentti.onTyhjä()) {
                komponentti.vapautaSiirrosta();
            }
        }
        return siirretty;
    }

    private linjasto.komponentit.varastoivat.Varastoiva haeVastaanottava(String komponentinTunnus, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVapaat(komponentinTunnus, käyttäjäId);

        linjasto.komponentit.varastoivat.Varastoiva komp = null;
        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTäynnä()) {
                komp = komponentti;
                break;
            }
        }
        return komp;
    }

    private linjasto.komponentit.varastoivat.Varastoiva haeSiirtävä(String komponentinTunnus, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVapaat(komponentinTunnus, käyttäjäId);

        linjasto.komponentit.varastoivat.Varastoiva komp = null;
        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTyhjä()) {
                komp = komponentti;
                break;
            }
        }
        return komp;
    }

    public int haeSiirettäväMäärä(String komponentinTunnus, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVapaat(komponentinTunnus, käyttäjäId);
        int määrä = 0;

        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTyhjä()) {
                määrä = komponentti.haeTäyttöaste();
                break;
            }
        }
        return määrä;
    }

    private ArrayList<linjasto.komponentit.varastoivat.Varastoiva> haeVapaat(String komponentinTunnus, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = new ArrayList<>();
        for (Komponentti komp : this.komponentit) {
            linjasto.komponentit.varastoivat.Varastoiva komponentti = (linjasto.komponentit.varastoivat.Varastoiva) komp;
            if (komponentti.onkoVapaa(komponentinTunnus, käyttäjäId)) {
                komponentit.add(komponentti);
            }
        }
        return komponentit;
    }

}
