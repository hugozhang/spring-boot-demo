package cn.com.hellowood.k8sservice;

//import com.linecorp.armeria.spring.actuate.ArmeriaSpringActuatorAutoConfiguration;
//import io.opentelemetry.instrumentation.spring.autoconfigure.EnableOpenTelemetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import zipkin2.server.internal.EnableZipkinServer;

//@SpringBootApplication(exclude = {ArmeriaSpringActuatorAutoConfiguration.class})
@SpringBootApplication
//@EnableZipkinServer
public class AlertWebhookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertWebhookApplication.class, args);
    }
}
