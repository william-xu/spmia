package org.xwl.mia.organization.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xwl.mia.organization.events.source.SimpleSourceBean;
import org.xwl.mia.organization.model.Organization;
import org.xwl.mia.organization.repository.OrganizationRepository;

import brave.Span;
import brave.Tracer;

@Service
public class OrganizationService {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);
	
    @Autowired
    private OrganizationRepository orgRepository;

    @Autowired
    SimpleSourceBean simpleSourceBean;
    
    @Autowired
    private Tracer tracer;

	public Organization getOrg(String organizationId) {

		Span newSpan = tracer.newTrace().name("xxxWWW").start();

		logger.debug("In the organizationService.getOrg() call");

		try {
			logger.info("before find by id ...");
			return orgRepository.findById(organizationId).get();
		} finally {
			logger.info("in the fi ...");
			newSpan.tag("peer.service", "postgres");
//			newSpan.annotate(" what this can do ? ");
			newSpan.finish();
		}

	}

    public void saveOrg(Organization org){
        org.setId( UUID.randomUUID().toString());
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("SAVE", org.getId());
    }

    public void updateOrg(Organization org){
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("UPDATE", org.getId());
    }

    public void deleteOrg(String organizationId){
        orgRepository.deleteById( organizationId);
    }
}
