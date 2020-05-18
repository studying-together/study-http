package sjt;

import lombok.Setter;
import sjt.http.HttpHeaders;

import java.util.Map;

@Setter
public class ResponseCacheControl {
    private Boolean isPrivate;
    private Boolean isPublic;
    private Boolean noCache;
    private Boolean noStore;
    private Boolean noTransform;
    private Boolean mustRevalidate;
    private Boolean proxyRevalidate;
    private Integer maxAgeSeconds;
    private Integer sMaxAgeSeconds;

    public static ResponseCacheControl parser() {
        ResponseCacheControl responseCacheControl = new ResponseCacheControl();

        Map<String, String> httpResponseHeaders = HttpHeaders.httpResponseHeader;
        for (Map.Entry<String, String> entry : httpResponseHeaders.entrySet()) {
            CacheControl cacheControl = CacheControl.getCacheControl(entry.getKey());
            cacheControl.doWork(null, responseCacheControl, entry.getValue());
        }

        return responseCacheControl;
    }
}
