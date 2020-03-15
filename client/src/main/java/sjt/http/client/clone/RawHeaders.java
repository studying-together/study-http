package sjt.http.client.clone;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class RawHeaders {
    private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
            if (a == b) {
                return 0;
            } else if (a == null) {
                return -1;
            } else if (b == null) {
                return 1;
            } else {
                return String.CASE_INSENSITIVE_ORDER.compare(a, b);
            }
        }
    };

    private final List<String> namesAndValues = new ArrayList<>(20);
    private String requestLine;
    private String statusLine;
    private int httpMinorVersion = -1;
    private int responseCode = -1;
    private String responseMessage;

    public RawHeaders() {}

    public RawHeaders(RawHeaders copyForm) {
        namesAndValues.addAll(copyForm.namesAndValues);
        requestLine = copyForm.requestLine;
        statusLine = copyForm.statusLine;
        httpMinorVersion = copyForm.httpMinorVersion;
        responseCode = copyForm.responseCode;
        responseMessage = copyForm.responseMessage;
    }

    public static RawHeaders fromMultimap(Map<String, List<String>> map, boolean response) {
        if (!response) {
            throw new UnsupportedOperationException();
        }

        RawHeaders result = new RawHeaders();
        for (Map.Entry<String, List<String>> entry  : map.entrySet()) {
            String fieldName = entry.getKey();
            List<String> values = entry.getValue();
            if (fieldName != null) {
                for (String value : values) {
                    result.addLenient(fieldName, value);
                }
            } else if (!values.isEmpty()) {
                result.setStatusLine(values.get(values.size() - 1));
            }
        }

        return result;
    }

    public static RawHeaders fromBytes(InputStream in) throws IOException {
        RawHeaders headers;
        do {
            headers = new RawHeaders();
            headers.setStatusLine(Util.readAsciiLine(in));
            readHeaders(in, headers);
        } while (headers.getResponseCode() == HttpEngine.HTTP_CONTINUE);
        return headers;
    }

    private static void readHeaders(InputStream in, RawHeaders out) throws IOException {
        String line;
        while ((line = Util.readAsciiLine(in)).length() != 0) {
            out.addLine(line);
        }
    }

    private void addLine(String line) {
        int index = line.indexOf(":");
        if (index == -1) {
            addLenient("", line);
        } else {
            addLenient(line.substring(0, index), line.substring(index + 1));
        }
    }

    public void setStatusLine(String statusLine) {

    }

    public String getStatusLine() {
        return statusLine;
    }

    private void addLenient(String fieldName, String value) {
        namesAndValues.add(fieldName);
        namesAndValues.add(value.trim());
    }


    public void removeAll(String fieldName) {
    }

    public Map<String, List<String>> toMultimap(boolean response) {
        Map<String, List<String>> result = new TreeMap<>(FIELD_NAME_COMPARATOR);
        for (int i = 0; i < namesAndValues.size() ; i+= 2) {
            String fieldName = namesAndValues.get(i);
            String value = namesAndValues.get(i + 1);

            List<String> allValues = new ArrayList<>();
            List<String> otherValues = result.get(fieldName);
            if (otherValues != null) {
                allValues.addAll(otherValues);
            }
            allValues.add(value);
            result.put(fieldName, Collections.unmodifiableList(allValues));
        }
        if (response && statusLine != null) {
            result.put(null, Collections.unmodifiableList(Collections.singletonList(statusLine)));
        } else if (requestLine != null) {
            result.put(null, Collections.unmodifiableList(Collections.singletonList(requestLine)));
        }
        return Collections.unmodifiableMap(result);
    }

    public void setRequestLine(String requestLine) {
        requestLine = requestLine.trim();
        this.requestLine = requestLine;
    }

    public void add(String fieldName, String value) {

    }

    public void addAll(String key, List<String> value) {

    }

    public void set(String fieldName, String value) {
        removeAll(fieldName);
        add(fieldName, value);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String get(String fieldName) {
        for (int i = namesAndValues.size() - 2; i >= 0; i -= 2) {
            if (fieldName.equalsIgnoreCase(namesAndValues.get(i))) {
                return namesAndValues.get(i + 1);
            }
        }
        return null;
    }

    public byte[] toBytes() throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder(256);
        result.append(requestLine).append("\r\n");
        for (int i = 0; i < namesAndValues.size(); i += 2) {
            result.append(namesAndValues.get(i))
                    .append(": ")
                    .append(namesAndValues.get(i + 1))
                    .append("\r\n");
        }
        result.append("\r\n");
        return result.toString().getBytes("ISO-8859-1");
    }
}
