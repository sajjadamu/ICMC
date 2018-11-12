<!DOCTYPE html>


<title>CMS : Indent Request Trend</title>
<style>
.card {
    width: 100%;
    float: left;
}
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
          <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Bread crumb and right sidebar toggle -->
                <!-- ============================================================== -->
                <div class="row page-titles">
                    <div class="col-md-5 col-8 align-self-center">
                        <h3 class="text-themecolor m-b-0 m-t-0">Indent Request Trend</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
                            <li class="breadcrumb-item active">Indent Request Trend</li>
                        </ol>
                    </div>
               <!--       <div class="col-md-7 col-4 align-self-center">
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
                                <h4 class="card-title">Indent Request & Approval Trend ( Last Seven Days )</h4>
                                <div class="flot-chart">
                                   <div id="chartdiv" style="width: 100%; height: 400px;"></div>
        <div style="margin-left:30px;">
            <input type="radio" checked="true" name="group" id="rb1" onclick="setDepth()">2D
            <input type="radio" name="group" id="rb2" onclick="setDepth()">3D
        </div>
                                </div>
                            </div>
                            </div>
                        </div>
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
<script src="../assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
    <script src="../amcharts/amcharts.js" type="text/javascript"></script>
      <script src="../amcharts/serial.js" type="text/javascript"></script>

        <script>
            var chart;

            var chartData = [
                {
                    "year": "10 Dec",
                    "europe": 2.5,
                    "namerica": 2.5,
                    "asia": 2.1,
                    "lamerica": 1.3,
                    "meast": 1.2,
                    "africa": 0.3
                },
                {
                    "year": "11 Dec",
                    "europe": 2.6,
                    "namerica": 2.7,
                    "asia": 2.2,
                    "lamerica": 1.3,
                    "meast": 1.3,
                    "africa": 0.4
                },
                 {
                    "year": "12 Dec",
                    "europe": 2.5,
                    "namerica": 2.5,
                    "asia": 2.1,
                    "lamerica": 1.3,
                    "meast": 1.2,
                    "africa": 0.3
                },
                {
                    "year": "13 Dec",
                    "europe": 2.6,
                    "namerica": 2.7,
                    "asia": 2.2,
                    "lamerica": 1.3,
                    "meast": 1.3,
                    "africa": 0.4
                },
                 {
                    "year": "14 Dec",
                    "europe": 2.5,
                    "namerica": 2.5,
                    "asia": 2.1,
                    "lamerica": 1.3,
                    "meast": 1.2,
                    "africa": 0.3
                },
                {
                    "year": "15 Dec",
                    "europe": 2.6,
                    "namerica": 2.7,
                    "asia": 2.2,
                    "lamerica": 1.3,
                    "meast": 1.3,
                    "africa": 0.4
                },
                {
                    "year": "16 Dec",
                    "europe": 2.8,
                    "namerica": 2.9,
                    "asia": 2.4,
                    "lamerica": 1.3,
                    "meast": 1.3,
                    "africa": 0.5
                }

            ];

            AmCharts.ready(function () {
                // SERIAL CHART
                chart = new AmCharts.AmSerialChart();
                chart.dataProvider = chartData;
                chart.categoryField = "year";
                chart.plotAreaBorderAlpha = 0.2;

                // AXES
                // category
                var categoryAxis = chart.categoryAxis;
                categoryAxis.gridAlpha = 0.1;
                categoryAxis.axisAlpha = 0;
                categoryAxis.gridPosition = "start";

                // value
                var valueAxis = new AmCharts.ValueAxis();
                valueAxis.stackType = "regular";
                valueAxis.gridAlpha = 0.1;
                valueAxis.axisAlpha = 0;
                chart.addValueAxis(valueAxis);

                // GRAPHS
                // first graph
                var graph = new AmCharts.AmGraph();
                graph.title = "2000";
                graph.labelText = "[[value]]";
                graph.valueField = "europe";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#C72C95";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // second graph
                graph = new AmCharts.AmGraph();
                graph.title = "1000";
                graph.labelText = "[[value]]";
                graph.valueField = "namerica";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#D8E0BD";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // third graph
                graph = new AmCharts.AmGraph();
                graph.title = "500";
                graph.labelText = "[[value]]";
                graph.valueField = "asia";
                graph.type = "column";
                graph.newStack = false; // this line starts new stack
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#B3DBD4";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // fourth graph
                graph = new AmCharts.AmGraph();
                graph.title = "200";
                graph.labelText = "[[value]]";
                graph.valueField = "lamerica";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#69A55C";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // fifth graph
                graph = new AmCharts.AmGraph();
                graph.title = "100";
                graph.labelText = "[[value]]";
                graph.valueField = "meast";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#B5B8D3";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // sixth graph
                graph = new AmCharts.AmGraph();
                graph.title = "50";
                graph.labelText = "[[value]]";
                graph.valueField = "africa";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#F4E23B";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span class='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // Second Line first graph
                var graph = new AmCharts.AmGraph();
                graph.title = "2000";
                graph.labelText = "[[value]]";
                graph.valueField = "europe";
                graph.type = "column";
                graph.newStack = true; // this line starts new stack
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#462cc7";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // second graph
                graph = new AmCharts.AmGraph();
                graph.title = "1000";
                graph.labelText = "[[value]]";
                graph.valueField = "namerica";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#9206d4";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // third graph
                graph = new AmCharts.AmGraph();
                graph.title = "500";
                graph.labelText = "[[value]]";
                graph.valueField = "asia";
                graph.type = "column";               
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#06d467";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // fourth graph
                graph = new AmCharts.AmGraph();
                graph.title = "200";
                graph.labelText = "[[value]]";
                graph.valueField = "lamerica";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#d45806";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // fifth graph
                graph = new AmCharts.AmGraph();
                graph.title = "100";
                graph.labelText = "[[value]]";
                graph.valueField = "meast";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#f43b55";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span style='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);

                // sixth graph
                graph = new AmCharts.AmGraph();
                graph.title = "50";
                graph.labelText = "[[value]]";
                graph.valueField = "africa";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                graph.lineColor = "#3b9ff4";
                graph.balloonText = "<span style='color:#555555;'>[[category]]</span><br><span class='font-size:14px'>[[title]]:<b>[[value]]</b></span>";
                chart.addGraph(graph);
                // LEGEND
                var legend = new AmCharts.AmLegend();
                legend.borderAlpha = 0.2;
                legend.horizontalGap = 10;
                chart.addLegend(legend);

                // WRITE
                chart.write("chartdiv");
            });

            // this method sets chart 2D/3D
            function setDepth() {
                if (document.getElementById("rb1").checked) {
                    chart.depth3D = 0;
                    chart.angle = 0;
                } else {
                    chart.depth3D = 25;
                    chart.angle = 30;
                }
                chart.validateNow();
            }
        </script>

</body>