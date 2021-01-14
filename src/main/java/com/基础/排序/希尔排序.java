package com.基础.排序;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/29 9:55
 */
public class 希尔排序 {
    public static void main(String[] args) {
        String s = "stdout";
        //优化前();
        //优化中(s);
        //优化
    }
    private static int 优化(String s) {
        int maxLen = 0;
        for(int i = 0; i < s.length(); i++){
            // 创建一个存放字符的集合
            HashSet<Character> set = new HashSet<>();
            for(int j = i; j < s.length(); j++) {
                // 判断集合是否存在第 j 个字符
                if(set.contains(s.charAt(j)))
                    break;
                set.add(s.charAt(j));
            }
            maxLen = Math.max(maxLen,set.size());
        }
        return maxLen;
    }

    private static int 优化中(String s) {
        int num = 0;
        if(null == s || "".equals(s)){
            System.out.println(0);
            return 0;
        }
        if(1 == s.length()){
            System.out.println(1);
            return 1;
        }
        String[] split = s.split("");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.clear();
            for (int j = i; j < split.length; j++) {
                if(list.contains(split[j]) || j == split.length-1){
                    if(j == split.length-1 && !list.contains(split[j])){
                        list.add(split[j]);
                    }
                    if(list.size()>num){
                        num = list.size();
                    }
                    break ;
                }
                list.add(split[j]);
            }
        }
        System.out.println(num);
        return num;
    }

    private static void 优化前() {
        String s = "aab";
        int num = 0;
        if(null == s || "".equals(s)){
            num = 0;
        }
        String[] split = s.split("");

        if(1 == split.length){
            num = 1;
        }
        for (int i = 0; i < split.length; i++) {
            for (int j = i + 1; j < split.length; j++) {
                if (s.substring(i, j).contains(split[j])) {
                    if (j - i > num) {
                        num = j - i;
                    }
                    break;
                }
                if (split[j].equals(split[i]) || j == split.length - 1) {
                    if (j - i +1 > num) {
                        num = j - i + 1;
                        break;
                    }
                }
            }
        }
        System.out.println(num);
    }


}
