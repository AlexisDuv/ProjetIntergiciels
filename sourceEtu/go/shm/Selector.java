package go.shm;

import go.Direction;
import go.Observer;
import go.Channel;
import java.util.Map;
import java.util.Set;

public class Selector implements go.Selector {

    Map<Channel, Direction> channels;

    public Selector(Map<Channel, Direction> channels) {
        // TODO
        this.channels = channels;

        
    }

    public Channel select() {

        // TODO

        class ObserverIn implements Observer{
            Channel selectedChannel;

            public void setSelectChannel(Channel c){
                selectedChannel = c;
            }

            public void update(){

                try {
                    notify();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
        
        //Ajout observers
        for (Map.Entry<Channel, Direction> entry : this.channels.entrySet()) {
            Channel channel = entry.getKey();
            Direction direction = entry.getValue();
            ObserverIn obs = new ObserverIn();
            obs.setSelectChannel(channel);
            channel.observe(Direction.inverse(direction), obs);
        }
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // if (selectedChannel != null){
         //   return selectedChannel;
        //}


        //
        

        return null;
    }

}
