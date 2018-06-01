package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream IPS) {
        in = IPS;
    }


    // gets byte and transfers to 8 or less bits sequence
    private byte[] ByteToBits(int b) {
        int tmp = (int) b;
        if (tmp < 0)
            tmp += 256;
        byte[] ans = new byte[8];
        for (int i = 7; i >= 0; i--) {
            ans[i] = (byte) (tmp % 2);
            tmp = tmp / 2;
        }
        return ans;
    }

//TODO remmber to do x.length - something because of %8

    /**
     * read from input stream
     *
     * @param b - bye array
     * @return nothing
     */
    public int read(byte[] b) {
        ArrayList<Byte> fromUser = new ArrayList<>();

        try {
            while (in.available() > 0) // check if the file stream is at the end
                fromUser.add((byte) in.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 8; i++)
            b[i] = fromUser.remove(0);
        int row = b[0] * 256 + b[1]; //set size of row
        int col = b[2] * 256 + b[3]; // set size of column

        byte[] byteArrayFinal = new byte[row * col + 8];
        int i = 8;
        while (fromUser.size() != 1) {
            int toBinary = fromUser.remove(0);
            if (toBinary < 0)
                toBinary += 256;
            byte[] binaryValues = ByteToBits(toBinary);
            for (int j = 0; j < 8; i++) {
                byteArrayFinal[i] = binaryValues[j];
                j++;
            }
        }
        byte toBinary = fromUser.remove(0);
        int lastCheck = byteArrayFinal.length % 8;
        if (lastCheck != 0) {
            byte[] binaryValues = ByteToBits(toBinary);

            //for example, if size is 2,value 3, we dont want to add 00000011
            for (int j = 8 - lastCheck; j < 8; i++) {
                byteArrayFinal[i] = binaryValues[j];
                j++;
            }
        } else {
            if (toBinary < 0)
                toBinary += 256; // if negative number
            byte[] binaryValues = ByteToBits(toBinary);
            for (int j = 0; j < 8; i++) {//trasfer 8 values of details of maze
                byteArrayFinal[i] = binaryValues[j];
                j++;
            }
        }
        for (int f = 8; f < byteArrayFinal.length; f++)
            b[f] = byteArrayFinal[f];//trasfser rest

        for (int m = 0; m < b.length; m++) {
            System.out.print(b[m]);

        }
        System.out.println("");
        return 0;
    }


    public int read() {

        return 0;
    }
}
