package com.基础.排序;

import java.util.Arrays;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/28 10:40
 */
public class 插入排序 {
    public static void main(String[] args) {
        //定义需要排序的数组
        Integer[] arr = {1,23,2,55,66,12,2,11,0,99,3,1};
        System.out.println(Arrays.toString(arr));
        //统计次数
        int num = 0;
        //外层遍历，从第二个元素开始
        for (int i = 1; i < arr.length ; i++) {
            //获取要插入的元素
            Integer insertNum = arr[i];
            //计算插入的位置
            Integer insertSite = i - 1;
            while (insertSite >= 0 &&  arr[insertSite] > insertNum ){
                insertSite--;
            }
            //内层遍历，将插入位置到外层i之间的元素往后挪一位
            for (int j = i-1 ; j > insertSite ; j--) {
                num ++;
                arr[j+1] = arr[j];
            }
            //插入元素
            arr[++insertSite] = insertNum;
            /*//内层遍历，取i之前的元素进行比较
            for (int j = i; j > 0  ; j--) {
                //小于之前的元素，则插入到之前元素之前
                 if(arr[j] < insertNum){
                     num++;
                     arr[j] = arr[j-1];
                     arr[j-1] = insertNum;
                 }
            }*/
        }
        System.out.println(Arrays.toString(arr));
        System.out.println(num);
    }
}
