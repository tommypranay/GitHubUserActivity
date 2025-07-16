package org.tommy.model;

import org.tommy.parser.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonDataHandler {

    private final JsonParser jsonParser = new JsonParser();

    public JsonDataHandler(){}

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
