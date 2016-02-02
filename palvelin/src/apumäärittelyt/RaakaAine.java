package apumäärittelyt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RaakaAine extends UnicastRemoteObject {
    private String nimi = "Appelsiinit";
    private String juomanNimi = "Jaffa";

    public RaakaAine() throws RemoteException {}

    public String haeJuomanNimi() {
        return juomanNimi;
    }

    public String haeNimi() {
        return nimi;
    }
}
