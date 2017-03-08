package com.apical.dmcloud.security.core.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dayatang.utils.Assert;
import org.openkoala.security.core.AuthorizationIsNotExisted;

import com.apical.dmcloud.AbstractIDEntity;

/**
 * 授权中心，在指定范围将授权授予参与者
 *
 * @author lucas
*/

@Entity
@Table(name = "ks_authorizations")
public class Authorization extends AbstractIDEntity {

	private static final long serialVersionUID = -7604610067031217444L;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ACTOR_ID")
	private User user;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "AUTHORITY_ID")
	private Authority authority;

	protected Authorization() {
	}

	public Authorization(User user, Authority authority) {
		Assert.notNull(user, "actor cannot be empty.");
		Assert.notNull(authority, "authority cannot be empty.");
		this.user = user;
		this.authority = authority;
	}

	/**
	 * 保存授权中心。
	 * 如果存在就直接返回。
	*/
	@Override
	public void save() {
		if (checkAuthorization(user.getId(), authority.getId())) {
			return;
		}
		super.save();
	}

	/**
	 * 更改授权中心的范围。
	 * @param user 用户信息
	*/
	public static List<Authorization> findByUser(User user) {
		return getRepository().createCriteriaQuery(Authorization.class)
				.eq("user", user)
				.list();
	}
	
	/**
	 * 获取参与者的授权中心
	 * @param userId 用户id
	 * @return 授权中心
	*/
	public static List<Authorization> findByUserId(long userId) {
		String jpql = "SELECT _authorization FROM Authorization _authorization WHERE"
				+ " _authorization.user.id = :userId";
		return getRepository().createJpqlQuery(jpql)
				.addParameter("userId", userId)
				.list();
	}

	/**
	 * 获取参与者的授权中心
	 * @param authority 授权信息
	 * @return 授权中心
	*/
	public static List<Authorization> findByAuthority(Authority authority) {
		return getRepository().createCriteriaQuery(Authorization.class)
				.eq("authority", authority)
				.list();
	}
	
	/**
	 * 获取授权信息的授权中心
	 * @param authorityId 授权信息id
	 * @return 授权中心
	*/
	public static List<Authorization> findByAuthorityId(long authorityId) {
		String query = "SELECT _authorization FROM Authorization _authorization WHERE"
				+ " _authorization.authority.id = :authorityId";
		return getRepository().createJpqlQuery(query)
				.addParameter("authorityId", authorityId)
				.list();
	}

	/**
	 * 查找用户的授权信息
	 * @param actor 用户信息
	 * @return 授权信息
	 */
	public static Set<Authority> findAuthoritiesByActor(User actor) {
		Set<Authority> results = new HashSet<Authority>();
		Set<Authorization> authorizations = findAuthorizationsByUser(actor);
		for (Authorization authorization : authorizations) {
			if (authorization.getAuthority() instanceof Role) {
				Role role = (Role) authorization.getAuthority();
				results.addAll(role.getPermissions());
			}
			results.add(authorization.getAuthority());
		}
		return results;
	}

	/**
	 * 获取授权信息的授权中心
	 * @param actor 用户信息
	 * @param authority 授权信息
	 * @return 授权中心
	*/
	public static Authorization findByActorInAuthority(User actor, Authority authority) {
		return getRepository().createCriteriaQuery(Authorization.class)
				.eq("actor", actor)
				.eq("authority", authority)
				.singleResult();
	}

	/**
	 * 是否已对用户授权
	 * @param user 用户
	 * @param authority 权限
	 */
	public static void checkAuthorization(User user, Authority authority) {
		if (!checkAuthorization(user.getId(), authority.getId())) {
			throw new AuthorizationIsNotExisted();
		}
	}
	
	/**
	 * 是否已对用户授权
	 * @param userId 用户id
	 * @param authorityId 权限id
	 * @return 是否已授权
	 */
	public static boolean checkAuthorization(long userId, long authorityId) {
		String jpql = "select 1 from Authorization _authorization where _authorization.user.id = :userId"
				+ " and _authorization.authority.id = :authorityId";
		Integer result = getRepository().createJpqlQuery(jpql)
				.addParameter("userId", userId)
				.addParameter("authorityId", authorityId)
				.singleResult();
		if(result != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 取消对用户的某个权限
	 * @param userId 用户id
	 * @param authorityId 权限信息
	 * @return 取消是否成功
	 */
	public static boolean terminateAuthorization(long userId, long authorityId) {
		String jpql = "delete from Authorization _authorization where _authorization.user.id = :userId"
				+ " and _authorization.authority.id = :authorityId";
		int count = getRepository().createJpqlQuery(jpql)
				.addParameter("userId", userId)
				.addParameter("authorityId", authorityId)
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

	private static Set<Authorization> findAuthorizationsByUser(User user) {
		Set<Authorization> results = new HashSet<Authorization>();
		List<Authorization> authorizations = getRepository().createCriteriaQuery(Authorization.class)
				.eq("user", user)
				.list();
		results.addAll(authorizations);
		return results;
	}

	@Override
	public String[] businessKeys() {
		return new String[]{"actor", "authority"};
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(user)
				.append(authority)
				.build();
	}

	public User getUser() {
		return user;
	}

	public Authority getAuthority() {
		return authority;
	}
}
