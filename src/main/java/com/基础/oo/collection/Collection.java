package com.基础.oo.collection;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/22 14:21
 */

public class Collection {
    public static void main(String[] args) {

        //this.test();

        //subList方法();

    }

    private static void subList方法() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("2");
        List<String> subList =  list.subList(0, 1);
        // 对原 List 增加一个值
        list.add("10");

        // 这一行会报 java.util.ConcurrentModificationException
        subList.add("11");

        System.out.println(list.toString());
        System.out.println(subList.toString());
    }

    public  static  void test(){
        List<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("1");
        System.out.println(arrayList.toString());

        List<String> linkedList = new LinkedList<>();
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
        linkedList.add("4");
        linkedList.add("1");
        System.out.println(linkedList.toString());

        Set<String> hashSet = new HashSet<>();
        hashSet.add("1");
        hashSet.add("2");
        hashSet.add("3");
        hashSet.add("4");
        hashSet.add("1");
        System.out.println(hashSet.toString());

        Set<String> treeSet = new TreeSet<>();
        treeSet.add("1");
        treeSet.add("2");
        treeSet.add("3");
        treeSet.add("4");
        treeSet.add("1");
        System.out.println(treeSet.toString());

        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("1");
        linkedHashSet.add("2");
        linkedHashSet.add("3");
        linkedHashSet.add("4");
        linkedHashSet.add("1");
        System.out.println(linkedHashSet.toString());

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("1",1);
        hashMap.put("2",2);
        hashMap.put("3",3);
        hashMap.put("4",4);
        hashMap.put("1",5);
        System.out.println(hashMap.toString());

        Map<String, Object> linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("1",1);
        linkedHashMap.put("2",2);
        linkedHashMap.put("3",3);
        linkedHashMap.put("4",4);
        linkedHashMap.put("1",5);
        System.out.println(linkedHashMap.toString());

        BigDecimal bigDecimal = new BigDecimal("1200");
        BigDecimal sum = new BigDecimal("31");
        BigDecimal count = new BigDecimal("7");
        BigDecimal divide = bigDecimal.multiply(sum).divide(count,2,BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);

        List<String> strNums = new ArrayList<>();
        strNums.add("a");
        strNums.add("b");
        strNums.add("c");
        strNums.add("d");
        strNums.add("e");
        strNums.add("f");

        List<String> str2Nums = new ArrayList<>();
        for (int i = strNums.size() -1 ;i >= 0;i--){
            str2Nums.add(strNums.get(i));
        }
        System.out.println(str2Nums.toString());

        for (int i = 0;i<strNums.size()/2;i++){
            int end = strNums.size()-1-i;
            if(i != end){
                String s = strNums.get(i);
                strNums.set(i,strNums.get(end));
                strNums.set(end,s);
            }
        }
        System.out.println(strNums.toString());
    }
}
