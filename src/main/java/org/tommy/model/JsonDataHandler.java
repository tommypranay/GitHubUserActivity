package org.tommy.model;

import org.tommy.parser.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonDataHandler {

    private final JsonParser jsonParser = new JsonParser();

    public JsonDataHandler(){}

    public Object extractJsonData(String s){
        Object data = jsonParser.parseJson(s);
        if(data instanceof List<?>){
            List<JsonData> jsonDataList = new ArrayList<>();
            List<?> listData = (List<?>)data;
            for(Object obj: listData){
                jsonDataList.add(JsonData.of((Map<String,Object>) obj));
            }
            return jsonDataList;
        }
        return JsonData.of((Map<String, Object>) data);
    }
}
