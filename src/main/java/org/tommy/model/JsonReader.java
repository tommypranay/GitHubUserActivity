package org.tommy.model;

import java.util.List;
import java.util.Map;

/**
 * JsonReader provides utility methods to retrieve deeply nested values
 * from a parsed JSON structure represented as a Map<String, Object>.
 *
 * It supports dot-delimited key access, allowing retrieval of values
 * like "payload.issue.number" from a nested structure.
 */
public class JsonReader {

    // Parsed JSON data stored as a nested map
    private Map<String, Object> data;

    // Delimiter used to traverse nested keys (default: ".")
    private String delimiter;

    private JsonReader(Map<String, Object> data, String delimiter){
        this.data = data;
        this.delimiter = delimiter;
    }


    public static JsonReader of(Map<String, Object> data, String delimiter){
        return new JsonReader(data, delimiter);
    }

    public static JsonReader of(Map<String, Object> data){
        return of(data, "\\.");
    }

    /**
     * Retrieves a value from the nested map structure using a delimited key path.
     * Example: getValueForKey("payload.issue.number") will drill down through the
     * 'payload' map, then 'issue', and return the value associated with 'number'.
     *
     * @param keys the list of keys
     * @return the corresponding value (or null if path is invalid)
     * @param <T> expected return type
     */
    @SuppressWarnings("unchecked")
    private  <T> T getValueForNestedKeys(String... keys){
        Object value = data;
        for(String key : keys){
            if(isKeyList(key)){
                value = fetchValueFromList(key, ((Map<String, List< ? extends Object>>) value));
            }else if(value instanceof Map){
                value = ((Map<String, Object>)value).get(key);
            }else break;
        }
        return (T)value;
    }


    /**
     * Retrieves a value from a list inside a nested JSON structure using a key of the form:
     * "payload.issues[3]" where "payload.issues" refers to a list and "[3]" is the index.
     *
     * @param key the full key string, e.g., "payload.issues[3]"
     * @return the object at the specified list index, or null if path is invalid or out of bounds
     */
    private Object fetchValueFromList(String key, Map<String, List<? extends Object>> data) {
        int bracketIndex = key.indexOf("[");
        int elementNo = Integer.parseInt(key.substring(bracketIndex+1,key.length()-1));
        String actualKey = key.substring(0,bracketIndex);
        return data.get(actualKey).get(elementNo);
    }

    private boolean isKeyList(String key) {
        return key.indexOf("[") > 0 && key.endsWith("]");
    }

    public <T> T getValueForKey(String key){
        String[] keys = key.split(delimiter);
        return getValueForNestedKeys(keys);
    }
}
