<!DOCTYPE html>


<title>CMS : Add/Upload Branch</title>
 <!-- Bootstrap Core CSS -->
    <link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../assets/plugins/wizard/steps.css" rel="stylesheet">
    <!--alerts CSS -->
    <link href="../assets/plugins/sweetalert/sweetalert.css" rel="stylesheet" type="text/css">
    <!-- Custom CSS -->
    <link href="css/style.css" rel="stylesheet">
    <!-- You can change the theme colors from here -->
    <link href="css/colors/blue.css" id="theme" rel="stylesheet">

<body class="fix-header fix-sidebar card-no-border" oncontextmenu="return false;">
    <!-- ============================================================== -->
    <!-- Preloader - style you can find in spinners.css -->
    <!-- ============================================================== -->
    <div class="preloader">
        <svg class="circular" viewBox="25 25 50 50">
            <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" /> </svg>
    </div>
    <!-- ============================================================== -->
    <!-- Preloader - style you can find in spinners.css -->
    <!-- ============================================================== -->
  
    <!-- ============================================================== -->
    <!-- Main wrapper - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <div id="main-wrapper">
        <!-- ============================================================== -->
        <!-- Topbar header - style you can find in pages.scss -->
        <!-- ============================================================== -->
       
       
       <jsp:include page="mainHeader.jsp"/>
       
       
       
        <!-- ============================================================== -->
        <!-- End Topbar header -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <jsp:include page="sideMenu.jsp"/>
             <!-- ============================================================== -->
        <!-- End Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================= -->
        <!-- ============================================================== -->
        <!-- Page wrapper  -->
        <!-- ============================================================== -->
        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Container fluid  -->
            <!-- ============================================================== -->
            
            
           <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Bread crumb and right sidebar toggle -->
                <!-- ============================================================== -->
                <div class="row page-titles">
                    <div class="col-md-5 col-8 align-self-center">
                        <h3 class="text-themecolor m-b-0 m-t-0">Vendor Employee Enrollment</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">Vendor Employee Enrollment</li>
                        </ol>
                    </div>
                 <!--    <div class="col-md-7 col-4 align-self-center">
                        <div class="d-flex m-t-10 justify-content-end">
                            <div class="d-flex m-r-20 m-l-10 hidden-md-down">
                                <div class="chart-text m-r-10">
                                    <h6 class="m-b-0"><small>THIS MONTH</small></h6>
                                    <h4 class="m-t-0 text-info">$58,356</h4></div>
                                <div class="spark-chart">
                                    <div id="monthchart"></div>
                                </div>
                            </div>
                            <div class="d-flex m-r-20 m-l-10 hidden-md-down">
                                <div class="chart-text m-r-10">
                                    <h6 class="m-b-0"><small>LAST MONTH</small></h6>
                                    <h4 class="m-t-0 text-primary">$48,356</h4></div>
                                <div class="spark-chart">
                                    <div id="lastmonthchart"></div>
                                </div>
                            </div>
                            <div class="">
                                
                            </div>
                        </div>
                    </div> -->
                </div>
                <!-- ============================================================== -->
                <!-- End Bread crumb and right sidebar toggle -->
                <!-- ============================================================== -->
                <!-- ============================================================== -->
                <!-- Start Page Content -->
                <!-- ============================================================== -->
                <div class="row">
                    <div class="col-12">

                        <div class="card">
                            <div class="card-body wizard-content">
                               
                                <form action="#" class="tab-wizard wizard-circle">
                                    <!-- Step 1 -->
                                    <h6>Employee Details</h6>
                                    <section>
                                    <div class="card">
                            <div class="card-body">
                                <table class="table table-striped table-bordered" id="editable-datatable">
                                    <thead>
                                        <tr>
                                            <th>Vendor Name</th>
                                            <th>Employee Type</th>
                                                                   
                                        </tr>
                                    </thead>
                                    <tbody>                    
                                        <tr id="1" class="gradeX">
                                            <td>
                                            <select class="form-group form-control">
                                                    <option>Select</option>                                                 
                                            </select>
                                            </td>
                                            <td>
                                            <select class="form-group form-control">
                                                    <option>Select</option>  
                                                    <option>Custodian</option>
                                                    <option>Driver</option>
                                                    <option>Gunman</option>
                                                    <option>Loader</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        
                                    </tbody>
                          </table>
                        </div>
                  </div>
                                        <div class="card">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs profile-tab" role="tablist">
                                <li class="nav-item"> <a class="nav-link active" data-toggle="tab" href="#tab1" role="tab">Personal Details</a> </li>
                                <li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#tab2" role="tab">Official Details</a> </li>
                                <li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#tab3" role="tab">Address Details</a> </li>
                                <li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#tab4" role="tab">Profile</a> </li>
                                <li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#tab5" role="tab">Documents</a> </li>
                            </ul>
                            <!-- Tab panes -->
                            <div class="tab-content">
                              <div class="tab-pane active" id="tab1" role="tabpanel">
                                    <div class="card-body">
                                       <div class="table-responsive">
                                           <table class="table-striped table">
                                               <tr><th>Name</th><td><input class="form-control" name=""></td></tr>
                                               <tr><th>DOB</th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Father Name</th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Adhar Number</th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Phone No.</th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Email Id</th><td><input type="" name="" class="form-control"></td></tr>
                                           </table>
                                       </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="tab2" role="tabpanel">
                                    <div class="card-body">
                                        <div class="table-responsive">
                                           <table class="table-striped table">
                                               <tr><th>Vendor Emp Id</th><td><input class="form-control" name=""></td></tr>
                                               <tr><th>Vendor Email Id</th><td><input type="" name="" class="form-control"></td></tr>
                                             
                                           </table>
                                       </div>
                                    </div>
                                </div>
                                <!--second tab-->
                                <div class="tab-pane" id="tab3" role="tabpanel">
                                   <div class="card-body">
                                        <div class="table-responsive">
                                           <table class="table-striped table">
                                               <tr style="background: #1e88e5; color: #fff"><th>Current Address</th><td></td></tr>
                                               <tr><th>Address Line 1   </th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Address Line 2   </th><td><input type="" name="" class="form-control"></td></tr>
                                              <tr><th>Country  </th><td><select class="form-group form-control"><option>Select</option></select></td></tr>
                                               <tr>
                                               <th>State</th> 
                                                    <td>
                                                       <select class="form-group form-control"> <option>Select</option> <option>Delhi</option> <option>UP</option></select>
                                                    </td>
                                                </tr>
                                               <tr><th>City</th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Zipcode</th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr style="background: #1e88e5; color: #fff"><th>Permanent Address</th><td></td></tr>
                                               <tr><th>Address Line 1   </th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Address Line 2   </th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Country  </th><td><select class="form-group form-control"><option>Select</option></select></td></tr>
                                               <tr>
                                               <th>State</th> 
                                                    <td>
                                                       <select class="form-group form-control"> <option>Select</option> <option>Delhi</option> <option>UP</option></select>
                                                    </td>
                                                </tr>
                                               <tr><th>City</th><td><input type="" name="" class="form-control"></td></tr>
                                               <tr><th>Zipcode</th><td><input type="" name="" class="form-control"></td></tr>
                                               
                                               
                                           </table>
                                       </div>
                                   </div>
                                </div>
                                 <div class="tab-pane" id="tab4" role="tabpanel">
                                   <div class="card-body">
                                        <div class="table-responsive">
                                           <table class="table-striped table">
                                               <tr><th>Upload Image</th><td><input type="file" name=""></td></tr>
                                               <tr><th>Identification Mark (If Any)</th><td><input type="" name="" class="form-control"></td></tr>
                                           
                                               <tr><th>Blood Group</th>
                                                   <td>
                                                   <select class="form-control">
                                                       <option>Select</option>
                                                       <option>A+</option>
                                                       <option>A-</option>
                                                       <option>B+</option>
                                                       <option>B-</option>
                                                        <option>D</option>
                                                       <option>O</option>
                                                   </select>
                                                   </td>
                                               </tr>
                                               <tr><th>Medical Complication</th>
                                                   <td>
                                                  <textarea class="form-control" style="height: 150px;"></textarea>
                                                   </td>
                                               </tr>
                                               
                                              

                                           </table>
                                       </div>
                                   </div>
                                </div>
                              
                               <div class="tab-pane" id="tab5" role="tabpanel">
                                   <div class="card-body">
                                    <div class="table-responsive">
                                           <table class="table-striped table">
                                             <tr><th>Sr. No.</th><th>Document Type</th><th>File</th><td></td></tr>
                                         
                                               <tr>
                                               <th>1</th>
                                               <th>License</th> 
                                               <td><input type="file" name=""></td><td><button class="btn btn-success">Upload</button></td>
                                               </tr>
                                                <tr>
                                               <th>2</th>
                                               <th>Offical Proof</th> 
                                               <td><input type="file" name=""></td><td><button class="btn btn-success">Upload</button></td>
                                               </tr>
                                                <tr>
                                               <th>3</th>
                                               <th>Address Proof</th> 
                                               <td><input type="file" name=""></td><td><button class="btn btn-success">Upload</button></td>
                                               </tr>
                                               
                                                

                                           </table>
                                       </div>
                                   </div>
                                </div>
                                <div class="box"><button class="btn-success btn">Save</button>&nbsp;<button class=" btn">Cancel</button></div>
                              
                            </div>
                        </div>
                                        
                                        
                                    </section>
                                    <!-- Step 2 -->
                                    <h6>Biometric Details</h6>
                                    <section>
                                        <div class="row">
                                            <div class="col-md-6">

                                            <table class="table-striped table">
                                  
                                         
                                               <tr>                                         
                                               <th>Biometric Id:</th> 
                                               <td><input type="text" name="" class="form-control"></td>
                                               </tr>
                                               <tr>                                         
                                               <th>Biometric Image:</th> 
                                               <td><img src="download.jpg"></td>
                                               </tr>
                                               
                                               
                                                

                                           </table>

                                           
                                           
                                            
                                        </div>
                                    </section>
                                    <!-- Step 3 -->
                                    <h6>Verification Details</h6>
                                    <section>
                                        <div class="row">
                                           
                                            <div class="card-body">
                                    <div class="table-responsive">
                                           <table class="table-striped table">
                                             <tr>
                                             <th>Sr. No.</th>
                                             <th>Verification Type</th>
                                             <th>Verification Date</th>
                                             <th>Reference ID</th>
                                             <th>Remarks</th>
                                             <th>File</th>                                             
                                             <td></td>
                                             </tr>
                                         
                                               <tr>
                                                   <th>1</th>
                                                   <th>E-KYC</th> 
                                                   <th><input type="" name="" class="form-control form-group"></th>
                                                   <th><input type="" name="" class="form-control form-group"></th>
                                                   <td><textarea class="form-group form-control"></textarea></td>
                                                   <td><input type="file" name=""></td><td><button class="btn btn-success">Upload</button></td>
                                               </tr>
                                               <tr>
                                                   <th>2</th>
                                                   <th>Police Verification</th> 
                                                   <th><input type="" name="" class="form-control form-group"></th>
                                                   <th><input type="" name="" class="form-control form-group"></th>
                                                   <td><textarea class="form-group form-control"></textarea></td>
                                                   <td><input type="file" name=""></td><td><button class="btn btn-success">Upload</button></td>
                                               </tr>
                                               <tr>
                                                   <th>3</th>
                                                   <th>Bank Verification</th> 
                                                   <th><input type="" name="" class="form-control form-group"></th>
                                                   <th><input type="" name="" class="form-control form-group"></th>
                                                   <td><textarea class="form-group form-control"></textarea></td>
                                                   <td><input type="file" name=""></td><td><button class="btn btn-success">Upload</button></td>
                                               </tr>
                                               
                                               
                                                

                                           </table>
                                       </div>
                                   </div>
                                        </div>
                                    </section>
                                    <!-- Step 4 -->
                                    <h6>Final Approval</h6>
                                    <section>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="behName1">Approved By :</label>
                                                    <input type="text" class="form-control" id="behName1">
                                                </div>
                                                <div class="form-group">
                                                    <label for="participants1">Apporve Date</label>
                                                    <input type="text" class="form-control" id="participants1">
                                                </div>
                                               
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="decisions1">Comments</label>
                                                    <textarea name="decisions" id="decisions1" rows="4" class="form-control"></textarea>
                                                </div>                                               
                                            </div>
                                             <div class="col-md-12">
                                                <div class="form-group">
                                                    <label>Undertaking :</label>
                                                    <div class="c-inputs-stacked">
                                                        <label class="inline custom-control custom-checkbox block">
                                                            <input type="checkbox" class="custom-control-input"> <span class="custom-control-indicator"></span> <span class="custom-control-description ml-0">I certify that i have checked all the details and apporving this employee to be enrolled in the system.</span> </label>
                                                       
                                                    </div>
                                                </div>
                                             </div>
                                        </div>
                                    </section>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
              
                <!-- ============================================================== -->
                <!-- End PAge Content -->
                <!-- ============================================================== -->
                <!-- ============================================================== -->
                <!-- Right sidebar -->
                <!-- ============================================================== -->
                <!-- .right-sidebar -->
               
                <!-- ============================================================== -->
                <!-- End Right sidebar -->
                <!-- ============================================================== -->
            </div>
            
            
            
            
                       <!-- ============================================================== -->
            <!-- End Container fluid  -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- footer -->
            <!-- ============================================================== -->
          <jsp:include page="mainFooter.jsp"/>
            <!-- ============================================================== -->
            <!-- End footer -->
            <!-- ============================================================== -->
        </div>
        <!-- ============================================================== -->
        <!-- End Page wrapper  -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- All Jquery -->
      <!-- Sweet-Alert  -->
  <!--Custom JavaScript -->
  <script src="../assets/plugins/jquery/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="../assets/plugins/bootstrap/js/popper.min.js"></script>
    <script src="../assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <!-- slimscrollbar scrollbar JavaScript -->
    <script src="js/jquery.slimscroll.js"></script>
    <!--Wave Effects -->
    <script src="js/waves.js"></script>
    <!--Menu sidebar -->
    <script src="js/sidebarmenu.js"></script>
    <!--stickey kit -->
    <script src="../assets/plugins/sticky-kit-master/dist/sticky-kit.min.js"></script>
    <script src="../assets/plugins/sparkline/jquery.sparkline.min.js"></script>
    <!--Custom JavaScript -->
    <script src="js/custom.min.js"></script>
    <script src="../assets/plugins/moment/min/moment.min.js"></script>
    <script src="../assets/plugins/wizard/jquery.steps.min.js"></script>
    <script src="../assets/plugins/wizard/jquery.validate.min.js"></script>
    <!-- Sweet-Alert  -->
    <script src="../assets/plugins/sweetalert/sweetalert.min.js"></script>
    <script src="../assets/plugins/wizard/steps.js"></script>
    <!-- ============================================================== -->
    <!-- Style switcher -->
    <!-- ============================================================== -->
    <script src="../assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
    

</body>