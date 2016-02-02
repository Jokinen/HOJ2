import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

class Palvelin {
    public static void main(String[] args) {
        try {

            // Luo uuden linjaston
            Linjasto linjasto = new Linjasto();

            LocateRegistry.createRegistry(8080);
            Naming.rebind("//localhost:8080/linjasto", linjasto);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
