package com.apical.dmcloud.spring.database.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.apical.dmcloud.security.core.UserNotHasRoleException;
import com.apical.dmcloud.security.core.domain.Authority;
import com.apical.dmcloud.security.core.domain.MenuResource;
import com.apical.dmcloud.security.core.domain.PageElementResource;
import com.apical.dmcloud.security.core.domain.Permission;
import com.apical.dmcloud.security.core.domain.ResourceAssignment;
import com.apical.dmcloud.security.core.domain.Role;
import com.apical.dmcloud.security.core.domain.SecurityResource;
import com.apical.dmcloud.security.core.domain.UrlAccessResource;

public class TestAuthority extends KoalaBaseSpringTestCase
{
//	@Test
//	public void testRole() throws UserNotHasRoleException
//	{
//		List<Role> roles = Role.findAllRolesByUserAccount("123456@qq.com");
//		for(Role r : roles)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//		}
		
//		List<Role> roles = Role.findAllRolesByUserIdInPage(1, 1, 1);
//		for(Role r : roles)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			
//			List<PageElementResource> bres  = r.findPageElementResources();
//			System.out.println(bres.size());
//			for(PageElementResource m : bres)
//			{
//				System.out.println(m.getName());
//				System.out.println(m.getDescription());
//				System.out.println();
//			}
//		}
		
//		Set<Authority> sets = new HashSet<Authority>();
//		sets.add(roles.get(0));
//		List<MenuResource> bres = ResourceAssignment.findMenuResourceByAuthorities(sets);
//		System.out.println(bres.size());
//		for(MenuResource m : bres)
//		{
//			System.out.println(m.getName());
//			System.out.println(m.getDescription());
//			System.out.println();
//		}
		
//		List<UrlAccessResource> bres = ResourceAssignment.findUrlAccessResourcesByAuthorities(sets);
//		System.out.println(bres.size());
//		for(UrlAccessResource m : bres)
//		{
//			System.out.println(m.getName());
//			System.out.println(m.getDescription());
//			System.out.println();
//		}
		
//		PageElementResource e = PageElementResource.getBy(9L);
//		List<Role> roles = ResourceAssignment.findRoleBySecurityResource(e);
//		for(Role r : roles)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//		}
//	}
	
	@Test
	public void testPermission() throws UserNotHasRoleException
	{
//		List<Permission> roles = Permission.findAllPermissionsByUserAccount("123456@qq.com");
//		for(Permission r : roles)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//		}
		
		PageElementResource e = PageElementResource.getBy(9L);
		List<Permission> roles = ResourceAssignment.findPermissionBySecurityResource(e);
		for(Permission r : roles)
		{
			System.out.println(r.getName());
			System.out.println(r.getDescription());
		}
	}
}
