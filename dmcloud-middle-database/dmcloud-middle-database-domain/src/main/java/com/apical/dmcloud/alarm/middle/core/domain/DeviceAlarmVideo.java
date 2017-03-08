package com.apical.dmcloud.alarm.middle.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.apical.dmcloud.middle.infra.AbstractIDEntity;
import com.apical.dmcloud.storage.middle.core.domain.Video;

/**
 * 设备报警视频
 * @author qiuzeng
 *
 */
@Entity(name = "Middle.DeviceAlarmVideo")
@Table(name = "cl_alarm_device_video_map")
public class DeviceAlarmVideo extends AbstractIDEntity
{
	/**
	 * serialVersionUID = -4376817516480063419L;
	 */
	private static final long serialVersionUID = -4376817516480063419L;

	/**
	 * 设备报警信息ID
	 */
	@Column(name = "DEV_RECORDID")
	private Long deviceAlarmId = null;
	
	/**
	 * 视频ID
	 */
	@Column(name = "VIDEO_ID")
	private Long videoId = null;
	
	@Transient
	private Video alarmVideo = null;
	
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
	 * 获取视频id
	 * @return 视频id
	 */
	public Long getVideoId()
	{
		return videoId;
	}

	/**
	 * 设置视频id
	 * @param videoId 视频id
	 */
	public void setVideoId(Long videoId)
	{
		this.videoId = videoId;
	}
	
	/**
	 * 获取报警视频
	 * @return 报警视频
	 */
	public Video getAlarmVideo()
	{
		if(alarmVideo == null && videoId != null)
		{
			alarmVideo = Video.get(videoId);
		}
		return alarmVideo;
	}

	/**
	 * 设置报警视频
	 * @param alarmVideo 报警视频
	 */
	public void setAlarmVideo(Video alarmVideo)
	{
		this.alarmVideo = alarmVideo;
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
		return new String[]{"DeviceAlarmVideo"};
	}
	
	/**
	 * 根据设备报警id，来获取报警视频数量
	 * @param deviceAlarmId 设备报警信息id
	 * @return 报警视频数量
	 */
	public static long countDeviceAlarmVideosByAlarmId(long deviceAlarmId)
	{
		String query = "select count(_video.id) from Middle.DeviceAlarmVideo _video where"
				+ " _video.deviceAlarmId=:deviceAlarmId";
		Long pictures = getRepository().createJpqlQuery(query)
				.addParameter("deviceAlarmId", deviceAlarmId)
				.singleResult();
		return pictures;
	}
	
	/**
	 * 根据设备报警id，来获取报警视频
	 * @param deviceAlarmId 设备报警信息id
	 * @return 报警视频
	 */
	public static List<DeviceAlarmVideo> queryDeviceAlarmVideosByAlarmId(long deviceAlarmId)
	{
		String query = "select _video from Middle.DeviceAlarmVideo _video where"
				+ " _video.deviceAlarmId=:deviceAlarmId";
		List<DeviceAlarmVideo> videos = getRepository().createJpqlQuery(query)
				.addParameter("deviceAlarmId", deviceAlarmId)
				.list();
		return videos;
	}
}
