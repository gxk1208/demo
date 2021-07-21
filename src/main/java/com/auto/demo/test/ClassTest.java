package com.auto.demo.test;


        import com.alibaba.fastjson.JSON;
        import com.alibaba.fastjson.JSONObject;
        import com.alibaba.fastjson.serializer.SerializerFeature;
        import com.auto.demo.dto.JsonTestDto;
        import com.auto.demo.entity.ChargeRule;
        import com.auto.demo.entity.PickList;
        import com.auto.demo.utils.Person;
        import org.springframework.mock.web.MockMultipartFile;
        import org.springframework.web.multipart.MultipartFile;

        import java.io.InputStream;
        import java.lang.reflect.Field;
        import java.net.HttpURLConnection;
        import java.net.URL;
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

    public static void main(String[] args) throws Exception {
        ChargeRule chargeRule = new ChargeRule();
        chargeRule.setRule("1111111111");
        Field[] declaredFields = chargeRule.getClass().getDeclaredFields();
        Field rule = chargeRule.getClass().getDeclaredField("rule");
        rule.setAccessible(true);
        Object o = rule.get(chargeRule);

        System.out.println(rule);


        // json转换();


        double num = 11+3.32+0.6;
        double gnum = 0.2+0.2+0.14;
// "token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDE3IiwiYXV0aEFjY291bnQiOiI0Mld1QlY1VHNnSjRuMVlaeFRTZDY2bGlUNk5kc3h4TTVQOXJvUGNUb1lCbG1laUV1WGYrUWV6RnFFN0xzdFNDOHFPZnQxRnR3MFY1TFhXOHFnZ042UWdpRkJTRllCbUNNK093S1ZCcUI5bjI0YjhCTXltUGxpQjkwNE9xZCtwTjN0Tm5nc2VaUzQ5TjJ4RDRKZU9tbTUxN25YYlcrMXNCUVFCT2VKVE9MTVU9IiwidHlwZSI6MSwiZXhwIjoxNjI2NTY5MTg2fQ.-fIgGTNzCEzO8_HGW0JCXGd9Lj04_1clN9YkN6j2UeY","key":"key1"

        // {"key":"key1","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDE3IiwiYXV0aEFjY291bnQiOiI0Mld1QlY1VHNnSjRuMVlaeFRTZDY2bGlUNk5kc3h4TTVQOXJvUGNUb1lCbG1laUV1WGYrUWV6RnFFN0xzdFNDOHFPZnQxRnR3MFY1TFhXOHFnZ042UWdpRkJTRllCbUNNK093S1ZCcUI5bjI0YjhCTXltUGxpQjkwNE9xZCtwTjN0Tm5nc2VaUzQ5TjJ4RDRKZU9tbTUxN25YYlcrMXNCUVFCT2VKVE9MTVU9IiwidHlwZSI6MSwiZXhwIjoxNjI2NTY5MTg2fQ.-fIgGTNzCEzO8_HGW0JCXGd9Lj04_1clN9YkN6j2UeY"}
        System.out.println(num+gnum);

        //x();

        //JSONObject与Map区别();
        //序列化不忽略非空();

        List<String> strs = new ArrayList<>();

        URL url =  new  URL("https://smart-park-saas-files.oss-cn-beijing.aliyuncs.com/2021/1/0ae9a0f1-f69f-4fb4-bb1c-294115ebfd38.png");
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        //设置是否要从 URL 连接读取数据,默认为true
        uc.setDoInput( true );
        uc.connect();
        InputStream inputstream = uc.getInputStream();

        MultipartFile multipartFile = new MockMultipartFile("temp.jpg","temp.jpg","", inputstream);

    }

    private static void json转换() {
        String message = "token:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDE3IiwiYXV0aEFjY291bnQiOiI0Mld1QlY1VHNnSjRuMVlaeFRTZDY2bGlUNk5kc3h4TTVQOXJvUGNUb1lCbG1laUV1WGYrUWV6RnFFN0xzdFNDOHFPZnQxRnR3MFY1TFhXOHFnZ042UWdpRkJTRllCbUNNK093S1ZCcUI5bjI0YjhCTXltUGxpQjkwNE9xZCtwTjN0Tm5nc2VaUzQ5TjJ4RDRKZU9tbTUxN25YYlcrMXNCUVFCT2VKVE9MTVU9IiwidHlwZSI6MSwiZXhwIjoxNjI2NTY5MTg2fQ.-fIgGTNzCEzO8_HGW0JCXGd9Lj04_1clN9YkN6j2UeY\",\"key:key1";
        JsonTestDto jsonTestDto = new JsonTestDto();

        jsonTestDto.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDE3IiwiYXV0aEFjY291bnQiOiI0Mld1QlY1VHNnSjRuMVlaeFRTZDY2bGlUNk5kc3h4TTVQOXJvUGNUb1lCbG1laUV1WGYrUWV6RnFFN0xzdFNDOHFPZnQxRnR3MFY1TFhXOHFnZ042UWdpRkJTRllCbUNNK093S1ZCcUI5bjI0YjhCTXltUGxpQjkwNE9xZCtwTjN0Tm5nc2VaUzQ5TjJ4RDRKZU9tbTUxN25YYlcrMXNCUVFCT2VKVE9MTVU9IiwidHlwZSI6MSwiZXhwIjoxNjI2NTY5MTg2fQ.-fIgGTNzCEzO8_HGW0JCXGd9Lj04_1clN9YkN6j2UeY");
        jsonTestDto.setKey("key1");
        String s = JSON.toJSONString(jsonTestDto);
        JSONObject jsonObject = JSON.parseObject(s);
        Map map = JSON.parseObject(s, Map.class);
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
String a = json.toJSONString();
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
