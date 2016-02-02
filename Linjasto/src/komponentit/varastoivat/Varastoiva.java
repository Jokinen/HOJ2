package komponentit.varastoivat;

import apuluokat.RaakaAine;
import komponentit.Komponentti;

import java.util.UUID;

/**
 * Varastoiviin komponentteihin jää vastaanoton jälkeen raaka-ainetta, joten niille
 * on toteutettava siirrä-metodi, jonka avulla säiliöstä saa sirrettyä sen sisältöä
 * pois.
 *
 * Komponentit, jotka varastoivat tavaraa, perivät tämän luokan.
 */
public abstract class Varastoiva extends Komponentti {
    private int maksimiKoko;   // kiloa
    private int täyttöAste;
    private RaakaAine raakaAine;

    // Komponentin varaamista koskevat
    private boolean varattu = false;
    private UUID käyttäjä;
    protected boolean täytetään = false;
    protected boolean tyhjennetään = false;
    private String komponentinTunnus = "";


    public Varastoiva(String t, int m) {
        super(t);
        maksimiKoko = m;
    }

    public RaakaAine haeRaakaAine() {
        return raakaAine;
    }

    public boolean haeVarattu() {
        return varattu;
    }

    public UUID haeKäyttäjä() {
        return käyttäjä;
    }

    public String haeKomponentinTunnus() {
        return komponentinTunnus;
    }

    // Palauttaa komponentin täyttöasteen
    public int haeTäyttöaste() {
        return täyttöAste;
    }

    // Asettaa komponentin täyttöasteen
    public void asetaTäyttöaste(int täyttöAste) {
        this.täyttöAste = täyttöAste;
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
    public synchronized int vastaanota(int määrä, RaakaAine raakaAine) {
        if (this.raakaAine == null) {
            this.raakaAine = raakaAine;
        }
        täyttöAste = täyttöAste + määrä;
        return määrä;
    }

    public synchronized int siirrä(int määrä, RaakaAine raakaAine) {
        täyttöAste = täyttöAste - määrä;
        if (täyttöAste == 0) {
            this.raakaAine = null;
        }
        return määrä;
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

    public boolean vapauta(UUID käyttäjäId) {
        boolean bol = true;
        if (varattu && käyttäjäId.equals(this.käyttäjä)) {
            varattu = false;
            käyttäjä = null;
        } else {
            bol = false;
            System.err.println("Komponentti '" + super.haeTunnus() + "' yritettiin vapauttaa, mutta vapautus epäonnistui.");
        }
        return bol;
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
        return maksimiKoko - täyttöAste;
    }

    public boolean onkoVapaa(String komponentinTunnus, UUID käyttäjäId, RaakaAine raakaAine) {
        boolean onVapaaKysyjälle = false;
        if ((täytetään || tyhjennetään) && komponentinTunnus.equals(this.komponentinTunnus)) {
            onVapaaKysyjälle = true;
        } else if (!täytetään && !tyhjennetään) {
            onVapaaKysyjälle = true;
        }

        boolean onVapaaRaakaAineelle = false;
        if (this.raakaAine != null && raakaAine.haeNimi().equals(this.raakaAine.haeNimi())) {
            onVapaaRaakaAineelle = true;
        } else if (this.raakaAine == null) {
            onVapaaRaakaAineelle = true;
        }

        return varattu && käyttäjäId.equals(käyttäjä) && onVapaaKysyjälle && onVapaaRaakaAineelle;
    }

    public synchronized void varaaTäyttö(String komponentinTunnus) {
        if (!täytetään) {
            System.out.println(super.haeTunnus() + " varattiin (täyttö) (" + komponentinTunnus + ")");
            täytetään = true;
            this.komponentinTunnus = komponentinTunnus;
        }
    }

    public synchronized void varaaTyhjennys(String komponentinTunnus) {
        if (!tyhjennetään) {
            System.out.println(super.haeTunnus() + " varattiin (tyhjennys) (" + komponentinTunnus + ")");
            tyhjennetään = true;
            this.komponentinTunnus = komponentinTunnus;
        }
    }

    public synchronized void vapautaSiirrosta() {
        System.out.println(super.haeTunnus() + " vapautettiin (" + komponentinTunnus + ")");
        täytetään = false;
        tyhjennetään = false;
        komponentinTunnus = "";
    }
}
