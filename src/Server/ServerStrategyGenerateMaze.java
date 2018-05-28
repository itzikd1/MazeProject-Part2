package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            ArrayList<Integer> al = (ArrayList<Integer>)fromClient.readObject();
            Maze returnToClientMaze = new Maze(al.get(0),al.get(1));
            toClient.writeObject(returnToClientMaze.toByteArray());
            MyCompressorOutputStream temp = new MyCompressorOutputStream(toClient);
            toClient.writeObject(temp);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
