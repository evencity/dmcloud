package com.apical.dmcloud.vehicle.middle.core.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.apical.dmcloud.middle.infra.AbstractVersionEntity;
import com.apical.dmcloud.middle.infra.Assert;
import com.apical.dmcloud.vehicle.middle.core.SIMCardNotExistException;

/**
 * sim卡信息表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.SIMCard")
@Table(name = "cl_sim")
public class SIMCard extends AbstractVersionEntity
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
		String jpql = "select _SIMCards from Middle.SIMCard _SIMCards where _SIMCards.number = :number";
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
    	String jpql = "select _SIMCards.id from Middle.SIMCard _SIMCards where _SIMCards.number = :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", number)
				.singleResult();
    	if(id == null)
    	{
    		throw new SIMCardNotExistException("SIMCard of number" + number + " not exist");
    	}
    	return id;
    }
	
	/**
	 * 通过设备id，来查询该设备装配的SIM卡信息
	 * @param deviceId 设备id
	 * @return SIM卡信息
	 */
	public static SIMCard getByDeviceId(long deviceId) throws SIMCardNotExistException
	{
		String jpql = "select _SIMCards from Middle.SIMCard _SIMCards where _SIMCards.device.id = :deviceId";
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
		String jpql = "select _simcards from Middle.SIMCard _simcards where _simcards.company.name = :name";
		
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
		String jpql = "select count(_SIMCards.id) from Middle.SIMCard _SIMCards join _SIMCards.company _company where _company.name = :name";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", companyName)
				.singleResult();
		return count;
	}
}
