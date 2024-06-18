////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package cn.com.hellowood.k8sservice.zipkin.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.linecorp.armeria.common.HttpMethod;
//import com.linecorp.armeria.common.HttpResponse;
//import com.linecorp.armeria.common.HttpStatus;
//import com.linecorp.armeria.common.MediaType;
//import com.linecorp.armeria.internal.shaded.guava.collect.ImmutableList;
//import com.linecorp.armeria.internal.shaded.guava.collect.ImmutableMap;
//import com.linecorp.armeria.internal.shaded.guava.collect.ImmutableSet;
//import com.linecorp.armeria.internal.shaded.guava.collect.Streams;
//import com.linecorp.armeria.server.PathMapping;
//import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
//import org.springframework.boot.actuate.endpoint.EndpointFilter;
//import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
//import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
//import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
//import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
//import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
//import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
//import org.springframework.boot.actuate.endpoint.web.Link;
//import org.springframework.boot.actuate.endpoint.web.PathMapper;
//import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
//import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
//import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpointDiscoverer;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//
//@Configuration
//@AutoConfigureAfter({EndpointAutoConfiguration.class})
//@EnableConfigurationProperties({WebEndpointProperties.class})
//public class ArmeriaSpringActuatorAutoConfiguration {
//    private static final List<String> MEDIA_TYPES = ImmutableList.of("application/vnd.spring-boot.actuator.v2+json", "application/json");
//    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//
//    public ArmeriaSpringActuatorAutoConfiguration() {
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    EndpointMediaTypes endpointMediaTypes() {
//        return new EndpointMediaTypes(MEDIA_TYPES, MEDIA_TYPES);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean({WebEndpointsSupplier.class})
//    WebEndpointDiscoverer webEndpointDiscoverer(ApplicationContext applicationContext, ParameterValueMapper parameterValueMapper, EndpointMediaTypes endpointMediaTypes, ObjectProvider<PathMapper> endpointPathMappers, ObjectProvider<OperationInvokerAdvisor> invokerAdvisors, ObjectProvider<EndpointFilter<ExposableWebEndpoint>> filters) {
//        return new WebEndpointDiscoverer(applicationContext, parameterValueMapper, endpointMediaTypes, (List)endpointPathMappers.orderedStream().collect(ImmutableList.toImmutableList()), (Collection)invokerAdvisors.orderedStream().collect(ImmutableList.toImmutableList()), (Collection)filters.orderedStream().collect(ImmutableList.toImmutableList()));
//    }
//
//    @Bean
//    ArmeriaServerConfigurator actuatorServerConfigurator(WebEndpointsSupplier endpointsSupplier, EndpointMediaTypes mediaTypes, WebEndpointProperties properties) {
//        EndpointMapping endpointMapping = new EndpointMapping(properties.getBasePath());
//        Collection<ExposableWebEndpoint> endpoints = endpointsSupplier.getEndpoints();
//        return (sb) -> {
//            endpoints.stream().flatMap((endpoint) -> {
//                return endpoint.getOperations().stream();
//            }).forEach((operation) -> {
//                WebOperationRequestPredicate predicate = operation.getRequestPredicate();
//                sb.service(getPathMapping(predicate.getHttpMethod().name(), endpointMapping.createSubPath(predicate.getPath()), predicate.getConsumes(), predicate.getProduces()), new WebOperationHttpService(operation));
//            });
//            if (StringUtils.hasText(endpointMapping.getPath())) {
//                PathMapping mapping = getPathMapping(HttpMethod.GET.name(), endpointMapping.getPath(), ImmutableList.of(), mediaTypes.getProduced());
//                sb.service(mapping, (ctx, req) -> {
//                    Map<String, Link> links = (new EndpointLinksResolver(endpoints)).resolveLinks(req.path());
//                    return HttpResponse.of(HttpStatus.OK, MediaType.JSON, OBJECT_MAPPER.writeValueAsBytes(ImmutableMap.of("_links", links)));
//                });
//            }
//
//        };
//    }
//
//    private static PathMapping getPathMapping(String method, String path, Collection<String> consumes, Collection<String> produces) {
//        return PathMapping.of(path).withHttpHeaderInfo(ImmutableSet.of(HttpMethod.valueOf(method)), convertMediaTypes(consumes), convertMediaTypes(produces));
//    }
//
//    private static List<MediaType> convertMediaTypes(Iterable<String> mediaTypes) {
//        return (List)Streams.stream(mediaTypes).map(MediaType::parse).collect(ImmutableList.toImmutableList());
//    }
//}
