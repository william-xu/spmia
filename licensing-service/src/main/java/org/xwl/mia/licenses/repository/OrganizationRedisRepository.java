package org.xwl.mia.licenses.repository;

import org.xwl.mia.licenses.model.Organization;

public interface OrganizationRedisRepository {
    void saveOrganization(Organization org);
    void updateOrganization(Organization org);
    void deleteOrganization(String organizationId);
    Organization findOrganization(String organizationId);
}
