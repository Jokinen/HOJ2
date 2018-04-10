import apuluokat.RaakaAine;
import komponentit.Komponentti;
import komponentit.siirtävät.Siirtävä;
import komponentit.siirtävät.pumppu.Pumppu;
import komponentit.siirtävät.ruuvikuljetin.Ruuvikuljetin;

import komponentit.varastoivat.Varastoiva;
import komponentit.varastoivat.juomakeitin.Juomakeitin;
import komponentit.varastoivat.kypsytyssäiliö.Kypsytyssäiliö;
import komponentit.varastoivat.raakaAineSiilo.RaakaAineSiilo;
import osiot.Osio;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Linjastorajapinnan toteutus, jonka kanssa käyttöliittymä keskustelee. Joissakin
 * metoideissa on sisällytettynä myös kuvailuja niistä metoideista, joita ne kutsuvat,
 * jotta toteutus olisi selkeämpi pelkkää rajapintaa tarkastelemalla.
 *
 * @.privateClassInvariant   FOREACH(osa in osiot; osa.haeTunnus() unique in osiot)
 *                           FOREACH(käyttäjä in käyttäjät; käyttäjä unique in käyttäjät)
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

    /**
     * @.pre    onSeuraavaOsio(osio)
     */
    private Osio haeSeuraavaOsio(Osio osio) {
        int index = osiot.indexOf(osio) + 1;
        return osiot.get(index);
    }

    /**
     * @.pre    onEdellinenOsio(osio)
     */
    private Osio haeEdellinenOsio(Osio osio) {
        int index = osiot.indexOf(osio) - 1;
        return osiot.get(index);
    }

    /**
     * @.pre    osiot.indexOf(osio) >= 0
     */
    private boolean onSeuraavaOsio(Osio osio) {
        int index = osiot.indexOf(osio) + 1;
        return index < osiot.size();
    }

    /**
     * @.pre    osiot.indexOf(osio) >= 0
     */
    private boolean onEdellinenOsio(Osio osio) {
        int index = osiot.indexOf(osio);
        return index != 0;
    }

    /**
     * Yrittää kirjata käyttäjän sisään ja suorittaa jonkinlaista yksinkertaista
     * varmennusta käyttäjänimeä varten.
     *
     * @.post   if (käyttäjäNimi != null & käyttäjäNimi.length() > 0 & !käyttäjäNimiVarattu(käyttäjäNimi);
     *              RETURN = UUID : RETURN = null)
     */
    public UUID kirjauduSisään(String käyttäjäNimi) throws RemoteException {
        UUID käyttäjäId = null;
        if (käyttäjäNimi != null && käyttäjäNimi.length() > 0 && !käyttäjäNimiVarattu(käyttäjäNimi)) {
            käyttäjäId = UUID.randomUUID();
            käyttäjät.put(käyttäjäId, käyttäjäNimi);
            System.out.println("Käyttäjä: " + käyttäjäNimi + " (" + käyttäjäId + ") kirjattu sisään");
        } else {
            System.err.println("Käyttäjä yritetiin kirjata sisääsn, mutta yritys epäonnistui.");
        }
        return käyttäjäId;
    }

    /**
     * Yrittää kirjata käyttäjän ulos. Kun kysytyllä käyttäId:llä löytyy käyttäjä
     * järjestelmästä, niin uloskirjaus onnistuu.
     *
     * @.post   RETURN = äyttäjät.containsKey(käyttäjäId)
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

    /**
     * Jos käyttäjänimi löytyy jo järjestelmästä, niin palautta tosi.
     *
     * @.post RETURN = haeKäyttäjät().containsValue(käyttäjäNimi)
     */
    public boolean käyttäjäNimiVarattu(String käyttäjäNimi) {
        return käyttäjät.containsValue(käyttäjäNimi);
    }

    /**
     * Luokan yksityinen metodi, joka etsii osion haetulla tunnuksella.
     *
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus() == tunnus))
     * @.post   RETURN = FOREACH(osio in osiot;
     *                          osio.haeTunnus() == tunnus)
     */
    private Osio haeOsio(String tunnus) {
        Osio palautettavaOsio = null;
        for (Osio osio : osiot) {
            if (osio.haeTunnus().equals(tunnus))
                palautettavaOsio = osio;
        }
        return palautettavaOsio;
    }

    /**
     * Luokan yksityinen metodi, joka etsii komponentin haetulla tunnuksella.
     *
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus))
     */
    private Komponentti haeKomponentti(String osionTunnus, String komponentinTunnus) {
        return haeOsio(osionTunnus)
                .haeKomponentti(komponentinTunnus);
    }

    /**
     * Käynnistää linjaston tulo. Tulo vastaa huonosti ohjelman muuta nimeämistä,
     * sillä siitä ei käy selväksi, että onko "tulo" komponentti vai osio.
     * Metodia olisi voinut yksinkertaistaa esim. siten, että sille ei tarvitse
     * syöttää laisinkaan tunnuksia osiolle tai komponentille.
     *
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus))
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Siirtävä
     */
    public void käynnistäTulo(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) {
        käynnistäSiirtäväKomponentti(osionTunnus, komponentinTunnus, käyttäjäId, 40000);
    }

    /**
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus))
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Siirtävä
     */
    public void käynnistäSiirtäväKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) {
        try {
            RaakaAine raakaAine = new RaakaAine();
            Osio osio = haeOsio(osionTunnus);
            osiot.Varastoiva edellinenOsio = (osiot.Varastoiva) haeEdellinenOsio(osio);
            käynnistäSiirtäväKomponentti(osionTunnus,
                                        komponentinTunnus,
                                        käyttäjäId, edellinenOsio.haeSiirettäväMäärä(komponentinTunnus,
                                        käyttäjäId,
                                        raakaAine));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus))
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Siirtävä
     */
    public void käynnistäSiirtäväKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId, int määrä) {
        try {
            RaakaAine raakaAine = new RaakaAine();
            Osio osio = haeOsio(osionTunnus);
            Komponentti komponentti = osio.haeKomponentti(komponentinTunnus);
            Siirtävä komp = (Siirtävä) komponentti;
            if (onSeuraavaOsio(osio) && onEdellinenOsio(osio)) {
                komp.käynnistä(haeEdellinenOsio(osio), haeSeuraavaOsio(osio), käyttäjäId, määrä, raakaAine);
            } else if (onSeuraavaOsio(osio)) {
                komp.käynnistä(null, haeSeuraavaOsio(osio), käyttäjäId, määrä, raakaAine);
            } else if (onEdellinenOsio(osio)) {
                komp.käynnistä(haeEdellinenOsio(osio), null, käyttäjäId, määrä, raakaAine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus))
     */
    public boolean onkoKomponenttiKäynnissä(String osionTunnus, String komponentinTunnus) {
        return haeKomponentti(osionTunnus, komponentinTunnus)
                .onkoKäynnissä();
    }

    /**
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus)) &
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Varastoiva
     */
    public void käynnistäVarastoivaKomponentti(String osionTunnus, String komponentinTunnus) {
        haeKomponentti(osionTunnus, komponentinTunnus).käynnistä();
    }

    /**
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus)) &
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Varastoiva
     */
    public int haeKomponentinTäyttöaste(String osionTunnus, String komponentinTunnus) {
        Varastoiva komponentti = (Varastoiva) haeKomponentti(osionTunnus, komponentinTunnus);
        return komponentti.haeTäyttöaste();
    }

    /**
     * Pyrkii varaamaan komponentin. Jos komponentti on jo varattu, niin operaatio epäonnistuu.
     *
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus)) &
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Varastoiva
     * @.post   haeKomponentti(osionTunnus, komponentinTunnus).varaa(käyttäjäId)
     */
    public boolean varaaKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) {
        Varastoiva komponentti = (Varastoiva) haeKomponentti(osionTunnus, komponentinTunnus);
        return komponentti.varaa(käyttäjäId);
    }

    /**
     * Vapauttaa komponentin, mikäli annettu käyttäjäId on komponentin varaaja.
     *
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus)) &
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Varastoiva
     * @.post   komponentti.vapauta(käyttäjäId)
     */
    public boolean vapautaKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) {
        Varastoiva komponentti = (Varastoiva) haeKomponentti(osionTunnus, komponentinTunnus);
        return komponentti.vapauta(käyttäjäId);
    }

    /**
     * Kertoo, että onko komponentti tällä hetkellä varattu.
     *
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus)) &
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Varastoiva
     * @.post   if(haeKOmponentti(osionTunnus, komponentinTunnus).haeVarattu(); RETURN = true : RETURN = false)
     */
    public boolean onkoKomponenttiVarattu(String osionTunnus, String komponentinTunnus) {
        Varastoiva komponentti = (Varastoiva) haeKomponentti(osionTunnus, komponentinTunnus);
        return komponentti.haeVarattu();
    }

    /**
     * Palauttaa sen käyttäjän NIMEN, joka on varannut komponentin.
     *
     * @.pre    EXISTS(FOREACH(osio in osiot; osio.haeTunnus().equals(osionTunnus))) &
     *          EXISTS(FOREACH(osio in osiot; FOREACH(komponentti in osio; komponentti.haeTunnus().equals(komponentinTunnus)) &
     *          onkoKomponenttiVarattu(osionTunnus, komponentinTunnus) = true &
     *          typeof haeKomponentti(osionTunnus, komponentinTunnus) = Juomakeitin
     * @.post   käyttäjät.get(RETURN = haeKOmponentti(osionTunnus, komponentinTunnus).haeKäyttäjä())
     */
    public String kukaOnVarannutKomponentin(String osionTunnus, String komponentinTunnus) {
        Varastoiva komponentti = (Varastoiva) haeKomponentti(osionTunnus, komponentinTunnus);
        return käyttäjät.get(komponentti.haeKäyttäjä());
    }

    /**
     * Rakennetaan halutunlaisista komponenteista koostuva linjasto.
     */
    private void rakennaLinjasto() {
        // Sillojen täyttöosio
        ArrayList<Komponentti> komponentit1 = new ArrayList<>();
        Ruuvikuljetin täyttöKuljetin = new Ruuvikuljetin("TäytönRuuvikuljetin");
        komponentit1.add(täyttöKuljetin);
        osiot.Siirtävä tulo = new osiot.Siirtävä("Tulo", komponentit1);
        osiot.add(tulo);

        // Raaka-ainesiilot
        ArrayList<Komponentti> raakaAinesiiloKomponentit = new ArrayList<>();

        RaakaAineSiilo siilo1 = new RaakaAineSiilo("Siilo1");
        raakaAinesiiloKomponentit.add(siilo1);

        RaakaAineSiilo siilo2 = new RaakaAineSiilo("Siilo2");
        raakaAinesiiloKomponentit.add(siilo2);

        RaakaAineSiilo siilo3 = new RaakaAineSiilo("Siilo3");
        raakaAinesiiloKomponentit.add(siilo3);

        RaakaAineSiilo siilo4 = new RaakaAineSiilo("Siilo4");
        raakaAinesiiloKomponentit.add(siilo4);

        osiot.Varastoiva siilosäiliö = new osiot.Varastoiva("Siilo", raakaAinesiiloKomponentit);
        osiot.add(siilosäiliö);

        // Raaka-ainesiilojen "käynnistys"
        siilo1.käynnistä();
        siilo2.käynnistä();
        siilo3.käynnistä();
        siilo4.käynnistä();

        // Raaka-ainekuljettimet juomakeittimeen
        ArrayList<Komponentti> raakaAinekuljetinKomponentit = new ArrayList<>();

        Ruuvikuljetin raakaAinekuljetin1 = new Ruuvikuljetin("KeittimenTäytönRuuvikuljetin1");
        raakaAinekuljetinKomponentit.add(raakaAinekuljetin1);

        Ruuvikuljetin raakaAinekuljetin2 = new Ruuvikuljetin("KeittimenTäytönRuuvikuljetin2");
        raakaAinekuljetinKomponentit.add(raakaAinekuljetin2);

        osiot.Siirtävä kuljettimetJuomakeittimeen = new osiot.Siirtävä("KeittimenTäytönRuuvikuljetin", raakaAinekuljetinKomponentit);
        osiot.add(kuljettimetJuomakeittimeen);

        // Juomakeittimet
        ArrayList<Komponentti> juomakeitinKomponentit = new ArrayList<>();

        Juomakeitin juomakeitin1 = new Juomakeitin("Juomakeitin1");
        juomakeitinKomponentit.add(juomakeitin1);

        Juomakeitin juomakeitin2 = new Juomakeitin("Juomakeitin2");
        juomakeitinKomponentit.add(juomakeitin2);

        Juomakeitin juomakeitin3 = new Juomakeitin("Juomakeitin3");
        juomakeitinKomponentit.add(juomakeitin3);

        osiot.Varastoiva juomakeittimet = new osiot.Varastoiva("Juomakeittimet", juomakeitinKomponentit);
        osiot.add(juomakeittimet);

        // Pumput kypsytyssäiliöihin
        ArrayList<Komponentti> kypsytyssäiliöPumppuKomponentit = new ArrayList<>();

        Pumppu kypsytyssäiliöpumppu1 = new Pumppu("PumppuKypsytykseen1");
        kypsytyssäiliöPumppuKomponentit.add(kypsytyssäiliöpumppu1);

        Pumppu kypsytyssäiliöpumppu2 = new Pumppu("PumppuKypsytykseen2");
        kypsytyssäiliöPumppuKomponentit.add(kypsytyssäiliöpumppu2);

        osiot.Siirtävä pumppaus = new osiot.Siirtävä("PumputKypsytykseen", kypsytyssäiliöPumppuKomponentit);
        osiot.add(pumppaus);

        // Kypsytyssäiliöt
        ArrayList<Komponentti> kypsytyssäiliöKomponentit = new ArrayList<>();

        Kypsytyssäiliö kypsytyssäiliö1 = new Kypsytyssäiliö("Kypsytyssäiliö1");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö1);

        Kypsytyssäiliö kypsytyssäiliö2 = new Kypsytyssäiliö("Kypsytyssäiliö2");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö2);

        Kypsytyssäiliö kypsytyssäiliö3 = new Kypsytyssäiliö("Kypsytyssäiliö3");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö3);

        Kypsytyssäiliö kypsytyssäiliö4 = new Kypsytyssäiliö("Kypsytyssäiliö4");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö4);

        Kypsytyssäiliö kypsytyssäiliö5 = new Kypsytyssäiliö("Kypsytyssäiliö5");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö5);

        Kypsytyssäiliö kypsytyssäiliö6 = new Kypsytyssäiliö("Kypsytyssäiliö6");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö6);

        Kypsytyssäiliö kypsytyssäiliö7 = new Kypsytyssäiliö("Kypsytyssäiliö7");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö7);

        Kypsytyssäiliö kypsytyssäiliö8 = new Kypsytyssäiliö("Kypsytyssäiliö8");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö8);

        Kypsytyssäiliö kypsytyssäiliö9 = new Kypsytyssäiliö("Kypsytyssäiliö9");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö9);

        Kypsytyssäiliö kypsytyssäiliö10 = new Kypsytyssäiliö("Kypsytyssäiliö10");
        kypsytyssäiliöKomponentit.add(kypsytyssäiliö10);

        osiot.Varastoiva kypsytys = new osiot.Varastoiva("Kypsytyssäiliöt", kypsytyssäiliöKomponentit);
        osiot.add(kypsytys);

        // Kypsytyssäiliöiden "käynnistys"
        kypsytyssäiliö1.käynnistä();
        kypsytyssäiliö2.käynnistä();
        kypsytyssäiliö3.käynnistä();
        kypsytyssäiliö4.käynnistä();
        kypsytyssäiliö5.käynnistä();
        kypsytyssäiliö6.käynnistä();
        kypsytyssäiliö7.käynnistä();
        kypsytyssäiliö8.käynnistä();
        kypsytyssäiliö9.käynnistä();
        kypsytyssäiliö10.käynnistä();

        // Pumput pullotukseen
        ArrayList<Komponentti> pullotusPumppuKomponentit = new ArrayList<>();

        Pumppu pullotuspumppu1 = new Pumppu("PumppuPullotukseen1");
        pullotusPumppuKomponentit.add(pullotuspumppu1);

        Pumppu pullotuspumppu2 = new Pumppu("PumppuPullotukseen2");
        pullotusPumppuKomponentit.add(pullotuspumppu2);

        osiot.Siirtävä pullotus = new osiot.Siirtävä("PumputPullotukseen", pullotusPumppuKomponentit);
        osiot.add(pullotus);
    }
}
