package com.apical.dmcloud.alarm.middle.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apical.dmcloud.middle.infra.AbstractIDEntity;

/**
 * 设备登录信息实体类
 * @author qiuzeng
 *
 */
@Entity(name = "Middle.DeviceLoginRecord")
@Table(name = "cl_device_loginhistory")
public class DeviceLoginRecord extends AbstractIDEntity
{
	private static final long serialVersionUID = 1L;

	/**
	 * 设备ID
	 */
	@Column(name = "DEVICE_ID")
	private Long deviceId = null;
	
	/**
	 * 登录ip地址
	 */
	@Column(name = "LOGINIP")
	private String loginIP = null;
	
	/**
	 * 登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGINTIME")
	private Date loginTime = null;
	
	/**
	 * 登录状态（登录失败或成功，true表示登录成功，false表示登录失败）
	 */
	@Column(name = "LOGINSTATUS")
	private Boolean loginStatus = null;
	
	/**
	 * 登录失败错误代码
	 */
	@Column(name = "LOGINCODE")
	private Integer loginCode = null;
	
	/**
	 * 注销时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGOUTTIME")
	private Date logoutTime = null;
	
	/**
	 * 注销错误代码
	 */
	@Column(name = "LOGOUTCODE")
	private Integer logoutCode = null;
	
	public DeviceLoginRecord()
	{
	}
	
	/**
	 * 获取设备ID
	 * @return 设备ID
	 */
	public Long getDeviceId() {
		return deviceId;
	}

	/**
	 * 设置设备ID
	 * @param deviceId 设备ID
	 */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * 获取登录ip地址
	 * @return 登录ip地址
	 */
	public String getLoginIP()
	{
		return loginIP;
	}

	/**
	 * 设置登录ip地址
	 * @param loginIP 登录ip地址
	 */
	public void setLoginIP(String loginIP)
	{
		this.loginIP = loginIP;
	}

	/**
	 * 获取登录时间
	 * @return 登录时间
	 */
	public Date getLoginTime()
	{
		return loginTime;
	}

	/**
	 * 设置登录时间
	 * @param loginTime 登录时间
	 */
	public void setLoginTime(Date loginTime)
	{
		this.loginTime = loginTime;
	}

	/**
	 * 获取登录状态
	 * @return 登录状态
	 */
	public Boolean getLoginStatus()
	{
		return loginStatus;
	}

	/**
	 * 设置登录状态
	 * @param loginStatus 登录状态
	 */
	public void setLoginStatus(Boolean loginStatus)
	{
		this.loginStatus = loginStatus;
	}

	/**
	 * 获取登录错误代码
	 * @return 错误代码
	 */
	public Integer getLoginCode()
	{
		return loginCode;
	}

	/**
	 * 设置登录错误代码
	 * @param loginCode 错误代码
	 */
	public void setLoginCode(Integer loginCode)
	{
		this.loginCode = loginCode;
	}
	
	/**
	 * 获取注销时间
	 * @return 注销时间
	 */
	public Date getLogoutTime()
	{
		return logoutTime;
	}

	/**
	 * 设置注销时间
	 * @param logoutTime 注销时间
	 */
	public void setLogoutTime(Date logoutTime)
	{
		this.logoutTime = logoutTime;
	}
	
	/**
	 * 获取注销错误代码
	 * @return 错误代码
	 */
	public Integer getLogoutCode()
	{
		return logoutCode;
	}

	/**
	 * 设置注销错误代码
	 * @param logoutCode 错误代码
	 */
	public void setLogoutCode(Integer logoutCode)
	{
		this.logoutCode = logoutCode;
	}
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"DeviceLoginRecord"};
	}
	
	/**
	 * 根据id来删除行车记录
	 * @param id 行车记录id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from DeviceLoginRecord _record where _record.id=:id";
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
	 * 通过设备id，来统计登录信息数量
	 * @param deviceId 设备id
	 * @return 数量
	 */
	public static long countAllByDeviceId(long deviceId)
	{
	String jpql = "select count(_record.id) from Middle.DeviceLoginRecord _record where _record.deviceId=:deviceId";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.singleResult();
		return count;
//		return 1;
	}
	
	/**
	 * 通过设备id，来获取设备的登录信息
	 * @param deviceId 设备id
	 * @return 登录信息
	 */
	public static List<DeviceLoginRecord> queryAllByDeviceId(long deviceId)
	{
		String jpql = "select _record from Middle.DeviceLoginRecord _record where _record.deviceId=:deviceId";
		List<DeviceLoginRecord> records = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.list();
		
		return records;
	}
	
	/**
	 * 通过设备id，来获取设备的登录信息
	 * @param deviceId 设备id
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 登录信息
	 */
	public static List<DeviceLoginRecord> queryAllByDeviceIdInPage(long deviceId, int pageCount, int pageSize)
	{
		String jpql = "select _record from Middle.DeviceLoginRecord _record where _record.deviceId=:deviceId";
		List<DeviceLoginRecord> records = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return records;
	}
}
