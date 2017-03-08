package com.apical.dmcloud.rule.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.dayatang.utils.Assert;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;
import com.apical.dmcloud.rule.core.NameIsExistedException;

/**
 * 行车区域表
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_driving_area")
public class DrivingArea extends KoalaAbstractEntity
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
		checkName(name);
		if(isExistDrivingAreaName(name))
		{
			throw new NameIsExistedException("area name is existed!");
		}
		
		this.name = name;
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
	
	/**
	 * 使用断言方式检测区域名称是否为空
	 * @param name 区域名称
	 */
	private void checkName(String name) {
		Assert.notBlank(name, "name cannot be empty.");
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
		String jpql = "select 1 from DrivingArea _area where _area.name=:name";
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
		String jpql = "delete from DrivingArea _area where _area.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", id)
				.executeUpdate();
		if(count == 1)
		{
			MiddleEntryCacheService.removeGPSData_BelongRule(DrivingAreaGPSData.class, id);
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
		String jpql = "select count(_area.id) from DrivingArea _area";
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
		String jpql = "select _area from DrivingArea _area";
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
		String jpql = "select _area from DrivingArea _area";
		List<DrivingArea> areas = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return areas;
	}
}
