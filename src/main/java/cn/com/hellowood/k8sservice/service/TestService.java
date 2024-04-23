package cn.com.hellowood.k8sservice.service;

public interface TestService {


    String returnString(String name);

    void test(String name);

    void test(Integer name);

    void test();

    void test(String name,Long age);

}
