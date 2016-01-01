package linjasto.komponentit.siirtävät.pumppu;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.siirtävät.Siirtävä;
import linjasto.osiot.Osio;
import omatVirheilmoitukset.LiianSuuriMääräException;

/**
 * Siirtää juomaa 500 litraa sekunnissa
 * Pumpulle annetaan parametrina siirrettävä määrä tai se voidaan asettaa tyhjentämään koko yksikkö
 * Säiliöitä ei saa ylitäyttää
 * Tyhjästä säiliöstä ei voi pumpata pullotukseen
 * Pullotukseen oletetaan mahtuvan aina niin paljon kuin säiliössä on juomaa
 *
 * HUOM! Pumppuja on kahteen eri kohtaan linjastoa. Jos kummankin toteutusta ei ole
 *       järkevää toteuttaa samaan luokkaan, niin tätä luokkaa voi abstrahoida (!absktraktoida).
 *       (ts. tälle luokalle voi tehdä aliluokkia; jos kaikille keisseille tehdään
 *       omat luokkansa niin tästä luokasta kannattaa tehdä abstrakti)
 *       TODO Poista HUOM! ennen esittelyä
 */
public class Pumppu extends Siirtävä {
    private static final int VIRTAAMA = 500;    // litraa/sekunti

    /**
     * Hakumetodi luokan staattiselle ominaisuudelle VIRTAAMA.
     *
     * @.post RETURN = VIRTAAMA
     */
    public int haeVirtaama() {
        return VIRTAAMA;
    }

    public void vastaanota(RaakaAine raakaAine, int määrä, Osio seuraavOsio) throws LiianSuuriMääräException {}
    public void ime() {}
}
