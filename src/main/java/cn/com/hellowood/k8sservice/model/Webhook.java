package cn.com.hellowood.k8sservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hugo.zxh
 * @date: 2023/11/27 18:35
 */
@Data
public class Webhook {

    private List<Alert> alerts = new ArrayList<>();

}
