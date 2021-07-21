package com.基础.排序;


import java.util.Arrays;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/23 9:58
 */
public class 选择排序 {
    public static void main(String[] args) {
        //依次取出一个数字，与它之后的数字比较，小于等于不交换。大于交换位置
        //定义需要排序的数组
        Integer[] arr = {1,23,2,55,66,12,99,3,1};
        System.out.println(Arrays.toString(arr));
        //外层遍历，依次取出每个数据（最后一个不用取）
        for (int i = 0; i < arr.length-1; i++) {
            //假设当前数据为最小数据，保存下标
            Integer min = i;
            //内层遍历，取出外层取出的数之后的数进行比较
            for (int j = i+1; j < arr.length; j++) {
                //比较，小于等于不处理，大于，则替换保存的最小元素的下标,替换最小元素
                if(arr[min] > arr[j]){
                    min = j;
                }
            }
            //找到比外层遍历取出数更小的数，进行交换
            if(min != i){
                Integer temp =  arr[i];
                arr[i] = arr[min];
                arr[min] = temp;}
        }
        System.out.println(Arrays.toString(arr));
    }
}
