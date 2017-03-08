package com.apical.dmcloud.middle.infra;

import java.io.Serializable;
import java.util.List;

import javax.persistence.MappedSuperclass;

import com.apical.dmcloud.middle.infra.jpa.EntityRepositoryJpa;

/**
 * 抽象实体类，可作为所有领域实体的基类。
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Entity
{
	/**
	 * serialVersionUID = 4739212873424823980L
	 */
	private static final long serialVersionUID = 4739212873424823980L;
	
	/**
	 * 实体仓储访问接口
	 */
	private static EntityRepository repository;
	
	@Override
	public boolean existed()
    {
		Object id = getId();
		if (id == null)
		{
			return false;
		}
		
		if (id instanceof Number && ((Number)id).intValue() == 0)
		{
			return false;
		}
		
		return getRepository().exists(getClass(), getId());
    }
	
	@Override
	public boolean notExisted()
	{
		return !existed();
	}
	
	/**
	 * 将实体本身持久化到数据库
	 */
	public void save()
	{
		getRepository().save(this);
	}
	
	/**
	 * 将实体重新更新到数据库
	 */
	public void update()
	{
		getRepository().update(this);
	}

	/**
	 * 将实体本身从数据库中删除
	 */
	public void remove()
	{
		getRepository().remove(this);
	}
	
	/**
     * 获取业务主键。业务主键是判断相同类型的两个实体在业务上的等价性的依据。如果相同类型的两个
     * 实体的业务主键相同，则认为两个实体是相同的，代表同一个实体。
     * <p>业务主键由实体的一个或多个属性组成。
     * @return 组成业务主键的属性的数组。
     */
	public abstract String[] businessKeys();
	
	/**
	 * 获取实体仓储访问接口
	 * @return 实体仓储访问接口
	 */
	public static EntityRepository getRepository()
	{
		if (repository == null)
		{
			synchronized(EntityRepository.class)
			{
				if(repository == null)
				{
					repository = new EntityRepositoryJpa();
				}
			}
		}
		return repository;
	}
	
	/**
	 * 设置实体仓储访问接口
	 * @param entityRepository
	 */
	public static void setRepository(EntityRepository entityRepository)
	{
		repository = entityRepository;
	}
	
	/**
     * 根据实体类型和ID从仓储中获取实体
     * @param <T> 实体类型
     * @param clazz 实体的类
     * @param id 实体的ID
     * @return 类型为T或T的子类型，ID为id的实体。
     */
	public static <T extends Entity> T get(Class<T> clazz, Serializable id)
	{
		return getRepository().get(clazz, id);
	}
	
	/**
     * 查找实体在数据库中的未修改版本
     * @param <T> 实体类型
     * @param clazz 实体的类
     * @param entity  实体
     * @return 实体的未修改版本。
     */
	public static <T extends Entity> T getUnmodified(Class<T> clazz, T entity)
	{
		return getRepository().getUnmodified(clazz, entity);
	}
	
	/**
     * 根据实体类型和ID从仓储中加载实体对象代理
     * 与get()方法的区别在于除id外所有的属性值都未填充
     * @param <T> 实体类型
     * @param clazz 实体的类
     * @param id 实体的ID
     * @return 类型为T或T的子类型，ID为id的实体。
     */
	public static <T extends Entity> T load(Class<T> clazz, Serializable id)
	{
		return getRepository().load(clazz, id);
	}
	
	/**
     * 查找指定类型的所有实体
     * @param <T> 实体所属的类型
     * @param clazz 实体所属的类
     * @return 符合条件的实体列表
     */
	public static <T extends Entity> List<T> findAll(Class<T> clazz)
	{
		return getRepository().findAll(clazz);
	}
}
