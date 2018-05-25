package IO;
//TODO ADD THE ANSWER TO THE OPS
//TODO 128 REPETITIONS.
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;


    public MyCompressorOutputStream(OutputStream OPS) {

        out = OPS;
    }

    public void write(int b) {

    }

    public void write(byte[] b) {
        ArrayList<Integer> temp = new ArrayList<>();//byte[] answer size is unknown
        int oneRepeatitions = 0;
        int zeroRepeatitions = 0;
        int j = 8;//here the maze's values start
        while (j != b.length) {
            for (; j < b.length; j++) {
                //TODO until reach the first cell that describe maze size etc.. if b[i]=='sign' ->done
                if (b[j] == 0) {
                    zeroRepeatitions++;
                    if (j == b.length - 1)//last cell
                        temp.add(zeroRepeatitions);
                } else {//a 0's combo finished
                    temp.add(zeroRepeatitions);
                    zeroRepeatitions = 0;//reset for next 0's scan
                    break;
                }

            }

            for (; j < b.length; j++) {
                if (b[j] == 1) {
                    oneRepeatitions++;
                    if (j == b.length - 1)
                        temp.add(oneRepeatitions);
                } else {
                    temp.add(oneRepeatitions);
                    oneRepeatitions = 0;
                    break;

                }
            }
        }

        byte[] compressedMaze = new byte[8 + temp.size()];//8 cells for maze' details and rest for 0,1 repeatitions
        int copy = 0;
        for (; copy < 8; copy++)
            compressedMaze[copy] = b[copy];//8 cells for maze's info
        while (temp.size() != 0) {//add arraylist values to the byte[] answer
            //(0,0) is start position, therefore the values on even indexes (8,10,...)represents 0 combos
            compressedMaze[copy] = (byte) (temp.remove(0).intValue());
            copy++;
        }

        try{
            for(int i = 0 ; i < compressedMaze.length; i++)
                out.write(compressedMaze[i]);
        }
        catch(IOException e){
            e.printStackTrace();

        }
    }
}
