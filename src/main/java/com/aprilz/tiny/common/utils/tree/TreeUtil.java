package com.aprilz.tiny.common.utils.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Aprilz
 * @date 2023/3/6-9:13
 * @description 构建树形结构工具类
 */
public class TreeUtil {

    /**
     * @param list
     * @param pid  顶级id
     * @return
     */
    public static List<? extends BaseTree> getTree(List<? extends BaseTree> list, Long pid) {
        if (Objects.isNull(pid)) {
            pid = 0L;
        }
        Long finalPid = pid;
        //根据修改时间排序
        list = list.stream().sorted((o1, o2) -> {
            if (o1.getUpdateTime().isAfter(o2.getUpdateTime())) {
                return -1;
            } else return 1;
        }).collect(Collectors.toList());
        List<? extends BaseTree> finalList = list;
        return list.stream().filter(item -> finalPid == item.getParentId()).map(item -> {
            item.setChildList(getChildren(item, finalList));
            return item;
        }).collect(Collectors.toList());

    }

    private static List<? extends BaseTree> getChildren(BaseTree root, List<? extends BaseTree> list) {
        return list.stream().filter(item -> item.getParentId().equals(root.getId()))
                .map(item -> {
                    item.setChildList(getChildren(item, list));
                    return item;
                }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<TreeTestVO> respVOs = new ArrayList<>();
        getTree(respVOs, null);
    }

}
