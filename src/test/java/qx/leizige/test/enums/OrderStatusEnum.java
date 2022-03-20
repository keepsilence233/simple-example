package qx.leizige.test.enums;


import lombok.AllArgsConstructor;
import qx.leizige.enums.NameValueEnum;

@AllArgsConstructor
public enum OrderStatusEnum implements NameValueEnum<Integer> {


    ENABLE(0, "启用"),
    DISABLE(1, "禁用"),

    ;

    private final Integer value;
    private final String name;


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
