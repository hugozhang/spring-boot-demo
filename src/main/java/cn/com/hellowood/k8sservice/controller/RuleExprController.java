package cn.com.hellowood.k8sservice.controller;


import cn.com.hellowood.k8sservice.dto.Rule;
import cn.com.hellowood.k8sservice.dto.RuleParam;
import cn.com.hellowood.k8sservice.dto.UserExcel;
import cn.com.hellowood.k8sservice.service.TestService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.about.widget.BusinessRule;
import me.about.widget.DefaultBusinessRule;
import me.about.widget.MultiBusinessRule;
import me.about.widget.RuleContext;
import me.about.widget.excel.spring.support.reader.ExcelMultipart;
import me.about.widget.mybatis.plugin.page.model.PageParam;
import me.about.widget.mybatis.plugin.page.model.PageResult;
import me.about.widget.spring.support.SpringContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "规则表达式")
@Slf4j
@RestController
@RequestMapping("/rule")
public class RuleExprController {

    @Resource
    private TestService testService;



    @PostMapping("/list")
    public PageResult<Rule> ruleList(@RequestBody PageParam<Object> ruleParam) {
        PageResult<Rule> pageResult = new PageResult<>();

        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(1, "规则名称1","表达式1", "配置1"));
        rules.add(new Rule(2, "规则名称2","表达式2", "配置2"));
        pageResult.setRows(rules);

        pageResult.setTotalPage(1);
        pageResult.setTotal(2);
        return pageResult;

    }


    @PostMapping("/execute")
    public List<Map<String, Object>> root(@RequestBody RuleParam ruleParam) {
        List<Map<String, Object>> results = new ArrayList<>();

        MultiBusinessRule rule = new MultiBusinessRule(ruleParam
                .getRules()
                .stream()
                .map(DefaultBusinessRule::new)
                .toArray(BusinessRule[]::new));

        for (Map<String, Object> param : ruleParam.getParams()) {
            List<Map<String, Object>> result = rule.execute(new RuleContext(param, SpringContextHolder.getApplicationContext()));
            results.addAll(result);
        }

        return results;
    }

    @PostMapping("/upload")
    public void upload(@ExcelMultipart(name = "file1",outputClass = UserExcel.class,handler = ExcelHandler.class) List<UserExcel> userExcels,
                       @ExcelMultipart(name = "file2",outputClass = UserExcel.class) List<UserExcel> users2) {
        System.out.println(userExcels);
        System.out.println(users2);
        log.info("{}", userExcels);
    }
}
