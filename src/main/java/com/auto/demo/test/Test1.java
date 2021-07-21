package com.auto.demo.test;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.auto.demo.entity.AnyContains01;
import com.auto.demo.entity.AnyContains02;
import com.auto.demo.entity.SelfField;
import com.auto.demo.utils.BeanUtil;
import com.auto.demo.utils.UUIDUtil;
import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/7/21 11:22
 */
@Data
public class Test1 {
    public static void main(String[] args) throws Exception {
        //计算两日期相差天数();
        // 标识转换时间();
         //map字母大写转_小写();
        //bigDecimal转double取绝对值();
        //时分秒清零();
        //java8流();
        //判断特殊字符();
        //copyProperties方法null值覆盖();
        //两对象互为彼此元素_栈内存溢出();
        //stringBuffer_insert方法
        //工具类convert();
        //两日期相差x小时y分钟();
         //某一日期前或后的日期();
         //时间戳转字符串转日期();
        List<String> str = new ArrayList<>();
        List<String> len1 = new ArrayList<>();
        List<String> len2 = new ArrayList<>();
        for(int i = 0; i < 20; i++){
           String uuid = UUIDUtil.getUUID();
           if(i == 0){
               len1.add(uuid);
           }else{
               if(uuid.length() == len1.get(0).length()){
                   len1.add(uuid);
               }else{
                   len2.add(uuid);
               }
           }
        }

        System.out.println(str);
    }

    private static void 时间戳转字符串转日期() {
        String l = "1602295650785";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SS");
        String format = sdf.format(new Date(Long.parseLong(l)));
        System.out.println(format);
    }

    private static void 某一日期前或后的日期() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse("2020-10-31");
        Calendar instance = Calendar.getInstance();
        instance.setTime(parse);
        instance.add(Calendar.MONTH,-1);
        Date time = instance.getTime();
        System.out.println(time.toString());
        System.out.println(parse.toString());
    }

    private static void 两日期相差x小时y分钟() throws ParseException {
        long l = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long time = sdf.parse("2020-10-11 11:11").getTime();
        long minutes = (l-time)/1000/60;
        long hours = minutes/60;
        long minute = minutes%60;
        System.out.println(hours+"小时"+minute+"分钟");
    }

    private static void 工具类convert() {
        String date = "20200501";
        Date date1 = Convert.toDate(date);
        System.out.println(date1);
    }

    private static void stringBuffer_insert方法() {
        StringBuilder str1 = new StringBuilder();
        StringBuffer str2 = new StringBuffer();
        str1.append("1").append("2");
        str2.insert(0,"2").insert(1,"4").insert(0,"3");
    }

    private static void 两对象互为彼此元素_栈内存溢出() {
        AnyContains01 anyContains01 = new AnyContains01();
        AnyContains02 anyContains02 = new AnyContains02();
        anyContains01.setId(1);
        anyContains02.setId(2);
        anyContains01.setAc02(anyContains02);
        anyContains02.setAc01(anyContains01);
        System.out.println(anyContains01.toString());
    }

    private static void copyProperties方法null值覆盖() {
        SelfField field1 = new SelfField();
        SelfField field2 = new SelfField();
        field1.setId(1);
        field1.setType(1);
        field2.setId(2);
        field2.setEntityId(3);
        //空值覆盖  field2.entityId为null
        BeanUtils.copyProperties(field1, field2);
        System.out.println(field2.getEntityId());

        SelfField field3 = new SelfField();
        SelfField field4 = new SelfField();
        field3.setId(3);
        field3.setType(1);
        field4.setId(4);
        field4.setEntityId(3);
        //空值未覆盖  field2.entityId依旧为3
        BeanUtils.copyProperties(field1, field2, BeanUtil.getNullPropertyNames(field1));
        System.out.println(field4.getEntityId());
    }

    private static void 判断特殊字符() {
        String str = null;
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        m.find();
        System.out.println(m.find());
    }

    private static void java8流() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        System.out.println(strings.toString());
        StringBuilder str = new StringBuilder();
        strings.forEach(s -> str.append(s).append(","));
        System.out.println(str.toString());
        List<String> filtered = strings.stream().filter(string -> string.contains("a")).collect(Collectors.toList());
        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        strings = strings.stream().filter(string -> string.length() > 3).collect(Collectors.toList());
        System.out.println(strings);
        List<Integer> integers = Arrays.asList(22, 10, 3, 1, 2, 5, 99);
        List<Integer> sorted = integers.stream().sorted().collect(Collectors.toList());
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
// 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map(i -> i * i).collect(Collectors.toList());
        List<String> strings1 = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
// 获取空字符串的数量
        long count1 = strings1.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println(filtered);
    }

    private static void 时分秒清零() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        long time = instance.getTime().getTime();
        Date time1 = instance.getTime();
        long timeInMillis = instance.getTimeInMillis();
        System.out.println(time);
    }

    private static void bigDecimal转double取绝对值() {
        BigDecimal bigDecimal = new BigDecimal("-10.2");
        System.out.println(bigDecimal);
        System.out.println(bigDecimal.doubleValue());
        System.out.println(Math.abs(bigDecimal.doubleValue()));
    }

    private static void map字母大写转_小写() {
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", "AAdBBdCC");
        map.put("Abc", 12);
        map.put("baCC", 13);
        map.put("bcBaBc", 14);
        /*String s = JSON.toJSONString(map);
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if ((char) c >= 'A' && (char) c <= 'Z') {
                String a = String.valueOf(c);
                s = s.replace(a, "_" + a.toLowerCase());
            }
        }
        map = JSON.parseObject(s, Map.class);
        System.out.println(map);*/
        /*Map<String, Object> tmap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        Map.Entry<String, Object> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            char[] chars = entry.getKey().toCharArray();
            String a = entry.getKey();
            for (char c : chars) {
                if ((char) c >= 'A' && (char) c <= 'Z') {
                    String s = String.valueOf(c);
                    a = entry.getKey().replace(s, "_" + s.toLowerCase());
                    break;
                } else {
                    a = entry.getKey();
                }
            }

            tmap.put(a, entry.getValue());
            iterator.remove();
        }
        map.putAll(tmap);*/

        /*方式二*/
        Map<String, Object> result = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            char[] keyChars = key.toCharArray();
            StringBuilder str = new StringBuilder();
            for (char c : keyChars) {
                String newKey = String.valueOf(c);
                if ((char) c >= 'A' && (char) c <= 'Z') {
                     newKey = "_"+newKey.toLowerCase();
                }
                str.append(newKey);
            }
            result.put(str.toString(),map.get(key));
        }
        System.out.println(result);
    }

    private static void 计算两日期相差天数() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse1 = sdf.parse("2020-07-29");
        Date parse = sdf.parse("2009-07-29");
        long l = System.currentTimeMillis();
        long time1 = parse1.getTime();
        long time = parse.getTime();
        long s = l - time;
        s = s / 1000;
        s = s / 3600;
        s = s / 24;
        long day = (l - time) / (1000 * 3600 * 24) + 1;
        System.out.println("已逾期" + day + "天");
    }

    private static void 标识转换时间() {
        String s = "{YYYYMMDD}-{0000}";
        String express = "\\{(\\w+)\\}";
        Matcher match = Pattern.compile(express).matcher(s);
        while (match.find()) {
            String group = match.group(1);
            if (!group.matches("[0-9]{1,}")) {
                String date = group.replace("Y", "y");
                // YYYY-MM-DD HH-II-SS > yyyy-MM-dd HH-mm:ss
                date = date.replace("D", "d");
                date = date.replace("I", "m");
                date = date.replace("S", "s");
                SimpleDateFormat sdf = new SimpleDateFormat(date);
                String format = sdf.format(new Date());
                s = s.replace("{" + group + "}", format);
            }
            s = s.replace("{" + group + "}", String.format("%0" + group.length() + "d", 1));
            System.out.println(s);
        }
    }

}
