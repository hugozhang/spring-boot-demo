package cn.com.hellowood.k8sservice.dto;

import lombok.Data;
import me.about.widget.excel.annotation.ExcelColumn;

@Data
public class UserExcel {

    @ExcelColumn(name = "姓名")
    private String name;

    @ExcelColumn(name = "年龄")
    private Integer age;

}
