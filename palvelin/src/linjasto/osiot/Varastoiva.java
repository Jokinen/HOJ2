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
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVapaat(määrä, käyttäjäId);
        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            komponentti.vastaanota(määrä/komponentit.size());
            siirretty = siirretty + määrä/komponentit.size();
        }
        return siirretty;
    }

    private ArrayList<linjasto.komponentit.varastoivat.Varastoiva> haeVapaat(int määrä, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = new ArrayList<>();
        for (Komponentti komp : this.komponentit) {
            linjasto.komponentit.varastoivat.Varastoiva komponentti = (linjasto.komponentit.varastoivat.Varastoiva) komp;
            if (!komponentti.onTäynnä()) {
                if (komponentti.haeVarattu() && käyttäjäId == komponentti.haeKäyttäjä()) {
                    komponentit.add(komponentti);
                }
            } else if (komponentti.haeVarattu() && käyttäjäId == komponentti.haeKäyttäjä()) {
                komponentti.vapauta(käyttäjäId);
            }
        }
        int määräYhdelle;
        if (komponentit.size() > 0)
            määräYhdelle = määrä / komponentit.size();
        else
            määräYhdelle = määrä;
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komp = new ArrayList<>();
        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (komponentti.onTilaa(määräYhdelle))
                komp.add(komponentti);
        }
        return komp;
    }

    public void valmis(UUID käyttäjäId) {
        for (Komponentti komp : komponentit) {
            linjasto.komponentit.varastoivat.Varastoiva komponentti = (linjasto.komponentit.varastoivat.Varastoiva) komp;
            if (komponentti.haeVarattu() && komponentti.haeKäyttäjä() == käyttäjäId) {
                komponentti.vapauta(käyttäjäId);
            }
        }
    }
}
