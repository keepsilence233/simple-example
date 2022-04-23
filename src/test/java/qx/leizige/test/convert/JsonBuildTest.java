package qx.leizige.test.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import qx.leizige.convert.build.TemplateEntity;
import qx.leizige.test.BaseTest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class JsonBuildTest extends BaseTest {


    @Test
    public void test() {

        TemplateEntity entity = TemplateEntity.builder().oldName("items").newName("itemList").array(true).build();
        LinkedList<TemplateEntity> linkedList = new LinkedList<>();
        linkedList.add(TemplateEntity.builder().oldName("i_id").newName("id").array(false).array(false).build());
        linkedList.add(TemplateEntity.builder().oldName("name").newName("itemName").array(false).array(false).build());
        linkedList.add(TemplateEntity.builder().oldName("sku_id").newName("skuId").array(false).array(false).build());
        entity.setTemplateList(linkedList);


        String s = buildJsonTemplate(Collections.singletonList(entity));
        System.out.println(s);
    }


    private String buildJsonTemplate(List<TemplateEntity> entityList) {
        JSONObject jsonObject = new JSONObject();

        entityList.forEach(entity -> {
            String key = entity.getOldName() + "::" + entity.getNewName();
            if (entity.getTemplateList().isEmpty()) {
                jsonObject.put(key, "");
            } else {
                JSONArray array = buildJsonArrayValue(entity.getNewName(), entity.getTemplateList());
                jsonObject.put(key, array);
            }
        });
        return jsonObject.toJSONString();
    }

    private JSONArray buildJsonArrayValue(String parentName, List<TemplateEntity> entityList) {
        JSONArray jsonArray = new JSONArray();
        entityList.forEach(entity -> {
            JSONObject jsonObject = new JSONObject();
            String key = entity.getOldName() + "::" + parentName + "[${_index}]" + entity.getNewName();
            jsonObject.put(key, "");
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }

    /**
     * 去除首尾指定字符
     *
     * @param str     字符串
     * @param element 指定字符
     * @return
     */
    private static String trimFirstAndLastChar(String str, String element) {
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do {
            int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
            int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
            str = str.substring(beginIndex, endIndex);
            beginIndexFlag = (str.indexOf(element) == 0);
            endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
        } while (beginIndexFlag || endIndexFlag);
        return str;
    }

}
