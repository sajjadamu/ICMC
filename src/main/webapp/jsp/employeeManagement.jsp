<!DOCTYPE html>


<title>CMS : Employee Management</title>

   <style type="text/css">
        .card-body .form-group {
    margin-bottom: 0;
}
 .card-body table{ margin-bottom: 0; }
 .card-body{ padding: 5px; }
 .card{ width: 100%; float: left; margin-top: 10px; }

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
                        <h3 class="text-themecolor m-b-0 m-t-0">Vendor Employee Management</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">Vendor Employee Management</li>
                        </ol>
                    </div>
                   <!--   <div class="col-md-7 col-4 align-self-center">
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
                                <table class="table table-striped table-bordered" id="editable-datatable">
                                    <thead>
                                        <tr>
                                            <th>Vendor Type</th>
                                            <th>Vendor Name</th>
                                       
                                            <th></th>                                         
                                        </tr>
                                    </thead>
                                    <tbody>                    
                                        <tr id="1" class="gradeX">
                                            <td><select class="form-group form-control">
                                                    <option>CRA</option>
                                                    <option>CIT</option>
                                                    <option>DSB</option>
                                                    <option>Security</option>
                                                </select></td>
                                            <td><input type="" name="" class="form-control"></td>                                            
                                            <td class="center"><button class="btn btn-success">Search</button></td>                                        
                                        </tr>
                                        
                                    </tbody>
                          </table>
                        </div>
                        </div>


                           <div class="card">
                            <div class="card-body">
                                <table class="table table-striped table-bordered" id="editable-datatable">
                                    <thead>
                                        <tr>
                                            <th>Sr. No.</th>
                                            <th>Vendor Name </th>
                                            <th>Vendor Type </th>
                                            <th>Employee Name</th> 
                                            <th>Employee Type</th> 
                                            <th>Contact Details</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="1" class="gradeX">
                                            <td>01</td>
                                            <td>CMS</td>
                                            <td>CIT </td>
                                            <td>Rohit Sharma</td>
                                            <td>CIT Custodian</td>
                                            <td><span class="popupopen">View Contact Details</span>
                                            </td>
                                            <td> 
                                            <table>
                                                <tr>
                                                    <td><a href="">View</a></td>
                                                    <td><a href="">Edit</a></td>
                                                    <td><a href="">Delete</a></td>

                                                </tr>
                                            </table>
                                            </td>
                                           </td>                                                                                  
                                        </tr>
                                         <tr id="2" class="gradeX">
                                            <td>02</td>
                                            <td>CMS</td>
                                            <td>CIT </td>
                                            <td>Virat Kohli </td>
                                            <td>Driver </td>
                                            <td><table>
                                                <tr>
                                                    <td><span class="popupopen">View Contact Details</span></td>
                                                </tr>
                                            </table>
                                            </td>
                                            <td> 
                                            <table>
                                                <tr>
                                                    <td><a href="">View</a></td>
                                                    <td><a href="">Edit</a></td>
                                                    <td><a href="">Delete</a></td>

                                                </tr>
                                            </table>
                                            </td>
                                           </td>                                                                                  
                                        </tr>
                                         <tr id="3" class="gradeX">
                                            <td>03</td>
                                            <td>TATA</td>
                                            <td>DSB </td>
                                            <td>Yuvraj Singh </td>
                                            <td>DSB Custodian </td>
                                            <td><table>
                                                <tr>
                                                    <td><span class="popupopen">View Contact Details</span></td>
                                                </tr>
                                            </table>
                                            </td>
                                            <td> 
                                            <table>
                                                <tr>
                                                    <td><a href="">View</a></td>
                                                    <td><a href="">Edit</a></td>
                                                    <td><a href="">Delete</a></td>

                                                </tr>
                                            </table>
                                            </td>
                                           </td>                                                                                  
                                        </tr>
                                        
                                    </tbody>
                          </table></div>
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