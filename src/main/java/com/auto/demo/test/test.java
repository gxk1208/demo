package com.auto.demo.test;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.auto.demo.dto.SimpFieldDto;
import com.auto.demo.entity.Config;
import com.auto.demo.param.CustomFieldLayoutParam;
import com.auto.demo.utils.Person;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.math3.optimization.general.ConjugateGradientFormula;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/4/20
 */
public class test {
    public static void main(String[] args) throws Exception {
        //String autoNo = getAutoNo(5, "{YYYYMMDD}-{0000}", "1");
        List<Object> objects2 = new ArrayList<>();
        objects2.add("11");
        objects2.add(22);
        String s5 = JSON.toJSONString(objects2);
        List<Object> objects3 = JSON.parseArray(s5, Object.class);
        Map<String, Object> maps = new HashMap<>();
        maps.put("pname1","val1");
        maps.put("pname2",123);
        String s4 = JSON.toJSONString(maps);
        System.out.println(s4);
        Map map2 = JSON.parseObject(s4, Map.class);
        s4 = s4.replace("{","[").replace("}","]");
        List<Object> objects1 = JSON.parseArray(s4, Object.class);
        SimpleDateFormat sdf22 = new SimpleDateFormat("yyy-MM-dd");



        Calendar  from  =  Calendar.getInstance();
        from.setTime(sdf22.parse("2020-08-07"));
        Calendar  to  =  Calendar.getInstance();
        to.setTime(sdf22.parse("2020-12-21"));
        int days = (int) ((to.getTime().getTime() - from.getTime().getTime()) / (1000 * 3600 * 24))+1;
        to.add(Calendar.DAY_OF_MONTH,1);
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH)+1;
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH)+1;
        int toDay = to.get(Calendar.DAY_OF_MONTH);

        int year = toYear  -  fromYear;
        int month = toMonth  - fromMonth;
        int day = toDay  - fromDay;
        if(0 > month){
            year = year -1;
            if(0 > day){
                month = toMonth+(12-fromMonth)-1;
                Calendar c = Calendar.getInstance();
                c.set(toYear, toMonth-1, 0);
                day = toDay + c.get(Calendar.DAY_OF_MONTH) - fromDay;
            }else{
                month = toMonth+(12-fromMonth);
            }
        }else{
            if(0 > day){
                month = month -1;
                Calendar c = Calendar.getInstance();
                c.set(toYear, toMonth-1, 0);
                day = toDay + c.get(Calendar.DAY_OF_MONTH) - fromDay;
            }
        }
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
         Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.set(Calendar.DATE,15);
        System.out.println(sdf22.format(cal1.getTime()));


        //caldate();

        //objectTransMap();


        //transfromMap();
        Person person = new Person();
        person.setAdd0003("333");
        person.setAdd0004("444");
        String s3 = JSON.toJSONString(person);
        System.out.println(s3);
        Map map1 = JSON.parseObject(s3, Map.class);
        System.out.println(map1);
        Map<String, Object> map0001 = new HashMap<>();
        map0001.put("add001","111");
        map0001.put("add003","333");
        String s2 = JSON.toJSONString(map0001);
        System.out.println(s2);
        String json = "{\n" +
                "                    \"name\": \"节点1-1\",\n" +
                "                    \"sonConfigs\": []\n" +
                "                }";
        Map map = JSON.parseObject(json, Map.class);
        System.out.println(map.get("sonConfigs"));

        List<Config> configs1 = new ArrayList<>();
        for(int i = 1; i<=3; i++){
            for(int  j= 1; j<=3; j++) {
                Config config1 = new Config();
                config1.setName("节点" + i + "-" + j);
                config1.setSonConfigs(new ArrayList<>());
                configs1.add(config1);
            }
        }

        Config config = new Config();
        List<Config> configs = new ArrayList<>();
        for(int i = 1; i<=3; i++){
            Config config1 = new Config();
            config1.setName("节点"+i);
            config1.setSonConfigs(configs1);
            configs.add(config1);
        }


        config.setName("根节点");
        config.setSonConfigs(configs);
        String s1 = JSON.toJSONString(config);
        System.out.println(s1);
//        ArrayList<Object> objects = new ArrayList<>();
//        objects.add(null);
//        objects.add(1);
//        objects.add(2);
//        objects.add(null);
//        objects.add(null);
//        System.out.println(objects.toString());
//        //true为含有特殊符号标点符号以及空格
//        System.out.println(isSpecialChar(" "));
//        System.out.println(getPingYin("1.12345你好"));
//        System.out.println(getPingYin("pinyin你好"));
//        System.out.println(getPingYin("PINYIN---"));
//
//
//        StringBuilder s = new StringBuilder("123456789");
//        s.delete(s.length()-2,s.length()-1);
//        System.out.println(s.toString());
//        String s1 = "20200525-0001";
//
//        String head = s1.substring(0, 8);
//        String bottom = s1.substring(9, 13);
//        String bottom1 = s1.substring(s1.length()-4, s1.length());
//        System.out.println(head);
//        System.out.println(bottom);
//        System.out.println(bottom1);
//        System.out.println(String.format("%0" + 4 + "d",Integer.parseInt(bottom)+1));
        SimpleDateFormat sdf = new SimpleDateFormat("yymmddhhmmss");
        String format = sdf.format(new Date());

        String s = "zgb{yy{{{{yy}11{DD}1110000{yyyy}{0001}";
//        char[] chars = s.toCharArray();
//        ArrayList<String> ss = new ArrayList<>();
//        for(int i =0;i<chars.length;i++){
//            String s1 = String.valueOf(chars[i]);
//            if("{".equals(s1)){
//                String s2  = String.valueOf(chars[i+1]);
//            }
//        }
        String express = "\\{(\\w+)\\}";
        Matcher match = Pattern.compile(express).matcher(s);
        while (match.find()) {
           // System.out.println(match.group(1));
            String group = match.group(1);
            System.out.println(group);
            SimpleDateFormat sdf1 = new SimpleDateFormat(group);
            String format1 = sdf1.format(new Date());
            s = s.replace("{"+group+"}",format1);
        }
        //System.out.println(s);
        ArrayList<SimpFieldDto> objects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SimpFieldDto simpFieldDto = new SimpFieldDto();
            simpFieldDto.setId(i);
            simpFieldDto.setName("e"+i);
            simpFieldDto.setIsVisible(true);
            objects.add(simpFieldDto);
        }
        System.out.println(JSON.toJSONString(objects));
        SimpleDateFormat sdf1  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println( sdf1.format(new Date(System.currentTimeMillis())));
        System.out.println(new Date());

        SimpleDateFormat sdf2  = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf3  = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = sdf2.format(new Date());
        System.out.println(format1);

        List<SimpFieldDto> simpFieldDtos = JSON.parseArray("[]", SimpFieldDto.class);
        System.out.println(1);
    }

    /**
     * 将YYYYMMDD HHIISS转换为yyyyMMdd HHmmss
     * @param group
     * @return
     */
    public static String convertDate(String group){
        String s = group.replace("Y", "y");
        // YYYY-MM-DD HH-II-SS > yyyy-MM-dd HH-mm:ss
        s = s.replace("D", "d");
        s = s.replace("I", "m");
        s = s.replace("S", "s");
        return  s;
    }

    /**
     * 获取自增编号
     * @param id 字段id
     * @param s 自增编号的格式
     * @param type 自增编号的归0规则
     * @return
     */
    public String getAutoNo(Integer id, String s, String type){
        String express = "\\{(\\w+)\\}";
        Matcher match = Pattern.compile(express).matcher(s);
        while (match.find()) {
            String group = match.group(1);
            if(!isDigit(group)){
                String date = convertDate(group);
                SimpleDateFormat sdf = new SimpleDateFormat(date);
                String format = sdf.format(new Date());
                s = s.replace("{"+date+"}",format);
            }
                s = s.replace("{"+group+"}",String.format("%0" + group.length() + "d",1));
        }
        return s;
    }


    private static void caldate() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH)-1;
        int year = cal.get(Calendar.YEAR);
        List<String> months = new ArrayList<>();
        Map<String,Integer> number = new HashMap<>();
        for(int i = 0; i<36; i++){
            String time = month<10?year+"-0"+month:year+"-"+month;
            months.add(time);
            number.put(time,0);
            month++;
            if(12 == month){
                month = 1;
                year++;
            }
        }
    }

    private static void objectTransMap() {
        String s11 = "[\"1\",\"0\",\"1\"]";
        List<String> strings1 = JSON.parseArray(s11, String.class);
        System.out.println(strings1.toString());
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(6);
        List<String> list =  new ArrayList<>();
        list.add("1,23,33");
        list.add("44,55,66");
        Iterator<String> iterator1 = list.iterator();
        while (iterator1.hasNext()){
            boolean flag = false;
            String[] next = iterator1.next().split(",");
            List<String> strings = Arrays.asList(next);
            for (Integer id : ids) {
                if(strings.contains(id.toString()+",")){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                iterator1.remove();
            }
            System.out.println(list.toString());
        }
    }

    private static void transfromMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            hashMap.put("keY" + i, "valuE-" + i);
        }
        System.out.println(hashMap.toString());
        HashMap<String, String> newMap = new HashMap<>();
        Iterator<Map.Entry<String, String>> iterator = hashMap.entrySet().iterator();
        Map.Entry<String, String> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            char[] chars = entry.getKey().toCharArray();
            boolean flag = false;
            String a = null;
            for (char c : chars) {
                if ((char)c>='A'&&(char)c<='Z') {
                    String s = String.valueOf(c);
                    a = entry.getKey().replace(s,"_"+s.toLowerCase());
                    flag = true;
                }

            }
            if(flag){
                hashMap.put(a, entry.getValue());
                //iterator.remove();
            }
        }
        System.out.println("结果：");
        System.out.println(newMap.toString());
        System.out.println(hashMap.toString());
    }

    /**
     * 获取每月的天数
     */
    private static void getDayNum() {
        int actualMaximum = Calendar.getInstance(Locale.CHINA).getActualMaximum(Calendar.DATE);
        System.out.println(actualMaximum);
    }

    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    // 将汉字转换为全拼
    public static String getPingYin(String src) {

        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += Character.toString(t1[i]);
                }
            }
            // System.out.println(t4);
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    //判断字符串都为数字
    public static boolean isDigit(String strNum){
        return strNum.matches("[0-9]{1,}");
    }
}
