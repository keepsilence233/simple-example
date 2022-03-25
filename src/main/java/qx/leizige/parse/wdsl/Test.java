package qx.leizige.parse.wdsl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import javax.wsdl.WSDLException;
import java.util.List;

/**
 * wsdl 解析
 */
public class Test {
    public static void main(String[] args) throws WSDLException {
        String wsdluri = "http://localhost:8080/webservice/weaverOA?wsdl";
        List<String> operations = Lists.newArrayList();
        WAWsdlUtil.getOperationList(wsdluri, operations);
        for (String operationName : operations) {
            System.out.println("-----------------operation----------------");
            System.out.println(operationName);
            List<ParameterInfo> parameterInfos = WAWsdlUtil.getMethodParams(wsdluri, operationName);
            printParams(parameterInfos, "");
            System.err.println(JSON.toJSONString(parameterInfos));
        }
    }

    private static void printParams(List<ParameterInfo> parameterInfos, String parentName) {
        if (parameterInfos != null) {
            for (ParameterInfo parameterInfo : parameterInfos) {
                System.out.println("parentname : " + parentName + " ; name : " + parameterInfo.getName() + " ; type :" +
                        " " + parameterInfo.getType() + " ;" +
                        " childtype : " + parameterInfo.getChildType());
                printParams(parameterInfo.getChildren(), parameterInfo.getName());
            }
        }
    }
}
