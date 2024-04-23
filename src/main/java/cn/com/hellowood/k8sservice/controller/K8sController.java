package cn.com.hellowood.k8sservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class K8sController {

    @Value("${app.name:Hello K8S}")
    private String appName;

    @GetMapping("/")
    public String root() {
        log.info(appName);
        return "Hello Kubernetes";
    }

    @GetMapping("/healthz")
    public String healthz() {
        return "ok";
    }
}


