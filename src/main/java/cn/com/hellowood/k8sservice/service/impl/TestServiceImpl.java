package cn.com.hellowood.k8sservice.service.impl;

import cn.com.hellowood.k8sservice.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service(value = "testService")
@Slf4j
public class TestServiceImpl implements TestService {

    @Override
    public String returnString(String name) {
        log.info("returnString param {}", name);
        return name;
    }

    @Override
    public void test(String name) {
        log.info("param {}", name);
    }

    @Override
    public void test(Integer name) {
        log.info("param {}", name);
    }

    @Override
    public void test() {
        log.info("no param");
    }

    @Override
    public void test(String name, Long age) {
        log.info("param {},{}", name, age);
    }
}
