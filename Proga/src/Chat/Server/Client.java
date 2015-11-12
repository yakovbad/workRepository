package Chat.Server;


import java.net.Socket;

public class Client {
    String name;
    Socket socket;

    public Client(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }


    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
