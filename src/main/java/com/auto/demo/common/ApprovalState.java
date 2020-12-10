package com.auto.demo.common;

public enum ApprovalState {
    DRAFT(10, "草稿"),
    PROCESSING(20, "审批中"),
    APPROVED(30, "通过"),
    REJECTED(40, "驳回"),
    CANCELED(50, "撤回"),
    REVOKED(60, "撤销"),
    ;

    private int state;
    private String name;

    ApprovalState(int state, String name) {
        this.state = state;
        this.name = name;
    }


    public int getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
