package com.auto.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class NodeEntity{
    Integer nodeId;//节点唯一标识id
    String name;//节点名称
    Integer nodeType;//节点类型
    List<Integer>  userList; //用户列表
    Integer signType;// 1 或签  2 and
}
