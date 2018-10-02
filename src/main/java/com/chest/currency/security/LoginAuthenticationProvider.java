package com.chest.currency.security;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.chest.currency.entity.model.User;
import com.chest.currency.jpa.dao.UserAdministrationJpaDao;

@Component("icmcAuthenticationProvider")
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	protected UserAdministrationJpaDao userAdministrationJpaDao;

	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication)
          throws AuthenticationException {

	  try {

		Authentication auth = super.authenticate(authentication);

		//if reach here, means login success, else an exception will be thrown
		//reset the user_attempts
		userAdministrationJpaDao.resetFailAttempts(authentication.getName());

		return auth;

	  } catch (BadCredentialsException e) {

		//invalid login, update to user_attempts
		  userAdministrationJpaDao.updateFailAttempts(authentication.getName());
		throw e;

	  } catch (LockedException e){

		//this user is locked!
		String error = "";
		User userAttempts =
				userAdministrationJpaDao.getUserById(authentication.getName());

               if(userAttempts!=null && userAttempts.getIsAccountLocked() == 'Y'){
			Calendar lastAttempts = userAttempts.getLastAuthFail();
			error = "User account is locked! <br><br>Username : "
                           + authentication.getName() + "<br>Last Attempts : " + lastAttempts;
		}else{
			error = e.getMessage();
		}

	  throw new LockedException(error);
	}

	}

}