package cn.com.hellowood.task;


import lombok.SneakyThrows;
import me.about.widget.jobflow.core.Task;
import org.springframework.stereotype.Service;


@Service
public class TaskA implements Task {
    @SneakyThrows
    @Override
    public void execute() {
        Thread.sleep(1000);
        System.out.println("TaskA execute");
    }

}
