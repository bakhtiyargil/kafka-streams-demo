package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.FunctionProperties;
import org.springframework.cloud.function.context.config.RoutingFunction;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterMessageBrokerRouters {

    private final FunctionCatalog functionCatalog;
    private final FunctionProperties functionProperties;
    private final BindingServiceProperties bindingServiceProperties;
    private final ConfigurableListableBeanFactory configurableListableBeanFactory;

    @PostConstruct
    void registerRouters() {
        bindingServiceProperties.getBindings().keySet().stream()
                .filter(it -> it.contains(Messaging.ROUTER_BINDING_SUFFIX))
                .map(it -> it.replaceAll(Messaging.ROUTER_BINDING_SUFFIX + "\\d+", ""))
                .forEach(module -> {
                    final String beanName = module + "Router";
                    configurableListableBeanFactory.registerSingleton(beanName, createRoutingFunction());
                });
    }

    private RoutingFunction createRoutingFunction() {
        return new RoutingFunction(
                functionCatalog,
                functionProperties,
                new BeanFactoryResolver(configurableListableBeanFactory),
                new EventTypeMessageRoutingCallback()
        );
    }

}
