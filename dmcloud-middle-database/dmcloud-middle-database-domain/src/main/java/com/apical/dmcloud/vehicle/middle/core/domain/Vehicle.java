package com.apical.dmcloud.vehicle.middle.core.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.apical.dmcloud.commons.infra.LicencePlate;
import com.apical.dmcloud.middle.infra.AbstractVersionEntity;
import com.apical.dmcloud.middle.infra.Assert;
import com.apical.dmcloud.vehicle.middle.core.VehicleNotExistException;

/**
 * 车辆信息。
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.Vehicle")
@Table(name = "cl_vehicle")
public class Vehicle extends AbstractVersionEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 车牌号
	 */
	@Column(name = "NUMBER")
	private String number;
	
	/**
	 * 车牌类型
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TYPE")
	private LicencePlate plate;
	
	/**
	 * 车辆品牌
	 */
	@Column(name = "BRAND")
	private String brand;
	
	/**
	 * 用途
	 */
	@Column(name = "PURPOSE")
	private String purpose;
	
	/**
	 * 颜色
	 */
	@Column(name = "COLOR")
	private String color;
	
	/**
	 * 车辆所属的公司
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "COM_ID")
	private Company company;
	
	
	

	protected Vehicle()
	{
	}
	
	public Vehicle(String number)
	{
		checkNumber(number);
		
		this.number = number;
	}

	/**
	 * 获取车牌号
	 * @return 车牌号
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * 设置车牌号
	 * @param number 车牌号
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 获取车牌类型
	 * @return 车牌类型
	 */
	public LicencePlate getPlate() {
		return plate;
	}

	/**
	 * 设置车牌类型
	 * @param plate 车牌类型
	 */
	public void setPlate(LicencePlate plate) {
		this.plate = plate;
	}

	/**
	 * 获取车辆品牌
	 * @return 车辆品牌
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * 设置车辆品牌
	 * @param brand 车辆品牌
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * 获取用途
	 * @return 用途
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * 设置用途
	 * @param purpose 用途
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * 获取车辆颜色
	 * @return 车辆颜色
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 设置车辆颜色
	 * @param color 车辆颜色
	 */
	public void setColor(String color) {
		this.color = color;
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
	 * 使用断言方式检测用户名是否为空
	 * @param number 用户名称
	 */
	private void checkNumber(String number) {
		Assert.notBlank(number, "number cannot be empty.");
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"Vehicle"};
	}
	
	/**
	 * 根据车辆id，来删除车辆信息
	 * @param vehicleId 车辆id
	 * @return 删除是否成功
	 */
	public static boolean deleteByVehicleId(long vehicleId)
	{
		String jpql = "delete from Middle.Vehicle _vehicle where _vehicle.id = :vehicleId";
		int count = getRepository().createJpqlQuery(jpql.toString())
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
	 * 判断该车牌号是否已经录入系统中
	 * @param number 车牌号
	 * @return 是否已经录入系统中
	 */
	public static boolean isExistVehicleNumber(String number)
	{
		String jpql = "select _vehicle from Middle.Vehicle _vehicle where _vehicle.number = :number";
		Vehicle vehicle = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("number", number)
				.singleResult();
		if(vehicle == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
     * 通过车牌id来获取车牌号
     * @param id 车牌id
     * @return 车牌号
     */
    public static String getVehicleNumberById(long id) throws VehicleNotExistException
    {
    	String jpql = "select _vehicle.number from Middle.Vehicle _vehicle where _vehicle.id= :id";
    	String number = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("id", id)
    			.singleResult();
    	if(number == null)
    	{
    		throw new VehicleNotExistException("Vehicle of id:"+ id + "not exist");
    	}
    	return number;
    }
	
	/**
     * 通过车牌号来获取车牌id
     * @param number 车牌号
     * @return 车牌id
     */
    public static long getIdByVehicleNumber(String number) throws VehicleNotExistException
    {
    	String jpql = "select _vehicle.id from Middle.Vehicle _vehicle where _vehicle.number= :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
    	if(id == null)
    	{
    		throw new VehicleNotExistException("Vehicle of number"+ number + "not exist");
    	}
    	return id;
    }
	
	/**
	 * 通过车辆id，来获取车辆信息
	 * @param vehicleId 车辆id
	 * @return 车辆信息
	 */
	public static Vehicle getByVehicleId(long vehicleId) throws VehicleNotExistException
	{
		String jpql = "select _vehicle from Middle.Vehicle _vehicle where _vehicle.id = :vehicleId";
		Vehicle vehicle = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.singleResult();
		if(vehicle == null)
		{
			throw new VehicleNotExistException("Vehicle with id "+ vehicleId + "not exist");
		}
		return vehicle;
	}
	
	/**
	 * 通过车牌号，来获取车辆信息
	 * @param number 车牌号
	 * @return 车辆信息
	 */
	public static Vehicle getByLicenceNumber(String number) throws VehicleNotExistException
	{
		String jpql ="select _vehicle from Middle.Vehicle _vehicle where _vehicle.number = :number";
		
		Vehicle vehicle = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("number", number)
				.singleResult();
		if(vehicle == null)
		{
			throw new VehicleNotExistException("Vehicle with number "+ number + "not exist");
		}
		return vehicle;
	}
	
	/**
	 * 查询司机驾驶的车辆信息
	 * @param driverId 司机id
	 * @return 车辆信息
	 */
	public static Vehicle getByDriverId(long driverId) throws VehicleNotExistException
	{
		String jpql = "select _vehicle from Middle.Vehicle _vehicle where id in"
				+ "(select VEHICLE_ID from cl_driver_vehicle_map where DRIVER_ID = :driverId";
		Vehicle vehicle = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("driverId", driverId)
				.singleResult();
		if(vehicle == null)
		{
			throw new VehicleNotExistException("Vehicle with driverId "+ driverId + "not exist");
		}
		return vehicle;
	}
	
	/**
	 * 分页获取车辆信息
	 * @param companyName 公司名称
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 车辆信息
	 */
	public static List<Vehicle> findAllVehiclesInPage(String companyName, int pageCount, int pageSize)
	{
		String jpql = "select _vehicle from Middle.Vehicle _vehicle where _vehicle.company.name = :name";
		List<Vehicle> vehicles = getRepository().createJpqlQuery(jpql)
				.addParameter("name", companyName)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		return vehicles;
	}
	
	/**
	 * 根据公司名获取车辆总数
	 * @param companyName
	 * @return 车辆总数
	 */
	public static long countAllVehiclesByCompany(String companyName)
	{
		String jpql = "select count(_vehicle.id) from Middle.Vehicle _vehicle where _vehicle.company.name = :name";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", companyName)
				.singleResult();
		return count;
	}
}
