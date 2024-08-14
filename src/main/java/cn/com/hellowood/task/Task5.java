package cn.com.hellowood.task;

import me.about.widget.jobflow.annotation.JobFlow;
import me.about.widget.jobflow.core.Task;
import org.springframework.stereotype.Service;


@Service
@JobFlow(id = "AAA")
public class Task5 implements Task {
    @Override
    public void execute() {
        System.out.println("Task5 execute");
    }

}
