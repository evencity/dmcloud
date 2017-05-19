package com.apical.dmcloud.security.core.domain;

import org.apache.commons.lang3.StringUtils;

import com.apical.dmcloud.security.core.UserNotHasRoleException;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * 角色是权限的集合，它可以可以是逻辑上的权限{@link Permission}，也可以是物理上的权限{@link SecurityResource}。
 * 它代表一系列可执行操作或责任，因此它是授权的粗粒度。
 *
 * @author luzhao
 * 
 * 这个不知道干什么的，表数据是空的，说明没有使用到
 */

@Entity
@DiscriminatorValue("ROLE")
public class Role extends Authority {

    private static final long serialVersionUID = 4327840654680779887L;

    /**
     * 查询Role需要级联的查询出Permission
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ks_role_permission_map",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
    private Set<Permission> permissions = new HashSet<Permission>();

    protected Role() {
    }

    public Role(String name) {
        super(name);
    }

    /**
     * 通过角色名称，来判断该角色是否存在
     * @param roleName 角色名称
     * @return 角色是否存在
     */
    public static boolean checkName(String roleName) {
        return getRoleByName(roleName) != null;
    }
    
    /**
     * 获取系统所有的角色信息
     * @return 角色信息
     */
    public static List<Role> findAll() {
        return Role.findAll(Role.class);
    }

    /**
     * 通过角色名称，来判断该角色是否存在
     * @param name 角色名称
     * @return 角色信息
     */
    public static Role getRoleByName(String name) {
        return getRepository()
                .createCriteriaQuery(Role.class)
                .eq("name", name)
                .singleResult();
    }

    /**
     * 查看用户的角色信息
     * @param user 用户
     * @return 角色信息
     */
    public static Set<Role> findRolesByUser(User user) {
        Set<Role> results = new HashSet<Role>();
        List<Authorization> authorizations = Authorization.findByUser(user);
        for (Authorization authorization : authorizations) {
            Authority authority = authorization.getAuthority();
            if (authority instanceof Role) {
                results.add((Role) authority);
            }
        }
        return results;
    }
    
    /**
     * 获取用户的角色信息
     *
     * @param userAccount 用户帐户名
     * @return 角色信息
     * @throws UserNotHasRoleException 
     */
    public static List<Role> findAllRolesByUserAccount(String userAccount) throws UserNotHasRoleException {
    	String query = "select * from ks_authorities where ID in"
    			+ " (select a.AUTHORITY_ID from ks_authorizations a, cl_users u where u.ID=a.ACTOR_ID"
    			+ " and u.USER_ACCOUNT=:userAccount) and CATEGORY='ROLE'";
    	List<Role> results = getRepository().createSqlQuery(query)
                .addParameter("userAccount", userAccount)
                .setResultEntityClass(Role.class)
                .list();
        if (results.isEmpty()) {
            throw new UserNotHasRoleException("user do have not a role");
        }
        return results;
    }
    
    /**
     * 根据用户帐户名，来分页查询用户的角色信息
     * @param userAccount 用户帐户名
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 角色信息
     * @throws UserNotHasRoleException 
     */
    public static List<Role> findAllRolesByUserAccountInPage(String userAccount,
    		int pageCount, int pageSize) throws UserNotHasRoleException {
    	String query = "select * from ks_authorities where ID in"
    			+ " (select a.AUTHORITY_ID from ks_authorizations a, cl_users u where u.ID=a.ACTOR_ID"
    			+ " and u.USER_ACCOUNT=:userAccount) and CATEGORY='ROLE'";
    	List<Role> roleList = getRepository().createSqlQuery(query)
    			.addParameter("userAccount", userAccount)
                .setResultEntityClass(Role.class)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
        if (roleList.isEmpty()) {
            throw new UserNotHasRoleException("user do have not a role");
        }
        return roleList;
    }
    
    /**
     * 根据用户帐户名，来分页查询用户的角色信息
     * @param userId 用户id
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 角色信息
     * @throws UserNotHasRoleException 
     */
    public static List<Role> findAllRolesByUserIdInPage(long userId,
    		int pageCount,
    		int pageSize) throws UserNotHasRoleException {
    	String query = "select * from ks_authorities where ID in"
    			+ " (select a.AUTHORITY_ID from ks_authorizations a where a.ACTOR_ID=:userId)"
    			+ " and CATEGORY='ROLE'";
    	List<Role> roleList = getRepository().createSqlQuery(query)
                .addParameter("userId", userId)
                .setResultEntityClass(Role.class)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
        if (roleList.isEmpty()) {
            throw new UserNotHasRoleException("user do have not a role");
        }
        return roleList;
    }
    
    /**
     * 根据用户id，来分页查询用户未分配的角色信息
     * @param userId 用户id
     * @param roleName 角色名称（不需要此条件时，请设置为null）
     * @param roleDesription 角色描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 角色信息
     * @throws UserNotHasRoleException 
     */
    public static List<Role> findNotGrantRolesByUserIdInPage(long userId,
    		String roleName,
    		String roleDesription,
    		int pageCount,
    		int pageSize) throws UserNotHasRoleException {
    	StringBuilder jpql = new StringBuilder("SELECT _role FROM Role _role WHERE 1 = 1 ");
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
    	
    	assembleRoleJpqlAndConditionValues(roleName,
    			roleDesription,
				jpql, "_role", conditionVals);
    	jpqlHasWhereCondition(jpql);
    	jpql.append(" _role.id NOT IN(SELECT _authority.id FROM Authorization _authorization JOIN _authorization.actor _actor JOIN _authorization.authority _authority WHERE _actor.id= :userId)");
		conditionVals.put("userId", userId);
    	
    	List<Role> roleList = getRepository()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setMaxResults(pageSize)
                .setFirstResult((pageCount - 1) * pageSize)
                .list();
        if (roleList.isEmpty()) {
            throw new UserNotHasRoleException("user do have not a role");
        }
        return roleList;
    }
    
    public static Role getByRoleId(long id) {
        return Role.get(Role.class, id);
    }
    
    /**
     * 根据条件，来分页查询角色信息
     * @param roleName 角色名称（不需要此条件时，请设置为null）
     * @param roleDesription 角色描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 角色信息
     */
    public static List<Role> findAllRolesInPage(String roleName,
    		String roleDesription,
    		int pageCount,
    		int pageSize) {
    	Map<String, Object> conditionVals = new HashMap<String, Object>();
    	
		StringBuilder jpql = new StringBuilder("SELECT _role FROM Role _role where 1 = 1");
		assembleRoleJpqlAndConditionValues(roleName,
				roleDesription,
				jpql, "_role", conditionVals);

		return getRepository().createJpqlQuery(jpql.toString())
				.setParameters(conditionVals)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
    }
    
    /**
     * 分页查找角色已分配的URL访问资源集合。
     * @param roleId 角色id
     * @param url url（不需要此条件时，请设置为null）
     * @param urlName url名称（不需要此条件时，请设置为null）
     * @param urlDescription URL描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return URL访问资源
     */
    public static List<UrlAccessResource> findAllUrlAccessResourcesInPage(long roleId,
    		String url,
    		String urlName,
    		String urlDescription,
    		int pageCount,
    		int pageSize) {
    	return UrlAccessResource.findAllUrlAccessResourcesByRoleIdInPage(roleId,
    			url, urlName, urlDescription,
    			pageCount,
    			pageSize);
    }
    
    /**
     * 分页查找角色未分配的URL访问资源集合。
     * @param roleId 角色id
     * @param url url（不需要此条件时，请设置为null）
     * @param urlName url名称（不需要此条件时，请设置为null）
     * @param urlDescription URL描述（不需要此条件时，请设置为null）
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return URL访问资源
     */
    public static List<UrlAccessResource> findAllNotGrantUrlAccessResourcesInPage(long roleId,
    		String url,
    		String urlName,
    		String urlDescription,
    		int pageCount,
    		int pageSize) {
    	return UrlAccessResource.findAllNotGrantUrlAccessResourcesByRoleIdInPage(roleId,
    			url, urlName, urlDescription,
    			pageCount,
    			pageSize);
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
    public static List<Permission> findPermissionsInPage(long roleId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	return Permission.findAllPermissionsByRoleIdInPage(roleId,
    			permissionName, permissionIdentifier, permissionDescription,
    			pageCount,
    			pageSize);
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
    public static List<Permission> findNotGrantPermissionsInPage(long roleId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount, int pageSize) {
    	return Permission.findAllNotGrantPermissionsByRoleIdInPage(roleId,
    			permissionName, permissionIdentifier, permissionDescription,
    			pageCount,
    			pageSize);
    }
    
    /**
     * 分页查询角色拥有的页面元素资源。
     * @param roleId 角色id
     * @param resourceName 资源名称
     * @param resourceIdentifier 资源标识符
     * @param resourceDescription 资源描述
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 页面元素资源集合
     */
    public static List<PageElementResource> findPageElementResourcesInPage(long roleId,
    		String resourceName,
    		String resourceIdentifier,
    		String resourceDescription,
    		int pageCount,
    		int pageSize) {
    	return PageElementResource.findPageElementResourceByAuthorityIdInPage(roleId,
    			Role.class,
    			resourceName, resourceIdentifier, resourceDescription,
    			pageCount, pageSize);
    }
    
    /**
     * 分页查询角色未分配的页面元素资源。
     * @param roleId 角色id
     * @param resourceName 资源名称
     * @param resourceIdentifier 资源标识符
     * @param resourceDescription 资源描述
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 页面元素资源集合
     */
    public static List<PageElementResource> findNotGrantPageElementResourcesInPage(long roleId,
    		String resourceName,
    		String resourceIdentifier,
    		String resourceDescription,
    		int pageCount,
    		int pageSize) {
    	return PageElementResource.findNotGrantPageElementResourceByAuthorityIdInPage(roleId,
    			Role.class,
    			resourceName, resourceIdentifier, resourceDescription,
    			pageCount, pageSize);
    }
    
    /**
     * 检查JPQL里面是否包含WHERE 关键字，如果没有就加上。
     *
     * @param jpql
     */
    private static void jpqlHasWhereCondition(StringBuilder jpql) {
        if (jpql.indexOf("WHERE") != -1) {
            jpql.append(" AND ");
        } else {
            jpql.append(" WHERE ");
        }
    }
    
    private static void assembleRoleJpqlAndConditionValues(String roleName,
    		String description,
    		StringBuilder jpql,
    		String conditionPrefix,
    		Map<String, Object> conditionVals) {
        String andCondition = " AND " + conditionPrefix;
        
        if (!StringUtils.isBlank(roleName)) {
            jpql.append(andCondition);
            jpql.append(".name LIKE :name");
            conditionVals.put("name", MessageFormat.format("%{0}%", roleName));
        }
        
        if (!StringUtils.isBlank(description)) {
            jpql.append(andCondition);
            jpql.append(".description LIKE :description");
            conditionVals.put("description", MessageFormat.format("%{0}%", description));
        }
    }
    
    public Set<Authority> findAuthoritiesBy() {
        Set<Authority> results = new HashSet<Authority>();
        results.add(this);
        results.addAll(this.getPermissions());
        return results;
    }

    /**
     * 给角色添加一个权限
     * @param permission 权限信息
     */
    public void addPermission(Permission permission) {
        this.permissions.add(permission);
        permission.addRole(this);
        this.save();
    }

    /**
     * 给角色添加多个权限信息
     * @param permissions 权限信息
     */
    public void addPermissions(List<Permission> permissions) {
        this.permissions.addAll(permissions);
        for (Permission permission : permissions) {
            permission.addRole(this);
        }
        this.save();
    }

    public void terminatePermission(Permission permission) {
        this.permissions.remove(permission);
        permission.terminateRole(this);
        this.save();
    }

    public void terminatePermissions(List<Permission> permissions) {
        this.permissions.removeAll(permissions);
        for (Permission permission : permissions) {
            permission.terminateRole(this);
        }
        this.save();
    }

    public List<MenuResource> findMenuResources() {
    	String sql = "select distinct r.*,p.PARENT_ID from ks_securityresources r left outer join ks_menu_resource_relation p"
    			+ " on r.ID=p.CHILD_ID,ks_resourceassignments a,ks_authorities u where u.ID=:id and u.CATEGORY='ROLE'"
    			+ " and u.ID=a.AUTHORITY_ID and r.ID = a.SECURITYRESOURCE_ID and r.CATEGORY='MENU_RESOURCE'"
    			+ " GROUP by r.ID order by r.ID";
        return getRepository().createSqlQuery(sql)
                .addParameter("id", this.getId())
                .setResultEntityClass(MenuResource.class)
                .list();
    }

    public List<UrlAccessResource> findUrlAccessResources() {
    	String sql = "select distinct r.* from ks_securityresources r,ks_resourceassignments a,ks_authorities u where"
    			+ " u.ID=:id and u.CATEGORY='ROLE' and u.ID=a.AUTHORITY_ID and r.ID = a.SECURITYRESOURCE_ID"
    			+ " and r.CATEGORY='URL_ACCESS_RESOURCE' GROUP by r.ID order by r.ID";
        return getRepository().createSqlQuery(sql)
                .addParameter("id", this.getId())
                .setResultEntityClass(UrlAccessResource.class)
                .list();
    }

    public List<PageElementResource> findPageElementResources() {
    	String sql = "select distinct r.* from ks_securityresources r,ks_resourceassignments a,ks_authorities u where"
    			+ " u.ID=:id and u.CATEGORY='ROLE' and u.ID=a.AUTHORITY_ID and r.ID = a.SECURITYRESOURCE_ID"
    			+ " and r.CATEGORY='PAGE_ELEMENT_RESOURCE' GROUP by r.ID order by r.ID";
    	return getRepository().createSqlQuery(sql)
                .addParameter("id", this.getId())
                .setResultEntityClass(PageElementResource.class)
                .list();
    }

    /*-------------- getter setter methods  ------------------*/

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
