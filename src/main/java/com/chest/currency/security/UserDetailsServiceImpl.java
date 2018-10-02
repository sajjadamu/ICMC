package com.chest.currency.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chest.currency.entity.model.DelegateRight;
import com.chest.currency.entity.model.Role;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.PermissionName;
import com.chest.currency.enums.Status;
import com.chest.currency.jpa.dao.UserAdministrationJpaDao;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private static final String ROLE_ = "ROLE_";
	private static final Character YES = 'Y';

	@Autowired
	protected UserAdministrationJpaDao userAdministrationJpaDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		LOG.info("userId getUserById.........." + userId);
		User user = userAdministrationJpaDao.getUserById(userId);
		LOG.info("user getUserById.........." + user);
		DelegateRight delegateRight = userAdministrationJpaDao.getUserDelegatedRightById(user);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRole(), delegateRight);
		return buildUserForAuthentication(user, authorities);

	}

	// Converts User user to
	// org.springframework.security.core.userdetails.User
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,
			List<GrantedAuthority> authorities) {

		String username = user.getId();
		String password = user.getPassword();
		boolean enabled = user.getStatus() == Status.ENABLED ? true : false;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = YES.equals(user.getIsAccountLocked()) ? false : true;

		return new org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Role role, DelegateRight delegateRight) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		List<PermissionName> userRolePermissions = role.getRolePermission();

		setAuths(setAuths, userRolePermissions);

		if (delegateRight != null && delegateRight.getRole() != null
				&& !CollectionUtils.isEmpty(delegateRight.getRole().getRolePermission())) {

			userRolePermissions = delegateRight.getRole().getRolePermission();

			setAuths(setAuths, userRolePermissions);
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	private void setAuths(Set<GrantedAuthority> setAuths, List<PermissionName> userRolePermissions) {
		for (PermissionName userRole : userRolePermissions) {
			setAuths.add(new SimpleGrantedAuthority(ROLE_ + userRole.name()));
		}
	}

}
