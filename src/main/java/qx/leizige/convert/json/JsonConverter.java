package qx.leizige.convert.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.parser.Feature;
import qx.leizige.convert.utils.StringPool;

import java.util.Map;

;

/**
 * <p>json模板转换器，</p>
 * <p>配置示例请查看jsonTemplate.json</p>
 * <p>每个key的配置格式如下 </p>
 * <p>新的key(必填):基本类型(选填)::旧的全路径key(必填):基本类型(选填)::自定义转换器(选填,暂未实现)</p>
 * <p>newProp:int::oldProp:short::convertShort</p>
 * <p>一般用以下方式配置即可</p>
 * <p>newProp::oldProp</p>
 * <p>每个key对应的值，用默认值，例如数字用0，字符串用""</p>
 * <p>如果key对应的值是对象或数组,则key只写新的key即可,例如</p>
 * <p>"newProp":{}</p>
 * <p>旧的全路径key:即是items[0].item.receiveArea这种格式</p>
 */
public class JsonConverter implements StringPool {

    /**
     * 类型转换入口方法
     *
     * @param jsonTemplate   转换模板（配置方式请参考jsonTemplate.json）
     * @param requestOldJson 源json文件
     * @return 要生成的json对象
     */
    public static JSON rebuildJson(String jsonTemplate, String requestOldJson) {
        if (!isJsonObj(requestOldJson)) {
            throw new IllegalArgumentException("oldJson formal error!");
        }
        Object obj = JSON.parse(requestOldJson);
        if (obj instanceof JSONObject) {
            return buildJsonObject(jsonTemplate, obj);
        } else if (obj instanceof JSONArray) {
            JSONArray jsonArrayRebuildResult = new JSONArray();
            JSONArray jsonArrayObject = (JSONArray) obj;
            for (Object object : jsonArrayObject) {
                JSONObject jsonObject = buildJsonObject(jsonTemplate, object);
                jsonArrayRebuildResult.add(jsonObject);
            }
            return jsonArrayRebuildResult;
        } else {
            throw new IllegalArgumentException("unknown object type!");
        }
    }

    /**
     * 构建类型为为jsonObject的对象
     *
     * @param jsonTemplate json转换模版
     * @param obj          源json对象
     * @return jsonObject
     */
    private static JSONObject buildJsonObject(String jsonTemplate, Object obj) {
        JSONObject jsonRebuildResult = new JSONObject(16, true);
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject jsonFormatTemplate = (JSONObject) JSONObject.parse(jsonTemplate, Feature.OrderedField);
        transform(jsonRebuildResult, jsonFormatTemplate, jsonObject);
        return jsonRebuildResult;
    }


    /**
     * 类型转换器核心方法 根据模板和json对象生成新的json对象
     *
     * @param targetResult   将要生成的json对象
     * @param formatTemplate 转换模板（配置方式请参考jsonTemplate.json）
     * @param srcJsonObj     源json文件
     *                       //     * @param index          集合所在下标 example 1::2
     * @return 要生成的json对象
     */
    private static JSONObject transform(JSONObject targetResult, JSONObject formatTemplate, JSONObject srcJsonObj) {
        for (Map.Entry<String, Object> entry : formatTemplate.entrySet()) {
            String mixtureKey = entry.getKey();
            Object value = entry.getValue();

            // 如果只有一个key，则默认为new key，有value则默认为new value
            if (!mixtureKey.contains("::")) {
                targetResult.put(mixtureKey, value);
                continue;
            }
            String[] mixtureKeyArr = mixtureKey.split("::");
            String[] newDataArr = mixtureKeyArr[0].split(":");
            String oldDataArrayName = mixtureKeyArr[1].split(":")[0];

            if (value instanceof JSONObject) {
                JSONObject subJsonResult = new JSONObject();
                targetResult.fluentPut(newDataArr[0], subJsonResult);
                transform(subJsonResult, (JSONObject) value, srcJsonObj);
            } else if (value instanceof JSONArray) {
                JSONArray subJsonArrResult = new JSONArray();
                targetResult.put(newDataArr[0], subJsonArrResult);

                JSONArray old_valueJsonArray = (JSONArray) value;
                JSONArray new_valueJsonArray = rebuildJsonArray(old_valueJsonArray, srcJsonObj, oldDataArrayName);

                for (Object subVal : new_valueJsonArray) {
                    if (subVal instanceof JSONObject) {
                        JSONObject subJsonResult = new JSONObject();
                        transform(subJsonResult, (JSONObject) subVal, srcJsonObj);
                        subJsonArrResult.add(subJsonResult);
                    }
                }
            } else {
                String[] oldDataArr = mixtureKeyArr[1].split(":");
                String oldStr = oldDataArr[0];
                Object oldVal = JSONPath.read(srcJsonObj.toString(), oldStr);
                String targetDataType = "String";

                // 如果new key有配置key的类型，则进行赋值，否则默认为String
                if (newDataArr.length == 2) {
                    targetDataType = newDataArr[1];
                } else {
                    //推断类型
                    if (value != null) {
                        targetDataType = value.getClass().getSimpleName();
                    } else if (oldVal != null) {
                        targetDataType = oldVal.getClass().getSimpleName();
                    }
                }
                // 如果通过JSONPath.read取到的value为null时，则填为默认值
                Object newVal = new TypeConvertor().convertTo(targetDataType, oldVal != null ? oldVal : value);
                targetResult.fluentPut(newDataArr[0], newVal);
            }
        }
        return targetResult;
    }


    /**
     * 根据源json文件构建新的json数组
     *
     * @param old_valueJsonArray 旧的json数组
     * @param srcJsonObj         源json文件
     * @param oldDataArrayName   旧的json数组名称
     * @return 新构建的json数组，size = 源json文件中json数组的长度
     */
    private static JSONArray rebuildJsonArray(JSONArray old_valueJsonArray, JSONObject srcJsonObj, String oldDataArrayName) {
        JSONArray new_valueJsonArray = new JSONArray();
        for (int i = 0; i < JSONPath.size(srcJsonObj.toString(), ROOT_OBJECT + oldDataArrayName); i++) {
            int final_index = i;
            old_valueJsonArray.forEach(valueJson -> {
                JSONObject old_jsonObject = (JSONObject) valueJson;
                JSONObject new_jsonObject = new JSONObject();
                for (Map.Entry<String, Object> item : old_jsonObject.entrySet()) {
                    String key = item.getKey();
                    Object val = item.getValue();
                    if (key.contains("[") && key.contains("]")) {
                        key = key.replace(PLACEHOLDER, String.valueOf(final_index));
                        new_jsonObject.put(key, val);
                    }
                }
                new_valueJsonArray.add(new_jsonObject);
            });
        }
        return new_valueJsonArray;
    }


    /**
     * 判断是否为源json是否为json字符串
     *
     * @param json jsonStr
     * @return boolean
     */
    public static boolean isJsonObj(String json) {
        try {
            JSONObject.parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
