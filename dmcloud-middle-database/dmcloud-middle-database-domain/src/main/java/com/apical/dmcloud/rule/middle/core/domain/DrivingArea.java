package com.apical.dmcloud.rule.middle.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.apical.dmcloud.middle.infra.AbstractVersionEntity;

/**
 * 区域表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.DrivingArea")
@Table(name = "cl_driving_area")
public class DrivingArea extends AbstractVersionEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 区域名称
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 区域描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	protected DrivingArea()
	{
	}
	
	public DrivingArea(String name)
	{
	}
	
	/**
	 * 获取区域名称
	 * @return 区域名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置区域名称
	 * @param name 区域名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取区域描述
	 * @return 区域描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置区域描述
	 * @param description 区域描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"DrivingArea"};
	}
	
	/**
	 * 判断该类型名称是否已被使用
	 * @param name 类型名称
	 * @return 是否已被使用
	 */
	public static boolean isExistDrivingAreaName(String name)
	{
		String jpql = "select 1 from Middle.DrivingArea _area"
				+ " where _area.name=:name";
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
	 * 根据id来删除区域信息
	 * @param id 区域信息id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.DrivingArea _area"
				+ " where _area.id=:id";
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
	 * 获取区域的数量
	 * @return 区域数量
	 */
	public static long countAllDrivingArea()
	{
		String jpql = "select count(_area.id) from Middle.DrivingArea _area";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}
	
	/**
	 * 查询区域信息
	 * @return 区域信息
	 */
	public static List<DrivingArea> queryAllDrivingArea()
	{
		String jpql = "select _area from Middle.DrivingArea _area";
		List<DrivingArea> areas = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return areas;
	}

	/**
	 * 分页查询区域信息
	 * @param pageCount 页数（pageCount >= 1）
     * @param pageSize 页面大小（pageSize >= 1）
	 * @return 区域信息
	 */
	public static List<DrivingArea> queryAllDrivingAreaInPage(int pageCount, int pageSize)
	{
		String jpql = "select _area from Middle.DrivingArea _area";
		List<DrivingArea> areas = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return areas;
	}
}
