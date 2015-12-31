package linjastonKomponentit.Ruuvikuljetin;

import java.util.ArrayList;

import linjastonKomponentit.LinjastonKomponentti;
import linjastonKomponentit.RaakaAineSiilo;
import omatVirheilmoitukset.Virheet.*;

/**
 * Ruuvikuljetin raaka-ainesiilojen täyttöön.
 * Siirtää raaka-ainetta 200 kiloa sekunnissa
 * Kuljetin ei voi siirtää enempää kuin vastaanottavaan yksikköön mahtuu
 * Raaka-ainetta tulee lisää tehtaaseen yhdessä erässä 40 tonnia
 * Siilo tai keitin ei saa ylitäyttyä
 */
public class Ruuvikuljetin extends LinjastonKomponentti {
    private final int SIIRTORAJA = 200;                 // per sekunti
    private final int ERÄNKOKO = 40000;                 // kiloa

    // konstruktori
    public Ruuvikuljetin() {}

}

