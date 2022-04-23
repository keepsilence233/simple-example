package qx.leizige.test.convert;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import qx.leizige.convert.json.JsonConverter;
import qx.leizige.test.BaseTest;

/**
 * 测试json转换工具
 */
public class JsonConvertTest extends BaseTest {


    String jsonTemplate = "{\n" +
            "  \"items::itemList\": [\n" +
            "    {\n" +
            "      \"i_id::itemList[${_index}].id\": \"\",\n" +
            "      \"name::itemList[${_index}].itemName\": \"\",\n" +
            "      \"sku_id::itemList[${_index}].skuId\": \"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    String oldJsonStr = "{\n" +
            "    \"itemList\":[\n" +
            "        {\n" +
            "            \"id\":\"Test-20211109\",\n" +
            "            \"itemName\":\"奈飞夹克\",\n" +
            "            \"skuId\":\"Test-001\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Test
    public void jsonObjectTest() {
        JSON json = JsonConverter.rebuildJson(jsonTemplate, oldJsonStr);
        System.out.println(json.toJSONString());
    }



    String oldJsonArrayStr = "[\n" +
            "    {\n" +
            "        \"itemList\":[\n" +
            "            {\n" +
            "                \"id\":\"Test-20211109\",\n" +
            "                \"itemName\":\"奈飞夹克\",\n" +
            "                \"skuId\":\"Test-001\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"itemList\":[\n" +
            "            {\n" +
            "                \"id\":\"Test-20211108\",\n" +
            "                \"itemName\":\"耐克球鞋\",\n" +
            "                \"skuId\":\"Test-002\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]";

    @Test
    public void jsonArrayTest() {
        JSON json = JsonConverter.rebuildJson(jsonTemplate, oldJsonArrayStr);
        System.out.println(JSON.toJSONString(json,true));
    }


    @Override
    public void before() {
        super.before();
    }
}
