package cn.com.hellowood.k8sservice.controller;

import cn.com.hellowood.k8sservice.model.Webhook;
import cn.com.hellowood.k8sservice.service.WebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * AlertManager Web Hook
 *
 * @author: hugo.zxh
 * @date: 2023/11/27 18:32
 */

@Slf4j
@RestController
@RequestMapping("/webhook")
public class WebHookController {

    @Resource
    private WebhookService webhookService;

    @PostMapping("/wechat")
    public void wechat(@RequestParam("key") String key,@RequestBody Webhook webhook) {
        webhookService.alertToQiyeWeChat(key,webhook);
    }

}
