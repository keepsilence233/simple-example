package qx.leizige.enums;

/**
 * 包含枚举值 (实现此接口可使用{@link qx.leizige.enums.EnumUtils}中的方法)
 *
 * @param <T>
 * @author leizige
 * 2022/03/20
 */
public interface ValueEnum<T> {


    /**
     * 获取枚举值
     *
     * @return enum value
     */
    T getValue();

}
