package com.apical.dmcloud;

import java.io.Serializable;
import java.util.List;

import javax.persistence.MappedSuperclass;

import org.dayatang.domain.Entity;
import org.openkoala.koala.commons.domain.KoalaBaseEntity;

@MappedSuperclass
public abstract class AbstractEntity extends KoalaBaseEntity
{
	/**
	 * serialVersionUID = -7786417380929843783L
	 */
	private static final long serialVersionUID = -7786417380929843783L;
	
	public void save()
	{
		getRepository().save(this);
	}

	public void remove()
	{
		getRepository().remove(this);
	}
	
	public static <T extends Entity> T get(Class<T> clazz, Serializable id)
	{
		return getRepository().get(clazz, id);
	}
	
	public static <T extends Entity> T getUnmodified(Class<T> clazz, T entity)
	{
		return getRepository().getUnmodified(clazz, entity);
	}
	
	public static <T extends Entity> T load(Class<T> clazz, Serializable id)
	{
		return getRepository().load(clazz, id);
	}
	
	public static <T extends Entity> List<T> findAll(Class<T> clazz)
	{
		return getRepository().findAll(clazz);
	}
}
