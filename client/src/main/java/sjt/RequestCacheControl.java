package sjt;

import lombok.Setter;
import sjt.http.HttpHeaders;

import java.util.Map;

@Setter
public class RequestCacheControl {

    private Boolean noCache;
    private Boolean noStore;
    private Integer maxAgeSeconds;
    private Integer maxStaleSeconds;
    private Integer minFreshSeconds;
    private Boolean noTransform;
    private Boolean onlyIfCache;

    public static RequestCacheControl parser() {
        RequestCacheControl requestCacheControl = new RequestCacheControl();

        Map<String, String> httpRequestHeaders = HttpHeaders.httpRequestHeader;
        for (Map.Entry<String, String> entry : httpRequestHeaders.entrySet()) {
            CacheControl cacheControl = CacheControl.getCacheControl(entry.getKey());
            cacheControl.doWork(requestCacheControl, null, entry.getKey());
        }

        return requestCacheControl;
    }
}
