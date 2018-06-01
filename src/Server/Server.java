package Server;

import Server.IServerStrategy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;

    /**
     * server class
     *
     * @param port - port of server
     * @param listeningInterval - listing interval
     * @param serverStrategy - strateft type
     */
    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    /**
     * start thread function
     */
    public void start() {
        new Thread(() -> {
            serverStrategy();
        }).start();
    }

    private void serverStrategy() {
        try {
            ThreadPoolExecutor x = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            x.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 2);
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            System.out.println(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    System.out.println(String.format("Client excepted: %s", clientSocket.toString()));
                    x.submit(new Thread(() -> {
                                handleClient(clientSocket);
                            })
                    );
                } catch (SocketTimeoutException e) {
                    System.out.println("SocketTimeout - No clients pending!");
                }
            }
            server.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            System.out.println("Client excepted!");
            System.out.println(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    public void stop() {
        System.out.println("Stopping server..");
        stop = true;
    }
}

