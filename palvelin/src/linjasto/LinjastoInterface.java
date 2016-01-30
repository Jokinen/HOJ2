package linjasto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LinjastoInterface extends Remote {
    public void testiMetodi() throws RemoteException;
}
