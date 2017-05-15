package com.apical.dmcloud;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractIntegerIDEntity extends AbstractEntity
{
	
	private static final long serialVersionUID = -548584827115512206L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * 获取资源的行健值
	 * @return Long 资源行健值
	 */
	@Override
	public Integer getId()
	{
		return id;
	}
	
	@Transient
	public boolean isNew()
	{
		return id == null || id.intValue() == 0;
	}
}
