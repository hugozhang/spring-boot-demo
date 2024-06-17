package cn.com.hellowood.k8sservice.dto;


import lombok.Data;

@Data
public class SuccessResponse<T> {

    private String statusText;

    private T data;

    public static <T> SuccessResponse<T> success(T data) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setStatusText("success");
        response.setData(data);
        return response;
    }

}
