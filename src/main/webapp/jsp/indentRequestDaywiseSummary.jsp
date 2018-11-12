<!DOCTYPE html>


<title>CMS : Indent Request Approval History</title>
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
                        <h3 class="text-themecolor m-b-0 m-t-0">Indent Request Approval History</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">Indent Request Approval History</li>
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
                  <div class="card">
                            <div class="card-body">
                                <table class="table table-striped table-bordered" id="editable-datatable">
                                    <thead>
                                        <tr>
                                            
                                            <th>From Date</th>
                                            <th>To Date</th>
                                            <th></th>                                         
                                        </tr>
                                    </thead>
                                    <tbody>                    
                                        <tr id="1" class="gradeX">
                                            
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
                                             <th>Date</th>                                         
                                            <th>Total Amount Requested</th>
                                            <th>Total Amount Approved</th>
                                            <th></th> 
                                                                                  
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="1" class="gradeX">
                                            <td>01</td>
                                            <td>05 Dec 2017 </td>
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>     
                                            <td><span class="btn btn-success btn1_clk">+</span></td>                                                                              
                                        </tr>
                                        <div>

                                         <tr class="hide1">
                                            <td colspan="5">
                                                <table class="table">
                                                    <tr>
                                                        <th>IR No</th>
                                                        <th>SR No.</th>
                                                        <th>Branch</th>
                                                        <th>Requested Amount</th>
                                                        <th>Approved Amount</th>
                                                    </tr>   
                                                    <tr>
                                                        <td>IRHK-12345</td>
                                                        <td>SR12345 </td>
                                                        <td>Hauz Khas</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRMN-12345</td>
                                                        <td>SR12345 </td>
                                                        <td>Malviya Nagar</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRCH-12345</td>
                                                        <td>SR12234 </td>
                                                        <td>Chattarpur</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRSK-12345</td>
                                                        <td>SR13575 </td>
                                                        <td>Saket</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                </table>
                                            </td>
                                        </tr>
                                        </div>
                                         <tr id="1" class="gradeX">
                                            <td>02</td>
                                            <td>07 Dec 2017  </td>
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>
                                            <td><span class="btn btn-success btn2_clk">+</span></td>                                                                                  
                                        </tr>
                                        <tr class="hide2">
                                            <td colspan="5">
                                                <table class="table">
                                                    <tr>
                                                        <th>IR No</th>
                                                        <th>SR No.</th>
                                                        <th>Branch</th>
                                                        <th>Requested Amount</th>
                                                        <th>Approved Amount</th>
                                                    </tr>   
                                                    <tr>
                                                        <td>IRHK-12345</td>
                                                        <td>SR12345 </td>
                                                        <td>Hauz Khas</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRMN-12345</td>
                                                        <td>SR12345 </td>
                                                        <td>Malviya Nagar</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRSK-12345</td>
                                                        <td>SR12345 </td>
                                                        <td>Saket</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                </table>
                                            </td>
                                        </tr>
                                        <tr id="1" class="gradeX">
                                            <td>03</td>
                                            <td>07 Dec 2017 </td>                                          
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>  
                                            <td><span class="btn btn-success btn3_clk">+</span></td>            
                                        </tr>
                                        <tr class="hide3">
                                            <td colspan="5">
                                                <table class="table">
                                                    <tr>
                                                        <th>IR No</th>
                                                        <th>SR No.</th>
                                                        <th>Branch</th>
                                                        <th>Requested Amount</th>
                                                        <th>Approved Amount</th>
                                                    </tr>   
                                                    <tr>
                                                        <td>IRHK-12345</td>
                                                        <td>SR12345 </td>
                                                        <td>Hauz Khas</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRPB-12345</td>
                                                        <td>SR12532 </td>
                                                        <td>Punjabi Bagh</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRCH-12345</td>
                                                        <td>SR16534 </td>
                                                        <td>Chattarpur</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRNA-12345</td>
                                                        <td>SR153985 </td>
                                                        <td>New Ashok</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IRMN-12345</td>
                                                        <td>SR12345 </td>
                                                        <td>Malviya Nagar</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                    <tr>
                                                        <td>IROK-12345</td>
                                                        <td>SR12355 </td>
                                                        <td>Okhla</td>
                                                        <td>INR 20,00,000</td>
                                                        <td>INR 15,00,000</td>

                                                    </tr> 
                                                </table>
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
  <!-- ============================================================== -->
    <script src="../assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
    <script type="text/javascript">
         jQuery(".btn1_clk").click(function(){          
              jQuery(".hide1").slideToggle();           
        })
        jQuery(".btn2_clk").click(function(){          
              jQuery(".hide2").slideToggle();           
        })
        jQuery(".btn3_clk").click(function(){          
              jQuery(".hide3").slideToggle();           
        })

    </script>
</body>