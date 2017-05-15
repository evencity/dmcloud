package com.apical.dmcloud.alarm.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apical.dmcloud.AbstractIDEntity;
import com.apical.dmcloud.alarm.core.AlarmRecordNotExistException;
import com.apical.dmcloud.commons.infra.AlarmType;

/**
 * 外围设备报警信息表对应的：外围设备报警信息实体类
 * @author lgx
 * 2017-5-15
 */
@Entity
@Table(name = "cl_alarm_device_peripherals")
public class DevicePeripheralsAlarmRecord extends AbstractIDEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1314610208346928324L;

	/**
	 * 设备ID
	 */
	@Column(name = "DEVICE_ID")
	private Long deviceId;
	
	/**
	 * 车辆id号
	 */
	@Column(name = "VEHICLE_ID")
	private Long vehicleId;
	
	/**
	 * 报警类型标记
	 */
	@Column(name = "TYPE")
	private Integer type;
	
	/**
	 * 报警UID
	 */
	@Column(name = "ALARM_UID")
	private String alarmUId;
	
	/**
	 * 报警时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME")
	private Date alarmTime;
	
	/**
	 * 报警内容
	 */
	@Column(name = "CONTENT")
	private String alarmContent;
	
	@Column(name = "CAMERA_NO")
	private String cameraNo;
	
	public DevicePeripheralsAlarmRecord() {
	}
	
	/**
	 * 获取设备ID
	 * @return 设备ID
	 */
	public Long getDeviceId() {
		return deviceId;
	}

	/**
	 * 设置设备ID
	 * @param deviceId 设备ID
	 */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getCameraNo() {
		return cameraNo;
	}

	public void setCameraNo(String cameraNo) {
		this.cameraNo = cameraNo;
	}

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
	 * 获取产品id
	 * @return 产品id
	 */
	public String getAlarmUId() {
		return alarmUId;
	}

	/**
	 * 设置报警uid
	 * @param alarmUId 报警uid
	 */
	public void setAlarmUId(String alarmUId) {
		this.alarmUId = alarmUId;
	}

	/**
	 * 获取报警类型标记
	 * @return 报警类型标记
	 */
	public Integer getType() {
		return type;
	}
	
	/**
	 * 设置报警类型标记
	 * @param type 报警类型标记
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	
	/**
	 * 获取报警时间
	 * @return 报警时间
	 */
	public Date getAlarmTime() {
		return alarmTime;
	}

	/**
	 * 设置报警时间
	 * @param alarmTime 报警时间
	 */
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	/**
	 * 获取报警内容
	 * @return 报警内容
	 */
	public String getAlarmContent() {
		return alarmContent;
	}

	/**
	 * 设置报警内容
	 * @param alarmContent 报警内容
	 */
	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	@Override
	public String[] businessKeys() {
		return new String[]{"DevicePeripheralsAlarmRecord"};
	}
	
	/**
	 * 根据id来删除报警信息
	 * @param id 报警信息id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id) {
		String jpql = "delete from DevicePeripheralsAlarmRecord _record"
				+ " where _record.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", id)
				.executeUpdate();
		if(count == 1) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据id，获取设备报警信息
	 * @param id 报警信息id
	 * @return 设备报警信息
	 */
	public static DevicePeripheralsAlarmRecord getById(long id) {
		return get(DevicePeripheralsAlarmRecord.class, id);
	}
	
	/**
	 * 通过设备id和报警uid，来获取报警信息id;
	 * 由于报警uid是唯一，因此结果是唯一（虽然一台设备可以被多个用户绑定）
	 * @param deviceId 设备id
	 * @param alarmUId 报警uid
	 * @return 报警信息id
	 * @throws Exception
	 */
	public static long queryIdByDeviceIdAndAlarmUId(long deviceId, String alarmUId)
			throws AlarmRecordNotExistException
	{
		String jpql = "select _record.id from DevicePeripheralsAlarmRecord _record"
				+ " where _record.deviceId=:deviceId"
				+ " and _record.alarmUId=:alarmUId";
		Long id = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.addParameter("alarmUId", alarmUId)
				.singleResult();
		return id;
	}
	
	/**
	 * 通过设备id和报警uid，来获取报警信息;
	 * 由于报警uid是唯一，因此结果是唯一（虽然一台设备可以被多个用户绑定）
	 * @param deviceId 设备id
	 * @param alarmUId 报警uid
	 * @return 报警信息
	 * @throws Exception
	 */
	public static DevicePeripheralsAlarmRecord queryByDeviceIdAndAlarmUId(long deviceId, String alarmUId) {
		String jpql = "select _record from DevicePeripheralsAlarmRecord _record"
				+ " where _record.deviceId=:deviceId"
				+ " and _record.alarmUId=:alarmUId";
		DevicePeripheralsAlarmRecord record = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.addParameter("alarmUId", alarmUId)
				.singleResult();
		
		return record;
	}
	
	/**
	 * 统计车辆的设备报警数量。
	 * @param vehicleId 车辆id
	 * @return 设备报警数量
	 * @throws Exception
	 */
	public static long countAllByVehicleId(long vehicleId) {
		String jpql = "select count(_record.id) from DevicePeripheralsAlarmRecord _record"
				+ " where _record.vehicleId=:vehicleId";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.singleResult();
		
		return count;
	}
	
	/**
	 * 根据车辆id，来获取设备报警。
	 * @param vehicleId 车辆id
	 * @return 设备报警信息
	 * @throws Exception
	 */
	public static List<DevicePeripheralsAlarmRecord> queryAllByVehicleId(long vehicleId) {
		String jpql = "select _record from DevicePeripheralsAlarmRecord _record"
				+ " where _record.vehicleId=:vehicleId"
				+ " order by _record.alarmTime desc";
		List<DevicePeripheralsAlarmRecord> records = getRepository().createJpqlQuery(jpql)
				.addParameter("vehicleId", vehicleId)
				.list();
		
		return records;
	}
	
	/**
	 * 根据车辆id，来分页查询设备报警。
	 * @param vehicleId 车辆id
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 设备报警信息
	 * @throws Exception
	 */
	public static List<DevicePeripheralsAlarmRecord> queryAllByVehicleIdInPage(long vehicleId,
			int pageCount, int pageSize) {
		String jpql = "select _record from DevicePeripheralsAlarmRecord _record"
				+ " where _record.vehicleId=:vehicleId"
				+ " order by _record.alarmTime desc";
		List<DevicePeripheralsAlarmRecord> records = getRepository().createJpqlQuery(jpql)
				.addParameter("vehicleId", vehicleId)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return records;
	}
	
	/**
	 * 统计车辆的设备报警数量。
	 * @param vehicleId 车辆id
	 * @param type 报警类型
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 设备报警数量
	 * @throws Exception
	 */
	public static long countAllByVehicleIdAndTimerange(long vehicleId, int type,
			Date startDate, Date endDate) {
		String jpql = "select count(_record.id) from DevicePeripheralsAlarmRecord _record where"
				+ " _record.vehicleId=:vehicleId"
				+ " and _record.type=:type"
				+ " and _record.alarmTime > :beginDate"
				+ " and _record.alarmTime < :endDate";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.addParameter("type", type)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.singleResult();
		
		return count;
	}
	
	
	
	/**
	 * 根据车辆id，来获取设备报警。
	 * @param vehicleId 车辆id
	 * @param type 报警类型
	 * @return 设备报警信息
	 * @throws Exception
	 */
	public static List<DevicePeripheralsAlarmRecord> queryAllByVehicleIdAndTimerange(long vehicleId, int type,
			Date startDate, Date endDate) {
		String jpql = "select _record from DevicePeripheralsAlarmRecord _record"
				+ " where _record.vehicleId=:vehicleId"
				+ " and _record.type=:type"
				+ " and _record.alarmTime > :beginDate"
				+ " and _record.alarmTime < :endDate";
		List<DevicePeripheralsAlarmRecord> records = getRepository().createJpqlQuery(jpql)
				.addParameter("vehicleId", vehicleId)
				.addParameter("type", type)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.list();
		
		return records;
	}
	
	/**
	 * 根据车辆id，来分页查询设备报警。
	 * @param vehicleId 车辆id
	 * @param type 报警类型
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 设备报警信息
	 * @throws Exception
	 */
	public static List<DevicePeripheralsAlarmRecord> queryAllByVehicleIdAndTimerangeInPage(long vehicleId,
			int type, Date startDate, Date endDate,
			int pageCount, int pageSize) {
		String jpql = "select _record from DevicePeripheralsAlarmRecord _record"
				+ " where _record.vehicleId=:vehicleId"
				+ " and _record.type=:type"
				+ " and _record.alarmTime > :beginDate"
				+ " and _record.alarmTime < :endDate";
		List<DevicePeripheralsAlarmRecord> records = getRepository().createJpqlQuery(jpql)
				.addParameter("vehicleId", vehicleId)
				.addParameter("type", type)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return records;
	}
	
	/**
	 * 统计车辆的设备报警数量。
	 * @param vehicleId 车辆id
	 * @param alarmType 报警类型（枚举类型）
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 设备报警数量
	 * @throws Exception
	 */
	public static long countAllByVehicleIdAndTimerange(long vehicleId, AlarmType alarmType,
			Date startDate, Date endDate) {
		int type = alarmType.ordinal();
		
		return countAllByVehicleIdAndTimerange(vehicleId, type, startDate, endDate);
	}
	
	
	
	/**
	 * 根据车辆id，来获取设备报警。
	 * @param vehicleId 车辆id
	 * @param alarmType 报警类型（枚举类型）
	 * @return 设备报警信息
	 * @throws Exception
	 */
	public static List<DevicePeripheralsAlarmRecord> queryAllByVehicleIdAndTimerange(long vehicleId, AlarmType alarmType,
			Date startDate, Date endDate) {
		int type = alarmType.ordinal();
		
		return queryAllByVehicleIdAndTimerange(vehicleId, type, startDate, endDate);
	}
	
	/**
	 * 根据车辆id，来分页查询设备报警。
	 * @param vehicleId 车辆id
	 * @param alarmType 报警类型（枚举类型）
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 设备报警信息
	 * @throws Exception
	 */
	public static List<DevicePeripheralsAlarmRecord> queryAllByVehicleIdAndTimerangeInPage(long vehicleId,
			AlarmType alarmType, Date startDate, Date endDate,
			int pageCount, int pageSize) {
		int type = alarmType.ordinal();
		
		return queryAllByVehicleIdAndTimerangeInPage(vehicleId, type, startDate, endDate,
				pageCount, pageSize);
	}
}
