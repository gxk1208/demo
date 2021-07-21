package com.测试;

import com.auto.demo.dto.IotSceneUpdStatusDateDto;
import com.auto.demo.entity.SelfField;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/2/20 8:54
 */
public class ClassTest {
    public static void main(String[] args) throws Exception {
        ArrayList<String>  strings1 = new ArrayList<>();
        strings1.add(null);
        strings1.removeAll(Collections.singleton(null));
        System.out.println(strings1.toString());

        String s1 = new Date().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long time5 = sdf.parse("2021-03-28 17:45").getTime();

        long changeTime = time5 - System.currentTimeMillis();
            if(changeTime/1000<60){
                System.out.println("1分钟内");
            }else if(changeTime/(1000*60)<60){
                System.out.println(changeTime/(1000*60)+"分钟后");;
            }else if(changeTime/(1000*60*60)<60){
                System.out.println(changeTime/(1000*60*60)+"小时后");;
            }else{
                System.out.println("1天后"); ;
            }


        String sts = "2021-03-27 11:12";
        String[] s = sts.split(" ");

        System.out.println("11".compareTo("22"));


        Calendar instance = Calendar.getInstance();
        Date parse1 = sdf.parse("2021-03-25 23:50");
        instance.setTime(parse1);
        instance.add(Calendar.DAY_OF_MONTH,1);
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        long time3 = instance.getTime().getTime();
        long time4 = sdf.parse("2021-03-26 00:00").getTime();


        SimpleDateFormat sdfHM = new SimpleDateFormat("HH:mm");
        long timex = sdfHM.parse("08:00").getTime();
        long time0 = sdfHM.parse("09:11").getTime();
        long time1 = sdfHM.parse("12:10").getTime();
        long time2 = sdfHM.parse("23:10").getTime();


        List<IotSceneUpdStatusDateDto> dtos = new ArrayList<>();

        ArrayList<Date> list = new ArrayList<>();
        IotSceneUpdStatusDateDto dto1 = new IotSceneUpdStatusDateDto();
        dto1.setStatus(1);
        dto1.setUpdTime(sdf.parse("2023-02-05 05:01").getTime());
        dtos.add(dto1);
        IotSceneUpdStatusDateDto dto2 = new IotSceneUpdStatusDateDto();
        dto2.setStatus(0);
        dto2.setUpdTime(sdf.parse("2021-02-05 05:02").getTime());
        dtos.add(dto2);
        IotSceneUpdStatusDateDto dto3 = new IotSceneUpdStatusDateDto();
        dto3.setStatus(1);
        dto3.setUpdTime(sdf.parse("2021-02-03 05:01").getTime());
        dtos.add(dto3);
        IotSceneUpdStatusDateDto dto4 = new IotSceneUpdStatusDateDto();
        dto4.setStatus(0);
        dto4.setUpdTime(sdf.parse("2021-01-05 05:01").getTime());
        dtos.add(dto4);
        IotSceneUpdStatusDateDto dto5 = new IotSceneUpdStatusDateDto();
        dto5.setStatus(1);
        dto5.setUpdTime(sdf.parse("2022-02-05 05:01").getTime());
        dtos.add(dto5);
        list.add(sdf.parse("2023-02-05 05:01"));
        list.add(sdf.parse("2021-02-05 05:02"));
        list.add(sdf.parse("2021-02-03 05:01"));
        list.add(sdf.parse("2021-01-05 05:01"));
        list.add(sdf.parse("2022-02-05 05:01"));
        Collections.sort(list);
        Collections.sort(dtos, new Comparator<IotSceneUpdStatusDateDto>() {
            @Override
            public int compare(IotSceneUpdStatusDateDto o1, IotSceneUpdStatusDateDto o2) {
                return (int) (o2.getUpdTime()-o1.getUpdTime());
            }
        });
        System.out.println(list.toString());

        Integer l1 = 10003;
        long l2 = System.currentTimeMillis();
        if(l2 -l1 > 900000){
            System.out.println("long类型减");
        }
        System.out.println(l2);
        System.out.println(l1);
        System.out.println(l2 - l1);

        SelfField field = new SelfField();
        field.setEntityId(2);
        SelfField field1 = new SelfField();
        field1.setEntityId(2);
        boolean equals1 = Objects.equals(field.toString(), field1.toString());
        boolean equals = Objects.equals(field, field1);


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Period period = Period.between(LocalDate.parse("2020-03-15"), LocalDate.parse("2021-03-17"));

        StringBuffer sb = new StringBuffer();
        sb.append(period.getYears()).append(",")
                .append(period.getMonths()).append(",")
                .append(period.getDays());

        System.out.println(sb.toString());

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = calendar.getTime();



        Date date = sdf.parse("2021-03-01");
        date = new Date();
        String format = sdf.format(date);
        Date parse = sdf.parse(format);
        System.out.println(format.equals("2021-03-01"));

        //equalsNULL();
    }

    private static void equalsNULL() {
        String str = new String();
        Class clazz=str.getClass();
        System.out.println(clazz.getName());
        System.out.println(clazz.getSimpleName());

        String s = "zz";
        String a = null;

        System.out.println(a.equals(a));

        String name = "";

        File file;


    }
}
