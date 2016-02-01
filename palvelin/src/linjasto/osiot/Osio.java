package linjasto.osiot;

import linjasto.komponentit.Komponentti;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Luokka joka simuloi yhtä linjaston askelta.
 *
 * HUOM! LinjastoOsa on ABSTRAKTI luokka, jolloin siitä ei voi luoda olioita,
 *       mutta sen perivistä luokista voi. Tässä tapauksessa luokalla on kaksi perijää:
 *        -  Siirtävä osille linjastoa joilla tapahtuu siirtämistä
 *        -  Varastoiva osille linjastoa joihin raaka aine jätetään jotain prosessia varten
 *
 *       Tämä huomio selventää vain luokan toimivuutta, eikä siinä esitettyjen tietojen
 *       tulisi asettaa rajoitteita luokan geneerisyydelle. Tämän luokan toteutuksen ei
 *       tulisi olla sidottu kahden mainitun aliluokkansa toteutuksista. Ne manitaan tässä
 *       ainoastaan selventämään luokkarakennetta.
 *
 *       TODO: Poista viimeinen ylempi HUOM! ennen koodin esittelyä.
 */
public abstract class Osio {
    private final String TUNNUS;
    protected final ArrayList<Komponentti> komponentit;

    public Osio(String tunnus, ArrayList<Komponentti> k) {
        this.TUNNUS = tunnus;
        komponentit = k;
    }

    /**
     * Palauttaa tämän osin tunnuksen.
     *
     * @.post   RETURN = String tunnus
     */
    public String haeTunnus() {
        return TUNNUS;
    }

    /**
     * Palauttaa komponentin haetulla tunnuksella.
     *
     * @.pre    EXISTS(
     *              FOREACH(komponentti in komponentit; komponentti.haeTunnus() == tunnus)
     *          )
     * @.post   RETURN = FOREACH(komponentti in komponentit;
     *                          komponentti.haeTunnus() == tunnus)
     */
    public Komponentti haeKomponentti(String tunnus) {
        Komponentti palautettavaKomponentti = null;
        for (Komponentti komponentti : komponentit) {
            if (komponentti.haeTunnus().equals(tunnus))
                palautettavaKomponentti = komponentti;
        }
        return palautettavaKomponentti;
    }

    public void valmis(String komponentinTunnus, UUID käyttäjäId) {
        linjasto.komponentit.varastoivat.Varastoiva komponentti = (linjasto.komponentit.varastoivat.Varastoiva) haeKomponentti(komponentinTunnus);
        if (komponentti.haeVarattu() && komponentti.haeKäyttäjä().equals(käyttäjäId)) {
            komponentti.vapauta(käyttäjäId);
        }
    }

}
