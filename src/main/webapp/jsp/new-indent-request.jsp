<!DOCTYPE html>


<title>CMS : New Indent Request</title>

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
                        <h3 class="text-themecolor m-b-0 m-t-0">New Indent Request</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">New Indent Request</li>
                        </ol>
                    </div>                    

                     <!-- <div class="col-md-7 col-4 align-self-center">
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
                     <div class="col-md-12 col-4 align-self-center">
                        <div class="right" style="text-align: right; ">
                                <table class="table table-striped table-bordered" style="margin: 0; padding: 0;">
                                    <tr>
                                    <td><b>Total Amount of Indent Request: INR <span style="color: #000; font-size: 24px;"><strong>20,00,000.00</strong></span></b></td>
                                     </tr> 
                                </table>
                          
                        </div>
                     </div>
                  <div class="card">

                            <div class="card-body">
                                <table class="table table-striped table-bordered" id="editable-datatable">
                                    <thead>
                                        <tr>
                                            <th>Sr. No.</th>
                                            <th>Denomination</th>
                                            <th>Issuable Packet</th>
                                            <th>Issuable Value</th>
                                            <th>ATM Packet</th>
                                            <th>ATM Value</th>
                                            <th>Fresh Packet</th>
                                            <th>Fresh Value</th>                                         
                                            <th>Total Value</th>                                         
                                        </tr>
                                    </thead>
                                    <tbody>                    
                                        <tr id="1" class="gradeX">
                                            <td>01</td>       
                                            <td>2000</td>                               
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td>2000</td>
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>   
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>  
                                             <td class="center">8000</td>                                      
                                        </tr>                                        
                                       <tr id="2" class="gradeX">
                                            <td>02</td>  
                                            <td>1000</td>
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td>2000</td>
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>   
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>  
                                             <td class="center">8000</td>                                      
                                        </tr>   
                                        <tr id="3" class="gradeX">
                                            <td>03</td> 
                                             <td>500</td>                                     
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td>2000</td>
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>   
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>  
                                             <td class="center">8000</td>                                      
                                        </tr>   
                                        <tr id="4" class="gradeX">
                                            <td>04</td>    
                                            <td>200</td>                                   
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td>2000</td>
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>   
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>  
                                             <td class="center">8000</td>                                      
                                        </tr>   
                                        <tr id="5" class="gradeX">
                                            <td>05</td>   
                                             <td>100</td>                                     
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td>2000</td>
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>   
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000</td>  
                                             <td class="center">8000</td>                                      
                                        </tr> 
                                        </tbody>
                                        </table>
                                        <div class="denominationbox"><h2>Other Denominations</h2></div>
                                        <table class="pricetap table table-striped table-bordered">
                                        <thead>
                                        <tr>
                                           <th>Sr. No.</th>
                                            <th>Denomination</th>
                                            <th>Issuable Packet</th>
                                            <th>Issuable Value</th>
                                            <th>ATM Packet</th>
                                            <th>ATM Value</th>
                                            <th>Fresh Packet</th>
                                            <th>Fresh Value</th>                                         
                                            <th>Total Value</th>                                           
                                        </tr>
                                        </thead>
                                        <tbody>

                                            <tr id="6" class="gradeX">
                                                <td>01</td>
                                                <td>
                                                    <select><option>50</option><option>20</option><option>10</option><option>5</option><option>2</option><option>1</option></select>
                                                </td>                                       
                                                <td><input type="" name="" class="form-group form-control"> </td>
                                                <td>2000</td>
                                                <td><input type="" name="" class="form-group form-control"> </td>
                                                <td class="center">4000</td>   
                                                <td><input type="" name="" class="form-group form-control"> </td>
                                                <td class="center">4000</td>  
                                                 <td class="center">8000</td>  
                                                 <td><button class="btn">+</button></td>                                    
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
                                            <th>Coins</th>
                                            <th>Bags</th>
                                            <th>Amount</th>  
                                            <th></th>                                       
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="1" class="gradeX">
                                            <td>01</td>
                                            <td><select><option>10</option><option>5</option><option>2</option><option>1</option></select></td>
                                            <td><input type="" name="" class="form-group form-control"> </td>
                                            <td class="center">4000000</td>       
                                            <td> <button class="btn">+</button></td>                                 
                                        </tr>
                                       
                                       
                                        
                                    </tbody>
                          </table></div>
                        </div>
                        </div>
                         <div class="right" style="text-align: right; ">
                                <table class="table table-striped table-bordered">
                                    <tr style="text-align:left">
                                    <td colspan="5"><b>Total Amount of Indent Request</b>: INR <span style="color: #000; font-size: 24px;">20,00,000.00</span></td>

                                    </tr>
                                    <tr  style="text-align:left">
                                        <td colspan="5"><b>Total Amount of Indent Request (In words)</b>: <span style="color: #000; font-size: 24px;">INR Twenty Lakhs Only</span></td>
                                    </tr>
                                    <tr>
                                    <td><b>Remarks:</b></td>
                                    <td style="color: #000; font-size: 24px;"><input type="" name="" class="form-control"></td>                              
                                    <td><b>SR Number:</b></td>
                                    <td style="color: #000; font-size: 24px;"><input type="" name="" class="form-control"></td>
                                    <td><button class="btn-success btn">Submit Request</button> <button class=" btn">Reset</button></td></tr>
                                  
                                </table>
                          
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

            </div>
            <!-- ============================================================== -->
            <!-- End Container fluid  -->
            
            
            
            
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