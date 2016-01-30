package linjasto.komponentit.varastoivat;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;
import linjasto.osiot.Osio;
import omatVirheilmoitukset.LiianSuuriMääräException;

/**
 * Varastoiviin komponentteihin jää vastaanoton jälkeen raaka-ainetta, joten niille
 * on toteutettava siirrä-metodi, jonka avulla säiliöstä saa sirrettyä sen sisältöä
 * pois.
 */
public abstract class Varastoiva extends Komponentti {
    private final int maksimiKoko;   // kiloa
    private boolean täytetään = false;
    private boolean tyhjennetään = false;
    private int täyttöAste;
    private RaakaAine raakaAine;

    public Varastoiva(String t, int m) {
        super(t);
        maksimiKoko = m;
    }

    /**
     * Lisää Varastoiva-komponenttin raaka-ainetta jonkin määrän.
     *
     * @param raakaAine siiloon siirrettävä raaka-aine;
     *                  raakaAine.getClass().equals(haeRaakaAine().getClass()) ||
     *                  raakaAine == null
     * @param määrä     siirrettävän raaka-aineen määrä;
     *                  määrä < maksimiKoko &&
     *                  määrä + täyttöAste < maksimiKoko
     *
     * @.postPrivate täyttöAste = täyttöAste + määrä
     */
    public void vastaanota(RaakaAine raakaAine, int määrä, Osio seuraavOsio) throws LiianSuuriMääräException {
        // TODO
    }

    public void siirrä(RaakaAine raakaAine, int määrä, Osio seuraavaOsio) throws LiianSuuriMääräException {
        // TODO
    }

    /**
     * RaakaAineSiilon ollessa täynnä palauttaa true.
     *
     * @return  if (this.täyttöAste == this.maksimiKoko)
     *              return true
     *          else
     *              return false
     */
    public boolean onTäynnä() {
        return täyttöAste == maksimiKoko;
    }

    /**
     * RaakaAineSiilon ollessa tyhjä palauttaa true.
     *
     * @return  if (this.täyttöAste == 0)
     *              return true
     *          else
     *              return false
     */
    public boolean onTyhjä() {
        return täyttöAste == 0;
    }

    /**
     * Palauttaa RaakaAineSiillossa jäljellä olevan tilan määrän
     *
     * @return  RESULT = this.maksimiKoko - this.täyttöAste
     *          RESULT < this.maksimiKoko
     */
    public int tilaaJäljellä() {
        return 1;
    }
}
