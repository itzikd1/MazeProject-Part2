package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            toClient.flush();

            Maze returnToClientMaze = (Maze) fromClient.readObject();
            Solution solve = new Solution();

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
