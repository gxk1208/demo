package com.auto.demo.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 处理排序
 *
 * @author 袁贵
 * @version 1.0
 * @since 1.0
 */
public class OrderParamUtil {

    static final String ASC = "asc";
    static final String DESC = "desc";
    static final String COMMA = ",";

    private OrderParamUtil() {
    }
    /**
     * 处理单字段排序
     *
     * @param sort
     * @param direction
     * @return
     */
    public static String toString(String sort, int direction) {

        if (StringUtils.isBlank(sort)) {
            return StringUtils.EMPTY;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(sort).append(StringUtils.SPACE).append(direction == 1 ? DESC : ASC);

        return builder.toString();
    }

    /**
     * 处理多字段排序
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * 可处理多字段排序
     * <br>
     * TODO 是否需要处理客户端传递过来的驼峰形式的字段名
     *
     * @author yuan'gui
     * @version 1.0
     * @since 2019年7月5日
     */
    public static class Builder {

        Map<String, String> map = new LinkedHashMap<>();

        public Builder append(String sort, int direction) {
            map.put(sort, direction == 1 ? DESC : ASC);
            return this;
        }

        public String build() {
            if (map.isEmpty()) {
                return StringUtils.EMPTY;
            }
            StringBuilder builder = new StringBuilder();
            for (Entry<String, String> kv : map.entrySet()) {
                if (builder.length() > 0) {
                    builder.append(COMMA);
                }
                builder.append(kv.getKey()).append(StringUtils.SPACE).append(kv.getValue());
            }
            return builder.toString();
        }
    }


}
