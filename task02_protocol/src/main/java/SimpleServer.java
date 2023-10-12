import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static final int SERVER_PORT = 33333;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {

            System.out.println("Waiting for client");

            while (true) {
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
            try (InputStream is = clientSocket.getInputStream();
                 OutputStream os = clientSocket.getOutputStream()) {

                MainByteProtocol.read(is);
                System.out.println("Client response from " + Thread.currentThread().getName());

                MainByteProtocol.send(os, "Ваш запрос будет рассмотрен");

                System.out.println("Client disconnected");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}