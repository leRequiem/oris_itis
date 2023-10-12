import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            Socket clientSocket = new Socket ("127.0.0.1",33333);

            System.out.println(clientSocket.getLocalAddress());
            System.out.println(clientSocket.getLocalPort());

            OutputStream os = clientSocket.getOutputStream();
            InputStream is = clientSocket.getInputStream();

            MainByteProtocol.send(os, in.nextLine());
            MainByteProtocol.read(is);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}