package com.xwl.spmia.organization.events.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.xwl.spmia.organization.events.models.OrganizationChangeModel;
import com.xwl.spmia.organization.utils.UserContext;

@Component
public class SimpleSourceBean {
	private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);
	
	private Source source;
	@Autowired
	public SimpleSourceBean(Source source) {
		this.source = source;
	}
	
	public void publishOrgChange(String action, String orgId) {
		logger.debug("Sending Kafka message {} fro Organization Id: {}", action, orgId);
		OrganizationChangeModel change = new OrganizationChangeModel(OrganizationChangeModel.class.getTypeName(), action, orgId, UserContext.getCorrelationId());
		source.output().send(MessageBuilder.withPayload(change).build());
	}
}
