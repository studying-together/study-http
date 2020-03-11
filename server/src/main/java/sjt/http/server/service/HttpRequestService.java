package sjt.http.server.service;

import com.sun.tools.javac.util.Assert;
import org.apache.commons.lang3.StringUtils;
import sjt.http.server.HttpMethod;
import sjt.http.server.request.HttpRequest;

public class HttpRequestService {

    public void populateRequestStartLine(HttpRequest httpRequest, String line) {

        String[] firstLineArray = StringUtils.split(line, " ");
        Assert.checkNonNull(firstLineArray);
        String method = firstLineArray[0];
        String path = firstLineArray[1];
        String version = firstLineArray[2];

        HttpMethod httpMethod = HttpMethod.getHttpMethod(method);
        httpRequest.setHttpMethod(httpMethod);
        httpRequest.setPath(path);
        httpRequest.setHttpVersion(version);
    }

    public void populateEntityHeader(HttpRequest httpRequest, String line) {

    }
}
