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

    void käynnistäKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) throws RemoteException;
    boolean onkoKomponenttiKäynnissä(String osionTunnus, String komponentinTunnus) throws RemoteException;

    //-- Varastoiva spesifit

    boolean varaaKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) throws RemoteException;
    boolean vapautaKomponentti(String osionTunnus, String komponentinTunnus, UUID käyttäjäId) throws RemoteException;
    boolean onkoKomponenttiVarattu(String osionTunnus, String komponentinTunnus) throws RemoteException;
    int haeKomponentinTäyttöaste(String osionTunnus, String komponentinTunnus) throws RemoteException;

    //---- Juomakeitin spesifi

    String kukaOnVarannutKomponentin(String osionTunnus, String komponentinTunnus) throws RemoteException;
}