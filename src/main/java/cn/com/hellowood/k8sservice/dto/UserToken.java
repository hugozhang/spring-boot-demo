package cn.com.hellowood.k8sservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserToken {

    private int userId;

    private String accessJwt;

    private String refreshJwt;
}
