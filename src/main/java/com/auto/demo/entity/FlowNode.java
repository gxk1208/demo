package com.auto.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class FlowNode {
    private Integer parentNodeId;

    private NodeEntity nodeEntity;
    private List<FlowNode> childNodes;
    private List<FlowDataFilter> flowDataFilterList;

    public FlowNode(NodeEntity nodeEntity) {
        this.nodeEntity = nodeEntity;
    }

    public FlowNode() {
    }

    public void addChildNode(FlowNode childNode) {
        if (nodeEntity != null) {
            childNode.setParentNodeId(nodeEntity.getNodeId());
        }
        if (this.childNodes == null) {
            this.childNodes = new ArrayList<FlowNode>();
        }
        this.childNodes.add(childNode);
    }

    public void addDataFilter(FlowDataFilter flowDataFilter) {
        if (this.flowDataFilterList == null) {
            this.flowDataFilterList = new ArrayList<FlowDataFilter>();
        }
        this.flowDataFilterList.add(flowDataFilter);
    }

    public void removeDataFilter(FlowDataFilter flowDataFilter) {
        if (this.flowDataFilterList != null) {
            this.childNodes.remove(flowDataFilter);
        }
    }

    public void removeChildNode(FlowNode childNode) {
        if (this.childNodes != null) {
            this.childNodes.remove(childNode);
        }
    }

    public FlowNode findChildNodeById(int nodeId) {
        if (childNodes != null && !childNodes.isEmpty()) {
            int childNumber = childNodes.size();
            for (int i = 0; i < childNumber; i++) {
                FlowNode child = childNodes.get(i);
                Integer searchId = child.getNodeEntity().getNodeId();
                if (searchId == nodeId){
                    return child;
                }
                FlowNode resultNode = child.findChildNodeById(nodeId);
                if (resultNode != null) {
                    return resultNode;
                }
            }
        }
        return null;
    }

}
