package cn.com.hellowood.k8sservice.model;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hugo.zxh
 * @date: 2023/11/27 21:14
 */
@Data
public class WeChatMarkdown {

    private String msgtype;

    private Markdown markdown;

}
