package osiot;

import komponentit.Komponentti;
import apuluokat.RaakaAine;

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
    public synchronized int vastaanota(int määrä, int kokonaisMäärä, UUID käyttäjäId, String komponentinTunnus, RaakaAine raakaAine) {
        int siirretty = 0;

        komponentit.varastoivat.Varastoiva komponentti = haeVastaanottava(komponentinTunnus, käyttäjäId, raakaAine);
        if (komponentti != null) {
            komponentti.varaaTäyttö(komponentinTunnus);
            if (komponentti.tilaaJäljellä() < määrä) {
                siirretty += komponentti.vastaanota(komponentti.tilaaJäljellä(), raakaAine);
                komponentti.vapautaSiirrosta();
            } else {
                siirretty += komponentti.vastaanota(määrä, raakaAine);
            }
            if (komponentti.onTäynnä() || kokonaisMäärä - määrä <= 0) {
                komponentti.vapautaSiirrosta();
            }
        }
        return siirretty;
    }

    public synchronized int siirrä(int määrä, int kokonaisMäärä, UUID käyttäjäId, String komponentinTunnus, RaakaAine raakaAine) {
        int siirretty = 0;
        komponentit.varastoivat.Varastoiva komponentti = haeSiirtävä(komponentinTunnus, käyttäjäId, raakaAine);
        if (komponentti != null) {
            komponentti.varaaTyhjennys(komponentinTunnus);
            if (komponentti.haeTäyttöaste() < määrä) {
                siirretty += komponentti.siirrä(komponentti.haeTäyttöaste(), raakaAine);
                komponentti.vapautaSiirrosta();
            } else {
                siirretty += komponentti.siirrä(määrä, raakaAine);
            }
            if (komponentti.onTyhjä() || kokonaisMäärä - määrä <= 0) {
                komponentti.vapautaSiirrosta();
            }
        }
        return siirretty;
    }

    private synchronized komponentit.varastoivat.Varastoiva haeVastaanottava(String komponentinTunnus, UUID käyttäjäId, RaakaAine raakaAine) {
        ArrayList<komponentit.varastoivat.Varastoiva> komponentit = haeVapaat(komponentinTunnus, käyttäjäId, raakaAine);

        komponentit.varastoivat.Varastoiva komp = null;
        for (komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (komponentti.haeKomponentinTunnus().equals(komponentinTunnus)) {
                komp = komponentti;
                break;
            }
        }

        if (komp != null)
            return komp;

        for (komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTäynnä()) {
                komp = komponentti;
                break;
            }
        }
        return komp;
    }

    private synchronized komponentit.varastoivat.Varastoiva haeSiirtävä(String komponentinTunnus, UUID käyttäjäId, RaakaAine raakaAine) {
        ArrayList<komponentit.varastoivat.Varastoiva> komponentit = haeVapaat(komponentinTunnus, käyttäjäId, raakaAine);

        komponentit.varastoivat.Varastoiva komp = null;
        for (komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (komponentti.haeKomponentinTunnus().equals(komponentinTunnus)) {
                komp = komponentti;
                break;
            }
        }

        if (komp != null)
            return komp;

        for (komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (komponentti.haeTäyttöaste() > 0) {
                komp = komponentti;
                break;
            }
        }
        return komp;
    }

    public synchronized int haeSiirettäväMäärä(String komponentinTunnus, UUID käyttäjäId, RaakaAine raakaAine) {
        ArrayList<komponentit.varastoivat.Varastoiva> komponentit = haeVapaat(komponentinTunnus, käyttäjäId, raakaAine);
        int määrä = 0;

        for (komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTyhjä()) {
                määrä = komponentti.haeTäyttöaste();
                break;
            }
        }
        return määrä;
    }

    private synchronized ArrayList<komponentit.varastoivat.Varastoiva> haeVapaat(String komponentinTunnus, UUID käyttäjäId, RaakaAine raakaAine) {
        ArrayList<komponentit.varastoivat.Varastoiva> komponentit = new ArrayList<>();
        for (Komponentti komp : this.komponentit) {
            komponentit.varastoivat.Varastoiva komponentti = (komponentit.varastoivat.Varastoiva) komp;
            if (komponentti.onkoVapaa(komponentinTunnus, käyttäjäId, raakaAine)) {
                komponentit.add(komponentti);
            }
        }
        return komponentit;
    }

}
