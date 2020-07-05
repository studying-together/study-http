package sjt;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum Protocol {

    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1");

    private final String protocol;

    Protocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public static Protocol getProtocol(String type) {
        return Arrays.stream(Protocol.values())
                .filter(protocol -> StringUtils.equals(protocol.getProtocol(), type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
