import linjasto.Linjasto;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Palvelin {
    public static void main(String[] args) {
        try {
            Linjasto linjasto = new Linjasto();
            LocateRegistry.createRegistry(8081);
            Naming.rebind("//localhost:8081/linjasto", linjasto);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
