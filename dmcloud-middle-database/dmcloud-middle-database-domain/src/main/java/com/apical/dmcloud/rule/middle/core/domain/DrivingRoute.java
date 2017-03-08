package com.apical.dmcloud.rule.middle.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.apical.dmcloud.middle.infra.AbstractVersionEntity;

/**
 * 路线表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.DrivingRoute")
@Table(name = "cl_driving_route")
public class DrivingRoute extends AbstractVersionEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 路线名称
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 路线描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	protected DrivingRoute()
	{
	}
	
	public DrivingRoute(String name)
	{
	}

	/**
	 * 获取路线名称
	 * @return 路线名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置路线名称
	 * @param name 路线名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取路线描述
	 * @return 路线描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置路线描述
	 * @param description 路线描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"DrivingRoute"};
	}
	
	/**
	 * 判断该类型名称是否已被使用
	 * @param name 类型名称
	 * @return 是否已被使用
	 */
	public static boolean isExistDrivingRouteName(String name)
	{
		String jpql = "select 1 from Middle.DrivingRoute _route"
				+ " where _route.name=:name";
		Integer result = getRepository().createJpqlQuery(jpql)
				.addParameter("name", name)
				.setMaxResults(1)
				.singleResult();
		if(result != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据id来删除路线信息
	 * @param id 路线信息id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.DrivingRoute _route"
				+ " where _route.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", id)
				.executeUpdate();
		if(count == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 获取路线的数量
	 * @return 路线数量
	 */
	public static long countAllDrivingRoute()
	{
		String jpql = "select count(_route.id) from Middle.DrivingRoute _route";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}

	/**
	 * 查询路线信息
	 * @return 路线信息
	 */
	public static List<DrivingRoute> queryAllDrivingRoute()
	{
		String jpql = "select _route from Middle.DrivingRoute _route";
		List<DrivingRoute> routes = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return routes;
	}

	/**
	 * 分页查询路线信息
	 * @param pageCount 页数（pageCount >= 1）
     * @param pageSize 页面大小（pageSize >= 1）
	 * @return 路线信息
	 */
	public static List<DrivingRoute> queryAllDrivingRouteInPage(int pageCount, int pageSize)
	{
		String jpql = "select _route from Middle.DrivingRoute _route";
		List<DrivingRoute> routes = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return routes;
	}
}
