package sjt.http;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

class ResponseTest {


    @Test
    void readBody() throws IOException {
        String data = "{\"data\":{\"copied\":\"true\", \"title\":\"mixAndMatch\"}, \"count\":23}\r\n";
        int length = data.getBytes().length;
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.ready())
                .thenReturn(true);
        when(bufferedReader.read((char[]) anyObject()))
                .thenReturn(length);

        Response response = new Response();
        response.readBody(bufferedReader, length);

        System.out.println(response.toString());
        // TODO : 테스트 방법 고려
        assertEquals(data, response.getBody());
        assertEquals(length, response.getBody().getBytes().length);
    }

    @Test
    void readChunkedBody() throws IOException {
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.ready())
                .thenReturn(true);
        when(bufferedReader.readLine())
                .thenReturn("4")
                .thenReturn("Wiki")
                .thenReturn("5")
                .thenReturn("pedia")
                .thenReturn("E")
                .thenReturn(" in")
                .thenReturn("")
                .thenReturn("chunks.")
                .thenReturn("0")
                .thenReturn("");

        Response response = new Response();
        response.readChunkedBody(bufferedReader);

        System.out.println(response.toString());
        assertEquals("Wiki\r\npedia\r\n in\r\n\r\nchunks.\r\n", response.getBody());
    }
}