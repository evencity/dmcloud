package com.apical.dmcloud.alarm.core.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apical.dmcloud.AbstractIntegerIDEntity;

/**
 * 外围设备报警类型信息类
 * @author lgx
 * 2015-5-15
 */

@Entity
@Table(name = "cl_alarm_type_peripherals")
public class DevicePeripheralsAlarmType extends AbstractIntegerIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2756729696847286453L;

	/**
	 * 大类报警类型标记
	 */
	@NotNull
	@Column(name = "TYPE_BIG")
	private Short typeBig;

	/**
	 * 大了报警类型名称
	 */
	@NotNull
	@Column(name = "NAME_BIG")
	private String nameBig;
	
	/**
	 * 大类报警类型标记
	 */
	@NotNull
	@Column(name = "TYPE_MID")
	private Short typeMid;

	/**
	 * 大了报警类型名称
	 */
	@NotNull
	@Column(name = "NAME_MID")
	private String nameMid;
	
	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
    
	@OneToMany(mappedBy="devicePeripheralsAlarmType")
	private Set<DevicePeripheralsAlarmRecord> devicePeripheralsAlarmRecords; 
	
	public Set<DevicePeripheralsAlarmRecord> getDevicePeripheralsAlarmRecords() {
		return devicePeripheralsAlarmRecords;
	}


	public void setDevicePeripheralsAlarmRecords(
			Set<DevicePeripheralsAlarmRecord> devicePeripheralsAlarmRecords) {
		this.devicePeripheralsAlarmRecords = devicePeripheralsAlarmRecords;
	}


	protected DevicePeripheralsAlarmType() {
	}
	

	public Short getTypeBig() {
		return typeBig;
	}


	public void setTypeBig(Short typeBig) {
		this.typeBig = typeBig;
	}


	public String getNameBig() {
		return nameBig;
	}


	public void setNameBig(String nameBig) {
		this.nameBig = nameBig;
	}


	public Short getTypeMid() {
		return typeMid;
	}


	public void setTypeMid(Short typeMid) {
		this.typeMid = typeMid;
	}


	public String getNameMid() {
		return nameMid;
	}


	public void setNameMid(String nameMid) {
		this.nameMid = nameMid;
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

	@Override
	public String[] businessKeys() {
		return new String[]{"DevicePeripheralsAlarmType"};
	}
	
	/**
	 * 根据id，删除报警类型信息, 只能在数据库层面进行手动删除
	 * @param id 类型id
	 * @return 删除是否成功
	 */
/*	public static boolean deleteById(int id) {
		String jpql = "delete from DevicePeripheralsAlarmType _record where _record.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", id)
				.executeUpdate();
		if(count == 1) {
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
	public static boolean isExistAlarmTypeNameBig(String name) {
		String jpql = "select 1 from DevicePeripheralsAlarmType _type where _type.nameBig=:nameBig";
		Integer result = getRepository().createJpqlQuery(jpql)
				.addParameter("nameBig", name)
				.setMaxResults(1)
				.singleResult();
		if(result != null) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isExistAlarmTypeNameMid(String name) {
		String jpql = "select 1 from DevicePeripheralsAlarmType _type where _type.nameMid=:nameMid";
		Integer result = getRepository().createJpqlQuery(jpql)
				.addParameter("nameMid", name)
				.setMaxResults(1)
				.singleResult();
		if(result != null) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过报警类型标记，来获取报警类型
	 * @param typeBig 大类
	 * @param typeMid 种类
	 * @return 类型对象
	 */
	public static DevicePeripheralsAlarmType getIdByAlarmType(short typeBig, short typeMid)
	{
		String jpql = "select _type from DevicePeripheralsAlarmType _type where _type.typeBig=:typeBig and _type.typeMid=:typeMid";
		DevicePeripheralsAlarmType t = getRepository().createJpqlQuery(jpql)
				.addParameter("typeBig", typeBig)
				.addParameter("typeMid", typeMid)
				.singleResult();
		return t;
	}
	/**
	 * 获取报警类型的数量
	 * @return 类型数量
	 */
	public static Integer countAllAlarmType() {
		String jpql = "select count(_type.id) from DevicePeripheralsAlarmType _type";
		Integer count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
		
//		return 1;
	}
	
	/**
	 * 获取所有的报警类型信息
	 * @return 报警类型信息
	 */
	public static List<DevicePeripheralsAlarmType> queryAllAlarmType() {
		String jpql = "select _type from DevicePeripheralsAlarmType _type";
		List<DevicePeripheralsAlarmType> types = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return types;
	}
	
	/**
	 * 获取所有的报警类型信息
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 报警类型信息
	 */
	public static List<DevicePeripheralsAlarmType> queryAllAlarmTypeInPage(int pageCount, int pageSize) {
		String jpql = "select _type from DevicePeripheralsAlarmType _type";
		List<DevicePeripheralsAlarmType> types = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return types;
	}
	/**
	 * 获取外围设备报警类型（大类）
	 * @throws Exception
	 */
	public static List<DevicePeripheralsAlarmType> getAllTypebigDevicePeripheralsAlarmType() {
		String jpql = "select _type from DevicePeripheralsAlarmType _type GROUP BY _type.typeBig ";
		List<DevicePeripheralsAlarmType> records = getRepository().createJpqlQuery(jpql).list();
		return records;
	}
	/**
	 * 获取外围设备报警类型（通过大类获取小类）
	 * @throws Exception
	 */
	public static List<DevicePeripheralsAlarmType> getAllDevicePeripheralsAlarmTypeByTypebig(short typeBig) {
		String jpql = "select _type from DevicePeripheralsAlarmType _type where _type.typeBig=:typeBig";
		List<DevicePeripheralsAlarmType> records = getRepository().createJpqlQuery(jpql)
				.addParameter("typeBig", typeBig).list();
		return records;
	}
	
}
