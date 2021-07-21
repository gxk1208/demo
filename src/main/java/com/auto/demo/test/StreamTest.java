package com.auto.demo.test;

import com.auto.demo.entity.SelfField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/7/20 11:17
 */

public class StreamTest {
    public static void main(String[] args) {
        ArrayList<SelfField> fields = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {

            SelfField field = new SelfField();
            field.setType(i);
            if(i == 5){
                field.setType(3);
            }
            fields.add(field);
        }

        //取字段去重(fields);

    }

    private static void 取字段去重(ArrayList<SelfField> fields) {
        List<Integer> collect = fields.stream().map(SelfField::getType).distinct().collect(Collectors.toList());
        System.out.println(collect);
    }
}
