package L5_IO_tasks;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.SequenceInputStream;

public class Task3 {
    public static void main(String[] args) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;

        try {
            fileInputStream = new FileInputStream("input.txt");

            FileInputStream fileInputStream1 = new FileInputStream("input1.txt");
            fileOutputStream = new FileOutputStream("output.txt");

            SequenceInputStream sequenceInputStream = new SequenceInputStream(fileInputStream, fileInputStream1);

            int buf = sequenceInputStream.read();

            while (buf != -1) {
                fileOutputStream.write(buf);
                buf = sequenceInputStream.read();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
