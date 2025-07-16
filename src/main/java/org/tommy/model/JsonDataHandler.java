package org.tommy.model;

import org.tommy.parser.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A utility class to handle raw JSON strings and convert them into structured
 * {@link JsonReader} objects for easy key-based access.
 * <p>
 * Internally uses a custom {@link JsonParser} to parse the input JSON string
 * and convert it to either a list of objects (if the JSON is an array)
 * or a single object (if it's a single JSON object).
 */

public class JsonDataHandler {

    private final JsonParser jsonParser = new JsonParser();

    public JsonDataHandler(){}

    /**
     * Parses a raw JSON string and returns structured {@link JsonReader} instances.
     *
     * If the JSON string represents a list of objects, returns a List of {@code JsonReader}.
     * If it is a single object, returns a single {@code JsonReader} instance.
     *
     *
     * @param s Raw JSON string
     * @return A {@code JsonReader} if it's a single object, or a {@code List<JsonReader>} if it's an array
     * @throws RuntimeException if parsing fails or if the structure is invalid
     */
    @SuppressWarnings("unchecked")
    public Object extractJsonData(String s){
        Object data = jsonParser.parseJson(s);
        if(data instanceof List<?>){
            List<JsonReader> jsonReaderList = new ArrayList<>();
            List<?> listData = (List<?>)data;
            for(Object obj: listData){
                jsonReaderList.add(JsonReader.of((Map<String,Object>) obj));
            }
            return jsonReaderList;
        }
        return JsonReader.of((Map<String, Object>) data);
    }
}
