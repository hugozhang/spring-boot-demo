package cn.com.hellowood.job;

import java.util.*;

public class TopologicalSort {
    public static List<Node> topologicalSort(List<Node> graph, int n) {
        List<Node> sortedList = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        int[] indegree = new int[n];

        // 初始化入度数组，并找到所有入度为0的顶点加入队列
        for (Node node : graph) {
            for (Node nn : node.adjacent) {
                indegree[nn.value]++;
            }
            if (indegree[node.value -1] == 0) {
                queue.add(node);
            }
        }

        // Kahn's Algorithm
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            sortedList.add(node);

            for (Node nn : node.adjacent) {
                indegree[nn.value]--;
                if (indegree[nn.value] == 0) {
                    queue.add(nn);
                }
            }
        }

        // 检查是否所有顶点都被访问了（即图中是否有环）
        if (sortedList.size() != n) {
            throw new RuntimeException("Graph contains a cycle");
        }

        return sortedList;
    }

    static class Node {
        int value;
        List<Node> adjacent;

        Node(int value) {
            this.value = value;
            this.adjacent = new ArrayList<>();
        }

        void addEdge(Node v) {
            adjacent.add(v);
        }
    }

    // 测试代码
    public static void main(String[] args) {
        // 创建图并添加边
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.addEdge(node2);
        node2.addEdge(node1);
        // ... 添加更多节点和边

        // 执行拓扑排序
        List<Node> sortedNodes = topologicalSort(Arrays.asList(node1, node2, node3), 3);
        // 打印排序结果
        for (Node node : sortedNodes) {
            System.out.println(node.value);
        }
    }
}
