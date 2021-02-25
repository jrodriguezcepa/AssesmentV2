function deleteIFConfirm(form,msg){

	var elements=form.elements;
	var length=elements.length;
	var i;
	var valueCheckboxParamList="";
	var separator="<";
		
	for(i=0;i<length;i++){
		var element=elements[i];
		if(element.type.toLowerCase()=="checkbox"){
			if(element.checked){
				if(valueCheckboxParamList==""){
					valueCheckboxParamList=element.value;
				}else{
					valueCheckboxParamList=element.value+"<"+valueCheckboxParamList;
				}	
			}	
		} 
	}
	form.checkboxParamList.value=valueCheckboxParamList;
	if(valueCheckboxParamList.length>0){
		if(confirm(msg)){
			form.submit();
		}
	}
}
function doSubmit() {
	document.forms[0].submit();
}
function cambio(parent,child,message){
	var parentCombo = document.forms[2].elements[parent];
	var childCombo = document.forms[2].elements[child];
	if(parentCombo.options[parentCombo.selectedIndex].value != "") {
		var divi = parentCombo.options[parentCombo.selectedIndex].text+parentCombo.options[parentCombo.selectedIndex].value;
		var list = document.forms[2].elements[divi];
		childCombo.options.length=list.options.length;
		for(i=0; i < list.length; i++) {
			childCombo.options[i].value = list.options[i].value;
			childCombo.options[i].text = list.options[i].text;
		}
	}else {
		childCombo.options.length=1;
		childCombo.options[0].value = "";
		childCombo.options[0].text = message;
	}
}
function changeCountry(formName,parent,child,selectoption){
	var parentCombo = document.forms[formName].elements[parent];
	var childCombo = document.forms[formName].elements[child];
	if(parentCombo.options[parentCombo.selectedIndex].value != "") {
		var divi = "geo"+parentCombo.options[parentCombo.selectedIndex].value;
		var list = document.forms[formName].elements[divi];
		var vector = new Array();
		for(i=0; i < list.length; i++) {
			vector[i] = list.options[i].text+"|"+list.options[i].value;
		}
		vector.sort();
		childCombo.options.length=list.options.length+1;
		childCombo.options[0].value = "";
		childCombo.options[0].text = selectoption;
		for(i=0; i < vector.length; i++) {
			values = vector[i].split("|");
			childCombo.options[i+1].value = values[1];
			childCombo.options[i+1].text = values[0];
		}
	}else {
		var list = document.forms[formName].elements['allcountry'];
		childCombo.options.length=list.options.length;
		for(i=0; i < list.length; i++) {
			childCombo.options[i].value = list.options[i].value;
			childCombo.options[i].text = list.options[i].text;
		}
	}
	childCombo.options.selectedIndex = 0;
}
function changeGeo1(formName,selectoption){
	var parentCombo = document.forms[formName].elements['country1'];
	var childCombo = document.forms[formName].elements['country2'];
	var child = childCombo.options[childCombo.selectedIndex].value;
	if(child == "") {
		parentCombo.selectedIndex = 0;
		var list = document.forms[formName].elements['allcountry'];
		childCombo.options.length=list.options.length;
		for(i=0; i < list.length; i++) {
			childCombo.options[i].value = list.options[i].value;
			childCombo.options[i].text = list.options[i].text;
		}
		childCombo.selectedIndex = 0;
	}else {
		if(parentCombo.options[parentCombo.selectedIndex].value == "") {
			for(i=0; i < parentCombo.length; i++) {
				var found = 0;
				if(parentCombo.options[i].value != "") {
					var divi = "geo"+parentCombo.options[i].value;
					var list = document.forms[formName].elements[divi];
					childCombo.options.length=list.options.length+1;
					childCombo.options[0].value = "";
					childCombo.options[0].text = selectoption;
					for(j=0; j < list.length; j++) {
						childCombo.options[j+1].value = list.options[j].value;
						childCombo.options[j+1].text = list.options[j].text;
						if(list.options[j].value == child) {
							parentCombo.selectedIndex = i;
							childCombo.selectedIndex = j+1;
							found = 1;
						}
					}
					if(found == 1) {
						return;
					}
				}
			}
		}	
	}
}
function changeOrg(formName,selectoption){
	var parentCombo = document.forms[formName].elements['country2'];

	if(parentCombo.options[parentCombo.selectedIndex].value == "") {
		putAll(formName,'divOrg11','allorg0');
		reset(formName,'divOrg12',selectoption);
		reset(formName,'divOrg13',selectoption);
		putAll(formName,'divOrg21','allorg1');
		reset(formName,'divOrg22',selectoption);
		reset(formName,'divOrg23',selectoption);
		putAll(formName,'divOrg31','allorg2');
		reset(formName,'divOrg32',selectoption);
		reset(formName,'divOrg33',selectoption);
	}else {
		var divi = 'geo_org0'+parentCombo.options[parentCombo.selectedIndex].value;
		loadOrg(formName,'divOrg11',divi,'divOrg12','org0_','divOrg13','org_20_',selectoption);
		divi = 'geo_org1'+parentCombo.options[parentCombo.selectedIndex].value;
		loadOrg(formName,'divOrg21',divi,'divOrg22','org1_','divOrg23','org_21_',selectoption);
		divi = 'geo_org2'+parentCombo.options[parentCombo.selectedIndex].value;
		loadOrg(formName,'divOrg31',divi,'divOrg32','org2_','divOrg33','org_22_',selectoption);
	}
}
function changeDivOrg(form,parent,child,level,message){
	var parentCombo = document.forms[form].elements[parent];
	var childCombo = document.forms[form].elements[child];
	if(parentCombo.options[parentCombo.selectedIndex].value != "") {
		var divi = level+parentCombo.options[parentCombo.selectedIndex].value;
		var list = document.forms[form].elements[divi];
		var vector = new Array();
		for(i=0; i < list.length; i++) {
			vector[i] = list.options[i].text+"|"+list.options[i].value;
		}
		vector.sort();
		childCombo.options.length=list.options.length+1;
		childCombo.options[0].value = "";
		childCombo.options[0].text = message;
		for(i=0; i < vector.length; i++) {
			values = vector[i].split("|");
			childCombo.options[i+1].value = values[1];
			childCombo.options[i+1].text = values[0];
		}
		childCombo.selectedIndex = 0;
	}else {
		childCombo.options.length=1;
		childCombo.options[0].value = "";
		childCombo.options[0].text = message;
	}
}

function putAll(formName,divorg,all) {
	var childCombo = document.forms[formName].elements[divorg];
	var list = document.forms[formName].elements[all];
	childCombo.options.length=list.options.length;
	for(i=0; i < list.length; i++) {
		childCombo.options[i].value = list.options[i].value;
		childCombo.options[i].text = list.options[i].text;
	}
	childCombo.options.selectedIndex = 0;
}

function reset(formName,divorg,selectoption) {
	if(document.forms[formName].elements[divorg] != null) {
		combo = document.forms[formName].elements[divorg];
		combo.options.length=1;
		combo.options[0].value = "";
		combo.options[0].text = selectoption;
	}
}

function loadOrg(formName,divOrg1,divi,divOrg2,org,divOrg3,org2,selectoption) {
	var childCombo = document.forms[formName].elements[divOrg1];
	var list = document.forms[formName].elements[divi];
	var start = 0;
	if(list.options.length > 1) {
		childCombo.options.length=list.options.length+1;
		childCombo.options[0].value = "";
		childCombo.options[0].text = selectoption;
		start = 1;
	}else {
		childCombo.options.length=list.options.length;
	}
	for(i=0; i < list.length; i++) {
		childCombo.options[start].value = list.options[i].value;
		childCombo.options[start].text = list.options[i].text;
		start = start+1;
		if(childCombo.options.length == 1) {
			var childCombo2 = document.forms[formName].elements[divOrg2];
			var divi2 = org+list.options[i].value;
			var list2 = document.forms[formName].elements[divi2];
			if(list2 != null) {
				var start2 = 0;
				if(list2.options.length > 1) {
					childCombo2.options.length=list2.options.length+1;
					childCombo2.options[0].value = "";
					childCombo2.options[0].text = selectoption;
					start2 = 1;
				}else {
					childCombo2.options.length=list2.options.length;
				}
				for(i=0; i < list2.length; i++) {
					childCombo2.options[start2].value = list2.options[i].value;
					childCombo2.options[start2].text = list2.options[i].text;
					if(start2 == 0) {
						var childCombo3 = document.forms[formName].elements[divOrg3];
						var divi3 = org2+list2.options[i].value;
						var list3 = document.forms[formName].elements[divi3];
						if(list3 != null) {
							var start3 = 0;
							if(list3.options.length > 1) {
								childCombo3.options.length=list3.options.length+1;
								childCombo3.options[0].value = "";
								childCombo3.options[0].text = selectoption;
								start3 = 1;
							}else {
								childCombo3.options.length=list3.options.length;
							}
							for(i=0; i < list3.length; i++) {
								childCombo3.options[start3].value = list3.options[i].value;
								childCombo3.options[start3].text = list3.options[i].text;
								start3 = start3+1;
							}
						}
					}else {
						start2 = start2+1;
					}
				}
			}
		}		
	}
}