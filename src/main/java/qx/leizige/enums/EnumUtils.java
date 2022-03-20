package qx.leizige.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 枚举工具类
 * 使用该工具类需要指定枚举实现{@link qx.leizige.enums.ValueEnum>} OR {@link qx.leizige.enums.NameValueEnum}
 *
 * @author leizige
 * 2022/03/20
 */
public class EnumUtils {

    /**
     * 判断枚举值是否存在于指定枚举数组中
     *
     * @param enums 枚举数组
     * @param value 枚举值
     * @return 是否存在
     */
    public static <T> boolean isExist(ValueEnum<T>[] enums, T value) {
        if (value == null) return Boolean.FALSE;
        return Arrays.stream(enums).anyMatch(v -> v.getValue().equals(value));
    }

    /**
     * 判断枚举值是否存与指定枚举类中
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <E>       枚举类型
     * @param <V>       值类型
     * @return true：存在
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<V>>, V> boolean isExist(Class<E> enumClass, V value) {
        if (value == null) return Boolean.FALSE;
        return Arrays.stream(enumClass.getEnumConstants()).map(v -> (ValueEnum<V>) v).anyMatch(v -> v.getValue().equals(value));
    }

    /**
     * 根据枚举值获取其对应的名字
     *
     * @param enums 枚举列表
     * @param value 枚举值
     * @return 枚举名称
     */
    public static <T> String getNameByValue(NameValueEnum<T>[] enums, T value) {
        if (value == null) return null;
        return Arrays.stream(enums).filter(v -> v.getValue().equals(value)).findFirst().map(NameValueEnum::getName).orElse(null);
    }

    /**
     * 根据枚举名称获取对应的枚举值
     *
     * @param enums 枚举列表
     * @param name  枚举名
     * @return 枚举值
     */
    public static <T> T getValueByName(NameValueEnum<T>[] enums, String name) {
        if (StringUtils.isEmpty(name)) return null;
        return Arrays.stream(enums).filter(v -> v.getName().equals(name)).findFirst().map(NameValueEnum::getValue).orElse(null);
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enums 枚举列表
     * @return 枚举对象
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<V>>, V> E getEnumByValue(E[] enums, V value) {
        return Arrays.stream(enums).filter(v -> ((ValueEnum<V>) v).getValue().equals(value)).findFirst().orElse(null);
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enumClass 枚举class
     * @return 枚举对象
     */
    public static <E extends Enum<? extends ValueEnum<V>>, V> E getEnumByValue(Class<E> enumClass, V value) {
        return getEnumByValue(enumClass.getEnumConstants(), value);
    }
}
