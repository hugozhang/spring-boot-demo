package cn.com.hellowood.k8sservice.controller;


import me.about.widget.taskflow.spring.JobFlowExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/job")
public class JobFlowController {

    @Resource
    private JobFlowExecutor jobFlowExecutor;

    @GetMapping("/test")
    public void get() {
        jobFlowExecutor.executeJobFlow("AAA");
    }
}
