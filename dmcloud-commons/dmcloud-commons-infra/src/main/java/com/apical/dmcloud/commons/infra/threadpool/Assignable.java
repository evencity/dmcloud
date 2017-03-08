/**
 * @(#) Assignable.java 2013-1-11
 */
package com.apical.dmcloud.commons.infra.threadpool;

/**
 * Assignable.java
 * 
 * @author mclaren
 * 
 */
public interface Assignable<T>
{
	public void assign(T service);
}
