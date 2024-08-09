package cn.com.hellowood.k8sservice.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
//@RestController
@RequestMapping("/")
public class K8sController {

    @Value("${app.name:Hello K8S}")
    private String appName;

    private final Tracer tracer;

    @Resource
    private K8sController k8sController;


    private final MeterRegistry registry;

    public K8sController(OpenTelemetry openTelemetry, MeterRegistry registry) {
        this.tracer = openTelemetry.getTracer("application");
        this.registry = registry;
    }



    @WithSpan
    public void test1() throws InterruptedException {
        Thread.sleep(500);
        k8sController.test11();
    }



    @WithSpan
    public void test11() throws InterruptedException {
        Thread.sleep(500);
    }

    @GetMapping("/")
    public String root() throws InterruptedException {

        registry.counter("greetings.total", "name", "测试1").increment();

        log.info(appName);

        k8sController.test1();


        Span span = tracer.spanBuilder("rollTheDice").startSpan();
        span.addEvent("start");

        span.setAttribute("app.name", appName);


        Thread.sleep(1000);

        span.addEvent("end");
        span.end();


//
//            Span span = Span.current();
//        span.updateName("创建 eventDemo");
//        //手动更新 Event 持续时间
//        span.addEvent("timeEvent1",System.currentTimeMillis()+2000,
//                TimeUnit.MILLISECONDS);
//
//        //手动更新 Event 持续时间
//        span.addEvent("timeEvent2");
//
//
//        //给 Event 添加相关信息
//        Attributes appInfo = Attributes.of(AttributeKey
//                        .stringKey("app.id"), "123456",
//                AttributeKey.stringKey("app.name"), "应用程序 demo");
//        span.addEvent("auth.appinfo", appInfo);
//
//        span.end();

        return "Hello Kubernetes";

    }

    @GetMapping("/healthz")
    public String healthz() {
        return "ok";
    }
}


