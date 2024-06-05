package go.sock;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


public class Naming extends UnicastRemoteObject {

    protected Naming() throws RemoteException {
        super();
        }

    // TODO
    Map<String, InfosConnection> connections = new HashMap<String, InfosConnection>();


    public boolean exists(String name) {
        return connections.containsKey(name);
    }

    public InfosConnection get(String name) {
        return connections.get(name);
    }

    public void add(String name, InfosConnection infos) {
        connections.put(name, infos);
    }

    public static void main(String args[]) throws Exception {
                        //  Création du serveur de noms
                try {
                    LocateRegistry.createRegistry(1099);
                } catch (java.rmi.server.ExportException e) {
                    System.out.println("A registry is already running, proceeding...");
                }
        
                
                Naming naming = new Naming();

               LocateRegistry.getRegistry(1099).bind("dns", naming);
                
        
                // Service prêt : attente d'appels
                System.out.println ("Le systeme est pret.");
    }

}
