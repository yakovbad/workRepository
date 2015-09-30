package L5_IO_tasks;


import java.io.*;

public class Task3 {
    public static void main(String[] args) throws Exception{
        InputStream fileInputStream = null;
        OutputStream fileOutputStream = null;
        InputStream fileInputStream1 = null;
        try {
            fileInputStream = new FileInputStream("input.txt");

            fileInputStream1 = new FileInputStream("input1.txt");
            fileOutputStream = new FileOutputStream("output.txt");

            SequenceInputStream sequenceInputStream = new SequenceInputStream(fileInputStream, fileInputStream1);

            int buf = sequenceInputStream.read();

            while (buf != -1) {
                fileOutputStream.write(buf);
                buf = sequenceInputStream.read();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream != null){
                fileInputStream.close();
                fileOutputStream.close();
                fileInputStream1.close();
            }
        }
    }
}
