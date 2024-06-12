package com.aprilz.tiny.component.mybatis.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class BaseTreeDO extends BaseEntity {

    public static final String SEPARATOR = ",";

    public static final String ROOT_NODE = "0";

    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(value = "parent_ids")
    private String parentIds;

    @TableField(value = "sort")
    private Integer sort;

}
