/**
 *  raaka-aineen tyyppi
 *  täyttöaste 0-10000 kiloa
 *  siiloa ei voi tyhjentää ja täyttää samaan aikaan
 *  Samasta siilosta voi ottaa raaka-ainetta vain yksi kuljetin kerrallaan
 */
public class RaakaAineSiilo {
    final int maksimiKoko = 10000;   // kiloa
    private RaakaAine raakaAine;
    private boolean täytetään = false;
    private boolean tyhjennetään = false;
    private int täyttöAste;

    /**
     * RaakaAineSiilon ollessa täynnä, palauttaa true.
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
     * RaakaAineSiilon ollessa tyhjä, palauttaa true.
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
     *
     */

}
