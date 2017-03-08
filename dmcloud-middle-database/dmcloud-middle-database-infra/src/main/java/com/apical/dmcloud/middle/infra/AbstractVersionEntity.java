package com.apical.dmcloud.middle.infra;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractVersionEntity extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Version
	@Column(name = "VERSION")
	private int version;
	
	/**
	 * 获取资源的行健值
	 * @return Long 资源行健值
	 */
	@Override
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	@Transient
	public boolean isNew()
	{
		return id == null || id.intValue() == 0;
	}
	
	/**
	 * 获得实体的版本号。持久化框架以此实现乐观锁。
	 *
	 * @return 实体的版本号
	 */
	public int getVersion()
	{
		return version;
	}

	/**
	 * 设置实体的版本号。持久化框架以此实现乐观锁。
	 *
	 * @param version 要设置的版本号
	 */
	public void setVersion(int version)
	{
		this.version = version;
	}
}
