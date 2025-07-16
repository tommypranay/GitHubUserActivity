package org.tommy.parser;

import java.util.*;


/**
 * A simple JSON parser that parses basic JSON strings into Java data structures.
 *
 * Supported types:
 * - JSON objects (mapped to Map<String, Object>)
 * - JSON arrays (mapped to List<Object>)
 * - Strings
 * - Booleans
 * - Numbers (integers and floating point)
 * - null
 *
 * Note: This is a lightweight implementation and does not support all edge cases
 * of the full JSON specification (e.g., unicode escapes, number precision limits).
 */

public class JsonParser {


    /**
     * Entry point to parse a JSON string into corresponding Java objects.
     *
     * @param s the raw JSON string
     * @return a Java object (Map, List, String, Number, Boolean, or null)
     */
    public Object parseJson(String s){
        return parseValue(s);
    }

    /**
     * Parses a JSON object string into a Map.
     * Example input: {"key": "value", "num": 42}
     */
    private Object parseObject(String s){
        s = s.trim();
        if (!s.endsWith("}")) throw new RuntimeException("Invalid JSON object: " + s);

        Map<String, Object> map = new LinkedHashMap<>();
        String body = s.substring(1, s.length() - 1).trim();
        if (body.isEmpty()) return map;
        String trimmed = s.substring(1,s.length()-1);
        List<String> pairs = splitJsonPairs(trimmed);
        for(String pair: pairs){
            int colonIndex = pair.indexOf(':');
            if (colonIndex == -1) {
                throw new IllegalArgumentException("Invalid key-value pair: " + pair);
            }
            String key = pair.substring(0,colonIndex).trim();
            if(key.startsWith("\"") && key.endsWith("\"")){
                key=key.substring(1,key.length()-1);
            }
            String valueStr = pair.substring(colonIndex+1);
            Object value = parseValue(valueStr);
            map.put(key,value);
        }
        return map;
    }

    /**
     * Parses a JSON array string into a List.
     * Example input: [1, "two", true]
     */
    private Object parseArray(String s){
        s = s.trim();
        List<Object> list = new ArrayList<>();
        String trimmed = s.substring(1,s.length()-1);
        List<String> items = splitJsonArray(trimmed);
        for(String item: items){
            list.add(parseValue(item));
        }
        return list;
    }

    /**
     * Splits the key-value pairs inside a JSON object, correctly handling
     * commas inside strings or nested objects/arrays.
     */
    private List<String> splitJsonPairs(String s){
        return splitJsonArray(s);
    }

    /**
     * Splits the elements of a JSON array (or JSON object key-value pairs).
     * Correctly handles nesting and quoted commas using a level counter and
     * string flag.
     */
    private List<String> splitJsonArray(String s){
        s= s.trim();
        List<String> result = new ArrayList<>();
        int level = 0;
        StringBuilder current = new StringBuilder();
        boolean inString = false;
        for(int i=0; i < s.length(); i++){
            char c = s.charAt(i);

            // If inside a string we have to ignore ','s and brackets '{', '}', '[', ']'
            if (c == '"' && (i == 0 || s.charAt(i - 1) != '\\')) {
                inString = !inString;
            }

            if (c == ',' && level == 0 && !inString) {
                result.add(current.toString().trim());
                current.setLength(0);
            } else {
                if (!inString) {
                    if (c == '{' || c == '[') level++;
                    if (c == '}' || c == ']') level--;
                }
                current.append(c);
            }

        }
        if(!current.isEmpty()) result.add(current.toString().trim());
        return result;
    }


    /**
     * Determines the type of the value based on its syntax and delegates to appropriate parser.
     */
    private Object parseValue(String s) {
        s = s.trim();
        if (s.startsWith("{")) {
            return parseObject(s);
        } else if (s.startsWith("[")) {
            return parseArray(s);
        } else if (s.startsWith("\"")) {
            return s.substring(1, s.length() - 1);
        } else if (s.equals("null")) {
            return null;
        } else if (s.equals("true") || s.equals("false")) {
            return Boolean.parseBoolean(s);
        } else if (s.matches("-?\\d+(\\.\\d+)?([Ee][+-]?\\d+)?")) {

            try {
                if (s.contains(".") || s.contains("e") || s.contains("E")) {
                    return Double.parseDouble(s);
                } else {
                    return Long.parseLong(s);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid number: " + s);
            }
        } else {
            throw new RuntimeException("Unrecognized JSON value: " + s);
        }
    }


}
