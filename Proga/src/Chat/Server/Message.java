package Chat.Server;


import java.io.Serializable;

public class Message implements Serializable{
    private static final long serialVersionUID = 71285910512L;
    private String author;
    private String text;

    public Message(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
