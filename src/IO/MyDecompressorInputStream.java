package IO;

import algorithms.mazeGenerators.Maze;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    InputStream in;
    public MyDecompressorInputStream(InputStream IPS){
        in = IPS;
    }
    public int read(byte [] b) {

        b[0] = 1;

        try {
            in.read(b);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int read(){

        return 0;
    }
}
