package cn.com.hellowood.k8sservice.service;

import cn.com.hellowood.k8sservice.model.WeChatMarkdown;
import me.about.widget.retrofit2.annotation.RetrofitHttpClient;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hugo.zxh
 * @date: 2023/11/27 18:52
 */
@RetrofitHttpClient(baseUrl = "${webhook.qiyebot.url}")
public interface QiyeWeChatBotService {

    /**
     * 发送给企业机器人
     * @param key
     * @param weChatMarkdown
     * @return
     */
    @POST("/cgi-bin/webhook/send")
    String sendToBot(@Query ("key")String key,@Body WeChatMarkdown weChatMarkdown);

}
