package com.apical.dmcloud.security.core.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dayatang.utils.Assert;

import com.apical.dmcloud.security.core.CorrelationException;
import com.apical.dmcloud.security.core.IdentifierIsExistedException;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限是一个抽象的概念，代表一项操作或责任，因此是授权的细粒度表现。
 *
 * @author lucas
 */

@Entity
@DiscriminatorValue("PERMISSION")
public class Permission extends Authority {

    private static final long serialVersionUID = 4631351008490511334L;

    /**
     * 权限标识符 例如：user:create
     */
    @NotNull
    @Column(name = "IDENTIFIER")
    private String identifier;

    //@ManyToMany(mappedBy = "permissions")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ks_role_permission_map",
            joinColumns = @JoinColumn(name = "PERMISSION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<Role>();

    protected Permission() {
    }

    public Permission(String name, String identifier) {
        super(name);
        Assert.notBlank(identifier, "identifier cannot be empty.");
        isIdentifierExisted(identifier);
        this.identifier = identifier;
    }

    public Permission getPermissionBy(String identifier) {
        return getRepository()
                .createCriteriaQuery(Permission.class)
                .eq("identifier", identifier)
                .singleResult();
    }

    /**
     * 由于permission 是细粒度权限。因此需要判断Permission 是否已经分配给SecurityResource子类，
     * 如果已经分配就不能再分配。getClass()能够拿到真实类型，例如传递的是menuResource 那么securityResource
     * 就是MenuResource类型。如果是同一个permission或者同一个资源就不能被分配。
     */
    @Override
    public void addSecurityResource(SecurityResource securityResource) {
        ResourceAssignment resourceAssignment = getRepository()
                .createNamedQuery("ResourceAssignment.findByResourceTypeAndAuthority")
                .addParameter("resourceType", securityResource.getClass())
                .addParameter("authorityType", this.getClass())
                .singleResult();
        if (resourceAssignment == null) {
            new ResourceAssignment(this, securityResource).save();
            return;
        }
        
        if (securityResource.equals(resourceAssignment.getResource()) || this.equals(resourceAssignment.getAuthority())) {
            throw new CorrelationException("permission or kind of resource existed.");
        }
        
        new ResourceAssignment(this, securityResource).save();
    }

    /**
     * 通过权限id，来获取权限信息
     * @param id 权限信息id
     * @return 权限信息
     */
    public static Permission getByPermissionId(Long id) {
        return Permission.get(Permission.class, id);
    }
    
    /**
     * 分页查询权限信息
     * @param permissionName 权限名称
     * @param permissionIdentifier 权限标识
     * @param permissionDescription 权限描述
     * @param pageCount 页数
     * @param pageSizze 页面大小
     * @return 权限信息
     */
    public static List<Permission> findAllPermissionsInPage(String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSizze) {
    	StringBuilder jpql = new StringBuilder("SELECT _permission FROM Permission _permission WHERE 1 = 1");
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
    	
    	assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_permission",
				conditionVals);

    	return getRepository()
    			.createJpqlQuery(jpql.toString())
        		.setParameters(conditionVals)
                .setMaxResults(pageSizze)
                .setFirstResult((pageCount - 1) * pageSizze)
                .list();
    }
    
    /**
     * 根据用户帐户名，来获取用户的权限信息
     * @param userAccount 用户帐户名
     * @return 权限信息
     */
    public static List<Permission> findAllPermissionsByUserAccount(String userAccount) {
    	String query = "select * from ks_authorities where ID in"
    			+ " (select a.AUTHORITY_ID from ks_authorizations a, cl_users u where u.ID=a.ACTOR_ID"
    			+ " and u.USER_ACCOUNT=:userAccount) and CATEGORY='PERMISSION'";
        return getRepository().createSqlQuery(query)
                .addParameter("userAccount", userAccount)
                .setResultEntityClass(Permission.class)
                .list();
    }
    
    /**
     * 分页查询用户的权限信息
     * @param userId 用户id
     * @param pageCount 页数
     * @param pageSizze 页面大小
     * @return 权限集合
     */
    public static List<Permission> findAllPermissionsByUserIdInPage(long userId,
    		int pageCount, int pageSizze) {
    	String query = "select * from ks_authorities where ID in"
    			+ " (select a.AUTHORITY_ID from ks_authorizations a where a.ACTOR_ID=:userId)"
    			+ " and CATEGORY='PERMISSION'";
    	return getRepository().createSqlQuery(query)
        		.addParameter("userId", userId)
                .setResultEntityClass(Permission.class)
                .setMaxResults(pageSizze)
                .setFirstResult((pageCount - 1) * pageSizze)
                .list();
    }
    
    /**
     * 分页查询角色拥有的权限集合
     * @param roleId 角色id
     * @param permissionName 权限名称（不需要此条件时，请设置为null）
     * @param permissionIdentifier 权限标识符（不需要此条件时，请设置为null）
     * @param permissionDescription 权限描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findAllPermissionsByRoleIdInPage(long roleId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _permission FROM Permission _permission JOIN _permission.roles _role WHERE _role.id = :roleId");
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
		conditionVals.put("roleId", roleId);
		
		assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_permission",
				conditionVals);

    	return getRepository().createJpqlQuery(jpql.toString())
        		.setParameters(conditionVals)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
    }
    
    /**
     * 分页查询角色未拥有的权限集合
     * @param roleId 角色id
     * @param permissionName 权限名称（不需要此条件时，请设置为null）
     * @param permissionIdentifier 权限标识符（不需要此条件时，请设置为null）
     * @param permissionDescription 权限描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findAllNotGrantPermissionsByRoleIdInPage(long roleId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _permission FROM Permission _permission WHERE _permission.id NOT IN(SELECT _permission.id FROM Permission _permission JOIN _permission.roles _role WHERE _role.id = :roleId)");
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
		conditionVals.put("roleId", roleId);
		
		assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_permission",
				conditionVals);

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
    public static List<Permission> findAllPermissionsByUrlAccessResourceIdInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _authority FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE TYPE(_authority) = :authorityType AND _resource.id = :resourceId");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
		conditionVals.put("resourceId", resourceId);
		conditionVals.put("authorityType", Permission.class);
    	
    	assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_authority",
				conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
        		.setParameters(conditionVals)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
    }
    
    /**
     * 根据url资源id，分页查询未分配的权限信息
     * @param resourceId url资源id
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findAllNotGrantPermissionsByUrlAccessResourceIdInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _authority FROM Authority _authority WHERE _authority.id NOT IN(SELECT _authority.id FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE _resource.id = :resourceId AND TYPE(_authority) = :authorityType) AND TYPE(_authority) = :authorityType");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
		conditionVals.put("resourceId", resourceId);
		conditionVals.put("authorityType", Permission.class);
    	
    	assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_authority",
				conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
    }
    
    /**
     * 根据菜单资源id，分页查询权限信息
     * @param resourceId 菜单资源id
     * @param permissionName 权限名称（不需要此条件时，请设置为null）
     * @param permissionIdentifier 权限标识符（不需要此条件时，请设置为null）
     * @param permissionDescription 权限描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findAllPermissionsByMenuResourceIdInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _authority FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE TYPE(_authority) = :authorityType AND _resource.id = :resourceId");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
		conditionVals.put("resourceId", resourceId);
		conditionVals.put("authorityType", Permission.class);
    	
    	assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_authority",
				conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
        		.setParameters(conditionVals)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
    }
    
    /**
     * 根据菜单资源id，分页查询权限信息
     * @param resourceId 菜单资源id
     * @param permissionName 权限名称（不需要此条件时，请设置为null）
     * @param permissionIdentifier 权限标识符（不需要此条件时，请设置为null）
     * @param permissionDescription 权限描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findAllNotGrantPermissionsByMenuResourceIdInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _authority FROM Authority _authority WHERE _authority.id NOT IN(SELECT _authority.id FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE _resource.id = :resourceId AND TYPE(_authority) = :authorityType)");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
		conditionVals.put("resourceId", resourceId);
		conditionVals.put("authorityType", Permission.class);
    	
    	assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_authority",
				conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
        		.setParameters(conditionVals)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
    }
    
    /**
     * 根据页面元素资源id，分页查询权限信息
     * @param resourceId 页面元素id
     * @param permissionName 权限名称（不需要此条件时，请设置为null）
     * @param permissionIdentifier 权限标识符（不需要此条件时，请设置为null）
     * @param permissionDescription 权限描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findAllPermissionsByPageElementResourceIdInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _authority FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE _resource.id = :resourceId AND TYPE(_authority) = :authorityType");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
		conditionVals.put("resourceId", resourceId);
		conditionVals.put("authorityType", Permission.class);
		
    	assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_authority",
				conditionVals);
    	
    	return getRepository().createJpqlQuery(jpql.toString())
        		.setParameters(conditionVals)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
    }
    
    /**
     * 根据页面元素id，分页查询权限信息
     * @param resourceId 页面元素id
     * @param permissionName 权限名称（不需要此条件时，请设置为null）
     * @param permissionIdentifier 权限标识符（不需要此条件时，请设置为null）
     * @param permissionDescription 权限描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 权限集合
     */
    public static List<Permission> findAllNotGrantPermissionsByPageElementResourceIdInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	StringBuilder jpql = new StringBuilder(
    			"SELECT _authority FROM Authority _authority WHERE _authority.id NOT IN(SELECT _authority.id FROM ResourceAssignment _resourceAssignment JOIN _resourceAssignment.authority _authority JOIN _resourceAssignment.resource _resource WHERE _resource.id = :resourceId AND TYPE(_authority) = :authorityType)");
    	
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
		conditionVals.put("resourceId", resourceId);
		conditionVals.put("authorityType", Permission.class);
		
    	assemblePermissionJpqlAndConditionValues(permissionName,
				permissionIdentifier,
				permissionDescription,
				jpql,
				"_authority",
				conditionVals);
    	
    	return getRepository()
        		.createJpqlQuery(jpql.toString())
        		.setParameters(conditionVals)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
    }
    
    private static void assemblePermissionJpqlAndConditionValues(String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		StringBuilder jpql,
    		String conditionPrefix,
    		Map<String, Object> conditionVals) {
        String andCondition = " AND " + conditionPrefix;
        
        if (!StringUtils.isBlank(permissionName)) {
            jpql.append(andCondition);
            jpql.append(".name LIKE :name");
            conditionVals.put("name", MessageFormat.format("%{0}%", permissionName));
        }
        
        if (!StringUtils.isBlank(permissionIdentifier)) {
            jpql.append(andCondition);
            jpql.append(".identifier LIKE :identifier");
            conditionVals.put("identifier", MessageFormat.format("%{0}%", permissionIdentifier));
        }
        
        if (!StringUtils.isBlank(permissionDescription)) {
            jpql.append(andCondition);
            jpql.append(".description LIKE :description");
            conditionVals.put("description", MessageFormat.format("%{0}%", permissionDescription));
        }
    }

    private void isIdentifierExisted(String identifier) {
        Permission permission = getPermissionBy(identifier);
        if (permission != null) {
            throw new IdentifierIsExistedException("permission.identifier.existed");
        }

    }

    public void changeIdentifier(String identifier) {
        Assert.notBlank(identifier,"identifier cannot be empty.");
        if (!identifier.equals(this.getIdentifier())) {
            isIdentifierExisted(identifier);
            this.identifier = identifier;
            this.save();
        }
    }

    public void addRole(Role role) {
        this.roles.add(role);
        this.save();
    }

    public void addRoles(Set<Role> roles) {
        this.roles.addAll(roles);
        this.save();
    }

    public void terminateRole(Role role) {
        this.roles.remove(role);
        this.save();
    }

    public void terminateRoles(Set<Role> roles) {
        this.roles.removeAll(roles);
        this.save();
    }

    @Override
    public void remove() {
        if (!this.getRoles().isEmpty()) {
            throw new CorrelationException("permission has role, so can't remove it.");
        }
        super.remove();
    }

    public String[] businessKeys() {
        return new String[]{"name", "identifier"};
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(getName())
                .append(identifier)
                .append(getDescription())
                .build();
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getIdentifier() {
        return identifier;
    }
}