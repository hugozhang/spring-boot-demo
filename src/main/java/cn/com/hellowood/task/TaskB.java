package cn.com.hellowood.task;

import lombok.SneakyThrows;
import me.about.widget.jobflow.annotation.DependsOn;
import me.about.widget.jobflow.annotation.JobFlow;
import me.about.widget.jobflow.core.Task;
import org.springframework.stereotype.Service;


@Service
public class TaskB implements Task {
    @Override
    @SneakyThrows
    public void execute() {
        Thread.sleep(2000);
        System.out.println("TaskB execute");
    }

}
