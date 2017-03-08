package com.apical.dmcloud.storage.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.commons.infra.ResourceLocation;

@Entity
@Table(name = "cl_storageinfo")
public class StorageInfo extends SecurityAbstractEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 资源文件存储位置
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "LOCATION")
	private ResourceLocation location;
	
	/**
	 * 资源文件的存储路径；
	 * 在不同的存储系统下，该字段含义会不同
	 */
	@Column(name = "PATH")
	private String path;
	
	/**
	 * 小尺寸缩略图存储位置
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "SMALLLOCATION")
	private ResourceLocation thumbnailLocation;
	
	/**
	 * 小尺寸缩略图的存储路径；
	 */
	@Column(name = "SMALLPATH")
	private String thumbnailPath;
	
	/**
	 * 中等尺寸缩略图存储位置
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "MIDDLELOCATION")
	private ResourceLocation middleThumbnailLocation;
	
	/**
	 * 中等尺寸缩略图的存储路径；
	 */
	@Column(name = "MIDDLEPATH")
	private String middleThumbnailPath;
	
	public StorageInfo()
	{
	}

	/**
	 * 获取存储位置
	 * @return 存储位置
	 */
	public ResourceLocation getLocation() {
		return location;
	}

	/**
	 * 设置存储位置
	 * @param location 存储位置
	 */
	public void setLocation(ResourceLocation location) {
		this.location = location;
	}

	/**
	 * 获取存储路径
	 * @return 存储路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置存储路径
	 * @param path 存储路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 获取小尺寸缩略图存储位置
	 * @return 小尺寸缩略图存储位置
	 */
	public ResourceLocation getThumbnailLocation() {
		return thumbnailLocation;
	}

	/**
	 * 设置小尺寸缩略图存储位置
	 * @param thumbnailLocation 小尺寸缩略图存储位置
	 */
	public void setThumbnailLocation(ResourceLocation thumbnailLocation) {
		this.thumbnailLocation = thumbnailLocation;
	}

	/**
	 * 获取小尺寸缩略图存储路径
	 * @return 小尺寸缩略图存储路径
	 */
	public String getThumbnailPath() {
		return thumbnailPath;
	}

	/**
	 * 设置小尺寸缩略图存储路径
	 * @param thumbnailPath 小尺寸缩略图存储路径
	 */
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	/**
	 * 获取中等尺寸缩略图存储位置
	 * @return 中等尺寸缩略图存储位置
	 */
	public ResourceLocation getMiddleThumbnailLocation() {
		return middleThumbnailLocation;
	}

	/**
	 * 设置中等尺寸缩略图存储位置
	 * @param middleThumbnailLocation 中等尺寸缩略图存储位置
	 */
	public void setMiddleThumbnailLocation(ResourceLocation middleThumbnailLocation) {
		this.middleThumbnailLocation = middleThumbnailLocation;
	}

	/**
	 * 获取中等尺寸缩略图存储路径
	 * @return 中等尺寸缩略图存储路径
	 */
	public String getMiddleThumbnailPath() {
		return middleThumbnailPath;
	}

	/**
	 * 设置中等尺寸缩略图存储路径
	 * @param middleThumbnailPath 中等尺寸缩略图存储路径
	 */
	public void setMiddleThumbnailPath(String middleThumbnailPath) {
		this.middleThumbnailPath = middleThumbnailPath;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"StorageInfo"};
	}

	/**
	 * 批量获取存储信息
	 * @param ids
	 * @return
	 */
	public static List<StorageInfo> queryStorageInfosByIds(List<Long> ids){
		String jqpl = "select _s from StorageInfo _s where _s.id in :ids";
		List<StorageInfo> storageInfos = StorageInfo.getRepository().createJpqlQuery(jqpl).addParameter("ids", ids).list();
		return storageInfos;
	}
	

	/**
	 * 查询文件的存储路径
	 * @param id 文件存储信息ID
	 * @return 文件路径
	 */
	public static String queryStoragePathById(Long id){
		String jqpl = "select _s.path from StorageInfo _s where _s.id =:id";
		String storagepath = StorageInfo.getRepository().createJpqlQuery(jqpl).addParameter("id", id).singleResult();
		return storagepath;
	}
}
