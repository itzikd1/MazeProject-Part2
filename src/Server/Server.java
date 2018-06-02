package Server;

import Server.IServerStrategy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
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
     * @param port              - port of server
     * @param listeningInterval - listing interval
     * @param serverStrategy    - strategy type
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
        Server.Configurations.Conf();
        Properties prop = new Properties();
        InputStream input = null;
        File file = new File("config.properties");
        try {
            int core = 2; //default TODO check if file empty
            if (file.length() != 0) { //if properties file empty, and hasnt been run yet
                input = new FileInputStream("config.properties");
                // load a properties file
                prop.load(input);
                core = Integer.parseInt(prop.getProperty("numberCores")); //get number of cores from config file
            }
            ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            threadPool.setCorePoolSize(Runtime.getRuntime().availableProcessors() * core);
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
//            System.out.println(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
//                    System.out.println(String.format("Client excepted: %s", clientSocket.toString()));
                    threadPool.submit(new Thread(() -> {
                                handleClient(clientSocket);
                            })
                    );
                } catch (SocketTimeoutException e) {
                    e.getStackTrace();
//                    stop();
                }
            }
            server.close();
            threadPool.shutdown();
        } catch (IOException | NumberFormatException e) {
            e.getStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
//            System.out.println("Client excepted!");
//            System.out.println(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
//            clientSocket.getInputStream().close();
//            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }

    /**
     * Configurations class
     */

    public static class Configurations {

        private Configurations() {
        }

        public static void Conf()
        {
            Properties prop = new Properties();
            OutputStream output = null;
            try {
                output = new FileOutputStream("Resources/config.properties");

                // set the properties value
                prop.setProperty("MazeAlgoType", "BreadthFirstSearch");
                prop.setProperty("numberCores", "2");
                prop.setProperty("MazeType", "MyMazeGenerator");

                // save properties to project root folder
                prop.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

