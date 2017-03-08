package com.apical.dmcloud.middle.infra.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.apical.dmcloud.middle.infra.BaseQuery;
import com.apical.dmcloud.middle.infra.Entity;
import com.apical.dmcloud.middle.infra.EntityManagerHelper;
import com.apical.dmcloud.middle.infra.EntityRepository;
import com.apical.dmcloud.middle.infra.JpqlQuery;
import com.apical.dmcloud.middle.infra.NamedParameters;
import com.apical.dmcloud.middle.infra.NamedQuery;
import com.apical.dmcloud.middle.infra.SqlQuery;

public class EntityRepositoryJpa implements EntityRepository
{
	@Override
	public <T extends Entity> T save(T entity)
	{
		// TODO Auto-generated method stub
		if ((!existed(entity)) || (entity.notExisted()))
		{
			getEntityManager().persist(entity);
			return entity;
		}
		entity = getEntityManager().merge(entity);
		return entity;
	}
	
	@Override
	public <T extends Entity> T update(T entity)
	{
		if(existed(entity))
		{
			entity = getEntityManager().merge(entity);
		}
		return entity;
	}

	@Override
	public void remove(Entity paramEntity)
	{
		// TODO Auto-generated method stub
		getEntityManager().remove(get(paramEntity.getClass(), paramEntity.getId()));
	}
	
	public <T extends Entity> boolean existed(Entity paramEntity)
	{
		Object id = paramEntity.getId();
		if (id == null)
		{
			return false;
		}
		if (((id instanceof Number)) && (((Number)id).intValue() == 0))
		{
			return false;
		}
		return true;
	}

	@Override
	public <T extends Entity> boolean exists(Class<T> paramClass,
			Serializable id)
	{
		// TODO Auto-generated method stub
		Entity entity = (Entity)getEntityManager().find(paramClass, id);
		return entity != null;
	}

	@Override
	public <T extends Entity> T get(Class<T> paramClass, Serializable id)
	{
		// TODO Auto-generated method stub
		return (T)getEntityManager().find(paramClass, id);
	}

	@Override
	public <T extends Entity> T load(Class<T> paramClass, Serializable id)
	{
		// TODO Auto-generated method stub
		return (T)getEntityManager().getReference(paramClass, id);
	}

	@Override
	public <T extends Entity> T getUnmodified(Class<T> paramClass, T entity)
	{
		// TODO Auto-generated method stub
		getEntityManager().detach(entity);
		return get(paramClass, entity.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> List<T> findAll(Class<T> paramClass)
	{
		// TODO Auto-generated method stub
		String queryString = "select o from " + paramClass.getName() + " as o";
		return getEntityManager().createQuery(queryString).getResultList();
	}

	@Override
	public JpqlQuery createJpqlQuery(String jpql)
	{
		// TODO Auto-generated method stub
		return new JpqlQuery(this, jpql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> find(JpqlQuery paramQuery)
	{
		// TODO Auto-generated method stub
		return getQuery(paramQuery).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSingleResult(JpqlQuery paramQuery)
	{
		// TODO Auto-generated method stub
		List<T> results = getQuery(paramQuery).getResultList();
		if(results.isEmpty())
		{
			return null;
		}
		else
		{
			return results.get(0);
		}
	}

	@Override
	public int executeUpdate(JpqlQuery paramQuery)
	{
		// TODO Auto-generated method stub
		return getQuery(paramQuery).executeUpdate();
	}
	
	private Query getQuery(JpqlQuery jpqlQuery)
	{
		Query query = getEntityManager().createQuery(jpqlQuery.getJpql());
		processQuery(query, jpqlQuery);
		return query;
	}

	@Override
	public NamedQuery createNamedQuery(String queryName)
	{
		// TODO Auto-generated method stub
		return new NamedQuery(this, queryName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> find(NamedQuery paramQuery)
	{
		// TODO Auto-generated method stub
		return getQuery(paramQuery).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSingleResult(NamedQuery paramQuery)
	{
		// TODO Auto-generated method stub
		List<T> results = getQuery(paramQuery).getResultList();
		if(results.isEmpty())
		{
			return null;
		}
		else
		{
			return results.get(0);
		}
	}

	@Override
	public int executeUpdate(NamedQuery paramQuery)
	{
		// TODO Auto-generated method stub
		return getQuery(paramQuery).executeUpdate();
	}
	
	private Query getQuery(NamedQuery namedQuery)
	{
		Query query = getEntityManager().createNamedQuery(namedQuery.getQueryName());
		processQuery(query, namedQuery);
		return query;
	}

	@Override
	public SqlQuery createSqlQuery(String sql)
	{
		// TODO Auto-generated method stub
		return new SqlQuery(this, sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> find(SqlQuery paramQuery)
	{
		// TODO Auto-generated method stub
		return getQuery(paramQuery).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSingleResult(SqlQuery paramQuery)
	{
		// TODO Auto-generated method stub
		List<T> results = getQuery(paramQuery).getResultList();
		if(results.isEmpty())
		{
			return null;
		}
		else
		{
			return results.get(0);
		}
	}

	@Override
	public int executeUpdate(SqlQuery paramQuery)
	{
		// TODO Auto-generated method stub
		return getQuery(paramQuery).executeUpdate();
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private Query getQuery(SqlQuery sqlQuery)
	{
		Query query;
		if (sqlQuery.getResultEntityClass() == null)
		  query = getEntityManager().createNativeQuery(sqlQuery.getSql());
		else {
		  query = getEntityManager().createNativeQuery(sqlQuery.getSql(), sqlQuery.getResultEntityClass());
		}
		
		processQuery(query, sqlQuery);
		Class resultEntityClass = sqlQuery.getResultEntityClass();
		return query;
	}
	
	@SuppressWarnings("rawtypes")
	private void processQuery(Query query, BaseQuery originQuery)
	{
		fillParameters(query, originQuery.getParameters());
		query.setFirstResult(originQuery.getFirstResult());
		if (originQuery.getMaxResults() > 0)
		{
			query.setMaxResults(originQuery.getMaxResults());
		}
	}

	private void fillParameters(Query query, NamedParameters params)
	{
		for (Map.Entry<String, Object> each : params.getParams().entrySet())
		{
			query = query.setParameter(each.getKey(), each.getValue());
		}
	}

	@Override
	public void flush()
	{
		// TODO Auto-generated method stub
		getEntityManager().flush();
	}

	@Override
	public void refresh(Entity paramEntity)
	{
		// TODO Auto-generated method stub
		getEntityManager().refresh(paramEntity);
	}

	@Override
	public void clear()
	{
		// TODO Auto-generated method stub
		getEntityManager().clear();
	}

	@Override
	public EntityManager getEntityManager()
	{
		// TODO Auto-generated method stub
		return EntityManagerHelper.getEntityManager();
	}
}
