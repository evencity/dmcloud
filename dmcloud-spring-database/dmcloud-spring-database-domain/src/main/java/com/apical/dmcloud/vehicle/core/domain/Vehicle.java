package com.apical.dmcloud.vehicle.core.domain;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.dayatang.domain.JpqlQuery;
import org.dayatang.utils.Assert;
import org.hibernate.annotations.Cascade;
import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.commons.infra.LicencePlate;
import com.apical.dmcloud.vehicle.core.VehicleIsExistException;
import com.apical.dmcloud.vehicle.core.VehicleNotExistException;

/**
 * 车辆信息。
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_vehicle")
public class Vehicle extends SecurityAbstractEntity
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
	
	/**
	 * 绑定的设备
	 */
	@OneToOne(targetEntity=Device.class,fetch = FetchType.LAZY,cascade = CascadeType.REFRESH,mappedBy="vehicle")
	private Device devices;
	


	public Device getDevices() {
		return devices;
	}

	public void setDevices(Device devices) {
		this.devices = devices;
	}

	protected Vehicle()
	{
	}
	
	public Vehicle(String number)
	{
		checkNumber(number);
		if(isExistVehicleNumber(number))
		{
			throw new VehicleIsExistException("Vehicle of number "+number+ "is exist");
		}
		
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
		String jpql = "delete from Vehicle _vehicle where _vehicle.id = :vehicleId";
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
		String jpql = "select _vehicle from Vehicle _vehicle where _vehicle.number = :number";
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
     * 通过车牌号来获取车牌id
     * @param number 车牌号
     * @return 车牌id
     */
    public static long getIdByVehicleNumber(String number) throws VehicleNotExistException
    {
    	String jpql = "select _vehicle.id from Vehicle _vehicle where _vehicle.number= :number";
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
		String jpql = "select _vehicle from Vehicle _vehicle where _vehicle.id = :vehicleId";
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
		String jpql ="select _vehicle from Vehicle _vehicle where _vehicle.number = :number";
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
		String sql = "select VEHICLE_ID from cl_driver_vehicle_map where DRIVER_ID = :driverId";
		try{
		
			BigInteger vecicleId = getRepository().createSqlQuery(sql.toString()).addParameter("driverId", driverId).singleResult();
			if(vecicleId == null){
				throw new VehicleNotExistException("Vehicle with driverId "+ driverId + "not exist");
			}

			String jpql = "select _vehicle from Vehicle _vehicle where id in :vecicleIds";
					
			Vehicle vehicle = getRepository().createJpqlQuery(jpql.toString()).addParameter("vecicleIds", vecicleId.longValue())
					.singleResult();
			if(vehicle == null)
			{
				throw new VehicleNotExistException("Vehicle with driverId "+ driverId + "not exist");
			}
			return vehicle;
		}catch(NoResultException e){
			return null;
		}
	
	
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
		String jpql = "select _vehicle from Vehicle _vehicle where _vehicle.company.name = :name";
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
		String jpql = "select count(_vehicle.id) from Vehicle _vehicle where _vehicle.company.name = :name";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", companyName)
				.singleResult();
		return count;
	}
	
	/**
	 * 统计符合条件的车辆信息数目
	 * @param companyId 公司Id
	 * @param number 车牌号
	 * @param color 颜色
	 * @param plate 车牌类型
	 * @param brand 车辆品牌
	 * @return 数量
	 */
	public static long countAllByKeyWord(Long companyId, String number, String color,LicencePlate plate, String brand){
		StringBuilder jpql = new StringBuilder("select count(_vehicle.id) from Vehicle _vehicle where");
		Map<String,Object> conditions = new HashMap<String,Object>();
		
		if(companyId != null){
			jpql.append(" _vehicle.company.id = :companyId and");
			conditions.put("companyId", companyId);
		}
		if(!StringUtils.isBlank(number)){
			jpql.append(" _vehicle.number like :number and");
			conditions.put("number", "%" + number + "%");
			
		}
		if(!StringUtils.isBlank(color)){
			jpql.append(" _vehicle.color like :color and");
			conditions.put("color", "%" + color +"%");
		}

		if(plate == null || plate == LicencePlate.Unknown ){
			
		}else{
			jpql.append(" _vehicle.plate = :plate and");
			conditions.put("plate", plate);
		}
		if(brand != null){
			jpql.append(" _vehicle.brand like :brand and");
			conditions.put("brand", "%" + brand + "%");
		
		}
		jpql.append(" 1=1");
		Long count = getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions).singleResult();
		return count;
		
	}
	
	
	/**
	 * 获取符合条件的车辆信息
	 * @param companyId 公司Id 可选
	 * @param number 车牌号
	 * @param color 颜色
	 * @param plate 车牌类型
	 * @param brand 车辆品牌
	 * @return 数量
	 */
	public static List<Vehicle> queryAllByKeyWordInPage(Long companyId, String number, String color,LicencePlate plate, String brand,int pageCount , int pageSize){
	StringBuilder jpql = new StringBuilder("select _vehicle from Vehicle _vehicle  left join fetch _vehicle.company left join fetch _vehicle.devices where");
		
	
	Map<String,Object> conditions = new HashMap<String,Object>();
		if(companyId != null){
			jpql.append(" _vehicle.company.id = :companyId  and");
			conditions.put("companyId", companyId);
		}
		
		if(!StringUtils.isBlank(number)){
			jpql.append(" _vehicle.number like :number and");
			conditions.put("number", "%" + number + "%");
			
		}
		if(!StringUtils.isBlank(color)){
			jpql.append(" _vehicle.color like :color and");
			conditions.put("color", "%" + color +"%");
		}
	
		if(plate == null || plate == LicencePlate.Unknown ){
			
		}else{
			jpql.append(" _vehicle.plate = :plate  and");
			conditions.put("plate", plate);
		}
		if(brand != null){
			jpql.append(" _vehicle.brand like :brand and");
			conditions.put("brand", "%" + brand + "%");
		}
		
		jpql.append(" 1 = 1");
		jpql.append(" order by _vehicle.id");
		
		List<Vehicle> vehiches = getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions).setFirstResult(pageSize*(pageCount -1)).setMaxResults(pageSize).list();
		return vehiches;
		
	}
	
	/**
	 * 查询所有车辆数量
	 * @return
	 */
	public static long countAll(){
		String jpql = "select count(_vehicle.id) from Vehicle _vehicle";
		Long vehicles = getRepository().createJpqlQuery(jpql).singleResult();
		if(vehicles == null) return 0;
		return vehicles;
	}
	
	
	/**
	 * 根据公司Id获取车辆总数
	 * @param companyName
	 * @return 车辆总数
	 */
	public static long countAllVehiclesByCompanyId(long companyId)
	{
		String jpql = "select count(_vehicle.id) from Vehicle _vehicle where _vehicle.company.id = :companyId";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("companyId", companyId)
				.singleResult();
		return count;
	}
	
	
	/**
	 * 根据ID集合批量查询车辆
	 * @param ids
	 * @return 车辆
	 */
	public static List<Vehicle> findByIds(List<Long> ids){
		String jpql = "select _vehicle from Vehicle _vehicle where _vehicle.id in (:ids)";
		List<Vehicle> vehicles = getRepository().createJpqlQuery(jpql)
				.addParameter("ids", ids)
				.list();
		return vehicles;
	}
	
	
	/**
	 * 根据ID集合批量查询车辆
	 * @param ids
	 * @return 车辆
	 */
	public static List<Vehicle> findExcludeIds(List<Long> ids,int pageCount, int pageSize){
		StringBuilder jpql = new StringBuilder("select _vehicle from Vehicle _vehicle");
		if(ids != null && ids.size() > 0 ){
			jpql.append(" where _vehicle.id not in (:ids)");
		}
		JpqlQuery query  = getRepository().createJpqlQuery(jpql.toString());
		if(ids !=null && ids.size() > 0){
		    query.addParameter("ids", ids);
		}
		
		List<Vehicle> vehicles = query.setFirstResult(pageSize*pageCount - pageSize).setMaxResults(pageSize)
				.list();
		return vehicles;
	}
	
	
	/**
	 * 根据排除的车辆ID，和所属公司集合批量查询车辆
	 * @param ids
	 * @param companyId 公司ID
	 * @return 车辆
	 */
	public static List<Vehicle> findExcludeIds(List<Long> ids,int pageCount, int pageSize,long companyId){
		StringBuilder jpql = new StringBuilder("select _vehicle from Vehicle _vehicle join _vehicle.company where _vehicle.company.id =:companyId");
		if(ids != null && ids.size() > 0 ){
			jpql.append(" and _vehicle.id not in (:ids)");
		}
		JpqlQuery query  = getRepository().createJpqlQuery(jpql.toString());
		query.addParameter("companyId", companyId);
		if(ids !=null && ids.size() > 0){
		    query.addParameter("ids", ids);
		}
		
		List<Vehicle> vehicles = query.setFirstResult(pageSize*pageCount - pageSize).setMaxResults(pageSize)
				.list();
		return vehicles;
	}

}
