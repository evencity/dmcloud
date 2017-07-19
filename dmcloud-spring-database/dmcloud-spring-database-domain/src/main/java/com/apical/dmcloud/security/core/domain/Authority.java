package com.apical.dmcloud.security.core.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dayatang.utils.Assert;
import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.security.core.CorrelationException;
import com.apical.dmcloud.security.core.NameIsExistedException;

/**
 * 授权是是一个抽象的概念，是角色{@link Role}和权限{@link Permission}共同的基类。
 * 它代表某种权限（Permission）或权限集合（Role），可被授予Actor(即对Actor授予Authority)。
 * 它代表一系列的可执行操作或责任，用于限定您再软件系统中能做什么。不能做什么。
 *
 * @author lucas
 */

@Entity
@Table(name = "ks_authorities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.STRING)
public abstract class Authority extends SecurityAbstractEntity {

    private static final long serialVersionUID = -5570169700634882013L;

    /**
     * 名称
     */
    @NotNull
    @Column(name = "NAME")
    private String name;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    protected Authority() {
    }

    public Authority(String name) {
        Assert.notBlank(name,"name cannot be empty.");
        isExistedName(name);
        this.name = name;
    }

    /**
     * 如果授权中心里面有授权，表示授权中心中有参与者和授权。那么授权就不能撤销。
     * 如果资源分配中有授权，那么需要撤销资源分配。最后才是撤销自己。
     */
    @Override
    public void remove() {
        if (!Authorization.findByAuthority(this).isEmpty()) {
            throw new CorrelationException("authority has actor, so can't remove authority.");
        }
        for (ResourceAssignment resourceAssignment : ResourceAssignment.findByAuthority(this)) {
            resourceAssignment.remove();
        }
        super.remove();
    }

    /**
     * 通过授权名称和授权的真实类型得到授权或者授权子类。
     *
     * @param name 授权名称
     * @param <T>  授权子类
     * @return 授权或者授权子类
     */
    public <T extends Authority> T getByName(String name) {
    	String query = "select _authority from Authority _authority where TYPE(_authority) = :authorityType"
    			+ " and _authority.name = :name";
        T authority = getRepository().createJpqlQuery(query)
        		.addParameter("authorityType", this.getClass())
        		.addParameter("name", name)
        		.singleResult();
        return authority;
    }

    /**
     * 授权分配权限资源。
     *
     * @param securityResource 权限资源
     */
    public void addSecurityResource(SecurityResource securityResource) {
        new ResourceAssignment(this, securityResource).save();
    }

    /**
     * 授权分配多个权限资源。
     *
     * @param securityResources 权限资源集合
     */
    public void addSecurityResources(List<? extends SecurityResource> securityResources) {
        for (SecurityResource securityResource : securityResources) {
            this.addSecurityResource(securityResource);
        }
    }

    /**
     * 从授权中撤销权限资源。
     *
     * @param securityResource 权限资源
     */
    public void terminateSecurityResource(SecurityResource securityResource) {
        ResourceAssignment resourceAssignment = ResourceAssignment.findByResourceInAuthority(
        		this, securityResource);
        if (resourceAssignment != null) {
            resourceAssignment.remove();
        }
    }

    /**
     * 从授权中撤销多个权限资源。
     *
     * @param securityResources 权限资源集合 {@link SecurityResource}
     */
    public void terminateSecurityResources(Set<? extends SecurityResource> securityResources) {
        for (SecurityResource securityResource : securityResources) {
            this.terminateSecurityResource(securityResource);
        }
    }

    /**
     * 改变授权的名称。
     *
     * @param name 授权名称
     */
    public void changeName(String name) {
        Assert.notBlank(name, "authority name cannot be empty.");
        if (!name.equals(this.getName())) {
            isExistedName(name);
            this.name = name;
            this.save();
        }
    }

    /**
     * 获取该授权信息的菜单资源
     * @return 菜单资源
     */
    public List<MenuResource> findMenuResourceByAuthority() {
        return ResourceAssignment.findMenuResourceByAuthority(this);
    }

    /**
     * 获取该授权信息的URL资源
     * @return URL资源
     */
    public List<UrlAccessResource> findUrlAccessResourceByAuthority() {
        return ResourceAssignment.findUrlAccessResourcesByAuthority(this);
    }

    /**
     * 获取多个授权信息的菜单资源
     * @param authorities 授权信息
     * @return 菜单资源
     */
    public static List<MenuResource> findMenuResourceByAuthorities(Set<Authority> authorities) {
        return ResourceAssignment.findMenuResourceByAuthorities(authorities);
    }

    /**
     * 通过id，来获取授权信息
     * @param authorityId 授权信息id
     * @return 授权信息
     */
    @SuppressWarnings("unchecked")
	public static <T extends Authority> T getById(Long authorityId) {
        return (T) Authority.get(Authority.class, authorityId);
    }

    /**
     * 该授权信息是否有页面资源
     * @param authorities 授权信息
     * @param identifier 资源标识
     * @return true表示有页面资源，false则相反
     */
    public static boolean checkHasPageElementResource(Set<Authority> authorities,
    		String identifier) {
    	if(authorities.size() < 1)
    	{
    		return false;
    	}
    	
    	String sql = "select count(a.ID) from ks_resourceassignments a,ks_securityresources s where a.AUTHORITY_ID in (:ids)"
    			+ " and a.SECURITYRESOURCE_ID=s.ID and CATEGORY='PAGE_ELEMENT_RESOURCE'"
    			+ " and IDENTIFIER=:identifier";
    	
    	List<Long> ids = new ArrayList<Long>();
    	for(Authority a : authorities)
    	{
    		ids.add(a.getId());
    	}
    	
        BigInteger count = getRepository().createSqlQuery(sql)
                .addParameter("ids", ids)
                .addParameter("identifier", identifier)
                .singleResult();
        if(count.longValue() > 0)
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }

	/*------------- Private helper methods  -----------------*/

    private void isExistedName(String name) {
        if (getByName(name) != null) {
            throw new NameIsExistedException("authority name existed.");
        }
    }

    @Override
    public String[] businessKeys() {
        return new String[]{"name"};
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(name)
                .append(description)
                .build();
    }

    /*-------------- getter setter methods  ------------------*/
    /**
     * 获取名称
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取描述信息
     * @return 描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述信息
     * @param description 描述信息
     */
    public void setDescription(String description) {
        this.description = description;
    }

}