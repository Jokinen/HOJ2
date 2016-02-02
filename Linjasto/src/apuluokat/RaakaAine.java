package apuluokat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RaakaAine extends UnicastRemoteObject {
    private final String nimi;
    private final String juomanNimi;

    public RaakaAine() throws RemoteException {
        nimi = "Appelsiinit";
        juomanNimi = "Jaffa";
    }

    public String haeJuomanNimi() {
        return juomanNimi;
    }

    public String haeNimi() {
        return nimi;
    }
}
