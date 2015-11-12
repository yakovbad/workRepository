package Chat.Client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class ChatClient extends JFrame {

    private JButton sendButton;
    private JTextField inputText;
    private JTextArea chat;

    private PrintWriter out;
    private Socket socket;

    /**
     * Send button handler
     */
    private final ActionListener sendAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            out.write("[message]" + inputText.getText() + "\n");
            out.flush();
            inputText.setText("");
        }
    };

    public void init(String name) {

        initComponents();

        connect(name);

        startServerListener();
    }

    private void startServerListener() {
        new Thread(() -> {
            try {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String messageFromServer = "";
                    while ((messageFromServer = in.readLine()) != null) {
                        addMessageFromServer(messageFromServer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Adds String message to GUI component
     * @param message
     */
    private void addMessageFromServer(String message) {
        chat.append(message + "\n");
    }

    private void initComponents() {
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(320, 480));
        container.setLayout(null);

        setContentPane(container);


        JLabel inputLabel = new JLabel("Введите текст:");
        inputLabel.setSize(150, 20);
        inputLabel.setLocation(5, 430);
        container.add(inputLabel);

        sendButton = new JButton("Send");
        sendButton.setSize(70, 20);
        sendButton.setLocation(245, 450);
        container.add(sendButton);

        sendButton.addActionListener(sendAction);

        inputText = new JTextField();
        inputText.setSize(235, 20);
        inputText.setBorder(BorderFactory.createLineBorder(Color.black));
        inputText.setLocation(5, 450);
        inputText.addActionListener(sendAction);
        container.add(inputText);

        chat = new JTextArea();
        chat.setSize(320, 400);
        chat.setEditable(false);
        chat.setFont(new Font("Arial", Font.BOLD, 12));
        chat.setBorder(BorderFactory.createEtchedBorder(Color.green, Color.black));

        JScrollPane scroll = new JScrollPane(chat);
        scroll.setSize(300, 420);
        scroll.setLocation(5, 5);
        container.add(scroll);

        pack();
        setLocation(200, 150);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void connect(String name) {
        String hostName = "localhost";
        int portNumber = 7777;
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write("[name]" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        ChatClient cc = new ChatClient();
        cc.init(args[0]+"\n");
    }
}
