<!doctype html>

<html lang="en">
   <head>
      <meta charset="iso-8859-1">
      <meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />
      <meta name="apple-mobile-web-app-capable" content="yes" />
      <meta name="viewport" content="user-scalable=no, width=980" />
      <meta name="description" content="">
      <meta name="keywords" content="">
      <title>CEPA Assessment</title>
      <link rel="shortcut icon" type="image/ico" href="images/favicon.ico">
      <link rel="stylesheet" href="styles/base.css">
      <link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
      <script type="text/javascript">
      	function sendData() {
      		var contact = {
     			     "Name": "John Doe",
     			     "PermissionToCall": true,
     			     "PhoneNumbers": [ 
     			       {
     			           "Location": "Home",
     			           "Number": "555-555-1234"
     			       },
     			       {
     			           "Location": "Work",
     			           "Number": "555-555-9999 Ext. 123"
     			       }
     			     ]
     			};
 			alert(contact);
 			document.forms['assessment']['jsonobj'].value = contact;
	    	document.forms['assessment'].submit();
      	}
      </script>
   </head>
   <body>
      <header>
         <section class="grid_container">
            <h1>CEPA Assessment</h1>
            <div class="toolbar">
               <div class="customer_logo" style="background-image: url('images/pepsico_logo.png');"></div>
               <span class="username">Jorge P�rez</span>
               <span class="exit"><a href="#">Salir</a></span>
            </div>
         </section>
      </header>


      <section id="content">
         <section class="grid_container">
            <nav class="sections" data-min-rel-top="0" data-min-rel-bottom="0">
               <div class="score">
                  <h2 class="title">Cultura General de Seguridad</h2>
                  <span class="numbers">--/--</span>
                  <span class="percent"></span>
               </div>
               <ul class="sections unstyled">
                  <li class="completed">
                     <a class="title" href="module_da.jsp?module=554">1. Datos Personales</a>
                  </li>
                  <li class="pending">
                     <a class="title" href="module_da.jsp?module=556">2. Conocimientos de la Pol�tica de Seguridad</a>
                  </li>
                  <li class="pending active">
                     <a class="title" href="module_da.jsp?module=555">3. Cultura General de Seguridad</a>
                  </li>
                  <li>
                     <a class="title" href="module_da.jsp?module=557">4. Conocimientos de Seguridad Vehicular</a>
                  </li>
                  <li>
                     <a class="title" href="module_da.jsp?module=0">5. Test de Comportamiento</a>
                  </li>
                  </div>
               </ul>
               <p class="copyright">CEPA Safe Drive � 2013</p>
            </nav>
            <form id="assessment" name="assessment" action="result.jsp">
            	<input type="hidden" name="jsonobj" />
<% 		String link = "module.jsp?module=554"; %>         
				<jsp:include page='<%=link%>'/>
              	<input type="button" onclick="javascript:sendData();">
             </form>
         </section>
      </section>
		<script type="text/javascript" src="scripts/json2.min.js"></script>
		<!--[if lt IE 9]>
			<script type="text/javascript" src="scripts/jquery-1.11.1.min.js"></script>
		<![endif]-->
		<!--[if gte IE 9]><!-->
			<script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
		<!--<![endif]-->
		<script type="text/javascript" src="scripts/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.ui.datepicker-pt.js"></script>
		<script type="text/javascript" src="scripts/jquery.ui.datepicker-es.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.loggable.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.assessmentController.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.question.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.card.js"></script>
		<script type="text/javascript" src="scripts/jquery.tadeouy.assessment.js"></script>
		<script type="text/javascript" src="scripts/assessment.js"></script>
         (function($){
            $(document).ready(function() {
               if (!window.mobilecheck()){
                  $("fieldset > div > input[type='date']").prop('id','').datepicker().prop('type','text');
               }
               var $completedFieldsets = $('fieldset.completed');
               $completedFieldsets.prop('disabled', true);
            });
         })(jQuery);
      </script>
   </body>
</html>
