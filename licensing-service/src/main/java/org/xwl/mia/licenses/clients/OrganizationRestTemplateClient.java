package org.xwl.mia.licenses.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.xwl.mia.licenses.config.ServiceConfig;
import org.xwl.mia.licenses.model.Organization;
import org.xwl.mia.licenses.repository.OrganizationRedisRepository;

import brave.Span;
import brave.Tracer;

@Component
public class OrganizationRestTemplateClient {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);
	
    @Autowired
    ServiceConfig config;
    
    @Autowired
    Tracer tracer;
    
    @Autowired
    OAuth2RestTemplate restTemplate;

    @Autowired
    OrganizationRedisRepository orgRedisRepo;
    
    private Organization checkRedisCache(String organizationId) {
    	Span span = tracer.newTrace().name("getOrgDataFromRedis").start();
        try {
            return orgRedisRepo.findOrganization(organizationId);
        }
        catch (Exception ex){
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", organizationId, ex);
            return null;
        }finally {
        	span.tag("peer.service", "redis");
        	span.finish();
        }
    }

    private void cacheOrganizationObject(Organization org) {
        try {
            orgRedisRepo.saveOrganization(org);
        }catch (Exception ex){
            logger.error("Unable to cache organization {} in Redis. Exception {}", org.getId(), ex);
        }
    }    
    
    
    public Organization getOrganization(String organizationId){
   
    	Organization org = checkRedisCache(organizationId);    	
        if (org!=null){
        	System.out.println("name is ::: " + org.getName());
            logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, org);
            return org;
        }

        logger.debug("Unable to locate organization from the redis cache: {}.", organizationId);
    	
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(//这里的问题： 能否再度通过服务发现去发现zuul服务实例去调用组织服务，但是在引导类OAuth2RestTemplate处使用LoadBalanced注解会出错
//                		"http://zuulservice/api/organization/v1/organizations/{organizationId}",
                        config.getZuulServerAddr() + "/api/organization/v1/organizations/{organizationId}",
//                		  "http://192.168.43.104:9182/v1/organizations/{organizationId}",
//                		  "http://organizationservice/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        org = restExchange.getBody();

        if (org!=null) {
            cacheOrganizationObject(org);
        }
        
        return org;
    }
}
