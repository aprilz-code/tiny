//package com.aprilz.tiny.common.utils;
//
//
//import cn.hutool.core.collection.CollUtil;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.function.BiConsumer;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
///**
// * @description: list转tree
// * @author: Aprilz
// * @since: 2022/10/31
// **/
//public class TreeUtils {
//
//    /**
//     * @param list             源数据
//     * @param setChildListFn   设置递归的方法
//     * @param idFn             获取id的方法
//     * @param pidFn            获取父id的方法
//     * @param getRootCondition 获取根节点的提哦啊见
//     * @return 将List 转换成 Tree
//     */
//    public static <M, T> List<M> listToTree(List<M> list,
//                                            Function<M, T> idFn,
//                                            Function<M, T> pidFn,
//                                            BiConsumer<M, List<M>> setChildListFn,
//                                            Predicate<M> getRootCondition) {
//        if (CollUtil.isEmpty(list)) return null;
//        Map<T, List<M>> listMap = list.stream().collect(Collectors.groupingBy(pidFn));
//        list.forEach(model -> setChildListFn.accept(model, listMap.get(idFn.apply(model))));
//        return list.stream().filter(getRootCondition).collect(Collectors.toList());
//    }
//
//    public static <M> List<M> treeToList(List<M> source,
//                                         Function<M, List<M>> getChildListFn,
//                                         BiConsumer<M, List<M>> setChildListFn,
//                                         Predicate<M> getRootCondition) {
//        List<M> target = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(source)) {
//            treeToList(source, target, getChildListFn);
//            target.forEach(model -> setChildListFn.accept(model, null));
//            target.addAll(target.stream().filter(getRootCondition).collect(Collectors.toList()));
//        }
//        return target;
//    }
//
//    private static <F> void treeToList(List<F> source,
//                                       List<F> target,
//                                       Function<F, List<F>> getChildListFn) {
//        if (CollectionUtils.isNotEmpty(source)) {
//            target.addAll(source);
//            source.forEach(model -> {
//                List<F> childList = getChildListFn.apply(model);
//                if (CollectionUtils.isNotEmpty(childList)) {
//                    treeToList(childList, target, getChildListFn);
//                }
//            });
//        }
//    }
//
//    //test
//    public static void main(String[] args) {
//        Node first = new Node(1, 0, "first");
//        Node second = new Node(2, 1, "second");
//        Node third = new Node(3, 2, "third");
//        Node second001 = new Node(4, 1, "second001");
//        Node third001 = new Node(5, 4, "third001");
//        List<Node> nodes = Arrays.asList(first, second, third, second001, third001);
//        // 只需要一行数据
//        List<Node> nodes1 = TreeUtils.listToTree(nodes, Node::getId, Node::getPid, Node::setTreeNode, (l) -> l.getPid() == 0);
//
//        System.out.println(nodes1);
//        // 树状结构转换成 List 也就是还原数据
//        List<Node> nodes2 =  TreeUtils.treeToList(nodes1, Node::getTreeNode, Node::setTreeNode, (l) -> l.getPid() == 0);
//        System.out.println(nodes2);
//    }
//
//    @Data
//    static class Node {
//        private Integer Id;
//
//        private String name;
//
//        private Integer pid;
//
//        private List<Node> treeNode = new ArrayList<>();
//
//        public Node(int id, int pid) {
//            this.Id = id;
//            this.pid = pid;
//        }
//
//        public Node(int id, int pid, String name) {
//            this(id, pid);
//            this.name = name;
//        }
//
//    }
//}
