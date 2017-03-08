package com.apical.dmcloud.rule.middle.core.domain;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;
import com.apical.dmcloud.middle.infra.AbstractVersionEntity;
import com.apical.dmcloud.rule.middle.core.RuleAssignmentException;

/**
 * 震动报警规则表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.RuleVibration")
@Table(name = "cl_rule_vibration")
public class RuleVibration extends AbstractVersionEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 规则名称
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 规则描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * 规则所属公司id
	 */
	@Column(name = "COM_ID")
	private Long companyId;
	
	/**
	 * 创建用户id
	 */
	@Column(name = "USER_ID")
	private Long userId;
	
	/**
	 * 拍照信息表
	 */
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "TAKEPIC_ID")
	private TakePictureInfo takePictureInfo;
	
	/**
	 * 录像信息表
	 */
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "TAKEVID_ID")
	private TakeVideoInfo takeVideoInfo;
	
	/**
	 * 电话列表，电话直接用';'分割
	 */
	@Column(name = "PHONES")
	private String phones;
	
	/**
	 * 电子邮箱列表，电子邮箱用';'分割
	 */
	@Column(name = "MAILS")
	private String mails;
	
	protected RuleVibration()
	{
	}
	
	public RuleVibration(String name)
	{
	}

	/**
	 * 获取规则名称
	 * @return 规则名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置规则名称
	 * @param name 规则名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取规则描述
	 * @return 规则描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置规则描述
	 * @param description 规则描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取所属公司id
	 * @return 所属公司id
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * 设置所属公司id
	 * @param companyId 所属公司id
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * 获取创建用户id
	 * @return 创建用户id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置创建用户id
	 * @param userId 创建用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取拍照信息
	 * @return 拍照信息
	 */
	public TakePictureInfo getTakePictureInfo() {
		return takePictureInfo;
	}

	/**
	 * 设置拍照信息
	 * @param takePictureInfo 拍照信息
	 */
	public void setTakePictureInfo(TakePictureInfo takePictureInfo) {
		this.takePictureInfo = takePictureInfo;
	}

	/**
	 * 获取录像信息
	 * @return 录像信息
	 */
	public TakeVideoInfo getTakeVideoInfo() {
		return takeVideoInfo;
	}

	/**
	 * 设置录像信息
	 * @param takeVideoInfo 录像信息
	 */
	public void setTakeVideoInfo(TakeVideoInfo takeVideoInfo) {
		this.takeVideoInfo = takeVideoInfo;
	}

	/**
	 * 获取电话列表，电话直接用';'分割
	 * @return 电话列表
	 */
	public String getPhones() {
		return phones;
	}

	/**
	 * 设置电话列表，电话直接用';'分割
	 * @param phones 电话列表
	 */
	public void setPhones(String phones) {
		this.phones = phones;
	}

	/**
	 * 获取电子邮箱列表，电子邮箱用';'分割
	 * @return 电子邮箱列表
	 */
	public String getMails() {
		return mails;
	}

	/**
	 * 设置电子邮箱列表，电子邮箱用';'分割
	 * @param mails 电子邮箱列表
	 */
	public void setMails(String mails) {
		this.mails = mails;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"RuleVibration"};
	}
	
	/**
	 * 根据id来删除规则信息
	 * @param id 规则id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.RuleVibration _rule where _rule.id=:id";
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
	 * 判断该规则名称是否已被使用
	 * @param name 规则名称
	 * @return 是否已被使用
	 */
	public static boolean isExistRuleName(String name)
	{
		String jpql = "select 1 from Middle.RuleVibration _rule where _rule.name=:name";
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
	 * 车辆是否有分配夜间驾驶规则
	 * @param vehicleId 车辆id
	 * @return 是否已分配
	 */
	public static boolean isAssignedToVehicle(long vehicleId)
	{
		try
		{
			String sql = "select 1 from cl_rule_vibration_vehicle_map where VEHICLE_ID=:vehicleId limit 1";
			BigInteger result = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
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
		catch(NoResultException e)
		{
			return false;
		}
	}
	
	/**
	 * 规则是否已分配给车辆
	 * @param ruleId 规则id
	 * @param vehicleId 车辆id
	 * @return 是否已分配
	 */
	public static boolean isAssignedToVehicle(long ruleId, long vehicleId)
	{
		try
		{
			String sql = "select 1 from cl_rule_vibration_vehicle_map where RULE_ID=:ruleId and"
					+ " VEHICLE_ID=:vehicleId limit 1";
			BigInteger result = getRepository().createSqlQuery(sql)
					.addParameter("ruleId", ruleId)
					.addParameter("vehicleId", vehicleId)
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
		catch(NoResultException e)
		{
			return false;
		}
	}
	
	/**
	 * 将规则分配给某个车辆
	 * @param ruleId 规则id
	 * @param vehicleId 车辆id
	 * @return 分配是否成功
	 * @throws RuleAssignmentException 
	 */
	public static boolean assignToVehicle(long ruleId, long vehicleId) throws RuleAssignmentException
	{
		if(isAssignedToVehicle(vehicleId))
		{
			//如果已经分配给该车辆，则抛出异常
			throw new RuleAssignmentException("the RuleVibration:" + ruleId + " has been assigned to vehicle:" + vehicleId);
		}
		
		String sql = "insert into cl_rule_vibration_vehicle_map (RULE_ID, VEHICLE_ID) values(:ruleId, :vehicleId)";
		int count = getRepository().createSqlQuery(sql)
				.addParameter("ruleId", ruleId)
				.addParameter("vehicleId", vehicleId)
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
	 * 通过车辆id，来获取该车辆所分配的夜间驾驶规则信息
	 * @param vehicleId 车辆id
	 * @return 规则信息
	 */
	public static RuleVibration getByVehicleId(long vehicleId)
	{
		List<RuleVibration> rules = MiddleEntryCacheService.getRuleAssigned2Vehicle(RuleVibration.class, vehicleId);;
		//boolean isInCache = MiddleEntryCacheService.isKeyInRuleAssigned2VehicleCache(RuleFatigue.class, vehicleId);
		if(rules == null){
		String sql = "select * from cl_rule_vibration where id in"
				+ " (select RULE_ID from cl_rule_vibration_vehicle_map where VEHICLE_ID=:vehicleId)";
		rules = getRepository().createSqlQuery(sql)
				.addParameter("vehicleId", vehicleId)
				.setResultEntityClass(RuleVibration.class)
				.list();
		if(rules != null){
			 MiddleEntryCacheService.saveRuleAssigned2Vehicle(RuleVibration.class, vehicleId, rules);
				
		}
		}
		if(rules.size() > 0)
		{
			return rules.get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 统计规则数量
	 * @return 规则数量
	 */
	public static long countAllRules()
	{
		String jpql = "select count(_rule.id) from Middle.RuleVibration _rule";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}
	
	/**
	 * 查询所有规则
	 * @return 规则信息
	 */
	public static List<RuleVibration> queryAllRules()
	{
		String jpql = "select _rule from Middle.RuleVibration _rule";
		List<RuleVibration> rules = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return rules;
	}
	
	/**
	 * 查询所有规则
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 规则信息
	 */
	public static List<RuleVibration> queryAllRulesInPage(int pageCount, int pageSize)
	{
		String jpql = "select _rule from Middle.RuleVibration _rule";
		List<RuleVibration> rules = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
}
