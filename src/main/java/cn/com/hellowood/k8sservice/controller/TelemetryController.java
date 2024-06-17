package cn.com.hellowood.k8sservice.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceRequest;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;
import io.opentelemetry.proto.logs.v1.LogRecord;
import io.opentelemetry.proto.logs.v1.ResourceLogs;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class TelemetryController {

    @PostMapping("/traces")
    public ResponseEntity<String> receiveTraces(String tracesData,HttpServletRequest request) throws IOException {
        // 处理追踪数据
//        saveDataToStorage(tracesData);
        parseTraceData(request.getInputStream());
        return ResponseEntity.ok("Traces received");
    }

    @PostMapping("/logs")
    public ResponseEntity<String> receiveLogs(String logsData,HttpServletRequest request) throws IOException {
        // 处理日志数据
//        saveDataToStorage(logsData);
        parseLogData(request.getInputStream());
        return ResponseEntity.ok("Logs received");
    }

    @PostMapping("/metrics")
    public ResponseEntity<String> receiveMetrics(@RequestBody String metricsData, HttpServletRequest request) throws IOException {
        // 处理度量指标数据
//        saveDataToStorage(metricsData);
        return ResponseEntity.ok("Metrics received");
    }

    private void saveDataToStorage(String data) {
        // 将数据保存到MySQL、Redis或其他存储系统
//        System.out.println("Received data: " + data);
    }


    @SneakyThrows
    public static void parseLogData(InputStream binaryData) {

            // 将二进制数据解码为 ExportLogsServiceRequest 对象
            ExportLogsServiceRequest request = ExportLogsServiceRequest.parseFrom(binaryData);

            request.getResourceLogsList().forEach(resourceLogs -> resourceLogs.getScopeLogsList().forEach(scopeLogs -> {
                scopeLogs.getLogRecordsList().forEach(logRecord -> {
                    System.out.println("Log Record Body: " + logRecord.getBody().getStringValue());
                    logRecord.getAttributesList().forEach(attribute -> {
                        System.out.println("Attribute Key: " + attribute.getKey() + ", Value: " + attribute.getValue().getStringValue());
                    });
                });
            }));
    }


    @SneakyThrows
    public static void parseTraceData(InputStream binaryData) {

        // 将二进制数据解码为 ExportLogsServiceRequest 对象
        ExportTraceServiceRequest request = ExportTraceServiceRequest.parseFrom(binaryData);

        request.getResourceSpansList().forEach(resourceSpans -> resourceSpans.getScopeSpansList().forEach(instrumentationLibrarySpans -> {
            instrumentationLibrarySpans.getSpansList().forEach(span -> {
                System.out.println("Span Name: " + span.getName());
                span.getAttributesList().forEach(attribute -> {
                    System.out.println("Attribute Key: " + attribute.getKey() + ", Value: " + attribute.getValue().getStringValue());
                });
            });
        }));
    }


    // 解析二进制 Protobuf 日志数据
    // 解析二进制 Protobuf 日志数据
    public static void parseLogData(byte[] binaryData) {
        try {
            // 将二进制数据解码为 ExportLogsServiceRequest 对象
            ExportLogsServiceRequest request = ExportLogsServiceRequest.parseFrom(binaryData);

            // 获取请求中的所有 ResourceLogs 列表
            for (ResourceLogs resourceLogs : request.getResourceLogsList()) {
                // 遍历所有日志记录
                for (LogRecord logRecord : resourceLogs.getScopeLogsList().get(0).getLogRecordsList()) {
                    // TODO: 处理每个日志记录
                    // 示例：打印日志记录的正文
                    System.out.println("Log Record Body: " + logRecord.getBody().getStringValue());

                    // 打印日志记录的属性（如果存在）
                    for (io.opentelemetry.proto.common.v1.KeyValue attribute : logRecord.getAttributesList()) {
                        System.out.println("Attribute Key: " + attribute.getKey() + ", Value: " + attribute.getValue().getStringValue());
                    }
                }
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            // 处理 Protobuf 解码异常
        }
    }
}
