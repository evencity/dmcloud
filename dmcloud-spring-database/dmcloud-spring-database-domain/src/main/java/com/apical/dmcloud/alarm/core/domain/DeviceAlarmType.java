package com.apical.dmcloud.alarm.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.dayatang.utils.Assert;

import com.apical.dmcloud.AbstractIntegerIDEntity;
import com.apical.dmcloud.alarm.core.NameIsExistedException;
import com.apical.dmcloud.alarm.core.UnsupportedAlarmTypeException;
import com.apical.dmcloud.commons.infra.AlarmType;

/**
 * 报警类型信息类
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_alarm_type")
public class DeviceAlarmType extends AbstractIntegerIDEntity
{
	/**
	 * serialVersionUID = 5508335498457027100L;
	 */
	private static final long serialVersionUID = 5508335498457027100L;
	
	/**
	 * 报警类型标记
	 */
	@Column(name = "TYPE")
	private Integer type;

	/**
	 * 报警类型名称
	 */
	@NotNull
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 报警类型描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
    
	protected DeviceAlarmType()
	{
	}
	
	public DeviceAlarmType(String name)
	{
		checkName(name);
		if(isExistAlarmTypeName(name))
		{
			throw new NameIsExistedException("name of alarm type is existed!");
		}
		
		this.name = name;
	}

    /**
     * 获取报警类型名称
     * @return 报警类型名称
     */
	public String getName() {
		return name;
	}

	/**
	 * 设置报警类型名称
	 * @param name 报警类型名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	 /**
     * 获取报警类型描述
     * @return 报警类型描述
     */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置报警类型描述
	 * @param description 报警类型描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 获取报警类型标记
	 * @return 报警类型标记
	 */
	public Integer getType()
	{
		return type;
	}
	
	/**
	 * 获取报警类型标记（枚举类型）
	 * @return 报警类型标记（枚举类型）
	 * @throws UnsupportedAlarmTypeException 
	 */
	public AlarmType getAlarmType() throws UnsupportedAlarmTypeException
	{
		AlarmType types[] = AlarmType.values();
		if(type >= types.length)
		{
			throw new UnsupportedAlarmTypeException("Unsupported AlarmType:" + type);
		}
		
		return types[type];
	}

	/**
	 * 设置报警类型标记
	 * @param type 报警类型标记
	 */
	public void setType(Integer type)
	{
		this.type = type;
	}
	
	/**
	 * 设置报警类型标记（枚举类型）
	 * @param type 报警类型标记（枚举类型）
	 */
	public void setAlarmType(AlarmType type)
	{
		this.type = type.ordinal();
	}

	/**
	 * 使用断言方式检测类型名称是否为空
	 * @param name 类型名称
	 */
	private void checkName(String name) {
		Assert.notBlank(name, "name cannot be empty.");
	}

	@Override
	public String[] businessKeys()
	{
		// TODO Auto-generated method stub
		return new String[]{"DeviceAlarmType"};
	}
	
	/**
	 * 根据id，删除报警类型信息
	 * @param id 类型id
	 * @return 删除是否成功
	 */
/*	public static boolean deleteById(int id)
	{
		String jpql = "delete from DeviceAlarmType _record where _record.id=:id";
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
	}*/
	
	/**
	 * 判断该类型名称是否已被使用
	 * @param name 类型名称
	 * @return 是否已被使用
	 */
	public static boolean isExistAlarmTypeName(String name)
	{
		String jpql = "select 1 from DeviceAlarmType _type where _type.name=:name";
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
	 * 获取报警类型的数量
	 * @return 类型数量
	 */
	public static Integer countAllAlarmType()
	{
		String jpql = "select count(_type.id) from DeviceAlarmType _type";
		Integer count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
		
//		return 1;
	}
	
	/**
	 * 获取所有的报警类型信息
	 * @return 报警类型信息
	 */
	public static List<DeviceAlarmType> queryAllAlarmType()
	{
		String jpql = "select _type from DeviceAlarmType _type";
		List<DeviceAlarmType> types = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return types;
	}
	
	/**
	 * 获取所有的报警类型信息
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 报警类型信息
	 */
	public static List<DeviceAlarmType> queryAllAlarmTypeInPage(int pageCount, int pageSize)
	{
		String jpql = "select _type from DeviceAlarmType _type";
		List<DeviceAlarmType> types = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return types;
	}

	@Override
	public String toString() {
		return "DeviceAlarmType [type=" + type + ", name=" + name
				+ ", description=" + description + "]";
	}
	
}
