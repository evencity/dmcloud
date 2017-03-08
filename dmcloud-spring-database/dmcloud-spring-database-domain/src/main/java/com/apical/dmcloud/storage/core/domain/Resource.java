package com.apical.dmcloud.storage.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apical.dmcloud.AbstractIDEntity;
import com.apical.dmcloud.commons.infra.ClientType;
import com.apical.dmcloud.commons.infra.ResourceType;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Resource extends AbstractIDEntity
{
	/**
	 * serialVersionUID = -4053563613919360484L;
	 */
	private static final long serialVersionUID = -4053563613919360484L;
	
	/**
	 * 文件名
	 */
	@Column(name = "FILENAME")
	private String filename = null;
	
	/**
	 * 文件大小
	 */
	@Column(name = "FILESIZE")
	private Long filesize = 0L;
	
	/**
	 * 文件上传的时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOADTIME")
	private Date uploadTime = null;
	
	/**
	 * 上传日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOADDATE")
	private Date uploadDate = null;
	
	/**
	 * 拍摄时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SHOOTTIME")
	private Date shootTime = null;
	
	/**
	 * 资源是否紧急
	 */
	@Column(name = "IS_URGENT")
	private Boolean isUrgent = false;
	
	/**
	 * 摄像头编号
	 */
	@Column(name = "CAMERA")
	private Short cameraIndex;
	
	/**
	 * 设备id号
	 */
	@Column(name = "DEVICE_ID")
	private Long deviceId = null;
	
	/**
	 * 车辆id号
	 */
	@Column(name = "VEHICLE_ID")
	private Long vehicleId = null;
	
	/**
	 * 资源所属设备类型，默认为DeviceType.Web，表示从web端上传
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "CLIENTTYPE")
	private ClientType clientType = ClientType.Unknown;
	
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
	 * 资源的存储信息ID
	 */
	@Column(name = "STORAGE_ID")
	private Long resourceStorageInfoId = null;
	
	/**
	 * 资源描述
	 */
	@Column(name = "DESCRIPTION")
	private String description = null;

	/**
	 * 返回资源的类型
	 * @return ResourceType 资源类型
	 */
	public ResourceType getType()
	{
		return ResourceType.Undefined;
	}

	/**
	 * 返回资源的文件名
	 * @return String - 文件名
	 */
	public String getFilename()
	{
		return filename;
	}

	/**
	 * 设置资源的文件名
	 * @param filename - 新的文件名
	 */
	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	/**
	 * 返回资源的文件大小
	 * @return Long - 资源的文件大小
	 */
	public Long getFilesize()
	{
		return filesize;
	}

	/**
	 * 设置资源的文件大小
	 * @param filesize - 文件大小
	 */
	public void setFilesize(Long filesize)
	{
		this.filesize = filesize;
	}
	
	/**
	 * 返回资源的描述信息
	 * @return String - 描述信息
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * 设置资源的描述信息
	 * @param description - 描述信息
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * 获取设备id号
	 * @return 设备id号
	 */
	public Long getDeviceId()
	{
		return deviceId;
	}

	/**
	 * 设置设备id号
	 * @param deviceId - 设备id号
	 */
	public void setDeviceId(Long deviceId)
	{
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
	 * 获取客户端类型
	 * @return 客户端类型
	 */
	public ClientType getClientType() {
		return clientType;
	}

	/**
	 * 设置客户端类型
	 * @param clientType 客户端类型
	 */
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
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
	 * 获取上传日期
	 * @return 上传日期
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * 设置上传日期
	 * @param uploadDate 上传日期
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * 获取拍摄时间
	 * @return 拍摄时间
	 */
	public Date getShootTime() {
		return shootTime;
	}

	/**
	 * 设置拍摄时间
	 * @param shootTime 拍摄时间
	 */
	public void setShootTime(Date shootTime) {
		this.shootTime = shootTime;
	}

	/**
	 * 获取资源是否紧急
	 * @return 资源是否紧急
	 */
	public Boolean getIsUrgent() {
		return isUrgent;
	}

	/**
	 * 设置资源是否紧急
	 * @param isUrgent 资源是否紧急
	 */
	public void setIsUrgent(Boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	/**
	 * 获取摄像头编号
	 * @return 摄像头编号
	 */
	public Short getCameraIndex() {
		return cameraIndex;
	}

	/**
	 * 设置摄像头编号
	 * @param cameraIndex 摄像头编号
	 */
	public void setCameraIndex(Short cameraIndex) {
		this.cameraIndex = cameraIndex;
	}

	/**
	 * 获取资源存储信息id
	 * @return 资源存储信息id
	 */
	public Long getResourceStorageInfoId() {
		return resourceStorageInfoId;
	}

	/**
	 * 设置资源存储信息id
	 * @param resourceStorageInfoId 资源存储信息id
	 */
	public void setResourceStorageInfoId(Long resourceStorageInfoId) {
		this.resourceStorageInfoId = resourceStorageInfoId;
	}
}
