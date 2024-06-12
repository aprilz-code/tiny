package com.aprilz.tiny.component.mybatis.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 租户基础实体类
 *
 * @author Xjr
 * @version 1.0
 * @date 2023-04-23 2:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TenantEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    private Long tenantId;
}