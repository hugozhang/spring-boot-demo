package cn.com.hellowood.task;

import me.about.widget.jobflow.annotation.DependsOn;
import me.about.widget.jobflow.annotation.JobFlow;
import me.about.widget.jobflow.core.Task;
import org.springframework.stereotype.Service;


@Service
@JobFlow(id = "AAA")
@DependsOn(value = {Task4.class,Task5.class})
public class Task0 implements Task {
    @Override
    public void execute() {
        System.out.println("Task0 execute");
    }

}
