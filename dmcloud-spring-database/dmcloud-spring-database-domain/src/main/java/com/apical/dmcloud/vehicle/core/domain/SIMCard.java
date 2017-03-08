package com.apical.dmcloud.vehicle.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.dayatang.domain.JpqlQuery;
import org.dayatang.domain.NamedParameters;
import org.dayatang.domain.QueryCriterion;
import org.dayatang.domain.QueryParameters;
import org.dayatang.utils.Assert;
import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.vehicle.core.SIMCardIsExistException;
import com.apical.dmcloud.vehicle.core.SIMCardNotExistException;

/**
 * sim卡信息表
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_sim")
public class SIMCard extends SecurityAbstractEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * SIM卡号
	 */
	@Column(name = "NUMBER")
	private String number;
	
	/**
	 * 启用状态
	 */
	@Column(name = "IS_USED")
	private Boolean isUsed;
	
	/**
	 * 安装情况
	 */
	@Column(name = "IS_INSTALL")
	private Boolean isInstalled;
	
	/**
	 * SIM所属的公司
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "COM_ID")
	private Company company;
	
	/**
	 * SIM卡装配的设备
	 */
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "DEVICE_ID")
	private Device device;
	
	protected SIMCard()
	{
	}
	
	public SIMCard(String number)
	{
		checkNumber(number);
		if(isExistSIMNumber(number))
		{
			throw new SIMCardIsExistException("SIMCard of number"+ number + " is exist");
		}
		
		this.number = number;
	}

	/**
	 * 获取SIM卡号
	 * @return SIM卡号
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * 设置SIM卡号
	 * @param number SIM卡号
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 获取启用状态
	 * @return 启用状态
	 */
	public Boolean getIsUsed() {
		return isUsed;
	}

	/**
	 * 设置启用状态
	 * @param isUsed 启用状态
	 */
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * 获取安装情况
	 * @return 安装情况
	 */
	public Boolean getIsInstalled() {
		return isInstalled;
	}

	/**
	 * 设置安装情况
	 * @param isInstalled 安装情况
	 */
	public void setIsInstalled(Boolean isInstalled) {
		this.isInstalled = isInstalled;
	}

	/**
	 * 获取所属公司
	 * @return 所属公司
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * 设置所属公司
	 * @param company 所属公司
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * 获取装配设备
	 * @return 装配设备
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * 设置装配设备
	 * @param device 装配设备
	 */
	public void setDevice(Device device) {
		this.device = device;
	}
	
	/**
	 * 使用断言方式检测设备编号是否为空
	 * @param number 设备编号
	 */
	private void checkNumber(String number) {
		Assert.notBlank(number, "number cannot be empty.");
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"SIMCard"};
	}
	
	/**
	 * 判断该SIM卡号码是否已被使用
	 * @param number SIM卡号码
	 * @return 是否已被使用
	 */
	public static boolean isExistSIMNumber(String number)
	{
		String jpql = "select _SIMCards from SIMCard _SIMCards where _SIMCards.number = :number";
		SIMCard simcard = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("number", number)
				.singleResult();
		if(simcard == null)
			return false;
		else
			return true;
	}
	
	/**
     * 通过SIM号码来获取SIM卡信息id
     * @param number SIM号码
     * @return SIM卡信息id
     */
    public static long getIdBySIMNumber(String number) throws SIMCardNotExistException
    {
    	String jpql = "select _SIMCards.id from SIMCard _SIMCards where _SIMCards.number = :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("number", number)
				.singleResult();
    	if(id == null)
    	{
    		throw new SIMCardNotExistException("SIMCard of number" + number + " not exist");
    	}
    	return id;
    }
	
    
	
	/**
     * 通过SIM号码来查询SIM卡信息
     * @param number SIM号码
     * @return SIM卡信息id
     */
    public static List<SIMCard> queryBySIMNumber(String number)
    {
    	String jpql = "select _SIMCards from SIMCard _SIMCards where _SIMCards.number like :number";
    	List<SIMCard> simCards = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("number", "%" + number +  "%")
				.list();
    	
    	return simCards;
    }
	/**
	 * 通过设备id，来查询该设备装配的SIM卡信息
	 * @param deviceId 设备id
	 * @return SIM卡信息
	 */
	public static SIMCard getByDeviceId(long deviceId) throws SIMCardNotExistException
	{
		String jpql = "select _SIMCards from SIMCard _SIMCards where _SIMCards.device.id = :deviceId";
		SIMCard sim = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.singleResult();
		if(sim == null)
    	{
    		throw new SIMCardNotExistException("Device  of id "+ deviceId + "not binding simcard");
    	}
		return sim;
	}
	
	/**
	 * 分页获取sim卡信息
	 * @param companyName 公司名称
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return sim卡信息
	 */
	public static List<SIMCard> findAllSIMCardsInPage(String companyName, int pageCount, int pageSize)
	{
		String jpql = "select _simcards from SIMCard _simcards where _simcards.company.name = :name";
		List<SIMCard> sims = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", companyName)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		return sims;
	}
	/**
	 * 根据公司名获取SIMCard 总数
	 * @param companyName
	 * @return SIMCard 总数
	 */
	public static long countAllSIMCardsByCompany(String companyName)
	{
		String jpql = "select count(_SIMCards.id) from SIMCard _SIMCards join _SIMCards.company _company where _company.name = :name";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", companyName)
				.singleResult();
		return count;
	}
	
	
	/**
	 * 统计符合条件的SIM卡数目
	 * @param companyId
	 * @param number
	 * @param isUsed
	 * @param isInstalled
	 * @return 数量
	 */
	public static long countAllByKeyWord(Long companyId, String number,Boolean isUsed, Boolean isInstalled){
		StringBuilder jpql = new StringBuilder("select count(_SIMCards.id) from SIMCard _SIMCards where");
		Map<String,Object> conditions = new HashMap<String,Object>();
		if(companyId != null){
			jpql.append(" _SIMCards.company.id = :companyId and");
			conditions.put("companyId", companyId);
		}
		if(!StringUtils.isBlank(number)){
			jpql.append(" _SIMCards.number like :number and");
			conditions.put("number", "%" +  number + "%");
		}
		if(isInstalled != null){
			jpql.append(" _SIMCards.isInstalled =:isInstalled and");
			conditions.put("isInstalled", isInstalled);
		}
		if( isUsed != null){
			jpql.append(" _SIMCards.isUsed =:isUsed and");
			conditions.put("isUsed", isUsed);
		}
		
		jpql.append(" 1 = 1");
		JpqlQuery query = getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions);
		Long count = query.singleResult();
		return count;
	}
	
	
	
	/**
	 * 获取符合条件的SIM卡信息
	 * @param companyId 公司ID
	 * @param number  SIM卡卡号
	 * @param isUsed  是否使用
	 * @param isInstalled  是否安装
	 * @param pageCount  页数大小
	 * @param pageSize   每页大小
	 * @return List<SIMCard> 
	 */
	public static List<SIMCard> queryAllByKeyWordInPage(Long companyId, String number,Boolean isUsed, Boolean isInstalled,int pageCount, int pageSize){
		StringBuilder jpql = new StringBuilder("select _SIMCards from SIMCard _SIMCards left join fetch _SIMCards.device left join fetch _SIMCards.company where");
		Map<String,Object> conditions = new HashMap<String,Object>();
		
		if(companyId != null){
			jpql.append(" _SIMCards.company.id = :companyId and");
			conditions.put("companyId", companyId);
		}
		if(!StringUtils.isBlank(number)){
			jpql.append(" _SIMCards.number like :number and");
			conditions.put("number", "%" +  number + "%");
		}
		if(isInstalled != null){
			jpql.append(" _SIMCards.isInstalled =:isInstalled and");
			conditions.put("isInstalled", isInstalled);
		}
		if( isUsed != null){
			jpql.append(" _SIMCards.isUsed =:isUsed and");
			conditions.put("isUsed", isUsed);
		}
		
		jpql.append(" 1 = 1");
		JpqlQuery query = getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions);
		List<SIMCard> simCards = query.list();
		return simCards;
	}
	

}
