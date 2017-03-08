package com.apical.dmcloud.middle.infra;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper
{
	private static final EntityManagerFactory emf;
	private static final ThreadLocal<EntityManager> threadLocal;
	
	static
	{
		emf = Persistence.createEntityManagerFactory("CloudJPA");
		threadLocal = new ThreadLocal<EntityManager>();
	}
	
	/**
	 * 获取实体管理器
	 * @return 实体管理器
	 */
	public static EntityManager getEntityManager()
	{
		EntityManager manager = threadLocal.get();	
		if (manager == null || !manager.isOpen())
		{
			manager = emf.createEntityManager();
			threadLocal.set(manager);
		}
		return manager;
	}
	
	/**
	 * 关闭实体管理器
	 */
	public static void closeEntityManager()
	{
		EntityManager em = threadLocal.get();
		threadLocal.set(null);
		if (em != null)
		{
			em.close();
		}
    }
    
	/**
	 * 请求事务
	 */
	public static void beginTransaction()
	{
		getEntityManager().getTransaction().begin();
	}
	
	/**
	 * 提交事务
	 */
	public static void commitTransaction()
	{
		getEntityManager().getTransaction().commit();
	}
	
	/**
	 * 回滚事务
	 */
	public static void rollbackTransaction()
	{
		getEntityManager().getTransaction().rollback();
	}
}
