/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

public class LoginBean
{
		private String username;

		private String password;

		 private String roles;
		 
		  boolean isValidUser;
		  
		
		public boolean isValidUser() {
			return isValidUser;
		}

		public void setValidUser(boolean isValidUser) {
			this.isValidUser = isValidUser;
		}

		public String getRoles() {
			return roles;
		}

		public void setRoles(String roles) {
			this.roles = roles;
		}

		public String getPassword()
		{
				return this.password;
		}

		public String getUsername()
		{
				return this.username;
		}

		public void setUsername(String username)
		{
				this.username = username;
		}

		public void setPassword(String password)
		{
				this.password = password;
		}


}
