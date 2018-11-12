<!DOCTYPE html>


<title>CMS : Chest Approval</title>

 <style type="text/css">
        .card-body .form-group {
    margin-bottom: 0;
}
 .card-body table{ margin-bottom: 0; }
 .card-body{ padding: 5px; }
 .card{ width: 100%; float: left; margin-top: 10px; }
 .table thead th{ vertical-align: top; }

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
                        <h3 class="text-themecolor m-b-0 m-t-0">Approve Indent Request</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Chest</a></li>
                            <li class="breadcrumb-item active">Approve Indent Request</li>
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
                                <table class="table table-striped table-bordered" id="editable-datatable">
                                    <thead>
                                        <tr>
                                            <th>Branch</th>
                                          
                                            <th></th>                                         
                                        </tr>
                                    </thead>
                                    <tbody>                    
                                        <tr id="1" class="gradeX">
                                            <td><select class="form-control">
                                            <option>All</option>
                                            <option>Hauz Khas</option><option>Malviya Nagar</option><option>Saket</option><option>Rohini </option><option>Okhla</option><option>Chattarpur</option></select></td>
                                 
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
                                     <tr style="background: #26344b; border-bottom: #000 2px solid;">
                                            <th></th>
                                            <th> </th>
                                            <th></th>  
                                             <th></th>
                                            <th></th>                                         
                                            <th colspan="10" style="text-align: center;" class="bg1">Notes</th>
                                            <th colspan="4" class="bg2">Coins</th>  
                                            <th></th>                                          
                                            <th></th>
                                            <th></th>

                                        </tr>
                                        <tr style="border-bottom: #999 1px solid">
                                            <th>
                                            <div class="demo-checkbox">
                                                <input type="checkbox" id="basic_checkbox_1" /><label for="basic_checkbox_1"></label>
                                            </div>
                                            </th>
                                            <th>
                                                Sr. No.
                                            </th>
                                             <th>IR Number</th>  
                                            <th>SR Number</th>  
                                            <th>Branch Code</th>  
                                            <th class="bg1">2000</th>
                                            <th class="bg1">1000</th>
                                            <th class="bg1">500</th>
                                            <th class="bg1">100</th>
                                            <th class="bg1">50</th>
                                            <th class="bg1">20</th>
                                            <th class="bg1">10</th>
                                            <th class="bg1">5</th>
                                            <th class="bg1">2</th>
                                            <th class="bg1">1</th>
                                            <th class="bg2">10</th>
                                            <th class="bg2">5</th>
                                            <th class="bg2">2</th>
                                            <th class="bg2">1</th>
                                             <th>Amount Requested</th> 
                                            <th> Approval Amount</th>
                                            <th>Action</th>
                                        </tr>
                                        <tr style="border-bottom: #fff 1px solid">
                                            <th>
                                            <div class="demo-checkbox">
                                                <input type="checkbox" id="basic_checkbox_2" /><label for="basic_checkbox_2"></label>
                                            </div>
                                            </th>
                                            <th>
                                              1
                                            </th>
                                            <th>IRHK-12345</th>
                                            <th>ICICI2334</th> 
                                            <th>HK</th>                                           
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                           <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th> 
                                           <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th>5,00,00</th> 
                                            <th>120,00,00</th>
                                            <th><button class="btn btn-success">Approve</button> &nbsp; <button class="btn btn-danger" style="margin-top: 10px;">Reject</button></th>

                                        </tr>
                                        <tr style="border-bottom: #fff 1px solid">
                                            <th>
                                            <div class="demo-checkbox">
                                                <input type="checkbox" id="basic_checkbox_3" /><label for="basic_checkbox_3"></label>
                                            </div>
                                            </th>
                                            <th>
                                              2
                                            </th>
                                            <th>IROK-12345</th>
                                            <th>ICICI2301</th>   
                                            <th>OK</th>                                           
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                           <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>

                                           <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th>120,00,00</th>
                                            <th>5,00,00</th> 
                                            <th><button class="btn btn-success">Approve</button> &nbsp; <button class="btn btn-danger" style="margin-top: 10px;">Reject</button></th>
                                        </tr>
                                        <tr>
                                            <th>
                                            <div class="demo-checkbox">
                                                <input type="checkbox" id="basic_checkbox_3" /><label for="basic_checkbox_3"></label>
                                            </div>
                                            </th>
                                            <th>
                                              3
                                            </th>
                                            <th>IRPP-12345</th>
                                            <th>ICICI2213</th> 
                                            <th>PP</th>                                            
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                           <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg1">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>

                                           <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th class="bg2">
                                            <div>20</div>
                                            <div><input type="" name="" value="20" style="width: 40px;"></div>
                                            </th>
                                            <th>5,00,00</th> 
                                            <th>120,00,00</th>
                                             <th><button class="btn btn-success">Approve</button> &nbsp; <button class="btn btn-danger" style="margin-top: 10px;">Reject</button></th>
                                        </tr>
</table>

                                </div>
                                <div class="right" style="text-align: right; ">
                                <table class="table table-striped table-bordered">
                                    <tr style="text-align:left">
                                    <td><b>Total Requested Amount </b>: INR <span style="color: #000; font-size: 24px;">20,00,000.00</span></td>
                                    <td><b>Total Approval Amount</b>: INR <span style="color: #000; font-size: 24px;">20,00,000.00</span></td>
                                    <td ><b>Remarks:</b><input type="" name="" class="form-control"></td>
                                    <td></td>
                                    </tr>
                                    <tr style="text-align:left">
                                    <td><b>Total Requested Amount  (In words)</b>: <span style="color: #000; font-size: 24px;">INR Twenty Lakhs Only</span></td>
                                   <td><b>Total Approval Amount (In words)</b>: <span style="color: #000; font-size: 24px;">INR Twenty Lakhs Only</span></td>
                                    <td><button class="btn-success btn">Approve Request</button> <button class="btn btn-danger">Reject</button> <button class=" btn">Reset</button></td>
                                    </tr>

                                </table>
                          
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