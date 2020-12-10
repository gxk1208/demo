package com.auto.demo.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对象工具类
 * @author ShanQiang
 */
public class BeanUtil {
    /**
     * bean转map
     *
     * @param bean 实体
     * @return
     * @throws Exception
     */
    public static Map<String, Object> bean2map(Object bean) throws Exception {
        if(bean == null){
            return new HashMap<String, Object>(16);
        }
        Map<String, Object> map = new HashMap<>(16);
        BeanInfo info = Introspector.getBeanInfo(bean.getClass(), Object.class);
        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            Object value = pd.getReadMethod().invoke(bean);
            map.put(String.valueOf(pd.getName()), value);
        }
        return map;
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
