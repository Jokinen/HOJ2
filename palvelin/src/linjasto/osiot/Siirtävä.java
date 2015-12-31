package linjasto.osiot;

import java.util.ArrayList;

/**
 * Linjaston osa, jossa raaka-aine ainoastaan liikkuu. Sitä ei varastoida, eikä
 * sitä muuten prosessoida.
 */
public class Siirtävä extends Osio {
    /*
    ArrayListin kohdeluokkaan viitataan tässä pakettipolulla, sillä luokka
    samanniminen kuin tämä luokka. Pakettien sisäisen selkeän nimeämisen
    säilyttämiseksi kumpaakaan luokkaa ei nimetty uudelleen.
    */
    private ArrayList<linjasto.komponentit.siirtävät.Siirtävä> komponentit;

    public Siirtävä(String t, String e, String s, ArrayList<linjasto.komponentit.siirtävät.Siirtävä> k) {
        super(t, e, s);
        komponentit = k;
    }

    /**
     * @see Osio (String, boolean, String)
     */
    public Siirtävä(String t, boolean a, String s, ArrayList<linjasto.komponentit.siirtävät.Siirtävä> k) {
        super(t, a, s);
        komponentit = k;
    }

    /**
     * @see Osio (String, String, boolean)
     */
    public Siirtävä(String t, String e, boolean a, ArrayList<linjasto.komponentit.siirtävät.Siirtävä> k) {
        super(t, e, a);
        komponentit = k;
    }

}
