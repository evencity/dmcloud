package com.apical.dmcloud;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractIDEntity extends AbstractEntity
{
	/**
	 * serialVersionUID = 5930870265086380437L
	 */
	private static final long serialVersionUID = 5930870265086380437L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	/**
	 * 获取资源的行健值
	 * @return Long 资源行健值
	 */
	@Override
	public Long getId()
	{
		return id;
	}
	
	@Transient
	public boolean isNew()
	{
		return id == null || id.intValue() == 0;
	}
}
