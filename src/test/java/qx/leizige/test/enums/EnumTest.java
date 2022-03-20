package qx.leizige.test.enums;

import org.junit.Assert;
import org.junit.Test;
import qx.leizige.enums.EnumUtils;
import qx.leizige.test.BaseTest;

/**
 * 枚举工具类测试
 * @author leizige
 * 2022/03/20
 */
public class EnumTest extends BaseTest {


    @Test
    public void isExit(){
        Assert.assertTrue(EnumUtils.isExist(OrderStatusEnum.values(),0));
        Assert.assertTrue(EnumUtils.isExist(OrderStatusEnum.values(),1));
        boolean exist1 = EnumUtils.isExist(OrderStatusEnum.values(), null);
        System.out.println("exist1 = " + exist1);



        Assert.assertTrue(EnumUtils.isExist(OrderStatusEnum.class,0));
        Assert.assertTrue(EnumUtils.isExist(OrderStatusEnum.class,1));
        boolean exist2 = EnumUtils.isExist(OrderStatusEnum.class, null);
        System.out.println("exist2 = " + exist2);
    }


    @Test
    public void getNameByValue(){
        String enable = EnumUtils.getNameByValue(OrderStatusEnum.values(), 0);
        String disable = EnumUtils.getNameByValue(OrderStatusEnum.values(), 1);
        String other = EnumUtils.getNameByValue(OrderStatusEnum.values(), 3);

        System.out.println("0 = " + enable);
        System.out.println("1 = " + disable);
        System.out.println("3 = " + other);
    }

    @Test
    public void getValueByName(){
        Integer enable = EnumUtils.getValueByName(OrderStatusEnum.values(), "启用");
        Integer disable = EnumUtils.getValueByName(OrderStatusEnum.values(), "禁用");

        System.out.println("启用 = " + enable);
        System.out.println("禁用 = " + disable);
    }


    @Test
    public void getEnumByValue(){
        OrderStatusEnum orderStatusEnum = EnumUtils.getEnumByValue(OrderStatusEnum.values(), 0);
        System.out.println("\"name\" = " + orderStatusEnum.getName());
        System.out.println("\"value\" = " + orderStatusEnum.getValue());
    }

}
