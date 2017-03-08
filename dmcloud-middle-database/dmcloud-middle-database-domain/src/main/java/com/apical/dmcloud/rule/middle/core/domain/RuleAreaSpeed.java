package com.apical.dmcloud.rule.middle.core.domain;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;
import com.apical.dmcloud.rule.middle.core.RuleAssignmentException;
import com.apical.dmcloud.vehicle.middle.core.domain.Vehicle;

/**
 * 区域限速规则表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.RuleAreaSpeed")
@Table(name = "cl_rule_areaspeed")
public class RuleAreaSpeed extends Rule
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 限速的区域
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "AREA_ID")
	private DrivingArea area;
	
	/**
	 * 最大速度
	 */
	@Column(name = "MAXSPEED")
	private Float maxSpeed;
	
	/**
	 * 最低速度
	 */
	@Column(name = "MINSPEED")
	private Float minSpeed;
	
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
	
	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinTable(name = "cl_rule_area_vehicle_map",
			joinColumns = @JoinColumn(name = "RULE_ID", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID"))
	private Set<Vehicle> vehicles;
	
	protected RuleAreaSpeed()
	{
	}
	
	public RuleAreaSpeed(String name)
	{
	}

	/**
	 * 获取限速区域
	 * @return 限速区域
	 */
	public DrivingArea getArea() {
		return area;
	}

	/**
	 * 设置限速区域
	 * @param area 限速区域
	 */
	public void setArea(DrivingArea area) {
		this.area = area;
	}

	/**
	 * 最大速度
	 * @return 最大速度
	 */
	public Float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * 最大速度
	 * @param maxSpeed 最大速度
	 */
	public void setMaxSpeed(Float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * 最低速度
	 * @return 最低速度
	 */
	public Float getMinSpeed() {
		return minSpeed;
	}

	/**
	 * 最低速度
	 * @param minSpeed 最低速度
	 */
	public void setMinSpeed(Float minSpeed) {
		this.minSpeed = minSpeed;
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
		return new String[]{"RuleAreaSpeed"};
	}
	
	/**
	 * 根据id来删除规则信息
	 * @param id 规则id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.RuleAreaSpeed _rule"
				+ " where _rule.id=:id";
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
	 * 车辆是否有分配区域限速规则
	 * @param vehicleId 车辆id
	 * @return 是否已分配
	 */
	public static boolean isAssignedToVehicle(long vehicleId)
	{
		try
		{
			String sql = "select 1 from cl_rule_area_vehicle_map"
					+ " where VEHICLE_ID=:vehicleId"
					+ " limit 1";
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
			String sql = "select 1 from cl_rule_area_vehicle_map"
					+ " where RULE_ID=:ruleId"
					+ " and VEHICLE_ID=:vehicleId"
					+ " limit 1";
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
		if(isAssignedToVehicle(ruleId, vehicleId))
		{
			//如果已经分配给该车辆，则抛出异常
			throw new RuleAssignmentException("the RuleAreaSpeed:" + ruleId + " has been assigned to vehicle:" + vehicleId);
		}
		
		String sql = "insert into cl_rule_area_vehicle_map (RULE_ID, VEHICLE_ID)"
				+ " values(:ruleId, :vehicleId)";
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
	 * 通过车辆id，来获取该车辆所分配的区域限速规则数量
	 * @param vehicleId 车辆id
	 * @return 规则数量
	 */
	public static long countAllRulesByVehicleId(long vehicleId)
	{
		String sql = "select count(1) from cl_rule_area_vehicle_map"
				+ " where VEHICLE_ID=:vehicleId";
		BigInteger count = getRepository().createSqlQuery(sql)
				.addParameter("vehicleId", vehicleId)
				.singleResult();
		
		return count.longValue();
	}
	
	/**
	 * 通过车辆id，来获取该车辆所分配的区域限速规则信息
	 * @param vehicleId 车辆id
	 * @return 规则信息
	 */
	public static List<RuleAreaSpeed> queryAllRulesByVehicleId(long vehicleId)
	{
		//boolean isInCache = MiddleEntryCacheService.isKeyInRuleAssigned2VehicleCache(RuleAreaSpeed.class, vehicleId);
		List<RuleAreaSpeed> rules  = MiddleEntryCacheService.getRuleAssigned2Vehicle(RuleAreaSpeed.class, vehicleId);
		
		if(rules == null){

			String sql = "select * from cl_rule_areaspeed where id in"
					+ " (select RULE_ID from cl_rule_area_vehicle_map where VEHICLE_ID=:vehicleId)";
			rules = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.setResultEntityClass(RuleAreaSpeed.class).list();
			if(rules != null){
				MiddleEntryCacheService.saveRuleAssigned2Vehicle(RuleAreaSpeed.class, vehicleId, rules);

			}
		 }
		return rules;
	}
	
	/**
	 * 通过车辆id，来分页获取该车辆所分配的区域限速规则信息
	 * @param vehicleId 车辆id
	 * @return 规则信息
	 */
	public static List<RuleAreaSpeed> queryAllRulesByVehicleIdInPage(long vehicleId,
			int pageCount, int pageSize)
	{
		String sql = "select * from cl_rule_areaspeed where id in"
				+ " (select RULE_ID from cl_rule_area_vehicle_map where VEHICLE_ID=:vehicleId)";
		List<RuleAreaSpeed> rules = getRepository().createSqlQuery(sql)
				.addParameter("vehicleId", vehicleId)
				.setResultEntityClass(RuleAreaSpeed.class)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
	
	/**
	 * 统计规则数量
	 * @return 规则数量
	 */
	public static long countAllRules()
	{
		String jpql = "select count(_rule.id) from Middle.RuleAreaSpeed _rule";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}
	
	/**
	 * 查询所有规则
	 * @return 规则信息
	 */
	public static List<RuleAreaSpeed> queryAllRules()
	{
		String jpql = "select _rule from Middle.RuleAreaSpeed _rule";
		List<RuleAreaSpeed> rules = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return rules;
	}
	
	/**
	 * 查询所有规则
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 规则信息
	 */
	public static List<RuleAreaSpeed> queryAllRulesInPage(int pageCount, int pageSize)
	{
		String jpql = "select _rule from Middle.RuleAreaSpeed _rule";
		List<RuleAreaSpeed> rules = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
}
