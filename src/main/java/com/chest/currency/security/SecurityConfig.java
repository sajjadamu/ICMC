/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import com.chest.currency.enums.PermissionName;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("icmcAuthenticationProvider")
	AuthenticationProvider authenticationProvider;
	
	@Autowired
	@Qualifier("userAuthoritiesPopulator")
	LdapAuthoritiesPopulator userAuthoritiesPopulator;
	
	@Autowired
	@Qualifier("ldapURL")
	String ldapURL;
	
	@Autowired
	@Qualifier("ldapDomain")
	String ldapDomain;
	
	@Autowired
	@Qualifier("activeDirectoryLdapAuthenticationProvider")
	ADLoginAuthenticationProvider aDLoginAuthenticationProvider;
	
	/**
	 * DB Authentication
	 * @param auth
	 * @throws Exception
	 */

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	/**
	 * LDAP Authentication
	 * @param auth
	 * @throws Exception
	 * auth
		.ldapAuthentication()
			.ldapAuthoritiesPopulator(userAuthoritiesPopulator)
			.userDnPatterns("uid={0},ou=people")
			.groupSearchBase("ou=groups")
			.contextSource().ldif("classpath:testldif/test-server.ldif");
	 */
	
	
	/*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(aDLoginAuthenticationProvider);
		
         auth.ldapAuthentication()
         .contextSource().url(ldapURL+"/dc="+ldapDomain)
          .and()   
            .userSearchBase("ou=people")
            .userSearchFilter("(uid={0})")
           .ldapAuthoritiesPopulator(userAuthoritiesPopulator);
    }*/

	
	@Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	      .ignoring()
	         .antMatchers("/app/resources/**","/app/js/**");
	  }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
	      .authorizeRequests()
	        .antMatchers("/app/login").permitAll()
	        .antMatchers("/app/resetPasswordRequest").permitAll()
	        .antMatchers("/app/resetPassword").permitAll()
	        
	        .antMatchers("/app/addRole/**").hasRole(PermissionName.ADD_ROLE.val)
	        .antMatchers("/app/saveRole/**").hasAnyRole(PermissionName.ADD_ROLE.val, PermissionName.UPDATE_ROLE.val)
	        .antMatchers("/app/editRole/**").hasRole(PermissionName.UPDATE_ROLE.val)
	        .antMatchers("/app/updateRole/**").hasRole(PermissionName.UPDATE_ROLE.val)
	        .antMatchers("/app/viewRole/**").hasRole(PermissionName.VIEW_ROLE.val)
	        
	        .antMatchers("/app/addICMC/**").hasRole(PermissionName.ADD_ICMC.val)
	        .antMatchers("/app/saveICMC/**").hasAnyRole(PermissionName.ADD_ICMC.val, PermissionName.UPDATE_ICMC.val)
	        .antMatchers("/app/removeICMC/**").hasRole(PermissionName.UPDATE_ICMC.val)
	        .antMatchers("/app/deleteICMC/**").hasRole(PermissionName.UPDATE_ICMC.val)
	        .antMatchers("/app/viewICMC/**").hasRole(PermissionName.VIEW_ICMC.val)
	        
	        .antMatchers("/app/addICMCReport/**").hasRole(PermissionName.ADD_ICMC_REPORT.val)
	        .antMatchers("/app/saveICMCReport/**").hasAnyRole(PermissionName.ADD_ICMC_REPORT.val, PermissionName.UPDATE_ICMC_REPORT.val)
	        .antMatchers("/app/removeReport/**").hasRole(PermissionName.UPDATE_ICMC_REPORT.val)
	        .antMatchers("/app/deleteReport/**").hasRole(PermissionName.UPDATE_ICMC_REPORT.val)
	        .antMatchers("/app/viewICMCReport/**").hasRole(PermissionName.VIEW_ICMC_REPORT.val)
	        
	        .antMatchers("/app/addBranch/**").hasRole(PermissionName.ADD_BRANCH.val)
	        .antMatchers("/app/saveBranch/**").hasAnyRole(PermissionName.ADD_BRANCH.val, PermissionName.UPDATE_BRANCH.val)
	        .antMatchers("/app/removeBranch/**").hasRole(PermissionName.UPDATE_BRANCH.val)
	        .antMatchers("/app/deleteBranch/**").hasRole(PermissionName.UPDATE_BRANCH.val)
	        .antMatchers("/app/viewBranch/**").hasRole(PermissionName.VIEW_BRANCH.val)
	        
	        .antMatchers("/app/addUser/**").hasRole(PermissionName.ADD_USER.val)
	        .antMatchers("/app/saveUser/**").hasAnyRole(PermissionName.ADD_USER.val, PermissionName.UPDATE_USER.val)
	        .antMatchers("/app/editUser/**").hasRole(PermissionName.UPDATE_USER.val)
	        .antMatchers("/app/updateUser/**").hasRole(PermissionName.UPDATE_USER.val)
	        .antMatchers("/app/viewAll/**").hasRole(PermissionName.VIEW_USER.val)
	        
	        .antMatchers("/app/unlockLockedUser/**").hasRole(PermissionName.UPDATE_LOCKED_USER.val)
	        .antMatchers("/app/viewLockedUser/**").hasRole(PermissionName.VIEW_LOCKED_USER.val)
	        
	        .antMatchers("/app/addDelegateRight/**").hasRole(PermissionName.ADD_DELEGATE_RIGHT.val)
	        .antMatchers("/app/saveDelegateRight/**").hasAnyRole(PermissionName.ADD_DELEGATE_RIGHT.val, PermissionName.UPDATE_DELEGATE_RIGHT.val)
	        .antMatchers("/app/editDelegateRight/**").hasRole(PermissionName.UPDATE_DELEGATE_RIGHT.val)
	        .antMatchers("/app/updateDelegateRight/**").hasRole(PermissionName.UPDATE_DELEGATE_RIGHT.val)
	        .antMatchers("/app/viewDelegateRight/**").hasRole(PermissionName.VIEW_DELEGATE_RIGHT.val)
	        
	        .antMatchers("/app/addVendor/**").hasRole(PermissionName.ADD_VENDOR.val)
	        .antMatchers("/app/saveVendor/**").hasRole(PermissionName.ADD_VENDOR.val)
	        .antMatchers("/app/viewVendor/**").hasRole(PermissionName.VIEW_VENDOR.val)
	        
	        .antMatchers("/app/addJurisdiction/**").hasRole(PermissionName.ADD_JURISDICTION.val)
	        .antMatchers("/app/saveJurisdiction/**").hasAnyRole(PermissionName.ADD_JURISDICTION.val, PermissionName.UPDATE_JURISDICTION.val)
	        .antMatchers("/app/editJurisdiction/**").hasRole(PermissionName.UPDATE_JURISDICTION.val)
	        .antMatchers("/app/updateJurisdiction/**").hasRole(PermissionName.UPDATE_JURISDICTION.val)
	        .antMatchers("/app/viewJurisdiction/**").hasRole(PermissionName.VIEW_JURISDICTION.val)
	        
	        .antMatchers("/app/addMachineModelDetails/**").hasRole(PermissionName.ADD_MACHINE_MODEL.val)
	        .antMatchers("/app/saveMachineModelDetails/**").hasAnyRole(PermissionName.ADD_MACHINE_MODEL.val, PermissionName.UPDATE_MACHINE_MODEL.val)
	        .antMatchers("/app/editMachineModel/**").hasRole(PermissionName.UPDATE_MACHINE_MODEL.val)
	        .antMatchers("/app/updateMachineModelDetails/**").hasRole(PermissionName.UPDATE_MACHINE_MODEL.val)
	        .antMatchers("/app/viewMachineModelDetails/**").hasRole(PermissionName.VIEW_MACHINE_MODEL.val)
	        
	        .antMatchers("/app/addMachineDetails/**").hasRole(PermissionName.ADD_MACHINE.val)
	        .antMatchers("/app/saveMachineDetails/**").hasAnyRole(PermissionName.ADD_MACHINE.val, PermissionName.UPDATE_MACHINE.val)
	        .antMatchers("/app/editMachine/**").hasRole(PermissionName.UPDATE_MACHINE.val)
	        .antMatchers("/app/updateMachine/**").hasRole(PermissionName.UPDATE_MACHINE.val)
	        .antMatchers("/app/viewMachineDetails/**").hasRole(PermissionName.VIEW_MACHINE.val)
	        
	        .antMatchers("/app/uploadHoliday/**").hasRole(PermissionName.UPLOAD_HOLIDAY_MASTER.val)
	        .antMatchers("/app/uploadHolidayMaster/**").hasRole(PermissionName.UPLOAD_HOLIDAY_MASTER.val)
	        .antMatchers("/app/viewHoliday/**").hasRole(PermissionName.VIEW_HOLIDAY_MASTER.val)
	        
	        .antMatchers("/app/servicingBranch/**").hasRole(PermissionName.UPLOAD_SERVICING_BRANCH.val)
	        .antMatchers("/app/UploadServicingBranch/**").hasRole(PermissionName.UPLOAD_SERVICING_BRANCH.val)
	        .antMatchers("/app/editServicingBranch/**").hasRole(PermissionName.UPDATE_SERVICING_BRANCH.val)
	        .antMatchers("/app/updateServicingBranch/**").hasRole(PermissionName.UPDATE_SERVICING_BRANCH.val)
	        .antMatchers("/app/viewServicingBranch/**").hasRole(PermissionName.VIEW_SERVICING_BRANCH.val)
	        
	        .antMatchers("/app/addRbiMaster/**").hasRole(PermissionName.ADD_RBI_MASTER.val)
	        .antMatchers("/app/saveRbiMaster/**").hasAnyRole(PermissionName.ADD_RBI_MASTER.val, PermissionName.UPDATE_RBI_MASTER.val)
	        .antMatchers("/app/editRbiMaster/**").hasRole(PermissionName.UPDATE_RBI_MASTER.val)
	        .antMatchers("/app/updateRbiMaster/**").hasRole(PermissionName.UPDATE_RBI_MASTER.val)
	        .antMatchers("/app/viewRbiMaster/**").hasRole(PermissionName.VIEW_RBI_MASTER.val)
	        
	        .antMatchers("/app/addMachineCompany/**").hasRole(PermissionName.ADD_MACHINE_COMPANY.val)
	        .antMatchers("/app/saveMachineCompany/**").hasAnyRole(PermissionName.ADD_MACHINE_COMPANY.val, PermissionName.UPDATE_MACHINE_COMPANY.val)
	        .antMatchers("/app/editMachineCompany/**").hasRole(PermissionName.UPDATE_MACHINE_COMPANY.val)
	        .antMatchers("/app/updateMachineCompany/**").hasRole(PermissionName.UPDATE_MACHINE_COMPANY.val)
	        .antMatchers("/app/viewMachineCompany/**").hasRole(PermissionName.VIEW_MACHINE_COMPANY.val)
	        
	        .antMatchers("/app/addDSBAccountDetail/**").hasRole(PermissionName.ADD_DSB_ACCOUNT.val)
	        .antMatchers("/app/saveDSBAccountDetail/**").hasAnyRole(PermissionName.ADD_DSB_ACCOUNT.val, PermissionName.UPDATE_DSB_ACCOUNT.val)
	        .antMatchers("/app/editDSBAccountDetail/**").hasRole(PermissionName.UPDATE_DSB_ACCOUNT.val)
	        .antMatchers("/app/updateDSBAccountDetail/**").hasRole(PermissionName.UPDATE_DSB_ACCOUNT.val)
	        .antMatchers("/app/viewDSBAccountDetail/**").hasRole(PermissionName.VIEW_DSB_ACCOUNT.val)
	        
	        .antMatchers("/app/addCRAAccountDetail/**").hasRole(PermissionName.ADD_CRA_ACCOUNT.val)
	        .antMatchers("/app/saveCRAAccountDetail/**").hasAnyRole(PermissionName.ADD_CRA_ACCOUNT.val, PermissionName.UPDATE_CRA_ACCOUNT.val)
	        .antMatchers("/app/editCRAAccountDetail/**").hasRole(PermissionName.UPDATE_CRA_ACCOUNT.val)
	        .antMatchers("/app/updateCRAAccountDetail/**").hasRole(PermissionName.UPDATE_CRA_ACCOUNT.val)
	        .antMatchers("/app/viewCRAAccountDetail/**").hasRole(PermissionName.VIEW_CRA_ACCOUNT.val)
	        
	        .antMatchers("/app/viewBin/**").hasRole(PermissionName.VIEW_BIN_DASHBOARD.val)
	        .antMatchers("/app/viewBinStatus/**").hasRole(PermissionName.VIEW_BIN_DASHBOARD.val)
	        
	        .antMatchers("/app/addBinCapacity/**").hasRole(PermissionName.ADD_BIN_CAPACITY.val)
	        .antMatchers("/app/saveBinCapacity/**").hasAnyRole(PermissionName.ADD_BIN_CAPACITY.val, PermissionName.UPDATE_BIN_CAPACITY.val)
	        .antMatchers("/app/editBinCapacity/**").hasRole(PermissionName.UPDATE_BIN_CAPACITY.val)
	        .antMatchers("/app/updateBinCapacity/**").hasRole(PermissionName.UPDATE_BIN_CAPACITY.val)
	        .antMatchers("/app/viewBinCapacity/**").hasRole(PermissionName.VIEW_BIN_CAPACITY.val)
	        
	        .antMatchers("/app/binMaster/**").hasRole(PermissionName.ADD_BIN.val)
	        .antMatchers("/app/saveDataInBin/**").hasAnyRole(PermissionName.ADD_BIN.val, PermissionName.UPDATE_BIN.val)
	        .antMatchers("/app/viewBinMaster/**").hasRole(PermissionName.VIEW_BIN.val)
	        
	        .antMatchers("/app/acceptIndent/**").hasRole(PermissionName.VIEW_INDENT.val)
	        .antMatchers("/app/AcceptSASIndent/**").hasRole(PermissionName.VIEW_INDENT.val)
	        .antMatchers("/app/AcceptSoiledIndent/**").hasRole(PermissionName.VIEW_INDENT.val)
	        .antMatchers("/app/AcceptORVIndent/**").hasRole(PermissionName.VIEW_INDENT.val)
	        .antMatchers("/app/AcceptDORVIndent/**").hasRole(PermissionName.VIEW_INDENT.val)
	        
	        .antMatchers("/app/getBinInDropDown/**").hasRole(PermissionName.UPDATE_INDENT.val)
	        .antMatchers("/app/updateIndentStatus/**").hasAnyRole(PermissionName.ACCEPT_INDENT.val, PermissionName.UPDATE_INDENT.val)
	        .antMatchers("/app/updateprocessedStatus/**").hasRole(PermissionName.ACCEPT_INDENT.val)
	        .antMatchers("/app/acceptSASIndent/**").hasRole(PermissionName.ACCEPT_INDENT.val)
	        .antMatchers("/app/acceptSoiledIndent/**").hasRole(PermissionName.ACCEPT_INDENT.val)
	        .antMatchers("/app/acceptOrvIndent/**").hasRole(PermissionName.ACCEPT_INDENT.val)
	        .antMatchers("/app/acceptDorvIndent/**").hasRole(PermissionName.ACCEPT_INDENT.val)
	        
	        .antMatchers("/app/DefineKeySet/**").hasRole(PermissionName.ADD_DEFINE_KEYSET.val)
	        .antMatchers("/app/AddDefineKeySet/**").hasAnyRole(PermissionName.ADD_DEFINE_KEYSET.val, PermissionName.UPDATE_DEFINE_KEYSET.val)
	        .antMatchers("/app/editDefineKeySet/**").hasRole(PermissionName.UPDATE_DEFINE_KEYSET.val)
	        .antMatchers("/app/UpdateDefineKeySet/**").hasRole(PermissionName.UPDATE_DEFINE_KEYSET.val)
	        .antMatchers("/app/viewDefineKeySet/**").hasRole(PermissionName.VIEW_DEFINE_KEYSET.val)
	        
	        .antMatchers("/app/AssignVaultCustodian/**").hasRole(PermissionName.ADD_ASSIGN_VAULT_CUSTODIAN.val)
	        .antMatchers("/app/AddAssignVaultCustodian/**").hasAnyRole(PermissionName.ADD_ASSIGN_VAULT_CUSTODIAN.val, PermissionName.UPDATE_ASSIGN_VAULT_CUSTODIAN.val)
	        .antMatchers("/app/editAssignVaultCustodian/**").hasRole(PermissionName.UPDATE_ASSIGN_VAULT_CUSTODIAN.val)
	        .antMatchers("/app/updateAssignVaultCustodian/**").hasRole(PermissionName.UPDATE_ASSIGN_VAULT_CUSTODIAN.val)
	        .antMatchers("/app/viewAssignVaultCustodian/**").hasRole(PermissionName.VIEW_ASSIGN_VAULT_CUSTODIAN.val)
	        
	        .antMatchers("/app/CITCRAVendor/**").hasRole(PermissionName.ADD_CIT_CRA_VENDOR.val)
	        .antMatchers("/app/addCITCRAVendor/**").hasAnyRole(PermissionName.ADD_CIT_CRA_VENDOR.val, PermissionName.UPDATE_CIT_CRA_VENDOR.val)
	        .antMatchers("/app/editCITCRAVendor/**").hasRole(PermissionName.UPDATE_CIT_CRA_VENDOR.val)
	        .antMatchers("/app/updateCITCRAVendor/**").hasRole(PermissionName.UPDATE_CIT_CRA_VENDOR.val)
	        .antMatchers("/app/removeCITCRAVendor/**").hasRole(PermissionName.UPDATE_CIT_CRA_VENDOR.val)
	        .antMatchers("/app/removeVendor/**").hasRole(PermissionName.UPDATE_CIT_CRA_VENDOR.val)
	        .antMatchers("/app/viewCITCRAVendor/**").hasRole(PermissionName.VIEW_CIT_CRA_VENDOR.val)
	        
	        .antMatchers("/app/CITCRAVehicle/**").hasRole(PermissionName.ADD_CIT_CRA_VEHICLE.val)
	        .antMatchers("/app/addCITCRAVehicle/**").hasAnyRole(PermissionName.ADD_CIT_CRA_VEHICLE.val, PermissionName.UPDATE_CIT_CRA_VEHICLE.val)
	        .antMatchers("/app/editCITCRAVehicle/**").hasRole(PermissionName.UPDATE_CIT_CRA_VEHICLE.val)
	        .antMatchers("/app/updateCITCRAVehicle/**").hasRole(PermissionName.UPDATE_CIT_CRA_VEHICLE.val)
	        .antMatchers("/app/removeCITCRAVehicle/**").hasRole(PermissionName.UPDATE_CIT_CRA_VEHICLE.val)
	        .antMatchers("/app/deleteVehicle/**").hasRole(PermissionName.UPDATE_CIT_CRA_VEHICLE.val)
	        .antMatchers("/app/viewCITCRAVehicle/**").hasRole(PermissionName.VIEW_CIT_CRA_VEHICLE.val)
	        
	        .antMatchers("/app/CITCRADriver/**").hasRole(PermissionName.ADD_CIT_CRA_DRIVER.val)
	        .antMatchers("/app/addCITCRADriver/**").hasAnyRole(PermissionName.ADD_CIT_CRA_DRIVER.val, PermissionName.UPDATE_CIT_CRA_DRIVER.val)
	        .antMatchers("/app/editCITCRADriver/**").hasRole(PermissionName.UPDATE_CIT_CRA_DRIVER.val)
	        .antMatchers("/app/updateCITCRADriver/**").hasRole(PermissionName.UPDATE_CIT_CRA_DRIVER.val)
	        .antMatchers("/app/removeCITCRADriver/**").hasRole(PermissionName.UPDATE_CIT_CRA_DRIVER.val)
	        .antMatchers("/app/removeDriver/**").hasRole(PermissionName.UPDATE_CIT_CRA_DRIVER.val)
	        .antMatchers("/app/viewCITCRADriver/**").hasRole(PermissionName.VIEW_CIT_CRA_DRIVER.val)
	        
	        .antMatchers("/app/Addshrink/**").hasRole(PermissionName.ADD_BRANCH_RECEIPT.val)
	        .antMatchers("/app/viewShrink/**").hasRole(PermissionName.VIEW_BRANCH_RECEIPT.val)
	        
	        .antMatchers("/app/DSB/**").hasRole(PermissionName.ADD_DSB.val)
	        .antMatchers("/app/viewDSB/**").hasRole(PermissionName.VIEW_DSB.val)
	        
	        .antMatchers("/app/Dirv/**").hasRole(PermissionName.ADD_DIVERSION_RECEIPT.val)
	        .antMatchers("/app/viewDirv/**").hasRole(PermissionName.VIEW_DIVERSION_RECEIPT.val)
	        
	        .antMatchers("/app/freshFromRbi/**").hasRole(PermissionName.ADD_FRESH_FROM_RBI.val)
	        .antMatchers("/app/viewFresh/**").hasRole(PermissionName.VIEW_FRESH_FROM_RBI.val)
	        
	        .antMatchers("/app/AddOtherBankReceipt/**").hasRole(PermissionName.ADD_OTHER_BANK_RECEIPT.val)
	        .antMatchers("/app/viewBankReceipt/**").hasRole(PermissionName.VIEW_OTHER_BANK_RECEIPT.val)
	        
	        										
	        .antMatchers("/app/indentRequest/**").hasRole(PermissionName.ADD_INDENT.val)
	        .antMatchers("/app/saveIndentRequest/**").hasAnyRole(PermissionName.ADD_INDENT.val, PermissionName.UPDATE_INDENT.val)
	        .antMatchers("/app/updateIndentStatusForCancel/**").hasRole(PermissionName.UPDATE_INDENT.val)
	        
	        .antMatchers("/app/addDiscrepancy/**").hasRole(PermissionName.ADD_DISCREPANCY.val)
	        .antMatchers("/app/DiscrepancyAllocation/**").hasRole(PermissionName.ADD_DISCREPANCY.val)
	        
	        .antMatchers("/app/saveMachineAllocationData/**").hasRole(PermissionName.ADD_MACHINE_ALLOCATION.val)
	        .antMatchers("/app/processEntry/**").hasRole(PermissionName.ADD_PROCESSED_DATA.val)
	        .antMatchers("/app/QRPathProcess/**").hasRole(PermissionName.ADD_PROCESSED_DATA.val)
	        .antMatchers("/app/bundleRequestForMachineProcessing/**").hasRole(PermissionName.VIEW_BUNDLE_REQUEST_FOR_MACHINE.val)
	        																
	        .antMatchers("/app/MachineDowntimeUpdation/**").hasRole(PermissionName.ADD_MACHINE_DOWNTIME_UPDATION.val)
	        .antMatchers("/app/AddMachineDowntimeUpdation/**").hasAnyRole(PermissionName.ADD_MACHINE_DOWNTIME_UPDATION.val, 
	        																PermissionName.UPDATE_MACHINE_DOWNTIME_UPDATION.val)
	        .antMatchers("/app/editMachineDownTime/**").hasRole(PermissionName.UPDATE_MACHINE_DOWNTIME_UPDATION.val)
	        .antMatchers("/app/updateMachineDownTime/**").hasRole(PermissionName.UPDATE_MACHINE_DOWNTIME_UPDATION.val)
	        .antMatchers("/app/viewMachineDownTime/**").hasRole(PermissionName.VIEW_MACHINE_DOWNTIME_UPDATION.val)
	        														
	        .antMatchers("/app/MachineSoftwareUpdation/**").hasRole(PermissionName.ADD_MACHINE_SOFTWARE_UPDATION.val)
	        .antMatchers("/app/AddMachineSoftware/**").hasAnyRole(PermissionName.ADD_MACHINE_SOFTWARE_UPDATION.val, 
	        											PermissionName.UPDATE_MACHINE_SOFTWARE_UPDATION.val)
	        .antMatchers("/app/editMachineSoftware/**").hasRole(PermissionName.UPDATE_MACHINE_SOFTWARE_UPDATION.val)
	        .antMatchers("/app/updateMachineSoftware/**").hasRole(PermissionName.UPDATE_MACHINE_SOFTWARE_UPDATION.val)
	        .antMatchers("/app/viewMachineSoftware/**").hasRole(PermissionName.VIEW_MACHINE_SOFTWARE_UPDATION.val)
	        
	        .antMatchers("/app/RepeatabilityTestInput/**").hasRole(PermissionName.ADD_REPEATABILITY_TEST_INPUT.val)
	        .antMatchers("/app/AddRepeatabilityTestInput/**").hasAnyRole(PermissionName.ADD_REPEATABILITY_TEST_INPUT.val, 
	        																PermissionName.UPDATE_REPEATABILITY_TEST_INPUT.val)
	        .antMatchers("/app/editRepeatabilityTestInput/**").hasRole(PermissionName.UPDATE_REPEATABILITY_TEST_INPUT.val)
	        .antMatchers("/app/updateRepeatabilityTestInput/**").hasRole(PermissionName.UPDATE_REPEATABILITY_TEST_INPUT.val)
	        .antMatchers("/app/viewRepeatabilityTestInput/**").hasRole(PermissionName.VIEW_REPEATABILITY_TEST_INPUT.val)
	        
	        .antMatchers("/app/RepeatabilityTestOutput/**").hasRole(PermissionName.ADD_REPEATABILITY_TEST_OUTPUT.val)
	        .antMatchers("/app/AddRepeatabilityTestOutput/**").hasAnyRole(PermissionName.ADD_REPEATABILITY_TEST_OUTPUT.val, 
	        																PermissionName.UPDATE_REPEATABILITY_TEST_OUTPUT.val)
	        .antMatchers("/app/editRepeatabilityTestOutput/**").hasRole(PermissionName.UPDATE_REPEATABILITY_TEST_OUTPUT.val)
	        .antMatchers("/app/updateRepeatabilityTestOutput/**").hasRole(PermissionName.UPDATE_REPEATABILITY_TEST_OUTPUT.val)
	        .antMatchers("/app/viewRepeatabilityTestOutput/**").hasRole(PermissionName.VIEW_REPEATABILITY_TEST_OUTPUT.val)
	        														
	        .antMatchers("/app/FreshCurrency/**").hasRole(PermissionName.ADD_FRESH_CURRENCY.val)
	        .antMatchers("/app/AddFreshCurrency/**").hasAnyRole(PermissionName.ADD_FRESH_CURRENCY.val, PermissionName.UPDATE_FRESH_CURRENCY.val)
	        .antMatchers("/app/editFreshCurrency/**").hasRole(PermissionName.UPDATE_FRESH_CURRENCY.val)
	        .antMatchers("/app/updateFreshCurrency/**").hasRole(PermissionName.UPDATE_FRESH_CURRENCY.val)
	        .antMatchers("/app/viewfreshCurrency/**").hasRole(PermissionName.VIEW_FRESH_CURRENCY.val)
	        
	        
	        .antMatchers("/app/uploadSAS/**").hasRole(PermissionName.SAS_UPLOAD.val)
	        .antMatchers("/app/updateSAS/**").hasRole(PermissionName.UPDATE_SAS.val)
	        .antMatchers("/app/AssignBinForBulkIndent/**").hasRole(PermissionName.ADD_SAS.val)
	        .antMatchers("/app/SAS/**").hasRole(PermissionName.VIEW_SAS.val)
	        
	        .antMatchers("/app/Dorv/**").hasRole(PermissionName.ADD_DIVERSION_PAYMENT.val)
	        .antMatchers("/app/DorvAllocation/**").hasAnyRole(PermissionName.ADD_DIVERSION_PAYMENT.val, PermissionName.UPDATE_DIVERSION_PAYMENT.val)
	        
	        .antMatchers("/app/ORVBranch/**").hasRole(PermissionName.ADD_BRANCH_PAYMENT.val)
	        .antMatchers("/app/orvBranchAllocation/**").hasAnyRole(PermissionName.ADD_BRANCH_PAYMENT.val, PermissionName.UPDATE_BRANCH_PAYMENT.val)
	        
	        .antMatchers("/app/Soiled/**").hasRole(PermissionName.ADD_SOILED_REMITTANCE.val)
	        .antMatchers("/app/soiledRemittance/**").hasAnyRole(PermissionName.ADD_SOILED_REMITTANCE.val, PermissionName.UPDATE_SOILED_REMITTANCE.val)
	        
	        .antMatchers("/app/cashReleased/**").hasRole(PermissionName.VIEW_CASH_RELEASED.val)
	        
	        .antMatchers("/app/CRAPayment/**").hasRole(PermissionName.ADD_CRA_PAYMENT.val)
	        .antMatchers("/app/CRAAllocation/**").hasAnyRole(PermissionName.ADD_CRA_PAYMENT.val, PermissionName.UPDATE_CRA_PAYMENT.val)
	        .antMatchers("/app/viewCRA/**").hasRole(PermissionName.VIEW_CRA_PAYMENT.val)
	        
	        .antMatchers("/app/otherBankPayment/**").hasRole(PermissionName.ADD_OTHER_BANK_PAYMENT.val)
	        .antMatchers("/app/OtherBankAllocation/**").hasAnyRole(PermissionName.ADD_OTHER_BANK_PAYMENT.val, PermissionName.UPDATE_OTHER_BANK_PAYMENT.val)
	        .antMatchers("/app/viewOtherBankPayment/**").hasRole(PermissionName.VIEW_OTHER_BANK_PAYMENT.val)
	        
	        
	        //Similarly all URL should be added here for respective ROLE
	        .anyRequest().authenticated()
	        .and()
	    .formLogin()
	        .loginPage("/app/login")
	        .successForwardUrl("/app/welcome")
	        .permitAll()
	        .and()
	        .csrf().disable().sessionManagement().maximumSessions(1);
	  }

	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
