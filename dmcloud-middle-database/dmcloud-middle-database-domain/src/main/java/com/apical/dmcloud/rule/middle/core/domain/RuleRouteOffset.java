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
 * 路线偏移规则表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.RuleRouteOffset")
@Table(name = "cl_rule_route")
public class RuleRouteOffset extends Rule
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 路线
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "ROUTE_ID")
	private DrivingRoute route;
	
	/**
	 * 偏移宽度(单位为米)
	 */
	@Column(name = "WIDTH")
	private Short width;
	
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
	
	protected RuleRouteOffset()
	{
	}
	
	public RuleRouteOffset(String name)
	{
	}

	/**
	 * 获取路线
	 * @return 路线
	 */
	public DrivingRoute getRoute() {
		return route;
	}

	/**
	 * 设置路线
	 * @param route 路线
	 */
	public void setRoute(DrivingRoute route) {
		this.route = route;
	}

	/**
	 * 获取偏移宽度
	 * @return 偏移宽度
	 */
	public Short getWidth() {
		return width;
	}

	/**
	 * 设置偏移宽度
	 * @param width 偏移宽度
	 */
	public void setWidth(Short width) {
		this.width = width;
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
		return new String[]{"RuleRouteOffset"};
	}
	
	/**
	 * 根据id来删除规则信息
	 * @param id 规则id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.RuleRouteOffset _rule where _rule.id=:id";
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
	 * 车辆是否有分配路线偏移规则
	 * @param vehicleId 车辆id
	 * @return 是否已分配
	 */
	public static boolean isAssignedToVehicle(long vehicleId)
	{
		try
		{
			String sql = "select 1 from cl_rule_route_vehicle_map where VEHICLE_ID=:vehicleId limit 1";
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
			String sql = "select 1 from cl_rule_route_vehicle_map where RULE_ID=:ruleId and"
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
			throw new RuleAssignmentException("the RuleRouteOffset:" + ruleId + " has been assigned to vehicle:" + vehicleId);
		}
		
		String sql = "insert into cl_rule_route_vehicle_map (RULE_ID, VEHICLE_ID) values(:ruleId, :vehicleId)";
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
	 * 通过车辆id，来获取该车辆所分配的路线偏移规则信息
	 * @param vehicleId 车辆id
	 * @return 规则信息
	 */
	public static RuleRouteOffset getByVehicleId(long vehicleId)
	{
		List<RuleRouteOffset> rules = MiddleEntryCacheService.getRuleAssigned2Vehicle(RuleRouteOffset.class, vehicleId);
;
		//boolean isInCache = MiddleEntryCacheService.isKeyInRuleAssigned2VehicleCache(RuleRouteOffset.class, vehicleId);
		if(rules == null){
			String sql = "select * from cl_rule_route where id in"
					+ " (select RULE_ID from cl_rule_route_vehicle_map where VEHICLE_ID=:vehicleId)";
			rules = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.setResultEntityClass(RuleRouteOffset.class)
					.list();
			if(rules != null){
				 MiddleEntryCacheService.saveRuleAssigned2Vehicle(RuleRouteOffset.class, vehicleId, rules);

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
		String jpql = "select count(_rule.id) from Middle.RuleRouteOffset _rule";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}
	
	/**
	 * 查询所有规则
	 * @return 规则信息
	 */
	public static List<RuleRouteOffset> queryAllRules()
	{
		String jpql = "select _rule from Middle.RuleRouteOffset _rule";
		List<RuleRouteOffset> rules = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return rules;
	}
	
	/**
	 * 查询所有规则
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 规则信息
	 */
	public static List<RuleRouteOffset> queryAllRulesInPage(int pageCount, int pageSize)
	{
		String jpql = "select _rule from Middle.RuleRouteOffset _rule";
		List<RuleRouteOffset> rules = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
}
