
function MM_preloadImages() { //v3.0
  var d=document;
  if(d.images){
    if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; 
    for(i=0; i<a.length; i++)
    	if (a[i].indexOf("#")!=0){
    		 d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];
    	 }
    }
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;
  if(!d) d=document;
  if((p=n.indexOf("?"))>0&&parent.frames.length) {
    	d=parent.frames[n.substring(p+1)].document;
    	n=n.substring(0,p);
   }
  if(!(x=d[n])&&d.all) x=d.all[n];
  
  for (i=0;!x&&i<d.forms.length;i++)
   	 x=d.forms[i][n];
  
  for(i=0;!x&&d.layers&&i<d.layers.length;i++)
  	 x=MM_findObj(n,d.layers[i].document);
  
  if(!x && d.getElementById)
  	 x=d.getElementById(n);
  
  return x;
}

function MM_nbGroup(event, grpName) { //v6.0
  var i,img,nbArr,args=MM_nbGroup.arguments;
  if (event == "init" && args.length > 2) {
    if ((img = MM_findObj(args[2])) != null && !img.MM_init) {
      img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;
      if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();
      nbArr[nbArr.length] = img;
      for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
        if (!img.MM_up) img.MM_up = img.src;
        img.src = img.MM_dn = args[i+1];
        nbArr[nbArr.length] = img;
    } }
  } else if (event == "over") {
    document.MM_nbOver = nbArr = new Array();
    for (i=1; i < args.length-1; i+=3) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = (img.MM_dn && args[i+2]) ? args[i+2] : ((args[i+1])? args[i+1] : img.MM_up);
      nbArr[nbArr.length] = img;
    }
  } else if (event == "out" ) {
    for (i=0; i < document.MM_nbOver.length; i++) {
      img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; }
  } else if (event == "down") {
    nbArr = document[grpName];
    if (nbArr)
      for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }
    document[grpName] = nbArr = new Array();
    for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = img.MM_dn = (args[i+1])? args[i+1] : img.MM_up;
      nbArr[nbArr.length] = img;
  } }
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr;
   for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++)
   	 x.src=x.oSrc;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments;
   document.MM_sr=new Array;
    for(i=0;i<(a.length-2);i+=3)
   		if ((x=MM_findObj(a[i]))!=null){
   			document.MM_sr[j++]=x;
   			if(!x.oSrc)
   				 x.oSrc=x.src; 
   			x.src=a[i+2];
   		}
}


function tes () {
	alert('Nombre de usuario o contraseña incorrecta');
}


////////////////////////////////////////////////////////////////////////////////////////////

var opera6 = "op6";
var ie5 = "ie5"
var ie55 = "ie55"
var ie6 = "ie6"
var nav46 = "nav4"
var moz1 = "moz1"
 
var nav ="";

function navegador (){
         var sAux = navigator.userAgent
         
         // Opera
         iAux = navigator.userAgent.toLowerCase ().indexOf ("opera") 
         if (iAux > 0 ) {
             sNav = "Opera"
             
             sVer = parseFloat (navigator.userAgent.substr (iAux + 6))
             if (sVer > 5.5) {
               nav = opera6; 
             }
             
         }
         // Explorer
         iAux = navigator.userAgent.toLowerCase ().indexOf ("msie") 
         if (iAux > 0 ) {
             sNav = "Explorer"
             
             sVer = parseFloat (navigator.userAgent.substr (iAux + 5))
             if (sVer >=6) {
               nav = ie6;
             }
             else {
               if (sVer >= 5.5){
                  nav = ie55
               }
               else {
                   if (sVer > 5) {
                      nav = ie5
                   }
               }
             }
         }
         
         // Mozilla
         iAux = navigator.userAgent.toLowerCase ().indexOf ("mozilla") 
         if (iAux >= 0 ) {
             
             sVer = parseFloat (navigator.userAgent.substr (iAux + 8))
             if (sVer < 5) {
                sNav = "Navigator"
                nav = nav46
             }
             else {
                if (navigator.userAgent.toLowerCase ().indexOf ("netscape") >= 0) {
                   sNav = "Navigator"
                }
                else {
                   sNav = "Mozilla"
                }
                nav = moz1;
             }
             
         }
         
         
         
      }
      
      
      



function creaCapa (id, x, y, xx, yy, clase, txt) {
   
//   (document.all) ? creaCapaIE (id, x, y, xx, yy, clase, txt): creaCapaNS (id, x, y, xx, yy, clase, txt);
}
function creaCapaNS (id, x, y, xx, yy, clase, txt) {
   //document.layers[id] = new Layer (xx);
   capa = document.layers[id];
   capa.name = id;
   capa.top = y;
   capa.left = x;
   capa.height = yy;
   capa.visibility = "show";
   capa.document.open ();
   capa.document.write ('<DIV id="' + id+ 'a" class="' + clase + '">' + txt + '</DIV>');
   capa.document.close ();
   
}
function creaCapaIE (id, x, y, xx, yy, clase, txt ) {
var capa = '<DIV id ="' + id + '" class="' + clase + '" style="position:absolute;';
    
   capa += 'left:' + x + 'px;top:' + y + 'px;width:' + xx + 'px;height:' + yy + 'px;;">'
   capa += txt;
   capa += '</div>';
   document.body.insertAdjacentHTML ("BeforeEnd", capa);
   document.forms[0].campo.value +="\n" + capa;
}



i = -1;

var aOpt = new Array ();
   j = -1;
   aOpt [++i] = new Array ();
   aOpt[i][++j] = "Conocimiento"
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Cuaderno"; aOpt[i][j][1] = "cuad/inicio.html"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Encuestas"; aOpt[i][j][1] = "cuad/encuest.html"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Foro Web"; aOpt[i][j][1] = "http://free.corefusion.net/Free/dadomar/home.nsf/FEntrada?openForm&Start=1&Count=25"; aOpt[i][j][2] = "derecha";
   
   
   j = -1;
   aOpt [++i] = new Array ();
   aOpt[i][++j] = "C&oacute;digos"
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Capas"; aOpt[i][j][1] = "http://free.corefusion.net/Free/dadomar/home.nsf/codigosWeb?OpenView&RestrictToCategory=C&Start=1&Count=100"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Formularios"; aOpt[i][j][1] = "http://free.corefusion.net/Free/dadomar/home.nsf/codigosWeb?OpenView&RestrictToCategory=F&Start=1&Count=100"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Men&uacute;s"; aOpt[i][j][1] = "http://free.corefusion.net/Free/dadomar/home.nsf/codigosWeb?OpenView&RestrictToCategory=M&Start=1&Count=100"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Validaciones"; aOpt[i][j][1] = "http://free.corefusion.net/Free/dadomar/home.nsf/codigosWeb?OpenView&RestrictToCategory=V&Start=1&Count=100"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Varios"; aOpt[i][j][1] = "http://free.corefusion.net/Free/dadomar/home.nsf/codigosWeb?OpenView&RestrictToCategory=O&Start=1&Count=100"; aOpt[i][j][2] = "derecha";
   

   j = -1;
   aOpt [++i] = new Array ();
   aOpt[i][++j] = "&Uacute;tiles"
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Descarga"; aOpt[i][j][1] = "util/descarga.html"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Enlaces"; aOpt[i][j][1] = "util/enlaces.html"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Fundaci&oacute;n"; aOpt[i][j][1] = "util/funda.html"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Programas"; aOpt[i][j][1] = "util/programas.html"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Tutoriales"; aOpt[i][j][1] = "util/tutor.html"; aOpt[i][j][2] = "derecha";
   
   
   j = -1;
   aOpt [++i] = new Array ();
   aOpt[i][++j] = "Autor"
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Ayer"; aOpt[i][j][1] = "yo/ayer.html"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Hoy"; aOpt[i][j][1] = "yo/hoy.html"; aOpt[i][j][2] = "derecha";
   aOpt[i][++j] = new Array ();
   aOpt[i][j][0] = "Siempre"; aOpt[i][j][1] = "yo/gracias.html"; aOpt[i][j][2] = "derecha";
   
   
   var aVis = new Array ();
   
   yInc = 0;
   yyP = 0;
   yyS = 0;
   
   
   
   function pinta (aOpt, x, y, ancho, tab, altoP, altoS, estiloP, estiloS, fontP, fontS) {
      if (!(document.all)) {
         altoS = altoP
      }
      yInc = y;
      yyP = altoP;
      yyS = altoS;
      
      yAcum = y;
      
      for (i = 0; i < aOpt.length; i++) {
         aVis[i] = false;      
         
         creaCapa ('menu_' + i, x, yAcum, ancho, altoP, estiloP, '<A href="JavaScript:abre (' + i + ')" class="' + fontP + '">' + aOpt[i][0] + '</A>')
         yAcum += altoP ;
         for (j= 1; j < aOpt[i].length; j++) {
            sAntes= '<A href="' + aOpt[i][j][1]+ '" ';
            sAntes += (aOpt[i][j][2] == "") ? "": 'target= "' +  aOpt[i][j][2]+ '" ';
            sAntes +=  'class="' + fontS + '">';
            
            creaCapa ('menu_' + i + '_' + j, x + tab, yAcum, ancho - tab, altoS, estiloS, sAntes + aOpt[i][j][0] + '</A>')
         }
         
         
      }
      if (!(document.all)) {
         abre (-1)
      }      
   }
   function abre (sel){
      yAcum = yInc;
      for (i= 0; i < aVis.length; i++) {
         
         if (document.all) {
            c = eval  ('document.all.menu_' + i + '.style')
            c.top = yAcum
            
            yAcum += yyP;
            aVis [i] = (i == sel) ? !aVis[i] : false;
            
            if (aVis [i]) { // Mostrar
               for (a = 1; a < aOpt[i].length; a++) {
                  
                  c = eval ('document.all.menu_' + i + '_' + a)
                  c.style.top = yAcum;
                  c.style.visibility = "visible";
                  yAcum += yyS;  
               }
               
            }
            else { // Ocultar
               for (a = 1; a < aOpt[i].length; a++) {
                 
                  c = eval ('document.all.menu_' + i + '_' + a )
                  c.style.visibility = "hidden";
               }
               
            }
            
         }
         else {
            if (nav == nav46) {
               c = document.layers["menu_" + i]
               c.top = yAcum;
            }
            else{
               c = document.getElementById("menu_" + i);
               c.style.top = yAcum;
            }
            
            
            yAcum += yyP;
            aVis [i] = (i == sel) ? !aVis[i] : false;
            
            if (aVis [i]) { // Mostrar
               for (a = 1; a < aOpt[i].length; a++) {
                  
                  if (nav == nav46) {
                     c = document.layers["menu_" + i + "_" + a]
                     c.top = yAcum + 2;
                     c.visibility = "visible";
                  }
                  else{
                     c = document.getElementById("menu_" + i + "_" + a);
                     
                     c.style.top = "" + yAcum + "px;";
                     
                     c.style.visibility = "visible";
                  }
                  
                  
                  
                  
                  yAcum += yyS;  
               }
               
            }
            else { // Ocultar
               for (a = 1; a < aOpt[i].length; a++) {
                 
                  if (nav == nav46) {
                     c = document.layers["menu_" + i + "_" + a]
                     c.visibility = "hide";
                  }
                  else{
                     c = document.getElementById("menu_" + i + "_" + a);
                     c.style.visibility = "hidden";
                  }
            
                  
                 
               }
               
            }
         }
      }
   }










