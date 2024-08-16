package cn.com.hellowood.k8sservice.controller;


import lombok.extern.slf4j.Slf4j;
import me.about.widget.jobflow.core.dag.Graph;
import me.about.widget.jobflow.engine.JobFlowEngine;
import me.about.widget.jobflow.entity.JobFlowDef;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/job")
public class JobFlowController {

    @Resource
    private JobFlowEngine jobFlowEngine;


    @PostMapping("/test2")
    public void get2(@RequestBody JobFlowDef jobFlowDef) {
        // json 方式执行
//        String jobFlow  = "{}";

//        JobFlowDef jobFlowDef = JSON.parseObject(jobFlow, JobFlowDef.class);

        Graph graph = jobFlowEngine.registerJobFlow(jobFlowDef);
        jobFlowEngine.executeJobFlow(graph);
    }

    @GetMapping("/test")
    public void get() {
        //注解方式是通过id执行
        jobFlowEngine.executeJobFlow("AAA");
    }

    @GetMapping("/test3")
    public void get3(String expr) {
        // 表达式运行
        List<JobFlowDef> jobFlowDefs = jobFlowEngine.parseJobFlow(expr);
        for (JobFlowDef jobFlowDef : jobFlowDefs) {
            log.info("jobFlowDef:{}", jobFlowDef.getJobFlowId());
            Graph graph = jobFlowEngine.registerJobFlow(jobFlowDef);
            jobFlowEngine.executeJobFlow(graph);
        }
    }
}
