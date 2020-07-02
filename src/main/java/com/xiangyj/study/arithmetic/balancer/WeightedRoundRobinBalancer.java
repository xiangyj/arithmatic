package com.xiangyj.study.arithmetic.balancer;

import java.util.ArrayList;
import java.util.List;

/**
 * 加权轮训负载均衡算法
 * @param <T>
 */
public class WeightedRoundRobinBalancer<T> {

    private static final WeightedRoundRobinBalancer INSTANCE = new WeightedRoundRobinBalancer<>();
    private WeightedRoundRobinBalancer() {}

    public static WeightedRoundRobinBalancer<String> instance() {
        return INSTANCE;
    }
    /** 当前平衡器持有的负载因子*/
    private static final List<Node> invokers = new ArrayList<>();

    /** 所有负载因子权重之和*/
    private volatile static int totalWeight;

    /**
     * 添加负载因子
     * @param t 类型
     * @param weight 权重
     */
    public void addInvoker(T t, int weight) {
        invokers.add(new Node<>(t, weight));
        totalWeight += weight;
    }

    /**
     * 返回下一个负载因子
     * 该算法的原理如下：
     *
     * 　　每个服务器都有两个权重变量：
     * 　　a：weight，配置文件中指定的该服务器的权重，这个值是固定不变的；
     * 　　b：current_weight，服务器目前的权重。一开始为0，之后会动态调整。
     *
     * 　　每次当请求到来，选取负载因子时，会遍历数组中所有负载因子。对于每个负载，让它的current_weight增加它的weight；同时累加所有服务器的weight，并保存为total。（一般可认为保存不变）
     * 　　遍历完所有因子之后，如果该因子的current_weight是最大的，就选择这个处理本次请求。最后把该因子的current_weight减去total。
     * @return T
     */
    public T nextInvoker() {
        Node<T> result = null;
        int weight = -totalWeight;
        for (Node<T> node : invokers) {
            int currentWeight = node.currentWeight + node.weight;
            // 判断最大
            if (currentWeight >= weight) {
                result = node;
                weight = currentWeight;
            }
            node.currentWeight = currentWeight;
        }
        result.currentWeight -= totalWeight;
        return result.t;
    }

    /**
     * 负载因子节点，保存了权重以及当前权重与负载因子的关系
     * @param <T>
     */
    private static class Node<T> {
        T t;
        private int weight;
        private int currentWeight;

        public Node(T t, int weight) {
            this.t = t;
            this.weight = weight;
            this.currentWeight = 0;
        }
    }

    public static void main(String[] args) {
        WeightedRoundRobinBalancer<String> blander = WeightedRoundRobinBalancer.instance();
        blander.addInvoker("a", 4);
        blander.addInvoker("b", 2);
        blander.addInvoker("c", 1);
        blander.addInvoker("d", 3);
        for (int i = 0; i < 20; i++) {
            System.out.println(blander.nextInvoker());
        }
    }
}
