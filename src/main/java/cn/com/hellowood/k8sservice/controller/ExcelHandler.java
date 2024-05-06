package cn.com.hellowood.k8sservice.controller;

import lombok.extern.slf4j.Slf4j;
import me.about.widget.excel.spring.support.reader.MultiPartHandler;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ExcelHandler implements MultiPartHandler {
    @Override
    public void doHandle(MultipartFile multipartFile) {
        log.info("doHandle:扩展名错误");
    }
}
