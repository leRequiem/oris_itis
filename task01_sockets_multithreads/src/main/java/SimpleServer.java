import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static final int SERVER_PORT = 50070;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            System.out.println("Waiting for client...");

            while (true) {
                // Ожидание клиента
                Socket clientSocket = server.accept();
                System.out.println("Client connected");

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                System.out.println("Client connected to " + Thread.currentThread().getName());

                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    System.out.println("Клиент: " + inputLine);
                    writer.println("Сервер: Ваш запрос: " + inputLine);

                    if (inputLine.equals("stop")) {
                        System.out.println("Client on " + Thread.currentThread().getName() + " disconnected");
                        break;
                    }
                }
                clientSocket.close();

            } catch(IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}