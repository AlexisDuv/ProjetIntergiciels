package go.shm;

import go.Direction;
import go.Observer;
import go.Channel;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class Selector implements go.Selector {

    Map<Channel, Direction> channels;
    Channel selected;
    private final Semaphore sem = new Semaphore(0);

    public Selector(Map<Channel, Direction> channels) {
        this.channels = channels; 
    }

    public Channel select() {

            class ObserverSelect implements Observer{
                Channel observedChannel;

                public void setObservedChannel(Channel c){
                    observedChannel = c;
                }

                public void update(){
                    try {
                        selected = observedChannel;
                        sem.release();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
            //Ajout observers
            for (Map.Entry<Channel, Direction> entry : this.channels.entrySet()) {
                Channel channel = entry.getKey();
                Direction direction = entry.getValue();
                ObserverSelect obs = new ObserverSelect();
                obs.setObservedChannel(channel);
                channel.observe(Direction.inverse(direction), obs);
            }
            try {
                sem.acquire();
                return this.selected;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
            return null;
    }

}
