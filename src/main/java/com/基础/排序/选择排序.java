package com.基础.排序;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/23 9:58
 */
public class 选择排序 {
    public static void main(String[] args) {
        Integer[] arr = {1,23,2,55,66,12,99,3,1};
        List<Integer> numbers= new ArrayList<>(Arrays.asList(arr));
        System.out.println(numbers.toString());

        List<Integer> numbers2= new ArrayList<>(arr.length);
        Collections.addAll(numbers2,arr);

        for (int i = 0; i < numbers.size(); i++) {
            Integer flag = null;
            Integer min = numbers.get(i);
            for (int j = 0; j < numbers.size(); j++) {
                if(min > numbers.get(j)){
                    min  = numbers.get(j);
                    flag = j;
                }
            }
            if(null != flag){
                Integer temp = numbers.get(flag);
                numbers.set(flag,numbers.get(i));
                numbers.set(i,temp);

            }
        }

        System.out.println(numbers.toString());



    }
}
