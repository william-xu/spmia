package com.xwl.spmia.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
@EnableResourceServer
//@EnableOAuth2Client
@EnableDiscoveryClient
@EnableBinding(Source.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Sampler defaultSampler() {
    	return Sampler.ALWAYS_SAMPLE;
    }
}

/*
{
	
	"id":"442adb6e-fa58-47f3-9ca2-ed1fecdfe86c",
	"name": "xwlscom_like_success",
	"contactEmail":"xwlxxx@999.com",
	"contactName":"Doug Drewry",
	"contactPhone":"9394-9967"
	
}


{	
  "id":"442adb6e-fa58-47f3-9ca2-ed1fecdfe86c",
  "name": "xwlscom_like_success",
  "contactEmail":"xwlxxx@999.com",
  "contactName":"Doug Drewry",
  "contactPhone":"9394-9967"	
}

{
  "id":"e254f8c-c442-4ebe-a82a-e2fc1d1ff78a",
  "name": "customer-crm-co",
  "contactEmail":"mark.balster@custcrmco.com",
  "contactName":"Mark Balster",
  "contactPhone":"823-555-1212"	
}



*/