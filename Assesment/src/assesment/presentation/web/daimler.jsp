<%@ page language="java"
	import="assesment.business.*"	
	import="assesment.communication.language.*"
%>

<!DOCTYPE html>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>

<%
	
	RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
	dispatcher.include(request,response);
%>
<html class="no-js js_active  vc_desktop  vc_transform  vc_transform  vc_transform " lang="es">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<!-- keywords -->
		<meta charset="UTF-8">
		<!-- viewport -->
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
		<!-- profile -->
		<link rel="profile" href="https://gmpg.org/xfn/11">
		<title>movil</title>
		<script type="text/javascript">
	    function popUp(URL) {
	        window.open(URL, 'Nombre de la ventana', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=300,height=200,left = 390,top = 50');
	    }
	    </script>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js" type="text/javascript" charset="utf-8">
		$(function(){
     		$('a[href*=#]').click(function() {
	     		if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'')
	         		&& location.hostname == this.hostname) {
		             var $target = $(this.hash);
	             	$target = $target.length && $target || $('[name=' + this.hash.slice(1) +']');
	             	if ($target.length) {
	
	                	var targetOffset = $target.offset().top;
	
	                 	$('html,body').animate({scrollTop: targetOffset}, 1000);
	
	                 	return false;
	
	            	}
	       		}
   			});
		});
		</script>
		<script type="text/javascript">
		function usuarioRegistrado() {
			document.forms['salir'].submit();
		}
		function register() {
			var form = document.forms['DaimlerForm'];
			if(form.firstName.value == '') {
				alert('Ingrese nombre');
			} else if(form.lastName == '') {
				alert('Ingrese apellido');
			} else if(form.email == '') {
				alert('Ingrese email');
			} else {
				form.submit();
 			} 				
		}
		</script>
		<link rel="dns-prefetch" href="https://fonts.googleapis.com/">
		<link rel="dns-prefetch" href="https://s.w.org/">
		<link rel="alternate" type="application/rss+xml" title="Salbicha » Feed" href="https://salbicha.com/feed/">
		<link rel="alternate" type="application/rss+xml" title="Salbicha » Feed de los comentarios" href="https://salbicha.com/comments/feed/">
		<script type="text/javascript">
			window._wpemojiSettings = {"baseUrl":"https:\/\/s.w.org\/images\/core\/emoji\/12.0.0-1\/72x72\/","ext":".png","svgUrl":"https:\/\/s.w.org\/images\/core\/emoji\/12.0.0-1\/svg\/","svgExt":".svg","source":{"concatemoji":"https:\/\/salbicha.com\/wp-includes\/js\/wp-emoji-release.min.js?ver=5.4.2"}};
			/*! This file is auto-generated */
			!function(e,a,t){var r,n,o,i,p=a.createElement("canvas"),s=p.getContext&&p.getContext("2d");function c(e,t){var a=String.fromCharCode;s.clearRect(0,0,p.width,p.height),s.fillText(a.apply(this,e),0,0);var r=p.toDataURL();return s.clearRect(0,0,p.width,p.height),s.fillText(a.apply(this,t),0,0),r===p.toDataURL()}function l(e){if(!s||!s.fillText)return!1;switch(s.textBaseline="top",s.font="600 32px Arial",e){case"flag":return!c([127987,65039,8205,9895,65039],[127987,65039,8203,9895,65039])&&(!c([55356,56826,55356,56819],[55356,56826,8203,55356,56819])&&!c([55356,57332,56128,56423,56128,56418,56128,56421,56128,56430,56128,56423,56128,56447],[55356,57332,8203,56128,56423,8203,56128,56418,8203,56128,56421,8203,56128,56430,8203,56128,56423,8203,56128,56447]));case"emoji":return!c([55357,56424,55356,57342,8205,55358,56605,8205,55357,56424,55356,57340],[55357,56424,55356,57342,8203,55358,56605,8203,55357,56424,55356,57340])}return!1}function d(e){var t=a.createElement("script");t.src=e,t.defer=t.type="text/javascript",a.getElementsByTagName("head")[0].appendChild(t)}for(i=Array("flag","emoji"),t.supports={everything:!0,everythingExceptFlag:!0},o=0;o<i.length;o++)t.supports[i[o]]=l(i[o]),t.supports.everything=t.supports.everything&&t.supports[i[o]],"flag"!==i[o]&&(t.supports.everythingExceptFlag=t.supports.everythingExceptFlag&&t.supports[i[o]]);t.supports.everythingExceptFlag=t.supports.everythingExceptFlag&&!t.supports.flag,t.DOMReady=!1,t.readyCallback=function(){t.DOMReady=!0},t.supports.everything||(n=function(){t.readyCallback()},a.addEventListener?(a.addEventListener("DOMContentLoaded",n,!1),e.addEventListener("load",n,!1)):(e.attachEvent("onload",n),a.attachEvent("onreadystatechange",function(){"complete"===a.readyState&&t.readyCallback()})),(r=t.source||{}).concatemoji?d(r.concatemoji):r.wpemoji&&r.twemoji&&(d(r.twemoji),d(r.wpemoji)))}(window,document,window._wpemojiSettings);
		</script><script src="files_m/wp-emoji-release.js" type="text/javascript" defer="defer"></script>
		<style type="text/css">
			img.wp-smiley,
			img.emoji {
				display: inline !important;
				border: none !important;
				box-shadow: none !important;
				height: 1em !important;
				width: 1em !important;
				margin: 0 .07em !important;
				vertical-align: -0.1em !important;
				background: none !important;
				padding: 0 !important;
			}
		</style>
		<link rel="stylesheet" id="wp-block-library-css" href="files_m/style_002.css" type="text/css" media="all">
		<link rel="stylesheet" id="wp-block-library-theme-css" href="files_m/theme.css" type="text/css" media="all">
		<link rel="stylesheet" id="contact-form-7-css" href="files_m/styles.css" type="text/css" media="all">
		<link rel="stylesheet" id="hongo-google-font-css" href="files_m/css_002.css" type="text/css" media="all">
		<link rel="stylesheet" id="animate-css" href="files_m/animate.css" type="text/css" media="all">
		<link rel="stylesheet" id="bootstrap-css" href="files_m/bootstrap.css" type="text/css" media="all">
		<link rel="stylesheet" id="et-line-icons-css" href="files_m/et-line-icons.css" type="text/css" media="all">
		<link rel="stylesheet" id="font-awesome-css" href="files_m/font-awesome.css" type="text/css" media="all">
		<link rel="stylesheet" id="themify-icons-css" href="files_m/themify-icons.css" type="text/css" media="all">
		<link rel="stylesheet" id="simple-line-icons-css" href="files_m/simple-line-icons.css" type="text/css" media="all">
		<link rel="stylesheet" id="swiper-css" href="files_m/swiper.css" type="text/css" media="all">
		<link rel="stylesheet" id="magnific-popup-css" href="files_m/magnific-popup.css" type="text/css" media="all">
		<link rel="stylesheet" id="hongo-mCustomScrollbar-css" href="files_m/jquery.css" type="text/css" media="all">
		<link rel="stylesheet" id="select2-css" href="files_m/select2.css" type="text/css" media="all">
		<link rel="stylesheet" id="js_composer_front-css" href="files_m/js_composer.css" type="text/css" media="all">
		<link rel="stylesheet" id="justifiedGallery-css" href="files_m/justifiedGallery.css" type="text/css" media="all">
		<link rel="stylesheet" id="hongo-hotspot-css" href="files_m/hongo-frontend-hotspot.css" type="text/css" media="all">
		<link rel="stylesheet" id="hongo-addons-section-builder-css" href="files_m/section-builder.css" type="text/css" media="all">
		<link rel="stylesheet" id="hongo-style-css" href="files_m/style.css" type="text/css" media="all">
		<link rel="stylesheet" id="hongo-responsive-css" href="files_m/responsive.css" type="text/css" media="all">
<style id="hongo-responsive-inline-css" type="text/css">
	.tooltip-wrap {
  position: relative;
			
	
}
.tooltip-wrap .tooltip-content {
  display: none;
  position: absolute;
	height: 198px;
	width: 300px;
  bottom: 5%;
  left: 5%;
  right: 5%;
  background-image: url("files_m/images/popup.png");
  padding: .5em;

}
.tooltip-wrap:hover .tooltip-content {
  display: block;
	z-index: 10;
@media screen and (min-width:992px) and (max-width:1199px){header .hongo-ipad-icon .nav>li{text-align:right;padding-right:18px;}header .hongo-ipad-icon .nav>li > a{padding:25px 0 25px 18px;display:inline-block}header .hongo-ipad-icon .nav>li:last-child>a{padding-right:0;}header .hongo-ipad-icon .nav>li:first-child>a{padding-left:0;}header .hongo-ipad-icon .nav>li i.dropdown-toggle{display:inline;position:absolute;top:50%;margin-top:-13px;float:right;padding:7px 10px;z-index:1;}header .hongo-ipad-icon .nav>li.simple-dropdown i.dropdown-toggle{margin-left:0;right:-12px;}}@media (min-width:992px){header .widget_nav_menu > div:nth-child(2n){display:block !important;}header .widget_nav_menu > div:nth-child(2n){display:block !important;}.hongo-shop-dropdown-menu .navbar-nav > li > a,.hongo-shop-dropdown-menu .navbar-nav > li:first-child > a,.hongo-shop-dropdown-menu .navbar-nav > li:last-child > a{padding:17px 20px;}header .with-categories-navigation-menu .woocommerce.widget_shopping_cart .hongo-cart-top-counter{line-height:59px;}header .with-categories-navigation-menu .widget_hongo_search_widget,header .with-categories-navigation-menu .widget_hongo_account_menu_widget,header .with-categories-navigation-menu .widget_shopping_cart,header .with-categories-navigation-menu .widget_hongo_wishlist_link_widget{line-height:59px;min-height:59px;}header .with-categories-navigation-menu .woocommerce.widget_shopping_cart .hongo-top-cart-wrapper .hongo-mini-cart-counter{top:13px;}.simple-menu-open .simple-menu{overflow:visible !important;}}@media (max-width:991px){header .with-categories-navigation-menu .woocommerce.widget_shopping_cart .hongo-cart-top-counter{line-height:70px;}header .with-categories-navigation-menu .widget_hongo_search_widget,header .with-categories-navigation-menu .widget_hongo_account_menu_widget,header .with-categories-navigation-menu .widget_shopping_cart,header .with-categories-navigation-menu .widget_hongo_wishlist_link_widget{line-height:69px;min-height:69px;}header .with-categories-navigation-menu .woocommerce.widget_shopping_cart .hongo-top-cart-wrapper .hongo-mini-cart-counter{top:20px;}.navbar-toggle{background-color:transparent !important;border:none;border-radius:0;padding:0;font-size:18px;position:relative;top:-8px;right:0;display:inline-block !important;margin:0;float:none !important;vertical-align:middle;}.navbar-toggle .icon-bar{background-color:#232323;display:table}.sr-only{border:0;clip:rect(0,0,0,0);height:auto;line-height:16px;padding:0 0 0 5px;overflow:visible;margin:0;width:auto;float:right;clear:none;display:table;position:relative;font-size:12px;color:#232323;text-transform:uppercase;left:0;top:-2px;font-weight:500;letter-spacing:.5px;cursor:pointer;}.navbar-toggle.toggle-mobile .icon-bar+.icon-bar{margin-top:0;}.navbar-toggle.toggle-mobile span{position:absolute;margin:0;display:block;height:2px;width:16px;background-color:#232323;border-radius:0;opacity:1;margin:0 0 3px 0;-webkit-transform:rotate(0deg);-moz-transform:rotate(0deg);-o-transform:rotate(0deg);transform:rotate(0deg);-webkit-transition:.25s ease-in-out;-moz-transition:.25s ease-in-out;-o-transition:.25s ease-in-out;transition:.25s ease-in-out;}.navbar-toggle.toggle-mobile{width:16px;height:14px;top:-1px}.navbar-toggle.toggle-mobile span:last-child{margin-bottom:0;}.navbar-toggle.toggle-mobile span:nth-child(1){top:0px;}.navbar-toggle.toggle-mobile span:nth-child(2),.navbar-toggle.toggle-mobile span:nth-child(3){top:5px;}.navbar-toggle.toggle-mobile span:nth-child(4){top:10px;}.navbar-toggle.toggle-mobile span:nth-child(2){opacity:0;}.navbar-toggle.toggle-mobile.active span:nth-child(2){opacity:1;}.navbar-toggle.toggle-mobile.active span:nth-child(1){top:8px;width:0;left:0;right:0;opacity:0;}.navbar-toggle.toggle-mobile.active span:nth-child(2){-webkit-transform:rotate(45deg);-moz-transform:rotate(45deg);-o-transform:rotate(45deg);-ms-transform:rotate(45deg);transform:rotate(45deg);}.navbar-toggle.toggle-mobile.active span:nth-child(3){-webkit-transform:rotate(-45deg);-moz-transform:rotate(-45deg);-o-transform:rotate(-45deg);-ms-transform:rotate(-45deg);transform:rotate(-45deg);}.navbar-toggle.toggle-mobile.active span:nth-child(4){top:8px;width:0;left:0;right:0;opacity:0;}.navbar-collapse.collapse{display:none !important;height:auto!important;width:100%;margin:0;position:absolute;top:100%;}.navbar-collapse.collapse.in{display:block !important;overflow-y:hidden !important;}.navbar-collapse{max-height:400px;overflow-y:hidden !important;left:0;padding:0;position:absolute;top:100%;width:100%;border-top:0;}header .nav{float:none !important;padding-left:0;padding-right:0;margin:0px 0;width:100%;text-align:left;background-color:rgba(23,23,23,1) !important;}header .nav > li{position:relative;display:block;margin:0;border-bottom:1px solid rgba(255,255,255,0.06);}header .nav > li ul.menu{margin:5px 0;float:left;width:100%;}header .nav > li ul.menu > li:last-child > a{border-bottom:0;}header .nav > li > a > i{top:4px;min-width:12px;}header .nav > li > a,header .nav > li:first-child > a,header .nav > li:last-child > a,header .with-categories-navigation-menu .nav > li > a{display:block;width:100%;border-bottom:0 solid #e0e0e0;padding:14px 15px 15px;}header .nav > li > a,header .nav > li:hover > a,header .nav > li > a:hover{color:#fff;}header .nav > li > a.active,header .nav > li.active > a,header .nav > li.current-menu-ancestor > a,header .nav > li.current-menu-item > a,header .nav > li.current-menu-item > a{color:rgba(255,255,255,0.6);}header .nav > li:first-child > a{border-top:none;}header .nav > li i.dropdown-toggle{position:absolute;right:0;top:0;color:#fff;font-size:16px;cursor:pointer;display:block;padding:16px 14px 16px;}header .hongo-ipad-icon .nav > li i.dropdown-toggle{color:#fff;}header .nav > li.open i.dropdown-toggle:before{content:"\f106";}header .nav > li > a .menu-hover-line:after{display:none;}header .nav > li ul.menu li,header .nav>li ul.menu li.menu-title{margin:0;padding:0 15px;}header .nav > li ul.menu li a{line-height:22px;padding:7px 0 8px;margin-bottom:0;border-bottom:1px solid rgba(255,255,255,0.06);}header .nav .mega-menu-main-wrapper,.simple-dropdown .simple-menu,ul.sub-menu{position:static !important;height:0 !important;width:100% !important;left:inherit !important;right:inherit !important;padding:0 !important;}ul.sub-menu{opacity:1 !important;visibility:visible !important;}.mega-menu-main-wrapper section{padding:0 !important;left:0 !important;margin:0 !important;width:100% !important;}header .container{width:100%;}.mega-menu-main-wrapper{opacity:1 !important;visibility:visible !important;}header .header-main-wrapper > div > section.hongo-stretch-content-fluid{padding:0;}header .mini-header-main-wrapper > div > section.hongo-stretch-content-fluid{padding:0;}header .top-header-main-wrapper > div > section.hongo-stretch-content-fluid{padding:0;}.hongo-shop-dropdown-menu .nav > li i.dropdown-toggle{display:none}.hongo-shop-dropdown-menu .nav{background-color:transparent !important}.hongo-navigation-main-wrapper .hongo-tab.panel{max-height:400px;overflow-y:auto !important;width:100%;}.mega-menu-main-wrapper .container{padding:0;}.simple-dropdown .simple-menu ul.sub-menu{padding:0 15px !important;}.simple-dropdown ul.sub-menu > li > ul.sub-menu{top:0;left:0}.simple-dropdown ul.sub-menu>li>a{color:#fff;font-size:13px;}.simple-dropdown ul.sub-menu>li ul.sub-menu{margin-bottom:10px;}.simple-dropdown ul.sub-menu>li ul.sub-menu>li:last-child a{border-bottom:0}.simple-dropdown ul.sub-menu>li>ul.sub-menu{padding-left:0 !important;padding-right:0 !important;}.simple-dropdown ul.sub-menu>li>ul.sub-menu>li>a{color:#8d8d8d;font-size:12px;padding:10px 0 11px 0;}.simple-dropdown ul.sub-menu>li>ul.sub-menu>li.active > a,.simple-dropdown ul.sub-menu>li>ul.sub-menu>li.current-menu-item > a,.simple-dropdown ul.sub-menu>li>ul.sub-menu>li.current-menu-ancestor > a{color:#fff;}.simple-dropdown ul.sub-menu li a{padding:12px 0;border-bottom:1px solid rgba(255,255,255,0.06);}.simple-dropdown ul.sub-menu li > a i.ti-angle-right{display:none;}.simple-dropdown.open .simple-menu,header .nav > li.open > .mega-menu-main-wrapper,ul.sub-menu{height:auto !important;opacity:1;visibility:visible;overflow:visible;}.simple-dropdown ul.sub-menu li:last-child > ul > li:last-child > a{border-bottom:0;}header .header-main-wrapper .woocommerce.widget_shopping_cart .hongo-cart-top-counter{top:2px;}.header-default-wrapper .simple-dropdown ul.sub-menu li.menu-item > ul{display:block;}header .nav>li ul.menu li .left-icon,header .nav>li .simple-menu ul li .left-icon{top:-1px;position:relative;}.hongo-left-menu-wrapper .hongo-left-menu li.menu-item>a{padding:12px 0 13px;}.hongo-left-menu-wrapper .hongo-left-menu li.menu-item>span{line-height:43px;}.hongo-left-menu-wrapper .hongo-left-menu li.menu-item ul li a{padding:6px 0 6px 10px;}.menu-content-inner-wrap ul .menu-item .dropdown-menu .menu-item .dropdown-menu .menu-item a{padding:4px 15px;}.hongo-main-wrap{padding-left:0;}.hongo-main-wrap header{left:-290px;transition:all 0.2s ease-in-out;-moz-transition:all 0.2s ease-in-out;-webkit-transition:all 0.2s ease-in-out;-ms-transition:all 0.2s ease-in-out;-o-transition:all 0.2s ease-in-out;}.hongo-main-wrap header.left-mobile-menu-open{left:0;transition:all 0.2s ease-in-out;-moz-transition:all 0.2s ease-in-out;-webkit-transition:all 0.2s ease-in-out;-ms-transition:all 0.2s ease-in-out;-o-transition:all 0.2s ease-in-out;}.hongo-main-wrap header .header-logo-wrapper{position:fixed;left:0;top:0;width:100%;z-index:1;text-align:left;background-color:#fff;padding:20px 15px;}.hongo-left-menu-wrap .navbar-toggle{position:fixed;right:16px;top:25px;z-index:9;margin:0;}.hongo-left-menu-wrap .navbar-toggle.sr-only{right:40px;top:27px;z-index:9;cursor:pointer;margin:0;width:-webkit-fit-content;width:-moz-fit-content;width:fit-content;height:auto;clip:inherit;padding:0;text-align:right;left:inherit;}.hongo-left-menu-wrap .toggle-mobile ~ .navbar-toggle.sr-only {top:24px;}.header-left-wrapper .hongo-left-menu-wrapper{margin-top:10px;margin-bottom:50px;}.hongo-left-menu-wrapper .hongo-left-menu li.menu-item{z-index:0}.nav.hongo-left-menu{background-color:transparent !important;}.header-left-wrapper > .container{width:290px;padding:65px 20px 50px 20px !important;}.header-left-wrapper .widget_hongo_search_widget a,.header-left-wrapper .hongo-cart-top-counter i,header .header-left-wrapper .widget a,header .header-left-wrapper .widget_hongo_wishlist_link_widget a{font-size:15px;}header .header-left-wrapper .widget{margin-left:8px;margin-right:8px;}.hongo-left-menu-wrapper .hongo-left-menu li.menu-item a,.hongo-left-menu-wrapper .hongo-left-menu li.menu-item i{color:#232323;}.hongo-left-menu-wrapper .hongo-left-menu li.menu-item a:hover{color:#000;}.hongo-left-menu-wrapper .hongo-left-menu li.menu-item.dropdown > a{width:auto;}.hongo-navigation-main-wrapper{width:auto;}.hongo-navigation-main-wrapper .hongo-woocommerce-tabs-wrapper ul.navigation-tab{display:block;}.hongo-navigation-main-wrapper .hongo-woocommerce-tabs-wrapper{width:100%;left:0;position:absolute;top:100%;max-height:460px;}.hongo-navigation-main-wrapper .hongo-woocommerce-tabs-wrapper ul.navigation-tab{list-style:none;margin:0;padding:0;text-align:center;}.hongo-navigation-main-wrapper .hongo-woocommerce-tabs-wrapper ul.navigation-tab li{display:inline-block;width:50%;}.hongo-navigation-main-wrapper .hongo-woocommerce-tabs-wrapper ul.navigation-tab li > a{background-color:#000;width:100%;padding:20px 10px;display:block;color:#fff;font-size:13px;text-transform:uppercase;}.hongo-navigation-main-wrapper .hongo-woocommerce-tabs-wrapper ul.navigation-tab li > a:hover,.hongo-navigation-main-wrapper .hongo-woocommerce-tabs-wrapper ul.navigation-tab li.active > a{background-color:rgba(23,23,23,1);}.hongo-navigation-main-wrapper .hongo-shop-dropdown-menu,.hongo-navigation-main-wrapper .hongo-shop-dropdown-menu.hongo-tab.panel{width:100%;margin:0;}.hongo-navigation-main-wrapper .hongo-tab.panel{padding:0;margin:0;display:none;}.hongo-navigation-main-wrapper .hongo-tab.panel.active{display:block;}.hongo-navigation-main-wrapper .hongo-tab .shop-dropdown-toggle{display:none;}.hongo-navigation-main-wrapper .hongo-tab .hongo-shop-dropdown-button-menu{top:0;height:auto !important;overflow:visible;position:inherit;background-color:rgba(23,23,23,1);border:0 solid #e5e5e5;margin:0;padding:0;min-height:1px;transform:rotateX(0deg);-webkit-transform:rotateX(0deg);-moz-transform:rotateX(0deg);-ms-transform:rotateX(0deg);-o-transform:rotateX(0deg);}.header-common-wrapper .hongo-shop-dropdown-menu .navbar-nav > li{border-bottom:1px solid rgba(255,255,255,0.06);}.header-common-wrapper .hongo-shop-dropdown-menu .hongo-shop-dropdown-button-menu > ul > li > a,.hongo-shop-dropdown-menu .hongo-shop-dropdown-button-menu > ul > li > a:hover,.hongo-shop-dropdown-menu .hongo-shop-dropdown-button-menu > ul > li.on > a{color:#fff;}.hongo-shop-dropdown-menu li.menu-item .shop-mega-menu-wrapper,.hongo-shop-dropdown-menu .navbar-nav > li > a:after,.hongo-shop-dropdown-menu .simple-dropdown .sub-menu{display:none !important;}.header-common-wrapper .hongo-shop-dropdown-menu .hongo-shop-dropdown-button-menu > ul > li > a:hover,.header-common-wrapper .hongo-shop-dropdown-menu .hongo-shop-dropdown-button-menu > ul > li.on > a{background-color:rgba(23,23,23,1) !important;color:#fff;}.header-sticky .hongo-navigation-main-wrapper .hongo-shop-dropdown-menu.hongo-tab.panel{position:relative;width:100%;margin:0;}.header-sticky .hongo-shop-dropdown-menu .hongo-shop-dropdown-button-menu{width:100%;left:0;}header .nav > li > a > img.menu-link-icon,.hongo-shop-dropdown-menu .menu-item.menu-title img.menu-link-icon,.hongo-shop-dropdown-menu .menu-item img.menu-link-icon{-webkit-filter:brightness(200%);filter:brightness(200%);}.header-main-wrapper .nav > li ul.menu li.menu-title,header .nav>li .wpb_wrapper ul.menu:first-child li.menu-title{padding:7px 0 8px;margin:0 15px;width:calc(100% - 30px);border-bottom:1px solid rgba(255,255,255,0.06);}.header-main-wrapper .nav > li ul.menu li.menu-title a,header .nav > li .wpb_wrapper ul.menu:first-child li.menu-title a{line-height:22px;padding:0;margin:0;border-bottom:0 solid rgba(255,255,255,0.06);}.edit-hongo-section{display:none}.mega-menu-main-wrapper .widget .widget-title{line-height:22px;padding:5px 0 6px;margin:0 15px 0;}.widget_product_categories_thumbnail ul,.mega-menu-main-wrapper .woocommerce.widget_products ul.product_list_widget{margin-top:0;}.widget_product_categories_thumbnail ul li{margin:0 15px 0;width:auto;float:none;}.widget_product_categories_thumbnail ul li a{line-height:22px;padding:5px 0 6px;margin-bottom:0;font-size:12px;text-align:left;color:#8d8d8d;font-weight:400;display:block;position:relative;left:0;}.mega-menu-main-wrapper .widget{margin:10px 0;float:left;width:100%;}.mega-menu-main-wrapper .woocommerce.widget_products ul.product_list_widget li,.mega-menu-main-wrapper .woocommerce.widget_products ul.product_list_widget li:last-child{margin:0 15px 10px !important;width:auto;float:none;}.mini-header-main-wrapper .widget_hongo_account_menu_widget .hongo-top-account-menu a > i,header .mini-header-main-wrapper a.wishlist-link > i,.mini-header-main-wrapper .widget div > a > i{top:-2px;}.header-default-wrapper.navbar-default .accordion-menu{position:inherit;}.header-default-wrapper.navbar-default .navbar-nav>li{width:100%;}.header-default-wrapper.navbar-default .navbar-nav>li>a{color:#fff;}.header-default-wrapper.navbar-default .navbar-nav>li>ul>li>a,.header-default-wrapper.navbar-default .simple-dropdown ul.sub-menu>li>ul.sub-menu>li>a,.header-default-wrapper.navbar-default .navbar-nav>li>a:hover,.header-default-wrapper.navbar-default .navbar-nav>li:hover>a,.header-default-wrapper.navbar-default .navbar-nav>li>a.active,.header-default-wrapper.navbar-default .navbar-nav>li.urrent-menu-ancestor>a,.header-default-wrapper.navbar-default .navbar-nav>li.current_page_ancestor>a{color:#fff;}.header-default-wrapper.navbar-default .navbar-nav>li>ul>li:last-child a{border:0;}.header-default-wrapper.navbar-default .simple-dropdown ul.sub-menu{display:block;padding:0 15px !important;}.header-default-wrapper.navbar-default .navbar-collapse.collapse.in{overflow-y:auto !important;}.header-default-wrapper.navbar-default .simple-dropdown ul.sub-menu li.menu-item-has-children:before{display:none;}header .widget_nav_menu{position:relative;}header .widget_nav_menu .wp-nav-menu-responsive-button{display:block !important;position:relative;border-left:1px solid #cbc9c7;min-height:37px !important;line-height:37px !important;padding:2px 14px 0;font-size:11px;text-transform:uppercase;font-weight:500;color:#232323;}header .widget_nav_menu .wp-nav-menu-responsive-button:hover{color:#000;}header .widget_nav_menu .active .wp-nav-menu-responsive-button{background-color:#fff;color:#f57250;}header .widget_nav_menu .wp-nav-menu-responsive-button:after{content:"\e604";font-family:'simple-line-icons';margin-left:5px;border:0;font-weight:900;font-size:9px;}header .widget_nav_menu.active .wp-nav-menu-responsive-button:after{content:"\e607";}header .widget_nav_menu>div:nth-child(2n){display:none;width:160px;background-color:rgba(28,28,28,1);padding:0;margin:0;border-top:0;position:absolute;right:0;left:inherit;top:100%;}header .widget_nav_menu>div:nth-child(2n) .menu{padding:0;text-align:left;}header .widget_nav_menu>div:nth-child(2n) .menu li{padding:0;border-bottom:1px solid rgba(0,0,0,0.1);width:100%;float:left;margin:0;}header .widget_nav_menu>div:nth-child(2n) .menu li:last-child>a{border-bottom:0;}header .widget_nav_menu>div:nth-child(2n) .menu li a{color:#8d8d8d;padding:8px 10px;line-height:normal;display:block;border-bottom:1px solid rgba(255,255,255,0.1);position:relative;font-size:11px;text-transform:uppercase;font-weight:400;outline:none;}header .widget_nav_menu>div:nth-child(2n) .menu li:after{display:none;}header .widget_nav_menu>div:nth-child(2n) .widget.active>a{background-color:#fff;color:#f57250;}}body,.main-font,.hongo-timer-style-3.counter-event .counter-box .number:before{font-family:'Raleway',sans-serif;}rs-slides .main-font{font-family:'Raleway',sans-serif !important;}.alt-font,.button,.btn,.woocommerce-store-notice__dismiss-link:before,.product-slider-style-1 .pagination-number,.woocommerce-cart .cross-sells > h2,.woocommerce table.shop_table_responsive tr td::before,.woocommerce-page table.shop_table_responsive tr td::before{font-family:'Roboto Mono',sans-serif;}rs-slides .alt-font{font-family:'Roboto Mono',sans-serif !important;}.blog-post.blog-post-style-related:hover .blog-post-images img{opacity:0.5;}.blog-post.blog-post-style-default:hover .blog-post-images img,.blog-clean .blog-grid .blog-post-style-default:hover .blog-img img{opacity:0.5;}h1{font-size:150px;}h1{line-height:65px;}h1{letter-spacing:10px;}h6{font-size:18px;}h6{line-height:24px;}@media (max-width:1199px){.wow{-webkit-animation-name:none !important;animation-name:none !important;}}.bg-opacity-color{opacity:0.8;}
.header-common-wrapper > .container > section:first-of-type{background-color:rgba(0,0,0,0)!important;}.header-common-wrapper .nav > li > a,header .hongo-ipad-icon .nav>li i.dropdown-toggle,header .header-common-wrapper a.header-search-form,header .header-common-wrapper a.account-menu-link,header .header-common-wrapper a.wishlist-link,.header-common-wrapper .widget_shopping_cart,.header-common-wrapper .navbar-toggle .sr-only,.header-common-wrapper .hongo-section-heading,.header-common-wrapper .hongo-section-heading a,.header-common-wrapper .hongo-social-links a,.header-common-wrapper .hongo-left-menu-wrapper .hongo-left-menu > li > span i.ti-angle-down,.header-common-wrapper .hongo-section-heading{color:#ffffff;}.header-common-wrapper .navbar-toggle .icon-bar,.header-common-wrapper .header-menu-button .navbar-toggle span{background-color:#ffffff;}.header-common-wrapper .nav > li > a:hover,.header-common-wrapper .nav > li:hover > a,header .header-common-wrapper .widget.widget_shopping_cart:hover,header .header-common-wrapper a.account-menu-link:hover,header .header-common-wrapper a.wishlist-link:hover,header .header-common-wrapper a.header-search-form:hover,.header-common-wrapper .nav > li > a.active,.header-common-wrapper .nav > li.active > a,.header-common-wrapper .nav > li.current-menu-ancestor > a,.header-common-wrapper .nav > li.current-menu-item > a,.header-common-wrapper .widget.widget_hongo_search_widget:hover  > div > a,.header-common-wrapper .widget.widget_hongo_wishlist_link_widget:hover > div > a,.header-common-wrapper .widget.widget_hongo_account_menu_widget:hover > div > a,.header-common-wrapper .widget.widget_shopping_cart:hover,.header-common-wrapper .hongo-section-heading a:hover,.header-common-wrapper .hongo-social-links a:hover,.header-common-wrapper .hongo-left-menu-wrapper .hongo-left-menu > li.menu-item.on > span .ti-angle-down:before,.header-common-wrapper .nav > li.on > a{color:rgba(255,255,255,0.6);}.header-common-wrapper .navbar-toggle:hover .icon-bar,.header-common-wrapper .header-menu-button .navbar-toggle:hover span{background-color:rgba(255,255,255,0.6);}header .header-common-wrapper > div > section{-webkit-box-shadow:0px 1px rgba(255,255,255,0.15);-moz-box-shadow:0px 1px rgba(255,255,255,0.15);box-shadow:0px 1px rgba(255,255,255,0.15);}.header-common-wrapper.sticky-appear > .container > section:first-of-type{background-color:#000000!important;}header .header-common-wrapper.sticky-appear > div > section{box-shadow:none;}footer .hongo-footer-top .widget .widget-title,footer .hongo-footer-middle .widget .widget-title,footer .hongo-footer-middle .hongo-link-menu li.menu-title,footer .hongo-footer-middle .hongo-link-menu li.menu-title a,footer .hongo-section-heading,footer .hongo-footer-middle .widget .widget-title,footer .hongo-footer-middle .hongo-link-menu.navigation-link-vertical li.menu-title,footer .hongo-footer-middle .hongo-link-menu.navigation-link-vertical li.menu-title a{font-size:12px;}footer .hongo-footer-top .widget .widget-title,footer .hongo-footer-middle .widget .widget-title,footer .hongo-footer-middle .hongo-link-menu li.menu-title,footer .hongo-section-heading,footer .hongo-footer-middle .widget .widget-title,footer .hongo-footer-middle .hongo-link-menu.navigation-link-vertical li.menu-title{text-transform:uppercase;}
</style>
<script type="text/javascript" src="files_m/jquery_006.js"></script>
<script type="text/javascript" src="files_m/jquery-migrate.js"></script>
<!--[if lt IE 9]>
<script type='text/javascript' src='https://salbicha.com/wp-content/themes/hongo/assets/js/html5shiv.js?ver=3.7.3'></script>
<![endif]-->
<link rel="https://api.w.org/" href="https://salbicha.com/wp-json/">
<link rel="EditURI" type="application/rsd+xml" title="RSD" href="https://salbicha.com/xmlrpc.php?rsd">
<link rel="wlwmanifest" type="application/wlwmanifest+xml" href="https://salbicha.com/wp-includes/wlwmanifest.xml"> 
<meta name="generator" content="WordPress 5.4.2">
<link rel="canonical" href="https://salbicha.com/js2/">
<link rel="shortlink" href="https://salbicha.com/?p=99658">
<link rel="alternate" type="application/json+oembed" href="https://salbicha.com/wp-json/oembed/1.0/embed?url=https%3A%2F%2Fsalbicha.com%2Fjs2%2F">
<link rel="alternate" type="text/xml+oembed" href="https://salbicha.com/wp-json/oembed/1.0/embed?url=https%3A%2F%2Fsalbicha.com%2Fjs2%2F&amp;format=xml">
<meta name="generator" content="Powered by WPBakery Page Builder - drag and drop page builder for WordPress.">
	<style type="text/css" data-type="vc_shortcodes-custom-css">
	.vc_custom_1602778358238{margin-top: 0px !important;margin-right: 0px !important;margin-bottom: 0px !important;margin-left: 0px !important;padding-top: 0px !important;padding-right: 0px !important;padding-bottom: 0px !important;padding-left: 0px !important;}.vc_custom_1602778358238{margin-top: 0px !important;margin-right: 0px !important;margin-bottom: 0px !important;margin-left: 0px !important;padding-top: 0px !important;padding-right: 0px !important;padding-bottom: 0px !important;padding-left: 0px !important;}.vc_custom_1602778358238{margin-top: 0px !important;margin-right: 0px !important;margin-bottom: 0px !important;margin-left: 0px !important;padding-top: 0px !important;padding-right: 0px !important;padding-bottom: 0px !important;padding-left: 0px !important;}.vc_custom_1602658827767{padding-top: 0px !important;padding-right: 0px !important;padding-bottom: 0px !important;background: #160d00 url(files_m/images/bg_lat1.jpg?id=99611) !important;background-position: 0 0 !important;background-repeat: repeat !important;}.vc_custom_1602775762223{padding-right: 60px !important;padding-left: 40px !important;}.vc_custom_1602775769653{padding-left: 40px !important;}.vc_custom_1602791406221{padding-right: 0px !important;padding-left: 0px !important;background-position: 0 0 !important;background-repeat: repeat !important;}.vc_custom_1602791398478{padding-right: 0px !important;padding-left: 0px !important;}.vc_custom_1602778853322{padding-right: 5px !important;}.vc_custom_1602778868660{padding-left: 5px !important;}.vc_custom_1602785083258{background-position: 0 0 !important;background-repeat: repeat !important;}.vc_custom_1602778471791{padding-left: 15px !important;}.vc_custom_1602785934286{padding-top: 10px !important;padding-bottom: 30px !important;}.vc_custom_1602785964530{padding-top: 10px !important;padding-bottom: 30px !important;}.vc_custom_1602657991829{padding-right: 5px !important;padding-left: 40px !important;}.vc_custom_1602657356453{padding-right: 5px !important;padding-left: 5px !important;}.vc_custom_1602657542027{padding-right: 5px !important;padding-left: 40px !important;}.vc_custom_1602660311861{padding-right: 5px !important;padding-left: 40px !important;}.vc_custom_1602777406067{padding-top: 5px !important;padding-right: 5px !important;padding-bottom: 5px !important;padding-left: 5px !important;}.vc_custom_1602660281891{padding-right: 5px !important;padding-left: 40px !important;}.vc_custom_1602777434053{padding-top: 5px !important;padding-right: 5px !important;padding-bottom: 5px !important;padding-left: 5px !important;}
	</style>
	<noscript>
		<style> .wpb_animate_when_almost_visible { opacity: 1; }
		</style>
	</noscript>	
	</head>
	<form name="salir" action="logout.jsp">
	</form>
	<body class="page-template-default page page-id-99658 wp-embed-responsive wpb-js-composer js-comp-ver-6.2.0 vc_responsive hongo-ready">
				<div class="hongo-layout">
															<div id="post-99658" class="hongo-main-content-wrap post-99658 page type-page status-publish hentry">		<div class="container-fluid hongo_layout_no_sidebar_single">
			<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12 hongo-content-full-part">
		    				<div class="hongo-rich-snippet display-none">
					<span class="entry-title">
						js2					</span>
					<span class="author vcard">
						<a class="url fn n" href="https://salbicha.com/author/salbicha/">
							salbicha						</a>
					</span>
					<span class="published">
						14 de octubre de 2020					</span>
					<time class="updated" datetime="2020-10-15T19:50:08+00:00">
						15 de octubre de 2020					</time>
				</div>

				<div class="entry-content">
					<section data-vc-full-width="true" data-vc-full-width-init="true" class="vc_row wpb_row vc_row-fluid  vc_custom_1602778358238   hongo-stretch-content hongo-stretch-row-container" style="position: relative; left: -15px; box-sizing: border-box; width: 842px; padding-left: 15px; padding-right: 15px;"><div class="wpb_column vc_column_container vc_col-has-fill position-relative col-xs-mobile-fullwidth vc_col-sm-12"><div class="vc_column-inner cover-background vc_custom_1602658827767"><div class="wpb_wrapper"><div class="wpb_single_image vc_align_left">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/logos_head.png" class="vc_single_image-img attachment-full" alt="" srcset="https://salbicha.com/wp-content/uploads/2020/10/logos_head.png 452w, https://salbicha.com/wp-content/uploads/2020/10/logos_head-300x127.png 300w, https://salbicha.com/wp-content/uploads/2020/10/logos_head-84x35.png 84w, https://salbicha.com/wp-content/uploads/2020/10/logos_head-200x85.png 200w, https://salbicha.com/wp-content/uploads/2020/10/logos_head-450x190.png 450w, https://salbicha.com/wp-content/uploads/2020/10/logos_head-360x152.png 360w" sizes="(max-width: 452px) 100vw, 452px" data-no-retina="" width="452" height="191"></div>
			</figure>
			
		</div><div class="vc_empty_space" style="height: 150px"><span class="vc_empty_space_inner"></span></div><p style="font-size: 25px;color: #ffffff;line-height: 33px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading vc_custom_1602775762223">Gracias
 por unirte a la nueva generación de conductores conscientes, el que 
estes aqui sin duda demuestra el interés por tu bienestar, el de los 
demás y sobre todo generar un cambio en la cultura vial.</p><p style="font-size: 35px;color: #ffffff;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading vc_custom_1602775769653">Bienvenido</p><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div></div></div></div></section><div class="vc_row-full-width vc_clearfix"></div><section data-vc-full-width="true" data-vc-full-width-init="true" class="vc_row wpb_row vc_row-fluid  vc_custom_1602778358238   hongo-stretch-content hongo-stretch-row-container" style="position: relative; left: -15px; box-sizing: border-box; width: 842px; padding-left: 15px; padding-right: 15px;"><div class="wpb_column vc_column_container vc_col-has-fill position-relative col-xs-mobile-fullwidth vc_col-sm-12"><div class="vc_column-inner cover-background vc_custom_1602791406221"><div class="wpb_wrapper"><div class="wpb_single_image   vc_custom_1602791398478 cover-background">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/banner_movil.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 2000px) 100vw, 2000px" data-no-retina="" width="2000" height="716"></div>
			</figure>
			
		</div><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container vc_col-sm-6 vc_col-xs-6"><div class="vc_column-inner vc_custom_1602778853322"><div class="wpb_wrapper"><p style="font-size: 28px;color: #000000;line-height: 35px;text-align: right;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading one-page"><a href="#elcurso">EL CURSO</a></p></div></div></div><div class="wpb_column vc_column_container vc_col-sm-6 vc_col-xs-6"><div class="vc_column-inner vc_custom_1602778868660"><div class="wpb_wrapper"><p style="font-size: 28px;color: #000000;line-height: 35px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading menu nav alt-font hongo-menu-wrap"><a href="#contacto">/ CONTACTO</a></p></div></div></div></div></div></div></div></section><div class="vc_row-full-width vc_clearfix"></div><section data-vc-full-width="true" data-vc-full-width-init="true" class="vc_row wpb_row vc_row-fluid  vc_custom_1602778358238   hongo-stretch-content hongo-stretch-row-container" style="position: relative; left: -15px; box-sizing: border-box; width: 842px; padding-left: 15px; padding-right: 15px;"><div class="wpb_column vc_column_container vc_col-has-fill position-relative col-xs-mobile-fullwidth vc_col-sm-12"><div class="vc_column-inner cover-background vc_custom_1602785083258"><div class="wpb_wrapper"><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-6"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div><p style="font-size: 20px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Este curso en línea ha sido diseñado por Daimler y CEPA (Centro de Prevención de Accidentes).<br>
Con este curso obtendrás nuevas habilidades de manejo seguro y responsable, incluyendo técnicas y recomendaciones de manejo.</p><p style="font-size: 20px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Cada módulo contiene un video instructivo y una sección de preguntas que determinarán el porcentaje de dominio sobre el tema.</p><p style="font-size: 20px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Los
 usuarios aprobados recibirán certificado. emitido por Mercedes Benz y 
CEPA, y además un regalo que te acompañará en el camino.</p><p style="font-size: 26px;color: #000000;line-height: 32px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Comienza ahora y se parte de la nueva cultura vial.</p><div class="wpb_single_image vc_align_left">
			<figure class="wpb_wrapper vc_figure">
				<a href="#temario" class="vc_single_image-wrapper  "><img src="files_m/images/conoceletemario-300x89.png" class="vc_single_image-img attachment-medium" alt=""  sizes="(max-width: 300px) 100vw, 300px" data-no-retina="" width="300" height="89"></a>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-6"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div><p style="font-size: 26px;color: #000000;line-height: 32px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading vc_custom_1602778471791">Para iniciar tu curso, por favor completa los siguientes campos:</p><div role="form" class="wpcf7" id="wpcf7-f9661-p99584-o1" dir="ltr" lang="en-US">
	<div class="screen-reader-response" role="alert" aria-live="polite"></div>
	<html:form action="/DaimlerRegister">
		<div class="hongo-contact-form-style-2">
			<div class="col-sm-6 col-xs-12">
			  	<span class="wpcf7-form-control-wrap contact-name">
			  		<html:text property="firstName" value="" size="40" styleClass="wpcf7-form-control wpcf7-text" />Nombre
			  	</span>
			</div>
			<div class="col-sm-6 col-xs-12">
			  	<span class="wpcf7-form-control-wrap contact-phone">
			  		<html:text property="lastName" value="" size="40" styleClass="wpcf7-form-control wpcf7-text" />Apellido
			  	</span>
			</div>
			<div class="col-sm-12 col-xs-12">
			  	<span class="wpcf7-form-control-wrap contact-message">
			  		<html:text property="email" value="" size="40" styleClass="wpcf7-form-control wpcf7-text"/>Correo Electrónico
			  	</span>
			</div>
			<div class="col-sm-12 col-xs-12">
			  	<span class="wpcf7-form-control-wrap contact-message">
			  		<html:text property="extra" value="" size="40" styleClass="wpcf7-form-control wpcf7-text" />¿Qué uso le das a tu Sprinter?
			  	</span>
			</div>
			<div class="col-sm-6 col-xs-12">
			  	<span class="wpcf7-form-control-wrap contact-email">
			  		<html:text property="user" value="" size="40" styleClass="wpcf7-form-control wpcf7-text wpcf7-email wpcf7-validates-as-email"/>Usuario 
			  		<br> 
				  	<div class="tooltip-wrap">
					  	<p style="font-size: 12px;">
					  		<img src="files_m/images/popup.png" alt="" style="width:20px;" />
					  		(Escribe el número de VIN de tu Sprinter)
					  	</p>
					  	<div class="tooltip-content">
					    	<p><br>..<br></p>
					  	</div> 
					</div>
				</span>
			</div>
			<div class="col-sm-6 col-xs-12">
			  	<span class="wpcf7-form-control-wrap contact-subject">
			  		<html:password property="password" value="" size="40" styleClass="wpcf7-form-control wpcf7-text" />Contraseña 
			  		<br>
			  		<p style="font-size: 12px;">(Viene en la tarjeta que se te entrego previamente)</p>
			  		<br>
			  	</span>
				</div><br><br><div class="col-sm-12 col-xs-12"><br></div>
				<div class="col-sm-12 col-xs-12">
			  		<input style="font-family: Oranienbaum; font-size: 18px;" type="submit" value="Ingresar" class="wpcf7-form-control wpcf7-submit btn btn-large btn-square btn-black alt-font" onclick="register();"><span class="ajax-loader"></span>
				</div>
				<div class="col-sm-12 col-xs-12"><br></div>
				<div class="col-sm-12 col-xs-12">
			  		<span class="wpcf7-form-control-wrap acceptance-181"><span class="wpcf7-form-control wpcf7-acceptance">
			  			<span class="wpcf7-list-item">
			  				<label>
			  					<input type="checkbox" name="acceptance-181" value="1" aria-invalid="false">
			  					<span class="wpcf7-list-item-label">Acepto lo términos de Privacidad de datos de CEPA Mobility Care</span>
			  				</label>
			  			</span>
			  			</span></span>
					</div>
				</div>
				<div class="col-sm-12 col-xs-12"><br></div>
				<div class="col-sm-12 col-xs-12">
			  		<input style="font-family: Oranienbaum; font-size: 18px;" type="button" value="Ya soy usuario registrado" class="wpcf7-form-control wpcf7-submit btn btn-large btn-square btn-black alt-font" onclick="usuarioRegistrado();"><span class="ajax-loader"></span>
				</div>
				<div class="wpcf7-response-output" role="alert" aria-hidden="true"></div>
		</html:form>
		</div></div></div></div></div><div id="elcurso" class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-6"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div><p style="font-size: 52px;color: #000000;line-height: 28px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">El Curso</p><div class="vc_separator wpb_content_element vc_separator_align_center vc_sep_width_100 vc_sep_border_width_2 vc_sep_pos_align_center vc_separator_no_text vc_sep_color_black vc_custom_1602785934286  vc_custom_1602785934286"><span class="vc_sep_holder vc_sep_holder_l"><span class="vc_sep_line"></span></span><span class="vc_sep_holder vc_sep_holder_r"><span class="vc_sep_line"></span></span>
</div><p style="font-size: 22px;color: #020202;line-height: 28px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading" id="temario">Consta de 15 módulos, cada uno con un video de corta duración y un cuestionario para reforzar los conocimientos.</p><div class="vc_empty_space" style="height: 30px"><span class="vc_empty_space_inner"></span></div></div></div></div><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-6"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div></div></div></div></div><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/conduccion_a.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Conducción en condiciones adversas:</div><p style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Reconocer
 algunas de las condiciones climatológicas que producen complicaciones 
en las capacidades del vehículo y el conductor, de forma que los 
participantes reconozcan las limitaciones de una acción correctiva y 
preponderan la aplicación de mecanismos preventivos</p><div class="vc_empty_space" style="height: 10px"><span class="vc_empty_space_inner"></span></div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/hurtos.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Hurtos y robos:</div><div style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Que
 el conductor sea consciente de los riesgos que implica la velocidad 
tanto en las probabilidades de que se produzcan accidentes viales, como 
en la gravedad de sus consecuencias.</div><div class="vc_empty_space" style="height: 10px"><span class="vc_empty_space_inner"></span></div></div></div></div></div><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/Poblacion-Vulnerable.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Población Vulnerable:</div><div style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Identificar
 a los usuarios comprendidos como población vulnerable, conociendo sus 
características y desarrollando empatía hacia ellos, además de acciones 
preventivas que contribuyan a identificarlos y anticipar sus posibles 
comportamientos en la vía pública.</div><div class="vc_empty_space" style="height: 10px"><span class="vc_empty_space_inner"></span></div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/La-Velocidad-y-sus-Consecuencias.jpg" class="vc_single_image-img attachment-full" alt="" srcset="https://salbicha.com/wp-content/uploads/2020/10/La-Velocidad-y-sus-Consecuencias-copy.jpg 150w, https://salbicha.com/wp-content/uploads/2020/10/La-Velocidad-y-sus-Consecuencias-copy-84x54.jpg 84w" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Velocidad y sus consecuencias:</div><div style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Orientar
 al participante acerca de las medidas de seguridad básicas para 
minimizar el riesgo de ser víctima de algún delito en los diferentes 
entornos por los que transita a diario.</div><div class="vc_empty_space" style="height: 10px"><span class="vc_empty_space_inner"></span></div></div></div></div></div><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/Observacion.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Observación, Identificación y Anticipación del Riesgo:</div><div style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Reconocer
 la importancia de la aplicación de un proceso de conducción preventiva 
que contribuya a la anulación del riesgo en el tránsito.</div><div class="vc_empty_space" style="height: 10px"><span class="vc_empty_space_inner"></span></div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/Responsabilidad.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Responsabilidad y decisiones al conducir:</div><div style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Señalar
 a los conductores que la habilidad y la destreza son parte importante 
de la conducción, pero que la aplicación de un comportamiento altamente 
responsable y racional le permitirá actuar preventivamente en lugar de 
forma reactiva, haciendo que las condiciones propias y ajenas sean más 
favorables y seguras.</div><div class="vc_empty_space" style="height: 10px"><span class="vc_empty_space_inner"></span></div></div></div></div></div><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/Gestion.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Gestión del Estrés:</div><div style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Reconocer
 al estrés como un fenómeno biopsicosocial para que los participantes 
puedan identificar los mecanismos que puede ejecutar para que este no 
repercuta en su salud ni en su ejercicio laboral.</div><div class="vc_empty_space" style="height: 10px"><span class="vc_empty_space_inner"></span></div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/Intersecciones.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Intersecciones: Problemática y técnicas preventivas:</div><div style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Reconocer
 a la intersección como un lugar crítico en la consumación de un 
accidente de tránsito de consecuencias graves. Así mismo el conductor 
conocerá las técnicas adecuadas para evitar accidentes de tránsito en 
las intersecciones o cruces.</div><div class="vc_empty_space" style="height: 10px"><span class="vc_empty_space_inner"></span></div></div></div></div></div><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="wpb_single_image    hongo-full-width-single-image">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper  "><img src="files_m/images/conduccion_ecologica.jpg" class="vc_single_image-img attachment-full" alt="" sizes="(max-width: 150px) 100vw, 150px" data-no-retina="" width="150" height="96"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"><div style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Conducción Ecológica y Económica:</div><div style="font-size: 15px;color: #020202;line-height: 20px;text-align: left;font-family:Raleway;font-weight:400;font-style:normal" class="vc_custom_heading">Que
 el conductor identifique las repercusiones positivas de una conducción 
técnica hacia el medio ambiente, la salud y la seguridad.</div></div></div></div><div class="wpb_column vc_column_container vc_col-sm-2 vc_col-xs-5"><div class="vc_column-inner"><div class="wpb_wrapper" id="contacto"></div></div></div><div class="wpb_column vc_column_container vc_col-sm-4 vc_col-xs-7"><div class="vc_column-inner"><div class="wpb_wrapper"></div></div></div></div><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-6"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div><p style="font-size: 52px;color: #000000;line-height: 28px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Contacto</p><div class="vc_separator wpb_content_element vc_separator_align_center vc_sep_width_100 vc_sep_border_width_2 vc_sep_pos_align_center vc_separator_no_text vc_sep_color_black vc_custom_1602785964530  vc_custom_1602785964530"><span class="vc_sep_holder vc_sep_holder_l"><span class="vc_sep_line"></span></span><span class="vc_sep_holder vc_sep_holder_r"><span class="vc_sep_line"></span></span>
</div><p style="font-size: 22px;color: #020202;line-height: 28px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading">Si
 tienes alguna duda sobre como ingresar, el curso o la plataforma, no 
dudes en ponerte en contacto con nostros vía telefónicoa o correo 
electrónico.</p></div></div></div><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-6"><div class="vc_column-inner"><div class="wpb_wrapper"><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div></div></div></div></div><div class="vc_row wpb_row vc_inner vc_row-fluid"><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-2"><div class="vc_column-inner vc_custom_1602657991829"><div class="wpb_wrapper"><div class="wpb_single_image vc_align_right  vc_custom_1602660311861">
			<figure class="wpb_wrapper vc_figure">
				<div class="vc_single_image-wrapper" align="left"><img src="files_m/images/carta.png" class="vc_single_image-img attachment-full" alt="" data-no-retina="" width="45" height="29"></div>
			</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-4"><div class="vc_column-inner vc_custom_1602657356453"><div class="wpb_wrapper">
		<p> 
			<a href="mailto:soportemx@cepamobility.com">
				<span style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading vc_custom_1602777406067">
					soportemx@cepamobility.com
				</span>
			</a>	
		</p>
		</div>
		</div>
		</div>
		<div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-2">
			<div class="vc_column-inner vc_custom_1602657542027">
				<div class="wpb_wrapper">
					<div class="wpb_single_image vc_align_right  vc_custom_1602660281891">
						<figure class="wpb_wrapper vc_figure">
							<div class="vc_single_image-wrapper" align="left">
								<img src="files_m/images/fon.png" class="vc_single_image-img attachment-full" alt="" data-no-retina="" width="31" height="47">
							</div>
						</figure>
			
		</div></div></div></div><div class="wpb_column vc_column_container col-xs-mobile-fullwidth vc_col-sm-4"><div class="vc_column-inner"><div class="wpb_wrapper"><p style="font-size: 22px;color: #020202;line-height: 26px;text-align: left;font-family:Oranienbaum;font-weight:400;font-style:normal" class="vc_custom_heading vc_custom_1602777434053">(52) 55 8537 2874</p></div></div></div></div><div class="vc_empty_space" style="height: 70px"><span class="vc_empty_space_inner"></span></div></div></div></div></section><div class="vc_row-full-width vc_clearfix"></div>
				</div>
								</div>
						</div>
		</div>
</div>			</div><!-- box-layout / hongo-layout -->
				<link rel="stylesheet" id="vc_google_fonts_oranienbaumregular-css" href="files_m/css.css" type="text/css" media="all">
<link rel="stylesheet" id="vc_google_fonts_raleway100200300regular500600700800900-css" href="files_m/css_003.css" type="text/css" media="all">
<script type="text/javascript">
/* <![CDATA[ */
var wpcf7 = {"apiSettings":{"root":"https:\/\/salbicha.com\/wp-json\/contact-form-7\/v1","namespace":"contact-form-7\/v1"}};
/* ]]> */
</script>
<script type="text/javascript" src="files_m/scripts.js"></script>
<script type="text/javascript" src="files_m/bootstrap.js"></script>
<script type="text/javascript" src="files_m/jquery_002.js"></script>
<script type="text/javascript" src="files_m/smooth-scroll.js"></script>
<script type="text/javascript" src="files_m/select2.js"></script>
<script type="text/javascript" src="files_m/wow.js"></script>
<script type="text/javascript" src="files_m/swiper.js"></script>
<script type="text/javascript" src="files_m/isotope.js"></script>
<script type="text/javascript" src="files_m/jquery_003.js"></script>
<script type="text/javascript" src="files_m/jquery_005.js"></script>
<script type="text/javascript" src="files_m/jquery.js"></script>
<script type="text/javascript" src="files_m/imagesloaded.js"></script>
<script type="text/javascript" src="files_m/equalize.js"></script>
<script type="text/javascript" src="files_m/jquery_004.js"></script>
<script type="text/javascript" src="files_m/infinite-scroll.js"></script>
<script type="text/javascript" src="files_m/jquery_008.js"></script>
<script type="text/javascript" src="files_m/retina.js"></script>
<script type="text/javascript" src="files_m/jquery_007.js"></script>
<script type="text/javascript" src="files_m/hongo-parallax.js"></script>
<script type="text/javascript" src="files_m/threesixty.js"></script>
<script type="text/javascript">
/* <![CDATA[ */
var hongoAddons = {"ajaxurl":"https:\/\/salbicha.com\/wp-admin\/admin-ajax.php","site_id":"","disable_scripts":[],"quickview_addtocart_message":null,"quickview_ajax_add_to_cart":null,"compare_text":"Add To Compare","compare_added_text":"Compare products","compare_remove_message":"Are you sure you want to remove?","product_deal_day":"Days","product_deal_hour":"Hours","product_deal_min":"Min","product_deal_sec":"Sec","add_to_wishlist_text":"Add to Wishlist","browse_wishlist_text":"","remove_wishlist_text":"","wishlist_added_message":"","wishlist_remove_message":"","wishlist_addtocart_message":"","wishlist_multi_select_message":"","wishlist_empty_message":"","wishlist_url":"https:\/\/salbicha.com\/wishlist\/","wishlist_icon":"icon-heart","enable_promo_popup":"","display_promo_popup_after":"","delay_time_promo_popup":"","scroll_promo_popup":"","expired_days_promo_popup":"","enable_mobile_promo_popup":"","enable_smart_product":"","display_smart_product_after":"","delay_time_smart_product":"","scroll_smart_product":"","expired_days_smart_product":"","enable_mobile_smart_product":"","menu_breakpoint":"991","cart_url":"","noproductmessage":"No products were found matching your selection."};
/* ]]> */
</script>
<script type="text/javascript" src="files_m/section-builder.js"></script>
<script type="text/javascript">
/* <![CDATA[ */
var hongoMain = {"ajaxurl":"https:\/\/salbicha.com\/wp-admin\/admin-ajax.php","site_id":"","cart_empty_message":"Are you sure you want to empty cart?","loading_image":"https:\/\/salbicha.com\/wp-content\/themes\/hongo\/assets\/images\/loading-black.svg","menu_breakpoint":"991","add_to_cart_fill_icon":"icon-basket-loaded","product_added_message":"Product was added to cart successfully","enable_shop_filter_ajax":"1","zoom_icon":"https:\/\/salbicha.com\/wp-content\/themes\/hongo\/assets\/images\/zoom-icon.svg","zoom_tooltip_text":"Zoom","zoom_enabled":"1","photoswipe_enabled":"1","flexslider_enabled":"1","disable_scripts":[],"enable_zoom_icon":"0","mobileAnimation":"","popup_disableon":"700","variation_animation":"1"};
var simpleLikes = {"ajaxurl":"https:\/\/salbicha.com\/wp-admin\/admin-ajax.php","like":"Like","unlike":"Unlike"};
/* ]]> */
</script>
<script type="text/javascript" src="files_m/main.js"></script>
<script type="text/javascript" src="files_m/wp-embed.js"></script>
<script type="text/javascript" src="files_m/js_composer_front.js"></script>
	
</body></html>