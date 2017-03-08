package com.apical.dmcloud.vehicle.core.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.dayatang.utils.Assert;
import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.commons.infra.Gender;
import com.apical.dmcloud.vehicle.core.DriverIsExistException;
import com.apical.dmcloud.vehicle.core.DriverNotExistException;

/**
 * 司机信息表
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_driver")
public class Driver extends SecurityAbstractEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 工号
	 */
	@Column(name = "NUMBER")
	private String number;
	
	/**
	 * 司机名字
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 驾驶证号码
	 */
	@Column(name = "LICENSE")
	private String licenceNumber;
	
	/**
	 * 驾驶证办证日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TIME")
	private Date licenceDate;
	
	/**
	 * 驾驶证有效期限，单位为：月
	 */
	@Column(name = "TERM")
	private Short licenceTerm;
	
	/**
	 * 身份证号码
	 */
	@Column(name = "IDENTIFICATION")
	private String idCardNumber;
	
	/**
	 * 出生日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY")
	private Date birthday;
	
	/**
	 * 联系方式（电话或邮箱）
	 */
	@Column(name = "CONTACT")
	private String contact;
	
	/**
	 * 用户性别
	 * 0：未设置
	 * 1：男
	 * 2：女
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "GENDER")
	private Gender gender = Gender.Unknown;
	
	/**
	 * 车辆所属的公司
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "COM_ID")
	private Company company;
	
	/**
	 * 司机驾驶的车辆
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "cl_driver_vehicle_map",
			joinColumns = @JoinColumn(name = "DRIVER_ID", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "id"))
	private Vehicle vehicle;
	
	protected Driver()
	{
	}
	
	/**
	 * 构造函数
	 * @param name 司机姓名
	 * @param number 司机工号
	 * @param idNumber 司机身份证号码
	 */
	public Driver(String name, String number, String idNumber)
	{
		checkName(number);
		checkNumber(number);
		checkIdCardNumber(number);
		
		if(isExistDriverNumber(number))
		{
			throw new DriverIsExistException("Driver of number" + number + "is exist");
		}
		if(isExistDriverIdCardNumber(idNumber))
		{
			throw new DriverIsExistException("Driver of IdCardNumber" + idNumber + "is exist");
		}
		
		this.number = number;
	}

	/**
	 * 获取工号
	 * @return 工号
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * 设置工号
	 * @param number 工号
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 获取司机名称
	 * @return 司机名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置司机名称
	 * @param name 司机名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取驾驶证号码
	 * @return 驾驶证号码
	 */
	public String getLicenceNumber() {
		return licenceNumber;
	}

	/**
	 * 设置驾驶证号码
	 * @param licenceNumber 驾驶证号码
	 */
	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	/**
	 * 获取驾驶证办证日期
	 * @return 驾驶证办证日期
	 */
	public Date getLicenceDate() {
		return licenceDate;
	}

	/**
	 * 设置驾驶证办证日期
	 * @param licenceDate 驾驶证办证日期
	 */
	public void setLicenceDate(Date licenceDate) {
		this.licenceDate = licenceDate;
	}

	/**
	 * 获取驾驶证有效期限
	 * @return 驾驶证有效期限
	 */
	public Short getLicenceTerm() {
		return licenceTerm;
	}

	/**
	 * 设置驾驶证有效期限
	 * @param licenceTerm 驾驶证有效期限
	 */
	public void setLicenceTerm(Short licenceTerm) {
		this.licenceTerm = licenceTerm;
	}

	/**
	 * 获取身份证号
	 * @return 身份证号
	 */
	public String getIdCardNumber() {
		return idCardNumber;
	}

	/**
	 * 设置身份证号
	 * @param idCardNumber 身份证号
	 */
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	/**
	 * 获取出生日期
	 * @return 出生日期
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设置出生日期
	 * @param birthday 出生日期
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取联系方式
	 * @return 联系方式
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * 设置联系方式
	 * @param contact 联系方式
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * 获取性别
	 * @return 性别
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * 设置性别
	 * @param gender 性别
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
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
	 * 获取司机驾驶的车辆
	 * @return 车辆信息
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * 设置司机驾驶的车辆
	 * @param vehicle 车辆信息
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * 使用断言方式检测司机姓名是否为空
	 * @param name 司机姓名
	 */
	private void checkName(String name) {
		Assert.notBlank(name, "name cannot be empty.");
	}
	
	/**
	 * 使用断言方式检测司机工号是否为空
	 * @param number 司机工号
	 */
	private void checkNumber(String number) {
		Assert.notBlank(number, "number cannot be empty.");
	}
	
	/**
	 * 使用断言方式检测司机身份证号是否为空
	 * @param idCardNumber 身份证号
	 */
	private void checkIdCardNumber(String idCardNumber) {
		Assert.notBlank(idCardNumber, "idCardNumber cannot be empty.");
	}

	@Override
	public String[] businessKeys()
	{
		// TODO Auto-generated method stub
		return new String[]{"Driver"};
	}

	/**
	 * 判断该工号是否已被使用
	 * @param number 工号
	 * @return 工号是否已经使用
	 */
	public static boolean isExistDriverNumber(String number)
	{
		String jpql = "select _driver.id from Driver _driver where _driver.number= :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
    	if(id == null)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
	}
	
	/**
	 * 判断驾驶证号是否存在
	 * @param licenceNumber
	 * @return boolean
	 */
	public static boolean isExistLicenceNumber(String licenceNumber){
		String jpql = "select _driver.id from Driver _driver where _driver.licenceNumber= :licenceNumber";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("licenceNumber", licenceNumber)
    			.singleResult();
    	if(id == null)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
	}
	
	/**
	 * 判断该身份证号码是否已录入系统
	 * @param number 身份证号码
	 * @return 身份证号码是否已录入系统
	 */
	public static boolean isExistDriverIdCardNumber(String number)
	{
		String jpql = "select _driver.id from Driver _driver where _driver.idCardNumber= :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
    	if(id == null)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
	}
	
	/**
     * 通过司机工号，来获取司机信息id
     * @param number 司机工号
     * @return 司机信息id
     */
    public static long getIdByDriverNumber(String number) throws DriverNotExistException
    {
    	String jpql = "select _driver.id from Driver _driver where _driver.number= :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
    	if(id == null)
    	{
    		throw new DriverNotExistException("driver of number " + number + "not exits");
    	}
    	return id;
    }
    
    /**
     * 通过司机身份证号码，来获取司机信息id
     * @param number 身份证号码
     * @return 司机信息id
     */
    public static long getIdByDriverIdCardNumber(String number) throws DriverNotExistException
    {
    	String jpql = "select _driver.id from Driver _driver where _driver.idcardnumber= :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
    	if(id == null)
    	{
    		throw new DriverNotExistException("driver of IdCardNumber " + number + "not exits");
    	}
    	return id;
    }
	
	/**
	 * 通过司机工号，来获取司机信息
	 * @param number 司机工号
	 * @return 司机信息
	 */
	public static Driver getByDriverNumber(String number) throws DriverNotExistException
	{

		String jpql = "select _driver from Driver _driver where _driver.number= :number";
		Driver driver = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
		if(driver == null)
    	{
    		throw new DriverNotExistException("driver of number " + number + "not exits");
    	}
		return driver;
	}
	
	/**
	 * 通过司机身份证号码，来获取司机信息
	 * @param number 身份证号码
	 * @return 司机信息
	 */
	public static Driver getByDriverIdCardNumber(String number) throws DriverNotExistException
	{
		String jpql = "select _driver from Driver _driver where _driver.idCardNumber= :number";
		Driver driver = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
		if(driver == null)
    	{
    		throw new DriverNotExistException("driver of number " + number + "not exits");
    	}
		return driver;
	}
	
	/**
	 * 通过车辆id，来查询驾驶该车辆的司机信息
	 * @param vehicleId 车辆id
	 * @return 司机信息
	 */
	public static List<Driver> getByVehicleId(long vehicleId)
	{
		String jpql = "select _driver from Driver _driver where _driver.vehicle.id= :vehicleid";
		List<Driver> drivers = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("vehicleid", vehicleId)
    			.list();
		if(drivers != null)
		{
			return drivers;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 分页查询司机信息
	 * @param companyName 公司名称
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 司机信息
	 */
	public static List<Driver> findAllDriversInPage(String companyName, int pageCount, int pageSize)
	{
		String jpql = "select _driver from Driver _driver where _driver.company.name = :name";
		List<Driver> drivers = getRepository().createJpqlQuery(jpql)
				.addParameter("name", companyName)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		return drivers;
	}
	/**
	 * 根据公司名获取司机总数
	 * @param companyName
	 * @return 设备总数
	 */
	public static long countAllDriversByCompany(String companyName)
	{
		String jpql = "select count(_driver.id) from Driver _driver where _driver.company.name = :name";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", companyName)
				.singleResult();
		return count;
	}
	
	/**
	 * 通过关键字分页查询司机信息
	 * @param companyName 公司名
	 * @param number 工号
	 * @param name  名字
	 * @param pageCount 页数
	 * @param pageSize  条数
	 * @return   List<Driver>
	 */
	public static List<Driver> queryAllDriverByKeyWordsInPage(String companyName, String number,String name, int pageCount ,int pageSize)
	{
		StringBuilder jpql = new StringBuilder("select _driver from Driver _driver left join fetch _driver.vehicle left join fetch _driver.company  where");
		Map<String,Object> conditions = new HashMap<String,Object>();
		
		if(!StringUtils.isBlank(companyName)){
			jpql.append(" _driver.company.name = :companyName and");
			conditions.put("companyName", companyName);
				
		}
		if(!StringUtils.isBlank(number)){
			jpql.append(" _driver.number like :number and");
			conditions.put("number", "%" + number + "%");
					
		}
		if(!StringUtils.isBlank(name)){
			jpql.append(" _driver.name like :name and");
			conditions.put("name", "%" + name + "%");
		}
		
		jpql.append(" 1 = 1");
		List<Driver> drivers = getRepository().createJpqlQuery(jpql.toString())
				.setParameters(conditions).setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize).list();
		if(drivers != null)
		{
			return drivers;
		}
		else
		{
			return null;
		}
	}
	
	
	/**
	 * 统计关键字分页查找的司机信息总数
	 * @param companyName 公司名
	 * @param number 工号
	 * @param name  名字
	 * @return   List<Driver>
	 */
	public static long countAllDriverByKeyWords(String companyName,
			String number, String name){
		StringBuilder jpql = new StringBuilder("select count(_driver.id) from Driver _driver where");
		Map<String,Object> conditions = new HashMap<String,Object>();
		
		if(!StringUtils.isBlank(companyName)){
			jpql.append(" _driver.company.name = :companyName and");
			conditions.put("companyName", companyName);
				
		}
		if(!StringUtils.isBlank(number)){
			jpql.append(" _driver.number like :number and");
			conditions.put("number", "%"+ number + "%");
					
		}
		if(!StringUtils.isBlank(name)){
			jpql.append(" _driver.name like :name and");
			conditions.put("name", "%"+ name + "%");
		}
		
		jpql.append(" 1 = 1");
		Long count = getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions).singleResult();
		return count;
	}
}
