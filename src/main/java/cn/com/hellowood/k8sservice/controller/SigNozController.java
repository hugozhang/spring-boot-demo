package cn.com.hellowood.k8sservice.controller;

import cn.com.hellowood.k8sservice.dto.Login;
import cn.com.hellowood.k8sservice.dto.SuccessResponse;
import cn.com.hellowood.k8sservice.dto.User;
import cn.com.hellowood.k8sservice.dto.UserToken;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1")
public class SigNozController {

    @PostMapping("/login")
    public UserToken receiveTraces(@RequestBody Login login) throws IOException {
        // 处理追踪数据
//        saveDataToStorage(tracesData);
        return new UserToken(1, "accessJwt", "refreshJwt");
    }

    @GetMapping("/user/{userId}")
    public User get(@PathVariable("userId") String userId) throws IOException {
        // 处理追踪数据
//        saveDataToStorage(tracesData);
        return new User("name", "email", "1", "orgId", "1", "ADMIN");
    }

}
