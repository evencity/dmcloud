package com.apical.dmcloud.security.core.domain;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Assert;
import org.openkoala.security.core.domain.EncryptService;
import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.commons.infra.Gender;
import com.apical.dmcloud.security.core.EmailIsExistedException;
import com.apical.dmcloud.security.core.TelePhoneIsExistedException;
import com.apical.dmcloud.security.core.UserAccountIsExistedException;
import com.apical.dmcloud.security.core.UserNotExistException;
import com.apical.dmcloud.security.core.UserPasswordException;

/**
 * 用户信息。 可以对其授予角色 Role、权限 Permission
 *
 * @author lucas
 */

@Entity
@Table(name = "cl_users")
public class User extends SecurityAbstractEntity {
	/**
	 * serialVersionUID = 7849700468353029794L
	 */
	private static final long serialVersionUID = 7849700468353029794L;
	
	/**
	 * 初始化密码：888888
	 */
	public static final String INIT_PASSWORD = "888888";
	
	/**
	 * 子账户初始化密码：666666
	 */
	public static final String INIT_PASSWORD_SUB_ACCOUNT = "666666";
	
	/**
	 * 名称
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 最后更新时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_MODIFY_TIME")
	private Date lastModifyTime;
	
	/**
	 * 创建者
	 */
	@Column(name = "CREATE_OWNER")
	private String createOwner;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	private Date createDate = new Date();
	
	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * 用户帐户名
	 */
	@NotNull
	@Column(name = "USER_ACCOUNT")
	private String userAccount;
	
	/**
	 * 用户密码
	 */
	@Column(name = "PASSWORD")
	private String password = INIT_PASSWORD;
	
	/**
	 * 用户邮件
	 */
	@Column(name = "EMAIL")
	private String email;
	
	/**
	 * 用户状态
	 */
	@Column(name = "DISABLED")
	private boolean disabled = false;
	
	/**
	 * 用户电话号码
	 */
	@Column(name = "TELE_PHONE")
	private String telePhone;
	
	/**
	 * 性别
	 * 0：保密
	 */
	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "GENDER")
	private Gender gender = Gender.Unknown;
	
	/**
	 * 用户头像文件存储信息id
	 */
	@Column(name = "PHOTOID")
	private Long photoId;
	
	/**
	 * 用户所在的地区
	 */
	@Column(name = "REGION")
	private String region;
	
	/**
	 * 用户密码盐值
	 */
	@Column(name = "SALT")
	private String salt;
	
	/**
	 * 用户所属的公司id
	 */
	@Column(name = "COM_ID")
	private Long companyId;
	
	/**
	 * 用户主账户id
	 */
	@Column(name = "PID")
	private Long pId;
	
	public User() {
	}
	
	public User(String name, String userAccount) {
		checkName(name);
		checkUserAccount(userAccount);
		
		if(isExistUserAccount(userAccount))
		{
			throw new UserAccountIsExistedException("user userAccount is existed.");
		}
		
		this.name = name;
		this.userAccount = userAccount;
		this.salt = UUID.randomUUID().toString();
		this.password = encryptPassword(INIT_PASSWORD);
	}
	
	public User(String name, String userAccount,Boolean isSubAccount) {
		checkName(name);
		checkUserAccount(userAccount);
		
		if(isExistUserAccount(userAccount))
		{
			throw new UserAccountIsExistedException("user userAccount is existed.");
		}
		
		this.name = name;
		this.userAccount = userAccount;
		this.salt = UUID.randomUUID().toString();
		if(isSubAccount){
			this.password = encryptPassword(INIT_PASSWORD_SUB_ACCOUNT);
		}else{
			this.password = encryptPassword(INIT_PASSWORD);
		}
		
	}
	
	public User(String name, String userAccount, String password) {
		checkName(name);
		checkUserAccount(userAccount);
		
		if(isExistUserAccount(userAccount))
		{
			throw new UserAccountIsExistedException("user userAccount is existed.");
		}
		
		this.name = name;
		this.userAccount = userAccount;
		this.salt = UUID.randomUUID().toString();
		this.password = encryptPassword(password);
	}
	
	private void verifyPassword(String userPassword) throws UserPasswordException {
		if (!encryptPassword(userPassword).equals(this.getPassword())) {
		    throw new UserPasswordException("user password is not right.");
		}
	}
	
	/**
	 * 使用断言方式检测用户名是否为空
	 * @param name 用户名称
	 */
	private void checkName(String name) {
		Assert.notBlank(name, "name cannot be empty.");
	}
	
	private void checkUserAccount(String userAccount) {
		Assert.notBlank(userAccount, "userAccount cannot be empty.");
	}
	 
	private void checkEmail(String email) {
	    Assert.notBlank(email, "email cannot be empty.");
	}
	
	private void checkTelePhone(String telePhone) {
		Assert.notBlank(telePhone, "telePhone cannot be empty.");
	}
	
	private void checkRegion(String region) {
		Assert.notBlank(region, "region cannot be empty.");
	}
	
	public String encryptPassword(String password) {
	    return getPasswordEncryptService().encryptPassword(password, salt + userAccount);
	}
	
	@Override
	public String toString() {
	    return new ToStringBuilder(this)
	            .append(getId())
	            .append(getUserAccount())
	            .append(getEmail())
	            .append(getTelePhone())
	            .append(getName())
	            .build();
	}
	
	/**
	 * 获取用户帐户名称
	 * @return 帐户名称
	 */
	public String getUserAccount() {
	    return userAccount;
	}
	
	/**
	 * 设置帐户名称
	 * @param userAccount 帐户名称
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * 获取加密后的密码
	 * @return 加密后的密码
	 */
	public String getPassword() {
	    return password;
	}
	
	/**
	 * 设置加密后的密码
	 * @param password 加密后的密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取电子邮箱
	 * @return 电子邮箱
	 */
	public String getEmail() {
	    return email;
	}
	
	/**
	 * 设置电子邮箱
	 * @param email 电子邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 该帐户是否无效
	 * @return 是否无效
	 */
	public boolean isDisabled() {
	    return disabled;
	}
	
	/**
	 * 设置帐户是否无效
	 * @param disabled 是否无效
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * 获取电话号码
	 * @return 电话号码
	 */
	public String getTelePhone() {
	    return telePhone;
	}
	
	/**
	 * 设置电话号码
	 * @param telePhone 电话号码
	 */
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	/**
	 * 获取性别
	 * @return 性别
	 */
	public Gender getGender()
	{
		return gender;
	}
	
	/**
	 * 设置性别
	 * @param gender 性别
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * 设置头像存储信息id
	 * @return 头像存储信息id
	 */
	public Long getPhotoId()
	{
		return photoId;
	}
	
	/**
	 * 设置头像存储信息id
	 * @param photoId 头像存储信息id
	 */
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	/**
	 * 获取所在的区域
	 * @return 区域
	 */
	public String getRegion()
	{
		return region;
	}
	
	/**
	 * 设置所在的区域
	 * @param region 所在的区域
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * 获取盐值
	 * @return 盐值
	 */
	public String getSalt() {
		return salt;
	}
	
	/**
	 * 设置盐值
	 * @param salt 盐值
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 获取参与者的名称
	 * @return 参与者名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置参与者名称
	 * @param name 参与者名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取描述信息
	 * @return 描述信息
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 设置描述信息
	 * @param description 描述信息
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 获取公司id
	 * @return 公司id
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * 设置公司id
	 * @param companyId 公司id
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * 获取最后一次修改时间
	 * @return 最后一次修改时间
	 */
	public Date getLastModifyTime()
	{
		return lastModifyTime;
	}
	
	/**
	 * 设置最后一次修改时间
	 * @param lastModifyTime 最后一次修改时间
	 */
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * 获取创建者帐户名
	 * @return 创建者帐户名
	 */
	public String getCreateOwner() {
		return createOwner;
	}
	
	/**
	 * 设置创建者帐户名
	 * @param createOwner 创建者帐户名
	 */
	public void setCreateOwner(String createOwner) {
		this.createOwner = createOwner;
	}
	
	/**
	 * 获取创建日期
	 * @return 创建日期
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * 设置创建日期
	 * @param createDate 创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 更新用户密码
	 * @param userPassword 用户新密码
	 * @param oldUserPassword 用户旧密码
	 * @return 用户更新密码是否成功
	 */
	public boolean updatePassword(String userPassword, String oldUserPassword) {
		String encryptOldUserPassword = encryptPassword(oldUserPassword);
	    if (this.getPassword().equals(encryptOldUserPassword)) {
	        this.password = encryptPassword(userPassword);
	        return true;
	    }
	    return false;
	}
	
	/**
	 * 重置用户密码
	 */
	public void resetPassword() {
		User user = User.get(User.class, this.getId());
	    user.password = encryptPassword(INIT_PASSWORD);
	}
	
	/**
	 * 重置用户密码
	 */
	public void resetPassword_SubAccount() {
		User user = User.get(User.class, this.getId());
	    user.password = encryptPassword(INIT_PASSWORD_SUB_ACCOUNT);
	}
	
	/**
	 * 用户修改帐户名
	 * @param userAccount 用户新的帐户名
	 * @param userPassword 用户密码
	 * @throws UserPasswordException 
	 */
	public void changeUserAccount(String userAccount, String userPassword) throws UserPasswordException {
		checkUserAccount(userAccount);
		
		if (!this.getUserAccount().equals(userAccount)) {
			if(isExistUserAccount(userAccount))
			{
				throw new UserAccountIsExistedException("user userAccount is existed.");
			}
			
			verifyPassword(userPassword);
			this.userAccount = userAccount;
			save();
		}
	}
	
	/**
	 * 修改用户电子邮箱
	 * @param email 电子邮箱
	 * @param userPassword 用户密码
	 * @throws UserPasswordException 
	 * @throws EmailIsExistedException 
	 */
	public void changeEmail(String email, String userPassword) throws UserPasswordException, EmailIsExistedException {
		
	    checkEmail(email);
	    
	    if (!email.equals(this.getEmail())) {
	        if(isExistEmail(email))
	        {
	        	throw new EmailIsExistedException("user email is existed.");
	        }
	        
	        verifyPassword(userPassword);
	        this.email = email;
	        save();
	    }
	}
	
	public void changeTelePhone(String telePhone, String userPassword) throws UserPasswordException, TelePhoneIsExistedException {
		checkTelePhone(telePhone);
		
		if (!telePhone.equals(this.getTelePhone())) {
			if(isExistTelePhone(telePhone))
			{
				throw new TelePhoneIsExistedException("user telePhone is existed.");
			}
			
			verifyPassword(userPassword);
			this.telePhone = telePhone;
			save();
		}
	}
	
	/**
	 * 修改性别
	 * @param gender 性别
	 */
	public void changeGender(Gender gender)
	{
		if (this.gender != gender) {
			this.gender = gender;
			save();
		}
	}
	
	/**
	 * 修改所在的地方
	 * @param region 地方
	 */
	public void changeRegion(String region)
	{
		checkRegion(region);
	    
		if (!region.equals(this.region)) {
			this.region = region;
			save();
		}
	}
	
	/**
	 * 修改用户的头像文件
	 * @param photoId 头像文件的存储信息id
	 */
	public void changePhoto(long photoId)
	{
		this.photoId = photoId;
		save();
	}
	
	/**
	 * 更改最后修改时间。
	 */
	public void changeLastModifyTime() {
		this.lastModifyTime = new Date();
		save();
	}
	
	@Override
	public String[] businessKeys() {
		return new String[]{"userAccount"};
	}
	
	@Override
	public void save() {
		super.save();
	}
	
	/**
	 * 根据用户id，来删除用户信息
	 * @param userId 用户id
	 * @return 删除是否成功
	 */
	public static boolean deleteByUserId(long userId)
	{
		String jpql = "delete from User _user where _user.id = :userId";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("userId", userId)
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
	 * 为参与者分配授权。
	 * @param userId 用户id
	 * @param authorityId 授权id
	 */
	public static boolean grantAuthority(long userId, long authorityId) {
		if (Authorization.checkAuthorization(userId, authorityId)) {
			return true;
		}
		
		User user = User.getByUserId(userId);
		Authority authority = Authority.getById(authorityId);
		new Authorization(user, authority).save();
		return false;
	}
	
	/**
	 * 从参与者中撤销授权
	 * @param userId 用户id
	 * @param authorityId 授权id
	 */
	public static boolean terminateAuthority(long userId, long authorityId) {
		return Authorization.terminateAuthorization(userId, authorityId);
	}
	
	/**
	 * 判断某电话号码是否已被使用
	 * @param telePhone 电话号码
	 */
	public static boolean isExistTelePhone(String telePhone) {
		String jpql = "select 1 from User _user where _user.telePhone=:phone";
		Integer result = getRepository().createJpqlQuery(jpql)
				.addParameter("phone", telePhone)
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
	 * 判断某电子邮箱是否已被使用
	 * @param email 电子邮箱
	 */
	public static boolean isExistEmail(String email) {
		String jpql = "select 1 from User _user where _user.email=:email";
		Integer result = getRepository().createJpqlQuery(jpql)
				.addParameter("email", email)
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
	 * 判断某个帐户是否已被注册
	 * @param userAccount 帐户名
	 */
	public static boolean isExistUserAccount(String userAccount) {
		String jpql = "select 1 from User _user where _user.userAccount=:userAccount";
		Integer result = getRepository().createJpqlQuery(jpql)
				.addParameter("userAccount", userAccount)
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
	 * 通过用户帐户名称和密码，来验证用户
	 * @param userAccount 用户帐户名称
	 * @param passwd 密码
	 * @return 用户验证是否成功
	 */
	public static boolean verifyUserByAccountAndPassword(String userAccount, String passwd) {
		User user = getByUserAccount(userAccount);
		if(user != null)
		{
			String encryptUserPassword = user.encryptPassword(passwd);
		    if (user.getPassword().equals(encryptUserPassword))
		    {
		    	return true;
		    }
		    else
		    {
		    	return false;
		    }
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过用户id来获取用户信息
	 * @param userId 用户id
	 * @return 用户信息
	 */
	public static User getByUserId(long userId) {
	    return User.get(User.class, userId);
	}
	
	/**
	 * 通过用户帐户名来获取用户信息
	 * @param userAccount 用户帐户名
	 * @return 用户信息
	 */
	public static User getByUserAccount(String userAccount) {
		User user = getRepository().createCriteriaQuery(User.class)
	            .eq("userAccount", userAccount)
	            .singleResult();
	    return user;
	}
	
	/**
	 * 通过邮件，来获取用户信息
	 * @param email 邮件
	 * @return 用户信息
	 */
	public static User getByEmail(String email) {
		User user = getRepository().createCriteriaQuery(User.class)
	            .eq("email", email)
	            .singleResult();
		return user;
	}
	
	/**
	 * 通过电话号码，来获取用户信息
	 * @param telePhone 电话号码
	 * @return 用户信息
	 */
	public static User getByTelePhone(String telePhone) {
		User user = getRepository().createCriteriaQuery(User.class)
	            .eq("telePhone", telePhone)
	            .singleResult();
		return user;
	}
	
	/**
	 * 通过用户id来获取用户帐户名称
	 * @param userId
	 * @return 用户帐户名称
	 * @throws UserNotExistException 
	 */
	public static String getUserAccountByUserId(long userId) throws UserNotExistException {
		String jpql = "SELECT _user.userAccount FROM User _user where _user.id = :userId";
		String userAccount = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("userId", userId)
				.singleResult();
		if(userAccount == null) {
			throw new UserNotExistException("user id:" + userId + " not exist!");
		}
		return userAccount;
	}
	
	/**
	 * 通过用户帐户名来获取用户信息
	 * @param userAccount 用户帐户名
	 * @return 用户信息id
	 * @throws UserNotExistException 
	 */
	public static long getIdByUserAccount(String userAccount) throws UserNotExistException {
		String jpql = "SELECT _user.id From User _user WHERE _user.userAccount=:userAccount";
		Long id = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("userAccount", userAccount)
	            .singleResult();
		if(id == null)
		{
			throw new UserNotExistException("user:" + userAccount + " not exist!");
		}
		
		return id;
	}
	
	/**
	 * 分页获取用户信息
	 * @param disable 用户是否可用
	 * @param userName 用户名
	 * @param userAccount 用户帐户名
	 * @param description 用户描述
	 * @param email 邮件
	 * @param telephone 电话
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 用户信息
	 */
	public static List<User> findAllUsersInPage(Boolean disable,
			String userName,
			String userAccount,
			String description,
			String email,
			String telephone,
			int pageCount,
			int pageSize) {
		Map<String, Object> conditionVals = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder("SELECT _user FROM User _user where 1=1");
		
		assembleUserJpqlAndConditionValues(disable,
				userName,
				userAccount,
				description,
				email,
				telephone,
				jpql,
				"_user",
				conditionVals);
		
		return getRepository().createJpqlQuery(jpql.toString())
				.setParameters(conditionVals)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
	}
	
	/**
	 * 通过公司id，来获取用户信息
	 * @param companyId 公司id
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 用户信息
	 */
	public static List<User> findAllUsersByCompanyIdInPage(long companyId,
			int pageCount, int pageSize) {
		String jpql = "SELECT _user FROM User _user where _user.companyId=:companyId";
		List<User> users = getRepository().createJpqlQuery(jpql)
				.addParameter("companyId", companyId)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		return users;
	}
	
	/**
	 * 用户是否存在
	 * 
	 * @param userId 用户id
	 * @return 是否已存在，true：表示已存在，false：表示未存在
	 */
	public static boolean isUserExisted(long userId) {
		try
		{
			StringBuilder sql = new StringBuilder(
					"select 1 from ks_actors where ID = :id limit 1");
			BigInteger count = getRepository().createSqlQuery(sql.toString())
					.addParameter("id", userId)
					.singleResult();
			if(count != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(NoResultException e)
		{
			return false;
		}
	}
	
	 /**
	 * 检测系统中是否有用户
	 * @return 用户是否存在
	 */
	public static boolean hasUserExisted() {
		Long result = getRepository().createNamedQuery("User.count")
				.singleResult();
		return result > 0;
	}
	
	private static void assembleUserJpqlAndConditionValues(Boolean disabled,
			String userName,
			String userAccount,
			String description,
			String email,
			String telephone,
			StringBuilder jpql,
			String conditionPrefix,
			Map<String, Object> conditionVals) {
	    String andCondition = " AND " + conditionPrefix;
	    
	    if (null != disabled) {
	        jpql.append(andCondition);
	        jpql.append(".disabled = :disabled");
	        conditionVals.put("disabled", disabled);
	    }
	    
	    if (!StringUtils.isBlank(userName)) {
	        jpql.append(andCondition);
	        jpql.append(".name LIKE :name");
	        conditionVals.put("name", MessageFormat.format("%{0}%", userName));
	    }
	    
	    if (!StringUtils.isBlank(userAccount)) {
	        jpql.append(andCondition);
	        jpql.append(".userAccount LIKE :userAccount");
	        conditionVals.put("userAccount", MessageFormat.format("%{0}%", userAccount));
	    }
	    
	    if (!StringUtils.isBlank(email)) {
	        jpql.append(andCondition);
	        jpql.append(".email LIKE :email");
	        conditionVals.put("email", MessageFormat.format("%{0}%", email));
	    }
	    
	    if (!StringUtils.isBlank(telephone)) {
	        jpql.append(andCondition);
	        jpql.append(".telePhone LIKE :telePhone");
	        conditionVals.put("telePhone", MessageFormat.format("%{0}%", telephone));
	    }
	    
	    if (!StringUtils.isBlank(description)) {
	        jpql.append(andCondition);
	        jpql.append(".description LIKE :description");
	        conditionVals.put("description", MessageFormat.format("%{0}%", description));
	    }
	}
	
	protected static EncryptService passwordEncryptService;
	
	protected static EncryptService getPasswordEncryptService() {
	    if (passwordEncryptService == null) {
	        passwordEncryptService = InstanceFactory.getInstance(EncryptService.class);
	    }
	    return passwordEncryptService;
	}
	
	public static void setPasswordEncryptService(EncryptService passwordEncryptService) {
	    User.passwordEncryptService = passwordEncryptService;
	} 
	
	/**
	 * 根据用户ID查所属公司ID
	 * @param userId
	 * @return 公司ID
	 */
	public static Long getCompanyIdByUserId(long userId) {
		String jpql = "SELECT _user.companyId FROM User _user where _user.id=:userId";
		Long cId= getRepository().createJpqlQuery(jpql)
				.addParameter("userId", userId).singleResult();
		return cId;
	}

	/**
	 * 根据公司ID查询主账户
	 * @param 公司ID
	 * @return userId
	 */
	public static Long getPrimaryAccountByComId(long companyId) {
		String jpql = "SELECT _user.id FROM User _user where _user.companyId=:companyId AND _user.pId = 0";
		Long userId= getRepository().createJpqlQuery(jpql)
				.addParameter("companyId", companyId).singleResult();
		return userId;
	}
	
	/**
	 * 根据主账户ID查询有无子账户
	 * @param 公司ID
	 * @return userId
	 */
	public static boolean isExcitSubAccout(long pId) {
		String jpql = "SELECT _user.id FROM User _user where _user.pId=:pId";
		Long subId = getRepository().createJpqlQuery(jpql)
				.addParameter("pId", pId).singleResult();
		if(subId != null){
			return true;
		}
		return false;
	}
	
	
	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}
	
}