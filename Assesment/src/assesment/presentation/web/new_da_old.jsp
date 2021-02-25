<!doctype html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=9; IE=EDGE" />
      <meta name="apple-mobile-web-app-capable" content="yes" />
      <meta name="viewport" content="user-scalable=no, width=980" />
      <meta name="description" content="">
      <meta name="keywords" content="">
      <title>CEPA Assessment</title>
      <link rel="shortcut icon" type="image/ico" href="images/favicon.ico">
      <link rel="stylesheet" href="styles/base.css">
      <link href="styles/fonts/pontano_sans.css" rel="stylesheet" type="text/css">
      <link rel="stylesheet" href="styles/jquery-ui-1.10.3.custom.min.css">
   </head>
   <body>
      <header>
         <section class="grid_container">
            <h1>CEPA Assessment</h1>
            <ul class="language_selector">
               <li><a href="/es">español</a></li>
               <li><a href="/pt">português</a></li>
            </ul>
         </section>
      </header>
      <section id="content">
         <section class="grid_container">
			<jsp:include page='modules.jsp'/>
         </section>
      </section>
      <footer>
         <section class="grid_container">
            <p class="copyright">
               CEPA Safe Drive © 2013
            </p>
         </section>
      </footer>
      <script type="text/javascript" src="scripts/jquery-2.0.2.min.js"></script>
      <script type="text/javascript" src="scripts/jquery-ui-1.10.3.custom.min.js"></script>
      <script type="text/javascript" src="scripts/assessment.js"></script>
      <script>
         (function($){
            $(document).ready(function() {
               if (!window.mobilecheck()){
                  $("fieldset > div > input[type='date']").prop('id','').datepicker().prop('type','text');
               }

               var $completedFieldsets = $('fieldset.completed');
               $completedFieldsets.find(':input').prop('disabled', true);
            });
         })(jQuery);
      </script>
   </body>
</html>
