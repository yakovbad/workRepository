package Chat.Server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServer {
    static ArrayList<Client> clients = new ArrayList<>();
    static ArrayList<Message> messages = new ArrayList<>();
    static final int LIMITMESSAGE = 6;

    public void run() {
        int portNumber = 7777;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)
        ) {
            System.out.println("Started Chat Server");
            int id = 0;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                String name = "user" + ++id;
                Client client = new Client(name, clientSocket);
                clients.add(client);
                clientThread(client);
            }
        } catch (IOException e) {

        }
    }

    private void clientThread(Client newClient) {
        new Thread(() -> {
            try {
                System.out.println("New client connected: " + newClient.getName());
                BufferedReader in = new BufferedReader(new InputStreamReader(newClient.getSocket().getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(newClient.getSocket().getOutputStream()));
                this.showHistory(out);
                while (true) {
                    String inputLine = in.readLine();
                    if (inputLine.startsWith("[message]")) {
                        Message message = new Message(newClient.getName(),
                                inputLine.split("]")[1]);
                        messages.add(message);
                        this.writeHistory();
                        for (Client client : clients) {
                            try {
                                out = new PrintWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
                                out.println(message.getAuthor() + ": " + message.getText() + "\n");
                                out.flush();
                            } catch (Exception e) {
                                clients.remove(client);
                            }
                        }
                    } else if (inputLine.startsWith("[name]")){
                        newClient.setName(inputLine.split("]")[1]);
                    }
                }
            } catch (Exception e) {
                clients.remove(newClient);
            }
        }).start();
    }

    private void showHistory(PrintWriter out){
        try {
            FileInputStream inputStream = new FileInputStream("history.txt");
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            if (messages.isEmpty())
                messages = (ArrayList<Message>)ois.readObject();
        } catch (Exception e) {
            System.out.println("Error read");
        }
        if (messages.size() > 7)
            for (int i = messages.size() - LIMITMESSAGE; i < messages.size(); i++) {
                Message message = messages.get(i);
                out.println(message.getAuthor() + ": " + message.getText() + "\n");
                out.flush();
            }
        else
            for (Message message : messages) {
                out.println(message.getAuthor() + ": " + message.getText() + "\n");
                out.flush();
            }
    }

    private void writeHistory(){
        try {
            FileOutputStream outputStream = new FileOutputStream("history.txt");
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(messages);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error write");
        }
    }

    public static void main(String[] args) {
        new ChatServer().run();
    }
}
