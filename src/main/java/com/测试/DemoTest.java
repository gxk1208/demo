package com.测试;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/3/31 18:27
 */
public class DemoTest {
    public static void main(String[] args) {
        int[] nums = new int[10];
        int num;
        for(int i = 0;i < nums.length;i++){
            do{
                num = (int) (1+Math.random()*10);
            }while(judge(nums,num));
            //返回boolean值
            nums[i] = num;
            System.out.print(nums[i]+" ");

        }
        System.out.println();
        nums = list(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]+" ");
        }
    }

    public static boolean judge(int[] arr,int key){    //去重
        for(int j = 0;j < arr.length;j++){
            if(arr[j] == key){
                return true;
            }
        }
        return false;
    }
    private static int[] list(int[] nums){
        for (int i=0;i<nums.length;i++){
            for (int j=i+1;j<nums.length;j++){
                if (nums[i] < nums[j]) {
                    int temp=nums[i];
                    nums[i]=nums[j];
                    nums[j]=temp;
                }
            }
        }
        return nums;

    }

}
