<!DOCTYPE html>


<title>CMS : Vendor Registration</title>
 <style type="text/css">
        .card-body .form-group {
    margin-bottom: 0;
}
 .card-body table{ margin-bottom: 0; }
 .card-body{ padding: 5px; }
 .card{ width: 100%; float: left; margin-top: 10px; }
 .card-body .form-control { padding: 0 10px; min-height: 33px; font-size: 12px; }
    </style>

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
                        <h3 class="text-themecolor m-b-0 m-t-0">Vendor Registration</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">Vendor Registration</li>
                        </ol>
                    </div>
                    <!--  <div class="col-md-7 col-4 align-self-center">
                        <div class="d-flex m-t-10 justify-content-end">
                            <div class="d-flex m-r-20 m-l-10 hidden-md-down">
                                <div class="chart-text m-r-10">
                                    <h6 class="m-b-0"><small>This Month Indent Request</small></h6>
                                    <h4 class="m-t-0 text-info">INR 58,356</h4></div>
                                <div class="spark-chart">
                                    <div id="monthchart"></div>
                                </div>
                            </div>
                            <div class="d-flex m-r-20 m-l-10 hidden-md-down">
                                <div class="chart-text m-r-10">
                                    <h6 class="m-b-0"><small>This Month Indent Approval</small></h6>
                                    <h4 class="m-t-0 text-primary">INR 48,356</h4></div>
                                <div class="spark-chart">
                                    <div id="lastmonthchart"></div>
                                </div>
                            </div>
                            <div class="">
                                
                            </div>
                        </div>
                    </div> -->
                  <div class="card">
                            <div class="card-body">
                                <div class="col-12">
                                <table class="table table-striped table-bordered" id="editable-datatable">
                                    <thead>
                                            <tr>
                                                <th>Zone</th>
                                                <td><select class="form-group form-control">
                                                    <option>Select Zone</option>
                                                   
                                                </select></td>
                                            </tr>
                                            <tr>
                                                <th>Region</th>
                                                <td><select class="form-group form-control">
                                                    <option>Select Region</option>
                                                   
                                                </select></td>
                                            </tr>
                                            <tr>
                                                <th>ICMC</th>
                                                <td><select class="form-group form-control">
                                                    <option>Select ICMC</option>
                                                   
                                                </select></td>
                                            </tr>
                                            <tr>
                                                <th>Vendor Name</th>
                                                <td><input type="" name="" class="form-group form-control"></td>
                                            </tr>
                                             <tr>
                                                <th>Vendor Type</th>
                                                <td>
                                                <select class="form-group form-control">
                                                    <option>Select</option>
                                                    <option>CRA</option>
                                                    <option>CIT</option>
                                                    <option>DSB</option>
                                                    <option>Security</option>
                                                </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>MSP (Applicable for CRA)</th>
                                                <td>
                                                <select class="form-group form-control">
                                                    <option>Select</option>
                                                    <option>Tata</option>
                                                    <option>HCL</option>
                                                    
                                                </select>
                                                </td>
                                            </tr>
                                             <tr>
                                                <th style="vertical-align: top;">Address Line 1</th>
                                                <td><input type="" name="" class="form-group form-control"></td>
                                            </tr>
                                             <tr>
                                                <th style="vertical-align: top;">Address Line 2</th>
                                                <td><input type="" name="" class="form-group form-control"></td>
                                            </tr>
                                           <tr>
                                                <th style="vertical-align: top;">Country</th>
                                                <td>
                                                <select class="form-group form-control">
                                                    <option>Select</option>  
                                                    <option>India</option>                                                                        
                                                </select>
                                                </td>
                                            </tr>
                                             <tr>
                                                <th style="vertical-align: top;">State</th>
                                                <td><select class="form-group form-control">
                                                    <option>Select</option>
                                                    <option>Delhi</option>  
                                                    <option>UP</option>                                                                        
                                                </select>
                                                </td>
                                            </tr>
                                             <tr>
                                                <th style="vertical-align: top;">City</th>
                                                <td><input type="" name="" class="form-group form-control"></td>
                                            </tr>
                                             <tr>
                                                <th style="vertical-align: top;">Zipcode</th>
                                                <td><input type="" name="" class="form-group form-control"></td>
                                            </tr>
                                            <tr>
                                                <th style="vertical-align: top;">Contact Person</th>
                                                <td>
                                                <table>
                                                    <tr> <th style="white-space: nowrap;">Sr. No.</th><th>Name</th><th>Phone</th><th>Email Id</th> <th></th></tr>
                                                    <tr><td>1</td><td><input type="" name="" class="form-group form-control"></td><td><input type="" name="" class="form-group form-control"></td><td><input type="" name="" class="form-group form-control"></td><td><button class="btn-success btn">Add</button></td></tr>
                                                </table>
                                                </td>
                                            </tr>
                                            <tr><td></td><td><button class="btn-success btn">Save</button>&nbsp;<button class="btn">Reset</button></td></tr>
                                    </thead>
                                   
                                    </table>
                                    </div>
                        </div>
                        </div>


                           
                        </div>
                         


                <!-- ============================================================== -->
                <!-- End Bread crumb and right sidebar toggle -->
                <!-- ============================================================== -->
                <!-- ============================================================== -->
                <!-- Start Page Content -->
                <!-- ============================================================== -->
                <!-- Row -->
              
                <!-- Row -->
                <!-- Row -->
              
                <!-- Row -->
                
                <!-- Row -->
                <!-- Row -->
                  
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

</body>