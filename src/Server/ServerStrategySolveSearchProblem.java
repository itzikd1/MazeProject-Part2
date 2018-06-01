package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            toClient.flush();

            Maze returnToClientMaze = (Maze) fromClient.readObject(); // read object (maze) from client
            SearchableMaze searchableMaze = new SearchableMaze(returnToClientMaze); // create a new serachable maze
            BreadthFirstSearch BFS = new BreadthFirstSearch();
            Solution solve = BFS.solve(searchableMaze);
            toClient.flush();
            toClient.writeObject(solve);



//            String tempDirectoryPath = System.getProperty("java.io.tmpdir"); //create a temp direct
//            String uniqName = returnToClientMaze.toString(); //name of maze
//
//            File file = new File(tempDirectoryPath , uniqName); //create file from maze name
//            if (file.exists()) { //if it exist , solve gets the maze data
//                FileInputStream fIn = new FileInputStream(file);
//                ObjectInputStream FileToReturn = new ObjectInputStream(fIn);
//                solve = (Solution) FileToReturn.readObject();
//                FileToReturn.close();
//            }
//            else
//            {
//                try {
//                    OutputStream out = new ObjectOutputStream(new FileOutputStream(file));
//                    out.write(returnToClientMaze.toByteArray());
//                    out.flush();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

//            solve.addState();
//            returnCompressedMaze.write(returnToClientMaze.toByteArray());

            toClient.writeObject(solve);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
