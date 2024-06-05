package go.sock;

import go.Channel;
import go.Direction;
import go.Selector;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Factory implements go.Factory {

    Dns dns;
    Random random = new Random();
    int min = 1200;
    int max = 2000;

    public Factory()  {
        try {
           // Obtenir une référence à l'interface du serveur
            dns =  (Dns) java.rmi.Naming.lookup("rmi://localhost:1099/dns");

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> Channel<T> newChannel(String name) {
        try {
            System.out.println( dns.exists(name));
            if (dns.exists(name)){
                InfosConnection infosMaster = dns.get(name);
                ChannelSlave<T> cs = new ChannelSlave<>(name,infosMaster.address,infosMaster.port);
                return cs;
            }
            else{
                
                dns.add(name, new InfosConnection(InetAddress.getLocalHost().toString(), 1234));

                ChannelMaster<T> cm = new ChannelMaster<>(name);
            
                return cm;
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Selector newSelector(Map<Channel, Direction> channels) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newSelector'");
    }

    @Override
    public Selector newSelector(Set<Channel> channels, Direction direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newSelector'");
    }

}
