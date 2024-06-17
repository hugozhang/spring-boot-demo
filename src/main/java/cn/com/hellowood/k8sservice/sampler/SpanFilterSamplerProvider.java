//package cn.com.hellowood.k8sservice.sampler;
//
//import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
//import io.opentelemetry.sdk.autoconfigure.spi.traces.ConfigurableSamplerProvider;
//import io.opentelemetry.sdk.trace.samplers.Sampler;
//
//public class SpanFilterSamplerProvider implements ConfigurableSamplerProvider {
//    @Override
//    public Sampler createSampler(ConfigProperties configProperties) {
//        return new SpanFilterSampler();
//    }
//
//    @Override
//    public String getName() {
//        return "SpanFilterSampler"; // SpanFilterSampler可以替换为自定义的名称
//    }
//}
