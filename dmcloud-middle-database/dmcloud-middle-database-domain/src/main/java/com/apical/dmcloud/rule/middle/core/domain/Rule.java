package com.apical.dmcloud.rule.middle.core.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.apical.dmcloud.middle.infra.AbstractVersionEntity;

/**
 * 规则表
 * @author qiuzeng
 *
 */

@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Rule extends AbstractVersionEntity
{
	private static final long serialVersionUID = 1L;

	/**
	 * 规则名称
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 规则描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * 规则所属公司id
	 */
	@Column(name = "COM_ID")
	private Long companyId;
	
	/**
	 * 创建用户id
	 */
	@Column(name = "USER_ID")
	private Long userId;
	
	/**
	 * 拍照信息表
	 */
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "TAKEPIC_ID")
	private TakePictureInfo takePictureInfo;
	
	/**
	 * 录像信息表
	 */
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "TAKEVID_ID")
	private TakeVideoInfo takeVideoInfo;
	
	/**
	 * 报警语音
	 */
	@Column(name = "VOICE")
	private String voice;
	
	/**
	 * 电话列表，电话直接用';'分割
	 */
	@Column(name = "PHONES")
	private String phones;
	
	/**
	 * 电子邮箱列表，电子邮箱用';'分割
	 */
	@Column(name = "MAILS")
	private String mails;
	
	public Rule()
	{
	}

	/**
	 * 获取规则名称
	 * @return 规则名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置规则名称
	 * @param name 规则名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取规则描述
	 * @return 规则描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置规则描述
	 * @param description 规则描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取所属公司id
	 * @return 所属公司id
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * 设置所属公司id
	 * @param companyId 所属公司id
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * 获取创建用户id
	 * @return 创建用户id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置创建用户id
	 * @param userId 创建用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取拍照信息
	 * @return 拍照信息
	 */
	public TakePictureInfo getTakePictureInfo() {
		return takePictureInfo;
	}

	/**
	 * 设置拍照信息
	 * @param takePictureInfo 拍照信息
	 */
	public void setTakePictureInfo(TakePictureInfo takePictureInfo) {
		this.takePictureInfo = takePictureInfo;
	}

	/**
	 * 获取录像信息
	 * @return 录像信息
	 */
	public TakeVideoInfo getTakeVideoInfo() {
		return takeVideoInfo;
	}

	/**
	 * 设置录像信息
	 * @param takeVideoInfo 录像信息
	 */
	public void setTakeVideoInfo(TakeVideoInfo takeVideoInfo) {
		this.takeVideoInfo = takeVideoInfo;
	}

	/**
	 * 获取报警语音
	 * @return 报警语音
	 */
	public String getVoice() {
		return voice;
	}

	/**
	 * 设置报警语音
	 * @param voice 报警语音
	 */
	public void setVoice(String voice) {
		this.voice = voice;
	}

	/**
	 * 获取电话列表，电话直接用';'分割
	 * @return 电话列表
	 */
	public String getPhones() {
		return phones;
	}

	/**
	 * 设置电话列表，电话直接用';'分割
	 * @param phones 电话列表
	 */
	public void setPhones(String phones) {
		this.phones = phones;
	}

	/**
	 * 获取电子邮箱列表，电子邮箱用';'分割
	 * @return 电子邮箱列表
	 */
	public String getMails() {
		return mails;
	}

	/**
	 * 设置电子邮箱列表，电子邮箱用';'分割
	 * @param mails 电子邮箱列表
	 */
	public void setMails(String mails) {
		this.mails = mails;
	}
}
