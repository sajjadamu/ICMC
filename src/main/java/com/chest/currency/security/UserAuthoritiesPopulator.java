package com.chest.currency.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service("userAuthoritiesPopulator")
public class UserAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	@Autowired
	private UserDetailsService userDetailsService;

	static final Logger LOG = LoggerFactory.getLogger(UserAuthoritiesPopulator.class);

	@Transactional(readOnly = true)
	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData,
			String username) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		try {
			UserDetails user = userDetailsService.loadUserByUsername(username);
			if(user != null && !CollectionUtils.isEmpty(authorities)){
				return user.getAuthorities();
			}
			/*
			 * if (user==null) LOG.
			 * error("Threw exception in MyAuthoritiesPopulator::getGrantedAuthorities : User doesn't exist into ATS database"
			 * ); else{ for(UserRole userRole : user.getUserRole()) {
			 * authorities.add(new
			 * SimpleGrantedAuthority(userRole.getUserRoleKeys().getRole())); }
			 * return authorities; }
			 */
		} catch (Exception e) {
			// LOG.error("Threw exception in
			// MyAuthoritiesPopulator::getGrantedAuthorities : " +
			// ErrorExceptionBuilder.buildErrorResponse(e)); 
			}
	return authorities;
	}
}
