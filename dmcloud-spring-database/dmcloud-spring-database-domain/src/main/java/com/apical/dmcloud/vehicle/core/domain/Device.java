package com.apical.dmcloud.vehicle.core.domain;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.dayatang.utils.Assert;
import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.vehicle.core.DeviceIsExistException;
import com.apical.dmcloud.vehicle.core.DeviceNotExistException;
import com.apical.dmcloud.vehicle.core.VehicleNotExistException;

/**
 * 设备信息表
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_device")
public class Device extends SecurityAbstractEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 设备编号
	 */
	@Column(name = "NUMBER")
	private String number;
	
	/**
	 * 设备类型
	 */
	@Column(name = "TYPE")
	private String type;
	
	/**
	 * 品牌
	 */
	@Column(name = "BRAND")
	private String brand;
	
	/**
	 * 设备型号
	 */
	@Column(name = "MODEL")
	private String model;
	
	/**
	 * 服务开始时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TIME")
	private Date serverTime;
	
	/**
	 * 服务有效期限
	 */
	@Column(name = "TERM")
	private Integer term;
	
	/**
	 * 安装时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INSTALL")
	private Date installTime;
	
	/**
	 * 是否已安装
	 */
	@Column(name = "IS_INSTALL")
	private Boolean isInstalled;
	
	/**
	 * 磁盘数量
	 */
	@Column(name = "DISK")
	private Integer diskCount;
	
	/**
	 * 摄像头数量
	 */
	@Column(name = "CAMERA")
	private Integer cameraCount;
	
	/**
	 * 设备的固件版本号
	 */
	@Column(name = "FIRMWARE")
	private String firmwareVersion;
	
	/**
	 * 设备所属的公司
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "COM_ID")
	private Company company;

	@JoinColumn(name = "COM_ID", updatable=false, insertable=false)
	private Long companyId;
	
	/**
	 * 设备所属的车辆
	 */
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "VEHICLE_ID")
	private Vehicle vehicle;
	
	
	/**p2p uuid**/
	@Column(name = "P2PUUID",length=64,nullable=true,unique=true)
	private String P2PUUID;
	
	/**获取P2pUUId **/
	public String getP2PUUID() {
		return P2PUUID;
	}

	/**
	 * 设置p2pUUID
	 * @param p2puuid p2pUUid
	 */
	public void setP2PUUID(String p2puuid) {
		P2PUUID = p2puuid;
	}

	protected Device() {
	}
	
	public Device(String number) {
		checkNumber(number);
		if(isExistDeviceNumber(number)) {
			throw new DeviceIsExistException("Device is exist with number "+ number);
		}
		
		this.number = number;
	}

	@Override
	public void save(){
		Vehicle vehicle = this.getVehicle();
		if(vehicle != null){
			//更新缓存信息
			//MiddleEntryCacheService.saveVehicle_Binded_Device(this.getId(), vehicle.getId());
		}
		super.save();
		
	}
	/**
	 * 获取设备编号
	 * @return 设备编号
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * 设置设备编号
	 * @param number 设备编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 获取设备类型
	 * @return 设备类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置设备类型
	 * @param type 设备类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取产品品牌
	 * @return 产品品牌
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * 设置产品品牌
	 * @param brand 产品品牌
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * 获取产品型号
	 * @return 产品型号
	 */
	public String getModel() {
		return model;
	}

	/**
	 * 设置产品型号
	 * @param model 产品型号
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 获取服务开始日期
	 * @return 服务开始日期
	 */
	public Date getServerTime() {
		return serverTime;
	}

	/**
	 * 设置服务开始日期
	 * @param serverTime 服务开始日期
	 */
	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}

	/**
	 * 获取服务有效期限
	 * @return 服务有效期限
	 */
	public Integer getTerm() {
		return term;
	}

	/**
	 * 设置服务有效期限
	 * @param term 服务有效期限
	 */
	public void setTerm(Integer term) {
		this.term = term;
	}

	/**
	 * 获取安装日期
	 * @return 安装日期
	 */
	public Date getInstallTime() {
		return installTime;
	}

	/**
	 * 设置安装日期
	 * @param installTime 安装日期
	 */
	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
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
	 * 获取磁盘数量
	 * @return 磁盘数量
	 */
	public Integer getDiskCount() {
		return diskCount;
	}

	/**
	 * 设置磁盘数量
	 * @param diskCount 磁盘数量
	 */
	public void setDiskCount(Integer diskCount) {
		this.diskCount = diskCount;
	}

	/**
	 * 获取摄像头数量
	 * @return 摄像头数量
	 */
	public Integer getCameraCount() {
		return cameraCount;
	}

	/**
	 * 设置摄像头数量
	 * @param cameraCount 摄像头数量
	 */
	public void setCameraCount(Integer cameraCount) {
		this.cameraCount = cameraCount;
	}

	/**
	 * 固件版本号
	 * @return 固件版本号
	 */
	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	/**
	 * 固件版本号
	 * @param firmwareVersion 固件版本号
	 */
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
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
	 * 获取装配车辆
	 * @return 装配车辆
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * 设置装配车辆
	 * @param vehicle 装配车辆
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
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
		return new String[]{"Device"};
	}
	
	/**
	 * 判断该设备编号是否已被使用
	 * @param number 设备编号
	 * @return 是否已被使用
	 */
	public static boolean isExistDeviceNumber(String number) {
		String jpql = "select _device.id from Device _device where _device.number= :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
    	if(id == null) {
    		return false;
    	}
    	else
    	{
    		return true;
    	}
	}
	
	 /**
     * 通过设备编号来获取设备id
     * @param number 设备编号
     * @return 设备id
     */
    public static long getIdByDeviceNumber(String number) throws DeviceNotExistException
    {
    	String jpql = "select _device.id from Device _device where _device.number= :number";
    	Long id = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("number", number)
    			.singleResult();
    	if(id == null) {
    		throw new DeviceNotExistException("Device not exit with number" + number);
    	}
    	return id;
    }
	
	/**
	 * 通过车辆id，来查询该车辆装配的设备信息
	 * @param vehicleId 车辆id
	 * @return 设备信息
	 */
	public static List<Device> getByVehicleId(Long vehicleId) {
		//String jpql = "select _device from Device _device where _device.vehicle.id = :id";
		String jpql = "select _device from Device _device where _device.id = :id";
		List<Device> devices = getRepository().createJpqlQuery(jpql.toString())
							.addParameter("id", vehicleId)
							.list();
		if(devices != null) {
			return devices;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 通过SIM卡id，来查询该SIM卡所装配的设备信息
	 * @param simId SIM卡id
	 * @return 设备信息
	 */
	public static Device getBySIMId(long simId) throws DeviceNotExistException
	{
		
		String jpql = "select _device from Device _device where _device.id in"
				+ "(select _SIMCards.device.id from SIMCard  _SIMCards where _SIMCards.id = :simId)";
		Device device = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("simId", simId)
				.singleResult();
		if(device == null) {
			throw new DeviceNotExistException("Device of simId "+ simId + "not exist");
		}
		return device;
	}

	/**
	 * 分页获取设备信息
	 * @param companyName 公司名称
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 设备信息
	 */
	public static List<Device> findAllDevicesInPage(String companyName, int pageCount, int pageSize) {
		String jpql = "select _device from Device _device where _device.company.name = :name";
		List<Device> devices =getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", companyName)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		if(devices != null) {
			return devices;
		}
		else
		{
			return null;
		}
	}
	/**
	 * 根据公司名获取设备总数
	 * @param companyName
	 * @return 设备总数
	 */
	public static long countAllDevicesByCompany(String companyName) {
	
		String jpql = "select count(_device.id) from Device _device where _device.company.name = :name";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", companyName)
				.singleResult();
		return count;
	}
	/**
	 * 通过dvr编号查找出所属车辆的车牌号
	 * @param number dvr编号
	 * @return
	 */
	public static String getVehicleNumberByDeviceNumber(String number) throws VehicleNotExistException
	{
		String jpql = "select _device.vehicle.number from Device _device where _device.number = :number";
		String vehicleNumber = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("number", number)
				.singleResult();
		if(vehicleNumber == null) {
			throw new VehicleNotExistException("Device of number"+ number +" no't banding vehicle");
		}
		else
		{
			return vehicleNumber;
		}
	}
	/**
	 * 通过dvr编号获取车辆id
	 * @param number
	 * @return
	 */
	public static long  getVehicleIdByDeviceNumber(String number) throws VehicleNotExistException
	{
		String jpql = "select _device.vehicle.id from Device _device where _device.number = :number";
		Long id = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("number", number)
				.singleResult();
		if(id == null) {
			throw new VehicleNotExistException("Device of number"+ number +" no't banding vehicle");
		}
		else
		{
			return id;
		}
	}
	
	/**
	 * 通过关键字分页查找设备
	 * @param companyName 公司名
	 * @param number 设备编号
	 * @param type  类型
	 * @param brand  品牌
	 * @param model  型号
	 * @param pageCount 
	 * @param pageSize
	 * @return  List<Device>
	 */
	public static List<Device> queryAllDevicesInByKeyWordsInPage(String companyName, String number, String type,String brand,String model,int pageCount, int pageSize) {
		StringBuilder jpql = new StringBuilder("select _device from Device _device left join fetch _device.vehicle left join fetch _device.company  where");
		Map<String,Object> conditions = new HashMap<String,Object>();			
		if(!StringUtils.isBlank(companyName)){
			jpql.append(" _device.company.name = :companyName and");
			conditions.put("companyName", companyName);
		}
		
		if(!StringUtils.isBlank(number)){
			jpql.append(" _device.number like :number and");
			conditions.put("number","%" + number + "%");
		}
		
		if(!StringUtils.isBlank(type)){
			jpql.append(" _device.type like :type and");
			conditions.put("type","%" + type + "%");
		}
		if(!StringUtils.isBlank(brand)){
			jpql.append(" _device.brand like :brand and");
			conditions.put("brand","%" +brand + "%");
		}
		
		if(!StringUtils.isBlank(model)){
			jpql.append(" _device.model like :model and");
			conditions.put("model","%" + model + "%");
		}
		
		jpql.append(" 1=1");
		
	
		jpql.append(" order by _device.id");
		List<Device> devices =getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		if(devices != null) {
			return devices;
		}
		else
		{
			return null;
		}
	}
	
	
	
	
	/**
	 * 统计符合关键字查找的设备数量
	 * @param companyName 公司名
	 * @param number 设备编号
	 * @param type  类型
	 * @param brand  品牌
	 * @param model  型号
	 * @return  long 设备数量
	 */
	public static long countAllDevicesInByKeyWords(String companyName, String number, String type,String brand,String model) {
		StringBuilder jpql = new StringBuilder("select count(_device.id) from Device _device where");
		Map<String,Object> conditions = new HashMap<String,Object>();			
		if(!StringUtils.isBlank(companyName)){
			jpql.append(" _device.company.name = :companyName and");
			conditions.put("companyName", companyName);
		}
		
		if(!StringUtils.isBlank(number)){
			jpql.append(" _device.number like :number and");
			conditions.put("number","%" + number + "%");
		}
		
		if(!StringUtils.isBlank(type)){
			jpql.append(" _device.type like :type and");
			conditions.put("type","%" + type + "%");
		}
		if(!StringUtils.isBlank(brand)){
			jpql.append(" _device.brand like :brand and");
			conditions.put("brand","%" +brand + "%");
		}
		
		if(!StringUtils.isBlank(model)){
			jpql.append(" _device.model like :model and");
			conditions.put("model","%" + model + "%");
		}
		jpql.append(" 1=1");
		Long count = getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions).singleResult();
		return count;	
		
	}
	
	/**
	 * 通过P2PUUID，来获取设备
	 * @param P2PUUID
	 * @return 设备
	 */
	public static Device getDeviceByP2PUUID(String P2PUUID) {
		String jpql = "select t from Device t where t.P2PUUID = :P2PUUID";
		Device device = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("P2PUUID", P2PUUID)
				.singleResult();
		return device;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
