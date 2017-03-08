package com.apical.dmcloud.rule.middle.core.domain;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.Table;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;
import com.apical.dmcloud.rule.middle.core.RuleAssignmentException;

/**
 * 疲劳驾驶规则表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.RuleFatigue")
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
	 * 根据id来删除规则信息
	 * @param id 规则id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.RuleFatigue _rule where _rule.id=:id";
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
		List<RuleFatigue> rules =  MiddleEntryCacheService.getRuleAssigned2Vehicle(RuleFatigue.class, vehicleId);;
		//boolean isInCache = MiddleEntryCacheService.isKeyInRuleAssigned2VehicleCache(RuleFatigue.class, vehicleId);
		if(rules == null){
		
			String sql = "select * from cl_rule_fatigue where id in"
					+ " (select RULE_ID from cl_rule_fatigue_vehicle_map where VEHICLE_ID=:vehicleId)";
			rules = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.setResultEntityClass(RuleFatigue.class).list();
			if(rules != null){
				MiddleEntryCacheService.saveRuleAssigned2Vehicle(RuleFatigue.class,
						vehicleId, rules);
		
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
		String jpql = "select count(_rule.id) from Middle.RuleFatigue _rule";
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
		String jpql = "select _rule from Middle.RuleFatigue _rule";
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
		String jpql = "select _rule from Middle.RuleFatigue _rule";
		List<RuleFatigue> rules = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
}
