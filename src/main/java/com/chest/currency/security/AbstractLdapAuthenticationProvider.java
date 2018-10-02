package com.chest.currency.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractLdapAuthenticationProvider
		implements AuthenticationProvider, MessageSourceAware {
	private static final Logger logger = LoggerFactory.getLogger(AbstractLdapAuthenticationProvider.class);
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private boolean useAuthenticationRequestCredentials = true;
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	
	@Autowired
	@Qualifier("aDUserDetailsContextMapper")
	protected UserDetailsContextMapper userDetailsContextMapper;
	
	@Autowired
	@Qualifier("userDetailsService")
	protected UserDetailsService userDetailsService;

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				this.messages.getMessage("LdapAuthenticationProvider.onlySupports",
						"Only UsernamePasswordAuthenticationToken is supported"));

		final UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;

		String username = userToken.getName();
		String password = (String) authentication.getCredentials();
		logger.info("Authentication userToken ......"+userToken);
		logger.info("Authentication userToken.getName() ......"+userToken.getName());
		if (logger.isDebugEnabled()) {
			logger.debug("Processing authentication request for user: " + username);
		}

		if (!StringUtils.hasLength(username)) {
			throw new BadCredentialsException(this.messages.getMessage(
					"LdapAuthenticationProvider.emptyUsername", "Empty Username"));
		}

		if (!StringUtils.hasLength(password)) {
			throw new BadCredentialsException(this.messages.getMessage(
					"AbstractLdapAuthenticationProvider.emptyPassword",
					"Empty Password"));
		}

		Assert.notNull(password, "Null password was supplied in authentication token");

		DirContextOperations userData = doAuthentication(userToken);
		UserDetails userDB = userDetailsService.loadUserByUsername(username);
		if(userDB == null){
			logger.error("No user found: " + username);
			throw new BadCredentialsException("No user found: " + username);
		}
		UserDetails user = this.userDetailsContextMapper.mapUserFromContext(userData, authentication.getName(), userDB.getAuthorities());
		return createSuccessfulAuthentication(userToken, user);
	}

	protected abstract DirContextOperations doAuthentication(
			UsernamePasswordAuthenticationToken auth);

	protected abstract Collection<? extends GrantedAuthority> loadUserAuthorities(
			DirContextOperations userData, String username, String password);

	/**
	 * Creates the final {@code Authentication} object which will be returned from the
	 * {@code authenticate} method.
	 *
	 * @param authentication the original authentication request token
	 * @param user the <tt>UserDetails</tt> instance returned by the configured
	 * <tt>UserDetailsContextMapper</tt>.
	 * @return the Authentication object for the fully authenticated user.
	 */
	protected Authentication createSuccessfulAuthentication(
			UsernamePasswordAuthenticationToken authentication, UserDetails user) {
		Object password = this.useAuthenticationRequestCredentials
				? authentication.getCredentials() : user.getPassword();

		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				user, password,
				this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());

		return result;
	}

	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * Determines whether the supplied password will be used as the credentials in the
	 * successful authentication token. If set to false, then the password will be
	 * obtained from the UserDetails object created by the configured
	 * {@code UserDetailsContextMapper}. Often it will not be possible to read the
	 * password from the directory, so defaults to true.
	 *
	 * @param useAuthenticationRequestCredentials
	 */
	public void setUseAuthenticationRequestCredentials(
			boolean useAuthenticationRequestCredentials) {
		this.useAuthenticationRequestCredentials = useAuthenticationRequestCredentials;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
		this.authoritiesMapper = authoritiesMapper;
	}

	/**
	 * Allows a custom strategy to be used for creating the <tt>UserDetails</tt> which
	 * will be stored as the principal in the <tt>Authentication</tt> returned by the
	 * {@link #createSuccessfulAuthentication(org.springframework.security.authentication.UsernamePasswordAuthenticationToken, org.springframework.security.core.userdetails.UserDetails)}
	 * method.
	 *
	 * @param userDetailsContextMapper the strategy instance. If not set, defaults to a
	 * simple <tt>LdapUserDetailsMapper</tt>.
	 */
	public void setUserDetailsContextMapper(
			UserDetailsContextMapper userDetailsContextMapper) {
		Assert.notNull(userDetailsContextMapper,
				"UserDetailsContextMapper must not be null");
		this.userDetailsContextMapper = userDetailsContextMapper;
	}

	/**
	 * Provides access to the injected {@code UserDetailsContextMapper} strategy for use
	 * by subclasses.
	 */
	protected UserDetailsContextMapper getUserDetailsContextMapper() {
		return this.userDetailsContextMapper;
	}
}
