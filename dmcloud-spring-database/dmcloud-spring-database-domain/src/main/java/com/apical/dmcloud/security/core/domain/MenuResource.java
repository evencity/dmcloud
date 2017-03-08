package com.apical.dmcloud.security.core.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 菜单权限资源。它是系统提供的功能入口。
 *
 * @author lucas
 */

@Entity
@DiscriminatorValue("MENU_RESOURCE")
public class MenuResource extends SecurityResource {

    private static final long serialVersionUID = 2065808982375385340L;

    /**
     * 菜单图标
     */
    @Column(name = "MENU_ICON")
    private String menuIcon;

    /**
     * 用于菜单级别
     */
    @Column(name = "LEVEL")
    private int level = 0;

    /**
     * 菜单排序位置号
     */
    @Column(name = "POSITION")
    private int position = 0;

    @Column(name = "URL")
    private String url;

    @ManyToOne
    @JoinTable(name = "ks_menu_resource_relation",
            joinColumns = @JoinColumn(name = "CHILD_ID"),
            inverseJoinColumns = @JoinColumn(name = "PARENT_ID"))
    private MenuResource parent;

    @OneToMany(mappedBy = "parent")
    private Set<MenuResource> children = new HashSet<MenuResource>();

    protected MenuResource() {
    }

    public MenuResource(String name) {
        super(name);
    }

    @Override
    public void save() {
        super.save();
    }

    public void addChild(MenuResource child) {
        child.setLevel(level + 1);
        child.save();
        this.children.add(child);
        child.setParent(this);
    }

    public void removeChild(MenuResource child) {
        children.remove(child);
        child.remove();
    }

    @Override
    public void remove() {
        removeChildren();
        super.remove();
    }

    public static MenuResource getBy(Long id) {
        return MenuResource.get(MenuResource.class, id);
    }

    /**
     * 获取菜单资源
     * @param menuResourceIds 菜单资源id数组
     * @return 菜单资源列表
     */
    public static List<MenuResource> findAllByIds(Long[] menuResourceIds) {
        return getRepository().createCriteriaQuery(MenuResource.class)
                .in("id", menuResourceIds)
                .list();
    }
    
    public static List<MenuResource> findAllTopMenuResources() {
    	StringBuilder jpql = new StringBuilder("SELECT _menu FROM MenuResource _menu")
        		.append(" WHERE _menu.parent IS NULL")
        		.append(" AND _menu.level = :level")
        		.append(" GROUP BY _menu.id")
        		.append(" ORDER BY _menu.id"); // 必须有排序

    	return getRepository()
                .createJpqlQuery(jpql.toString())
                .addParameter("level", 0)
                .list();
    }
    
    public static List<MenuResource> findAllChildrenMenuResources() {
    	StringBuilder jpql = new StringBuilder("SELECT _resource FROM MenuResource _resource")
        .append(" WHERE _resource.level > :level")
        .append(" GROUP BY _resource.id")
        .append(" ORDER BY _resource.id"); // 必须有排序

    	return getRepository()
    			.createJpqlQuery(jpql.toString())
    			.addParameter("level", 0)
    			.list();
    }
    
    public static List<MenuResource> findAllMenuResourcesTree() {
		List<MenuResource> results = MenuResource.findAllTopMenuResources();
		List<MenuResource> childrenMenuResources = MenuResource.findAllChildrenMenuResources();
		List<MenuResource> all = new ArrayList<MenuResource>();
		
		all.addAll(results);
		all.addAll(childrenMenuResources);
		
		addMenuChildrenToParent(all);
		
		return results;
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
    public static List<Permission> findPermissionsInPage(long resourceId,
    		String permissionName,
    		String permissionIdentifier,
    		String permissionDescription,
    		int pageCount,
    		int pageSize) {
    	return Permission.findAllPermissionsByMenuResourceIdInPage(resourceId,
    			permissionName, permissionIdentifier, permissionDescription,
    			pageCount, pageSize);
    }
    
    /**
     * 根据菜单资源id，分页查询未分配的权限信息
     * @param resourceId 菜单资源id
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
    	return Permission.findAllNotGrantPermissionsByMenuResourceIdInPage(resourceId,
    			permissionName, permissionIdentifier, permissionDescription,
    			pageCount, pageSize);
    }
    
    private static void addMenuChildrenToParent(List<MenuResource> all) {
        LinkedHashMap<Long, MenuResource> map = new LinkedHashMap<Long, MenuResource>();
        for (MenuResource menuResource : all) {
            map.put(menuResource.getId(), menuResource);
        }
        for (MenuResource menuResource : map.values()) {
            Long parentId = menuResource.getParent().getId();
            if ((!StringUtils.isBlank(parentId + "")) && (map.get(parentId) != null)) {
                map.get(parentId).getChildren().add(menuResource);
            }
        }
    }

    private void removeChildren() {
        for (MenuResource child : children) {
            child.remove();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(getId())
                .append(getName())
                .append(getUrl())
                .build();
    }

    /*-------------- getter setter methods  ------------------*/

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public MenuResource getParent() {
        return parent;
    }

    public void setParent(MenuResource parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public Set<MenuResource> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public void setChildren(Set<MenuResource> children) {
        this.children = children;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}