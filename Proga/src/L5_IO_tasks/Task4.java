package L5_IO_tasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Task4 {
    public static void main(String[] args) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        long timeStartFile;
        long timeStartBuff;

        try {
            fileInputStream = new FileInputStream("input.txt");
            fileOutputStream = new FileOutputStream("output.txt");
            timeStartFile = System.currentTimeMillis();

            int buf = fileInputStream.read();

            while (buf != -1) {
                fileOutputStream.write(buf);
                buf = fileInputStream.read();
            }
            System.out.println("Время работы FileStream: " + (System.currentTimeMillis() - timeStartFile) + " мс");

            fileInputStream = new FileInputStream("input.txt");
            fileOutputStream = new FileOutputStream("output1.txt");
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            timeStartBuff = System.currentTimeMillis();

            byte [] a = new byte[fileInputStream.available()];
            bufferedInputStream.read(a);
            bufferedOutputStream.write(a);
            bufferedOutputStream.close();
            System.out.println("Время работы BufferedInputsStream: " + (System.currentTimeMillis() - timeStartBuff) + " мс");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }
}
