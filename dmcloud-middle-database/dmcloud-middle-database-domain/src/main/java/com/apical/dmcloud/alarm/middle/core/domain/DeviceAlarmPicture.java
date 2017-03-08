package com.apical.dmcloud.alarm.middle.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.apical.dmcloud.middle.infra.AbstractIDEntity;
import com.apical.dmcloud.storage.middle.core.domain.Picture;

/**
 * 设备报警图片
 * 并重新命名实体名称@Entity(name = "Middle.DeviceAlarmPicture")，避免与spring数据库模块中的相同实体
 * 冲突@Entity(name = "DeviceAlarmPicture")
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.DeviceAlarmPicture")
@Table(name = "cl_alarm_device_picture_map")
public class DeviceAlarmPicture extends AbstractIDEntity
{
	/**
	 * serialVersionUID = 5508335498457027100L;
	 */
	private static final long serialVersionUID = 5508335498457027100L;

	/**
	 * 设备报警信息ID
	 */
	@Column(name = "DEV_RECORDID")
	private Long deviceAlarmId = null;
	
	/**
	 * 图片ID
	 */
	@Column(name = "PICTURE_ID")
	private Long pictureId = null;
	
	@Transient
	private Picture alarmPicture = null;
	
	/**
	 * 上传状态
	 */
	@Column(name = "UPLOAD_STATUS")
	private Boolean uploadStatus = false;
	
	/**
	 * 文件存储路径
	 */
	@Column(name = "STORAGE_PATH")
	private String storagePath = null;
	
	/**
	 * 获取设备报警信息id
	 * @return 设备报警信息id
	 */
	public Long getDeviceAlarmId()
	{
		return deviceAlarmId;
	}

	/**
	 * 设置设备报警信息id
	 * @param deviceAlarmId 设备报警信息id
	 */
	public void setDeviceAlarmId(Long deviceAlarmId)
	{
		this.deviceAlarmId = deviceAlarmId;
	}

	/**
	 * 获取图片id
	 * @return 图片id
	 */
	public Long getPictureId()
	{
		return pictureId;
	}

	/**
	 * 设置图片id
	 * @param pictureId 图片id
	 */
	public void setPictureId(Long pictureId)
	{
		this.pictureId = pictureId;
	}
	
	/**
	 * 获取报警图片
	 * @return 报警图片
	 */
	public Picture getAlarmPicture()
	{
		if(alarmPicture == null && pictureId != null)
		{
			alarmPicture = Picture.get(pictureId);
		}
		return alarmPicture;
	}

	/**
	 * 设置报警图片
	 * @param alarmPicture 报警图片
	 */
	public void setAlarmPicture(Picture alarmPicture)
	{
		this.alarmPicture = alarmPicture;
	}

	/**
	 * 获取上传状态
	 * @return 上传状态
	 */
	public Boolean getUploadStatus()
	{
		return uploadStatus;
	}

	/**
	 * 设置上传状态
	 * @param uploadStatus 上传状态
	 */
	public void setUploadStatus(Boolean uploadStatus)
	{
		this.uploadStatus = uploadStatus;
	}

	/**
	 * 获取文件存储路径
	 * @return 文件存储路径
	 */
	public String getStoragePath()
	{
		return storagePath;
	}

	/**
	 * 设置文件存储路径
	 * @param storagePath 文件存储路径
	 */
	public void setStoragePath(String storagePath)
	{
		this.storagePath = storagePath;
	}

	@Override
	public String[] businessKeys()
	{
		// TODO Auto-generated method stub
		return new String[]{"DeviceAlarmPicture"};
	}
	
	/**
	 * 根据设备报警id，来获取报警图片数量
	 * @param deviceAlarmId 设备报警信息id
	 * @return 报警图片数量
	 */
	public static long countDeviceAlarmPicturesByAlarmId(long deviceAlarmId)
	{
		String query = "select count(_picture.id) from Middle.DeviceAlarmPicture _picture where"
				+ " _picture.deviceAlarmId=:deviceAlarmId";
		Long pictures = getRepository().createJpqlQuery(query)
				.addParameter("deviceAlarmId", deviceAlarmId)
				.singleResult();
		return pictures;
	}
	
	/**
	 * 根据设备报警id，来获取报警图片
	 * @param deviceAlarmId 设备报警信息id
	 * @return 报警图片
	 */
	public static List<DeviceAlarmPicture> queryDeviceAlarmPicturesByAlarmId(long deviceAlarmId)
	{
		String query = "select _picture from Middle.DeviceAlarmPicture _picture where"
				+ " _picture.deviceAlarmId=:deviceAlarmId";
		List<DeviceAlarmPicture> pictures = getRepository().createJpqlQuery(query)
				.addParameter("deviceAlarmId", deviceAlarmId)
				.list();
		return pictures;
	}
}
