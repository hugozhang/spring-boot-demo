package cn.com.hellowood.task;


import me.about.widget.jobflow.annotation.DependsOn;
import me.about.widget.jobflow.annotation.JobFlow;
import me.about.widget.jobflow.core.Task;
import org.springframework.stereotype.Service;


@Service
@JobFlow(id = "AAA")
@DependsOn({Task5.class})
public class Task2 implements Task {
    @Override
    public void execute() {
        System.out.println("Task2 execute");
//        throw new RuntimeException("Task2 execute error");
    }
}
