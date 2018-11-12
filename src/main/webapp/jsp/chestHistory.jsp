<!DOCTYPE html>


<title>CMS : Chest History</title>

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
            
            
            <!-- Container fluid  -->
            <!-- ============================================================== -->
         

            <!-- ============================================================== -->
            <!-- End Container fluid  -->
                        <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Bread crumb and right sidebar toggle -->
                <!-- ============================================================== -->
                <div class="row page-titles">
                 <div class="col-md-5 col-8 align-self-center">
                        <h3 class="text-themecolor m-b-0 m-t-0">Indent Request Approval History</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">Indent Request Approval History</li>
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
                                            <th>Status</th>
                                            <th>Branch Code</th>
                                            <th>From Date</th>
                                            <th>To Date</th>
                                            <th></th>                                         
                                        </tr>
                                    </thead>
                                    <tbody>                    
                                        <tr id="1" class="gradeX">
                                            <td><select class="form-control"><option>Submitted</option><option>Approved</option><option>Cash Dispatched</option><option>Cash Received</option><option>Rejected</option><option>Canceled</option></select></td>
                                            <td><select class="form-control">
                                            <option>All</option>
                                            <option>Hauz Khas</option><option>Malviya Nagar</option><option>Saket</option><option>Rohini </option><option>Okhla</option><option>Chattarpur</option></select></td>
                                            <td><input type="" name="" class="form-control"></td>
                                            <td><input type="" name="" class="form-control"> </td>
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
                                            <th>SR Number</th>
                                            <th>IR Number</th>
                                            <th>Branch Code</th>
                                            <th>Amount Requested</th>
                                            <th>Amount Approved</th> 
                                            <th>Indent Date</th> 
                                            <th>Requested By</th>    
                                            <th>Approved On</th> 
                                            <th>Approved By</th> 
                                            <th>Status</th>  
                                            <th>Action</th>  
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="1" class="gradeX">
                                            <td>01</td>
                                            <td>SR12345</td>
                                            <td>IRHK-12345  </td>
                                            <td>HK</td>
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>
                                            <td>05 Dec 2017 / 15:30 </td>
                                            <td>Mahesh Gupta </td>
                                            <td>05 Dec 2017 / 18:30 </td>
                                            <td>Ramesh Das </td>
                                            <td>Approved</td>
                                            <td><table>
                                                <tr>
                                                    <td><a href="" style="white-space: nowrap;">View Log</a></td>
                                                    <td><a href="">Reject</a></td>
                                                 

                                                </tr>
                                            </table></td>
                                                                                  
                                        </tr>
                                         <tr id="1" class="gradeX">
                                            <td>02</td>
                                            <td>SR12345</td>
                                            <td>IRHK-12345  </td>
                                            <td>HK</td>
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>
                                            <td>07 Dec 2017 / 15:30 </td>
                                            <td>Mahesh Gupta </td>
                                            <td> </td>
                                            <td></td>
                                            <td>Submitted</td>
                                            <td><table>
                                                <tr>
                                                    <td><a href="" style="white-space: nowrap;">View Log</a></td>
                                                    <td><a href="">Reject</a></td>
                                                   

                                                </tr>
                                            </table></td>
                                                                                  
                                        </tr>
                                        <tr id="1" class="gradeX">
                                            <td>02</td>
                                            <td>SR12345</td>
                                            <td>IRHK-12345  </td>
                                            <td>HK</td>
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>
                                            <td>07 Dec 2017 / 15:30 </td>
                                            <td>Mahesh Gupta </td>
                                            <td> </td>
                                            <td></td>
                                            <td>Canceled</td>
                                            <td><table>
                                                <tr>
                                                    <td><a href="" style="white-space: nowrap;">View Log</a></td>                                                   
                                                    <td><a href="">Reject</a></td>

                                                </tr>
                                            </table></td>
                                                                                  
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