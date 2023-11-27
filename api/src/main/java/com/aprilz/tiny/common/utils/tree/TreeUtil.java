package com.aprilz.tiny.common.utils.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.util.TypeUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Aprilz
 * @date 2023/3/6-9:13
 * @description 构建树形结构工具类
 */
public class TreeUtil {

    /**
     * 列表转为树形
     *
     * @param list     列表
     * @param id       对象ID
     * @param parentId 对象父级ID
     * @param children 对象子节点(必须初始化为空列表)
     * @param <T>      对象
     * @return 属性列表
     */
    public static <T> List<T> convert(List<T> list, Function<T, Long> id, Function<T, Long> parentId, Function<T, List<T>> children) {
        List<T> rootList = new ArrayList<>(list.size() / 2);
        Map<?, T> idMap = list.stream().collect(Collectors.toMap(id, Function.identity(), (k1, k2) -> k1));
        for (T item : list) {
            if (idMap.containsKey(parentId.apply(item))) {
                T parentItem = idMap.get(parentId.apply(item));
                children.apply(parentItem).add(item);
            } else {
                rootList.add(item);
            }
        }
        return rootList;
    }

    /**
     * 列表转为树形
     *
     * @param list 集合
     * @return List<? extends BaseTree>
     */
    public static List<? extends BaseTree> getTree(List<? extends BaseTree> list) {
        return getTree(list, 0L);
    }


    /**
     * 树形转为列表
     *
     * @param treeList 集合
     * @return List<? extends BaseTree>
     */
    public static List<? extends BaseTree> treeToList(List<? extends BaseTree> treeList) {
        List<BaseTree> resultList = new ArrayList<>();
        if(CollUtil.isNotEmpty(treeList)){
            for (BaseTree tree : treeList) {
                resultList.add(tree);
                List<BaseTree> childList = (List<BaseTree>) tree.getChildList();
                if(CollUtil.isNotEmpty(childList)){
                    resultList.addAll(treeToList(childList));
                }
            }
        }
        return resultList;
    }

    /**
     * 查找最内层子叶子节点数据(没有子节点的节点)
     * @param treeList（为排序完后的树）
     */
    public static List<? extends BaseTree>  findMinNodes(List<? extends BaseTree> treeList) {
        List<BaseTree> resultList = new ArrayList<>();
        if(CollUtil.isNotEmpty(treeList)){
            for (BaseTree tree : treeList) {
                List<BaseTree> childList = (List<BaseTree>) tree.getChildList();
                if(CollUtil.isNotEmpty(childList)){
                    resultList.addAll(findMinNodes(childList));
                    //无节点则直接加入
                }else{
                    resultList.add(tree);
                }
            }
        }
        return resultList;
    }



//    /**
//     * 树形层级 往上统计
//     *
//     * @param treeList
//     */
//    public static void treeCalculate(List<? extends BaseTree> treeList) {
//        if (CollUtil.isNotEmpty(treeList)) {
//            for (BaseTree tree : treeList) {
//                List<BaseTree> childList = (List<BaseTree>) tree.getChildList();
//                if (CollUtil.isNotEmpty(childList)) {
//                    List<BaseTree> collect = childList.stream().filter(tmp -> !tmp.isAddSelf()).collect(Collectors.toList());
//                    //存在未计算的子级则继续重复调用,之后再计算自身
//                    if (collect.size() > 0) {
//                        treeCalculate(childList);
//                    }
//                    //无节点则直接计算
//                    Integer reduce = childList.stream()
//                            .map(BaseTree::getProjectNum)
//                            .filter(value -> value != null).reduce(0, Integer::sum);
//                    tree.setProjectNum(reduce + (Objects.isNull(tree.getProjectNum()) ? 0 : tree.getProjectNum()));
//                }
//                //计算完成标识
//                tree.setAddSelf(true);
//            }
//        }
//    }


    /**
     * 树形层级 往上统计
     *  用法 ：  TreeUtil.treeCalculatePlus(respVOS,"projectNum");
     *
     * @param treeList   树层级List
     * @param fieldNames 可变长属性名
     */
    public static void treeCalculatePlus(List<? extends BaseTree> treeList, String... fieldNames) {
        if (fieldNames.length == 0) {
            throw new IllegalArgumentException("最少需要一个属性名");
        }
        if (CollUtil.isNotEmpty(treeList)) {
            for (BaseTree tree : treeList) {
                List<BaseTree> childList = (List<BaseTree>) tree.getChildList();
                if (CollUtil.isNotEmpty(childList)) {
                    List<BaseTree> collect = childList.stream().filter(tmp -> !tmp.isAddSelf()).collect(Collectors.toList());
                    //存在未计算的子级则继续重复调用,之后再计算自身
                    if (collect.size() > 0) {
                        treeCalculatePlus(childList, fieldNames);
                    }
                    //无节点则直接计算
                    for (String fieldName : fieldNames) {
                        Object fieldValue = Objects.isNull(ReflectUtil.getFieldValue(tree, fieldName)) ? 0 : ReflectUtil.getFieldValue(tree, fieldName);
                        Integer sum = TypeUtils.castToInt(fieldValue);
                        for (BaseTree child : childList) {
                            sum += TypeUtils.castToInt( Objects.isNull(ReflectUtil.getFieldValue(child, fieldName)) ? 0 : ReflectUtil.getFieldValue(child, fieldName));
                        }
                        ReflectUtil.setFieldValue(tree, fieldName, sum);
                    }
                }
                //计算完成标识
                tree.setAddSelf(true);
            }
        }
    }



    /**
     * 获取树形集合，根据sort字段排序 PID默认为0
     *
     * @param list 集合
     * @return List<? extends BaseTree>
     */
    public static List<? extends BaseTree> getTreeWithSort(List<? extends BaseTree> list) {
        return getTree(list, 0L, Comparator.comparing(BaseTree::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    /**
     * 获取树形集合，根据sort字段排序
     *
     * @param list 集合
     * @param pid  顶级id
     * @return List<? extends BaseTree>
     */
    public static List<? extends BaseTree> getTreeWithSort(List<? extends BaseTree> list, Long pid) {
        return getTree(list, pid, Comparator.comparing(BaseTree::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    /**
     * 获取树形集合，根据updateTime字段排序 PID默认为0
     *
     * @param list 集合
     * @return List<? extends BaseTree>
     */
    public static List<? extends BaseTree> getTreeWithTime(List<? extends BaseTree> list) {
        return getTree(list, 0L, Comparator.comparing(BaseTree::getUpdateTime, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    /**
     * 获取树形集合，根据updateTime字段排序
     *
     * @param list 集合
     * @param pid  顶级id
     * @return List<? extends BaseTree>
     */
    public static List<? extends BaseTree> getTreeWithTime(List<? extends BaseTree> list, Long pid) {
        return getTree(list, pid, Comparator.comparing(BaseTree::getUpdateTime, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    /**
     * 列表转为树形
     *
     * @param list 集合
     * @param pid  顶级id
     * @return List<? extends BaseTree>
     */
    public static List<? extends BaseTree> getTree(List<? extends BaseTree> list, final Long pid) {
        return getTree(list, pid, null);
    }

    /**
     * 获取树形集合，根据传入的比较器排序
     *
     * @param list       集合
     * @param pid        顶级id
     * @param comparator 比较器
     * @return List<? extends BaseTree>
     */
    public static List<? extends BaseTree> getTree(List<? extends BaseTree> list, Long pid, Comparator<BaseTree> comparator) {
        if (Objects.isNull(pid)) {
            pid = 0L;
        }
        Long finalPid = pid;
        if (!Objects.isNull(comparator)) {
            list = list.stream().sorted(comparator).collect(Collectors.toList());
        }
        List<? extends BaseTree> finalList = list;
        return list.stream().filter(item -> Objects.equals(finalPid, item.getParentId())).peek(item -> item.setChildList(getChildren(item, finalList))).collect(Collectors.toList());
    }

    /**
     * 获取child节点
     *
     * @param root 根节点
     * @param list 集合
     * @return 子节点
     */
    private static List<? extends BaseTree> getChildren(BaseTree root, List<? extends BaseTree> list) {
        return list.stream().filter(item -> Objects.equals(item.getParentId(), root.getId())).peek(item -> item.setChildList(getChildren(item, list))).collect(Collectors.toList());
    }

    /**
     * 获取所有的child节点id，查询使用
     *
     * @param pid  父id
     * @param list 集合
     * @param itself   true包含自身
     * @return 所有子节点id
     */
    public static Set<Long> getAllChildrenIds(List<? extends BaseTree> list, Long pid,Boolean itself ) {
        List<? extends BaseTree> tree = getTree(list, pid);
        Set<Long> result = new HashSet<>();
        if(itself){
            result.add(pid);
        }
        for (BaseTree baseTree : tree) {
            getChildrenIds(result, baseTree);
        }
        return result;
    }

    /**
     * 获取所有的child节点id，查询使用
     *  包含自身
     * @param pid  父id
     * @param list 集合
     * @return 所有子节点id
     */
    public static Set<Long> getAllChildrenIds(List<? extends BaseTree> list, Long pid) {
        return getAllChildrenIds(list,pid,true);
    }

    private static void getChildrenIds(Set<Long> result, BaseTree baseTree) {
        result.add(baseTree.getId());
        if (CollUtil.isEmpty(baseTree.getChildList())) {
            return;
        }
        for (BaseTree child : baseTree.getChildList()) {
            getChildrenIds(result, child);
        }
    }


    /**
     * 过滤树
     *
     * @param treeList 树
     * @param list     满足条件的元素id
     * @return 过滤之后的树
     */
    public static List<? extends BaseTree> filterTree(List<? extends BaseTree> treeList, List<Long> list) {
        List<BaseTree> filterTreeList = new ArrayList<>();
        for (BaseTree tree : treeList) {
            if (isRemoveNode(tree, list)) {
                return null;
            }
            Iterator<? extends BaseTree> iterator = tree.getChildList().iterator();
            while (iterator.hasNext()) {
                BaseTree child = iterator.next();
                deleteNode(child, iterator, list);
            }
            filterTreeList.add(tree);

        }
        return filterTreeList;
    }

    /**
     * 判断该节点是否该删除
     * 往下一直递归，子节点都不包含，则可删除
     *
     * @param root 根节点
     * @param list 命中的节点
     * @return ture 需要删除  false 不能被删除
     */
    private static boolean isRemoveNode(BaseTree root, List<Long> list) {
        List<? extends BaseTree> children = root.getChildList();
        // 叶子节点
        if (CollUtil.isEmpty(children)) {
            return !list.contains(root.getId());
        }
        // 子节点
        if (list.contains(root.getId())) {
            return false;
        }
        // 如果存在一个子节点不能删除，那么就不能删除
        boolean bool = true;
        for (BaseTree child : children) {
            if (!isRemoveNode(child, list)) {
                bool = false;
                break;
            }
        }
        return bool;
    }

    /**
     * 删除节点
     *
     * @param child    子节点
     * @param iterator 迭代器
     * @param list     list
     */
    private static void deleteNode(BaseTree child, Iterator<? extends BaseTree> iterator, List<Long> list) {
        if (isRemoveNode(child, list)) {
            iterator.remove();
            return;
        }
        List<? extends BaseTree> childrenList = child.getChildList();
        if (CollUtil.isEmpty(childrenList)) {
            return;
        }
        Iterator<? extends BaseTree> children = childrenList.iterator();
        while (children.hasNext()) {
            BaseTree childChild = children.next();
            deleteNode(childChild, children, list);
        }
    }
}
