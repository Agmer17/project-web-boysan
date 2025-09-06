package app.utils;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapperUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<String> mapJsonArrayToList(String json) {
        try {
            if (json == null || json.isBlank()) {
                return List.of();
            }
            return objectMapper.readValue(json, new TypeReference<List<String>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON array: " + json, e);
        }
    }
}
