package com.auto.demo.test;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/21 10:45
 */

public class CollectionTest {
    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(2);
        objects.add(3);
        System.out.println();
        Object[] objects1 = objects.toArray();
        System.out.println(objects1.length);
        System.out.println(objects1[objects1.length-1]);
        // testCapacity();

    }

    private static void testCapacity() {
        int x = 1;
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("初始"+list.size()+"------"+getArrayListCapacity(list));
        list.add(1);
        Integer remove = list.remove(x);
        System.out.println("添加"+list.size()+"------"+getArrayListCapacity(list));
        list.clear();
        System.out.println("清空"+list.size()+"------"+getArrayListCapacity(list));

        for (int i = 0; i < 6; i++) {
            list.add(i+1);
            System.out.println(i+1+"-"+list.size()+"------"+getElementData(list));
        }

        for (int i = 1; i < 10; i++) {
            System.out.println("ArrayList第"+i+"次扩增后elementData.length--------"+getSum(i));
        }
    }

    /**
     * 无参构造容量默认扩容机制
     * @param num 扩容次数
     * @return
     */
    public static  Integer getSum(Integer num){
        if(1 == num){
            return 10;
        }else{
            return getSum(num-1)+ getSum(num-1)/2;
        }
    }

    /**
     * 通过反射获取ArrayList.elementData.length
     * @param arrayList
     * @return
     */
    public static int getElementData(ArrayList<?> arrayList) {
        Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            Field field = arrayListClass.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] objects = (Object[]) field.get(arrayList);
            return objects.length;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 反射获取list的elementData.length
     * @param arrayList
     * @return
     */
    public static int getArrayListCapacity(ArrayList<?> arrayList) {
        Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            Field field = arrayListClass.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] objects = (Object[])field.get(arrayList);
            return objects.length;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
