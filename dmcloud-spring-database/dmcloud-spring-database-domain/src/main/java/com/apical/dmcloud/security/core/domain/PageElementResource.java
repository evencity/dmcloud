package com.apical.dmcloud.security.core.domain;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dayatang.utils.Assert;

import com.apical.dmcloud.security.core.IdentifierIsExistedException;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 页面元素全权限资源，它用于页面上的元素（按钮Button，标题title等等）。
 *
 * @author lucas
 */

@Entity
@DiscriminatorValue("PAGE_ELEMENT_RESOURCE")
public class PageElementResource extends SecurityResource {

    private static final long serialVersionUID = 8933589588651981397L;

    @Column(name = "IDENTIFIER")
    private String identifier;

    protected PageElementResource() {
    }

    public PageElementResource(String name, String identifier) {
        super(name);
        Assert.notEmpty(identifier, "identifier cannot be empty.");
        isIdentifierExisted(identifier);
        this.identifier = identifier;
    }

    @Override
    public void save() {
        super.save();
    }

    public void changeIdentifier(String identifier) {
        Assert.notEmpty(identifier, "identifier cannot be empty.");
        if (!identifier.equals(this.getIdentifier())) {
            isIdentifierExisted(identifier);
            this.identifier = identifier;
            save();
        }
    }

    public static PageElementResource getBy(String securityResourceName) {
        return getRepository().createCriteriaQuery(PageElementResource.class)
                .eq("name", securityResourceName)
                .singleResult();
    }

    public static PageElementResource getBy(Long id) {
        return PageElementResource.get(PageElementResource.class, id);
    }

    public static boolean hasIdentifier(String identifier) {
        return getby(identifier) != null;
    }
    
    public static List<PageElementResource> findPageElementResourceInPage(
    		String resourceName,
    		String resourceIdentifier,
    		String resourceDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _pageElementResource FROM PageElementResource _pageElementResource WHERE 1=1");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
    	assemblePageElementResourceJpqlAndConditionValues(resourceName,
    			resourceIdentifier,
    			resourceDescription,
    			jpql,
    			"_resource",
    			conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
    			.setParameters(conditionVals)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
    }
    
    public static List<PageElementResource> findPageElementResourceByAuthorityIdInPage(long authorityId,
    		Class<? extends Authority> authorityType,
    		String resourceName,
    		String resourceIdentifier,
    		String resourceDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _resource FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE _authority.id = :authorityId AND TYPE(_resource)= :resourceType AND TYPE(_authority) = :authorityType GROUP BY _resource.id ORDER BY _resource.id");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
    	conditionVals.put("resourceType", PageElementResource.class);
		conditionVals.put("authorityType", authorityType);
		conditionVals.put("authorityId", authorityId);
		
    	assemblePageElementResourceJpqlAndConditionValues(resourceName,
    			resourceIdentifier,
    			resourceDescription,
    			jpql,
    			"_resource",
    			conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
    			.setParameters(conditionVals)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
    }
    
    public static List<PageElementResource> findNotGrantPageElementResourceByAuthorityIdInPage(long authorityId,
    		Class<? extends Authority> authorityType,
    		String resourceName,
    		String resourceIdentifier,
    		String resourceDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder("SELECT _pageElementResource FROM PageElementResource _pageElementResource WHERE _pageElementResource.id NOT IN(SELECT _resource.id FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE TYPE(_resource) = :resourceType AND _authority.id = :authorityId ) ");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
    	conditionVals.put("resourceType", PageElementResource.class);
		conditionVals.put("authorityId", authorityId);
		
    	assemblePageElementResourceJpqlAndConditionValues(resourceName,
    			resourceIdentifier,
    			resourceDescription,
    			jpql,
    			"_resource",
    			conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
    			.setParameters(conditionVals)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
    }
    
    /**
     * 根据页面元素资源id，分页查询权限信息
     * @param resourceId 页面元素资源id
     * @param permissionName 权限名称（不需要此条件时，请设置为null）
     * @param permissionIdentifier 权限标识符（不需要此条件时，请设置为null）
     * @param permissionDescription 权限描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findPermissionsInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount,
    		int pageSize) {
    	return Permission.findAllPermissionsByPageElementResourceIdInPage(resourceId,
    			permissionName, permissionIdentifier, permissionDescription,
    			pageCount, pageSize);
    }
    
    /**
     * 根据页面元素资源id，分页查询未分配的权限信息
     * @param resourceId 页面元素资源id
     * @param permissionName 权限名称（不需要此条件时，请设置为null）
     * @param permissionIdentifier 权限标识符（不需要此条件时，请设置为null）
     * @param permissionDescription 权限描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findNotGrantPermissionsInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount,
    		int pageSize) {
    	return Permission.findAllNotGrantPermissionsByPageElementResourceIdInPage(resourceId,
    			permissionName, permissionIdentifier, permissionDescription,
    			pageCount, pageSize);
    }

    private static PageElementResource getby(String identifier) {
        return getRepository()
                .createCriteriaQuery(PageElementResource.class)
                .eq("identifier", identifier)
                .singleResult();
    }

    private void isIdentifierExisted(String identifier) {
        if (null != getby(identifier)) {
            throw new IdentifierIsExistedException("pageElemntResource identifier existed.");
        }
    }
    
    private static void assemblePageElementResourceJpqlAndConditionValues(String resourceName,
    		String resourceIdentifier,
    		String resourceDescription,
    		StringBuilder jpql,
    		String conditionPrefix,
    		Map<String, Object> conditionVals) {
        String andCondition = " AND " + conditionPrefix;
        
        if (!StringUtils.isBlank(resourceName)) {
            jpql.append(andCondition);
            jpql.append(".name LIKE :name");
            conditionVals.put("name", MessageFormat.format("%{0}%", resourceName));
        }
        
        if (!StringUtils.isBlank(resourceIdentifier)) {
            jpql.append(andCondition);
            jpql.append(".identifier LIKE :identifier");
            conditionVals.put("identifier", MessageFormat.format("%{0}%", resourceIdentifier));
        }
        
        if (!StringUtils.isBlank(resourceDescription)) {
            jpql.append(andCondition);
            jpql.append(".description LIKE :description");
            conditionVals.put("description", MessageFormat.format("%{0}%", resourceDescription));
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(getName())
                .append(getIdentifier())
                .append(getDescription())
                .build();
    }

    public String getIdentifier() {
        return identifier;
    }
}