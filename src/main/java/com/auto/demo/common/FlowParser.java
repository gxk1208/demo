package com.auto.demo.common;


import com.auto.demo.entity.FlowDataFilter;
import com.auto.demo.entity.FlowNode;
import com.auto.demo.entity.ParserTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FlowParser {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* 找到一颗树中某个节点 */


    public FlowNode getNextNode(FlowNode flowNode, Map<String, Object> taskFieldValueMap) {
        List<FlowNode> childNodeList = flowNode.getChildNodes();
        for (FlowNode nodeChild: childNodeList) {
            List<FlowDataFilter> filterList = nodeChild.getFlowDataFilterList();
            for (FlowDataFilter flowDataFilter : filterList) {//过滤条件
                String fieldKey = flowDataFilter.getField();
                String op = flowDataFilter.getOp();
                String tableName = flowDataFilter.getTableName();

                String token = ParserTokens.convetOperator(op);

                StringBuilder sql = new StringBuilder(" SELECT id ");
                sql.append(" FROM ").append(tableName);
                sql.append(" where ").append(fieldKey).append(token).append("'").append(taskFieldValueMap.get(fieldKey)).append("'");
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
                if (list != null && list.size() > 0) {
                    return nodeChild;
                }
            }
        }
        return null;
    }
}
