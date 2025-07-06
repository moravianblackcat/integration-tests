package cz.dan.integrationtests.http;

import java.util.*;

public class HttpResponsesCache extends HashMap<String, Object> {

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getArray(String key) {
        Object value = get(key);
        if (value instanceof List) {
            return (List<Map<String, Object>>) value;
        }
        throw new ClassCastException("Value is not a List<Map<String, Object>>");
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getObject(String key) {
        Object value = get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        throw new ClassCastException("Value is not a Map<String, Object>");
    }

    @Override
    public Object put(String key, Object value) {
        if (!(value instanceof Map || value instanceof List)) {
            throw new IllegalArgumentException("Value must be Map<String, Object> or List<Map<String, Object>>");
        }
        return super.put(key, value);
    }

}
