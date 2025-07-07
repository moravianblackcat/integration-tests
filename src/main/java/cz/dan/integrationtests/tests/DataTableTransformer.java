package cz.dan.integrationtests.tests;

import io.cucumber.java.DataTableType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DataTableTransformer {

    @DataTableType
    public Map<String, Object> transform(Map<String, String> entry) {
        Map<String, Object> transformedEntry = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

        entry.forEach((key, value) -> {
            if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
                transformedEntry.put(key, Boolean.parseBoolean(value));
            } else if (value == null) {
                transformedEntry.put(key, null);
            } else if (value.matches("-?\\d+")) {
                transformedEntry.put(key, Long.parseLong(value));
            } else if (value.matches("-?\\d+\\.\\d+")) {
                transformedEntry.put(key, Double.parseDouble(value));
            } else if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                transformedEntry.put(key, LocalDate.parse(value, dateFormatter));
            } else {
                transformedEntry.put(key, value);
            }
        });
        return transformedEntry;
    }

}
