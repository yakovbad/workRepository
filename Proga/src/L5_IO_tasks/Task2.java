package L5_IO_tasks;


import java.io.*;

public class Task2 {
    public static void main(String[] args) throws Exception {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream("input.txt"));
            outputStream = new FileOutputStream("output.txt");
            int buf;
            System.out.println(inputStream.available());
            buf = inputStream.read();
            for (int i = 1; buf != -1; i++) {
                buf = inputStream.read();
                if (i < 5) continue;
                if (i == 10) inputStream.mark(0);
                outputStream.write(buf);
            }
            inputStream.reset();
            buf = inputStream.read();
            while (buf != -1) {
                outputStream.write(buf);
                buf = inputStream.read();
            }
        } catch (Exception IO) {

        } finally {
            if (inputStream != null) {
                inputStream.close();
                outputStream.close();
            }
        }
    }
}
