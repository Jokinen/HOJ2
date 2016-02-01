package linjasto.komponentit.siirtävät.ruuvikuljetin;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.siirtävät.Siirtävä;
import linjasto.osiot.Varastoiva;

import java.util.concurrent.TimeUnit;

/**
 * Ruuvikuljetin raaka-ainesiilojen täyttöön.
 * Siirtää raaka-ainetta 200 kiloa sekunnissa
 * Täyttö ei voi siirtää enempää kuin vastaanottavaan yksikköön mahtuu
 * Raaka-ainetta tulee lisää tehtaaseen yhdessä erässä 40 tonnia
 * Siilo tai keitin ei saa ylitäyttyä
 */
public class Ruuvikuljetin extends Siirtävä {

    public Ruuvikuljetin(String tunnus) {
        super(tunnus, 200);
    }
}

