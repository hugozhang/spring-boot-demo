package cn.com.hellowood.k8sservice.service;

import cn.com.hellowood.k8sservice.model.Webhook;

/**
 * web hook
 *
 * @author: hugo.zxh
 * @date: 2023/11/27 18:47
 */
public interface WebhookService {

    /**
     * 企业微信机器人
     * @param key
     * @param webhook
     */
    void alertToQiyeWeChat(String key,Webhook webhook);

}
