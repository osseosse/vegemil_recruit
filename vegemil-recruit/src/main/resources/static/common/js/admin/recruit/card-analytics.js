/*=========================================================================================
    File Name: card-analytics.js
    Description: Card Analytics page content with Apexchart Examples
    ----------------------------------------------------------------------------------------
    Item Name: Vuexy  - Vuejs, HTML & Laravel Admin Dashboard Template
    Author: PIXINVENT
    Author URL: http://www.themeforest.net/user/pixinvent
==========================================================================================*/

var $textHeadingColor = '#5e5873';
var $strokeColor = '#ebe9f1';
var $labelColor = '#e7eef7';
var $avgSessionStrokeColor2 = '#ebf0f7';
var $budgetStrokeColor2 = '#dcdae3';
var $goalStrokeColor2 = '#51e5a8';
var $revenueStrokeColor2 = '#d0ccff';
var $textMutedColor = '#b9b9c3';
var $salesStrokeColor2 = '#df87f2';
var $white = '#fff';
var $earningsStrokeColor2 = '#28c76f66';
var $earningsStrokeColor3 = '#28c76f33';

var supportChartOptions;
var avgSessionChartOptions;
var revenueReportChartOptions;
var budgetChartOptions;
var goalChartOptions;
var revenueChartOptions;
var salesChartOptions;
var salesLineChartOptions;
var sessionChartOptions;
var customerChartOptions;
var orderChartOptions;
var earningsChartOptions;

var supportChart;
var avgSessionChart;
var revenueReportChart;
var budgetChart;
var goalChart;
var revenueChart;
var salesChart;
var salesLineChart;
var sessionChart;
var customerChart;
var orderChart;
var earningsChart;

var $supportTrackerChart = document.querySelector('#support-tracker-chart');
var $avgSessionChart = document.querySelector('#avg-session-chart');
var $revenueReportChart = document.querySelector('#revenue-report-chart');
var $budgetChart = document.querySelector('#budget-chart');
var $goalOverviewChart = document.querySelector('#goal-overview-chart');
var $revenueChart = document.querySelector('#revenue-chart');
var $salesChart = document.querySelector('#sales-chart');
var $salesLineChart = document.querySelector('#sales-line-chart');
var $sessionChart = document.querySelector('#session-chart');
var $customerChart = document.querySelector('#customer-chart');
var $productOrderChart = document.querySelector('#product-order-chart');
var $earningsChart = document.querySelector('#earnings-donut-chart');

$(window).on('load', function () {
  'use strict'; 
  console.log('onload')
});

function dailyChart(lineChartX,lineChartY){
	console.log('dailyChart')
	// Sales Line Chart //일별 지원자 수
	  // -----------------------------
	  salesLineChartOptions = {
	    chart: {
	      height: 240,
	      toolbar: { show: false },
	      zoom: { enabled: false },
	      type: 'line',
	      dropShadow: {
	        enabled: true,
	        top: 18,
	        left: 2,
	        blur: 5,
	        opacity: 0.2
	      },
	      offsetX: -10
	    },
	    stroke: {
	      curve: 'smooth',
	      width: 4
	    },
	    grid: {
	      borderColor: $strokeColor,
	      padding: {
	        top: -20,
	        bottom: 5,
	        left: 20
	      }
	    },
	    legend: {
	      show: false
	    },
	    colors: [$salesStrokeColor2],
	    fill: {
	      type: 'gradient',
	      gradient: {
	        shade: 'dark',
	        inverseColors: false,
	        gradientToColors: [window.colors.solid.primary],
	        shadeIntensity: 1,
	        type: 'horizontal',
	        opacityFrom: 1,
	        opacityTo: 1,
	        stops: [0, 100, 100, 100]
	      }
	    },
	    markers: {
	      size: 0,
	      hover: {
	        size: 5
	      }
	    },
	    xaxis: {
	      labels: {
	        offsetY: 5,
	        style: {
	          colors: $textMutedColor,
	          fontSize: '0.857rem'
	        }
	      },
	      axisTicks: {
	        show: false
	      },
	      categories: lineChartX,
	      axisBorder: {
	        show: false
	      },
	      tickPlacement: 'on'
	    },
	    yaxis: {
	      tickAmount: 5,
	      labels: {
	        style: {
	          colors: $textMutedColor,
	          fontSize: '0.857rem'
	        },
	        formatter: function (val) {
				console.log('fomatter', val);
	          return val > 999 ? (val / 1000).toFixed(1) + 'k' : val;
	        }
	      }
	    },
	    tooltip: {
	      x: { show: false }
	    },
	    series: [
	      {
	        name: 'day',
	        data: lineChartY
	      }
	    ]
	  };
	  salesLineChart = new ApexCharts($salesLineChart, salesLineChartOptions);
	  salesLineChart.render();
}

function orderChart(chung,nnp,osse){
	console.log('orderChart')
	// Product Order Chart //지원회사 통계
	// -----------------------------
	var total = chung + nnp + osse;
	var chungRatio = Math.round(chung/total*100);
	var nnpRatio = Math.round(nnp/total*100);
	var osseRatio = Math.round(osse/total*100);
	
	orderChartOptions = {
	    chart: {
	      height: 325,
	      type: 'radialBar'
	    },
		colors: ['#28c76f', '#1fe2ff', '#ff6305' ],  //넬보스코 사용시 '#9d5c1d'
	    stroke: { 
	      lineCap: 'round'
	    },
	    plotOptions: {
	      radialBar: {
	        size: 150,
	        hollow: {
	          size: '20%'
	        },
	        track: {
	          strokeWidth: '100%',
	          margin: 15
	        },
	        dataLabels: {
	          value: {
	            fontSize: '1rem',
	            colors: $textHeadingColor,
	            fontWeight: '500',
	            offsetY: 5
	          },
	          total: {
	            show: true,
	            label: 'Total',
	            fontSize: '1.286rem',
	            colors: $textHeadingColor,
	            fontWeight: '500',
	
	            formatter: function (w) {
	              // By default this function returns the average of all series. The below is just an example to show the use of custom formatter function
	              return total;
	            }
	          }
	        }
	      }
	    },
	    series: [chungRatio, nnpRatio, osseRatio], //%로 계산
	    labels: ['정식품', '자연과사람들', '오쎄']
	};
	orderChart = new ApexCharts($productOrderChart, orderChartOptions);
	orderChart.render();
}

function sessionChart(fieldCount,fieldList,fieldColor){
	console.log('sessionChart')
  // Session Chart //지원부문 도넛
  // ----------------------------------
  sessionChartOptions = {
    chart: {
      type: 'donut',
      height: 300,
      toolbar: {
        show: false
      }
    },
    dataLabels: {
      enabled: false
    },
    series: fieldCount,
    legend: { show: false },
    comparedResult: [2, -3, 8],
    labels: fieldList,
    stroke: { width: 0 },
    colors: fieldColor
  };
  sessionChart = new ApexCharts($sessionChart, sessionChartOptions);
  sessionChart.render();
}
