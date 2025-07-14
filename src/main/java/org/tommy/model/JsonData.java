package org.tommy.model;

import java.util.List;
import java.util.Map;

public class JsonData {

    private Map<String, Object> data;

    private String delimiter;

    private JsonData(Map<String, Object> data, String delimiter){
        this.data = data;
        this.delimiter = delimiter;
    }



    public static JsonData of(Map<String, Object> data, String delimiter){
        return new JsonData(data, delimiter);
    }

    public static JsonData of(Map<String, Object> data){
        return of(data, "\\.");
    }

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
