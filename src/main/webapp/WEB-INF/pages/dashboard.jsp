<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Dashboard</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="exteriorNavBar.jsp"%>
<div class="container">
  <div class="row">
    <div class="col-md-10">
        <div class="col-sm-12 col-md-6">
          <div class="panel panel-default">
            <div class="panel-body">
              Trending Chart
              <div id="chart1"></div>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6">
          <div class="panel panel-default">
            <div class="panel-body">
              Category Chart
              <div id="chart"></div>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-12">
          <div class="panel panel-default">
            <div class="panel-body">
              Current Budget
              <div id="chart2"></div>
            </div>
          </div>
        </div>
    </div>
    <div class="col-md-2">
      <div class="panel panel-default">
        <div class="panel-body">
          Notifications <span class="badge">1</span><br/><br/>
          <ul>
            <li>
              <span class="glyphicon glyphicon-warning-sign"></span><a href="#"> Upcoming Return</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-body">
          Recent Activity<br/><br/>
          <ul>
            <li>
              <span class="glyphicon glyphicon-barcode"></span><a href="/receipt?receiptId=receipt1"> Smiths</a>
            </li>
            <li>
              <span class="glyphicon glyphicon-barcode"></span><a href="#"> Gamestop</a>
            </li>
            <li>
              <span class="glyphicon glyphicon-barcode"></span><a href="#"> Best Buy</a>
            </li>
            <li>
              <span class="glyphicon glyphicon-barcode"></span><a href="#"> Best Buy</a>
            </li>
            <li>
              <span class="glyphicon glyphicon-barcode"></span><a href="#"> Chevron</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
  function createChart() {
    $("#chart").kendoChart({
      title: {
        text: "Totals By Category"
      },
      legend: {
        visible: false
      },
      seriesDefaults: {
        type: "bar"
      },
      series: [{
        name: "Total Amount",
        data: [100, 75, 200, 150, 48, 500],
        color: "#9de219"
      }],
      valueAxis: {
        max: 600,
        line: {
          visible: false
        },
        minorGridLines: {
          visible: true
        }
      },
      categoryAxis: {
        categories: ["Food", "Clothing", "Gas", "Dining", "Entertainment", "Electronics"],
        majorGridLines: {
          visible: false
        }
      },
      tooltip: {
        visible: true,
        format: "/$/{0}",
        template: "#= series.name #: #= kendo.format('{0:C}',value) #"
      }
    });
  }

  $(document).ready(createChart);
  $(document).bind("kendo:skinChange", createChart);

  function createChart1() {
    var dataset = new Array(1545.65,1436.43,1567.23,1501.54,null);
    var highlighted = new Array(null,null,null,null,1512.71);
    $("#chart1").kendoChart({
      title: {
        text: "Trending By Months"
      },
      legend: {
        position: "top"
      },
      seriesDefaults: {
        type: "column"
      },
      series: [
        {
          name: 'History',
          data: dataset,
          color: '#9de219'
        },
        {
          name: 'Generated',
          data: highlighted,
          color: '#033939'
        }
      ],
      valueAxis: {
        line: {
          visible: false
        },
        axisCrossingValue: 0
      },
      categoryAxis: {
        categories: ["Jan", "Feb", "Mar", "April", "May"],
        line: {
          visible: false
        }
      },
      tooltip: {
        visible: true,
        format: "{0}%",
        template: "#= kendo.format('{0:C}',value) #"
      }
    });
  }

  $(document).ready(createChart1);
  $(document).bind("kendo:skinChange", createChart1);

  function createChart2() {
    $("#chart2").kendoChart({
      title: {
        position: "bottom",
        text: "Budget Distribution"
      },
      legend: {
        visible: false
      },
      chartArea: {
        background: ""
      },
      seriesDefaults: {
        labels: {
          visible: true,
          background: "transparent",
          template: "#= category #: \n #= value#%"
        }
      },
      series: [{
        type: "pie",
        startAngle: 150,
        data: [{
          category: "Clothing",
          value: 25,
          color: "#9de219"
        },{
          category: "Food",
          value: 27,
          color: "#90cc38"
        },{
          category: "Electronics",
          value: 11.5,
          color: "#068c35"
        },{
          category: "Dining",
          value: 9.5,
          color: "#006634"
        },{
          category: "Entertainment",
          value: 10,
          color: "#004d38"
        },{
          category: "Gas",
          value: 17,
          color: "#033939"
        }]
      }],
      tooltip: {
        visible: true,
        format: "{0}%"
      }
    });
  }

  $(document).ready(createChart2);
  $(document).bind("kendo:skinChange", createChart2);
</script>
</body>
</html>