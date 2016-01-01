package linjasto.komponentit;

import apumäärittelyt.RaakaAine;
import linjasto.osiot.Osio;
import omatVirheilmoitukset.LiianSuuriMääräException;

public abstract class Komponentti {
    public abstract void vastaanota(RaakaAine raakaAine, int määrä, Osio seuraavOsio) throws LiianSuuriMääräException;
}
