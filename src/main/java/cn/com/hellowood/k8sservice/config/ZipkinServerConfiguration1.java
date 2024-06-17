//package cn.com.hellowood.k8sservice.config;
///*
// * Copyright 2015-2019 The OpenZipkin Authors
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
// * in compliance with the License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software distributed under the License
// * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
// * or implied. See the License for the specific language governing permissions and limitations under
// * the License.
// */
//
//import brave.Tracing;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.linecorp.armeria.common.*;
//import com.linecorp.armeria.server.HttpService;
//import com.linecorp.armeria.server.RedirectService;
//import com.linecorp.armeria.server.cors.CorsServiceBuilder;
//import com.linecorp.armeria.server.file.FileService;
//import com.linecorp.armeria.server.file.HttpFile;
//import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
////import com.linecorp.armeria.spring.actuate.ArmeriaSpringActuatorAutoConfiguration;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.config.MeterFilter;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringWriter;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.BeanCreationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Condition;
//import org.springframework.context.annotation.ConditionContext;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.io.Resource;
//import org.springframework.core.type.AnnotatedTypeMetadata;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import zipkin2.collector.CollectorMetrics;
//import zipkin2.collector.CollectorSampler;
//import zipkin2.server.internal.*;
//import zipkin2.server.internal.brave.TracingStorageComponent;
//import zipkin2.storage.InMemoryStorage;
//import zipkin2.storage.StorageComponent;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//@Configuration
////@ImportAutoConfiguration(ArmeriaSpringActuatorAutoConfiguration.class)
//public class ZipkinServerConfiguration implements WebMvcConfigurer {
//
//    @Autowired(required = false)
//    ZipkinQueryApiV2 httpQuery;
//
//    @Autowired(required = false)
//    ZipkinHttpCollector httpCollector;
//
//    @Autowired(required = false)
//    MetricsHealthController healthController;
//
//    static final String DEFAULT_BASEPATH = "/zipkin";
//
//    @Autowired ZipkinUiProperties ui;
//
//    @Value("classpath:zipkin-ui/index.html") Resource lensIndexHtml;
//
////    Resource lensIndexHtml = new ClassPathResource("zipkin-ui/");
//
//    @Bean
//    @ConditionalOnMissingBean(CollectorMetrics.class)
//    CollectorMetrics metrics(MeterRegistry registry) {
//        return new ActuateCollectorMetrics(registry);
//    }
//
//    @Bean
//    HttpService indexService() throws Exception {
//        HttpService lensIndex = maybeIndexService(ui.getBasepath(), lensIndexHtml);
//        if (lensIndex != null) return lensIndex;
//        throw new BeanCreationException("Could not load Lens UI from " + lensIndexHtml);
//    }
//
//    @Bean ArmeriaServerConfigurator uiServerConfigurator(
//            HttpService indexService
//    ) throws IOException {
//        ServerCacheControl maxAgeYear =
//                ServerCacheControl.builder().maxAgeSeconds(TimeUnit.DAYS.toSeconds(365)).build();
//
//        HttpService uiFileService = FileService.builder(getClass().getClassLoader(), "zipkin-ui")
//                .cacheControl(maxAgeYear)
//                .build();
//
//        String config = writeConfig(ui);
//        return sb -> {
//
//
//            if (httpQuery != null) {
//                sb.annotatedService(httpQuery);
//                sb.annotatedService("/zipkin", httpQuery); // For UI.
//            }
//            if (httpCollector != null) sb.annotatedService(httpCollector);
//
//            sb.service("/zipkin/config.json", HttpFile.builder(HttpData.ofUtf8(config))
//                    .cacheControl(ServerCacheControl.builder().maxAgeSeconds(600).build())
//                    .contentType(MediaType.JSON_UTF_8)
//                    .build()
//                    .asService());
//
//            sb.serviceUnder("/zipkin/", uiFileService);
//
//            // TODO This approach requires maintenance when new UI routes are added. Change to the following:
//            // If the path is a a file w/an extension, treat normally.
//            // Otherwise instead of returning 404, forward to the index.
//            // See https://github.com/twitter/finatra/blob/458c6b639c3afb4e29873d123125eeeb2b02e2cd/http/src/main/scala/com/twitter/finatra/http/response/ResponseBuilder.scala#L321
//            sb.service("/zipkin/", indexService)
//                    .service("/zipkin/index.html", indexService)
//                    .service("/zipkin/traces/{id}", indexService)
//                    .service("/zipkin/dependency", indexService)
//                    .service("/zipkin/traceViewer", indexService);
//
//            sb.service("/favicon.ico", new RedirectService(HttpStatus.FOUND, "/zipkin/favicon.ico"))
//                    .service("/", new RedirectService(HttpStatus.FOUND, "/zipkin/"));
////                    .service("/zipkin", new RedirectService(HttpStatus.FOUND, "/zipkin/"));
//
//            // don't add metrics for favicon
////            meterRegistry.ifPresent(m -> m.config().meterFilter(MeterFilter.deny(id -> {
////                String uri = id.getTag("uri");
////                return uri != null && uri.startsWith("/favicon.ico");
////            })));
//        };
//    }
//
//
//    static String writeConfig(ZipkinUiProperties ui) throws IOException {
//        StringWriter writer = new StringWriter();
//        try (JsonGenerator generator = JsonUtil.createGenerator(writer)) {
//            generator.useDefaultPrettyPrinter();
//            generator.writeStartObject();
//            generator.writeStringField("environment", ui.getEnvironment());
//            generator.writeNumberField("queryLimit", ui.getQueryLimit());
//            generator.writeNumberField("defaultLookback", ui.getDefaultLookback());
//            generator.writeBooleanField("searchEnabled", ui.isSearchEnabled());
//            generator.writeStringField("logsUrl", ui.getLogsUrl());
//            generator.writeStringField("supportUrl", ui.getSupportUrl());
//            generator.writeStringField("archivePostUrl", ui.getArchivePostUrl());
//            generator.writeStringField("archiveUrl", ui.getArchiveUrl());
//            generator.writeObjectFieldStart("dependency");
//            generator.writeBooleanField("enabled", ui.getDependency().isEnabled());
//            generator.writeNumberField("lowErrorRate", ui.getDependency().getLowErrorRate());
//            generator.writeNumberField("highErrorRate", ui.getDependency().getHighErrorRate());
//            generator.writeEndObject(); // .dependency
//            generator.writeEndObject(); // .
//        }
//        return writer.toString();
//    }
//
//    static HttpService maybeIndexService(String basePath, Resource resource) throws IOException {
//        String maybeContent = maybeResource(basePath, resource);
//        if (maybeContent == null) return null;
//
//        ServerCacheControl maxAgeMinute = ServerCacheControl.builder().maxAgeSeconds(60).build();
//
//        return HttpFile.builder(HttpData.ofUtf8(maybeContent))
//                .contentType(MediaType.HTML_UTF_8).cacheControl(maxAgeMinute)
//                .build().asService();
//    }
//
//    static String maybeResource(String basePath, Resource resource) throws IOException {
//        if (!resource.isReadable()) return null;
//
//        try (InputStream stream = resource.getInputStream()) {
//            String content = StreamUtils.copyToString(stream, UTF_8);
//            if (DEFAULT_BASEPATH.equals(basePath)) return content;
//
//            String baseTagValue = "/".equals(basePath) ? "/" : basePath + "/";
//            // html-webpack-plugin seems to strip out quotes from the base tag when compiling so be
//            // careful with this matcher.
//            return content.replaceAll(
//                    "<base href=[^>]+>", "<base href=\"" + baseTagValue + "\">"
//            );
//        }
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/zipkin-ui/**")
//                .addResourceLocations("classpath:/zipkin-ui/");
//    }
//
////    @Bean ArmeriaServerConfigurator serverConfigurator() {
////        return sb -> {
////            if (httpQuery != null) {
////                sb.annotatedService(httpQuery);
////                sb.annotatedService("/zipkin", httpQuery); // For UI.
////            }
////            if (httpCollector != null) sb.annotatedService(httpCollector);
////            if (healthController != null) sb.annotatedService(healthController);
////            // Redirects the prometheus scrape endpoint for backward compatibility
////            sb.service("/prometheus", new RedirectService("/actuator/prometheus"));
////            // Redirects the info endpoint for backward compatibility
////            sb.service("/info", new RedirectService("/actuator/info"));
////        };
////    }
//
//    /** Registers health for any components, even those not in this jar. */
//
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addRedirectViewController("/info", "/actuator/info");
//    }
//
//    /** Configures the server at the last because of the specified {@link Order} annotation. */
////    @Order @Bean ArmeriaServerConfigurator corsConfigurator(
////            @Value("${zipkin.query.allowed-origins:*}") String allowedOrigins) {
////        CorsServiceBuilder corsBuilder = CorsServiceBuilder.forOrigins(allowedOrigins.split(","))
////                // NOTE: The property says query, and the UI does not use POST, but we allow POST?
////                //
////                // The reason is that our former CORS implementation accidentally allowed POST. People doing
////                // browser-based tracing relied on this, so we can't remove it by default. In the future, we
////                // could split the collector's CORS policy into a different property, still allowing POST
////                // with content-type by default.
////                .allowRequestMethods(HttpMethod.GET, HttpMethod.POST)
////                .allowRequestHeaders(HttpHeaderNames.CONTENT_TYPE);
////        return builder -> builder.decorator(corsBuilder::build);
////    }
//
//    @Bean
//    @ConditionalOnMissingBean(CollectorSampler.class)
//    CollectorSampler traceIdSampler(@Value("${zipkin.collector.sample-rate:1.0}") float rate) {
//        return CollectorSampler.create(rate);
//    }
//
////    @Bean
////    @ConditionalOnMissingBean(CollectorMetrics.class)
////    CollectorMetrics metrics(MeterRegistry registry) {
////        return new ActuateCollectorMetrics(registry);
////    }
//
//    @Bean
//    public MeterRegistryCustomizer meterRegistryCustomizer() {
//        return registry ->
//                registry
//                        .config()
//                        .meterFilter(
//                                MeterFilter.deny(
//                                        id -> {
//                                            String uri = id.getTag("uri");
//                                            return uri != null
//                                                    && (uri.startsWith("/actuator")
//                                                    || uri.startsWith("/metrics")
//                                                    || uri.startsWith("/health")
//                                                    || uri.startsWith("/favicon.ico")
//                                                    || uri.startsWith("/prometheus"));
//                                        }));
//    }
//
//    @Configuration
//    @ConditionalOnSelfTracing
//    static class TracingStorageComponentEnhancer implements BeanPostProcessor {
//
//        @Autowired(required = false)
//        Tracing tracing;
//
//        @Override
//        public Object postProcessBeforeInitialization(Object bean, String beanName) {
//            return bean;
//        }
//
//        @Override
//        public Object postProcessAfterInitialization(Object bean, String beanName) {
//            if (tracing == null) return bean;
//            if (bean instanceof StorageComponent) {
//                return new TracingStorageComponent(tracing, (StorageComponent) bean);
//            }
//            return bean;
//        }
//    }
//
//    /**
//     * This is a special-case configuration if there's no StorageComponent of any kind. In-Mem can
//     * supply both read apis, so we add two beans here.
//     */
//    @Configuration
//    @ConditionalOnMissingBean(StorageComponent.class)
//    static class InMemoryConfiguration {
//        @Bean
//        StorageComponent storage(
//                @Value("${zipkin.storage.strict-trace-id:true}") boolean strictTraceId,
//                @Value("${zipkin.storage.search-enabled:true}") boolean searchEnabled,
//                @Value("${zipkin.storage.mem.max-spans:500000}") int maxSpans,
//                @Value("${zipkin.storage.autocomplete-keys:}") List<String> autocompleteKeys) {
//            return InMemoryStorage.newBuilder()
//                    .strictTraceId(strictTraceId)
//                    .searchEnabled(searchEnabled)
//                    .maxSpanCount(maxSpans)
//                    .autocompleteKeys(autocompleteKeys)
//                    .build();
//        }
//    }
//
//    static final class StorageTypeMemAbsentOrEmpty implements Condition {
//        @Override
//        public boolean matches(ConditionContext condition, AnnotatedTypeMetadata ignored) {
//            String storageType = condition.getEnvironment().getProperty("zipkin.storage.type");
//            if (storageType == null) return true;
//            storageType = storageType.trim();
//            if (storageType.isEmpty()) return true;
//            return storageType.equals("mem");
//        }
//    }
//}
//
