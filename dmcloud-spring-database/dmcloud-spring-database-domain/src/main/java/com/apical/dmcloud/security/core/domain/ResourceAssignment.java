package com.apical.dmcloud.security.core.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dayatang.utils.Assert;

import com.apical.dmcloud.AbstractIDEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 资源分配，它表示系统中将要在权限资源的基础上进行授权的控制。
 *
 * @author lucas
 */

@Entity
@Table(name = "ks_resourceassignments")
@NamedQueries({
        @NamedQuery(name = "ResourceAssignment.findByResourceTypeAndAuthority", query = "SELECT _resourceAssignment FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE TYPE(_resource) = :resourceType AND TYPE(_authority) = :authorityType")
})
public class ResourceAssignment extends AbstractIDEntity {

    /**
	 * serialVersionUID = -6269421333904783067L
	 */
	private static final long serialVersionUID = -6269421333904783067L;

	@ManyToOne
    @JoinColumn(name = "AUTHORITY_ID")
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "SECURITYRESOURCE_ID")
    private SecurityResource resource;

    protected ResourceAssignment() {
    }

    public ResourceAssignment(Authority authority, SecurityResource resource) {
        Assert.notNull(authority, "authority cannot be empty.");
        Assert.notNull(resource, "resource cannot be empty.");
        this.authority = authority;
        this.resource = resource;
    }

    @Override
    public void save() {
        if (existed(authority, resource)) {
            return;
        }
        super.save();
    }

    @Override
    public void remove() {
        super.remove();
    }

    /**
     * 根据授权和资源，来查找授权中心
     * @param authority 授权
     * @param resource 资源
     * @return 授权中心
     */
    public static ResourceAssignment findByResourceInAuthority(Authority authority, SecurityResource resource) {
        return getRepository().createCriteriaQuery(ResourceAssignment.class)
                .eq("authority", authority)
                .eq("resource", resource)
                .singleResult();
    }

    public static List<ResourceAssignment> findByAuthority(Authority authority) {
        Set<Authority> authorities = getAuthoritiesByAuthority(authority);
        return getRepository().createCriteriaQuery(ResourceAssignment.class)
                .in("authority", authorities)
                .asc("id")
                .list();
    }

    /**
     * 很奇怪~ 排序规则是变化的，所以强制使用id升序返回。
     */
    public static List<ResourceAssignment> findByResource(SecurityResource resource) {
        return getRepository().createCriteriaQuery(ResourceAssignment.class)
                .eq("resource", resource)
                .asc("id")
                .list();
    }

    public static List<MenuResource> findMenuResourceByAuthorities(Set<? extends Authority> authorities) {
    	if((authorities == null) || (authorities.size() < 1))
    	{
    		return new ArrayList<MenuResource>();
    	}
    	
//    	return getRepository().createNamedQuery("ResourceAssignment.findSecurityResourcesByAuthorities")
//                .addParameter("authorities", authorities)
//                .addParameter("resourceType", MenuResource.class)
//                .list();
    	
    	List<Long> ids = new ArrayList<Long>();
    	for(Authority a : authorities)
    	{
    		ids.add(a.getId());
    	}
    	
    	String sql = "select distinct r.*,p.PARENT_ID from ks_securityresources r left outer join ks_menu_resource_relation p"
    			+ " on r.ID=p.CHILD_ID,ks_resourceassignments a,ks_authorities u where u.ID in (:ids)"
    			+ " and u.ID=a.AUTHORITY_ID and r.ID = a.SECURITYRESOURCE_ID and r.CATEGORY='MENU_RESOURCE'"
    			+ " GROUP by r.ID order by r.ID";
    	return getRepository().createSqlQuery(sql)
    			.addParameter("ids", ids)
    			.setResultEntityClass(MenuResource.class)
    			.list();
    }

    public static List<MenuResource> findMenuResourceByAuthority(Authority authority) {
        Set<Authority> authorities = getAuthoritiesByAuthority(authority);
        return findMenuResourceByAuthorities(authorities);
    }
    
    public static List<UrlAccessResource> findUrlAccessResourcesByAuthority(Authority authority) {
        Set<Authority> authorities = getAuthoritiesByAuthority(authority);
        return findUrlAccessResourcesByAuthorities(authorities);
    }

    public static List<UrlAccessResource> findUrlAccessResourcesByAuthorities(Set<? extends Authority> authorities) {
    	if((authorities == null) || (authorities.size() < 1))
    	{
    		return new ArrayList<UrlAccessResource>();
    	}
    	
//    	return getRepository().createNamedQuery("ResourceAssignment.findSecurityResourcesByAuthorities")
//                .addParameter("authorities", authorities)
//                .addParameter("resourceType", UrlAccessResource.class)
//                .list();
    	
    	List<Long> ids = new ArrayList<Long>();
    	for(Authority a : authorities)
    	{
    		ids.add(a.getId());
    	}
    	
    	String sql = "select distinct r.* from ks_securityresources r,ks_resourceassignments a,ks_authorities u where"
    			+ " u.ID in (:ids) and u.ID=a.AUTHORITY_ID and r.ID = a.SECURITYRESOURCE_ID"
    			+ " and r.CATEGORY='URL_ACCESS_RESOURCE' GROUP by r.ID order by r.ID";
    	return getRepository().createSqlQuery(sql)
    			.addParameter("ids", ids)
    			.setResultEntityClass(UrlAccessResource.class)
    			.list();
    }

    public static List<Role> findRoleBySecurityResource(SecurityResource resource) {
    	String sql = "select distinct u.* from ks_securityresources r,ks_resourceassignments a,ks_authorities u where"
    			+ " r.ID=:id and r.ID = a.SECURITYRESOURCE_ID and u.ID=a.AUTHORITY_ID"
    			+ " and u.CATEGORY='ROLE' GROUP by u.ID order by u.ID";
    	
    		return getRepository().createSqlQuery(sql)
    				.addParameter("id", resource.getId())
    				.setResultEntityClass(Role.class)
    				.list();
    }

    public static List<Permission> findPermissionBySecurityResource(SecurityResource resource) {
    	String sql = "select distinct u.* from ks_securityresources r,ks_resourceassignments a,ks_authorities u where"
    			+ " r.ID=:id and r.ID = a.SECURITYRESOURCE_ID and u.ID=a.AUTHORITY_ID"
    			+ " and u.CATEGORY='PERMISSION' GROUP by u.ID order by u.ID";
    	
    		return getRepository().createSqlQuery(sql)
    				.addParameter("id", resource.getId())
    				.setResultEntityClass(Permission.class)
    				.list();
    }

    /**
     * @param resourceAssignmentId id for ResourceAssignment cannot null.
     */
    public static ResourceAssignment getById(Long resourceAssignmentId) {
        return ResourceAssignment.get(ResourceAssignment.class, resourceAssignmentId);
    }

    private static Set<Authority> getAuthoritiesByAuthority(Authority authority) {
        Set<Authority> results = new HashSet<Authority>();
        results.add(authority);
        if (authority instanceof Role) {
            Role role = (Role) authority;
            results.addAll(role.getPermissions());
        }
        return results;
    }

    private boolean existed(Authority authority, SecurityResource resource) {
        return findByResourceInAuthority(authority, resource) != null;
    }

    @Override
    public String[] businessKeys() {
        return new String[]{"resource", "authority"};
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(resource)
                .append(authority)
                .toString();
    }

    public SecurityResource getResource() {
        return resource;
    }

    public Authority getAuthority() {
        return authority;
    }
}
