package linjasto.osiot;

import apumäärittelyt.RaakaAine;
import linjasto.komponentit.Komponentti;
import omatVirheilmoitukset.LiianSuuriMääräException;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Linjaston osa, jossa raaka-aine ainoastaan liikkuu. Sitä ei varastoida, eikä
 * sitä muuten prosessoida.
 */
public class Siirtävä extends Osio {

    public Siirtävä(String tunnus, ArrayList<Komponentti> k) {
        super(tunnus, k);
    }


}
