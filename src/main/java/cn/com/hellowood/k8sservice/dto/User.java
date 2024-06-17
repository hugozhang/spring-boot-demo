package cn.com.hellowood.k8sservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String name;

    private String email;

    private String userId;

    private String orgId;

    private String id;

    private String role = "ADMIN";
}
