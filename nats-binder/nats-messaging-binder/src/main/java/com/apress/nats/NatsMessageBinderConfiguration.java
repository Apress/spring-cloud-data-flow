package com.apress.nats;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties(NatsProperties.class)
@Import(NatsConfiguration.class)
@Configuration
public class NatsMessageBinderConfiguration {


    @Bean
    public NatsMessageBinderProvisioningProvider natsMessageBinderProvisioningProvider(){
        return new NatsMessageBinderProvisioningProvider();
    }

    @Bean
    public NatsMessageBinder natsMessageBinder(NatsMessageBinderProvisioningProvider natsMessageBinderProvisioningProvider, NatsTemplate natsTemplate){
        return new NatsMessageBinder(null,natsMessageBinderProvisioningProvider, natsTemplate);
    }
}
