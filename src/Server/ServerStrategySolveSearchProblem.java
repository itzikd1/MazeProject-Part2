package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            Solution solve;

            Maze returnToClientMaze = (Maze) fromClient.readObject(); // read object (maze) from client

            String tempDirectoryPath = System.getProperty("java.io.tmpdir"); //create a temp direct
            System.out.println(System.getProperty("java.io.tmpdir"));
            String uniName = returnToClientMaze.toString(); //name of maze

            File file = new File(tempDirectoryPath , uniName); //create file from maze name
            if (file.exists()) { //if it exist , solve gets the maze data
                FileInputStream fIn = new FileInputStream(file);
                ObjectInputStream FileToReturn = new ObjectInputStream(fIn);
                solve = (Solution) FileToReturn.readObject();
                FileToReturn.close();
            }
            else
            {
                //solve maze
                SearchableMaze searchableMaze = new SearchableMaze(returnToClientMaze); // create a new searchable maze
                BreadthFirstSearch BFS = new BreadthFirstSearch();
                solve = BFS.solve(searchableMaze);

                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream objectReturn = new ObjectOutputStream(fileOut);

                objectReturn.writeObject(solve);
                objectReturn.flush();
                objectReturn.close();

            }
            toClient.writeObject(solve);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
