/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.security;

import java.net.ConnectException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Specialized LDAP authentication provider which uses Active Directory configuration
 * conventions.
 * <p>
 * It will authenticate using the Active Directory <a
 * href="http://msdn.microsoft.com/en-us/library/ms680857%28VS.85%29.aspx">
 * {@code userPrincipalName}</a> or a custom {@link #setSearchFilter(String) searchFilter}
 * in the form {@code username@domain}. If the username does not already end with the
 * domain name, the {@code userPrincipalName} will be built by appending the configured
 * domain name to the username supplied in the authentication request. If no domain name
 * is configured, it is assumed that the username will always contain the domain name.
 * <p>
 * The user authorities are obtained from the data contained in the {@code memberOf}
 * attribute.
 *
 * <h3>Active Directory Sub-Error Codes</h3>
 *
 * When an authentication fails, resulting in a standard LDAP 49 error code, Active
 * Directory also supplies its own sub-error codes within the error message. These will be
 * used to provide additional log information on why an authentication has failed. Typical
 * examples are
 *
 * <ul>
 * <li>525 - user not found</li>
 * <li>52e - invalid credentials</li>
 * <li>530 - not permitted to logon at this time</li>
 * <li>532 - password expired</li>
 * <li>533 - account disabled</li>
 * <li>701 - account expired</li>
 * <li>773 - user must reset password</li>
 * <li>775 - account locked</li>
 * </ul>
 *
 * If you set the {@link #setConvertSubErrorCodesToExceptions(boolean)
 * convertSubErrorCodesToExceptions} property to {@code true}, the codes will also be used
 * to control the exception raised.
 *
 */
public final class ADLoginAuthenticationProvider extends
AbstractLdapAuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(ADLoginAuthenticationProvider.class);
	
	private static final Pattern SUB_ERROR_CODE = Pattern
			.compile(".*data\\s([0-9a-f]{3,4}).*");

	// Error codes
	private static final int USERNAME_NOT_FOUND = 0x525;
	private static final int INVALID_PASSWORD = 0x52e;
	private static final int NOT_PERMITTED = 0x530;
	private static final int PASSWORD_EXPIRED = 0x532;
	private static final int ACCOUNT_DISABLED = 0x533;
	private static final int ACCOUNT_EXPIRED = 0x701;
	private static final int PASSWORD_NEEDS_RESET = 0x773;
	private static final int ACCOUNT_LOCKED = 0x775;

	private final String domain;
	@SuppressWarnings("unused")
	private final String rootDn;
	private final String url;
	private boolean convertSubErrorCodesToExceptions;
	//private String searchFilter = "(&(objectClass=user)(userPrincipalName={0}))";

	// Only used to allow tests to substitute a mock LdapContext
	ContextFactory contextFactory = new ContextFactory();
	
	private static int INDVAL = 0;

	private static String INITCTX = "";

	static String ret_s5 = "";

	private static String URL = "";

	@SuppressWarnings("unused")
	private static String[] URLARR = { "" };

	@SuppressWarnings("unused")
	private static String USRPRNCPL = "";
	
	String ret_mesg = "Welcome ";
	
	/**
	 * @param domain the domain name (may be null or empty)
	 * @param url an LDAP url (or multiple URLs)
	 * @param rootDn the root DN (may be null or empty)
	 */
	public ADLoginAuthenticationProvider(String domain, String url,
			String rootDn) {
		Assert.isTrue(StringUtils.hasText(url), "Url cannot be empty");
		this.domain = StringUtils.hasText(domain) ? domain.toLowerCase() : null;
		this.url = url;
		this.rootDn = StringUtils.hasText(rootDn) ? rootDn.toLowerCase() : null;
	}

	/**
	 * @param domain the domain name (may be null or empty)
	 * @param url an LDAP url (or multiple URLs)
	 */
	public ADLoginAuthenticationProvider(String domain, String url) {
		Assert.isTrue(StringUtils.hasText(url), "Url cannot be empty");
		this.domain = StringUtils.hasText(domain) ? domain.toLowerCase() : null;
		this.url = url;
		rootDn = this.domain == null ? null : rootDnFromDomain(this.domain);
	}

	@Override
	protected DirContextOperations doAuthentication(
			UsernamePasswordAuthenticationToken auth) {
		String username = auth.getName();
		String password = (String) auth.getCredentials();
		
		boolean isAuthenticated = false;
		try {
			isAuthenticated = checkUserCredentials(username, password, this.domain);
		}
		catch (Exception e) {
			logger.error("Failed to locate directory entry for authenticated user: "
					+ username, e);
			throw badCredentials(e);
		}
		
		if(!isAuthenticated){
			logger.error("User is not authenticated: " + username);
			throw new BadCredentialsException("User is not authenticated: " + username);
		}
		/*
		DirContext ctx = null;
		try {
			ctx = getDirContext();
			logger.info("searchForUser {}: ", username);
			DirContextOperations dirContextOperations = searchForUser(ctx, username);
			logger.info("searchForUser {}, dirContextOperations {}: ", username, dirContextOperations);
			return dirContextOperations;
		}
		catch (Exception e) {
			logger.error("Failed to locate directory entry for authenticated user: "
					+ username, e);
			throw badCredentials(e);
		}
		finally {
			LdapUtils.closeContext(ctx);
		}*/
		
		return null;
		
	}

	/**
	 * Creates the user authority list from the values of the {@code memberOf} attribute
	 * obtained from the user's Active Directory entry.
	 */
	@Override
	protected Collection<? extends GrantedAuthority> loadUserAuthorities(
			DirContextOperations userData, String username, String password) {
		
		/*UserDetails user = userDetailsService.loadUserByUsername(username);
		

		if (user == null || CollectionUtils.isEmpty(user.getAuthorities())) {
			logger.info("No User or Authorities found in icmc database for username "+username);
			return AuthorityUtils.NO_AUTHORITIES;
		}
		
		logger.info("User Authorities found in icmc database for username:{}, Authorities:{}: "+username,user.getAuthorities());*/
		return null;
	}

	@SuppressWarnings("unused")
	private DirContext bindAsUser(String username, String password) {
		// TODO. add DNS lookup based on domain
		final String bindUrl = url;

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		String bindPrincipal = createBindPrincipal(username);
		env.put(Context.SECURITY_PRINCIPAL, bindPrincipal);
		env.put(Context.PROVIDER_URL, bindUrl);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.OBJECT_FACTORIES, DefaultDirObjectFactory.class.getName());

		try {
			return contextFactory.createContext(env);
		}
		catch (NamingException e) {
			if ((e instanceof AuthenticationException)
					|| (e instanceof OperationNotSupportedException)) {
				handleBindException(bindPrincipal, e);
				throw badCredentials(e);
			}
			else {
				throw LdapUtils.convertLdapException(e);
			}
		}
	}

	private void handleBindException(String bindPrincipal, NamingException exception) {
		if (logger.isDebugEnabled()) {
			logger.info("Authentication for " + bindPrincipal + " failed:" + exception);
		}

		int subErrorCode = parseSubErrorCode(exception.getMessage());

		if (subErrorCode <= 0) {
			logger.info("Failed to locate AD-specific sub-error code in message");
			return;
		}

		logger.info("Active Directory authentication failed: "
				+ subCodeToLogMessage(subErrorCode));

		if (convertSubErrorCodesToExceptions) {
			raiseExceptionForErrorCode(subErrorCode, exception);
		}
	}

	private int parseSubErrorCode(String message) {
		Matcher m = SUB_ERROR_CODE.matcher(message);

		if (m.matches()) {
			return Integer.parseInt(m.group(1), 16);
		}

		return -1;
	}

	private void raiseExceptionForErrorCode(int code, NamingException exception) {
		String hexString = Integer.toHexString(code);
		Throwable cause = new ActiveDirectoryAuthenticationException(hexString,
				exception.getMessage(), exception);
		switch (code) {
		case PASSWORD_EXPIRED:
			throw new CredentialsExpiredException(messages.getMessage(
					"LdapAuthenticationProvider.credentialsExpired",
					"User credentials have expired"), cause);
		case ACCOUNT_DISABLED:
			throw new DisabledException(messages.getMessage(
					"LdapAuthenticationProvider.disabled", "User is disabled"), cause);
		case ACCOUNT_EXPIRED:
			throw new AccountExpiredException(messages.getMessage(
					"LdapAuthenticationProvider.expired", "User account has expired"),
					cause);
		case ACCOUNT_LOCKED:
			throw new LockedException(messages.getMessage(
					"LdapAuthenticationProvider.locked", "User account is locked"), cause);
		default:
			throw badCredentials(cause);
		}
	}

	private String subCodeToLogMessage(int code) {
		switch (code) {
		case USERNAME_NOT_FOUND:
			return "User was not found in directory";
		case INVALID_PASSWORD:
			return "Supplied password was invalid";
		case NOT_PERMITTED:
			return "User not permitted to logon at this time";
		case PASSWORD_EXPIRED:
			return "Password has expired";
		case ACCOUNT_DISABLED:
			return "Account is disabled";
		case ACCOUNT_EXPIRED:
			return "Account expired";
		case PASSWORD_NEEDS_RESET:
			return "User must reset password";
		case ACCOUNT_LOCKED:
			return "Account locked";
		}

		return "Unknown (error code " + Integer.toHexString(code) + ")";
	}

	private BadCredentialsException badCredentials() {
		return new BadCredentialsException(messages.getMessage(
				"LdapAuthenticationProvider.badCredentials", "Bad credentials"));
	}

	private BadCredentialsException badCredentials(Throwable cause) {
		return (BadCredentialsException) badCredentials().initCause(cause);
	}

	@SuppressWarnings("unused")
	private DirContextOperations searchForUser(DirContext context, String username)
			throws NamingException {
		
		
		String as[] = { "mailNickName", "distinguishedName", "mail", "name",
				"lname", "sAMAccountName" };
		SearchControls searchcontrols = new SearchControls();
		searchcontrols.setReturningAttributes(as);
		searchcontrols.setSearchScope(2);
		StringBuffer s1 = new StringBuffer("(&(sAMAccountName=");
		s1.append(username);
		s1.append(")( distinguishedName=*))");
		final NamingEnumeration<SearchResult> resultsEnum = context.search("DC=" + this.domain
				+ ",DC=com", s1.toString(), searchcontrols);
		
		Set<DirContextOperations> results = new HashSet<DirContextOperations>();
		try {
			while (resultsEnum.hasMore()) {
				SearchResult searchResult = resultsEnum.next();
				DirContextAdapter dca = (DirContextAdapter) searchResult.getObject();
				Assert.notNull(dca,
						"No object returned by search, DirContext is not correctly configured");

				if (logger.isDebugEnabled()) {
					logger.info("Found DN: " + dca.getDn());
				}
				results.add(dca);
			}
		}
		catch (Exception e) {
			org.springframework.security.ldap.LdapUtils.closeEnumeration(resultsEnum);
			logger.info("Ignoring PartialResultException");
		}

		if (results.size() == 0) {
			throw new IncorrectResultSizeDataAccessException(1, 0);
		}

		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}

		return results.iterator().next();
		
		/*try {
			return SpringSecurityLdapTemplate.searchForSingleEntryInternal(context,
					searchControls, searchRoot, searchFilter.toString(),
					new Object[] { bindPrincipal });
		}
		catch (IncorrectResultSizeDataAccessException incorrectResults) {
			// Search should never return multiple results if properly configured - just
			// rethrow
			if (incorrectResults.getActualSize() != 0) {
				throw incorrectResults;
			}
			// If we found no results, then the username/password did not match
			UsernameNotFoundException userNameNotFoundException = new UsernameNotFoundException(
					"User " + username + " not found in directory.", incorrectResults);
			throw badCredentials(userNameNotFoundException);
		}*/
	}

	@SuppressWarnings("unused")
	private String searchRootFromPrincipal(String bindPrincipal) {
		int atChar = bindPrincipal.lastIndexOf('@');

		if (atChar < 0) {
			logger.info("User principal '" + bindPrincipal
					+ "' does not contain the domain, and no domain has been configured");
			throw badCredentials();
		}

		return rootDnFromDomain(bindPrincipal.substring(atChar + 1,
				bindPrincipal.length()));
	}

	private String rootDnFromDomain(String domain) {
		String[] tokens = StringUtils.tokenizeToStringArray(domain, ".");
		StringBuilder root = new StringBuilder();

		for (String token : tokens) {
			if (root.length() > 0) {
				root.append(',');
			}
			root.append("dc=").append(token);
		}

		return root.toString();
	}

	String createBindPrincipal(String username) {
		if (domain == null || username.toLowerCase().endsWith(domain)) {
			return username;
		}

		return username + "@" + domain;
	}

	/**
	 * By default, a failed authentication (LDAP error 49) will result in a
	 * {@code BadCredentialsException}.
	 * <p>
	 * If this property is set to {@code true}, the exception message from a failed bind
	 * attempt will be parsed for the AD-specific error code and a
	 * {@link CredentialsExpiredException}, {@link DisabledException},
	 * {@link AccountExpiredException} or {@link LockedException} will be thrown for the
	 * corresponding codes. All other codes will result in the default
	 * {@code BadCredentialsException}.
	 *
	 * @param convertSubErrorCodesToExceptions {@code true} to raise an exception based on
	 * the AD error code.
	 */
	public void setConvertSubErrorCodesToExceptions(
			boolean convertSubErrorCodesToExceptions) {
		this.convertSubErrorCodesToExceptions = convertSubErrorCodesToExceptions;
	}

	/**
	 * The LDAP filter string to search for the user being authenticated. Occurrences of
	 * {0} are replaced with the {@code username@domain}.
	 * <p>
	 * Defaults to: {@code (&(objectClass=user)(userPrincipalName= 0}))}
	 * </p>
	 *
	 * @param searchFilter the filter string
	 *
	 * @since 3.2.6
	 */
	public void setSearchFilter(String searchFilter) {
		Assert.hasText(searchFilter, "searchFilter must have text");
		//this.searchFilter = searchFilter;
	}

	static class ContextFactory {
		DirContext createContext(Hashtable<?, ?> env) throws NamingException {
			return new InitialLdapContext(env, null);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public boolean checkUserCredentials(String s, String s1, String dom) throws Exception { // s - Username,s1-Password
		Hashtable<String, Object> hashtable = new Hashtable<>();

		boolean flag = false;
	
		boolean retmsg = false;
		String excep = "";
	    
	try{
	    
		logger.info("dom:::::::::::::::::"+dom);
	    INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
	    hashtable.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
	    logger.info("INITCTX:::::::::::"+INITCTX);
	    
		String URLprop = this.url;
		StringTokenizer sturl = new StringTokenizer(URLprop, ";");
		int ind = 0;
		while (sturl.hasMoreTokens()) {
			sturl.nextToken();
			++ind;
		}
		INDVAL = ind;
		logger.info("INDVAL:::::::::::::::::"+INDVAL);
		ind = 0;
		sturl = new StringTokenizer(URLprop, ";");
		String URLARR[] = new String[INDVAL];
		while (ind < INDVAL) {
			URLARR[ind] = sturl.nextToken();
			++ind;
		}
		INDVAL = ind;
		ind=0;
		
		logger.info("Indval::::::::::::::::"+INDVAL+":::::ind::::::::::"+ind+"flag:::::::::::::::::::"+flag);
		
		while ((!flag) && (ind < INDVAL)) {
			try {
				logger.info("I am in try:::::");
				URL = URLARR[ind];
				logger.info("URL = "+URL);
				
				logger.info("URL:::::::::::::"+URL);
				hashtable.put(Context.PROVIDER_URL, URL);				
				hashtable.put("com.sun.jndi.ldap.connect.timeout", "1000");
				hashtable.put("com.sun.jndi.ldap.read.timeout", "1000");
				hashtable.put("com.sun.jndi.dns.timeout.retries",1);
				

				Hashtable hashtable1 = getDN(s, dom);
				
				String s2 = (String) hashtable1.get("distinguishedName");
				logger.info("s2:::::::::::::::::::::"+s2);
				String USRPRNCPL = s2;
				hashtable1.get("mail");
				String s5 = (String) hashtable1.get("name");

				String inputStr = s5;
				String patternStr = "/";
				String[] fields = inputStr.split(patternStr);
				s5 = new String(fields[0]);

				ret_s5 = new String(s5);
				ret_mesg = ret_mesg + ret_s5;
				
				logger.info("ret_mesg:::::::::::::::"+ret_mesg);
				logger.info("USRPRNP:::::::::::::::"+USRPRNCPL);
				
				hashtable.put("java.naming.security.authentication", "simple");
				hashtable.put("java.naming.security.principal", USRPRNCPL);
				hashtable.put("java.naming.security.credentials", s1);

				flag = true;
				try {
					new InitialDirContext(hashtable);
					retmsg = true;

				} catch (CommunicationException ce) {
					logger.info("CommunicationException occured in "+URL);
					flag = false;
					retmsg = false;
					ret_mesg = "Invalid Username/Password !!!";

					throw new Exception("CANNOT CONNECT LDAP SERVER");
					
				} catch (AuthenticationException ae) {
					logger.info("AuthenticationException occured in "+URL);
					flag = false;
					retmsg =false;
					ret_mesg = "Invalid Username/Password !!!";

					throw new Exception("INVALID USERNAME OR PASSWORD");

				} catch (Exception exception) {
					flag = false;
					retmsg = false;
					ret_mesg = "Invalid Username/Password !!!";
					exception.printStackTrace();
					throw new Exception("INVALID USERNAME OR PASSWORD");
				}
			} catch (ConnectException coe) {
				logger.info("ConnectException occured in "+URL);

			} catch (Exception exception) {
				flag = false;
				String ret_mesg = exception.toString();
				excep = exception.toString();

				if (excep.equals("java.lang.Exception: INVALID USERNAME OR PASSWORD")){
					logger.info("INVALID USERNAME OR PASSWORD exception occured for "+URL);
					break;
				}

				if (excep.equals("java.lang.Exception: CANNOT CONNECT LDAP SERVER")){
					logger.info("CANNOT CONNECT LDAP SERVER exception occured for "+URL);

				}
			}
			++ind;
			logger.info("ind : "+ind);

			if(ind == INDVAL){
				break;
			}

		}// WHILE
		if (!flag) {
			throw new Exception("INVALID USERNAME OR PASSWORD");
		}
	
		}
		catch(Exception epp){
			epp.printStackTrace();
		}
		return retmsg;
	}
	
	@SuppressWarnings("rawtypes")
	private Hashtable getDN(String s, String dom) throws Exception {
		DirContext dircontext = null;
		try {
			dircontext = getDirContext();
		} catch (Exception e) {
			throw e;
		}

		String as[] = { "mailNickName", "distinguishedName", "mail", "name",
				"lname", "sAMAccountName" };
		SearchControls searchcontrols = new SearchControls();
		searchcontrols.setReturningAttributes(as);
		searchcontrols.setSearchScope(2);
		StringBuffer s1 = new StringBuffer("(&(sAMAccountName=");
		s1.append(s);
		s1.append(")( distinguishedName=*))");
		NamingEnumeration namingenumeration = dircontext.search("DC=" + dom
				+ ",DC=com", s1.toString(), searchcontrols);
		logger.info("namingenumeration:::::::::::::::"+namingenumeration+"domain Name::::::::::::"+dom);
		Hashtable hashtable = formatResults(namingenumeration);
		logger.info("hashtable::::::::DNS::::::::"+hashtable);
		if (hashtable == null) {
			ret_mesg = "Employee No Does not Exist";
			logger.info("Employee No Does not Exist::::::::::::::::");
			throw new Exception("Employee No Does not Exist");
		} else {
			dircontext.close();
			return hashtable;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DirContext getDirContext() throws Exception {
		int ind = 0;
		
		boolean flag = false;
		while ((!flag) && (ind < INDVAL)) {
			try {
				InitialDirContext initialdircontext = null;
				Hashtable hashtable = new Hashtable(11);
				hashtable.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
				hashtable.put(Context.PROVIDER_URL, URL);
				hashtable.put(Context.OBJECT_FACTORIES, DefaultDirObjectFactory.class.getName());

				hashtable.put("com.sun.jndi.ldap.connect.timeout", "1000");
				hashtable.put("com.sun.jndi.ldap.read.timeout", "1000");
				hashtable.put("com.sun.jndi.dns.timeout.retries",1);
				
				initialdircontext = new InitialDirContext(hashtable);
				return initialdircontext;
			} catch (Exception exception) {
				ind++;
				flag = false;
			}
		}
		throw new Exception("CONNECTION ERROR");
	}
	
	@SuppressWarnings("rawtypes")
	public Hashtable formatResults(NamingEnumeration namingenumeration)
			throws Exception {
		int i = 0;
		Hashtable hashtable = null;
		logger.info(""+namingenumeration);
		try {
			while (namingenumeration.hasMore()) {
				hashtable = new Hashtable();
				SearchResult searchresult = (SearchResult) namingenumeration
						.next();
				formatAttributes(searchresult.getAttributes(), hashtable);
				i++;
				logger.info(""+i);
			}

		} catch (NamingException namingexception) {
			throw new Exception("No Result Found");
		}
		return hashtable;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Hashtable formatAttributes(Attributes attributes, Hashtable hashtable)
			throws Exception {
		if (attributes == null)
			throw new Exception("This result has no attributes");
		try {
			for (NamingEnumeration namingenumeration = attributes.getAll(); namingenumeration
					.hasMore();) {
				Attribute attribute = (Attribute) namingenumeration.next();
				for (NamingEnumeration namingenumeration1 = attribute.getAll(); namingenumeration1
						.hasMore(); hashtable.put(attribute.getID(),
						namingenumeration1.next()))
					;

			}

		} catch (NamingException namingexception) {
			throw new Exception("This result has no attributes");
		}
		return hashtable;
	}
}
