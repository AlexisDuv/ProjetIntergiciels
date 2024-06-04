package go.sock;

public class Data<T> {

    public Data(String command, T value) {
        this.command = command;
        this.value = value;
    }

    String command;
    T value;

}
