package com.apical.dmcloud.rule.core.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.dayatang.utils.Assert;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * 规则表
 * @author qiuzeng
 *
 */

@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Rule extends KoalaAbstractEntity
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
	 * 报警语音
	 */
	@Column(name = "VOICE")
	private String voice;
	
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
	
	protected Rule()
	{
	}
	
	public Rule(String name)
	{
		checkName(name);
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
	 * 获取报警语音
	 * @return 报警语音
	 */
	public String getVoice() {
		return voice;
	}

	/**
	 * 设置报警语音
	 * @param voice 报警语音
	 */
	public void setVoice(String voice) {
		this.voice = voice;
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
	
	/**
	 * 使用断言方式检测规则名称是否为空
	 * @param name 规则名称
	 */
	private void checkName(String name) {
		Assert.notBlank(name, "name cannot be empty.");
	}
	/**
	 * 获取分配规则的车辆Id
	 * @param ruleId
	 * @return  List<Long>
	 */
	//public  abstract  List<Long> queryVehicleIds(long ruleId);
	
	
	/**
	 * 根据表名，规则ID，公司ID 查询属于公司的已分配该规则的车辆ID列表
	 * @param rule_vehicle_map_table
	 * @param ruleId
	 * @param companyId
	 * @return 车辆ID列表
	 */
	public static  List<Long> queryVehicleIds(String rule_vehicle_map_table ,long ruleId,long companyId){
		//String sql = "select VEHICLE_ID from cl_rule_vibration_vehicle_map where RULE_ID =:ruleId";
		String sql = "select distinct _m.VEHICLE_ID from " + rule_vehicle_map_table + " as _m join cl_vehicle as _v on _m.VEHICLE_ID=_v.ID where _m.RULE_ID=:ruleId and _v.COM_ID=:companyId";
		List<BigInteger> vihicleIds = getRepository().createSqlQuery(sql).addParameter("ruleId", ruleId).addParameter("companyId", companyId).list();
		List<Long> temps = new ArrayList<Long>();
		
		for(BigInteger i : vihicleIds){
			temps.add(i.longValue());
		}
		return temps;
	}
	
	
	/**
	 * 查询属于公司的，已分配该规则的车辆数量
	 * @param rule_vehicle_map_table 表名
	 * @param ruleId 规则ID
	 * @param companyId 公司ID
	 * @return 已分配规则数量
	 */
	public static long countAllVechilesById(String rule_vehicle_map_table, long ruleId,long companyId){
		String sql = "select count(*) from " + rule_vehicle_map_table +  " as _m join  cl_vehicle as _v on _m.VEHICLE_ID=_v.ID  where _m.RULE_ID =:ruleId and _v.COM_ID=:companyId";
		BigInteger vihicleIds = getRepository().createSqlQuery(sql).addParameter("ruleId", ruleId).addParameter("companyId", companyId).singleResult();
		if(vihicleIds == null){
			return 0;
		}
		return vihicleIds.longValue();
	}
	
	/**
	 * 分页查询属于公司的，已分配该规则的车辆ID列表
	 * @param rule_vehicle_map_table
	 * @param ruleId
	 * @param pageCount
	 * @param pageSize
	 * @param companyId
	 * @return
	 */
	public static List<Long> queryAssignedVehicleIdsInPage(String rule_vehicle_map_table, long ruleId,int pageCount , int pageSize,long companyId){
		String sql = "select distinct _m.VEHICLE_ID from " +  rule_vehicle_map_table + " as _m join  cl_vehicle as _v on _m.VEHICLE_ID=_v.ID  where _m.RULE_ID =:ruleId and _v.COM_ID=:companyId";
		
		List<BigInteger> vihicleIds = getRepository().createSqlQuery(sql).addParameter("ruleId", ruleId).addParameter("companyId", companyId).setFirstResult(pageCount*pageSize - pageSize).setMaxResults(pageSize).list();
		List<Long> temps = new ArrayList<Long>();
		
		for(BigInteger i : vihicleIds){
			temps.add(i.longValue());
		}
		return temps;
	}
	
}
