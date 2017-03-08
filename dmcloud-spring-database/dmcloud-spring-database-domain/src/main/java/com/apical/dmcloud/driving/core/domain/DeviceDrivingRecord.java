package com.apical.dmcloud.driving.core.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

import com.apical.dmcloud.storage.core.domain.Picture;
import com.apical.dmcloud.storage.core.domain.Resource;
import com.apical.dmcloud.storage.core.domain.Video;

/**
 * 行车记录信息表
 * @author qiuzeng
 *
 */
@Entity
@Table(name = "cl_drivingrecord_device")
public class DeviceDrivingRecord extends KoalaAbstractEntity
{
	/**
	 * serialVersionUID = 1L;
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 设备ID
	 */
	@Column(name = "DEVICE_ID")
	private Long deviceId = null;
	
	/**
	 * 车辆id号
	 */
	@Column(name = "VEHICLE_ID")
	private Long vehicleId = null;
	
	/**
	 * gps文件存储信息id
	 */
	@Column(name = "FILE_ID")
	private Long gpsFileId = null;
	
	/**
	 * 上传时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOADTIME")
	private Date uploadTime = null;
	
	/**
	 * 起始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME")
	private Date startTime = null;
	
	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINISHTIME")
	private Date endTime = null;
	
	/**
	 * 起始位置
	 */
	@Column(name = "STARTPLACE")
	private String startPlace = null;
	
	/**
	 * 结束位置
	 */
	@Column(name = "FINISHPLACE")
	private String endPlace = null;
	
	/**
	 * 经度
	 */
	@Column(name = "LONGITUDE")
	private Float longitude = null;
	
	/**
	 * 纬度
	 */
	@Column(name = "LATITUDE")
	private Float latitude = null;
	
	/**
	 * 里程数
	 */
	@Column(name = "MILEAGE")
	private Double mileage = 0.0;
	
	/**
	 * 最低时速
	 */
	@Column(name = "MINSPEED")
	private Float minSpeed = 0.0f;
	
	/**
	 * 平均时速
	 */
	@Column(name = "AVGSPEED")
	private Float averageSpeed = 0.0f;
	
	/**
	 * 最高时速
	 */
	@Column(name = "MAXSPEED")
	private Float maxSpeed = 0.0f;
	
	public DeviceDrivingRecord()
	{
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
	 * 获取gps文件存储信息id
	 * @return 存储信息id
	 */
	public Long getGpsFileId() {
		return gpsFileId;
	}

	/**
	 * 设置gps文件存储信息id
	 * @param gpsFileId 存储信息id
	 */
	public void setGpsFileId(Long gpsFileId) {
		this.gpsFileId = gpsFileId;
	}

	/**
	 * 获取上传时间
	 * @return 上传时间
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * 设置上传时间
	 * @param uploadTime 上传时间
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * 获取开始时间
	 * @return 开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 设置开始时间
	 * @param startTime 开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 获取结束时间
	 * @return 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置结束时间
	 * @param endTime 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取起始位置
	 * @return 起始位置
	 */
	public String getStartPlace()
	{
		return startPlace;
	}

	/**
	 * 设置起始位置
	 * @param startPlace 起始位置
	 */
	public void setStartPlace(String startPlace)
	{
		this.startPlace = startPlace;
	}

	/**
	 * 获取结束位置
	 * @return 结束位置
	 */
	public String getEndPlace()
	{
		return endPlace;
	}

	/**
	 * 设置结束位置
	 * @param endPlace 结束位置
	 */
	public void setEndPlace(String endPlace)
	{
		this.endPlace = endPlace;
	}
	
	/**
	 * 获取经度
	 * @return 经度
	 */
	public Float getLongitude()
	{
		return longitude;
	}

	/**
	 * 设置经度
	 * @param longitude - 经度
	 */
	public void setLongitude(Float longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * 获取纬度
	 * @return 纬度
	 */
	public Float getLatitude()
	{
		return latitude;
	}

	/**
	 * 设置纬度
	 * @param latitude - 纬度
	 */
	public void setLatitude(Float latitude)
	{
		this.latitude = latitude;
	}
	
	/**
	 * 获取里程数
	 * @return 里程数
	 */
	public Double getMileage()
	{
		return mileage;
	}

	/**
	 * 设置里程数
	 * @param mileage 里程数
	 */
	public void setMileage(Double mileage)
	{
		this.mileage = mileage;
	}

	/**
	 * 获取最低时速
	 * @return 最低时速
	 */
	public Float getMinSpeed()
	{
		return minSpeed;
	}

	/**
	 * 设置最低时速
	 * @param minSpeed 最低时速
	 */
	public void setMinSpeed(Float minSpeed)
	{
		this.minSpeed = minSpeed;
	}

	/**
	 * 获取平均时速
	 * @return 平均时速
	 */
	public Float getAverageSpeed()
	{
		return averageSpeed;
	}

	/**
	 * 设置平均时速
	 * @param averageSpeed 平均时速
	 */
	public void setAverageSpeed(Float averageSpeed)
	{
		this.averageSpeed = averageSpeed;
	}

	/**
	 * 获取最大时速
	 * @return 最大时速
	 */
	public Float getMaxSpeed()
	{
		return maxSpeed;
	}

	/**
	 * 设置最大时速
	 * @param maxSpeed 最大时速
	 */
	public void setMaxSpeed(Float maxSpeed)
	{
		this.maxSpeed = maxSpeed;
	}
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"DeviceDrivingRecord"};
	}
	
	/**
	 * 根据id来删除行车记录
	 * @param id 行车记录id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from DeviceDrivingRecord _record where _record.id=:id";
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
	 * 根据id，获取设备行车记录
	 * @param id 行车记录id
	 * @return 设备行车记录
	 */
	public static DeviceDrivingRecord getById(long id)
	{
		return get(DeviceDrivingRecord.class, id);
	}
	
	/**
	 * 更新速度统计信息
	 * @param recordId 设备行车记录id
	 * @param record 设备行车记录
	 * @return 更新是否成功
	 */
	public static boolean updateSpeedInfo(long recordId, DeviceDrivingRecord record)
	{
		String jpql = "update DeviceDrivingRecord _record set _record.startPlace=:startPlace"
				+ " and _record.endPlace=:endPlace"
				+ " and _record.longitude=:longitude"
				+ " and _record.latitude=:latitude"
				+ " and _record.mileage=:mileage"
				+ " and _record.minSpeed=:minSpeed"
				+ " and _record.averageSpeed=:averageSpeed"
				+ " and _record.maxSpeed=:maxSpeed"
				+ " where _record.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("startPlace", record.getStartPlace())
				.addParameter("endPlace", record.getEndPlace())
				.addParameter("longitude", record.getLongitude())
				.addParameter("latitude", record.getLatitude())
				.addParameter("mileage", record.getMileage())
				.addParameter("averageSpeed", record.getAverageSpeed())
				.addParameter("minSpeed", record.getMinSpeed())
				.addParameter("maxSpeed", record.getMaxSpeed())
				.addParameter("id", recordId)
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
	 * 获取行车记录的图片列表
	 * @param recordId 行车记录id
	 * @return 图片列表
	 */
	public static List<Picture> getPictureList(long recordId)
	{
		String sql = "select PICTURE_ID from cl_drivingrecord_device_picture_map"
				+ " where DEV_RECORDID=:recordId";
		List<BigInteger> ids = getRepository().createSqlQuery(sql)
				.addParameter("recordId", recordId)
				.list();
		List<Long> pictureIds = new ArrayList<Long>();
		for(BigInteger id : ids)
		{
			pictureIds.add(id.longValue());
		}
		
		List<Picture> pictures = Picture.getByIdList(pictureIds);
		
		return pictures;
	}
	
	/**
	 * 获取行车记录的视频列表
	 * @param recordId 行车记录id
	 * @return 视频列表
	 */
	public static List<Video> getVideoList(long recordId)
	{
		String sql = "select VIDEO_ID from cl_drivingrecord_device_video_map"
				+ " where DEV_RECORDID=:recordId";
		List<BigInteger> ids = getRepository().createSqlQuery(sql)
				.addParameter("recordId", recordId)
				.list();
		List<Long> videoIds = new ArrayList<Long>();
		for(BigInteger id : ids)
		{
			videoIds.add(id.longValue());
		}
		
		List<Video> videos = Video.getByIdList(videoIds);
		
		return videos;
	}
	
	/**
	 * 获取行车记录的资源列表
	 * @param recordId 行车记录id
	 * @return 资源列表
	 */
	public static List<Resource> getResourceList(long recordId)
	{
		List<Picture> pictures = getPictureList(recordId);
		List<Video> videos = getVideoList(recordId);
		
		List<Resource> resources = new ArrayList<Resource>();
		resources.addAll(pictures);
		resources.addAll(videos);
		
		return resources;
	}
	
	/**
	 * 根据车辆id，来统计行车记录的数量。注意：该接口只能获取历史记录；
	 * @param vehicleId 车辆id
	 * @return 行车记录数量
	 * @throws Exception
	 */
	public static long countAllByVehicleId(long vehicleId)
	{
		String jpql = "select count(_record.id) from DeviceDrivingRecord _record where _record.vehicleId=:vehicleId";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.singleResult();
		return count;
		
//		return 1;
	}
	
	/**
	 * 根据车辆id，来统计行车记录的数量。注意：该接口只能获取历史记录；
	 * @param vehicleId 车辆id
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 行车记录数量
	 * @throws Exception
	 */
	public static long countAllByVehicleIdAndTimerange(long vehicleId, Date startDate, Date endDate)
	{
		String jpql = "select count(_record.id) from DeviceDrivingRecord _record where _record.vehicleId=:vehicleId"
				+ " and _record.uploadTime > :beginDate and _record.uploadTime < :endDate";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.singleResult();
		return count;
		
//		return 1;
	}
	
	/**
	 * 根据车辆id，获取行车记录。注意：该接口只能获取历史记录；不能获取正在行驶当中的行车轨迹。
	 * @return vehicleId 车辆id
	 * @throws Exception
	 */
	public static List<DeviceDrivingRecord> queryAllByVehicleId(long vehicleId)
	{
		String query = "select _record from DeviceDrivingRecord _record where _record.vehicleId=:vehicleId"
				+ " order by _record.uploadTime desc";
		List<DeviceDrivingRecord> records = getRepository().createJpqlQuery(query)
				.addParameter("vehicleId", vehicleId)
				.list();
		
		return records;
	}
	
	/**
	 * 根据车辆id，获取行车记录。注意：该接口只能获取历史记录；不能获取正在行驶当中的行车轨迹。
	 * @return vehicleId 车辆id
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @throws Exception
	 */
	public static List<DeviceDrivingRecord> queryAllByVehicleIdAndTimerange(long vehicleId,
			Date startDate, Date endDate)
	{
		String query = "select _record from DeviceDrivingRecord _record where _record.vehicleId=:vehicleId"
				+ " and _record.uploadTime > :beginDate and _record.uploadTime < :endDate"
				+ " order by _record.uploadTime desc";
		List<DeviceDrivingRecord> records = getRepository().createJpqlQuery(query)
				.addParameter("vehicleId", vehicleId)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.list();
		
		return records;
	}

	/**
	 * 根据车辆id，分页查询行车记录。注意：该接口只能获取历史记录；不能获取正在行驶当中的行车轨迹
	 * @param vehicleId 车辆id
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 行车记录信息
	 * @throws Exception
	 */
	public static List<DeviceDrivingRecord> queryAllByVehicleIdInPage(long vehicleId,
			int pageCount, int pageSize)
	{
		String query = "select _record from DeviceDrivingRecord _record where _record.vehicleId=:vehicleId"
				+ " order by _record.uploadTime desc";
		List<DeviceDrivingRecord> records = getRepository().createJpqlQuery(query)
				.addParameter("vehicleId", vehicleId)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return records;
	}
	
	/**
	 * 根据车辆id，分页查询行车记录。注意：该接口只能获取历史记录；不能获取正在行驶当中的行车轨迹
	 * @param vehicleId 车辆id
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 行车记录信息
	 * @throws Exception
	 */
	public static List<DeviceDrivingRecord> queryAllByVehicleIdAndTimerangeInPage(long vehicleId,
			Date startDate, Date endDate, int pageCount, int pageSize)
	{
		String query = "select _record from DeviceDrivingRecord _record where _record.vehicleId=:vehicleId"
				+ " and _record.uploadTime > :beginDate and _record.uploadTime < :endDate"
				+ " order by _record.uploadTime desc";
		List<DeviceDrivingRecord> records = getRepository().createJpqlQuery(query)
				.addParameter("vehicleId", vehicleId)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return records;
	}
	
	/**
	 * 根据行车记录ID查询文件ID
	 * @param id 行车记录ID
	 * @return 文件ID
	 */
	public static Long  queryGpsFileIdById(long id){
		String jpql = "select _record.gpsFileId from DeviceDrivingRecord _record where _record.id =:id";
		Long vehicleId = getRepository().createJpqlQuery(jpql).addParameter("id", id).singleResult();
		return vehicleId;
	}

}
