package sjt.http.server;

import model.header.ContentType;
import model.header.HttpEntityHeader;
import model.request.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpReader {
    private BufferedReader reader;

    public HttpReader(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public HttpRequest readRequest() throws IOException{
        HttpRequest request = new HttpRequest();

        parseRequestLine(request);
        parseHeader(request);
        parseBody(request);

        return request;
    }

    private void parseRequestLine(HttpRequest request) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine != null) {
            request.parseRequestLine(requestLine);
        }
    }

    private void parseHeader(HttpRequest request) throws IOException {
        String requestHeaderLine;
        while (reader.ready() && (requestHeaderLine = reader.readLine()) != null) {
            if("".equals(requestHeaderLine)){
                break;
            }
            request.parseRequestHeader(requestHeaderLine);
        }
    }

    private void parseBody(HttpRequest request) throws IOException {
        // 정보가 없으면 읽지 않는다.
        if(!request.containHeader(HttpEntityHeader.CONTENT_TYPE)) {
            return;
        }

        String contentTypeString = request.getRequestHeader().get(HttpEntityHeader.CONTENT_TYPE);
        ContentType contentType = ContentType.fromStirng(contentTypeString);

        String requestBodyLine;
        StringBuilder stringBuilder = new StringBuilder();

        while (reader.ready() && (requestBodyLine = reader.readLine()) != null) {
            stringBuilder.append(requestBodyLine);
            stringBuilder.append("\n");
        }

        String requestBodyString = stringBuilder.toString();

        Object parsedBody = contentType.getParser().parseBody(requestBodyString);
        request.setRequestBody(parsedBody);
    }

    public void close() throws IOException {
        reader.close();
    }
}
