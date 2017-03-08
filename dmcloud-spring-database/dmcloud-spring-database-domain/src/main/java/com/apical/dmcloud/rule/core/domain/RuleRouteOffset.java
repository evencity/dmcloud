package com.apical.dmcloud.rule.core.domain;

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.apical.dmcloud.rule.core.NameIsExistedException;
import com.apical.dmcloud.rule.core.RuleAssignmentException;

/**
 * 路线偏移规则表
 * @author qiuzeng
 *
 */

@Entity
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
	 * 偏移宽度
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
		super(name);
		
		if(isExistRuleName(name))
		{
			throw new NameIsExistedException("rule name is existed!");
		}
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
	 * 判断该规则名称是否已被使用
	 * @param name 规则名称
	 * @return 是否已被使用
	 */
	public static boolean isExistRuleName(String name)
	{
		String jpql = "select 1 from RuleRouteOffset _rule where _rule.name=:name";
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
		String jpql = "delete from RuleRouteOffset _rule where _rule.id=:id";
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
		if(isAssignedToVehicle(vehicleId,vehicleId))
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
			MiddleEntryCacheService.removeRuleAssigned2Vehicle(RuleRouteOffset.class, vehicleId);
		
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
			throw new RuleAssignmentException("the RuleAreaSpeed:" + ruleId + " had not been assigned to vehicle:" + vehicleId);
		}
		
		String sql = "delete from cl_rule_route_vehicle_map where RULE_ID =:ruleId and VEHICLE_ID =:vehicleId";
		int count = getRepository().createSqlQuery(sql)
				.addParameter("ruleId", ruleId)
				.addParameter("vehicleId", vehicleId)
				.executeUpdate();
		
		if(count == 1)
		{
			MiddleEntryCacheService.removeRuleAssigned2Vehicle(RuleRouteOffset.class, vehicleId);
			
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
		String sql = "select * from cl_rule_route where id in"
				+ " (select RULE_ID from cl_rule_route_vehicle_map where VEHICLE_ID=:vehicleId)";
		List<RuleRouteOffset> rules = getRepository().createSqlQuery(sql)
				.addParameter("vehicleId", vehicleId)
				.setResultEntityClass(RuleRouteOffset.class)
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
		String jpql = "select count(_rule.id) from RuleRouteOffset _rule";
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
		String jpql = "select _rule from RuleRouteOffset _rule";
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
		String jpql = "select _rule from RuleRouteOffset _rule";
		List<RuleRouteOffset> rules = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
	
	/**
	 * 查询已分配规则的车辆ID
	 */
	public static  List<Long> queryVehicleIds(long ruleId){
		String sql = "select VEHICLE_ID from cl_rule_route_vehicle_map where RULE_ID =:ruleId";
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
		
		String sql = "select VEHICLE_ID from cl_rule_route_vehicle_map where RULE_ID =:ruleId";
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
		String sql = "select count(VEHICLE_ID) from cl_rule_route_vehicle_map where RULE_ID =:ruleId";
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
		
		return Rule.queryVehicleIds("cl_rule_route_vehicle_map", ruleId, companyId);
	}
	
	
	/**
	 * 统计某公司已分配某规则的车辆数量
	 * @param ruleId
	 * @param companyId
	 * @return 数量
	 */
	public static long countAllVechilesById(long ruleId,long companyId){
	
		return Rule.countAllVechilesById("cl_rule_route_vehicle_map", ruleId, companyId);
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
		
		return Rule.queryAssignedVehicleIdsInPage("cl_rule_route_vehicle_map", ruleId, pageCount, pageSize, companyId);
	}
	

}
