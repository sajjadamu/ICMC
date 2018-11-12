<%@page import="com.chest.currency.enums.RoleName"%>
<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
   
   <!-- Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <aside class="left-sidebar">
            <!-- Sidebar scroll-->
            <div class="scroll-sidebar">
                <!-- End User profile text-->
                <!-- Sidebar navigation-->
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">
                    <%-- <jsp:include page="common.jsp" />  --%><li> <a class="waves-effect waves-dark" href="./homeAction" aria-expanded="false"><i class="mdi mdi-gauge"></i><span class="hide-menu">Dashboard </span></a></li>
                    <li>
                    <a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false"><i class="mdi mdi-laptop-windows"></i><span class="hide-menu">Administration</span></a>
           <ul aria-expanded="false" class="collapse">
               <sec:authorize access="hasRole('ADD_ROLE')">
			
				<ul>

					<c:set var="addRole" value="false" />
					<sec:authorize access="hasRole('ADD_ROLE')">
						<!-- true -->
						<c:set var="addRole" value="true" />
						<li><a href="././addRole"> Login Management</a></li>
					</sec:authorize>
					<c:if test="${addRole == false}">
						<sec:authorize access="hasRole('VIEW_ROLE')">
							<li><a href="././viewRole"> Login Management</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="addICMC" value="false" />
					<sec:authorize access="hasRole('ADD_ICMC')">
						<c:set var="addICMC" value="true" />
						<li><a href="././addICMC"> Add / Modify ICMC</a></li>
					</sec:authorize>
					<c:if test="${addICMC == false}">
						<sec:authorize access="hasRole('VIEW_ICMC')">
							<li><a href="././viewICMC"> Add / Modify ICMC</a></li>
						</sec:authorize>
					</c:if>

					<%-- <c:set var="addICMCReport" value="false" />
					<sec:authorize access="hasRole('ADD_ICMC_REPORT')">
						<c:set var="addICMCReport" value="true" />
							<li><a href="././addICMCReport"> Manage ICMC Report</a></li>
					</sec:authorize>
				<c:if test="${addICMCReport == false}">
					<sec:authorize access="hasRole('VIEW_ICMC_REPORT')">
						<li><a href="././viewICMCReport"> View List of ICMC Report's</a></li>
					</sec:authorize>
				</c:if> --%>

					<c:set var="addBranch" value="false" />
					<sec:authorize access="hasRole('ADD_BRANCH')">
						<c:set var="addBranch" value="true" />
						<li><a href="././addBranch"> Add / Modify Branch</a></li>
					</sec:authorize>
					<c:if test="${addBranch == false}">
						<sec:authorize access="hasRole('VIEW_BRANCH')">
							<li><a href="././viewBranch"> Add / Modify Branch</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="addUser" value="false" />
					<sec:authorize access="hasRole('ADD_USER')">
						<c:set var="addUser" value="true" />
						<li><a href="././addUser"> Add User</a></li>
					</sec:authorize>
					<c:if test="${addUser == false}">
						<sec:authorize access="hasRole('VIEW_USER')">
							<li><a href="././viewAll"> Add User</a></li>
						</sec:authorize>
					</c:if>

					<%-- <sec:authorize access="hasRole('VIEW_LOCKED_USER')">
					<li><a href="././viewLockedUser"> Release Locked User</a></li>
				</sec:authorize> --%>

					<c:set var="addDelegateRight" value="false" />
					<sec:authorize access="hasRole('ADD_DELEGATE_RIGHT')">
						<c:set var="addDelegateRight" value="true" />
						<li><a href="././addDelegateRight"> Delegate User Rights</a></li>
					</sec:authorize>
					<c:if test="${addDelegateRight == false}">
						<sec:authorize access="hasRole('VIEW_DELEGATE_RIGHT')">
							<li><a href="././viewDelegateRight"> Delegate User
									Rights</a></li>
						</sec:authorize>
					</c:if>

					<%-- <c:set var="addVendor" value="false" />
					<sec:authorize access="hasRole('ADD_VENDOR')">
						<c:set var="addVendor" value="true" />
							<li><a href="././addVendor"> Add / Modify Vendor</a></li>
					</sec:authorize>
				<c:if test="${addVendor == false}">
					<sec:authorize access="hasRole('VIEW_VENDOR')">
						<li><a href="././viewVendor"> Add / Modify Vendor</a></li>
					</sec:authorize>
				</c:if> --%>


					<%-- <sec:authorize access="hasRole('VIEW_JURISDICTION')">
					<li><a href="././viewJurisdiction"> Manage Jurisdiction</a></li>
				</sec:authorize> --%>


					<sec:authorize access="hasRole('VIEW_USER_AUDIT_TRAIL')">
						<li><a href="././viewUserAuditTrail"> User wise Audit
								trail</a></li>
					</sec:authorize>

					<c:set var="addMachineCompany" value="false" />
					<sec:authorize access="hasRole('ADD_MACHINE_COMPANY')">
						<c:set var="addMachineCompany" value="true" />
						<li><a href="././addMachineCompany"> Manage Machine
								Company</a></li>
					</sec:authorize>
					<c:if test="${addMachineCompany == false}">
						<sec:authorize access="hasRole('VIEW_MACHINE_COMPANY')">
							<li><a href="././viewMachineCompany"> View List of
									Machine Company</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="addMachineModelDetails" value="false" />
					<sec:authorize access="hasRole('ADD_MACHINE_MODEL')">
						<c:set var="addMachineModelDetails" value="true" />
						<li><a href="././addMachineModelDetails"> Add / Modify
								Machine Model</a></li>
					</sec:authorize>
					<c:if test="${addMachineModelDetails == false}">
						<sec:authorize access="hasRole('VIEW_MACHINE_MODEL')">
							<li><a href="././viewMachineModelDetails"> Add / Modify
									Machine Model</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="addMachineDetails" value="false" />
					<sec:authorize access="hasRole('ADD_MACHINE')">
						<c:set var="addMachineDetails" value="true" />
						<li><a href="././addMachineDetails"> Add / Modify Machine</a></li>
					</sec:authorize>
					<c:if test="${addMachineDetails == false}">
						<sec:authorize access="hasRole('VIEW_MACHINE')">
							<li><a href="././viewMachineDetails"> Add / Modify
									Machine</a></li>
						</sec:authorize>
					</c:if>


					<sec:authorize access="hasRole('VIEW_HOLIDAY_MASTER')">
						<li><a href="././viewHoliday"> Holiday Master</a></li>
					</sec:authorize>


					<%-- <sec:authorize access="hasRole('VIEW_ACCOUNT_DETAILS')">
					<li><a href="././viewAccountDetails"> Account Master</a></li>
				</sec:authorize> --%>


					<%-- <sec:authorize access="hasRole('VIEW_SERVICING_BRANCH')">
					<li><a href="././viewServicingBranch"> Servicing Branch</a></li>
				</sec:authorize> --%>


					<c:set var="addRbiMaster" value="false" />
					<sec:authorize access="hasRole('ADD_RBI_MASTER')">
						<c:set var="addRbiMaster" value="true" />
						<li><a href="././addRbiMaster"> RBI Linkage Master</a></li>
					</sec:authorize>
					<c:if test="${addRbiMaster == false}">
						<sec:authorize access="hasRole('VIEW_RBI_MASTER')">
							<li><a href="././viewRbiMaster"> RBI Linkage Master</a></li>
						</sec:authorize>
					</c:if>



					<c:set var="addDSBAccountDetail" value="false" />
					<sec:authorize access="hasRole('ADD_DSB_ACCOUNT')">
						<c:set var="addDSBAccountDetail" value="true" />
						<li><a href="././addDSBAccountDetail"> Add / Modify DSB
								Agency</a></li>
					</sec:authorize>
					<c:if test="${addDSBAccountDetail == false}">
						<sec:authorize access="hasRole('VIEW_DSB_ACCOUNT')">
							<li><a href="././viewDSBAccountDetail"> Add / Modify DSB
									Agency</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="addCRAAccountDetail" value="false" />
					<sec:authorize access="hasRole('ADD_CRA_ACCOUNT')">
						<c:set var="addCRAAccountDetail" value="true" />
						<li><a href="././addCRAAccountDetail"> Add / Modify CRA
								Agency</a></li>
					</sec:authorize>
					<c:if test="${addCRAAccountDetail == false}">
						<sec:authorize access="hasRole('VIEW_CRA_ACCOUNT')">
							<li><a href="././viewCRAAccountDetail"> Add / Modify CRA
									Agency</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="addBinCapacity" value="false" />
					<sec:authorize access="hasRole('ADD_BIN_CAPACITY')">
						<c:set var="addBinCapacity" value="true" />
						<li><a href="././addBinCapacity"> Bin Capacity Define</a></li>
					</sec:authorize>
					<c:if test="${addBinCapacity == false}">
						<sec:authorize access="hasRole('VIEW_BIN_CAPACITY')">
							<li><a href="././viewBinCapacity"> View Bin Capacity
									List</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="binMaster" value="false" />
					<sec:authorize access="hasRole('ADD_BIN')">
						<c:set var="binMaster" value="true" />
						<li><a href="././binMaster"> Bin Management</a></li>
					</sec:authorize>
					<c:if test="${binMaster == false}">
						<sec:authorize access="hasRole('VIEW_BIN')">
							<li><a href="././viewBinMaster"> View Bin List</a></li>
						</sec:authorize>
					</c:if>

					<sec:authorize access="hasRole('ADD_PRINTER')">
						<li><a href="././addPrinter"> Printer Management</a></li>
					</sec:authorize>


				</ul></li>
		</sec:authorize>
                            </ul>
                        </li>
                        	<sec:authorize access="hasAnyRole('VIEW_BIN_DASHBOARD')">
			<li><a class=" waves-effect waves-dark" href="././viewBin" aria-expanded="false">
                         <i class="mdi mdi-chart-bubble"></i>
                         <span class="hide-menu">Bin Dashboard</span>
                         </a>                            
                        </li>
                        </sec:authorize>
                         <li>
                            	<sec:authorize access="hasRole('VIEW_SAS')">
                            	 <a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                        <i class="mdi mdi-cash"></i><span class="hide-menu">Cash Payment</span></a>
                            <ul aria-expanded="false" class="collapse">
				<ul>

					<c:set var="addRole" value="false" />

					<sec:authorize access="hasRole('VIEW_SAS')">
						<li><a href="././SAS"> Branch Payment SAS</a></li>
					</sec:authorize>

					<c:set var="ORVBranch" value="false" />
					<sec:authorize access="hasRole('ADD_BRANCH_PAYMENT')">
						<c:set var="ORVBranch" value="true" />
						<li><a href="././ORVBranch"> Branch Payment Manual</a></li>
					</sec:authorize>
					<c:if test="${ORVBranch == false}">
						<sec:authorize access="hasRole('VIEW_BRANCH_PAYMENT')">
							<li><a href="././viewORV"> View Branch Payment Manual</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="CRAPayment" value="false" />
					<sec:authorize access="hasRole('ADD_CRA_PAYMENT')">
						<c:set var="CRAPayment" value="true" />
						<li><a href="././CRAPayment"> CRA Payment</a></li>
					</sec:authorize>
					<c:if test="${CRAPayment == false}">
						<sec:authorize access="hasRole('VIEW_CRA_PAYMENT')">
							<li><a href="././viewCRA"> View CRA Payment</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="Dorv" value="false" />
					<sec:authorize access="hasRole('ADD_DIVERSION_PAYMENT')">
						<c:set var="Dorv" value="true" />
						<li><a href="././Dorv"> Outward Diversion</a></li>
					</sec:authorize>
					<c:if test="${Dorv == false}">
						<sec:authorize access="hasRole('VIEW_DIVERSION_PAYMENT')">
							<li><a href="././viewDorv"> View Diversion Payment</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="Soiled" value="false" />
					<sec:authorize access="hasRole('ADD_SOILED_REMITTANCE')">
						<c:set var="Soiled" value="true" />
						<li><a href="././Soiled"> Soiled Remittance</a></li>
					</sec:authorize>
					<c:if test="${Soiled == false}">
						<sec:authorize access="hasRole('VIEW_SOILED_REMITTANCE')">
							<li><a href="././viewSoiled"> View Soiled Remittance</a></li>
						</sec:authorize>
					</c:if>

					<sec:authorize access="hasRole('MUTILATED_PAYMENT')">
						<li><a href="././mutilatedPayment"> Mutilated Full Value</a></li>
					</sec:authorize>

					<c:set var="otherBankPayment" value="false" />
					<sec:authorize access="hasRole('ADD_OTHER_BANK_PAYMENT')">
						<c:set var="otherBankPayment" value="true" />
						<li><a href="././otherBankPayment"> Other Bank Payment</a></li>
					</sec:authorize>
					<c:if test="${otherBankPayment == false}">
						<sec:authorize access="hasRole('VIEW_OTHER_BANK_PAYMENT')">
							<li><a href="././viewOtherBankPayment"> View Other Bank
									Payment</a></li>
						</sec:authorize>
					</c:if>

					<sec:authorize access="hasRole('VIEW_CASH_RELEASED')">
						<li><a href="././cashReleased"> Cash Handover</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('VIEW_FORCE_HANDOVER')">
						<li><a href="././forceHandover"> Force Handover</a></li>
					</sec:authorize>



				</ul></li>
		</sec:authorize>
                            
                              

                            </ul>
                        </li>
                        <li> 
                       <sec:authorize access="hasRole('ADD_BRANCH_RECEIPT')">
			<a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                            <i class="mdi mdi-file"></i><span class="hide-menu">Cash Receipt</span></a>
				<ul  aria-expanded="false" class="collapse">

					<c:set var="Addshrink" value="false" />
					<sec:authorize access="hasRole('ADD_BRANCH_RECEIPT')">
						<c:set var="Addshrink" value="true" />
						<li><a href="././Addshrink"> Branch Receipt</a></li>
					</sec:authorize>
					<c:if test="${Addshrink == false}">
						<sec:authorize access="hasRole('VIEW_BRANCH_RECEIPT')">
							<li><a href="././viewShrink"> View Branch Receipt</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="DSB" value="false" />
					<sec:authorize access="hasRole('ADD_DSB')">
						<c:set var="DSB" value="true" />
						<li><a href="././DSB"> DSB Receipt</a></li>
					</sec:authorize>
					<c:if test="${DSB == false}">
						<sec:authorize access="hasRole('VIEW_DSB')">
							<li><a href="././viewDSB"> View DSB Receipt</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="Dirv" value="false" />
					<sec:authorize access="hasRole('ADD_DIVERSION_RECEIPT')">
						<c:set var="Dirv" value="true" />
						<li><a href="././Dirv"> Inward Diversion</a></li>
					</sec:authorize>
					<c:if test="${Dirv == false}">
						<sec:authorize access="hasRole('VIEW_DIVERSION_RECEIPT')">
							<li><a href="././viewDirv"> View Diversion Receipt</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="freshFromRbi" value="false" />
					<sec:authorize access="hasRole('ADD_FRESH_FROM_RBI')">
						<c:set var="freshFromRbi" value="true" />
						<li><a href="././freshFromRbi"> Fresh From RBI</a></li>
					</sec:authorize>
					<c:if test="${freshFromRbi == false}">
						<sec:authorize access="hasRole('VIEW_FRESH_FROM_RBI')">
							<li><a href="././viewFresh"> View Fresh From RBI</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="AddOtherBankReceipt" value="false" />
					<sec:authorize access="hasRole('ADD_OTHER_BANK_RECEIPT')">
						<c:set var="AddOtherBankReceipt" value="true" />
						<li><a href="././AddOtherBankReceipt"> Other Bank Receipt</a></li>
					</sec:authorize>
					<c:if test="${AddOtherBankReceipt == false}">
						<sec:authorize access="hasRole('VIEW_OTHER_BANK_RECEIPT')">
							<li><a href="././viewOtherBankReceipt"> View Other Bank
									Receipt</a></li>
						</sec:authorize>
					</c:if>
						<c:set var="AddBox" value="false" />
				<sec:authorize access="hasRole('ADD_BOX')">
					<c:set var="AddBox" value="true" />
					<li><a href="././createBox"> Add BOX</a></li>
				</sec:authorize>
				<c:if test="${AddBox == false}">
					<sec:authorize access="hasRole('VIEW_BOX')">
						<a href="././viewBoxDetails"><i class="fa fa-table fa-fw"></i>
							View Box Details</a>
					</sec:authorize>
				</c:if>
				</ul></li>

		</sec:authorize>
                       
                       
                       
                        </li>

                         <li> 
                      
                            <sec:authorize access="hasRole('VIEW_INDENT')">
			<a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                            <i class="mdi mdi-diamond"></i><span class="hide-menu">Vault Management</span></a>
                            
				<ul aria-expanded="false" class="collapse">

					<%-- <c:set var="addBinCapacity" value="false" />
					<sec:authorize access="hasRole('ADD_BIN_CAPACITY')">
						<c:set var="addBinCapacity" value="true" />
							<li><a href="././addBinCapacity"> Bin Capacity Define</a></li>
					</sec:authorize>
				<c:if test="${addBinCapacity == false}">
					<sec:authorize access="hasRole('VIEW_BIN_CAPACITY')">
						<li><a href="././viewBinCapacity"> View Bin Capacity List</a></li>
					</sec:authorize>
				</c:if>
				
				<c:set var="binMaster" value="false" />
					<sec:authorize access="hasRole('ADD_BIN')">
						<c:set var="binMaster" value="true" />
							<li><a href="././binMaster"> Bin Management</a></li>
					</sec:authorize>
				<c:if test="${binMaster == false}">
					<sec:authorize access="hasRole('VIEW_BIN')">
						<li><a href="././viewBinMaster"> View Bin List</a></li>
					</sec:authorize>
				</c:if> --%>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././AcceptSASIndent"> Accept Branch Payment</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././acceptCRAPayment"> Accept CRA Payment</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././acceptIndent"> Accept Processing Indent</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././acceptMutilatedIndent"> Accept Mutilated
								Indent</a></li>
					</sec:authorize>
					<%-- <sec:authorize access="hasRole('VIEW_INDENT')">
					<li><a href="././AcceptORVIndent"> Accept Payment Indent</a></li>
				</sec:authorize> --%>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././AcceptDORVIndent"> Accept Outward
								Diversion</a></li>
					</sec:authorize>


					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././AcceptOtherBankPayment"> Accept Other
								Bank Payment</a></li>
					</sec:authorize>


					<sec:authorize access="hasRole('VIEW_DEFINE_KEYSET')">
						<li><a href="././viewDefineKeySet"> Define Key Set</a></li>
					</sec:authorize>

					<c:set var="AssignVaultCustodian" value="false" />
					<sec:authorize access="hasRole('ADD_ASSIGN_VAULT_CUSTODIAN')">
						<c:set var="AssignVaultCustodian" value="true" />
						<li><a href="././AssignVaultCustodian"> Assign Vault
								Custodian</a></li>
					</sec:authorize>
					<c:if test="${AssignVaultCustodian == false}">
						<sec:authorize access="hasRole('VIEW_ASSIGN_VAULT_CUSTODIAN')">
							<li><a href="././viewAssignVaultCustodian"> View Assign
									Vault Custodian</a></li>
						</sec:authorize>
					</c:if>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././dailyBinRecon"> Daily Bin Recon</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././soiledPreparation">Preparation for
								Soiled Remittance</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././AcceptSoiledIndent"> Accept Soiled
								Indent</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././cashTransfer">Cash Transfer</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././disabledBin">Disabled Bin List</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././acceptAuditorIndent">Accept Auditor
								Indent</a></li>
					</sec:authorize>

				</ul></li>
		</sec:authorize>
                            
                        </li>

                        <li> 
                      
                       <sec:authorize access="hasRole('VIEW_INDENT')">
			<a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                            <i class="mdi mdi-table"></i><span class="hide-menu">Processing Room</span></a>
                            
				<ul aria-expanded="false" class="collapse">
                       

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././viewIndentRequest"> Indent Request</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('VIEW_MACHINE_ALLOCATION')">
						<li><a href="././viewForMachineAllocation"> Machine
								Allocation</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././fullValue"> Mutilated Full Value</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././processFreshFromRBI"> Process Fresh From
								RBI</a></li>
					</sec:authorize>


					<c:set var="processEntry" value="false" />
					<sec:authorize access="hasRole('ADD_PROCESSED_DATA')">
						<c:set var="processEntry" value="true" />
						<li><a href="././processEntry"> Processing Output</a></li>
					</sec:authorize>
					<c:if test="${processEntry == false}">
						<sec:authorize access="hasRole('VIEW_PROCESSED_DATA')">
							<li><a href="././viewProcess"> View Processed Data</a></li>
						</sec:authorize>
					</c:if>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././CRAPaymentForProcessing"> Payment
								Forwarded CRA</a></li>
					</sec:authorize>

					<%-- <sec:authorize access="hasRole('VIEW_BUNDLE_REQUEST_FOR_MACHINE')">
					<li><a href="././bundleRequestForMachineProcessing"> Machinewise Allocation</a></li>
				</sec:authorize> --%>

					<c:set var="discrepancy" value="false" />
					<sec:authorize access="hasRole('ADD_DISCREPANCY')">
						<c:set var="discrepancy" value="true" />
						<li><a href="././addDiscrepancy"> Discrepancy</a></li>
					</sec:authorize>
					<c:if test="${discrepancy == false}">
						<sec:authorize access="hasRole('VIEW_DISCREPANCY')">
							<li><a href="././viewDiscrepancy"> View Discrepancy Data</a></li>
						</sec:authorize>
					</c:if>
					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././addSuspenseDiscrepancy"> Suspense
								Discrepancy</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('VIEW_MACHINE_SOFTWARE_UPDATION')">
						<li><a href="././MachineSoftwareUpdation"> Machine
								Software Updation</a></li>
					</sec:authorize>

					<c:set var="MachineDowntimeUpdation" value="false" />
					<sec:authorize access="hasRole('ADD_MACHINE_DOWNTIME_UPDATION')">
						<c:set var="MachineDowntimeUpdation" value="true" />
						<li><a href="././MachineDowntimeUpdation"> Machine
								Downtime Updation</a></li>
					</sec:authorize>
					<c:if test="${MachineDowntimeUpdation == false}">
						<sec:authorize access="hasRole('VIEW_MACHINE_DOWNTIME_UPDATION')">
							<li><a href="././viewMachineDownTime"> View Machine
									Downtime Updation</a></li>
						</sec:authorize>
					</c:if>

					<c:set var="RepeatabilityTestInput" value="false" />
					<sec:authorize access="hasRole('ADD_REPEATABILITY_TEST_INPUT')">
						<c:set var="RepeatabilityTestInput" value="true" />
						<li><a href="././RepeatabilityTestInput"> Repeatability
								Test</a></li>
					</sec:authorize>
					<c:if test="${RepeatabilityTestInput == false}">
						<sec:authorize access="hasRole('VIEW_REPEATABILITY_TEST_INPUT')">
							<li><a href="././viewRepeatabilityTestInput"> View
									Repeatability Test</a></li>
						</sec:authorize>
					</c:if>

					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././machineMaintenance"> Machine Maintenance</a></li>
					</sec:authorize>

					<%-- <c:set var="RepeatabilityTestOutput" value="false" />
					<sec:authorize access="hasRole('ADD_REPEATABILITY_TEST_OUTPUT')">
						<c:set var="RepeatabilityTestOutput" value="true" />
							<li><a href="././RepeatabilityTestOutput"> Repeatability Test Output</a></li>
					</sec:authorize>
				<c:if test="${RepeatabilityTestOutput == false}">
					<sec:authorize access="hasRole('VIEW_REPEATABILITY_TEST_OUTPUT')">
						<li><a href="././viewRepeatabilityTestOutput"> View Repeatability Test Output</a></li>
					</sec:authorize>
				</c:if> --%>

					<%-- <c:set var="FreshCurrency" value="false" />
					<sec:authorize access="hasRole('ADD_FRESH_CURRENCY')">
						<c:set var="FreshCurrency" value="true" />
							<li><a href="././FreshCurrency"> Fresh Currency</a></li>
					</sec:authorize>
				<c:if test="${FreshCurrency == false}">
					<sec:authorize access="hasRole('VIEW_FRESH_CURRENCY')">
						<li><a href="././viewfreshCurrency"> View Fresh Currency</a></li>
					</sec:authorize>
				</c:if> --%>

				</ul></li>
		</sec:authorize>
                       
                        </li>


                        <li> 
                   
                        
                        <sec:authorize access="hasRole('VIEW_REPORTS')">
					<a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                            <i class="mdi mdi-file-check"></i><span class="hide-menu">Reports</span></a>
                            
				<ul aria-expanded="false" class="collapse">
					<sec:authorize access="hasRole('VIEW_REPORTS')">
						<!-- <li><a href="././dailyRecon"> Daily Recon</a></li> -->
						<li><a href="././icmcSummary"> ICMC Summary</a></li>
						<li><a href="././FIFO"> FIFO</a></li>
						<!-- <li><a href="././TE"> IO2 Statement</a></li> -->
						<li><a href="././IO2Reports"> IO2Reports</a></li>
						<li><a href="././currencyChestBook"> Currency Chest Book</a></li>
						<li><a href="././chestSlip"> Chest Slip</a></li>
						<li><a href="././cashBookDeposit"> Cash Book Deposit</a></li>
						<li><a href="././cashBookWithdrawal"> Cash Book
								Withdrawal</a></li>
						<!-- <li><a href="././machineWiseStatus"> Machine wise Status</a></li> -->
						<li><a href="././IRVReports"> IRV Reports</a></li>
						<!-- <li><a href="././IRVVoucher"> IRV Voucher</a></li> -->
						<li><a href="././ORVReports"> ORV Reports</a></li>
						<li><a href="././ORVVoucher"> ORV Voucher</a></li>
						<li><a href="././discFormat"> Discrepancy Report</a></li>
						<!-- <li><a href="././discrepancyReports"> Discrepancy Reports</a></li> -->
						<!-- <li><a href="././mutilatednotesSummary"> Mutilated Notes Summary</a></li> -->
						<!-- <li><a href="././discrepancyRPCFormat"> Discrepancy RPC Format</a></li> // Available in Processing Room Controller -->
						<li><a href="././inputOutputReports"> InputOutput Reports</a></li>
						<!-- <li><a href="././CashOutReport"> Daily Payment Report</a></li> -->
						<!-- <li><a href="././CashReceiptReports"> Daily Receipt Report</a></li> -->
						<li><a href="././binWiseSummaryReport"> BIN Wise Report</a></li>
						<li><a href="././binRegister1"> BIN Wise Register</a></li>
						<!-- <li><a href="././binCard"> BIN Card</a></li> -->
						<li><a href="././discrepancyDemand"> Discrepancy Demands</a></li>
						<li><a href="././fcrmFormat"> FCRM Uploadable Format</a></li>
						<li><a href="././iCoreFormat"> ICORE Uploadable Format</a></li>
						<li><a href="././keyMovementRegister"> key Movement
								Register</a></li>
						<li><a href="././DN2Report"> DN2 Register</a></li>
						<li><a href="././suspenseCashRegister"> Suspense Cash
								Register</a></li>
						<li><a href="././cashMovementRegister"> Cash Movement
								Register</a></li>
						<li><a href="././coinDistributionReport"> Coin
								Distribution Register</a></li>
						<li><a href="././machineDowntimeReport"> Machine Down
								Time Register</a></li>
						<!--<li><a href="././FIRattemptRegister"> FIR Attempt Register</a></li>
						<li><a href="././fakeNoteRegister"> Fake Note Register</a></li> -->
						<li><a href="././trainingRegister"> Training Register</a></li>
						<li><a href="././trainingRegisterReport"> Training
								Register Report</a></li>
					</sec:authorize>
				</ul></li>
		</sec:authorize>
                     
                        </li>
                        <li> 
                      
                            <sec:authorize access="hasRole('VIEW_INDENT')">
			 <a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                        <i class="mdi mdi-widgets"></i><span class="hide-menu">Migration</span></a>
				<ul aria-expanded="false" class="collapse">
					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././binTransaction"> BinTransaction</a></li>
					</sec:authorize>
				</ul></li>
		</sec:authorize>
                        </li>
                        <li>  <sec:authorize access="hasRole('VIEW_INDENT')">
			 <a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                        <i class="mdi mdi mdi-arrange-send-backward"></i><span class="hide-menu">Verification</span></a>
				<ul  aria-expanded="false" class="collapse">
					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././auditorIndent"> Auditor Indent</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('VIEW_INDENT')">
						<li><a href="././auditorProcessEntry"> Auditor Processing
								Output</a></li>
					</sec:authorize>

				</ul></li>
		</sec:authorize>
                            
                        </li>
<%--                         <li> <a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                        <i class="mdi mdi-laptop-windows"></i><span class="hide-menu">Branch</span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="new-indent-request">New Indent Request</a></li>
                                <li><a href="indent-request-history">Indent Request History</a></li>
                                <li><a href="indent-request-trend">Indent Request Trend</a></li>               
                            </ul>
                       
                       <sec:authorize access="hasRole('ADD_BRANCH_INDENT')">
			<a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                        <i class="mdi mdi mdi-source-branch"></i><span class="hide-menu">Branch</span></a>
				<ul aria-expanded="false" class="collapse">
					<c:set var="branchIndent" value="false" />
					<sec:authorize access="hasRole('ADD_BRANCH_INDENT')">
						<c:set var="branchIndent" value="true" />
						<li><a href="././branchIndent"> Branch Indent Request</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('VIEW_BRANCH_INDENT')">
						<li><a href="././viewBranchIndent"> View Branch Indent</a></li>
						<li><a href="././branchIndentHistory"> Indent Request History</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('BRANCH_RECEIPT')">
						<c:set var="branchReceipt" value="true" />
						<li><a href="././unprocessBranchReceipt"> Branch Outward Request</a></li>
					</sec:authorize>
				</ul></li>
		</sec:authorize>
                       
                       
                       
                       
                        </li>
                        
                        <li> 
                        <a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                        <i class="mdi mdi-currency-inr "></i>
                        <span class="hide-menu ">Chest </span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="chest-approval">Approve Indent Request</a></li>
                                <li><a href="chestHistory">Indent Request History</a></li> 
                                <li><a href="chestRequestTrend">Indent Request Trend</a></li>  
                                <li><a href="indentRequestDaywiseSummary">Indent Daywise Report</a></li>                              
                            </ul>
                        </li>
                        <li> 
                        <a class="has-arrow waves-effect waves-dark" href="#" aria-expanded="false">
                        <i class="mdi mdi-account-location"></i>
                        <span class="hide-menu">Vendor </span></a>
                            <ul aria-expanded="false" class="collapse">
                                <li><a href="vendorRegistration">New Registration</a></li>
                                <li><a href="vendorListing">Vendor Management</a></li> 
                                <li><a href="vendorEmployeeEnrollment">Employee Enrollment</a></li>
                                <li><a href="employeeManagement">Employee Management</a></li>                              
                            </ul>
                        </li> --%>
                      
                         
                    </ul>
                    
                </nav>
                <!-- End Sidebar navigation -->
            </div>
            <!-- End Sidebar scroll-->
            <!-- Bottom points-->
            <div class="sidebar-footer">
                <!-- item--><a href="" class="link" data-toggle="tooltip" title="Settings"><i class="ti-settings"></i></a>
                <!-- item--><a href="" class="link" data-toggle="tooltip" title="Email"><i class="mdi mdi-gmail"></i></a>
                <!-- item--><a href="" class="link" data-toggle="tooltip" title="Logout"><i class="mdi mdi-power"></i></a> </div>
            <!-- End Bottom points-->
        </aside>
        <!-- ============================================================== --> 
        <!-- End Left Sidebar - style you can find in sidebar.scss  -->
