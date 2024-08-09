package cn.com.hellowood.task;

import me.about.widget.taskflow.annotation.DependsOn;
import me.about.widget.taskflow.annotation.JobFlow;
import me.about.widget.taskflow.core.Task;
import org.springframework.stereotype.Service;


@Service
@JobFlow("AAA")
@DependsOn({TaskA.class,TaskB.class})
public class TaskC implements Task {
    @Override
    public void execute() {
        System.out.println("TaskC execute");
    }
}
