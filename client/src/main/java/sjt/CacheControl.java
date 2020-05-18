package sjt;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum CacheControl {

    NO_CACHE("no-cache") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Boolean convertedValue = (Boolean) value;
            if (ObjectUtils.isNotEmpty(requestCacheControl)) {
                requestCacheControl.setNoCache(convertedValue);
            }

            if (ObjectUtils.isNotEmpty(responseCacheControl)) {
                responseCacheControl.setNoCache(convertedValue);
            }
        }
    },
    NO_STORE("no-store") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Boolean convertedValue = (Boolean) value;
            if (ObjectUtils.isNotEmpty(requestCacheControl)) {
                requestCacheControl.setNoStore(convertedValue);
            }

            if (ObjectUtils.isNotEmpty(responseCacheControl)) {
                responseCacheControl.setNoStore(convertedValue);
            }
        }
    },
    MAX_AGE("max-age") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Integer convertedValue = (Integer) value;
            if (ObjectUtils.isNotEmpty(requestCacheControl)) {
                requestCacheControl.setMaxAgeSeconds(convertedValue);
            }

            if (ObjectUtils.isNotEmpty(responseCacheControl)) {
                responseCacheControl.setMaxAgeSeconds(convertedValue);
            }
        }
    },
    S_MAX_AGE("s-maxage") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Integer convertedValue = (Integer) value;
            if (ObjectUtils.isNotEmpty(responseCacheControl)) {
                responseCacheControl.setSMaxAgeSeconds(convertedValue);
            }
        }
    },
    PRIVATE("private") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Boolean convertedValue = (Boolean) value;
            if (ObjectUtils.isNotEmpty(responseCacheControl)) {
                responseCacheControl.setIsPrivate(convertedValue);
            }
        }
    },
    PUBLIC("public") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Boolean convertedValue = (Boolean) value;
            if (ObjectUtils.isNotEmpty(responseCacheControl)) {
                responseCacheControl.setIsPublic(convertedValue);
            }
        }
    },
    MUST_REVALIDATE("must-revalidate") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Boolean convertedValue = (Boolean) value;
            if (ObjectUtils.isNotEmpty(responseCacheControl)) {
                responseCacheControl.setMustRevalidate(convertedValue);
            }
        }
    },
    MAX_STALE("max-stale") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Integer convertedValue = (Integer) value;
            if (ObjectUtils.isNotEmpty(requestCacheControl)) {
                requestCacheControl.setMaxStaleSeconds(convertedValue);
            }
        }
    },
    MIN_FRESH("min-fresh") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Integer convertedValue = (Integer) value;
            if (ObjectUtils.isNotEmpty(requestCacheControl)) {
                requestCacheControl.setMinFreshSeconds(convertedValue);
            }
        }
    },
    ONLY_IF_CACHE("only-if-cache") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Boolean convertedValue = (Boolean) value;
            if (ObjectUtils.isNotEmpty(requestCacheControl)) {
                requestCacheControl.setOnlyIfCache(convertedValue);
            }
        }
    },
    NO_TRANSFORM("no-transform") {
        @Override
        public void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value) {
            Boolean convertedValue = (Boolean) value;
            if (ObjectUtils.isNotEmpty(requestCacheControl)) {
                requestCacheControl.setNoTransform(convertedValue);
            }
        }
    };

    private String directive;

    CacheControl(String directive) {
        this.directive = directive;
    }

    public String getDirective() {
        return directive;
    }

    public abstract void doWork(RequestCacheControl requestCacheControl, ResponseCacheControl responseCacheControl, Object value);

    public static CacheControl getCacheControl(String value) {
        return Arrays.stream(CacheControl.values())
                .filter(cacheControl -> StringUtils.equals(value, cacheControl.getDirective()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
