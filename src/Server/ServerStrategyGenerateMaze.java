package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            MyCompressorOutputStream returnCompressedMaze = new MyCompressorOutputStream(temp);

            toClient.flush();

            MyMazeGenerator mazeToGen = new MyMazeGenerator();
            int[] mazeProp = (int[]) fromClient.readObject();
            Maze returnToClientMaze = mazeToGen.generate(mazeProp[0], mazeProp[1]);
            byte[] ReturnDoneMaze = returnToClientMaze.toByteArray();
            returnCompressedMaze.write(ReturnDoneMaze);
            toClient.writeObject(temp.toByteArray());
            returnCompressedMaze.close();
            temp.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
