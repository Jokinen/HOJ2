package linjasto;

import linjasto.komponentit.Komponentti;
import linjasto.komponentit.siirtävät.pumppu.Pumppu;
import linjasto.komponentit.siirtävät.ruuvikuljetin.Ruuvikuljetin;

import linjasto.komponentit.varastoivat.Varastoiva;
import linjasto.komponentit.varastoivat.juomakeitin.Juomakeitin;
import linjasto.komponentit.varastoivat.kypsytyssäiliö.Kypsytyssäiliö;
import linjasto.komponentit.varastoivat.raakaAineSiilo.RaakaAineSiilo;
import linjasto.osiot.Osio;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * @.publicClassInvariant   FOREACH(osa in osiot;
 *                                  osa.haeTunnus() unique in osiot)
 *                          FOREACH(käyttäjä in käyttäjät;
 *                                  käyttäjä unique in käyttäjät)
 */
public class Linjasto extends UnicastRemoteObject implements LinjastoInterface {
    private ArrayList<Osio> osiot;
    private HashMap<UUID, String> käyttäjät;

    public Linjasto() throws RemoteException {
        super();
        osiot = new ArrayList<>();
        käyttäjät = new HashMap<>();
        rakennaLinjasto();
    }

    public void testiMetodi() throws RemoteException {
        System.out.println("Pöö");
    }

    public UUID kirjauduSisään(String käyttäjäNimi) throws RemoteException {
        UUID käyttäjäId = null;
        if (käyttäjäNimi != null && käyttäjäNimi.length() > 0 && !käyttäjät.containsValue(käyttäjäNimi)) {
            käyttäjäId = UUID.randomUUID();
            käyttäjät.put(käyttäjäId, käyttäjäNimi);
            System.out.println("Käyttäjä: " + käyttäjäNimi + " (" + käyttäjäId + ") kirjattu sisään");
        } else {
            System.err.println("Käyttäjä yritetiin kirjata sisääsn, mutta yritys epäonnistui.");
        }
        return käyttäjäId;
    }

    /**
     *
     * @.post   RETURN = IF(käyttäjät.containsKey(käyttäjäId); true)
     *                   ELSE(false)
     */
    public boolean kirjauduUlos(UUID käyttäjäId) throws RemoteException {
        boolean bol = false;
        if (käyttäjät.containsKey(käyttäjäId)) {
            bol = true;
            System.out.println("Käyttäjä: " + käyttäjät.get(käyttäjäId) + " (" + käyttäjäId + ") kirjattu ulos");
            käyttäjät.remove(käyttäjäId);
        }
        return bol;
    }

    public boolean käyttäjäNimiVarattu(String käyttäjäNimi) {
        return käyttäjät.containsValue(käyttäjäNimi);
    }

    /**
     * Metodi jolla linjaston osioista saa haettua tietyn osion, joka mahdollista
     * käskyjen kohdistuksen halutuille osioille.
     *
     * @.pre    EXISTS(
     *              FOREACH(osio in osiot; osio.haeTunnus() == tunnus)
     *          )
     * @.post   RETURN = FOREACH(osio in osiot;
     *                          osio.haeTunnus() == tunnus)
     */
    public Osio haeOsio(String tunnus) {
        Osio palautettavaOsio = null;
        for (Osio osio : osiot) {
            if (osio.haeTunnus().equals(tunnus))
                palautettavaOsio = osio;
        }
        return palautettavaOsio;
    }

    public Komponentti haeKomponentti(String osionTunnus, String komponentinTunnus) {
        return haeOsio(osionTunnus)
                .haeKomponentti(komponentinTunnus);
    }

    public void käynnistäKomponentti(String osionTunnnus, String komponentinTunnus) {
        haeKomponentti(osionTunnnus, komponentinTunnus)
                .käynnistä();
    }

    /**
     * @.pre    typeof haeKomponentti(osionTunnus, komponentinTunnus) = Varastoiva
     */
    public boolean onkoKomponenttiVarattu(String osionTunnus, String komponentinTunnus) {
        Varastoiva komponentti = (Varastoiva) haeKomponentti(osionTunnus, komponentinTunnus);
        return komponentti.haeVarattu();
    }

    /**
     * @.pre    onkoKomponenttiVarattu(osionTunnus, komponentinTunnus) = true
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Juomakeitin
     */
    public UUID kukaOnVarannutKomponentin(String osionTunnus, String komponentinTunnus) {
        Varastoiva komponentti = (Varastoiva) haeKomponentti(osionTunnus, komponentinTunnus);
        return komponentti.haeKäyttäjä();
    }

    /**
     * Rakennetaan halutunlaisista komponenteista koostuva linjasto.
     *
     * Osiot:
     * 1. Tulo
     * 2. Siilot
     * 3. Kuljettimet juomakeittimeen
     * 4. Juomakeittimet
     * 5. Kypsytyssäiliöille menevät pumput
     * 6. Kypsytyssäiliöt
     * 7. Pullotuspumput
     */
    private void rakennaLinjasto() {
        // Sillojen täyttöosio
        ArrayList<Komponentti> komponentit1 = new ArrayList<Komponentti>();
        Ruuvikuljetin täyttöKuljetin = new Ruuvikuljetin("Täytön_ruuvikuljetin");
        komponentit1.add(täyttöKuljetin);
        linjasto.osiot.Siirtävä tulo = new linjasto.osiot.Siirtävä("Tulo", komponentit1);
        osiot.add(tulo);

        // Raaka-ainesiilot
        ArrayList<Komponentti> raakaAinesiiloKomponentit = new ArrayList<Komponentti>();

        RaakaAineSiilo siilo1 = new RaakaAineSiilo("Siilo1");
        raakaAinesiiloKomponentit.add(siilo1);

        RaakaAineSiilo siilo2 = new RaakaAineSiilo("Siilo2");
        raakaAinesiiloKomponentit.add(siilo2);

        RaakaAineSiilo siilo3 = new RaakaAineSiilo("Siilo3");
        raakaAinesiiloKomponentit.add(siilo3);

        RaakaAineSiilo siilo4 = new RaakaAineSiilo("Siilo4");
        raakaAinesiiloKomponentit.add(siilo4);

        linjasto.osiot.Varastoiva siilosäiliö = new linjasto.osiot.Varastoiva("Siilot", raakaAinesiiloKomponentit);
        osiot.add(siilosäiliö);

        // Raaka-ainekuljettimet juomakeittimeen
        /*ArrayList<Komponentti> raakaAinekuljetinKomponentit = new ArrayList<Komponentti>();

        Ruuvikuljetin raakaAinekuljetin1 = new Ruuvikuljetin();
        raakaAinekuljetinKomponentit.add(raakaAinekuljetin1);

        Ruuvikuljetin raakaAinekuljetin2 = new Ruuvikuljetin();
        raakaAinekuljetinKomponentit.add(raakaAinekuljetin2);

        linjasto.osiot.Siirtävä kuljettimetJuomakeittimeen = new linjasto.osiot.Siirtävä("Kuljettimet juomakeittimeen", raakaAinekuljetinKomponentit);
        osiot.add(kuljettimetJuomakeittimeen);

        // Juomakeittimet
        ArrayList<Komponentti> juomakeitinKomponentit = new ArrayList<Komponentti>();

        Juomakeitin juomakeitin1 = new Juomakeitin();
        juomakeitinKomponentit.add(juomakeitin1);

        Juomakeitin juomakeitin2 = new Juomakeitin();
        juomakeitinKomponentit.add(juomakeitin2);

        linjasto.osiot.Varastoiva juomakeittimet = new linjasto.osiot.Varastoiva("Juomakeittimet", juomakeitinKomponentit);
        osiot.add(juomakeittimet);

        // Pumput kypsytyssäiliöihin
        ArrayList<Komponentti> kypsytyssäiliöPumppuKomponentit = new ArrayList<Komponentti>();

        Pumppu kypsytyssäiliöpumppu1 = new Pumppu();
        kypsytyssäiliöPumppuKomponentit.add(kypsytyssäiliöpumppu1);

        Pumppu kypsytyssäiliöpumppu2 = new Pumppu();
        kypsytyssäiliöPumppuKomponentit.add(kypsytyssäiliöpumppu2);

        linjasto.osiot.Siirtävä pumppaus = new linjasto.osiot.Siirtävä("Kypsytyssäiliöille menevät pumput", kypsytyssäiliöPumppuKomponentit);
        osiot.add(pumppaus);

        // Kypsytyssäiliöt
        ArrayList<Komponentti> kypsytyssäiliöKomponentit = new ArrayList<Komponentti>();

        Kypsytyssäiliö kypsytyssäiliö1 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö1);

        Kypsytyssäiliö kypsytyssäiliö2 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö2);

        Kypsytyssäiliö kypsytyssäiliö3 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö3);

        Kypsytyssäiliö kypsytyssäiliö4 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö4);

        Kypsytyssäiliö kypsytyssäiliö5 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö5);

        Kypsytyssäiliö kypsytyssäiliö6 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö6);

        Kypsytyssäiliö kypsytyssäiliö7 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö7);

        Kypsytyssäiliö kypsytyssäiliö8 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö8);

        Kypsytyssäiliö kypsytyssäiliö9 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö9);

        Kypsytyssäiliö kypsytyssäiliö10 = new Kypsytyssäiliö();
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö10);

        linjasto.osiot.Varastoiva kypsytys = new linjasto.osiot.Varastoiva("Kypsytyssäiliöt", kypsytyssäiliöKomponentit);
        osiot.add(kypsytys);

        // Pumput pullotukseen
        ArrayList<Komponentti> pullotusPumppuKomponentit = new ArrayList<Komponentti>();

        Pumppu pullotuspumppu1 = new Pumppu();
        pullotusPumppuKomponentit.add(pullotuspumppu1);

        Pumppu pullotuspumppu2 = new Pumppu();
        pullotusPumppuKomponentit.add(pullotuspumppu2);

        linjasto.osiot.Siirtävä pullotus = new linjasto.osiot.Siirtävä("Pullotuspumput", pullotusPumppuKomponentit);
        osiot.add(pullotus);*/
    }
}
