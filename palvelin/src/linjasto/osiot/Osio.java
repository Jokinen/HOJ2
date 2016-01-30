package linjasto.osiot;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Luokka joka simuloi yhtä linjaston askelta. Simuloi heikolla tasolla kahteen
 * suuntaan linkitettyä listaa. Osio:n on vain tiedettävä seuraajansa ja
 * edeltäjänsä sillä linjastolla siirtymiä tapahtuu ainoastaan eteenpäin, ja
 * ainoastaan heti seuraavalle linjaston osalle (ts. linjaston osia ei voi ohittaa).
 *
 * HUOM! Jos Osio:n edellinenOsa kentän jättää tyhjäksi, pidetään tätä
 *       objektia silloin "aloitusalkiona".
 *
 *       TODO: Kirjoita luokkainvariantiksi
 *
 * HUOM! Jos Osio:n seuraavaOsa kentän jättää tyhjäksi, pidetään tätä
 *       objektia silloin "lopetusalkiona".
 *
 *       TODO: Kirjoita luokkainvariantiksi
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
    private Osio edellinen;
    private Osio seuraava;
    protected final ArrayList<Komponentti> komponentit;


    public Osio(String tunnus, ArrayList<Komponentti> k) {
        this.TUNNUS = tunnus;
        komponentit = k;
    }

    public void setEdellinen(Osio o) {
        edellinen = o;
    }

    public void setOsio(Osio s) {
        seuraava = s;
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
    public Komponentti haeKomponentti(String tunnus) throws RemoteException {
        Komponentti palautettavaKomponentti = null;
        for (Komponentti komponentti : komponentit) {
            if (komponentti.haeTunnus().equals(tunnus))
                palautettavaKomponentti = komponentti;
        }
        return palautettavaKomponentti;
    }

    /**
     * Tarkistaako, että onko linjaston osa ensimmäinen linjaston osa.
     *
     * @return if (edellinenOsa === null)
     *              return true
     *         else
     *              return false
     */
    public boolean onEnsimmäinen() {
        // TODO: implement
        return true;
    }

    /**
     * Tarkistaako, että onko linjaston osa viimeinen linjaston osa.
     *
     * @return if (seuraavaOsa === null)
     *              return true
     *         else
     *              return false
     */
    public boolean onViimeinen() {
        // TODO: implement
        return false;
    }

    /**
     * Palauttaa tätä osiota seuraavan osion.
     *
     * @.post RETURN = seuraavaOsio
     */
    public Osio haeSeuraavaOsio() {
        return seuraava;
    }

    /**
     * Metodi, joka vastaanottaa osiolle raaka-ainetta, huolehtien sen tasaisesta
     * jakautumisesta osion komponenteille.
     *
     * @param raakaAine vastaanotettava RaakaAine
     * @param määrä     vastaanotettavan RaakaAineen määrä
     */
    public abstract void vastaanota(RaakaAine raakaAine, int määrä);


}
