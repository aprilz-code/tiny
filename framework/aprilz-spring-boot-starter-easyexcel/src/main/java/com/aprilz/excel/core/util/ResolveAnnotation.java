package com.aprilz.excel.core.util;

/**
 * @author Aprilz
 * @date 2023/3/29-15:24
 * @description TODO
 */

import cn.hutool.extra.spring.SpringUtil;
import com.aprilz.excel.core.drop.annotations.ChainDropDownFields;
import com.aprilz.excel.core.drop.annotations.DropDownFields;
import com.aprilz.excel.core.drop.entity.ChainDropDown;
import com.aprilz.excel.core.drop.services.IChainDropDownService;
import com.aprilz.excel.core.drop.services.IDropDownService;

import java.util.Arrays;
import java.util.Optional;

/**
 * 解析下拉注解工具类
 */
public class ResolveAnnotation {

    /**
     * 解析注解，得到具体的下拉数据
     *
     * @param dropDownField 注解
     * @return string[]
     */
    public static String[] resolve(DropDownFields dropDownField) {
        if (!Optional.ofNullable(dropDownField).isPresent()) {
            return new String[0];
        }
        // 获取固定下拉信息
        String[] source = dropDownField.source();
        if (source.length > 0) {
            return source;
        }

        // 获取动态的下拉数据
        Class<? extends IDropDownService> classes = dropDownField.sourceClass();
        if (null != classes) {
            IDropDownService dropDownService = SpringUtil.getBean(classes);
            String[] dynamicSource = dropDownService.getSource(dropDownField.type().getValue());
            if (null != dynamicSource && dynamicSource.length > 0) {
                return dynamicSource;
            }
        }

        return new String[0];
    }

    public static ChainDropDown resolve(ChainDropDownFields chainDropDownFields) {
        if (!Optional.ofNullable(chainDropDownFields).isPresent()) {
            return null;
        }
        // 获取动态的下拉数据
        Class<? extends IChainDropDownService>[] classes = chainDropDownFields.sourceClass();
        if (null != classes && classes.length > 0) {
            try {
                IChainDropDownService chainDropDownService = Arrays.stream(classes).findFirst().get().newInstance();
                ChainDropDown source = chainDropDownService.getSource(chainDropDownFields.isRoot(), chainDropDownFields.params());
                source.setTypeName(chainDropDownFields.type().getValue());
                return source;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

