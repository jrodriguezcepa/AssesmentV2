<%@page import="java.util.HashMap"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<html>
	<head>
		<meta charset="iso-8859-1">
	</head>
	<body>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys == null) {
		response.sendRedirect("logout.jsp");
	}else {
		UserSessionData userSessionData = sys.getUserSessionData();
	    HashMap hash = sys.getUserReportFacade().getModuleHashResult(userSessionData.getFilter().getLoginName(),new Integer(1079),userSessionData);
	    String[][] values = {{"Commercial","Sales","Agroceres"}, {"Commercial","Sales","Agroceres - Cascavel"}, {"Commercial","Sales","Agroceres - Centro Sul"}, {"Commercial","Sales","Agroceres - Dourados"}, {"Commercial","Sales","Agroceres - Londrina"}, {"Commercial","Sales","Agroceres - Médio Norte"}, {"Commercial","Sales","Agroceres - Palmas"}, {"Commercial","Sales","Agroceres - Rio Verde"}, {"Commercial","Sales","Agroceres - RS"}, {"Commercial","Sales","Agroceres - SENE"}, {"Commercial","Sales","Agroceres - Uberlândia"}, {"Commercial","Sales","Agroeste"}, {"Commercial","Sales","CENU"}, {"Commercial","Sales","Climate"}, {"Commercial","Sales","Dekalb"}, {"Commercial","Sales","Crop Protection - Centro"}, {"Commercial","Sales","Crop Protection - Norte"}, {"Commercial","Sales","Crop Protection - Sul"}, {"Commercial","Sales","Dekalb - Cascavel"}, {"Commercial","Sales","Dekalb - Centro Sul"}, {"Commercial","Sales","Dekalb - Londrina"}, {"Commercial","Sales","Dekalb - Médio Norte"}, {"Commercial","Sales","Dekalb - MS"}, {"Commercial","Sales","Dekalb - MT/GO"}, {"Commercial","Sales","Dekalb - Palmas"}, {"Commercial","Sales","Dekalb - RS"}, {"Commercial","Sales","Dekalb - SP"}, {"Commercial","Sales","Dekalb - Uberlândia"}, {"Commercial","Sales","Intacta - Centro"}, {"Commercial","Sales","Intacta - CERL"}, {"Commercial","Sales","Intacta - CERO"}, {"Commercial","Sales","Intacta - PRNA"}, {"Commercial","Sales","Intacta - RSSC"}, {"Commercial","Sales","KAM 1"}, {"Commercial","Sales","KAM 2"}, {"Commercial","Sales","Soybean - Centro"}, {"Commercial","Sales","Soybean - CERL"}, {"Commercial","Sales","Soybean - CERO"}, {"Commercial","Sales","Soybean - Monsoy"}, {"Commercial","Sales","Soybean - PRNA"}, {"Commercial","Sales","Soybean - Sul Leste"}, {"Commercial","Sales","Soybean - Sul Oeste"}, {"Global Biotechnology","Global Biotechnology","Santa Cruz das Palmeiras"}, {"Global Crop Protection","Crop Protection","São José dos Campos"}, {"Global Vegetables","Breeding - Seminis","Breeding - Seminis"}, {"Global Vegetables","Seminis - Quality","Seminis"}, {"Global Vegetables","Seminis - Sales","Seminis North"}, {"Global Vegetables","","Seminis South"}, {"Global Vegetables","Seminis TD","Seminis"}, {"LAN & SA Talent Acquisition","Human Resources","CENU"}, {"NeXT","Conversion","Petrolina"}, {"SA Breeding","BR Breeding Programs","Morrinhos"}, {"SA Breeding","SA Strategy & Ops","Campinas"}, {"SA Engineering","Engineering","Escritório - UDI"}, {"SA Engineering","Engineering","Uberlândia - Site"}, {"SA ESH","SA ESH","Escritório - UDI"}, {"SA Finance","Credit & Collection","CENU"}, {"SA Finance","Finance","CENU"}, {"SA Hub","SA Hub","CENU"}, {"SA Human Resources","Human Resources","CENU"}, {"SA Human Resources","SA Human Resources","CENU"}, {"SA Legal","Legal","CENU"}, {"SA M&PM","Crop Protection","Crop Protection"}, {"SA M&PM","Global Supply SA","CENU"}, {"SA M&PM","SA Corn Business","CENU"}, {"SA M&PM","SA Customer Care","Santa Helena - Site"}, {"SA M&PM","SA Customer Strategy","Corn"}, {"SA M&PM","SA Customer Strategy","Customer"}, {"SA M&PM","SA Customer Strategy","Dekalb"}, {"SA M&PM","SA M&PM","CENU"}, {"SA M&PM","SA PMO","CENU"}, {"SA M&PM","SA Soybean Business","CENU"}, {"SA M&PM","Soybean","Soybean"}, {"SA MSP","BR MSP","Petrolina"}, {"SA Production Research","BR Production Research","Escritório - UDI"}, {"SA Seed Applied Solutions","BioAg","BioAg"}, {"SA Supply Chain","Make  - Field & Operations","Escritório - UDI"}, {"SA Supply Chain","Make - Field & Plant Operations","Campo Verde"}, {"SA Supply Chain","Make - Field & Plant Operations","Ipuã"}, {"SA Supply Chain","Make - Field & Plant Operations","ItaÃ­"}, {"SA Supply Chain","Make - Field & Plant Operations","Paracatu - Site"}, {"SA Supply Chain","Make - Field & Plant Operations","Santa Helena - Site"}, {"SA Supply Chain","Make - Field & Plant Operations","Uberlândia"}, {"SA Supply Chain","Make - Field & Plant Operations","Uberlândia - Site"}, {"SA Supply Chain","Make - Maintenance ","Cachoeira Dourada - Site"}, {"SA Supply Chain","Make - Maintenance ","CENU"}, {"SA Supply Chain","Make - Maintenance ","Escritório - UDI"}, {"SA Supply Chain","Make - Maintenance ","Paracatu - Site"}, {"SA Supply Chain","Make - Maintenance ","Uberlândia - Site"}, {"SA Supply Chain","Make - Operations Intelligence","CENU"}, {"SA Supply Chain","Make - Operations Intelligence","Escritório - UDI"}, {"SA Supply Chain","Make - Operations Intelligence","Uberlândia - Site"}, {"SA Supply Chain","Make - Pre Commercial","Cachoeira Dourada"}, {"SA Supply Chain","Make - Pre Commercial","Escritório - UDI"}, {"SA Supply Chain","Make - Pre Commercial","Petrolina"}, {"SA Supply Chain","Make - Soy & Cotton Production","CENU"}, {"SA Supply Chain","Make - Soy & Cotton Production","Escritório - UDI"}, {"SA Supply Chain","Make - Soy & Cotton Production","Morrinhos"}, {"SA Supply Chain","Make - Soy & Cotton Production","Soybean - Pre Foundation"}, {"SA Supply Chain","SA Customer Care","CENU"}, {"SA Supply Chain","SA ESH","Campinas"}, {"SA Supply Chain","SA ESH","Campo Verde"}, {"SA Supply Chain","SA ESH","CENU"}, {"SA Supply Chain","SA ESH","Escritório - UDI"}, {"SA Supply Chain","SA ESH","ItaÃ­"}, {"SA Supply Chain","SA ESH","Petrolina"}, {"SA Supply Chain","SA ESH","Porto Nacional"}, {"SA Supply Chain","SA ESH","Rolândia"}, {"SA Supply Chain","SA ESH","Sao José do Campos"}, {"SA Supply Chain","SA ESH","Uberlândia - Escritório"}, {"SA Supply Chain","SA ESH","Uberlândia - Site"}, {"SA Supply Chain","SA Make Row Crops","CENU"}, {"SA Supply Chain","SA Planning","CENU"}, {"SA Supply Chain","SA Planning","Escritório - UDI"}, {"SA Supply Chain","SA Procurement","CENU"}, {"SA Supply Chain","SA Procuremente & Global Trade","CENU"}, {"SA Supply Chain","SA Strategy & Ops","CENU"}, {"SA Supply Chain","SA Supply Chain","CENU"}, {"SA TD Operations","BR Technology Development","TD Operations"}, {"SA Technology Development","BR Agronomy TD","Agronomy Subtropical"}, {"SA Technology Development","BR Agronomy TD","Agronomy Tropical Eastern"}, {"SA Technology Development","BR Agronomy TD","Agronomy Tropical Western"}, {"SA Technology Development","BR Agronomy TD","CENU"}, {"SA Technology Development","BR Agronomy TD","Knowledge Transfer"}, {"SA Technology Development","BR Technology Development","CENU"}, {"SA Technology Development","BR Technology Development","Subtropical"}, {"SA Technology Development","BR Technology Development","TD Operations"}, {"SA Technology Development","BR Technology Development","Tropical Eastern"}, {"SA Technology Development","BR Technology Development","Tropical Western"}, {"Seminis","Carrot","CarandaÃ­"}, {"Seminis","Carrot","Uberlândia - Site"}};
	    String[] cities = {"Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","RondÃ´nia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins"};
%>		
	
<%@page import="assesment.communication.administration.UserAnswerData"%>
<%@page import="assesment.presentation.translator.web.util.Util"%><script type="text/javascript">
		function selectDivision(division) {
			var sub = document.forms['assessment'];
			var $el = $("#q17588");
			var $el2 = $("#q17589");
			if(division.selectedIndex == 0) {
				$el.empty();
				$el.append($("<option></option>").attr("value", "").text("------"));
				$el2.empty();
				$el2.append($("<option></option>").attr("value", "").text("------"));
			} else if (division.selectedIndex == 22) {
				$el.empty();
				$el.append($("<option></option>").attr("value", "").text("------"));
				$el.append($("<option></option>").attr("value", "Outro").text("Outro"));
				$el2.empty();
				$el2.append($("<option></option>").attr("value", "").text("------"));
				$el2.append($("<option></option>").attr("value", "Outro").text("Outro"));
			} else {
				switch(division.selectedIndex) {
					case 1:
						var newOptions = ["Sales"];
						break;
					case 2:
						var newOptions = ["Global Biotechnology"];
						break;
					case 3:
						var newOptions = ["Crop Protection"];
						break;
					case 4:
						var newOptions = ["Breeding - Seminis" ,"Seminis - Quality" ,"Seminis - Sales" ,"Seminis TD"];
						break;
					case 5:
						var newOptions = ["Human Resources"];
						break;
					case 6:
						var newOptions = ["Conversion"];
						break;
					case 7:
						var newOptions = ["BR Breeding Programs" ,"SA Strategy & Ops"];
						break;
					case 8:
						var newOptions = ["Engineering"];
						break;
					case 9:
						var newOptions = ["SA ESH"];
						break;
					case 10:
						var newOptions = ["Credit & Collection","Finance"];
						break;
					case 11:
						var newOptions = ["SA Hub"];
						break;
					case 12:
						var newOptions = ["Human Resources" ,"SA Human Resources"];
						break;
					case 13:
						var newOptions = ["Legal"];
						break;
					case 14:
						var newOptions = ["Crop Protection" ,"Global Supply SA" ,"SA Corn Business" ,"SA Customer Care" ,"SA Customer Strategy" ,"SA M&PM" ,"SA PMO" ,"SA Soybean Business" ,"Soybean"];
						break;
					case 15:
						var newOptions = ["BR MSP"];
						break;
					case 16:
						var newOptions = ["BR Production Research"];
						break;
					case 17:
						var newOptions = ["BioAg"];
						break;
					case 18:
						var newOptions = ["Make  - Field & Operations" ,"Make - Field & Plant Operations" ,"Make - Maintenance" ,"Make - Operations Intelligence" ,"Make - Pre Commercial" ,"Make - Soy & Cotton Production" ,"SA Customer Care" ,"SA ESH" ,"SA Make Row Crops" ,"SA Planning" ,"SA Procurement" ,"SA Procuremente & Global Trade" ,"SA Strategy & Ops" ,"SA Supply Chain"];
						break;
					case 19:
						var newOptions = ["BR Technology Development"];
						break;
					case 20:
						var newOptions = ["BR Agronomy TD" ,"BR Technology Development"];
						break;
					case 21:
						var newOptions = ["Carrot"];
						break;
				}
				$el.empty();
				$el.append($("<option></option>").attr("value", "").text("------"));
				$.each(newOptions, function(value) {
					$el.append($("<option></option>").attr("value", newOptions[value]).text(newOptions[value]));
				});
				$el.append($("<option></option>").attr("value", "Outro").text("Outro"));
				$el.selectedIndex = 0;
				$el2.empty();
				$el2.append($("<option></option>").attr("value", "").text("------"));
			}
		}
		function selectSubDivision(subdivision) {
			var $el1 = $("#q17586");
			var $el2 = $("#q17588");
			var $el3 = $("#q17589");
			if($el2.prop("selectedIndex") == 0) {
				$el3.empty();
				$el3.append($("<option></option>").attr("value", "").text("------"));
			} else if ($el2.val() == "Outro") {
				$el3.empty();
				$el3.append($("<option></option>").attr("value", "").text("------"));
				$el3.append($("<option></option>").attr("value", "Outro").text("Outro"));
			} else {
				switch($el1.prop("selectedIndex")) {
					case 1:
						var newOptions = ["Agroceres", "Agroceres - Cascavel", "Agroceres - Centro Sul", "Agroceres - Dourados", "Agroceres - Londrina", "Agroceres - Médio Norte", "Agroceres - Palmas", "Agroceres - Rio Verde", "Agroceres - RS", "Agroceres - SENE", "Agroceres - Uberlândia", "Agroeste", "CENU", "Climate", "Dekalb", "Crop Protection - Centro", "Crop Protection - Norte", "Crop Protection - Sul", "Dekalb - Cascavel", "Dekalb - Centro Sul", "Dekalb - Londrina", "Dekalb - Médio Norte", "Dekalb - MS", "Dekalb - MT/GO", "Dekalb - Palmas", "Dekalb - RS", "Dekalb - SP", "Dekalb - Uberlândia", "Intacta - Centro", "Intacta - CERL", "Intacta - CERO", "Intacta - PRNA", "Intacta - RSSC", "KAM 1", "KAM 2", "Soybean - Centro", "Soybean - CERL", "Soybean - CERO", "Soybean - Monsoy", "Soybean - PRNA", "Soybean - Sul Leste", "Soybean - Sul Oeste"];
						break;
					case 2:
						var newOptions = ["Santa Cruz das Palmeiras"];
						break;
					case 3:
						var newOptions = ["São José dos Campos"];
						break;
					case 4:
						switch($el2.prop("selectedIndex")) {
							case 1:
								var newOptions = ["Breeding - Seminis"];
								break;
							case 2:
								var newOptions = ["Seminis"];
								break;
							case 3:
								var newOptions = ["Seminis North","Seminis South"];
								break;
							case 4:
								var newOptions = ["Seminis"];
								break;
						}
						break;
					case 5:
						var newOptions = ["CENU"];
						break;
					case 6:
						var newOptions = ["Petrolina"];
						break;
					case 7:
						switch($el2.prop("selectedIndex")) {
							case 1:
								var newOptions = ["Morrinhos"];
								break;
							case 2:
								var newOptions = ["Campinas"];
								break;
						}
						break;
					case 8:
						var newOptions = ["Escritório - UDI","Uberlândia - Site"];
						break;
					case 9:
						var newOptions = ["Escritório - UDI"];
						break;
					case 10:
						var newOptions = ["CENU"];
						break;
					case 11:
						var newOptions = ["CENU"];
						break;
					case 12:
						var newOptions = ["CENU"];
						break;
					case 13:
						var newOptions = ["CENU"];
						break;
					case 14:
						switch($el2.prop("selectedIndex")) {
							case 1:
								var newOptions = ["Crop Protection"];
								break;
							case 2:
								var newOptions = ["CENU"];
								break;
							case 3:
								var newOptions = ["CENU"];
								break;
							case 4:
								var newOptions = ["Santa Helena - Site"];
								break;
							case 5:
								var newOptions = ["Corn","Customer","Dekalb"];
								break;
							case 6:
								var newOptions = ["CENU"];
								break;
							case 7:
								var newOptions = ["CENU"];
								break;
							case 8:
								var newOptions = ["CENU"];
								break;
							case 9:
								var newOptions = ["Soybean"];
								break;
						}
						break;
					case 15:
						var newOptions = ["Petrolina"];
						break;
					case 16:
						var newOptions = ["Escritório - UDI"];
						break;
					case 17:
						var newOptions = ["BioAg"];
						break;
					case 18:
						switch($el2.prop("selectedIndex")) {
							case 1:
								var newOptions = ["Escritório - UDI"];
								break;
							case 2:
								var newOptions = ["Campo Verde","Ipuã","Itaí","Paracatu - Site","Santa Helena - Site","Uberlândia","Uberlândia - Site"];
								break;
							case 3:
								var newOptions = ["Cachoeira Dourada - Site","CENU","Escritório - UDI","Paracatu - Site","Uberlândia - Site"];
								break;
							case 4:
								var newOptions = ["CENU","Escritório - UDI","Uberlândia - Site"];
								break;
							case 5:
								var newOptions = ["Cachoeira Dourada","Escritório - UDI","Petrolina"];
								break;
							case 6:
								var newOptions = ["CENU","Escritório - UDI","Morrinhos","Soybean - Pre Foundation"];
								break;
							case 7:
								var newOptions = ["CENU"];
								break;
							case 8:
								var newOptions = ["Campinas","Campo Verde","CENU","Escritório - UDI","Itaí","Petrolina","Porto Nacional","Rolândia","Sao José do Campos","Uberlândia - Escritório","Uberlândia - Site"];
								break;
							case 9:
								var newOptions = ["CENU"];
								break;
							case 10:
								var newOptions = ["CENU","Escritório - UDI"];
								break;
							case 11:
								var newOptions = ["CENU"];
								break;
							case 12:
								var newOptions = ["CENU"];
								break;
							case 13:
								var newOptions = ["CENU"];
								break;
							case 14:
								var newOptions = ["CENU"];
								break;
						}
						break;
					case 19:
						var newOptions = ["TD Operations"];
						break;
					case 20:
						switch($el2.prop("selectedIndex")) {
							case 1:
								var newOptions = ["Agronomy Subtropical","Agronomy Tropical Eastern","Agronomy Tropical Western","CENU","Knowledge Transfer"];
								break;
							case 2:
								var newOptions = ["CENU","Subtropical","TD Operations","Tropical Eastern","Tropical Western"];
								break;
						}
						break;
					case 21:
						var newOptions = ["Carandaí","Uberlândia - Site"];
						break;
				}
				$el3.empty();
				$el3.append($("<option></option>").attr("value", "").text("------"));
				$.each(newOptions, function(value) {
					$el3.append($("<option></option>").attr("value", newOptions[value]).text(newOptions[value]));
				});
				$el3.append($("<option></option>").attr("value", "Outro").text("Outro"));
				$el3.selectedIndex = 0;
			}
		}
		</script>
		<form id="assessment" name="assessment" action="result.jsp" data-module="1079"  data-success-redirect="module_da.jsp">
<%		String completed = "";
		Integer questionId = new Integer(17581);
       	String city = "";
		if(hash.containsKey(questionId)) {
			city = ((UserAnswerData)hash.get(questionId)).getText();
  	        completed = "data-completed=\"true\"";
  	    }
%>			<fieldset>
				<div id="qw17581" data-type="1" <%=completed%> >
					<label for="q17581">Cidade</label>
					<input id="q17581" name="q17581" type="text" value='<%=city%>'>
				</div>
			</fieldset>
<%		completed = "";
		questionId = new Integer(17582);
       	String state = "";
		if(hash.containsKey(questionId)) {
			state = ((UserAnswerData)hash.get(questionId)).getText();
  	        completed = "data-completed=\"true\"";
  	    }
%>			<fieldset>
				<div id="qw17582" data-type="4" <%=completed%> >
                    <label for="q17582">Estado</label>
	               	<select id="q17582" name="q17582">
  		              	<option value="">---------</option>
<%		for(int i = 0; i < cities.length; i++) {
%>						<option value='<%=cities[i]%>' <%=(state.equals(cities[i])) ? "selected" : ""%>><%=cities[i]%></option>
<%		}
%>
					</select>
				</div>
			</fieldset>
<%		completed = "";
		questionId = new Integer(17583);
       	String phone = "";
		if(hash.containsKey(questionId)) {
			phone = ((UserAnswerData)hash.get(questionId)).getText();
  	        completed = "data-completed=\"true\"";
  	    }
%>			<fieldset>
				<div id="qw17583" data-type="1" <%=completed%> >
                	<label for="q17583">Telefone (DDD + Número)</label>
                  	<input id="q17583" name="q17583" type="text" value='<%=phone%>'>
				</div>
			</fieldset>
<%		completed = "";
		questionId = new Integer(17584);
       	String date = "";
		if(hash.containsKey(questionId)) {
			date = Util.formatDate(((UserAnswerData)hash.get(questionId)).getDate());
  	        completed = "data-completed=\"true\"";
  	    }
%>			<fieldset>
				<div id="qw17584" data-type="2" <%=completed%> >
                	<label for="q17584">Data de Vencimento CNH</label>
                  	<input id="q17584" name="q17584" type="date" value='<%=date%>'>
				</div>
			</fieldset>
<%		completed = "";
		questionId = new Integer(17585);
       	UserAnswerData userAnswerData = null;
		if(hash.containsKey(questionId)) {
			userAnswerData = (UserAnswerData)hash.get(questionId);
  	        completed = "data-completed=\"true\"";
  	    }
%>			<fieldset>
				<div id="qw17585" data-type="8" <%=completed%> >
                    <label for="q17585">Tipo de veículo autorizado a dirigir</label>
					<label class="checkbox"><input type="checkbox" name="q17585" value="61773" <%=(userAnswerData != null && userAnswerData.containsAnswer(new Integer(61773))) ? "checked" : ""%>>Automóvel</label>
					<label class="checkbox"><input type="checkbox" name="q17585" value="61771" <%=(userAnswerData != null && userAnswerData.containsAnswer(new Integer(61771))) ? "checked" : ""%>>Moto</label>
					<label class="checkbox"><input type="checkbox" name="q17585" value="61809" <%=(userAnswerData != null && userAnswerData.containsAnswer(new Integer(61809))) ? "checked" : ""%>>Pesados</label>
					<label class="checkbox"><input type="checkbox" name="q17585" value="61804" <%=(userAnswerData != null && userAnswerData.containsAnswer(new Integer(61804))) ? "checked" : ""%>>Pick Up Leve (Strada, Montana, Etc.)</label>
					<label class="checkbox"><input type="checkbox" name="q17585" value="61805" <%=(userAnswerData != null && userAnswerData.containsAnswer(new Integer(61805))) ? "checked" : ""%>>Pick Up Média (S10, Ranger, Etc.)</label>
					<label class="checkbox"><input type="checkbox" name="q17585" value="61806" <%=(userAnswerData != null && userAnswerData.containsAnswer(new Integer(61806))) ? "checked" : ""%>>Utilitário Esportivo (Ecosport, Santafé, Etc.)</label>
					<label class="checkbox"><input type="checkbox" name="q17585" value="61807" <%=(userAnswerData != null && userAnswerData.containsAnswer(new Integer(61807))) ? "checked" : ""%>>Van</label>
					<label class="checkbox"><input type="checkbox" name="q17585" value="61808" <%=(userAnswerData != null && userAnswerData.containsAnswer(new Integer(61808))) ? "checked" : ""%>>Veículo de Emergência</label>
				</div>
			</fieldset>
			<fieldset>
<%		completed = "";
		questionId = new Integer(17586);
       	String division = "";
		if(hash.containsKey(questionId)) {
  	      	division = ((UserAnswerData)hash.get(questionId)).getText();
  	        completed = "data-completed=\"true\"";
  	    }
%>				<div id="qw17586" data-type="4" <%=completed%> >
            		<label for="q17586">Diretoria</label>
                  	<select id="q17586" name="q17586" onchange="selectDivision(this)">
  		              	<option value="">------</option>
						<option value="Commercial" <%=(division.equals("Commercial")) ? "selected" : "" %>>Commercial</option>
						<option value="Global Biotechnology" <%=(division.equals("Global Biotechnology")) ? "selected" : "" %>>Global Biotechnology</option>
						<option value="Global Crop Protection" <%=(division.equals("Global Crop Protection")) ? "selected" : "" %>>Global Crop Protection</option>
						<option value="Global Vegetables" <%=(division.equals("Global Vegetables")) ? "selected" : "" %>>Global Vegetables</option>
						<option value="LAN & SA Talent Acquisition" <%=(division.equals("LAN & SA Talent Acquisition")) ? "selected" : "" %>>LAN & SA Talent Acquisition</option>
						<option value="NeXT" <%=(division.equals("NeXT")) ? "selected" : "" %>>NeXT</option>
						<option value="SA Breeding" <%=(division.equals("SA Breeding")) ? "selected" : "" %>>SA Breeding</option>
						<option value="SA Engineering" <%=(division.equals("SA Engineering")) ? "selected" : "" %>>SA Engineering</option>
						<option value="SA ESH" <%=(division.equals("SA ESH")) ? "selected" : "" %>>SA ESH</option>
						<option value="SA Finance" <%=(division.equals("SA Finance")) ? "selected" : "" %>>SA Finance</option>
						<option value="SA Hub" <%=(division.equals("SA Hub")) ? "selected" : "" %>>SA Hub</option>
						<option value="SA Human Resources" <%=(division.equals("SA Human Resources")) ? "selected" : "" %>>SA Human Resources</option>
						<option value="SA Legal" <%=(division.equals("SA Legal")) ? "selected" : "" %>>SA Legal</option>
						<option value="SA M&PM" <%=(division.equals("SA M&PM")) ? "selected" : "" %>>SA M&PM</option>
						<option value="SA MSP" <%=(division.equals("SA MSP")) ? "selected" : "" %>>SA MSP</option>
						<option value="SA Production Research" <%=(division.equals("SA Production Research")) ? "selected" : "" %>>SA Production Research</option>
						<option value="SA Seed Applied Solutions" <%=(division.equals("SA Seed Applied Solutions")) ? "selected" : "" %>>SA Seed Applied Solutions</option>
						<option value="SA Supply Chain" <%=(division.equals("SA Supply Chain")) ? "selected" : "" %>>SA Supply Chain</option>
						<option value="SA TD Operations" <%=(division.equals("SA TD Operations")) ? "selected" : "" %>>SA TD Operations</option>
						<option value="SA Technology Development" <%=(division.equals("SA Technology Development")) ? "selected" : "" %>>SA Technology Development</option>
						<option value="Seminis" <%=(division.equals("Seminis")) ? "selected" : "" %>>Seminis</option>
  		              	<option value="Outro" <%=(division.equals("Outro")) ? "selected" : "" %>>Outro</option>
                  	</select>
				</div>
<%		completed = "";
		questionId = new Integer(17588);
       	String subdivision = "";
		if(hash.containsKey(questionId)) {
  	      	subdivision = ((UserAnswerData)hash.get(questionId)).getText();
  	        completed = "data-completed=\"true\"";
  	    }
%>				<div id="qw17588" data-type="4" <%=completed%> >
                    <label for="q17588">Área</label>
                  	<select id="q17588" name="q17588" onclick="selectSubDivision(this)">
  		              	<option value="">------</option>
<%		for(int i = 0; i < values.length; i++) {
			if(values[i][0].equals(division)) {
%> 		              	<option value='<%=values[i][1]%>' <%=(subdivision.equals(values[i][1])) ? "selected" : "" %>><%=Util.formatHTML(values[i][1])%></option>
<%			}
		}
		if(division.length()>0) {
%> 		              	<option value="Outro" <%=(subdivision.equals("Outro")) ? "selected" : "" %>>Outro</option>
<%		}
%>                  	</select>
				</div>
<%		completed = "";
		questionId = new Integer(17588);
       	String unit = "";
		if(hash.containsKey(questionId)) {
			unit = ((UserAnswerData)hash.get(questionId)).getText();
  	        completed = "data-completed=\"true\"";
  	    }
%>				<div id="qw17589" data-type="4" <%=completed%> >
                    <label for="q17589">Unidade</label>
                  	<select id="q17589" name="q17589">
  		              	<option value="">------</option>
<%		for(int i = 0; i < values.length; i++) {
			if(values[i][0].equals(division) && values[i][1].equals(division)) {
%> 		              	<option value='<%=values[i][2]%>' <%=(unit.equals(values[i][2])) ? "selected" : "" %>><%=Util.formatHTML(values[i][2])%></option>
<%			}
		}
		if(subdivision.length()>0) {
%> 		              	<option value="Outro" <%=(unit.equals("Outro")) ? "selected" : "" %>>Outro</option>
<%		}
%>                  </select>
				</div>
<%		completed = "";
		questionId = new Integer(17591);
       	String text = ".";
		if(hash.containsKey(questionId)) {
			text = ((UserAnswerData)hash.get(questionId)).getText();
  	        completed = "data-completed=\"true\"";
  	    }
%>				<div id="qw17591" data-type="11" <%=completed%> >
                    <label for="q17591">Caso tenha selecionado a opção "Outro", por favor, especifique sua Diretoria/Área/Unidade:</label>
	                <textarea id="q17591" name="q17591" cols="30" rows="5"><%=text%></textarea>
	          	</div>
			</fieldset>
		</form>
<%	}
%>
	</body>
</html>
