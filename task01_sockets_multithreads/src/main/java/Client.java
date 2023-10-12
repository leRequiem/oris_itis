import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {
            Socket clientSocket = new Socket ("127.0.0.1",50070);

            System.out.println(clientSocket.getLocalAddress());
            System.out.println(clientSocket.getLocalPort());

            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream os = clientSocket.getOutputStream();

            String inputLine;
            while((inputLine = in.nextLine()) != null) {

                os.write((inputLine + "\n").getBytes("UTF8"));
                if (inputLine.equals("stop")) {
                    break;
                }

                String receivedData = br.readLine();
                System.out.println("Received Data: " + receivedData);
            }
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}