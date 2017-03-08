package com.apical.dmcloud.rule.core.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.ehcache.constructs.nonstop.ThrowTimeoutException;

import org.dayatang.utils.Assert;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;
import com.apical.dmcloud.rule.core.NameIsExistedException;
import com.apical.dmcloud.rule.core.RuleAssignmentException;

/**
 * 定时拍照规则表
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_rule_takepicture")
public class RuleTakePicture extends KoalaAbstractEntity
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
	 * 开始时间1
	 */
	@Temporal(TemporalType.TIME)
	@Column(name = "BEGIN1")
	private Date beginTime1;
	
	/**
	 * 结束时间1
	 */
	@Temporal(TemporalType.TIME)
	@Column(name = "END1")
	private Date endTime1;
	
	/**
	 * 开始时间2
	 */
	@Temporal(TemporalType.TIME)
	@Column(name = "BEGIN2")
	private Date beginTime2;
	
	/**
	 * 结束时间2
	 */
	@Temporal(TemporalType.TIME)
	@Column(name = "END2")
	private Date endTime2;
	
	/**
	 * 拍照间隔
	 */
	@Column(name = "TAKEINTVL")
	private Short takeInterval;
	
	protected RuleTakePicture()
	{
	}
	
	public RuleTakePicture(String name)
	{
		checkName(name);
		if(isExistRuleName(name))
		{
			throw new NameIsExistedException("rule name is existed!");
		}
	
		this.name = name;
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
	 * 获取开始时间1
	 * @return 开始时间1
	 */
	public Date getBeginTime1() {
		return beginTime1;
	}

	/**
	 * 设置开始时间1
	 * @param beginTime1 开始时间1
	 */
	public void setBeginTime1(Date beginTime1) {
		this.beginTime1 = beginTime1;
	}

	/**
	 * 获取结束时间1
	 * @return 结束时间1
	 */
	public Date getEndTime1() {
		return endTime1;
	}

	/**
	 * 设置结束时间1
	 * @param endTime1 结束时间1
	 */
	public void setEndTime1(Date endTime1) {
		this.endTime1 = endTime1;
	}
	
	/**
	 * 获取开始时间2
	 * @return 开始时间2
	 */
	public Date getBeginTime2() {
		return beginTime2;
	}

	/**
	 * 设置开始时间2
	 * @param beginTime2 开始时间2
	 */
	public void setBeginTime2(Date beginTime2) {
		this.beginTime2 = beginTime2;
	}

	/**
	 * 获取结束时间2
	 * @return 结束时间2
	 */
	public Date getEndTime2() {
		return endTime2;
	}

	/**
	 * 设置结束时间2
	 * @param endTime2 结束时间2
	 */
	public void setEndTime2(Date endTime2) {
		this.endTime2 = endTime2;
	}

	/**
	 * 获取拍照间隔
	 * @return 拍照间隔
	 */
	public Short getTakeInterval() {
		return takeInterval;
	}

	/**
	 * 设置拍照间隔
	 * @param takeInterval 拍照间隔
	 */
	public void setTakeInterval(Short takeInterval) {
		this.takeInterval = takeInterval;
	}
	
	/**
	 * 使用断言方式检测规则名称是否为空
	 * @param name 规则名称
	 */
	private void checkName(String name) {
		Assert.notBlank(name, "name cannot be empty.");
	}
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"RuleTakePicture"};
	}
	
	/**
	 * 根据id来删除规则信息
	 * @param id 规则id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from RuleTakePicture _rule where _rule.id=:id";
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
		String jpql = "select 1 from RuleTakePicture _rule where _rule.name=:name";
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
	 * 车辆是否有分配定时拍照规则
	 * @param vehicleId 车辆id
	 * @return 是否已分配
	 */
	public static boolean isAssignedToVehicle(long vehicleId)
	{
		try
		{
			String sql = "select 1 from cl_rule_takepicture_vehicle_map where VEHICLE_ID=:vehicleId limit 1";
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
			String sql = "select 1 from cl_rule_takepicture_vehicle_map where RULE_ID=:ruleId and"
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
			throw new RuleAssignmentException("the RuleTakePicture:" + ruleId + " has been assigned to vehicle:" + vehicleId);
		}
		
		String sql = "insert into cl_rule_takepicture_vehicle_map (RULE_ID, VEHICLE_ID) values(:ruleId, :vehicleId)";
		int count = getRepository().createSqlQuery(sql)
				.addParameter("ruleId", ruleId)
				.addParameter("vehicleId", vehicleId)
				.executeUpdate();
		
		if(count == 1)
		{
			MiddleEntryCacheService.removeRuleAssigned2Vehicle(RuleTakePicture.class, vehicleId);
		
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
		
		
		String sql = "delete from cl_rule_takepicture_vehicle_map where RULE_ID =:ruleId and VEHICLE_ID =:vehicleId";
		int count = getRepository().createSqlQuery(sql)
				.addParameter("ruleId", ruleId)
				.addParameter("vehicleId", vehicleId)
				.executeUpdate();
		
		if(count == 1)
		{
			MiddleEntryCacheService.removeRuleAssigned2Vehicle(RuleTakePicture.class, vehicleId);
			
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 通过车辆id，来获取该车辆所分配的定时拍照规则信息
	 * @param vehicleId 车辆id
	 * @return 规则信息
	 */
	public static RuleTakePicture getByVehicleId(long vehicleId)
	{
		String sql = "select * from cl_rule_takepicture where id in"
				+ " (select RULE_ID from cl_rule_takepicture_vehicle_map where VEHICLE_ID=:vehicleId)";
		List<RuleTakePicture> rules = getRepository().createSqlQuery(sql)
				.addParameter("vehicleId", vehicleId)
				.setResultEntityClass(RuleTakePicture.class)
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
		String jpql = "select count(_rule.id) from RuleTakePicture _rule";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}
	
	/**
	 * 查询所有规则
	 * @return 规则信息
	 */
	public static List<RuleTakePicture> queryAllRules()
	{
		String jpql = "select _rule from RuleTakePicture _rule";
		List<RuleTakePicture> rules = getRepository().createJpqlQuery(jpql.toString())
				.list();
		
		return rules;
	}
	
	/**
	 * 查询所有规则
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 规则信息
	 */
	public static List<RuleTakePicture> queryAllRulesInPage(int pageCount, int pageSize)
	{
		String jpql = "select _rule from RuleTakePicture _rule";
		List<RuleTakePicture> rules = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return rules;
	}
	/**
	 * 查询所有已分配规则的车辆ID
	 * @param ruleId
	 * @return
	 */
	public static  List<Long> queryVehicleIds(long ruleId){
		String sql = "select VEHICLE_ID from cl_rule_takepicture_vehicle_map where RULE_ID =:ruleId";
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
		String sql = "select VEHICLE_ID from cl_rule_takepicture_vehicle_map where RULE_ID =:ruleId";
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
		String sql = "select count(VEHICLE_ID) from cl_rule_takepicture_vehicle_map where RULE_ID =:ruleId";
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
		
		return Rule.queryVehicleIds("cl_rule_takepicture_vehicle_map", ruleId, companyId);
	}
	
	
	/**
	 * 统计某公司已分配某规则的车辆数量
	 * @param ruleId
	 * @param companyId
	 * @return 数量
	 */
	public static long countAllVechilesById(long ruleId,long companyId){
	
		return Rule.countAllVechilesById("cl_rule_takepicture_vehicle_map", ruleId, companyId);
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
		
		return Rule.queryAssignedVehicleIdsInPage("cl_rule_takepicture_vehicle_map", ruleId, pageCount, pageSize, companyId);
	}

	
	public static  Map<Long,Boolean> deleteByIds(List<Long> ids){
		Map<Long,Boolean> results = new TreeMap<Long,Boolean>();
		for(Long id : ids){
			try{
				RuleTakePicture rule = 	RuleTakePicture.get(RuleTakePicture.class, id);
				if(rule != null){
					List<Long> vehicleIds = rule.queryVehicleIds(id);
					MiddleEntryCacheService.removeRulesAssigned2Vehicle(RuleTakePicture.class, vehicleIds);
					rule.remove();
					results.put(id, Boolean.TRUE);
				}else{
					results.put(id, Boolean.TRUE);
					
				}
				}catch(Throwable t){
					results.put(id, Boolean.FALSE);
				}
			
		}
		
		return results;
		
	}
}
