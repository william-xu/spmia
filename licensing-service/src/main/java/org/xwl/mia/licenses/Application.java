package org.xwl.mia.licenses;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.xwl.mia.licenses.config.ServiceConfig;
import org.xwl.mia.licenses.events.CustomChannels;
import org.xwl.mia.licenses.utils.UserContextInterceptor;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableResourceServer
@EnableCircuitBreaker
@EnableEurekaClient
@EnableBinding(CustomChannels.class)
//@EnableOAuth2Client
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	@Autowired
	private ServiceConfig serviceConfig;

    @Bean
    public Sampler defaultSampler() {
    	return Sampler.ALWAYS_SAMPLE;
    }
	
	
//	@LoadBalanced
//	@Bean
//	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
//			OAuth2ProtectedResourceDetails details) {
//		return new OAuth2RestTemplate(details, oauth2ClientContext);
//	}

//	@LoadBalanced
//	@Bean
//	public RestTemplate getRestTemplate() {
//		return new RestTemplate();
//	}
	
		
//	@LoadBalanced //这个注解告诉Spring Cloud 创建一个支持Ribbon的RestTemplate类
	@Primary
	@Bean
	public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
		OAuth2RestTemplate restTemplate = factory.getUserInfoRestTemplate();
		List interceptors = restTemplate.getInterceptors();
		if(interceptors == null) {
			restTemplate.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		}else {
			interceptors.add(new UserContextInterceptor());
			restTemplate.setInterceptors(interceptors);
		}
	    return restTemplate;
	}	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
//	@StreamListener(Sink.INPUT)
//	public void loggerSink(OrganizationChangeModel orgChange) {
//		logger.debug("Received an event for organization id {}", orgChange.getOrganizationId());
//	}
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {		
		System.out.println(">>>>>> " + serviceConfig.getRedisServer() + ":" + serviceConfig.getRedisPort());
		RedisStandaloneConfiguration rscf = new RedisStandaloneConfiguration(serviceConfig.getRedisServer(), serviceConfig.getRedisPort());
		JedisConnectionFactory factory = new JedisConnectionFactory(rscf);
		return factory;
	}
	
//	@Bean
//	public LettuceConnectionFactory redisConnectionFactory() {
//		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(serviceConfig.getRedisServer(), serviceConfig.getRedisPort()));
//	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}
	
}
