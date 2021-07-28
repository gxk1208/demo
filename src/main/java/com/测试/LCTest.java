package com.测试;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/7/21 16:14
 */
public class LCTest {

    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicates(nums));


    }
    public static int removeDuplicates(int[] nums) {
        int length = nums.length;
        for (int i = 0; i < nums.length-1; i++) {
            if(nums[i] == nums[i+1]){
                nums[i] = nums[i+1];
                length -= 1;
            }
        }
        return length;
    }
}
