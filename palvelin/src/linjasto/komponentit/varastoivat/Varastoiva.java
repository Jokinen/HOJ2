package linjasto.komponentit.varastoivat;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;
import linjasto.osiot.Osio;
import linjasto.Linjasto;
import omatVirheilmoitukset.LiianSuuriMääräException;

import java.util.UUID;

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
    private boolean varattu = false;
    private UUID käyttäjä;

    public Varastoiva(String t, int m) {
        super(t);
        maksimiKoko = m;
    }

    public boolean haeVarattu() {
        return varattu;
    }

    public UUID haeKäyttäjä() {
        return käyttäjä;
    }

    /**
     * Lisää Varastoiva-komponenttin raaka-ainetta jonkin määrän.
     *
     * @param määrä     siirrettävän raaka-aineen määrä;
     *                  määrä < maksimiKoko &&
     *                  määrä + täyttöAste < maksimiKoko
     *
     * @.postPrivate täyttöAste = täyttöAste + määrä
     */
    public void vastaanota(int määrä) {
        täyttöAste = täyttöAste + määrä;
    }

    public void siirrä(RaakaAine raakaAine, int määrä, Osio seuraavaOsio) throws LiianSuuriMääräException {
        // TODO
    }

    public boolean varaa(UUID käyttäjäId) {
        boolean bol = true;
        if (!varattu) {
            varattu = true;
            käyttäjä = käyttäjäId;
        } else {
            bol = false;
            System.err.println("Jo varattua siiloa yritettiin varata uudelleen.");
        }
        return bol;
    }

    public int haeTäyttöaste() {
        return täyttöAste;
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

    public boolean onTilaa(int määrä) {
        return (täyttöAste + määrä) <= maksimiKoko;
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
