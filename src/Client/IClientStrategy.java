package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * client strategy class
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
