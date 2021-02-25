var module =angular.module('app.controllers', ['ngProgressLite', 'ngSanitize']);

module.filter('to_trusted', ['$sce', function($sce){
    return function(text) {
        return $sce.trustAsHtml(text);
    };
}]);

google.load("visualization", "1", {packages:["corechart"]});
google.load("visualization", "1", {packages:["geomap"]});

//START UNCONFORMITY REPORT
module.controller('ReportController', function (ngProgressLite,$filter,$compile,$location,$rootScope,$scope) {

	ngProgressLite.set(Math.random());
	ngProgressLite.start();
	 var defOptions= {
	          title: "",
	          width: 900,
	          height: 600,
	          fontName: 'Calibri',
	          orientation:'vertical',
	          fontSize: 11,
	          titleFontSize: 18,
	          bold: true,
	          italic: true,
	          color: '#871b47',     // The color of the text.
	          auraColor: '#d799ae', // The color of the text outline.
	          opacity: 0.8         , // The transparency of the text.,
	          hAxis: {title: '', titleTextStyle: {color: 'blue'}}
	        };
	 
	 var texts=[];
	 texts=translate(texts);
	 
	 $scope.showCPMM_IPMMReport = function (){
		 ngProgressLite.set(Math.random());
		 ngProgressLite.start();
    	 $location.path('/cpmmIpmmReport');
    	
     }

	 $scope.close=function (){
		 $("#content2").animate({left:1500,opacity:0.1},1000,function(){
			 $location.path('/');
			 $rootScope.$apply();
	 	});
	 }
	  
	 
	 $scope.titleReport="SAFE Fleet LA CPMM-IPMM";
	 $scope.page1Title="LA RESULTS";
	 $scope.page2Title="General Data by Country";
	 $scope.page12Title="CPMM & IPMM by Country";
	 $scope.page3Title="General Data by Champion";
	 $scope.page4Title="Crash By Type And Champion";
	 $scope.page5Title="Sales&Non-Sales Crash";
	 $scope.page11Title="Sales&Non-Sales Graphics";
	 $scope.page6Title="Monthly CPMM";
	 $scope.page10Title="Monthly IPMM";
	 $scope.page7Title="Dashboard";
	 $scope.page8Title="Crash Subtypes";
	 $scope.page9Title="Incidents by Type";
	 
	 $scope.lblAno="Year";
	 $scope.lblMonth="Month";
	 	 
	 var d=new Date();
	 var list=[];
	 for(var i=d.getFullYear()-5;i<d.getFullYear()+2;i++){
		 list.push(i);
	 }
	 $scope.years=list;
	 
	 list=[];
	 for(var i=1;i<13;i++){
		 list.push(i);
	 }
	 $scope.months=list;

	 setTimeout(function(){
	 	$scope.filter={year:d.getFullYear(),month:d.getMonth()+1};
	 	$rootScope.$apply();
	 },100 );
	 
	 function tabChanxsge(div1,div2,div3){
		 var rand=Math.random();
		 if(rand<0.6){
			 ngProgressLite.set(rand);	 
		 }else{
			 ngProgressLite.set(0.3);
		 }
		 
		 ngProgressLite.start();
		 $("#"+div2).unbind().hide('slow',function(){$(this).empty();});
		 $("#"+div3).unbind().hide('slow',function(){$(this).empty();});

		 $("#"+div1).show();
		 $("#"+div1).css("opacity","0.1");
		 $("#"+div1).show();
		 $("#"+div1).animate({opacity:1},1000,function(){ngProgressLite.done();});
	 }
	 
	 $scope.tab2= function(){
		 initTab(2);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/databycountryandop?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				 $scope.dataByCountryAndOP=d;
				 
				 for(var i=0;i<$scope.dataByCountryAndOP.dataByCountry.length;i++){
					 for(var j=0; j<$scope.dataByCountryAndOP.dataByCountry[i].data.length;j++){
						 $.each($scope.dataByCountryAndOP.dataByCountry[i].data[j],function(index,value){
							 $scope.dataByCountryAndOP.dataByCountry[i].data[j][index]=format(value);
						 });	 
					 }
					 
				 }
				 
				 for(var i=0;i<$scope.dataByCountryAndOP.totalDataByOP.length;i++){
						 $.each($scope.dataByCountryAndOP.totalDataByOP[i],function(index,value){
							 $scope.dataByCountryAndOP.totalDataByOP[i][index]=format(value);
						 });	 
				 }
					
				  //angular.element(document.getElementById('content2')).scope().$apply(function(){
				//	  
				//	  });
				 
				   $('#reportview1').html($compile(getPartial('partialDataByCountry.html'))($scope));
				   $rootScope.$apply();
				   endTab(2);
				  
				   stikyFirstColumn('reportview1',"table");			  	  
						 
				
			  } 
			});
		 
	 }
	 
	 $scope.tab12= function(){
		 initTab(12);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/databycountryandop?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				 $scope.dataByCountryAndOP=d;
				
				 var chartData=new Array();
				 for(var i=0;i<$scope.dataByCountryAndOP.dataByCountry.length;i++){
					 var chartCPMMData=new Array();
					 var chartIPMMData=new Array();
					 chartCPMMData.push(['Country','CPMM']);
					 chartIPMMData.push(['Country','IPMM']);
					 for(var j=0; j<$scope.dataByCountryAndOP.dataByCountry[i].data.length;j++){
						 
						 if($scope.dataByCountryAndOP.dataByCountry[i].data[j].lineLabel=='Rep. Dom. / Jamaica'){
							 chartCPMMData.push(['Jamaica',parseFloat($scope.dataByCountryAndOP.dataByCountry[i].data[j].cpmm)]);
							 chartIPMMData.push(['Jamaica',parseFloat($scope.dataByCountryAndOP.dataByCountry[i].data[j].ipmm)]);
							 chartCPMMData.push(['Dominican Republic',parseFloat($scope.dataByCountryAndOP.dataByCountry[i].data[j].cpmm)]);
							 chartIPMMData.push(['Dominican Republic',parseFloat($scope.dataByCountryAndOP.dataByCountry[i].data[j].ipmm)]);
						 }else{
							 chartCPMMData.push([$scope.dataByCountryAndOP.dataByCountry[i].data[j].lineLabel,parseFloat($scope.dataByCountryAndOP.dataByCountry[i].data[j].cpmm)]);
							 chartIPMMData.push([$scope.dataByCountryAndOP.dataByCountry[i].data[j].lineLabel,parseFloat($scope.dataByCountryAndOP.dataByCountry[i].data[j].ipmm)]);
						 }
					 }
					 chartData.push([chartCPMMData,chartIPMMData]);
				 }
				 
				 
				 for(var i=0;i<$scope.dataByCountryAndOP.dataByCountry.length;i++){
					 for(var j=0; j<$scope.dataByCountryAndOP.dataByCountry[i].data.length;j++){
						 $.each($scope.dataByCountryAndOP.dataByCountry[i].data[j],function(index,value){
							 $scope.dataByCountryAndOP.dataByCountry[i].data[j][index]=format(value);
						 });
						 
					 }
					 
				 }
				 
				 $scope.chartData=chartData;
				 
				 
					
				  //angular.element(document.getElementById('content2')).scope().$apply(function(){
				//	  
				//	  });
				 
				   $('#reportview1').html($compile(getPartial('partialCPMMIPMMByCountry.html'))($scope));
				   $rootScope.$apply();
				   
				   
				  
			       //options['dataMode'] = 'regions';
			       
				     
				   endTab(12);
				  
				
			  } 
			});
		 
	 }
	 
	 $scope.showTabCPMMByCountry= function(i){
		 var divId='ci'+i;
		 $('#'+divId).removeClass('OpenedIPMM');
		 if( ! $('#'+divId).hasClass('OpenedCPMM') ){
			 $('#'+divId).addClass('OpenedCPMM');
			 $("#"+divId).empty();
			 var options =   $.extend($.extend({},defOptions), {
			  	  orientation:'horizontal', 
			      title: "CPMM BY COUNTRY",
			      dataMode:'regions',
			      colors:[0x3300FF, 0x000033]
			      //region:'005'
			  });
			   var data = google.visualization.arrayToDataTable( $scope.chartData[i][0]);
		   
		       var container = document.getElementById(divId);
		       var geomap = new google.visualization.GeoMap(container);

		       geomap.draw(data, options);
		       
		      		      
		       $("#"+divId).show();
		 }else{
			 $('#'+divId).removeClass('OpenedCPMM');
			 $("#"+divId).empty();
		 }	     
	 };
	 
	 $scope.showTabIPMMByCountry= function(i){
		 var divId='ci'+i;
		 $('#'+divId).removeClass('OpenedCPMM');
		 if( ! $('#'+divId).hasClass('OpenedIPMM') ){
			 $('#'+divId).addClass('OpenedIPMM');
			 $("#"+divId).empty();
		 
			 var options =   $.extend($.extend({},defOptions), {
			  	  orientation:'horizontal', 
			      title: "IPMM BY COUNTRY",
			      dataMode:'regions',
			      colors:[0x3300FF, 0x000033]
			      //region:'005'
			  });
			 
			 var data = google.visualization.arrayToDataTable( $scope.chartData[i][1]);
			   
		       var container = document.getElementById(divId);
		       var geomap = new google.visualization.GeoMap(container);

		       geomap.draw(data, options);
		 }else{
			 $('#'+divId).removeClass('OpenedIPMM');
			 $("#"+divId).empty();
		 }	     
	 };
	 
	 $scope.tab3= function(){
		 initTab(3);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/databychampion?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				 $scope.dataByChampion=d;
				 
				 for(var i=0;i<$scope.dataByChampion.dataByChampion.length;i++){
					 for(var j=0; j<$scope.dataByChampion.dataByChampion[i].data.length;j++){
						 $.each($scope.dataByChampion.dataByChampion[i].data[j],function(index,value){
							 $scope.dataByChampion.dataByChampion[i].data[j][index]=format(value);
						 });	 
					 }
					 
				 }
					
				  //angular.element(document.getElementById('content2')).scope().$apply(function(){
				//	  
				//	  });
				 
				   $('#reportview1').html($compile(getPartial('partialDataByChampion.html'))($scope));
				   $rootScope.$apply();
				   
				   stikyFirstColumn('reportview1',"table");		
				   
				   endTab(3);
				  
			  } 
			});
		 
	 };
	 
	 $scope.tab4= function(){
		 initTab(4);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/crashByType?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				 $scope.crashByType=d;
				 
				 for(var i=0;i<$scope.crashByType.data.length;i++){
					 for(var j=1;j<$scope.crashByType.data[i].data.length;j++){
						 $scope.crashByType.data[i].data[j][1]=parseFloat($scope.crashByType.data[i].data[j][1]);
					 }
					 
					 var head=new Array();
					
					 for(var j=0;j<$scope.crashByType.data[i].dataChampion.length;j++){
						 if(j==0){
							 head=head.concat($scope.crashByType.data[i].dataChampion[0]);
						 }else{
							 var sumTotal=0;
							 for(var k=1;k<$scope.crashByType.data[i].dataChampion[j].length;k++){
								 $scope.crashByType.data[i].dataChampion[j][k]=parseInt($scope.crashByType.data[i].dataChampion[j][k]);
								 sumTotal+=parseInt($scope.crashByType.data[i].dataChampion[j][k]);
							 }
							 
							 $scope.crashByType.data[i].dataChampion[j][0]=$scope.crashByType.data[i].dataChampion[j][0]+" ("+sumTotal+")";
							 
						 }
					 }
					 
					 $scope.crashByType.data[i].dataChampionHead=head;
				 }
				    //angular.element(document.getElementById('content2')).scope().$apply(function(){
				//	  
				//	  });
				 
				   $('#reportview1').html($compile(getPartial('partialCrashByType.html'))($scope));
				   $rootScope.$apply();
				  
				  
				   
				   endTab(4);
				  
			}});
		 
	 };
	 
	 $scope.showTabCrashByType= function(i){
		 var divId='crashBy'+i;
		 $('#'+divId).removeClass('OpenedCH');
		 if( ! $('#'+divId).hasClass('OpenedT') ){
			 $('#'+divId).addClass('OpenedT');
			 $("#"+divId).empty();
		      var options =   $.extend($.extend({},defOptions), {
				  	  orientation:'horizontal', 
				      title: "Crash By Type"
			   });
			   var data = google.visualization.arrayToDataTable( $scope.crashByType.data[i].data);
			   
		       var chart = new google.visualization.PieChart(document.getElementById(divId));
		       chart.draw(data, options);
		       $("#"+divId).show();
		 }else{
			 $('#'+divId).removeClass('OpenedT');
			 $('#'+divId).empty();
		 }	     
	 };
	 
	 $scope.showTabCrashByChampion= function(i){
		 var divId='crashBy'+i;
		 $('#'+divId).removeClass('OpenedT');
		 if( ! $('#'+divId).hasClass('OpenedCH') ){
			 $('#'+divId).addClass('OpenedCH');
			 $("#"+divId).empty();
			 var options =   $.extend($.extend({},defOptions), {
			  	  orientation:'horizontal', 
			      title: "Crash By Champion",
			      legend: { position: 'top', maxLines: 3 },
			  	  bar: { groupWidth: '75%' },
			     isStacked: true,
			 	});
			  var data = google.visualization.arrayToDataTable($scope.crashByType.data[i].dataChampion);
			   
		      var chart = new google.visualization.BarChart(document.getElementById(divId));
		      chart.draw(data, options);
		      $("#"+divId).show(); 
		 }else{
			 $('#'+divId).removeClass('OpenedCH');
			 $('#'+divId).empty();
		 }
		  
	 };
	 
	 $scope.tab5= function(){
		 initTab(5);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/databysectorandop?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				 $scope.dataBySectorAndOP=d;
				 
				 for(var i=0;i<$scope.dataBySectorAndOP.dataBySector.length;i++){
					 for(var j=0; j<$scope.dataBySectorAndOP.dataBySector[i].data.length;j++){
						 $.each($scope.dataBySectorAndOP.dataBySector[i].data[j],function(index,value){
							 $scope.dataBySectorAndOP.dataBySector[i].data[j][index]=format(value);
						 });	 
					 }
					 
				 }
					
				  //angular.element(document.getElementById('content2')).scope().$apply(function(){
				//	  
				//	  });
				 
				   $('#reportview1').html($compile(getPartial('partialDataBySector.html'))($scope));
				   $rootScope.$apply();
				   
				   
				   stikyFirstColumn('reportview1',"table");		
				   
				   endTab(5);
				  
				
			  } 
			});
		 
	 }
	 
	 $scope.tab11= function(){
		 initTab(11);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/databysectorandop?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				 $scope.dataBySectorAndOP=d;
				 
				 var  dataCharts=new Array();
				 for(var i=0;i<$scope.dataBySectorAndOP.dataBySector.length;i++){
					 var  data=new Array();
					 data.push(["Country","Non Sales","Sales"]);
					 for(var j=0; j<$scope.dataBySectorAndOP.dataBySector[i].data.length;j++){
						  data.push([$scope.dataBySectorAndOP.dataBySector[i].data[j].lineLabel,parseFloat(format($scope.dataBySectorAndOP.dataBySector[i].data[j].percentageNonSales)),parseFloat(format($scope.dataBySectorAndOP.dataBySector[i].data[j].percentageSales))]);
					 }
					 dataCharts.push(data);
				 }
			
				 for(var i=0;i<$scope.dataBySectorAndOP.dataBySector.length;i++){
					 for(var j=0; j<$scope.dataBySectorAndOP.dataBySector[i].data.length;j++){
						 $.each($scope.dataBySectorAndOP.dataBySector[i].data[j],function(index,value){
							 $scope.dataBySectorAndOP.dataBySector[i].data[j][index]=format(value);
						 });	 
					 }
					 
				 }
					
			   $('#reportview1').html($compile(getPartial('partialDataBySectorCharts.html'))($scope));
			   $rootScope.$apply();
			   
			   var options =   $.extend($.extend({},defOptions), {
				  	  orientation:'horizontal', 
				      title: "Sales&Non-Sales Crash",
				      colors: ['#ADAF29', '#3891A8'],
				      isStacked:true
			     });
				 for(var i=0;i<dataCharts.length;i++){
				   var data = google.visualization.arrayToDataTable( dataCharts[i]);
				   var divId='sector'+i;
				   var chart = new google.visualization.BarChart(document.getElementById(divId));
				   chart.draw(data, options);
				 }
				 
			   
			   endTab(11);
				  
				
			  } 
			});
		 
	 }
	 
	 $scope.tab6= function(){
		 initTab(6);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/databymonth?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				   $scope.monthly=d;
				 
				  
				   $('#reportview1').html($compile(getPartial('partialMonthlyCPMM.html'))($scope));
				   $rootScope.$apply();
				  
				   var chartDataCPMM=new Array();
				   chartDataCPMM.push(["Month","CPMM","GOAL"]);
				   var chartDataCRASH=new Array();
				   chartDataCRASH.push(["Month","Crashes"]);
				   for(var i=0; i<$scope.monthly.length; i++){
					   var row=$scope.monthly[i];
					   chartDataCPMM.push([row.month,row.cpmm,row.cpmmGoal]);   
					   chartDataCRASH.push([row.month,row.totalCrashes]);
				   }
				 
				   var options =   $.extend($.extend({},defOptions), {
					  	  orientation:'horizontal', 
					      title: "CPMM VS GOAL",
					      series: {
					            0: { lineWidth: 4 },
					            1: { lineWidth: 2 }
					          },
					          colors: ['#e2431e', '#b9c246'],
					          vAxis:{
					        	  minValue:0
					          }
				   });
				   var data = google.visualization.arrayToDataTable( chartDataCPMM);
				   var divId='chartCPMM';
				   var chart = new google.visualization.LineChart(document.getElementById(divId));
				   chart.draw(data, options);
				   
				   var options =   $.extend($.extend({},defOptions), {
					  	  orientation:'horizontal', 
					      title: "CRESHES",
					      colors: ['#ADAF29']
				   });
				   var data = google.visualization.arrayToDataTable( chartDataCRASH);
				   var divId='chartCRASH';
				   var chart = new google.visualization.BarChart(document.getElementById(divId));
				   chart.draw(data, options);
				   
				   
				   endTab(6);
				  
				
			  } 
			});
		 
	 }
	 
	 $scope.tab10= function(){
		 initTab(10);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/databymonth?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				   $scope.monthly=d;
				 
				  
				   $('#reportview1').html($compile(getPartial('partialMonthlyIPMM.html'))($scope));
				   $rootScope.$apply();
				  
				   var chartDataINJURED=new Array();
				   chartDataINJURED.push(["Month","Crashes with employee injured"]);
				   var chartDataIPMM=new Array();
				   chartDataIPMM.push(["Month","IPMM","GOAL"]);
				   for(var i=0; i<$scope.monthly.length; i++){
					   var row=$scope.monthly[i];
					   chartDataINJURED.push([row.month,row.injured]);   
					   chartDataIPMM.push([row.month,row.ipmm,row.ipmmGoal]);
				   }
				 
				   var options =   $.extend($.extend({},defOptions), {
					  	  orientation:'horizontal', 
					      title: "IPMM VS GOAL",
					      series: {
					            0: { lineWidth: 4 },
					            1: { lineWidth: 2 }
					          },
					          colors: ['#e2431e', '#b9c246'],
					          vAxis:{
					        	  minValue:0
					          }
				   });
				   
				   var data = google.visualization.arrayToDataTable( chartDataIPMM);
				   var divId='chartIPMM';
				   var chart = new google.visualization.LineChart(document.getElementById(divId));
				   chart.draw(data, options);
				   
				   var options =   $.extend($.extend({},defOptions), {
					  	  orientation:'horizontal', 
					      title: "CRESHES WITH EMPLOYEE INJURED",
					      colors: ['#ADAF29']
					     
				   });
				   var data = google.visualization.arrayToDataTable( chartDataINJURED);
				   var divId='chartINJURED';
				   var chart = new google.visualization.BarChart(document.getElementById(divId));
				   chart.draw(data, options);
				   
				   endTab(10);
				  
				
			  } 
			});
		 
	 }
	 
	 
	 $scope.tab7= function(){
		 initTab(7);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/dashboard?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				   $scope.dashboard=d;
				   
				   for(var i=0;i<$scope.dashboard.dataByOP.length;i++){
						 for(var j=0; j<$scope.dashboard.dataByOP[i].data.length;j++){
							 $.each($scope.dashboard.dataByOP[i].data[j],function(index,value){
								 $scope.dashboard.dataByOP[i].data[j][index]=format(value);
							 });	 
						 }
						 
					 }
				  
				   $('#reportview1').html($compile(getPartial('partialDashboard.html'))($scope));
				   $rootScope.$apply();
				  
				   stikyFirstColumn('reportview1',"table");		
				   
				   endTab(7);
				  
				
			  } 
			});
		 
	 }
	 
	 
	 $scope.tab8= function(){
		 initTab(8);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/databysubtypes?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				   $scope.subtype=d;
				   
				   for(var i=0;i<$scope.subtype.dataBySubtype.length;i++){
						 for(var j=0; j<$scope.subtype.dataBySubtype[i].data.length;j++){
							 $.each($scope.subtype.dataBySubtype[i].data[j],function(index,value){
								 $scope.subtype.dataBySubtype[i].data[j][index]=format(value);
							 });	 
						 }
						 
					 }
				  
				   $('#reportview1').html($compile(getPartial('partialSubtypes.html'))($scope));
				   $rootScope.$apply();
				  
				   endTab(8);
			  } 
			});
		 
	 }
	 
	 $scope.tab9= function(){
		 initTab(9);
		 $.ajax({
			  type: "GET",
			  url: 'webapi/report/incidentsbytype?year='+$scope.filter.year+'&month='+$scope.filter.month,
			  async:true,
			  contentType: "application/json",
			  dataType:'json',
			  success: function(d ){
				 
				   $scope.incidents=d;
				   
				   for(var j=0; j<$scope.incidents.length;j++){
					 $.each($scope.incidents[j],function(index,value){
						 $scope.incidents[j][index]=format(value);
					 });	 
				   }	
				 
						 
				  
				   $('#reportview1').html($compile(getPartial('partialIncidents.html'))($scope));
				   $rootScope.$apply();
				   var dataChart=new Array();
				   dataChart.push(["incidents","Percentage"]);
				   for(var j=0; j<$scope.incidents.length;j++){
					   if($scope.incidents[j].type!='Total'){
						   dataChart.push([$scope.incidents[j].type,parseFloat($scope.incidents[j].percentage)]);   
					   }
				   }
				   var options =   $.extend($.extend({},defOptions), {
					  	  orientation:'horizontal', 
					      title: "Incidents by type"
				   });
				   var data = google.visualization.arrayToDataTable( dataChart);
				   var divId='chartIncidents';
				   var chart = new google.visualization.PieChart(document.getElementById(divId));
				   chart.draw(data, options);
				   
				   endTab(9);
			  } 
			});
		 
	 } 
		

	
     

	 $scope.$on('$routeChangeSuccess', function () {
	            var path = $location.path();
			 
            if(path.indexOf('cpmmIpmmReport')>0){
            	setTimeout(function(){
					$("#content2").animate({top:-($('#leftColumn').outerHeight()),opacity:1},1000,
							function(){
							ngProgressLite.done();
							 
						});
					},100);
	         }

            
        });	 

	 $scope.rowClass=function(i){
		 if(i%2==0) return "lineTwo";
		 return "lineOne";
	 }   			 
	 ngProgressLite.done();

	 
	 function initTab(i){
		 var rand=Math.random();
		 if(rand<0.6){
			 ngProgressLite.set(rand);	 
		 }else{
			 ngProgressLite.set(0.3);
		 }
		 
		 ngProgressLite.start();
		 $("#unc_reportview1").unbind().hide('slow',function(){$(this).empty();});
		 $("#unc_reportview2").unbind().hide('slow',function(){$(this).empty();});
		 $("#unc_reportview3").unbind().hide('slow',function(){$(this).empty();});
	     
	 }
	 
	 function endTab(i){
		 
	        $("#reportview1").show();
	        $("#reportview2").show();
	        $("#reportview3").show();
	        $("#reportview1").css("opacity","0.1");
	        $("#reportview1").show();
	        $("#reportview1").animate({opacity:1},1000,function(){ngProgressLite.done();});
	        $("#reportview2").css("opacity","0.1");
	        $("#reportview2").show();
	        $("#reportview2").animate({opacity:1},1000,function(){ngProgressLite.done();});
	        $("#reportview3").css("opacity","0.1");
	        $("#reportview3").show();
	        $("#reportview3").animate({opacity:1},1000,function(){ngProgressLite.done();});
	 }

});


 

//END CHECKLIST REPORT

//ROUTES

var module =angular.module('app', ['app.controllers','ngRoute']);

module.config(['$routeProvider',
                  function($routeProvider) {
                    $routeProvider.
                      when('/cpmmIpmmReport', {
                    templateUrl: 'partials/partialCPMM_IPMMReport.html',
                    controller: 'ReportController'
                      });
                      
                     
                }]);