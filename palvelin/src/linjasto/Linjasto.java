package linjasto;

import linjasto.komponentit.Komponentti;
import linjasto.komponentit.siirtävät.ruuvikuljetin.Ruuvikuljetin;

import linjasto.komponentit.varastoivat.raakaAineSiilo.RaakaAineSiilo;
import linjasto.osiot.Osio;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Linjasto extends UnicastRemoteObject {
    private ArrayList<Osio> linjastonOsat;

    public Linjasto() throws RemoteException {
        rakennaLinjasto();
    }

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

        
    }
}
