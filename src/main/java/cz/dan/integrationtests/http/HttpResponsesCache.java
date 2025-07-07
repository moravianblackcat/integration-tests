package cz.dan.integrationtests.http;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class HttpResponsesCache extends HashMap<String, Object> {

    private static final Pattern LOCAL_DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

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
        if (!isMapOrList(value)) {
            throw new IllegalArgumentException("Value must be Map<String, Object> or List<Map<String, Object>>");
        }
        Object converted = convert(value);
        return super.put(key, converted);
    }

    private boolean isMapOrList(Object v) {
        return v instanceof Map || v instanceof List;
    }

    @SuppressWarnings("unchecked")
    private Object convert(Object value) {
        if (value instanceof Map) {
            return convert((Map<String, Object>) value);
        } else if (value instanceof List) {
            return convert((List<Object>) value);
        } else if (isLocalDateString(value)) {
            return LocalDate.parse((String) value);
        }
        return value;
    }

    private Map<String, Object> convert(Map<String, Object> value) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Entry<String, Object> entry : value.entrySet()) {
            Object v = entry.getValue();
            if (isLocalDateString(v)) {
                result.put(entry.getKey(), LocalDate.parse((String) v));
            } else if (isMapOrList(v)) {
                result.put(entry.getKey(), convert(v));
            } else {
                result.put(entry.getKey(), v);
            }
        }
        return result;
    }

    private List<Object> convert(List<Object> value) {
        List<Object> result = new ArrayList<>();
        for (Object v : value) {
            if (isLocalDateString(v)) {
                result.add(LocalDate.parse((String) v));
            } else if (isMapOrList(v)) {
                result.add(convert(v));
            } else {
                result.add(v);
            }
        }
        return result;
    }

    private boolean isLocalDateString(Object value) {
        return value instanceof String && LOCAL_DATE_PATTERN.matcher((String) value).matches();
    }
}
