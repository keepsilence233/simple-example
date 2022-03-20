package qx.leizige.enums;

/**
 * 带有枚举值和枚举名称的枚举接口 (可使用{@link qx.leizige.enums.EnumUtils} 中的方法操作)
 *
 * @param <T>
 * @author leizige
 * 2022/03/20
 */
public interface NameValueEnum<T> extends ValueEnum<T> {

    /**
     * 获取枚举描述
     *
     * @return enum name
     */
    String getName();


}
