package sjt.http.server;

import model.HttpStatusCode;
import model.header.HttpEntityHeader;
import model.request.HttpRequest;
import model.response.HttpResponse;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

public class HttpWriter {
    private final String RESOURCE_PATH = "server/src/main/resource";
    private BufferedOutputStream bufferedOutputStream;

    public HttpWriter(OutputStream outputStream) {
         bufferedOutputStream = new BufferedOutputStream(outputStream);
    }

    public HttpResponse writeResponse(HttpRequest httpRequest) throws IOException{
            HttpResponse httpResponse = new HttpResponse();
            FileInputStream fileInputStream = null;

            String path = RESOURCE_PATH + httpRequest.getUri();
            fileInputStream = new FileInputStream(path);

            httpResponse.setProtocolVersion("Http /1.1");
            httpResponse.setStatusCode(HttpStatusCode.HTTP_STATUS_OK);

            if(fileInputStream == null) {
                writeSimpleResponse(httpResponse);
                close();
                return httpResponse;
            }

            //contentType 추가
            String contentType = URLConnection.guessContentTypeFromName(path);
            httpResponse.addResponseHeaders(HttpEntityHeader.CONTENT_TYPE, contentType);

            // 파일 읽기
            writeFileContent(fileInputStream, httpResponse);
            close();
            return httpResponse;
    }

    public void writeFileContent(FileInputStream fileInputStream, HttpResponse httpResponse) throws IOException {
        int readCount = 0;
        byte[] buffer = new byte[1024];

        bufferedOutputStream.write(httpResponse.getStatusLine().getBytes());

        while((readCount = fileInputStream.read(buffer)) != -1){
            bufferedOutputStream.write(buffer,0, readCount);
        }
    }

    public void writeSimpleResponse(HttpResponse httpResponse) throws IOException{
        bufferedOutputStream.write(httpResponse.getStatusLine().getBytes());
    }

    public void close() {
        try{ bufferedOutputStream.close(); } catch(Exception ignoreException){ }
    }


}
