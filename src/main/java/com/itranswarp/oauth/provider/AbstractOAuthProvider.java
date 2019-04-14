package com.itranswarp.oauth.provider;

import java.net.http.HttpClient;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itranswarp.oauth.OAuthAuthentication;

public abstract class AbstractOAuthProvider {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

	/**
	 * Get lower-case provider id as unique identity.
	 */
	public final String getProviderId() {
		String className = getClass().getSimpleName();
		if (className.endsWith("OAuthProvider")) {
			return className.substring(0, className.length() - "OAuthProvider".length()).toLowerCase();
		}
		throw new IllegalArgumentException("Could not get provider name from class name: " + className);
	}

	public abstract AbstractOAuthConfiguration getOAuthConfiguration();

	public abstract String getAuthenticateUrl(String redirectUrl);

	public abstract OAuthAuthentication getAuthentication(String code, String redirectUrl) throws Exception;

	protected final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(DEFAULT_TIMEOUT)
			.followRedirects(HttpClient.Redirect.NEVER).build();
}