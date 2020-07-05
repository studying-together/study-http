package sjt.http.client.module.response;

import lombok.Getter;
import sjt.http.StatusLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpResponse {

    private static final String HEADER_DELIMITER = ": ";
    private static final String CRLF = "\r\n";

    private StatusLine statusLine;
    private Map<String, String> responseHeaders = new HashMap<>();
    private String responseBody;

    /**
     * 서버로부터 Response 읽어오기
     * statusLine, header, body 순으로 읽어옵니다.
     *
     * @param inputStream
     * @throws IOException
     */
    public void readHttpResponse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        this.statusLine = StatusLine.parse(reader.readLine());
        readHeader(reader);
        readBody(reader);
    }

    /**
     * BufferedReader로부터 Header 정보를 읽어와 Map에 넣어준다.
     *
     * @param reader
     * @throws IOException
     */
    private void readHeader(BufferedReader reader) throws IOException {
        String responseHeaderLine;

        while (reader.ready() && (responseHeaderLine = reader.readLine()) != null) {
            if ("".equals(responseHeaderLine)) {
                break;
            }
            String[] splitHeader = responseHeaderLine.split(HEADER_DELIMITER);
            responseHeaders.put(splitHeader[0], splitHeader[1]);
        }
    }

    /**
     * BufferedReader로부터 Body정보를 읽어온다.
     *
     * @param reader
     * @throws IOException
     */
    private void readBody(BufferedReader reader) throws IOException {
        String responseBodyLine;
        StringBuilder stringBuilder = new StringBuilder();

        while (reader.ready() && (responseBodyLine = reader.readLine()) != null) {
            stringBuilder.append(responseBodyLine).append(CRLF);
        }

        responseBody = stringBuilder.toString();
    }

}
