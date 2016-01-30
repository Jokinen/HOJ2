package linjasto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface LinjastoInterface extends Remote {
    void testiMetodi() throws RemoteException;

    UUID kirjauduSisään(String käyttäjäNimi) throws RemoteException;
    boolean kirjauduUlos(UUID käyttäjäId) throws RemoteException;
    boolean käyttäjäNimiVarattu(String käyttäjäNimi) throws RemoteException;

    void käynnistäKomponentti(String osionTunnus, String komponentinTunnus) throws RemoteException;
}
