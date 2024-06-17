package cn.com.hellowood.k8sservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rule {

    private Integer id;

    private String ruleName;

    private String expression;

    private String config;

}
