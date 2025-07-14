package org.tommy.parser;

import java.util.*;

public class JsonParser {
/***
 * This is a reader
 * */

    public Object parseJson(String s){
        return parseValue(s);
    }

    private Object parseObject(String s){
        s = s.trim();
        Map<String, Object> map = new LinkedHashMap<>();
        String trimmed = s.substring(1,s.length()-1);
        List<String> pairs = splitJsonPairs(trimmed);
        for(String pair: pairs){
            int colonIndex = pair.indexOf(':');
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

    private List<String> splitJsonPairs(String s){
        return splitJsonArray(s);
    }

    private List<String> splitJsonArray(String s){
        s= s.trim();
        List<String> result = new ArrayList<>();
        int level = 0;
        StringBuilder current = new StringBuilder();
        for(int i=0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == ',' && level==0){
                result.add(current.toString().trim());
                current.setLength(0);
            }else{
                if(c == '{' || c == '[') level++;
                if(c == '}' || c == ']') level--;
                current.append(c);
            }
        }
        if(current.length()>0) result.add(current.toString().trim());
        return result;
    }


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
