package com.apical.dmcloud.middle.infra;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

public interface EntityRepository
{
	/**
     * 将实体（无论是新的还是修改了的）保存到仓储中。
     *
     * @param <T> 实体的类型
     * @param paramT 要存储的实体实例。
     * @return 持久化后的当前实体
     */
	public abstract <T extends Entity> T save(T paramT);
	
	/**
     * 将实体（无论是新的还是修改了的）更新到仓储中。
     *
     * @param <T> 实体的类型
     * @param paramT 要存储的实体实例。
     * @return 持久化后的当前实体
     */
	public abstract <T extends Entity> T update(T paramT);
	
	/**
     * 将实体从仓储中删除。如果仓储中不存在此实例将抛出EntityNotExistedException异常。
     *
     * @param paramEntity 要删除的实体实例。
     */
	public abstract void remove(Entity paramEntity);
	
	/**
     * 判断仓储中是否存在指定的实体实例。
     *
     * @param <T> 实体类型
     * @param paramEntity 实体实例
     * @return 如果实体实例存在，返回true，否则返回false
     */
	public abstract <T extends Entity> boolean existed(Entity paramEntity);
	
	/**
     * 判断仓储中是否存在指定ID的实体实例。
     *
     * @param <T> 实体类型
     * @param paramClass 实体的类
     * @param paramSerializable 实体标识
     * @return 如果实体实例存在，返回true，否则返回false
     */
	public abstract <T extends Entity> boolean exists(Class<T> paramClass, Serializable paramSerializable);
	
	/**
     * 从仓储中获取指定类型、指定ID的实体
     *
     * @param <T> 实体类型
     * @param paramClass 实体的类
     * @param paramSerializable 实体标识
     * @return 一个实体实例。
     */
	public abstract <T extends Entity> T get(Class<T> paramClass, Serializable paramSerializable);
	
	/**
     * 从仓储中装载指定类型、指定ID的实体。
     * 调用了实体管理器的getReference()，因此，该实体的只是一个引用，除了id字段，
     * 其他字段的值均为空，并未填充实际的值。
     *
     * @param <T> 实体类型
     * @param paramClass 实体的类
     * @param paramSerializable 实体标识
     * @return 一个实体实例。
     */
	public abstract <T extends Entity> T load(Class<T> paramClass, Serializable paramSerializable);
	
	/**
     * 从仓储中获取entity参数所代表的未修改的实体
     *
     * @param <T> 实体类型
     * @param paramClass 实体的类
     * @param paramT 要查询的实体
     * @return 参数entity在仓储中的未修改版本
     */
	public abstract <T extends Entity> T getUnmodified(Class<T> paramClass, T paramT);
	
	/**
     * 查找指定类型的所有实体
     *
     * @param <T> 实体类型
     * @param paramClass 实体的类
     * @return 符合条件的实体集合
     */
	public abstract <T extends Entity> List<T> findAll(Class<T> paramClass);
	
	/**
     * 执行更新仓储的操作。
     *
     * @param jpql 要执行的JPQL查询。
     * @return 被更新或删除的实体的数量
     */
	public abstract JpqlQuery createJpqlQuery(String jpql);
	
	/**
     * 执行SQL查询，返回符合条件的实体列表
     *
     * @param paramQuery 要执行的SQL查询。
     * @param <T> 返回结果元素类型
     * @return 符合查询条件的结果列表
     */
	public abstract <T> List<T> find(JpqlQuery paramQuery);
	
	/**
     * 执行SQL查询，返回符合条件的单个实体
     *
     * @param paramQuery 要执行的SQL查询。
     * @param <T> 返回结果类型
     * @return 符合查询条件的单个结果
     */
	public abstract <T> T getSingleResult(JpqlQuery paramQuery);
	
	/**
     * 使用SQL查询执行更新仓储的操作。
     *
     * @param paramQuery 要执行的SQL查询。
     * @return 被更新或删除的实体的数量
     */
	public abstract int executeUpdate(JpqlQuery paramQuery);
	
	/**
     * 创建命名查询
     *
     * @param queryName 命名查询的名字
     * @return 一个命名查询
     */
	public abstract NamedQuery createNamedQuery(String queryName);
	
	/**
     * 执行SQL查询，返回符合条件的实体列表
     *
     * @param paramQuery 要执行的SQL查询。
     * @param <T> 返回结果元素类型
     * @return 符合查询条件的结果列表
     */
	public abstract <T> List<T> find(NamedQuery paramQuery);
	
	/**
     * 执行SQL查询，返回符合条件的单个实体
     *
     * @param paramQuery 要执行的SQL查询。
     * @param <T> 返回结果类型
     * @return 符合查询条件的单个结果
     */
	public abstract <T> T getSingleResult(NamedQuery paramQuery);
	
	/**
     * 使用SQL查询执行更新仓储的操作。
     *
     * @param paramQuery 要执行的SQL查询。
     * @return 被更新或删除的实体的数量
     */
	public abstract int executeUpdate(NamedQuery paramQuery);
	
	/**
     * 创建原生SQL查询
     *
     * @param sql SQL语句
     * @return 一个原生SQL查询
     */
	public abstract SqlQuery createSqlQuery(String sql);
	
	/**
     * 执行SQL查询，返回符合条件的实体列表
     *
     * @param paramQuery 要执行的SQL查询。
     * @param <T> 返回结果元素类型
     * @return 符合查询条件的结果列表
     */
	public abstract <T> List<T> find(SqlQuery paramQuery);
	
	/**
     * 执行SQL查询，返回符合条件的单个实体
     *
     * @param paramQuery 要执行的SQL查询。
     * @param <T> 返回结果类型
     * @return 符合查询条件的单个结果
     */
	public abstract <T> T getSingleResult(SqlQuery paramQuery);
	
	/**
     * 使用SQL查询执行更新仓储的操作。
     *
     * @param paramQuery 要执行的SQL查询。
     * @return 被更新或删除的实体的数量
     */
	public abstract int executeUpdate(SqlQuery paramQuery);
	
	/**
     * 将内存中的持久化对象状态即时写入数据库
     */
	public abstract void flush();
	
	/**
     * 使用数据库中的最新数据更新实体的当前状态。实体中的任何已改变但未持久化的属性值将被数据库中的最新值覆盖。
     *
     * @param paramEntity 要刷新的实体
     */
	public abstract void refresh(Entity paramEntity);
	
	/**
     * 清空持久化缓存
     */
	public abstract void clear();
	
	/**
	 * 获取实体管理器
	 * @return 实体管理器
	 */
	public EntityManager getEntityManager();
}
