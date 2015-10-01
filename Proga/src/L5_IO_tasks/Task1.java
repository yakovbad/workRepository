package L5_IO_tasks;


import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Task1 {


    public static void main(String[] args) {

        FileInputStream inputStream;
        FileOutputStream outputStream;
        try {
            inputStream = new FileInputStream("input.txt");
            outputStream = new FileOutputStream("output.txt");

            int buf;
            buf = inputStream.read();

            while (buf != -1) {
                outputStream.write(buf);
                buf = inputStream.read();
            }

        } catch (Exception IO) {

        }
    }
}
