package com.apical.dmcloud.rule.core.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.Table;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;
import com.apical.dmcloud.rule.core.NameIsExistedException;
import com.apical.dmcloud.rule.core.RuleAssignmentException;

/**
 * 疲劳驾驶规则表
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_rule_fatigue")
public class RuleFatigue extends Rule
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 疲劳驾驶时长（单位为分钟）
	 */
	@Column(name = "LENGTH")
	private Short length;
	
	protected RuleFatigue()
	{
	}
	
	public RuleFatigue(String name)
	{
		super(name);
		
		if(isExistRuleName(name))
		{
			throw new NameIsExistedException("rule name is existed!");
		}
	}
	
	/**
	 * 获取疲劳驾驶时长
	 * @return 疲劳驾驶时长
	 */
	public Short getLength() {
		return length;
	}

	/**
	 * 设置疲劳驾驶时长
	 * @param length 驾驶时长
	 */
	public void setLength(Short length) {
		this.length = length;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"RuleFatigue"};
	}
	
	/**
	 * 判断该规则名称是否已被使用
	 * @param name 规则名称
	 * @return 是否已被使用
	 */
	public static boolean isExistRuleName(String name)
	{
		String jpql = "select 1 from RuleFatigue _rule where _rule.name=:name";
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
	 * 根据id来删除规则信息
	 * @param id 规则id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from RuleFatigue _rule where _rule.id=:id";
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
	 * 车辆是否有分配疲劳驾驶规则
	 * @param vehicleId 车辆id
	 * @return 是否已分配
	 */
	public static boolean isAssignedToVehicle(long vehicleId)
	{
		try
		{
			String sql = "select 1 from cl_rule_fatigue_vehicle_map where VEHICLE_ID=:vehicleId limit 1";
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
			String sql = "select 1 from cl_rule_fatigue_vehicle_map where RULE_ID=:ruleId and"
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
			throw new RuleAssignmentException("the RuleFatigue:" + ruleId + " has been assigned to vehicle:" + vehicleId);
		}
		
		String sql = "insert into cl_rule_fatigue_vehicle_map (RULE_ID, VEHICLE_ID) values(:ruleId, :vehicleId)";
		int count = getRepository().createSqlQuery(sql)
				.addParameter("ruleId", ruleId)
				.addParameter("vehicleId", vehicleId)
				.executeUpdate();
		
		if(count == 1)
		{
			MiddleEntryCacheService.removeRuleAssigned2Vehicle(RuleAreaSpeed.class, vehicleId);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * 取消分配规则
	 * @param ruleId 规则id
	 * @param vehicleId 车辆id
	 * @return 操作结果
	 * @throws RuleAssignmentException 
	 */
	public static boolean unAssignToVehicle(long ruleId, long vehicleId) throws RuleAssignmentException
	{
		if(!isAssignedToVehicle(ruleId, vehicleId))
		{
			//如果已经分配给该车辆，则抛出异常
			throw new RuleAssignmentException("the RuleFatigue:" + ruleId + " had not been assigned to vehicle:" + vehicleId);
		}
		
		
		String sql = "delete from cl_rule_fatigue_vehicle_map where RULE_ID =:ruleId and VEHICLE_ID =:vehicleId";
		int count = getRepository().createSqlQuery(sql)
				.addParameter("ruleId", ruleId)
				.addParameter("vehicleId", vehicleId)
				.executeUpdate();
		
		if(count == 1)
		{
			MiddleEntryCacheService.removeRuleAssigned2Vehicle(RuleFatigue.class, vehicleId);
			return true;
			
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过车辆id，来获取该车辆所分配的疲劳驾驶规则信息
	 * @param vehicleId 车辆id
	 * @return 规则信息
	 */
	public static RuleFatigue getByVehicleId(long vehicleId)
	{
		String sql = "select * from cl_rule_fatigue where id in"
				+ " (select RULE_ID from cl_rule_fatigue_vehicle_map where VEHICLE_ID=:vehicleId)";
		List<RuleFatigue> rules = getRepository().createSqlQuery(sql)
				.addParameter("vehicleId", vehicleId)
				.setResultEntityClass(RuleFatigue.class)
				.list();
		
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
		String jpql = "select count(_rule.id) from RuleFatigue _rule";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}
	
	/**
	 * 查询所有规则
	 * @return 规则信息
	 */
	public static List<RuleFatigue> queryAllRules()
	{
		String jpql = "select _rule from RuleFatigue _rule";
		List<RuleFatigue> rules = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return rules;
	}
	
	/**
	 * 查询所有规则
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 规则信息
	 */
	public static List<RuleFatigue> queryAllRulesInPage(int pageCount, int pageSize)
	{
		String jpql = "select _rule from RuleFatigue _rule";
		List<RuleFatigue> rules = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
	
	/**
	 * 查询已分配的车辆ID
	 */
	public  static List<Long> queryVehicleIds(long ruleId){
		String sql = "select VEHICLE_ID from cl_rule_fatigue_vehicle_map where RULE_ID =:ruleId";
		List<BigInteger> vihicleIds = getRepository().createSqlQuery(sql).addParameter("ruleId", ruleId).list();
		List<Long> temps = new ArrayList<Long>();
		
		for(BigInteger i : vihicleIds){
			temps.add(i.longValue());
		}
		return temps;
	}
	
	
	/**
	 * 查询分配规则的车辆信息
	 * @param ruleId
	 * @param pageCount
	 * @param pageSize
	 * @return 车辆
	 */
	public static List<Long> queryAssignedVehicleIdsInPage(long ruleId,int pageCount , int pageSize){
		String sql = "select VEHICLE_ID from cl_rule_fatigue_vehicle_map where RULE_ID =:ruleId";
		List<BigInteger> vihicleIds = getRepository().createSqlQuery(sql).addParameter("ruleId", ruleId).setFirstResult(pageCount*pageSize - pageSize).setMaxResults(pageSize).list();
		List<Long> temps = new ArrayList<Long>();
		
		for(BigInteger i : vihicleIds){
			temps.add(i.longValue());
		}
		return temps;
	}
	
	/**
	 * 统计已分配的车辆数量
	 * @param id 规则ID
	 */
	public static long countAllVechilesById(long ruleId){
		String sql = "select count(VEHICLE_ID) from cl_rule_fatigue_vehicle_map where RULE_ID =:ruleId";
		BigInteger vihicleIds = getRepository().createSqlQuery(sql).addParameter("ruleId", ruleId).singleResult();
		if(vihicleIds == null){
			return 0;
		}
		return vihicleIds.longValue();
	}
	
	
	/**
	 * 查询某公司 已分配某规则的车辆ID列表
	 * @param ruleId 规则ID
	 * @param companyId 公司ID
	 * @return 车辆ID列表
	 */
	public static  List<Long> queryVehicleIds(long ruleId,long companyId){
		
		return Rule.queryVehicleIds("cl_rule_fatigue_vehicle_map", ruleId, companyId);
	}
	
	
	/**
	 * 统计某公司已分配某规则的车辆数量
	 * @param ruleId
	 * @param companyId
	 * @return 数量
	 */
	public static long countAllVechilesById(long ruleId,long companyId){
	
		return Rule.countAllVechilesById("cl_rule_fatigue_vehicle_map", ruleId, companyId);
	}
	
	/**
	 * 查询某公司 已分配某规则的车辆ID列表
	 * @param ruleId
	 * @param pageCount
	 * @param pageSize
	 * @param companyId
	 * @return 车辆ID列表
	 */
	public static List<Long> queryAssignedVehicleIdsInPage(long ruleId,int pageCount , int pageSize,long companyId){
		
		return Rule.queryAssignedVehicleIdsInPage("cl_rule_fatigue_vehicle_map", ruleId, pageCount, pageSize, companyId);
	}
	

}
