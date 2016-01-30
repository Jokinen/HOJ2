package linjasto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LinjastoInterface extends Remote {
    void testiMetodi() throws RemoteException;

    void käynnistäKomponentti(String osionTunnus, String komponentinTunnus) throws RemoteException;
}