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
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVastaanottavat(määrä, käyttäjäId);
        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            komponentti.vastaanota(määrä/komponentit.size());
            siirretty = siirretty + määrä/komponentit.size();
        }
        return siirretty;
    }

    public int siirrä(int määrä, UUID käyttäjäId) {
        int siirretty = 0;
        linjasto.komponentit.varastoivat.Varastoiva komponentti = haeSiirtävä(määrä, käyttäjäId);
        if (komponentti != null) {
            if (komponentti.tilaaJäljellä() < määrä) {
                komponentti.siirrä(komponentti.tilaaJäljellä());
                siirretty += komponentti.tilaaJäljellä();
                määrä = määrä - komponentti.tilaaJäljellä();
                siirretty += siirrä(määrä, käyttäjäId);
            } else {
                komponentti.siirrä(määrä);
                siirretty += määrä;
            }
        }
        return siirretty;
    }

    private ArrayList<linjasto.komponentit.varastoivat.Varastoiva> haeVastaanottavat(int määrä, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVaratut(määrä, käyttäjäId);

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

    private linjasto.komponentit.varastoivat.Varastoiva haeSiirtävä(int määrä, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = haeVaratut(määrä, käyttäjäId);

        linjasto.komponentit.varastoivat.Varastoiva komp = null;
        for (linjasto.komponentit.varastoivat.Varastoiva komponentti : komponentit) {
            if (!komponentti.onTyhjä()) {
                komp = komponentti;
                break;
            }
        }
        return komp;
    }

    private ArrayList<linjasto.komponentit.varastoivat.Varastoiva> haeVaratut(int määrä, UUID käyttäjäId) {
        ArrayList<linjasto.komponentit.varastoivat.Varastoiva> komponentit = new ArrayList<>();
        for (Komponentti komp : this.komponentit) {
            linjasto.komponentit.varastoivat.Varastoiva komponentti = (linjasto.komponentit.varastoivat.Varastoiva) komp;
            if (komponentti.haeVarattu() && käyttäjäId.equals(komponentti.haeKäyttäjä())) {
                komponentit.add(komponentti);
            }
        }
        return komponentit;
    }

    public void valmis(UUID käyttäjäId) {
        for (Komponentti komp : komponentit) {
            linjasto.komponentit.varastoivat.Varastoiva komponentti = (linjasto.komponentit.varastoivat.Varastoiva) komp;
            if (komponentti.haeVarattu() && komponentti.haeKäyttäjä().equals(käyttäjäId)) {
                komponentti.vapauta(käyttäjäId);
            }
        }
    }
}
