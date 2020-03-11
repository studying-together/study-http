package sjt.http.client.clone;

import java.io.IOException;
import java.io.OutputStream;

public class AbstractOutputStream extends OutputStream {
    @Override
    public void write(int data) throws IOException {
        write(new byte[] { (byte) data});
    }
}
