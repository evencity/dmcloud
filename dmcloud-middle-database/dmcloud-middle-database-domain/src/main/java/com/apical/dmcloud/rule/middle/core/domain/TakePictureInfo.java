package com.apical.dmcloud.rule.middle.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.apical.dmcloud.middle.infra.AbstractVersionEntity;

/**
 * 拍照信息表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.TakePictureInfo")
@Table(name = "cl_take_picture")
public class TakePictureInfo extends AbstractVersionEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否拍照
	 */
	@Column(name = "TAKE")
	private Boolean takePicture = false;
	
	/**
	 * 摄像头1是否拍照
	 */
	@Column(name = "CAM1")
	private Boolean takeForCAM1 = false;
	
	/**
	 * 摄像头2是否拍照
	 */
	@Column(name = "CAM2")
	private Boolean takeForCAM2 = false;
	
	/**
	 * 摄像头3是否拍照
	 */
	@Column(name = "CAM3")
	private Boolean takeForCAM3 = false;
	
	/**
	 * 摄像头4是否拍照
	 */
	@Column(name = "CAM4")
	private Boolean takeForCAM4 = false;
	
	/**
	 * 摄像头5是否拍照
	 */
	@Column(name = "CAM5")
	private Boolean takeForCAM5 = false;
	
	/**
	 * 摄像头6是否拍照
	 */
	@Column(name = "CAM6")
	private Boolean takeForCAM6 = false;
	
	/**
	 * 摄像头7是否拍照
	 */
	@Column(name = "CAM7")
	private Boolean takeForCAM7 = false;
	
	/**
	 * 摄像头8是否拍照
	 */
	@Column(name = "CAM8")
	private Boolean takeForCAM8 = false;

	/**
	 * 获取是否拍照
	 * @return 是否拍照 
	 */
	public Boolean getTakePicture() {
		return takePicture;
	}

	/**
	 * 设置是否拍照
	 * @param takePicture 是否拍照
	 */
	public void setTakePicture(Boolean takePicture) {
		this.takePicture = takePicture;
	}

	/**
	 * 获取摄像头1是否拍照
	 * @return 摄像头1是否拍照
	 */
	public Boolean getTakeForCAM1() {
		return takeForCAM1;
	}

	/**
	 * 设置摄像头1是否拍照
	 * @param takeForCAM1 摄像头1是否拍照
	 */
	public void setTakeForCAM1(Boolean takeForCAM1) {
		this.takeForCAM1 = takeForCAM1;
	}

	/**
	 * 获取摄像头2是否进行拍照
	 * @return 摄像头2是否进行拍照
	 */
	public Boolean getTakeForCAM2() {
		return takeForCAM2;
	}

	/**
	 * 设置摄像头2是否进行拍照
	 * @param takeForCAM2 摄像头2是否进行拍照
	 */
	public void setTakeForCAM2(Boolean takeForCAM2) {
		this.takeForCAM2 = takeForCAM2;
	}

	/**
	 * 获取摄像头3是否进行拍照
	 * @return 摄像头3是否进行拍照
	 */
	public Boolean getTakeForCAM3() {
		return takeForCAM3;
	}

	/**
	 * 设置摄像头3是否进行拍照
	 * @param takeForCAM3 摄像头3是否进行拍照
	 */
	public void setTakeForCAM3(Boolean takeForCAM3) {
		this.takeForCAM3 = takeForCAM3;
	}

	/**
	 * 获取摄像头4是否进行拍照
	 * @return 摄像头4是否进行拍照
	 */
	public Boolean getTakeForCAM4() {
		return takeForCAM4;
	}

	/**
	 * 设置摄像头4是否进行拍照
	 * @param takeForCAM4 摄像头4是否进行拍照
	 */
	public void setTakeForCAM4(Boolean takeForCAM4) {
		this.takeForCAM4 = takeForCAM4;
	}

	/**
	 * 获取摄像头5是否进行拍照
	 * @return 摄像头5是否进行拍照
	 */
	public Boolean getTakeForCAM5() {
		return takeForCAM5;
	}

	/**
	 * 设置摄像头5是否进行拍照
	 * @param takeForCAM5 摄像头5是否进行拍照
	 */
	public void setTakeForCAM5(Boolean takeForCAM5) {
		this.takeForCAM5 = takeForCAM5;
	}

	/**
	 * 获取摄像头6是否进行拍照
	 * @return 摄像头6是否进行拍照
	 */
	public Boolean getTakeForCAM6() {
		return takeForCAM6;
	}

	/**
	 * 设置摄像头6是否进行拍照
	 * @param takeForCAM6 摄像头6是否进行拍照
	 */
	public void setTakeForCAM6(Boolean takeForCAM6) {
		this.takeForCAM6 = takeForCAM6;
	}

	/**
	 * 获取摄像头7是否进行拍照
	 * @return 摄像头7是否进行拍照
	 */
	public Boolean getTakeForCAM7() {
		return takeForCAM7;
	}

	/**
	 * 设置摄像头7是否进行拍照
	 * @param takeForCAM7 摄像头7是否进行拍照
	 */
	public void setTakeForCAM7(Boolean takeForCAM7) {
		this.takeForCAM7 = takeForCAM7;
	}

	/**
	 * 获取摄像头8是否进行拍照
	 * @return 摄像头8是否进行拍照
	 */
	public Boolean getTakeForCAM8() {
		return takeForCAM8;
	}

	/**
	 * 设置摄像头8是否进行拍照
	 * @param takeForCAM8 摄像头8是否进行拍照
	 */
	public void setTakeForCAM8(Boolean takeForCAM8) {
		this.takeForCAM8 = takeForCAM8;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"TakePictureInfo"};
	}

	/**
	 * 根据id来删除规则信息
	 * @param id 规则id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.TakePictureInfo _info where _info.id=:id";
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
}
