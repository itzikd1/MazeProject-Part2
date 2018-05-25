package IO;

import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    InputStream in;
    public MyDecompressorInputStream(InputStream IPS){
        in = IPS;
    }
    public int read(byte [] b){

        return 0;
    }
    public int read(){

        return 0;
    }
}
