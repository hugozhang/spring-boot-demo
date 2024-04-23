package cn.com.hellowood.k8sservice.model;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hugo.zxh
 * @date: 2023/11/27 18:38
 */
@Data
public class Label {

    private String alertname;

//    private String prometheus;

    private String severity;

    private String instance;

    private String container;

    private String pod;

    private String job;

    private String namespace;

    private String service;

}
