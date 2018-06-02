package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        Properties prop = new Properties();
        InputStream input = null;
        File fileCheck = new File("config.properties");
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            Solution solve;

            Maze returnToClientMaze = (Maze) fromClient.readObject(); // read object (maze) from client

            String tempDirectoryPath = System.getProperty("java.io.tmpdir"); //create a temp direct
//            System.out.println(System.getProperty("java.io.tmpdir"));
            String uniName = returnToClientMaze.toString(); //name of maze

            File file = new File(tempDirectoryPath, uniName); //create file from maze name
            if (file.exists()) { //if it exist , solve gets the maze data
                FileInputStream fIn = new FileInputStream(file);
                ObjectInputStream FileToReturn = new ObjectInputStream(fIn);
                solve = (Solution) FileToReturn.readObject();
                FileToReturn.close();
            } else {
                String mazeTypeSerach = "BreadthFirstSearch"; //default
                SearchableMaze searchableMaze = new SearchableMaze(returnToClientMaze); // create a new searchable maze
                ASearchingAlgorithm Type = null;
                if (fileCheck.length() != 0) { //if properties file empty, and hasnt been run yet
                    input = new FileInputStream("config.properties");
                    // load a properties file
                    prop.load(input);
                    mazeTypeSerach = prop.getProperty("mazeType"); //get number of cores from config file
                }
                //solve maze
                if (mazeTypeSerach.equals("DepthFirstSearch"))
                    Type = new DepthFirstSearch();
                else if (mazeTypeSerach.equals("BestFirstSearch"))
                    Type = new BestFirstSearch();
                else
                    Type = new BreadthFirstSearch();
                solve = Type.solve(searchableMaze);

                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream objectReturn = new ObjectOutputStream(fileOut);

                objectReturn.writeObject(solve);
                objectReturn.flush();


            }
            toClient.writeObject(solve);
            toClient.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
