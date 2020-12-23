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
        BigDecimal divide = bigDecimal.multiply(new BigDecimal("31")).divide(new BigDecimal("7")).setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
    }
}
