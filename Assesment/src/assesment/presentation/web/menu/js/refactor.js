


/*******************************************************************************************
This functions provides the toolkit for manipulating menu, home page and iframe sizes

********************************************************************************************/

var refactor=new createRefactor();
var loadingState="init";

function createRefactor(){}

//Refactor iframe sizes
createRefactor.prototype.refactorMenu=function(tamMenu){
/*
	  if(document.getElementById('menu').contentWindow.document.body.scrollHeight && document.getElementById('menu').contentWindow.innerHeight){
 	 	var tam1=document.getElementById('menu').contentWindow.innerHeight;
 	 	var tam2=document.getElementById('menu').contentWindow.document.body.scrollHeight;
 	 	if(tam1>tam2){
 	 		document.getElementById('menu').style.height=tam1;
 	 	}
 	 	else{
 	 		document.getElementById('menu').style.height=tam2; 	 	
 	 	}
 	 }
 	 else
	 if(document.getElementById('menu').contentWindow.document.body.scrollHeight){
	 	 document.getElementById('menu').style.height=document.getElementById('menu').contentWindow.document.body.scrollHeight; 	
	 }
	 else
	 if(document.getElementById('menu').contentWindow.innerHeight){
	 	 document.getElementById('menu').style.height=document.getElementById('menu').contentWindow.innerHeight;
	 }
	 else{
	 	document.getElementById('menu').style.height=tamMenu;
	 }
	*/ 
	document.getElementById('menu').style.height=550;
}

createRefactor.prototype.refactorResto=function(tamResto){
	 document.body.scroll="no";
	 if(document.getElementById('resto').contentWindow.document.body.scrollHeight){
		 document.getElementById('resto').style.height=document.getElementById('resto').contentWindow.document.body.scrollHeight+3000; 	
	 }
	 else
	 if(document.getElementById('resto').contentWindow.innerHeight){
	 	 document.getElementById('resto').style.height=document.getElementById('resto').contentWindow.innerHeight+3000;
	 }
	 else{
	 	document.getElementById('resto').style.height=tamResto;
	 }
	 document.body.scroll="yes";
}


createRefactor.prototype.refactorParent=function(){
	 parent.document.body.scroll="no";
	 if(parent.document.getElementById('resto').contentWindow.parent.document.body.scrollHeight){
		 parent.document.getElementById('resto').style.height=parent.document.getElementById('resto').contentWindow.parent.document.body.scrollHeight+30; 	
	 }
	 else
	 if(parent.document.getElementById('resto').contentWindow.innerHeight){
	 	 parent.document.getElementById('resto').style.height=parent.document.getElementById('resto').contentWindow.innerHeight+30;
	 }
	 else{
	 	parent.document.getElementById('resto').style.height=tamResto;
	 }
	 parent.document.body.scroll="yes";
}  


createRefactor.prototype.hiddeMenu=function(){
	var CEPAMenu=document.getElementById('menu');
	CEPAMenu.style.display='none';	
	var td=document.getElementById('td_menu');
	td.style.width='0%';

	//td=document.getElementById('td_resto');
	//td.style.width='100%';

	var hidde=document.getElementById('hidde');
	hidde.style.display='none';
	var show=document.getElementById('show');
	show.style.display='block';
}

createRefactor.prototype.showMenu=function(){
	var CEPAMenu=document.getElementById('menu');
	CEPAMenu.style.display='block';	
	var td=document.getElementById('td_menu');
	td.style.width='16%';
	
	//td=document.getElementById('td_resto');
	//td.style.width='84%';
	
	var hidde=document.getElementById('hidde');
	hidde.style.display='block';
	var show=document.getElementById('show');
	show.style.display='none';
}


createRefactor.prototype.getScreenSize=function(){
	var screensize=new ScreenSize();
	if (document.all)
		{	
			screensize.wHeight = document.body.clientHeight;
			screensize.wWidth = document.body.clientWidth;
			screensize.sHeight = screen.height;
			screensize.sWidth = screen.width;
			screensize.bitDepth = screen.colorDepth; 
		}
	else if (document.layers)
		{	
			screensize.wHeight = window.innerHeight;
			screensize.wWidth = window.innerWidth;
			screensize.sHeight = screen.height;
			screensize.sWidth = screen.width;
			screensize.bitDepth = screen.colorDepth; 
		}
	return screensize;	
}

function ScreenSize(){
	var wHeight, wWidth, sHeight, sWidth, bitDepth;
}

//Message loading
createRefactor.prototype.loading=function(){
	var loading=document.getElementById('loading');
	//loading.style.display='none';	
	var displayloading=document.getElementById('displayloading');
	//displayloading.style.display='block';	
}  


