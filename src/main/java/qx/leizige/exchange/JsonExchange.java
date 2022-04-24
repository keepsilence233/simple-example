package qx.leizige.exchange;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.util.HashMap;
import java.util.Map;

public class JsonExchange {

    /**
     * json映射转换，返回转换后的json数据
     * @param jobj 源json数据
     * @param map  map的key为目标jsonPath，value为源jsonPath，需要换为实际的映射对象，并传入类型和值集转换相关信息
     * @return
     */
    public static JSONObject exchangeJson(JSONObject jobj, Map<String,String> map) {
        Map<String, String> ruleMap = convertRules(map);
        JSONObject newJson = new JSONObject();
        Map<String,Object> paths = JSONPath.paths(jobj);
        for (Map.Entry<String,Object> pathEntry : paths.entrySet()){
            for (Map.Entry<String,String> ruleEntry:ruleMap.entrySet()){
                if(pathEntry.getKey().matches(ruleEntry.getValue())){
                    String newPath = pathEntry.getKey().replaceAll(ruleEntry.getValue(),ruleEntry.getKey());
                    //获取转换后的value。此处进行类型转换和值集映射转换
                    Object newValue = pathEntry.getValue();
                    if(JSONPath.contains(newJson,newPath) && JSONPath.size(newJson,newPath) < 0){
                        //path下已经存在一个值，则需要将类型转换为jsonArray并添加第二个值
                        Object tmp = JSONPath.eval(newJson,newPath);
                        Object[] tArray = {tmp,newValue};
                        JSONPath.set(newJson, newPath, tArray);
                    }else if(JSONPath.size(newJson,newPath) > 1){
                        //path下已经存在数组对象，直接追加
                        JSONPath.arrayAdd(newJson, newPath, newValue);
                    }else{
                        //path不存在，直接添加对象
                        JSONPath.set(newJson, newPath, newValue);
                    }
                }
            }
        }
        return newJson;
    }


    /**
     * 从规则生成转换正则，主要针对目标jsonPath和源jsonPath
     * @param map
     * @return
     */
    private static Map<String, String> convertRules(Map<String, String> map) {
        Map<String,String> ruleMap = new HashMap<String, String>();
        for (Map.Entry<String,String> entry:map.entrySet()){
            //针对目标路径进行转换
            String key = "/"+ entry.getKey().replaceAll("\\[(\\d+)\\]","/\\$$1").replace(".","/");
            //针对源路径进行转换
            String value = "/"+ entry.getValue().replaceAll("\\[\\]","/(\\\\d+)").replace(".","/\\d*/*");
            ruleMap.put(key,value);
        }
        return ruleMap;
    }
}
