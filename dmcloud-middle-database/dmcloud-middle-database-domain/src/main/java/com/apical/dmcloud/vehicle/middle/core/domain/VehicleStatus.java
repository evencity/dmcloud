package com.apical.dmcloud.vehicle.middle.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apical.dmcloud.middle.infra.AbstractIDEntity;
import com.apical.dmcloud.vehicle.middle.core.VehicleStatusNotExistException;

/**
 * 车辆实时状态
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.VehicleStatus")
@Table(name = "cl_vehicle_status")
public class VehicleStatus extends AbstractIDEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 车辆id
	 */
	@Column(name = "VEHICLE_ID")
	private Long vehicleId;
	
	/**
	 * 在线状态
	 */
	@Column(name = "ONLINE")
	private Boolean isOnline;
	
	/**
	 * 实时速度
	 */
	@Column(name = "SPEED")
	private Float speed;
	
	/**
	 * 实时里程数
	 */
	@Column(name = "MILEAGE")
	private Double mileage;
	
	/**
	 * 实时位置
	 */
	@Column(name = "POSITION")
	private String position;
	
	/**
	 * 实时上报时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME")
	private Date time;

	/**
	 * 获取车辆id
	 * @return 车辆id
	 */
	public Long getVehicleId() {
		return vehicleId;
	}

	/**
	 * 设置车辆id
	 * @param vehicleId 车辆id
	 */
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	/**
	 * 获取在线状态
	 * @return 在线状态
	 */
	public Boolean getIsOnline() {
		return isOnline;
	}

	/**
	 * 设置在线状态
	 * @param isOnline 在线状态
	 */
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	/**
	 * 获取实时速度
	 * @return 实时速度
	 */
	public Float getSpeed() {
		return speed;
	}

	/**
	 * 设置实时速度
	 * @param speed 实时速度
	 */
	public void setSpeed(Float speed) {
		this.speed = speed;
	}

	/**
	 * 获取实时里程数
	 * @return 实时里程数
	 */
	public Double getMileage() {
		return mileage;
	}

	/**
	 * 设置实时里程数
	 * @param mileage 实时里程数
	 */
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	/**
	 * 获取实时位置
	 * @return 实时位置
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * 设置实时位置
	 * @param position 实时位置
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * 获取实时上报时间
	 * @return 实时上报时间
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * 设置实时上报时间
	 * @param time 实时上报时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"VehicleStatus"};
	}
	
	/**
	 * 保存或更新车辆状态信息
	 * @param status 状态信息
	 * @return 保存或更新是否成功
	 */
	public static boolean saveOrUpdateByVehicleId(VehicleStatus status)
	{
		Long id = status.getId();
		if(id != null)
		{
			//若id不为空，则直接调用保存接口
			status.save();
			return true;
		}
		
		//id为空，则通过判断车辆id
		Long vehicleId = status.getVehicleId();
		if(vehicleId == null)
		{
			return false;
		}
		
		try
		{
			long vid = getIdByVehicleId(vehicleId);
			status.setId(vid);
			status.update();
		}
		catch (VehicleStatusNotExistException e)
		{
			// TODO Auto-generated catch block
			status.save();
		}
		
		return true;
	}
	
	/**
	 * 通过车辆id，来判断车辆状态是否存在
	 * @param vehicleId 车辆id
	 * @return 状态是否存在
	 */
	public static boolean existByVehicleId(long vehicleId)
	{
		String jpql ="select count(_status) from Middle.VehicleStatus _status"
				+ " where _status.vehicleId = :vehicleId";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.singleResult();
		
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
	 * 通过车辆id，来判断车辆状态是否存在
	 * @param vehicleId 车辆id
	 * @return 状态是否存在
	 * @throws VehicleStatusNotExistException 
	 */
	public static long getIdByVehicleId(long vehicleId) throws VehicleStatusNotExistException
	{
		String jpql ="select _status.id from Middle.VehicleStatus _status"
				+ " where _status.vehicleId = :vehicleId";
		Long id = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.singleResult();
		
		if(id == null)
		{
			throw new VehicleStatusNotExistException("vehicle status not exist! For vehicleid:" + vehicleId);
		}
		return id;
	}
	
	/**
	 * 根据id，来删除车辆状态信息
	 * @param id 状态id
	 * @return 删除是否成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = " delete from Middle.VehicleStatus _status"
				+ " where _status.id = :id";
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
	 * 根据车辆id，来删除车辆状态信息
	 * @param vehicleId 车辆id
	 * @return 删除是否成功
	 */
	public static boolean deleteByVehicleId(long vehicleId)
	{
		String jpql = "delete from Middle.VehicleStatus _status"
				+ " where _status.vehicleId = :vehicleId";
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
	 * 根据车辆id，来获取车辆状态信息
	 * @param vehicleId 车辆id
	 * @return 车辆状态信息
	 */
	public static VehicleStatus getByVehicleId(long vehicleId)
	{
		String jpql ="select _status from Middle.VehicleStatus _status"
				+ " where _status.vehicleId = :vehicleId";
		VehicleStatus status = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.singleResult();
		
		return status;
	}
}
