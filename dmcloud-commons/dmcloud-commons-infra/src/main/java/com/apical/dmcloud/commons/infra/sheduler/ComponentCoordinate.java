/**
 * @(#) ComponentCoordinate.java 2013-6-24
 * All rights reserved by SFC365
 */
package com.apical.dmcloud.commons.infra.sheduler;

/**
 * @author Administrator
 * 
 */
public class ComponentCoordinate
{
	private final String groupId;
	private final String artifactId;
	private final String version;
	private final String classifier;
	private final long timestamp;
	private final static String SEPARATOR = "/";

	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 * @param classifier
	 */
	public ComponentCoordinate(String groupId, String artifactId,
			String version, String classifier)
	{
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.classifier = classifier;
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId()
	{
		return groupId;
	}

	/**
	 * @return the artifactId
	 */
	public String getArtifactId()
	{
		return artifactId;
	}

	/**
	 * @return the version
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * @return the classifier
	 */
	public String getClassifier()
	{
		return classifier;
	}

	public String toString()
	{
		String s = groupId + SEPARATOR + artifactId + SEPARATOR;

		if (!classifier.isEmpty())
		{
			s += classifier;
			s += SEPARATOR;
		}

		s += version;
		s += Long.toString(timestamp);
		return s;
	}
}
