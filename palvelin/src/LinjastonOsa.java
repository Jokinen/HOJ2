import linjastonKomponentit.LinjastonKomponentti;

import java.util.ArrayList;

/**
 * Luokka joka simuloi yhtä linjaston askelta. Simuloi heikolla tasolla kahteen
 * suuntaan linkitettyä listaa. LinjastonOsa:n on vain tiedettävä seuraajansa ja
 * edeltäjänsä sillä linjastolla siirtymiä tapahtuu ainoastaan eteenpäin, ja
 * ainoastaan heti seuraavallae linjaston osalle (ts. linjaston osia ei voi ohittaa).
 *
 * HUOM! Jos LinjastonOsa:n edellinenOsa kentän jättää tyhjäksi, pidetään tätä
 * objektia silloin "aloitusalkiona".
 *
 * HUOM! Jos LinjastonOsa:n seuraavaOsa kentän jättää tyhjäksi, pidetään tätä
 * objektia silloin "lopetusalkiona".
 */
public class LinjastonOsa {
    private final String TUNNUS;
    private ArrayList<LinjastonKomponentti> komponentit;
    private String edellinenOsa;
    private String seuraavaOsa;

    public LinjastonOsa (String tunnus, ArrayList<LinjastonKomponentti>komponentit, String edellinenOsa, String seuraavaOsa) {
        this.TUNNUS = tunnus;
        this.komponentit = komponentit;
        this.edellinenOsa = edellinenOsa;
        this.seuraavaOsa = seuraavaOsa;
    }

    public LinjastonOsa (String tunnus, ArrayList<LinjastonKomponentti>komponentit, String edellinenOsa, boolean arvo) {
        this.TUNNUS = tunnus;
        this.komponentit = komponentit;
        this.edellinenOsa = edellinenOsa;
    }

    public LinjastonOsa (String tunnus, ArrayList<LinjastonKomponentti>komponentit, boolean arvo, String seuraavaOsa) {
        this.TUNNUS = tunnus;
        this.komponentit = komponentit;
        this.seuraavaOsa = seuraavaOsa;
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
}
