package Server;

import Server.Strategies.IServerStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Aviadjo on 3/2/2017.
 */
public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private final Logger LOG = LogManager.getLogger(); //Log4j2

    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            LOG.info(String.format("Server started (port: %s, listening Interval: %s)",port,listeningIntervalMS));
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    LOG.info(String.format("Client excepted: %s",clientSocket.toString()));
                    try {
                        serverStrategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                        clientSocket.getInputStream().close();
                        clientSocket.getOutputStream().close();
                        clientSocket.close();
                    } catch (IOException e) {
                        LOG.error("IOException", e);
                    }
                } catch (SocketTimeoutException e) {
                    LOG.debug("Socket Timeout - no Client requests!");
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
    }

    public void stop() {
        LOG.info("Stopping server..");
        stop = true;
    }
}
