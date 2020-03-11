package sjt.http.client.clone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RawHeaders {

    private final List<String> namesAndValues = new ArrayList<>(20);
    private String requestLine;
    private int responseCode = -1;
    private String responseMessage;

    public RawHeaders() {}

    public RawHeaders(RawHeaders copyForm) {
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

    public void setStatusLine(String statusLine) {

    }

    private void addLenient(String fieldName, String value) {
        namesAndValues.add(fieldName);
        namesAndValues.add(value.trim());
    }


    public void removeAll(String fieldName) {
    }

    public Map<String, List<String>> toMultimap(boolean response) {
        return null;
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
}
