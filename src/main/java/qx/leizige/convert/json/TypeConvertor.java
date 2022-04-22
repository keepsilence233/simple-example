package qx.leizige.convert.json;

import lombok.AllArgsConstructor;
import lombok.Setter;
import qx.leizige.convert.utils.TimeUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalTime;


@AllArgsConstructor
public class TypeConvertor {

    @Setter
    private String timestampPattern;

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Empty constructor of {@link TypeConvertor}, with <code>timestampPattern</code> specified
     * as <code>yyyy-MM-dd HH:mm:ss</code>.
     *
     * <p>
     * <strong>Note:</strong> if you wanna date time with other pattern, using constructor with pattern passed-in.
     * </p>
     *
     * @see TypeConvertor#TypeConvertor(String)
     */
    public TypeConvertor() {
        this(DATE_TIME_PATTERN);
    }

    /**
     * 类型转换器
     *
     * @param targetTypeName 要转成的类型
     * @param val            值
     * @return
     */
    public Object convertTo(String targetTypeName, Object val) {

        Object newVal;
        switch (targetTypeName) {
            /**需要转换的基本类型**/
            case "short":
            case "Short":
                newVal = Short.parseShort(val.toString().trim());
                break;
            case "int":
            case "Integer":
                newVal = Integer.parseInt(val.toString().trim());
                break;
            case "long":
            case "Long":
                newVal = Long.parseLong(val.toString().trim());
                break;
            case "float":
            case "Float":
                newVal = Float.parseFloat(val.toString().trim());
                break;
            case "double":
            case "Double":
                newVal = Double.parseDouble(val.toString().trim());
                break;
            case "boolean":
            case "Boolean":
                newVal = Boolean.parseBoolean(val.toString().trim());
                break;
            case "BigDecimal":
                newVal = new BigDecimal(val.toString().trim());
                break;
            case "BigInteger":
                newVal = new BigInteger(val.toString().trim());
                break;
            /**日期时间**/
            case "LocalDate":
                newVal = TimeUtils.toLocalDate(val.toString().trim());
                break;
            case "LocalTime":
                newVal = LocalTime.parse(val.toString().trim());
                break;
            case "LocalDateTime":
                newVal = TimeUtils.toLocalDateTime(val.toString().trim());
                break;
            default:
                newVal = val;
                break;
        }

        return newVal;
    }
}
