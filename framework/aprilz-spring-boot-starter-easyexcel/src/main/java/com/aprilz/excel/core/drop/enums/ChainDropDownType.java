package com.aprilz.excel.core.drop.enums;

/**
 * 级联下拉类型枚举
 */
public enum ChainDropDownType {
    NONE("NONE", "无"),
    AREA("AREA", "区域级联，相同的类型会创建一个隐藏的sheet，名称是value"),
    COMPANY_PROJECT("COMPANY_PROJECT", "公司-项目");

    private String value;
    private String remark;

    ChainDropDownType(String value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getValue() {
        return value;
    }
}
