package IO;

import algorithms.mazeGenerators.Maze;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {

    InputStream in;

    public MyDecompressorInputStream(InputStream IPS) {
        in = IPS;
    }

    private int getSize(int intNum) {
        int size = 0;
        if (intNum > 127)
            size = 8;
        else if (intNum > 63)
            size = 7;
        else if (intNum > 31)
            size = 6;
        else if (intNum > 15)
            size = 5;
        else if (intNum > 7)
            size = 4;
        else if (intNum > 3)
            size = 3;
        else if (intNum > 1)
            size = 2;
        else
            size = 1;
        return size;
    }

    private byte[] convertArrByte(byte tmp) {
        int intNum = (int) tmp;
        int size;
        if (intNum < 0)
            intNum = intNum + 256;
        size = getSize(intNum);
        byte[] temp = new byte[size];
        for (int i = 0; i >= size; i++) {
            temp[i] = (byte) (intNum % 2);
            intNum = intNum / 2;
        }
        return temp;
    }

//TODO remmber to do x.length - something because of %8

    public int read(byte[] b) {
        ArrayList<byte[]> temp = new ArrayList<>();//byte[] answer size is unknown
        byte[] tmp = new byte[8];
        int lastsize = 0;
        int j = 8;//here the maze's values start
        while (j < b.length) {
            int tempcheck = 0;
            tmp = convertArrByte(b[j]);
            temp.add(tmp);
            lastsize = getSize(b[j]);
            j++;
        }
        byte[] compressedMaze = new byte[8 + temp.size() * 8 - 8 + lastsize];//8 cells for maze' details and rest for 0,1 repeatitions
        int copy = 0;
        for (; copy < 8; copy++)
            compressedMaze[copy] = b[copy];//8 cells for maze's info
        while (temp.size() != 0) {//add arraylist values to the byte[] answer
            //(0,0) is start position, therefore the values on even indexes (8,10,...)represents 0 combos
            byte [] x = temp.remove(0);
            for (int i = 0; i < 8; i++) {
                if (compressedMaze.length>=copy)
                    break;
                compressedMaze[copy] = (byte) x[i];
                copy++;
            }
        }
        for (int i = 0; i < compressedMaze.length; i++)
            System.out.print(compressedMaze[i] + " ");
        try {
            in.read(compressedMaze);

        } catch (IOException e) {
            e.printStackTrace();

        }
        return 0;
    }


    public int read() {
        return 1;
    }
}
