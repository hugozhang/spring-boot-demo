package cn.com.hellowood.k8sservice.model;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hugo.zxh
 * @date: 2023/11/27 18:36
 */
@Data
public class Alert {

    private String status;

    private String generatorURL;

    private Label labels;

    private Annotation annotations;

}
