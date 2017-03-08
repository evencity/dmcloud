package com.apical.dmcloud.security.core.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.dayatang.utils.Assert;

import com.apical.dmcloud.security.core.UrlIsExistedException;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * URL访问权限资源，他表示页面请求服务端的URL。
 *
 * @author lucas
 */

@Entity
@DiscriminatorValue("URL_ACCESS_RESOURCE")
public class UrlAccessResource extends SecurityResource {

    private static final long serialVersionUID = -9116913523532845475L;

    @Column(name = "URL")
    private String url;

    protected UrlAccessResource() {
    }

    public UrlAccessResource(String name, String url) {
        super(name);
        checkUrl(url);
        isExistUrl(url);
        this.url = url;
    }

    private void checkUrl(String url) {
        Assert.notBlank(url, "url cannot be empty.");
    }

    @Override
    public void save() {
        super.save();
    }

    public void changeUrl(String url) {
        checkUrl(url);
        if (!url.equals(this.getUrl())) {
            isExistUrl(url);
            this.url = url;
            this.save();
        }
    }

    /**
     * TODO IllegalArgumentException hibernate 本身就有 直接捕获？
     * @param id id号
     * @return URL访问权限资源
     */
    public static UrlAccessResource getBy(Long id) {
        return UrlAccessResource.get(UrlAccessResource.class, id);
    }

    public static List<UrlAccessResource> findAllUrlAccessResources() {
    	String query = "SELECT _securityResource  FROM SecurityResource _securityResource WHERE"
    			+ " TYPE(_securityResource) = :securityResourceType";
        return getRepository().createJpqlQuery(query)
                .addParameter("securityResourceType", UrlAccessResource.class)
                .list();
    }
    
    public static List<UrlAccessResource> findAllUrlAccessResourcesInPage(String url,
    		String urlName,
    		String urlDescription,
    		int pageCount,
    		int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _urlAccessResource FROM UrlAccessResource _urlAccessResource where 1 = 1");
		Map<String, Object> conditionVals = new HashMap<String, Object>();
		
		assembleUrlAccessResourceJpqlAndConditionValues(url,
				urlName,
				urlDescription,
				jpql,
				"_urlAccessResource",
				conditionVals);
		
        return getRepository()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
    }

    public static List<Role> findRoleBySecurityResource(UrlAccessResource resource) {
        return ResourceAssignment.findRoleBySecurityResource(resource);
    }
    
    /**
     * 分页查找角色已分配的URL访问资源集合。
     * @param roleId 角色id
     * @param url url（不需要此条件时，请设置为null）
     * @param urlName url名称（不需要此条件时，请设置为null）
     * @param urlDescription URL描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return URL访问资源集合
     */
    public static List<UrlAccessResource> findAllUrlAccessResourcesByRoleIdInPage(long roleId,
    		String url,
    		String urlName,
    		String urlDescription,
    		int pageCount,
    		int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _resourceAssignment.resource FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE TYPE(_resource) = :resourceType AND _authority.id = :authorityId");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
    	conditionVals.put("resourceType", UrlAccessResource.class);
		conditionVals.put("authorityId", roleId);

		assembleUrlAccessResourceJpqlAndConditionValues(url,
				urlName, urlDescription,
    			jpql, "_resource", conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
    			.setParameters(conditionVals)
    			.setMaxResults(pageSize)
    			.setFirstResult((pageCount - 1) * pageSize)
				.list();
    }
    
    /**
     * 分页查找角色未分配的URL访问资源集合。
     * @param roleId 角色id
     * @param url url（不需要此条件时，请设置为null）
     * @param urlName url名称（不需要此条件时，请设置为null）
     * @param urlDescription URL描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return URL访问资源集合
     */
    public static List<UrlAccessResource> findAllNotGrantUrlAccessResourcesByRoleIdInPage(long roleId,
    		String url,
    		String urlName,
    		String urlDescription,
    		int pageCount,
    		int pageSize) {
    	StringBuilder jpql = new StringBuilder("SELECT _securityResource FROM SecurityResource _securityResource WHERE TYPE(_securityResource) = :resourceType AND _securityResource.id NOT IN (SELECT _resource.id FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE TYPE(_resource) = :resourceType AND _authority.id = :authorityId)");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
    	conditionVals.put("resourceType", UrlAccessResource.class);
		conditionVals.put("authorityId", roleId);
		
		assembleUrlAccessResourceJpqlAndConditionValues(url,
				urlName, urlDescription,
    			jpql, "_resource", conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
    			.setParameters(conditionVals)
    			.setMaxResults(pageSize)
    			.setFirstResult((pageCount - 1) * pageSize)
				.list();
    }
    
    /**
     * 根据url资源id，分页查询权限信息
     * @param resourceId url资源id
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
    	return Permission.findAllPermissionsByUrlAccessResourceIdInPage(resourceId,
    			permissionName, permissionIdentifier, permissionDescription,
    			pageCount, pageSize);
    }
    
    /**
     * 根据url资源id，分页查询未分配的权限信息
     * @param resourceId url资源id
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
    	return Permission.findAllNotGrantPermissionsByUrlAccessResourceIdInPage(resourceId,
    			permissionName, permissionIdentifier, permissionDescription,
    			pageCount,
    			pageSize);
    }
    
    private static void assembleUrlAccessResourceJpqlAndConditionValues(String url,
    		String urlName,
    		String urlDescription,
    		StringBuilder jpql,
    		String conditionPrefix,
    		Map<String, Object> conditionVals) {
        String andCondition = " AND " + conditionPrefix;
        
        if (!StringUtils.isBlank(urlName)) {
            jpql.append(andCondition);
            jpql.append(".name LIKE :name");
            conditionVals.put("name", MessageFormat.format("%{0}%", urlName));
        }
        
        if (!StringUtils.isBlank(urlDescription)) {
            jpql.append(andCondition);
            jpql.append(".description LIKE :description");
            conditionVals.put("description", MessageFormat.format("%{0}%", urlDescription));
        }
        
        if (!StringUtils.isBlank(url)) {
            jpql.append(andCondition);
            jpql.append(".url LIKE :url");
            conditionVals.put("url", MessageFormat.format("%{0}%", url));
        }
    }

    /**
     * @param url url of the UrlAccessResource, can't be null.
     * @return URL访问权限资源
     */
    protected UrlAccessResource findByUrl(String url) {
        checkUrl(url);
        return getRepository()
                .createCriteriaQuery(UrlAccessResource.class)
                .eq("url", url)
                .singleResult();
    }

    private void isExistUrl(String url) {
        if (findByUrl(url) != null) {
            throw new UrlIsExistedException("url existed.");
        }
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String[] businessKeys() {
        return new String[]{"name", "url"};
    }

}