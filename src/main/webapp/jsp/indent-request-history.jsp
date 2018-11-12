<!DOCTYPE html>


<title>CMS : Indent Request History</title>

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
             <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Bread crumb and right sidebar toggle -->
                <!-- ============================================================== -->
                <div class="row page-titles">
             <div class="col-md-5 col-8 align-self-center">
                        <h3 class="text-themecolor m-b-0 m-t-0">Indent Request History</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">Indent Request History</li>
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
                                            <th>From Date</th>
                                            <th>To Date</th>
                                            <th></th>                                         
                                        </tr>
                                    </thead>
                                    <tbody>                    
                                        <tr id="1" class="gradeX">
                                            <td><select class="form-control"><option>Submitted</option><option>Approved</option><option>Cash Dispatched</option><option>Cash Received</option><option>Rejected</option><option>Canceled</option><option>Closed</option></select></td>
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
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>
                                            <td>05 Dec 2017 / 15:30 </td>
                                            <td>Mahesh Gupta </td>
                                            <td>05 Dec 2017 / 18:30 </td>
                                            <td>Ramesh Das </td>
                                            <td>Closed</td>
                                            <td><table>
                                                <tr>
                                                    <td><span class="popupopen">View Log</span></td>
                                                    <td><a href="">Cancel</a></td>
                                                    <td><a href="">Repeat</a></td>

                                                </tr>
                                            </table></td>
                                                                                  
                                        </tr>
                                         <tr id="1" class="gradeX">
                                            <td>02</td>
                                            <td>SR12345</td>
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>
                                            <td>07 Dec 2017 / 15:30 </td>
                                            <td>Mahesh Gupta </td>
                                            <td>07 Dec 2017 / 18:30 </td>
                                            <td>Ramesh Das  </td>
                                            <td>Approved</td>
                                            <td><table>
                                                <tr>
                                                    <td><span class="popupopen1">View Log</span></td>
                                                    <td><a href="">Cancel</a></td>
                                                    <td><a href="">Repeat</a></td>

                                                </tr>
                                            </table></td>
                                                                                  
                                        </tr>
                                        <tr id="1" class="gradeX">
                                            <td>02</td>
                                            <td>SR12345</td>
                                            <td>INR 20,00,000 </td>
                                            <td>INR 15,00,000 </td>
                                            <td>07 Dec 2017 / 15:30 </td>
                                            <td>Mahesh Gupta </td>
                                            <td> </td>
                                            <td></td>
                                            <td>Canceled</td>
                                            <td><table>
                                                <tr>
                                                    <td><span class="popupopen">View Log</span></td>                                                   
                                                    <td><a href="">Repeat</a></td>
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
                <h1>Indent Request Status Log - SR 12345, Hauz Khas</h1>
                <div class="popupbox_main">
                            <div class="card">
                            <div class="card-body">
                                <ul class="timeline">
                                    <li>
                                        <div class="timeline-badge success"><img class="img-responsive" alt="user" src="../assets/images/users/1.jpg" alt="img"> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Indent Request</h4>                                                
                                            </div>
                                            <div class="timeline-body">
                                                <table class="table table-bordered table-striped "> 
                                                        <tr><th>Indent Date</th><td>05 Dec 2017 / 15:30</td></tr>
                                                        <tr><th>Requested By</th><td>Mahesh Gupta</td></tr>                                                        
                                                        <tr><th>Indent Value</th><td>20,00,000</td></tr>
                                                        <tr><td colspan="2">
                                                        <h6 class="slideboxbtn">View Denomination Details +</h6>
                                                        <table class="table table-bordered table-striped listone" >
                                                            <tr><th>Denomination</th><th>Packet / Bag</th></tr>
                                                            <tr><th>2000</th><td>100</td></tr>
                                                            <tr><th>500</th><td>200</td></tr>
                                                            <tr><th>10-Coins</th><td>20</td></tr>
                                                        </table>
                                                            
                                                        </td></tr>
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="timeline-inverted">
                                        <div class="timeline-badge warning"><img class="img-responsive" alt="user" src="../assets/images/users/2.jpg" alt="img"> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Indent Approval</h4>
                                            </div>
                                            <div class="timeline-body">                                               
                                                 <table class="table table-bordered table-striped "> 
                                                        <tr><th>Approved Date</th><td>05 Dec 2017 / 18:30</td></tr>
                                                        <tr><th>Approved By</th><td>Ramesh Das </td></tr>                                             
                                                        <tr><th>Indent Value</th><td>20,00,000</td></tr>
                                                        <tr><th>Approved Value</th><td>15,00,000</td></tr>
                                                        <tr><td colspan="2">
                                                        <h6 class="slideboxbtn1 ">View Denomination Details +</h6>
                                                        <table class="table table-bordered table-striped listtwo">
                                                            <tr><th>Denomination</th><th>Packet / Bag</th></tr>
                                                            <tr><th>2000</th><td>70</td></tr>
                                                            <tr><th>500</th><td>150</td></tr>
                                                            <tr><th>10-Coins</th><td>20</td></tr>
                                                        </table>
                                                            
                                                        </td></tr>
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-badge danger"><i class="fa fa-bomb"></i> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Cash Handover to CIT</h4>
                                            </div>
                                            <div class="timeline-body">                                               
                                                 <table class="table table-bordered table-striped "> 
                                                        <tr><th>Cash Handover Date</th><td>06 Dec 2017 / 09:30</td></tr>
                                                        <tr><th>Cash Handover By</th><td>Ram Kumar </td></tr>
                                                        <tr><th>Cash Handover To</th><td>Kunal Singh (CMS) </td></tr>                                        
                                                        <tr><th>Cash Value</th><td>15,00,000</td></tr>
                                                        <tr><td colspan="2">
                                                        <h6 class="slideboxbtn2">View Denomination Details +</h6>
                                                        <table class="table table-bordered table-striped listthree">
                                                            <tr><th>Denomination</th><th>Packet / Bag</th></tr>
                                                            <tr><th>2000</th><td>70</td></tr>
                                                            <tr><th>500</th><td>150</td></tr>
                                                            <tr><th>10-Coins</th><td>20</td></tr>
                                                        </table>
                                                            
                                                        </td></tr>
                                                       
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="timeline-inverted">
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Cash in Transit</h4>
                                            </div>
                                            <div class="timeline-body">
                                                 <table class="table table-bordered table-striped "> 
                                                        <tr><th>CIT Custodian</th><td>Kunal Singh (CMS)</td></tr>
                                                        <tr><th>Last Location</th><td>Malviya Nagar </td></tr>
                                                        <tr><th>Vehical No.</th><td> DL 3C AG 0786 </td></tr>
                                                        <tr><th>ETA</th><td>10:30 AM</td></tr>
                                                        <tr><th>Cash Value</th><td>15,00,000</td></tr>
                                                        <tr><td colspan="2">
                                                        <h6 class="slideboxbtn3">View Denomination Details +</h6>
                                                        <table class="table table-bordered table-striped listfour">
                                                            <tr><th>Denomination</th><th>Packet / Bag</th></tr>
                                                            <tr><th>2000</th><td>70</td></tr>
                                                            <tr><th>500</th><td>150</td></tr>
                                                            <tr><th>10-Coins</th><td>20</td></tr>
                                                        </table>
                                                            
                                                        </td></tr>
                                                       
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-badge info"><i class="fa fa-save"></i> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title"> Cash Received</h4>
                                            </div>
                                            <div class="timeline-body">
                                                <table class="table table-bordered table-striped "> 
                                                        <tr><th>Cash Received Date</th><td style="white-space: nowrap;">06 Dec 2017 / 11:00 AM</td></tr>
                                                        <tr><th>Cash Received By</th><td>Pradeep Singh</td></tr> 
                                                        <tr><th>Cash Value</th><td>15,00,000</td></tr>
                                                        <tr><th> Received Location</th><td>Hauz Khas</td></tr>
                                                        <tr><th> Location Pic</th><td><a href="">abc.jpg</a></td></tr>
                                                        <tr><td colspan="2">
                                                        <h6 class="slideboxbtn4">View Denomination Details +</h6>
                                                        <table class="table table-bordered table-striped listfive">
                                                            <tr><th>Denomination</th><th>Packet / Bag</th></tr>
                                                            <tr><th>2000</th><td>70</td></tr>
                                                            <tr><th>500</th><td>150</td></tr>
                                                            <tr><th>10-Coins</th><td>20</td></tr>
                                                        </table>
                                                            
                                                        </td></tr>

                                                       
                                                </table>
                                       
                                            </div>
                                        </div>
                                    </li>
                                    <li class="timeline-inverted">
                                        <div class="timeline-badge success"><i class="fa fa-graduation-cap"></i> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Request Closed</h4>
                                            </div>                                           
                                                <div class="timeline-body">                                     
                                                <table class="table table-bordered table-striped  "> 
                                                        <tr><th>Close Date</th><td style="white-space: nowrap;">06 Dec 2017 / 11:00 AM</td></tr>
                                                        <tr><th>Verified By</th><td>Subhash Rao</td></tr>
                                                        <tr><th>Closed By</th><td>Pradeep Singh</td></tr> 
                                                        <tr><th>Cash Value</th><td>15,00,000</td></tr> 
                                                       
                                                </table>
                                       
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </div>
    <div class="popupbox1">
        <div class="popupcontainer">
            <div class="popupmain">

                <div class="closepop1">X</div>
                <h1>Indent Request Status Log - SR 23456, Hauz Khas</h1>
                <div class="popupbox_main">
                            <div class="card">
                            <div class="card-body">
                                <ul class="timeline">
                                    <li>
                                        <div class="timeline-badge success"><img class="img-responsive" alt="user" src="../assets/images/users/1.jpg" alt="img"> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Indent Request</h4>                                                
                                            </div>
                                            <div class="timeline-body">
                                                <table class="table table-bordered table-striped "> 
                                                        <tr><th>Indent Date</th><td>05 Dec 2017 / 15:30</td></tr>
                                                        <tr><th>Requested By</th><td>Mahesh Gupta</td></tr>                                                        
                                                        <tr><th>Indent Value</th><td>20,00,000</td></tr>
                                                        <tr><td colspan="2">
                                                        <h6 class="slideboxbtn">View Denomination Details +</h6>
                                                        <table class="table table-bordered table-striped listone" >
                                                            <tr><th>Denomination</th><th>Packet / Bag</th></tr>
                                                            <tr><th>2000</th><td>100</td></tr>
                                                            <tr><th>500</th><td>200</td></tr>
                                                            <tr><th>10-Coins</th><td>20</td></tr>
                                                        </table>
                                                            
                                                        </td></tr>
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="timeline-inverted">
                                        <div class="timeline-badge warning"><img class="img-responsive" alt="user" src="../assets/images/users/2.jpg" alt="img"> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Indent Approval</h4>
                                            </div>
                                            <div class="timeline-body">                                               
                                                 <table class="table table-bordered table-striped "> 
                                                        <tr><th>Approved Date</th><td>05 Dec 2017 / 18:30</td></tr>
                                                        <tr><th>Approved By</th><td>Ramesh Das </td></tr>                                             
                                                        <tr><th>Indent Value</th><td>20,00,000</td></tr>
                                                        <tr><th>Approved Value</th><td>15,00,000</td></tr>
                                                        <tr><td colspan="2">
                                                        <h6 class="slideboxbtn1 ">View Denomination Details +</h6>
                                                        <table class="table table-bordered table-striped listtwo">
                                                            <tr><th>Denomination</th><th>Packet / Bag</th></tr>
                                                            <tr><th>2000</th><td>70</td></tr>
                                                            <tr><th>500</th><td>150</td></tr>
                                                            <tr><th>10-Coins</th><td>20</td></tr>
                                                        </table>
                                                            
                                                        </td></tr>
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-badge danger"><i class="fa fa-bomb"></i> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Cash Handover to CIT</h4>
                                            </div>
                                            <div class="timeline-body">                                               
                                                 <table class="table table-bordered table-striped "> 
                                                        <tr><th><span style="display: block; color: #ff0000; text-align: center; padding: 5px 0;">To be Processed</span></td></tr>
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="timeline-inverted">
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Cash in Transit</h4>
                                            </div>
                                            <div class="timeline-body">
                                                <table class="table table-bordered table-striped "> 
                                                        <tr><th><span style="display: block; color: #ff0000; text-align: center; padding: 5px 0;">To be Processed</span></td></tr>
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="timeline-badge info"><i class="fa fa-save"></i> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title"> Cash Received</h4>
                                            </div>
                                            <div class="timeline-body">
                                                <table class="table table-bordered table-striped "> 
                                                        <tr><th><span style="display: block; color: #ff0000; text-align: center; padding: 5px 0;">To be Processed</span></td></tr>
                                                </table>
                                       
                                            </div>
                                        </div>
                                    </li>
                                    <li class="timeline-inverted">
                                        <div class="timeline-badge success"><i class="fa fa-graduation-cap"></i> </div>
                                        <div class="timeline-panel">
                                            <div class="timeline-heading">
                                                <h4 class="timeline-title">Request Closed</h4>
                                            </div>                                           
                                                <div class="timeline-body">                                     
                                                <table class="table table-bordered table-striped "> 
                                                        <tr><th><span style="display: block; color: #ff0000; text-align: center; padding: 5px 0;">To be Processed</span></td></tr>
                                                </table>
                                       
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
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

    jQuery(".listone,.listtwo,.listthree,.listfour,.listfive").hide();
    jQuery(".slideboxbtn").click(function(){
          jQuery(".listone").slideToggle();
    })
    jQuery(".slideboxbtn1").click(function(){
          jQuery(".listtwo").slideToggle();
    })
    jQuery(".slideboxbtn2").click(function(){
          jQuery(".listthree").slideToggle();
    })
    jQuery(".slideboxbtn3").click(function(){
          jQuery(".listfour").slideToggle();
    })
    jQuery(".slideboxbtn4").click(function(){
          jQuery(".listfive").slideToggle();
    })

</script>

</body>