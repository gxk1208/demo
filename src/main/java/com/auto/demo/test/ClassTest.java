package com.auto.demo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.auto.demo.entity.AnyContains01;
import com.auto.demo.entity.AnyContains02;
import com.auto.demo.entity.PickList;
import com.auto.demo.utils.Person;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/11/11 10:32
 */
public class ClassTest {

    public static void main(String[] args) {
        //x();

        //JSONObject与Map区别();

        AnyContains01 anyContains01 = new AnyContains01();
        anyContains01.getAc02().setId(1);
        System.out.println(anyContains01.toString() );

        //序列化不忽略非空();

    }

    private static void 序列化不忽略非空() {
        Person person = new Person();
        person.setAdd0004("1111");
        String s = JSON.toJSONString(person, SerializerFeature.WriteMapNullValue);
        System.out.println(s);
    }

    private static void JSONObject与Map区别() {
        JSONObject json = new JSONObject();
        Map<String, Object> map = new HashMap<>();

        json.put("key1",1);
        json.put("key2",2);
        json.put("key3",3);
        json.put("key4",4);

        map.put("key1",1);
        map.put("key2",2);
        map.put("key3",3);
        map.put("key4",4);

        System.out.println(json.toString());
        System.out.println(map.toString());

        System.out.println(json.toJSONString());
        System.out.println(JSON.toJSONString(map));

        System.out.println("end");
    }

    private static void x() {
        List<PickList> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PickList pickList = new PickList();
            pickList.setName(String.valueOf(i));
            list.add(pickList);
        }
        List<String> names = new ArrayList<>();
        list.forEach(l -> names.add(l.getName()) );

        System.out.println(names.toString());
    }
}
