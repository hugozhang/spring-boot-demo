package cn.com.hellowood.k8sservice.controller;


import me.about.widget.jobflow.engine.JobFlowEngine;
import me.about.widget.jobflow.entity.JobFlowDef;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/job")
public class JobFlowController {

    @Resource
    private JobFlowEngine jobFlowEngine;


    @PostMapping("/test2")
    public void get2(@RequestBody JobFlowDef jobFlowDef) {
//        String jobFlow  = "{}";

//        JobFlowDef jobFlowDef = JSON.parseObject(jobFlow, JobFlowDef.class);

        jobFlowEngine.registerJobFlow(jobFlowDef);
        jobFlowEngine.executeJobFlow(jobFlowDef);
    }

    @GetMapping("/test")
    public void get() {
        jobFlowEngine.executeJobFlow("AAA");
    }

    @GetMapping("/test3")
    public void get3(String expr) {
        JobFlowDef jobFlowDef = jobFlowEngine.parseJobFlow(expr);
        jobFlowEngine.registerJobFlow(jobFlowDef);
        jobFlowEngine.executeJobFlow(jobFlowDef);

    }
}
