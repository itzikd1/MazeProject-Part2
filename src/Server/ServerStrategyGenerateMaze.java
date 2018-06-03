package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;
import java.util.Properties;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        Server.Configurations.Conf();
        Properties prop = new Properties();
        InputStream input = null;
        File fileCheck = new File("config.properties");
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            MyCompressorOutputStream returnCompressedMaze = new MyCompressorOutputStream(temp);

            toClient.flush();

            String mazeType = "MyMazeGenerator"; //default
            IMazeGenerator Type = null;
            if (fileCheck.length() != 0) { //if properties file empty, and hasnt been run yet
                input = new FileInputStream("config.properties");
                // load a properties file
                prop.load(input);
                mazeType = prop.getProperty("MazeType"); //get algorithm type from config file
                if (mazeType==null)
                    mazeType="MyMazeGenerator";
                if (mazeType.equals("SimpleMazeGenerator"))
                    Type = new SimpleMazeGenerator();
                else
                    Type = new MyMazeGenerator();
            }
            else
                Type = new MyMazeGenerator(); //default
            int[] mazeProp = (int[]) fromClient.readObject();
            Maze returnToClientMaze = Type.generate(mazeProp[0], mazeProp[1]);
            byte[] ReturnDoneMaze = returnToClientMaze.toByteArray();
            returnCompressedMaze.write(ReturnDoneMaze);
            toClient.writeObject(temp.toByteArray());
            returnCompressedMaze.flush();
            toClient.flush();


        } catch (IOException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
