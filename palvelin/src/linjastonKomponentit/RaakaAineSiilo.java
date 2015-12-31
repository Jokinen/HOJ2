package linjastonKomponentit;

/**
 *  raaka-aineen tyyppi
 *  täyttöaste 0-10000 kiloa
 *  siiloa ei voi tyhjentää ja täyttää samaan aikaan
 *  Samasta siilosta voi ottaa raaka-ainetta vain yksi kuljetin kerrallaan
 */
public class RaakaAineSiilo extends LinjastonKomponentti {
    final int maksimiKoko = 10000;   // kiloa
    private RaakaAine raakaAine;
    private boolean täytetään = false;
    private boolean tyhjennetään = false;
    private int täyttöAste;

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

    /**
     * Lisää siiloon raaka-ainetta jonkin määrän.
     *
     * (Käytännössä näillä oletuksilla määrä on aina 10 000, mutte implementaatiosta
     * riippuen tämä voidaan joutua siirtämään esimerkiksi erissä.)
     *
     * Siiloja käytetään ainoastaan linjaston alkuosassa varastoimaan järjestelmään
     * tuodut raaka-aine-erät. Näiden erien koko on aina 40 tonnia. Siilojen määrä on 4,
     * ja jokaisen siilon kapasiteetti on 10 tonnia, jolloin täytön tulisi aina loppua
     * siihen, että siilo on täynnä.
     *
     * Siiloa ei tule voida täyttää ellei se ole tyhjä. Toisaalta kaikki raaka-aineet
     * eivät mahtuisi järjestelmään, ja toisaalta raaka-aineet voisivat mennä sekaisin.
     *
     * @param raakaAine siiloon siirrettävä raaka-aine
     * @param määrä     siirrettävän raaka-aineen määrä,
     *                   määrä = 10000
     *
     * @.pre    onTyhjä()
     * @.post   onTäynnä()
     */
    public void lisääRaakaAinetta(RaakaAine raakaAine, int määrä) {}
}
