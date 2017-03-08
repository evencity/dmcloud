package com.apical.dmcloud.rule.middle.core.domain;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;
import com.apical.dmcloud.rule.middle.core.RuleAssignmentException;

/**
 * 区域禁出规则表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.RuleNoout")
@Table(name = "cl_rule_noout")
public class RuleNoout extends Rule
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 禁出的区域
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "AREA_ID")
	private DrivingArea area;
	
	/**
	 * 开始时间
	 */
	@Temporal(TemporalType.TIME)
	@Column(name = "BEGIN")
	private Date beginTime;
	
	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIME)
	@Column(name = "END")
	private Date endTime;
	
	protected RuleNoout()
	{
	}
	
	public RuleNoout(String name)
	{
	}

	/**
	 * 获取禁出区域
	 * @return 进出区域
	 */
	public DrivingArea getArea() {
		return area;
	}

	/**
	 * 设置禁出区域
	 * @param area 禁出区域
	 */
	public void setArea(DrivingArea area) {
		this.area = area;
	}

	/**
	 * 获取开始时间
	 * @return 开始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置开始时间
	 * @param beginTime 开始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 获取结束时间
	 * @return 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置结束时间
	 * @param endTime 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"RuleNoout"};
	}
	
	/**
	 * 根据id来删除规则信息
	 * @param id 规则id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.RuleNoout _rule where _rule.id=:id";
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
	 * 车辆是否有分配区域禁出规则
	 * @param vehicleId 车辆id
	 * @return 是否已分配
	 */
	public static boolean isAssignedToVehicle(long vehicleId)
	{
		try
		{
			String sql = "select 1 from cl_rule_noout_vehicle_map where VEHICLE_ID=:vehicleId limit 1";
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
			String sql = "select 1 from cl_rule_noout_vehicle_map where RULE_ID=:ruleId and"
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
			throw new RuleAssignmentException("the RuleNoout:" + ruleId + " has been assigned to vehicle:" + vehicleId);
		}
		
		String sql = "insert into cl_rule_noout_vehicle_map (RULE_ID, VEHICLE_ID) values(:ruleId, :vehicleId)";
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
	 * 通过车辆id，来获取该车辆所分配的区域禁出规则信息
	 * @param vehicleId 车辆id
	 * @return 规则信息
	 */
	public static RuleNoout getByVehicleId(long vehicleId)
	{
		List<RuleNoout> rules = MiddleEntryCacheService.getRuleAssigned2Vehicle(RuleNoout.class, vehicleId);;
		//boolean isInCache = MiddleEntryCacheService.isKeyInRuleAssigned2VehicleCache(RuleNoout.class, vehicleId);
		if(rules == null){
			String sql = "select * from cl_rule_noout where id in"
					+ " (select RULE_ID from cl_rule_noout_vehicle_map where VEHICLE_ID=:vehicleId)";
			 rules = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.setResultEntityClass(RuleNoout.class)
					.list();
			 if(rules != null){
				 MiddleEntryCacheService.saveRuleAssigned2Vehicle(RuleNoout.class, vehicleId, rules);

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
		String jpql = "select count(_rule.id) from Middle.RuleNoout _rule";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}
	
	/**
	 * 查询所有规则
	 * @return 规则信息
	 */
	public static List<RuleNoout> queryAllRules()
	{
		String jpql = "select _rule from Middle.RuleNoout _rule";
		List<RuleNoout> rules = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return rules;
	}
	
	/**
	 * 查询所有规则
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 规则信息
	 */
	public static List<RuleNoout> queryAllRulesInPage(int pageCount, int pageSize)
	{
		String jpql = "select _rule from Middle.RuleNoout _rule";
		List<RuleNoout> rules = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
}
