package cn.com.hellowood.task;

import me.about.widget.taskflow.annotation.JobFlow;
import me.about.widget.taskflow.core.Task;
import org.springframework.stereotype.Service;


@Service
@JobFlow("AAA")
public class TaskD implements Task {
    @Override
    public void execute() {
        System.out.println("TaskD execute");
    }

}
