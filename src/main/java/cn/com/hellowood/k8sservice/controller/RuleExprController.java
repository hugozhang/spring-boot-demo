package cn.com.hellowood.k8sservice.controller;


import cn.com.hellowood.k8sservice.dto.RuleParam;
import cn.com.hellowood.k8sservice.service.TestService;
import io.swagger.annotations.Api;
import me.about.widget.BusinessRule;
import me.about.widget.DefaultBusinessRule;
import me.about.widget.MultiBusinessRule;
import me.about.widget.RuleContext;
import me.about.widget.spring.support.SpringContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "规则表达式")
@RestController
@RequestMapping("/rule")
public class RuleExprController {

    @Resource
    private TestService testService;
    @PostMapping("/execute")
    public List<Map<String, Object>> root(@RequestBody RuleParam ruleParam) {
        List<Map<String, Object>> results = new ArrayList<>();

        MultiBusinessRule rule = new MultiBusinessRule(ruleParam.getRules().stream()
                .map(DefaultBusinessRule::new)
                .toArray(BusinessRule[]::new));

        for (Map<String, Object> param : ruleParam.getParams()) {
            List<Map<String, Object>> result = rule.execute(new RuleContext(param, SpringContextHolder.getApplicationContext()));
            results.addAll(result);
        }

        return results;
    }

}
