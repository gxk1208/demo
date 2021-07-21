package com.基础.排序;

import java.util.Arrays;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/28 17:07
 */
public class 冒泡排序 {
    public static void main(String[] args) {
        //优化前();
        //优化后();

    }

    private static void 优化后() {
        //定义需要排序的数组
        Integer[] arr = {1,23,2,55,66,12,2,11,0,99,3,1};
        System.out.println(Arrays.toString(arr));
        //判断是否为空
        if(null == arr || arr.length < 2){
            return;
        }
        //判断是否为已排序好的数组
        //外层遍历，用于控制内层遍历次数
        for (int i = 0; i < arr.length; i++) {
            boolean flag = false;
            //外层每遍历一次，内层少遍历一次（倒数i+1位已排序好）（length - (i+1)）
            for (int j = 0; j < arr.length-i-1; j++) {
                //前一位大于后一位，交换位置
                if(arr[j] > arr[j+1]){
                    flag = true;
                    Integer temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            if(flag){
                return;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    private static void 优化前() {
        //定义需要排序的数组
        Integer[] arr = {1,23,2,55,66,12,2,11,0,99,3,1};
        System.out.println(Arrays.toString(arr));
        //外层遍历，用于控制内层遍历次数
        for (int i = 0; i < arr.length; i++) {
            //外层每遍历一次，内层少遍历一次（倒数i+1位已排序好）（length - (i+1)）
            for (int j = 0; j < arr.length-i-1; j++) {
                //前一位大于后一位，交换位置
                if(arr[j] > arr[j+1]){
                    Integer temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
