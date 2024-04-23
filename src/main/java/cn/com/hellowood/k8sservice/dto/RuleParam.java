package cn.com.hellowood.k8sservice.dto;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RuleParam {

    private List<Map<String, Object>> params;

    private List<String> rules;

}
