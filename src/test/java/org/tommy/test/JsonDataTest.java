package org.tommy.test;

import org.tommy.model.JsonData;
import org.tommy.model.JsonDataHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;


public class JsonDataTest {

    public void testSimpleJson() {
        String json = "{\"name\":\"Tommy\",\"age\":30}";
        JsonDataHandler handler = new JsonDataHandler();
        Object data = handler.extractJsonData(json);
        if(data instanceof Map){
            JsonData jsonData = (JsonData)handler.extractJsonData(json);
            String name = jsonData.getValueForKey("name");
            if(!"Tommy".equals(name)){
                throw new AssertionError("Expected name to be 'Tommy', but got: " + name);

            }
            long age = (long)jsonData.getValueForKey("age");
            if(30!=age){
                throw new AssertionError("Expected age to be 30, but got: " + age);
            }
        }
        System.out.println("testSimpleJson Passed");

    }


    public void testNestedJson() {
        String json = "{\"person\":{\"name\":\"Tommy\",\"age\":30}}";
        JsonDataHandler handler = new JsonDataHandler();
        JsonData data = (JsonData)handler.extractJsonData(json);
        String name = data.getValueForKey("person.name");
        if(!"Tommy".equals(name)){
            throw new AssertionError("Expected name to be 'Tommy', but got: " + name);
        }
        long age = (long)data.getValueForKey("person.age");
        if(30!=age){
            throw new AssertionError("Expected age to be 30, but got: " + age);
        }
        System.out.println("testNestedJson Passed");
    }

    public void testJsonWithList() {
        String json = "{\"scores\":[90, 80, 85]}";
        JsonDataHandler handler = new JsonDataHandler();
        JsonData data = (JsonData)handler.extractJsonData(json);

        if ( (long)data.getValueForKey("scores[0]") != 90) {
            throw new AssertionError("Expected scores[0] to be 90");
        }
        if ( (long)data.getValueForKey("scores[1]") != 80) {
            throw new AssertionError("Expected scores[1] to be 80");
        }
        if ( (long)data.getValueForKey("scores[2]") != 85) {
            throw new AssertionError("Expected scores[2] to be 85");
        }

        System.out.println("testJsonWithList Passed");
    }

    public void testComplexJson() {
        String json = "{\"students\":[{\"name\":\"A\"},{\"name\":\"B\"}]}";
        JsonDataHandler handler = new JsonDataHandler();
        JsonData data = (JsonData)handler.extractJsonData(json);

        if(!"A".equals(data.getValueForKey("students[0].name"))){
            throw new AssertionError("Expected students[0].name to be A");
        }
        if(!"B".equals(data.getValueForKey("students[1].name"))){
            throw new AssertionError("Expected students[1].name to be B");
        }
        System.out.println("testJsonWithList Passed");
    }

    public void testTopLevelArray() {
        String json = "[{\"name\": \"Tommy\"}, {\"name\": \"Jerry\"}]";

        JsonDataHandler handler = new JsonDataHandler();
        Object data = handler.extractJsonData(json);
        if(data instanceof List){
            List<JsonData> jsonDataList = (List<JsonData>)data;
            String name1 =jsonDataList.get(0).getValueForKey("name");
            if(!name1.equals("Tommy")){
                throw new AssertionError("Expected name to be Tommy");
            }
            String name2 = jsonDataList.get(1).getValueForKey("name");
            if(!name2.equals("Jerry")){
                throw new AssertionError("Expected name to be Jerry");
            }
            System.out.println("testTopLevelArray Passed");
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        JsonDataTest dataTest = new JsonDataTest();

        for(Method method: JsonDataTest.class.getDeclaredMethods()){
            if(method.getParameterCount()==0 &&
                method.getReturnType()==void.class &&
                    Modifier.isPublic(method.getModifiers())){
                System.out.println("Invoking: "+ method.getName());
                method.invoke(dataTest);
            }
        }
    }
}
