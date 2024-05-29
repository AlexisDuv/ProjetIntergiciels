package go.cs;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Implantation d'un serveur hébergeant des canaux.
 *
 */
public class ServerImpl {

    public static void main(String args[]) throws RemoteException, AlreadyBoundException {
                //  Création du serveur de noms
                try {
                    LocateRegistry.createRegistry(1099);
                } catch (java.rmi.server.ExportException e) {
                    System.out.println("A registry is already running, proceeding...");
                }
        

               ApiImpl api = new ApiImpl();
               LocateRegistry.getRegistry(1099).bind("api", api);
                
        
                // Service prêt : attente d'appels
                System.out.println ("Le systeme est pret.");
    }

}
