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

    void käynnistäKomponentti(String osionTunnus, String komponentinTunnus) throws RemoteException;

    void varaaKomponentti(String osionTunnus, String komponentinTunnus) throws RemoteException;

    boolean onkoKomponenttiVarattu(String osionTunnus, String komponentinTunnus) throws RemoteException;

    UUID kukaOnVarannutKomponentin(String osionTunnus, String komponentinTunnus) throws RemoteException;
}