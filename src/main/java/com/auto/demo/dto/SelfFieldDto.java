package com.auto.demo.dto;

import com.auto.demo.entity.SelfField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/25 14:25
 */
@Data
@ApiModel(description = "特殊字段")
public class SelfFieldDto extends SelfField {
    private Object values;
}
