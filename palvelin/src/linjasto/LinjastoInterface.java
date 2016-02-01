package linjasto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface LinjastoInterface extends Remote {
    void testiMetodi() throws RemoteException;


    // Käyttäjän hallinta

    UUID kirjauduSisään(String käyttäjäNimi) throws RemoteException;
    boolean kirjauduUlos(UUID käyttäjäId) throws RemoteException;
    boolean käyttäjäNimiVarattu(String käyttäjäNimi) throws RemoteException;


    // Komponenttien hallinta

    boolean onkoKomponenttiKäynnissä(String osionTunnus, String komponentinTunnus) throws RemoteException;

    //-- Tulo

    void käynnistäTulo(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) throws RemoteException;

    //-- Siirtävät

    void käynnistäSiirtäväKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) throws RemoteException;
    void käynnistäSiirtäväKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId, int määrä) throws RemoteException;


    //-- Varastoiva spesifit

    void käynnistäVarastoivaKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) throws RemoteException;
    boolean varaaKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) throws RemoteException;
    boolean vapautaKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) throws RemoteException;
    boolean onkoKomponenttiVarattu(String osionTunnus, String komponentinTunnus) throws RemoteException;
    int haeKomponentinTäyttöaste(String osionTunnus, String komponentinTunnus) throws RemoteException;

    //---- Juomakeitin spesifi

    String kukaOnVarannutKomponentin(String osionTunnus, String komponentinTunnus) throws RemoteException;
}