package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * server strategy class
 */
public interface IServerStrategy {
    void serverStrategy(InputStream inputStream, OutputStream outputStream);
}
