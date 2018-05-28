package Server;

import Server.IServerStrategy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;

    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    try {
                        serverStrategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                        clientSocket.getInputStream().close();
                        clientSocket.getOutputStream().close();
                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("IOException " + e);
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket Timeout - no Client requests!");
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("IOException" + e);
        }
    }

    public void stop() {
        System.out.println("Stopping server..");
        stop = true;
    }
}
