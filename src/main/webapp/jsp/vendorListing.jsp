<!DOCTYPE html>


<title>CMS : Vendor Listing</title>
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
                        <h3 class="text-themecolor m-b-0 m-t-0">Vendor Management</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">Vendor Management</li>
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
                                <button class="right-side-toggle waves-effect waves-light btn-success btn btn-circle btn-sm pull-right m-l-10"><i class="ti-settings text-white"></i></button>
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
                                            <th>ICMC</th>
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
                                            <td> <select class="form-group form-control">
                                                    <option>Hauz Khas</option>
                                                    <option>Pushpanjali</option>
                                                    
                                                </select> </td>
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
                                            <th>Registration Date</th> 
                                            <th>ICMC</th> 
                                            <th>Contact Details</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="1" class="gradeX">
                                            <td>01</td>
                                            <td>CMS</td>
                                            <td>CID </td>
                                            <td>05 Dec 2017 </td>
                                            <td>Huaz Khas </td>
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
                                            <td>CID </td>
                                            <td>05 Dec 2017 </td>
                                            <td>Huaz Khas </td>
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
                                            <td>CMS</td>
                                            <td>CID </td>
                                            <td>05 Dec 2017 </td>
                                            <td>Huaz Khas </td>
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
    
    <div class="popupbox">
        <div class="popupcontainer">
            <div class="popupmain">

                <div class="closepop">X</div>
                <h1>Contact Details </h1>
                <div class="popupbox_main">
                          
                               <div class="main" style="padding: 0 20px;">
                                   <table class="table table-striped table-bordered">
                                        <tr><th>Sr. No.</th><th>Name</th><th>Phone</th><th>Email Id</th></tr>
                                        <tr><td>1</td><td>Ranjay</td><td>+91-12345676</td><td>ranjay@icici.com</td></tr>
                                        <tr><td>2</td><td>Pradeep</td><td>+91-12345676</td><td>ranjay@icici.com</td></tr>
                                        <tr><td>3</td><td>Abhijeet</td><td>+91-12345676</td><td>ranjay@icici.com</td></tr>
                                        <tr><td>4</td><td>Rahul</td><td>+91-12345676</td><td>ranjay@icici.com</td></tr>
                                   </table>
                             
                           </div>
                </div>
            </div>
        </div>
    </div>
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- All Jquery -->
        <script src="../assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
    <script>    
    jQuery(".popupopen").click(function(){
            jQuery(".popupbox").show();
        
    })
    jQuery(".popupopen1").click(function(){
            jQuery(".popupbox1").show();

        
    })
    jQuery(".closepop").click(function(){
        jQuery(".popupbox").hide();
    })
    jQuery(".closepop1").click(function(){
        jQuery(".popupbox1").hide();
    })

 

</script>

</body>