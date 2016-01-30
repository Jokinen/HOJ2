package linjasto;

import linjasto.komponentit.Komponentti;
import linjasto.komponentit.siirtävät.pumppu.Pumppu;
import linjasto.komponentit.siirtävät.ruuvikuljetin.Ruuvikuljetin;

import linjasto.komponentit.varastoivat.juomakeitin.Juomakeitin;
import linjasto.komponentit.varastoivat.kypsytyssäiliö.Kypsytyssäiliö;
import linjasto.komponentit.varastoivat.raakaAineSiilo.RaakaAineSiilo;
import linjasto.osiot.Osio;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Linjasto extends UnicastRemoteObject implements LinjastoInterface {
    private ArrayList<Osio> linjastonOsat;

    public Linjasto() throws RemoteException {
        super();
        rakennaLinjasto();
    }

    /**
     * Metodi jolla linjaston osioista saa haettua tietyn osion, joka mahdollista
     * käskyjen kohdistuksen halutuille osioille.
     *
     * @.post   RETURN = Linjasto
     */

    public void testiMetodi() throws RemoteException {
        System.out.println("Pöö");
    }

    /**
     * Rakennetaan halutunlaisista komponenteista koostuva linjasto.
     */
    private void rakennaLinjasto() {
        // Sillojen täyttöosio
        ArrayList<Komponentti> komponentit1 = new ArrayList<Komponentti>();
        Ruuvikuljetin täyttöKuljetin = new Ruuvikuljetin();
        komponentit1.add(täyttöKuljetin);
        linjasto.osiot.Siirtävä tulo = new linjasto.osiot.Siirtävä("Tulo", komponentit1);

        // Raaka-ainesiilot
        ArrayList<Komponentti> raakaAinesiiloKomponentit = new ArrayList<Komponentti>();

        RaakaAineSiilo siilo1 = new RaakaAineSiilo();
        raakaAinesiiloKomponentit.add(siilo1);

        RaakaAineSiilo siilo2 = new RaakaAineSiilo();
        raakaAinesiiloKomponentit.add(siilo2);

        RaakaAineSiilo siilo3 = new RaakaAineSiilo();
        raakaAinesiiloKomponentit.add(siilo3);

        RaakaAineSiilo siilo4 = new RaakaAineSiilo();
        raakaAinesiiloKomponentit.add(siilo4);

        linjasto.osiot.Varastoiva siilosäilö = new linjasto.osiot.Varastoiva("Siilosäilö", raakaAinesiiloKomponentit);

        // Raaka-ainekuljettimet juomakeittimeen
        ArrayList<Komponentti> raakaAinekuljetinKomponentit = new ArrayList<Komponentti>();

        Ruuvikuljetin raakaAinekuljetin1 = new Ruuvikuljetin();
        raakaAinekuljetinKomponentit.add(raakaAinekuljetin1);

        Ruuvikuljetin raakaAinekuljetin2 = new Ruuvikuljetin();
        raakaAinekuljetinKomponentit.add(raakaAinekuljetin2);

        linjasto.osiot.Siirtävä kuljettimetJuomakeittimeen = new linjasto.osiot.Siirtävä("Kuljettimet juomakeittimeen", raakaAinekuljetinKomponentit);

        // Juomakeittimet
        ArrayList<Komponentti> juomakeitinKomponentit = new ArrayList<Komponentti>();

        Juomakeitin juomakeitin1 = new Juomakeitin();
        juomakeitinKomponentit.add(juomakeitin1);

        Juomakeitin juomakeitin2 = new Juomakeitin();
        juomakeitinKomponentit.add(juomakeitin2);

        linjasto.osiot.Varastoiva juomakeittimet = new linjasto.osiot.Varastoiva("Juomakeittimet", juomakeitinKomponentit);

        // Pumput kypsytyssäiliöihin
        ArrayList<Komponentti> kypsytyssäiliöPumppuKomponentit = new ArrayList<Komponentti>();

        Pumppu kypsytyssäiliöpumppu1 = new Pumppu();
        kypsytyssäiliöPumppuKomponentit.add(kypsytyssäiliöpumppu1);

        Pumppu kypsytyssäiliöpumppu2 = new Pumppu();
        kypsytyssäiliöPumppuKomponentit.add(kypsytyssäiliöpumppu2);

        linjasto.osiot.Siirtävä pumppaus = new linjasto.osiot.Siirtävä("Kypsytyssäiliöille menevät pumput", kypsytyssäiliöPumppuKomponentit);

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

        // Pumput pullotukseen
        ArrayList<Komponentti> pullotusPumppuKomponentit = new ArrayList<Komponentti>();

        Pumppu pullotuspumppu1 = new Pumppu();
        pullotusPumppuKomponentit.add(pullotuspumppu1);

        Pumppu pullotuspumppu2 = new Pumppu();
        pullotusPumppuKomponentit.add(pullotuspumppu2);

        linjasto.osiot.Siirtävä pullotus = new linjasto.osiot.Siirtävä("Pullotuspumput", pullotusPumppuKomponentit);
    }
}
