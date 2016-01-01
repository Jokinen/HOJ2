package linjasto.komponentit.siirtävät;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;

/**
 * Komponentti, joka siirtää.
 */
public abstract class Siirtävä extends Komponentti {

    public void vastaanota(RaakaAine raakaAine, int määrä) {}

    public void siirrä(RaakaAine raakaAine, int määrä) {}
}
