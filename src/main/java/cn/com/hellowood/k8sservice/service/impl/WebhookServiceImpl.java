package cn.com.hellowood.k8sservice.service.impl;

import cn.com.hellowood.k8sservice.model.*;
import cn.com.hellowood.k8sservice.service.EnterpriseWeChatBotService;
import cn.com.hellowood.k8sservice.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

/**
 * webhook 服务
 *
 * @author: hugo.zxh
 * @date: 2023/11/27 18:50
 */
@Service
public class WebhookServiceImpl implements WebhookService {

    Logger logger = LoggerFactory.getLogger(WebhookServiceImpl.class);

    @Resource
    private EnterpriseWeChatBotService enterpriseWeChatBotService;


    @Override
    public void alertToQiyeWeChat(String key,Webhook webhook) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Alert> alerts = webhook.getAlerts();
        String content = "# <font color='warning'>{{status}}</font>\n" +
                "> **labels:**\n" +
                "{{labels}}" +
                "> **annotations:**\n" +
                "> ** · **summary: {{summary}}\n" +
                "> ** · **description: {{description}}\n";;
        for (Alert alert : alerts) {
//            LocalDateTime startsAt = alert.getStartsAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//            LocalDateTime endsAt = alert.getEndsAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            StringBuilder labels = new StringBuilder();

            Label alertLabels = alert.getLabels();

            Field[] declaredFields = Label.class.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(alertLabels);
                    if (value != null) {
                        labels.append("> ** · **").append(field.getName()).append(": ").append(value).append("\n");
                    }
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(),e);
                }
            }
            content =  content.replace("{{labels}}", labels.toString())
                .replace("{{status}}",alert.getStatus())
                .replace("{{summary}}",alert.getAnnotations().getSummary())
                .replace("{{description}}",alert.getAnnotations().getDescription());
        }
        WeChatMarkdown weChatMarkdown = new WeChatMarkdown();
        weChatMarkdown.setMsgtype("markdown");

        Markdown markdown = new Markdown();
        markdown.setContent(content);

        weChatMarkdown.setMarkdown(markdown);

        enterpriseWeChatBotService.sendToBot(key,weChatMarkdown);
    }
}
