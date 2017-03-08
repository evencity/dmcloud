package com.apical.dmcloud.security.middle.core.domain;

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
@Entity(name = "Middle.UserLoginRecord")
@Table(name = "cl_users_loginhistory")
public class UserLoginRecord extends AbstractIDEntity
{
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Column(name = "USER_ID")
	private Long userId = null;
	
	/**
	 * 登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGINTIME")
	private Date loginTime = null;
	
	/**
	 * 登录地点
	 */
	@Column(name = "LOGINPLACE")
	private String loginPlace = null;
	
	/**
	 * 登录方式
	 */
	@Column(name = "LOGINMODE")
	private String loginMode = null;
	
	/**
	 * 登录平台
	 */
	@Column(name = "LOGINPLATFORM")
	private String loginPlatform = null;
	
	/**
	 * 注销时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGOUTTIME")
	private Date logoutTime = null;
	
	public UserLoginRecord()
	{
	}
	
	/**
	 * 获取用户ID
	 * @return 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户ID
	 * @param userId 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取登录地点
	 * @return 登录地点
	 */
	public String getLoginPlace()
	{
		return loginPlace;
	}

	/**
	 * 设置登录地点
	 * @param loginPlace 登录地点
	 */
	public void setLoginPlace(String loginPlace)
	{
		this.loginPlace = loginPlace;
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
	 * 获取登录方式
	 * @return 登录方式
	 */
	public String getLoginMode()
	{
		return loginMode;
	}

	/**
	 * 设置登录方式
	 * @param loginMode 登录方式
	 */
	public void setLoginMode(String loginMode)
	{
		this.loginMode = loginMode;
	}

	/**
	 * 获取登录平台
	 * @return 登录平台
	 */
	public String getLoginPlatform()
	{
		return loginPlatform;
	}

	/**
	 * 设置登录平台
	 * @param loginPlatform 登录平台
	 */
	public void setLoginPlatform(String loginPlatform)
	{
		this.loginPlatform = loginPlatform;
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
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"DeviceLoginRecord"};
	}
	
	/**
	 * 通过用户id，来统计登录信息数量
	 * @param userId 用户id
	 * @return 数量
	 */
	public static long countAllByUserId(long userId)
	{
		String jpql = "select count(_record.id) from Middle.UserLoginRecord _record where _record.userId=:userId";
		Long count = getRepository().createJpqlQuery(jpql)
				.addParameter("userId", userId)
				.singleResult();
		
		return count;
	}
	
	/**
	 * 通过用户d，来获取用户的登录信息
	 * @param userId 用户id
	 * @return 登录信息
	 */
	public static List<UserLoginRecord> queryAllByUserId(long userId)
	{
		String jpql = "select _record from Middle.UserLoginRecord _record where _record.userId=:userId";
		List<UserLoginRecord> records = getRepository().createJpqlQuery(jpql)
				.addParameter("userId", userId)
				.list();
		
		return records;
		
//		UserLoginRecord record = new UserLoginRecord();
//		record.setLoginTime(new Date(0));
//		record.setLoginMode("pc");
//		record.setLoginPlatform("windows 7");
//		record.setLoginPlace("广东省深圳市");
//		record.setLogoutTime(new Date(60 * 1000));
//		
//		List<UserLoginRecord> records = new ArrayList<UserLoginRecord>();
//		records.add(record);
//		
//		return records;
	}
	
	/**
	 * 通过用户id，来获取用户的登录信息
	 * @param userId 用户id
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 登录信息
	 */
	public static List<UserLoginRecord> queryAllByUserIdInPage(long userId, int pageCount, int pageSize)
	{
		String jpql = "select _record from Middle.UserLoginRecord _record where _record.userId=:userId";
		List<UserLoginRecord> records = getRepository().createJpqlQuery(jpql)
				.addParameter("userId", userId)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return records;
		
//		UserLoginRecord record = new UserLoginRecord();
//		record.setLoginTime(new Date(0));
//		record.setLoginMode("pc");
//		record.setLoginPlatform("windows 7");
//		record.setLoginPlace("广东省深圳市");
//		record.setLogoutTime(new Date(60 * 1000));
//		
//		List<UserLoginRecord> records = new ArrayList<UserLoginRecord>();
//		records.add(record);
//		
//		return records;
	}
}
