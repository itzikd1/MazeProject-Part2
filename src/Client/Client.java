package Client;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }

    public void start() {
        try (Socket serverSocket = new Socket(serverIP, serverPort)) {
            System.out.println(String.format("Client is connected to server (IP: %s, Port: %s)", serverIP, serverPort));
            clientStrategy.applyStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void communicateWithServer() {
    }
}
