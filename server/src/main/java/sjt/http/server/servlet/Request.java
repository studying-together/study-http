package sjt.http.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {
    private String startLine;

    public Request(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        this.startLine = reader.readLine();
    }

    public String getStartLine() {
        return this.startLine;
    }
}
