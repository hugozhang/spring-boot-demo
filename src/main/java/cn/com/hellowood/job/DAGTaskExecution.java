package cn.com.hellowood.job;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// 任务接口
interface Task {
    void execute();
    Set<Task> getDependencies();
}

// 任务执行器
class TaskExecutor {
    private final Map<Task, Set<Task>> dag = new HashMap<>();
    private final Map<Task, Future<?>> taskFutures = new HashMap<>();

    // 构建DAG
    public void buildDag(List<Task> tasks) {
        for (Task task : tasks) {
            dag.put(task, new HashSet<>(task.getDependencies()));
        }
    }

    public void printDag() {
        for (Task task : topologicalSort()) {
            printTaskWithDependencies(task, 0);
        }
    }

    private void printTaskWithDependencies(Task task, int indent) {
        StringBuilder indentBuilder = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            indentBuilder.append("  ");
        }
        String prefix = indentBuilder.toString(); // 使用缩进来表示层次结构
        System.out.println(prefix + task);
        for (Task dependency : dag.getOrDefault(task, Collections.emptySet())) {
            printTaskWithDependencies(dependency, indent + 1);
        }
    }

    // 拓扑排序
    public List<Task> topologicalSort() {
        Map<Task, Integer> indegrees = new HashMap<>();
        for (Task task : dag.keySet()) {
            indegrees.put(task, 0);
        }
        for (Task task : dag.keySet()) {
            for (Task dep : dag.get(task)) {
                indegrees.put(task, indegrees.getOrDefault(dep, 0) + 1);
            }
        }

        Queue<Task> queue = new LinkedList<>();
        for (Task task : dag.keySet()) {
            if (indegrees.getOrDefault(task, 0) == 0) {
                queue.add(task);
            }
        }

        List<Task> sortedTasks = new ArrayList<>();
        while (!queue.isEmpty()) {
            Task current = queue.poll();
            sortedTasks.add(current);
            for (Task task : dag.get(current)) {
                int newIndegree = indegrees.get(task) - 1;
                indegrees.put(task, newIndegree);
                if (newIndegree == 0) {
                    queue.add(task);
                }
            }
        }

        if (indegrees.values().stream().anyMatch(val -> val > 0)) {
            throw new RuntimeException("DAG contains a cycle");
        }

        return sortedTasks;
    }

    // 执行任务
    public void executeTasks(List<Task> tasks) throws ExecutionException, InterruptedException {

        buildDag(tasks);

        printDag();

        List<Task> sortedTasks = topologicalSort();

        ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());
        for (Task task : sortedTasks) {
            taskFutures.put(task, executorService.submit(task::execute));
        }

        for (Future<?> future : taskFutures.values()) {
            future.get(); // 等待任务完成
        }

        executorService.shutdown();
    }
}

// 用法示例
class MyTask implements Task {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyTask myTask = (MyTask) o;
        return Objects.equals(name, myTask.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private final String name;
    private final Set<Task> dependencies = new HashSet<>();

    public MyTask(String name, MyTask... dependencies) {
        this.name = name;
        this.dependencies.addAll(Arrays.asList(dependencies));
    }

    @Override
    public void execute() {
        System.out.println("Executing: " + name);
    }

    @Override
    public Set<Task> getDependencies() {
        return dependencies;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class DAGTaskExecution {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Task> tasks = Arrays.asList(
                new MyTask("A"),
//                new MyTask("B", new MyTask("A")),
                new MyTask("C", new MyTask("A"), new MyTask("B"))
        );

        TaskExecutor executor = new TaskExecutor();
        executor.executeTasks(tasks);
    }
}
