package sjt.http.client.module.request;

import sjt.http.client.module.HttpMethod;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpRequest {

    private static final String HEADER_DELIMITER = ": ";

    private RequestLine requestLine;
    private Map<String, String> requestHeader;
    private String requestBody;

    public HttpRequest (HttpMethod httpMethod, String uri, String version) {
        requestLine = new RequestLine(httpMethod, uri, version);
    }

    /**
     * BufferedReader로 부터 request 읽어오기
     *
     * @param reader
     * @throws IOException
     */
    public void readHttpRequest(BufferedReader reader) throws IOException {
        requestLine = new RequestLine(reader.readLine());
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
        String requestHeaderLine;
        while (reader.ready() && (requestHeaderLine = reader.readLine()) != null) {
            if("".equals(requestHeaderLine)){
                break;
            }
            String[] splitHeader = requestHeaderLine.split(HEADER_DELIMITER);
            requestHeader.put(splitHeader[0], splitHeader[1]);
        }
    }

    /**
     * BufferedReader로부터 Body정보를 읽어온다.
     *
     * @param reader
     * @throws IOException
     */
    private void readBody(BufferedReader reader) throws IOException {
        String requestBodyLine;
        StringBuilder stringBuilder = new StringBuilder();

        while (reader.ready() && (requestBodyLine = reader.readLine()) != null) {
            stringBuilder.append(requestBodyLine);
            stringBuilder.append("\n");
        }

        requestBody = stringBuilder.toString();
    }

    /**
     *  request server에 전송하기.
     *  requestLine, header, body 순으로 http request를 서버로 전송합니다.
     *
     * @param outputStream
     * @throws IOException
     */
    public void writeHttpRequest(OutputStream outputStream) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        bufferedOutputStream.write(requestLine.toString().getBytes());
        writeHeader(bufferedOutputStream);
        writeBody(bufferedOutputStream);

        bufferedOutputStream.flush();
    }

    private void writeHeader(BufferedOutputStream bufferedOutputStream) throws IOException {
        if (requestHeader == null) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : requestHeader.keySet()) {
            stringBuilder.append(key + HEADER_DELIMITER + requestHeader.get(key));
        }

        bufferedOutputStream.write(stringBuilder.toString().getBytes());
    }

    private void writeBody(BufferedOutputStream bufferedOutputStream) throws IOException {
        if (requestBody == null) {
            return;
        }

        bufferedOutputStream.write(requestBody.getBytes());
    }


    public RequestLine getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

}
