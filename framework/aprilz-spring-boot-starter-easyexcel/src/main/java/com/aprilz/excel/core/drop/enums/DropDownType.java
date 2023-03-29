package com.aprilz.excel.core.drop.enums;

/**
 * 下拉类型枚举
 */
public enum DropDownType {
    NONE("NONE","无"),
    YES_NO("YES_NO","是否 1 是 2 否"),
    DIC_SEX("DIC_SEX","男女"),
    DEPT("dept","部门");

    private String value;
    private String remark;

    DropDownType(String value,String remark){
        this.value=value;
        this.remark=remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getValue() {
        return value;
    }
}