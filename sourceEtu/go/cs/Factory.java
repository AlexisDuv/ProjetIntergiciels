package go.cs;

import go.Direction;
import java.util.Set;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Factory implements go.Factory {

    Api api;

    public Factory()  {
        try {


           // Obtenir une référence à l'interface du serveur
            api = (Api) Naming.lookup("rmi://localhost:1099/api");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
     
    }

    /** Création ou accès à un canal existant.
     * Côté serveur, le canal est créé au premier appel avec un nom donné ;
     * les appels suivants avec le même nom donneront accès au même canal.
     * @throws RemoteException 
     */
    public <T> go.Channel<T> newChannel(String name){
        try {
            ServerChannel sc = api.newChannel(name);
        return new Channel<T>(name, sc);
        }catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    /** Spécifie quels sont les canaux écoutés et la direction pour chacun. */
    public go.Selector newSelector(Map<go.Channel, Direction> channels) {
        // TODO
        return null;
    }

    /** Spécifie quels sont les canaux écoutés et la même direction pour tous. */
    public go.Selector newSelector(Set<go.Channel> channels, Direction direction) {
        return newSelector(channels
                           .stream() 
                           .collect(Collectors.toMap(Function.identity(), e -> direction)));
    }

}

