package sjt.http;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import sjt.Protocol;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.Arrays;
import java.util.List;

public class StatusLine {

    private static String STATUS_LINE_SPLIT = " ";
    private static Integer STATUS_LINE_SPLIT_SIZE = 3;

    private Protocol protocol;
    private Integer statusCode;
    private String statusMessage;

    public StatusLine(Protocol protocol, Integer statusCode, String statusMessage) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public static StatusLine parse(String statusLine) throws IOException {
        List<String> splitLine = Arrays.asList(StringUtils.split(STATUS_LINE_SPLIT));
        if (isNotValidStatusLine(splitLine)) {
            throw new ProtocolException("Unexpected status line: " + statusLine);
        }

        String protocol = splitLine.get(0);
        Protocol convertedProtocol = Protocol.getProtocol(protocol);

        String statusCode = splitLine.get(1);
        Integer convertedStatusCode = Integer.valueOf(statusCode);

        String statusMessage = splitLine.get(2);

        return new StatusLine(convertedProtocol, convertedStatusCode, statusMessage);
    }

    private static Boolean isNotValidStatusLine(List<String> splitLine) {
        if (CollectionUtils.isEmpty(splitLine) && splitLine.size() == STATUS_LINE_SPLIT_SIZE) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
