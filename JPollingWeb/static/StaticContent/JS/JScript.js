function createXmlHttpRequest() {
	var xmlHttp;
	if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		}
	}
	return xmlHttp;
}

var xmlHttp;
var url = "/JPollingWeb/JPollingServlet";
var divID;
var dispUrl="/JPollingWeb/JPollingServlet";

function clearUrl(){
	//self.location = dispUrl;
	//window.location.replace("hello");
	//location.hash = "hello";
	//parent.location = dispUrl;
}

function loadHome() {
	showLoading();
	var params = "action=loadHome";

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function updateContent() {
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		document.getElementById("work_area").innerHTML = xmlHttp.responseText;
		hideLoading();
	}
}

function loadAdminPage() {
	showLoading();
	var params = "action=loadAdminPage";
	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContentTotalPage;
	xmlHttp.send(params);
}

function updateContentTotalPage() {
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		document.getElementById("total_page").innerHTML = xmlHttp.responseText;
		hideLoading();
	}
}

function loadCreateQuestion() {
	showLoading();
	var params = "action=loadCreateQuestion";

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function loadUsers(start) {
	showLoading();
	var params = "action=loadUsers&start="+start+"&pickUesr="+false;

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function navigateUserList(start,pickUesr){
	showLoading();
	var params = "action=loadUserList&start="+start+"&pickUesr="+pickUesr;

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateUserList;
	xmlHttp.send(params);
}

function updateUserList() {
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		document.getElementById("user_list").innerHTML = xmlHttp.responseText;
		checkSelectedUesrs();
		hideLoading();
	}
}

function navigateQuestionList(start,isReport){
	showLoading();
	var params = "action=loadQuestionList&start="+start+"&isReport="+isReport;

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateQuestionList;
	xmlHttp.send(params);
}

function updateQuestionList() {
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		document.getElementById("questions_div").innerHTML = xmlHttp.responseText;
		hideLoading();
	}
}

function selecteOrUnselectUser(id) {
	if (document.getElementById(id) != null) {
		var users = document.getElementById("selected_users").value;
		if (document.getElementById(id).checked == true) {
			if (users == "") {
				users = id;
			} else {
				users += "!_" + id;
			}
		} else {
			var temp = [];
			temp = users.split("!_");
			users = "";
			for ( var i = 0; i < temp.length; i++) {
				if (temp[i] != id) {
					if (users == "") {
						users = temp[i];
					} else {
						users += "!_" + temp[i];
					}
				}
			}
		}
		document.getElementById("selected_users").value = users;
	}
}

function loadCreateUsers(type) {
	showLoading();
	var params = "action=loadCreateUsers&type=" + type;

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function loadEditUser(userId) {
	showLoading();
	var params = "action=loadEditUser&userId=" + userId;

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function saveEditUser(userId) {
	showLoading();
	var params = "action=saveEditUser&userId=" + userId;

	var user_name = document.getElementById("user_name").value;
	var password = document.getElementById("password").value;
	var mail_id = document.getElementById("mail_id").value;

	if (user_name == "" && password == "" && mail_id == "") {
		alert("Kindly Specify the Proper values");
		return;
	}
	if (!(user_name.length >= 6)) {
		alert("user Name Should be Greater Then 6 Characters");
		return;
	}
	if (!(password.length >= 6)) {
		alert("Password Should be Greter then 6 Characters");
		return;
	}
	if (!isValidEmail(mail_id)) {
		alert("Kindly specify Proper Email Address");
		return;
	}
	params = params + "&user_name=" + user_name + "&password=" + password
			+ "&mail_id=" + mail_id;
	
	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function createQuestionPickUser() {
	showLoading();
	var question = document.getElementById("question").value;
	var end_date = document.getElementById("end_date").value;
	var answers = "";
	for ( var i = 0; i < 4; i++) {
		answers += document.getElementById("answer_" + (i + 1)).value + "!_";
	}
	
	var rates = document.getElementsByName('answerSet');
	var selected;
	for(var i = 0; i < rates.length; i++){
	    if(rates[i].checked){
	    	selected = rates[i].value;
	    }
	}
	
	selected = document.getElementById(selected).value;
	var params = "action=createQuestionPickUser&question=" + question
			+ "&answers=" + answers + "&end_date=" + end_date+"&pickUesr="+true + "&selected="+selected;
	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function assignQuestion(question_id) {
	showLoading();
	var user_ids = [];

	var content
	var tab = document.getElementById("work_area"); // table with id tbl1
	var elems = tab.getElementsByTagName("input");
	var len = elems.length;
	/*var j = 0;
	for ( var i = 0; i < len; i++) {
		if (elems[i].type == "checkbox" && elems[i].checked == true) {
			user_ids[j] = elems[i].id;
			j++;
		}
	}*/
	user_ids = document.getElementById("selected_users").value;

	var params = "action=assignQuestion&question_id=" + question_id
			+ "&user_ids=" + user_ids;
	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function checkSelectedUesrs() {
	var user_ids = [];
	if (document.getElementById("selected_users") != null) {
		var ids = document.getElementById("selected_users").value;
		user_ids = ids.split("!_");
		for ( var i = 0; i < user_ids.length; i++) {
			if (document.getElementById(user_ids[i]) != null) {
				document.getElementById(user_ids[i]).checked = true;
			}
		}
	}
}

function loadQuestion(start,end) {
	showLoading();
	var params = "action=loadQuestion&start="+start+"&end="+end;

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

var g_ques_id;
function showAnswer(question_id, tot) {
	showLoading();
	var tab = document.getElementById("questions_list"); // table with id tbl1
	var elems = tab.getElementsByTagName("div");
	var len = elems.length;
	for ( var i = 0; i < len; i++) {
		if(document.getElementById(elems[i].id)!=null){
			document.getElementById(elems[i].id).style.display = "none";
		}
	}

	var params = "action=showAnswer&question_id=" + question_id;
	g_ques_id = question_id;
	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContentans;
	xmlHttp.send(params);
}

function updateContentans() {

	var question_ids = "ans_div_" + g_ques_id;
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		document.getElementById(question_ids).style.display = "block";
		document.getElementById(question_ids).innerHTML = xmlHttp.responseText;
		hideLoading();
	}
}

function loadClientPage() {
	showLoading();
	var params = "action=loadClientPage";

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContentTotalPage;
	xmlHttp.send(params);
}

function validateUser() {
	showLoading();
	var params = "action=validateUser";
	var user_name = document.getElementById("user_name").value;
	var password = document.getElementById("password").value;
	if (checkValue(user_name) && checkValue(password)) {
		params = params + "&user_name=" + user_name + "&password=" + password;
		xmlHttp = createXmlHttpRequest();
		xmlHttp.open("POST", url, true);
		xmlHttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlHttp.setRequestHeader("Content-length", params.length);
		xmlHttp.setRequestHeader("Connection", "close");
		xmlHttp.onreadystatechange = updateReloadPage;
		xmlHttp.send(params);
	} else {
		alert("Authendication Fail");
		document.getElementById("user_name").focus();
		document.getElementById("user_name").value = "";
		document.getElementById("password").value = "";
	}
}

function updateContentlogIn() {
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		var redirectURL = "localhost:8080/JPollingWeb/JPollingServlet";
		/*var result = xmlHttp.responseText;
		if (result.indexOf("admin") != -1) {
			window.location = url ;//+ "?&action=loadAdminPage";
		} else if (result.indexOf("user") != -1) {
			window.location = url + "?&action=loadClientPage";
		} else if(result.indexOf("unAuthorized") != -1){
			alert("Authendication Fail");
			document.getElementById("user_name").focus();
			document.getElementById("user_name").value="";
			document.getElementById("password").value="";
		}*/
		hideLoading();
	}
}

function checkValue(id){
	var isValid =true;
	if(document.getElementById(id)!=null){
		var value = document.getElementById(id).value;
		if(value=="" || value==" "){
			isValid =false;
		}
		if(value==null || value=="null"){
			isValid =false;
		}
		if(value.length<1){
			isValid =false;
		}
	}
	return isValid;
}
function createUsers(type) {
	showLoading();
	var params = "action=createUsers&type=" + type;

	if (type == "single") {
		var user_name = document.getElementById("user_name").value;
		var password = document.getElementById("password").value;
		var mail_id = document.getElementById("mail_id").value;

		if (user_name == "" && password == "" && mail_id == "") {
			alert("Kindly Specify the Proper values");
			return;
		}
		if (!(user_name.length >= 6)) {
			alert("user Name Should be Greater Then 6 Characters");
			return;
		}
		if (!(password.length >= 6)) {
			alert("Password Should be Greter then 6 Characters");
			return;
		}
		if (!isValidEmail(mail_id)) {
			alert("Kindly specify Proper Email Address");
			return;
		}
		params = params + "&user_name=" + user_name + "&password=" + password
				+ "&mail_id=" + mail_id;

	}
	if (type == "multiple") {
		var common_user_name = document.getElementById("common_user_name").value;
		var common_password = document.getElementById("common_password").value;
		var common_mail_id = document.getElementById("common_mail_id").value;
		var start_value = document.getElementById("start_value").value;
		var end_value = document.getElementById("end_value").value;

		if (common_user_name == "" && common_password == ""
				&& common_mail_id == "" && start_value == "" && end_value == "") {
			alert("Kindly Specify the Proper values");
			return;
		}
		if (!(common_user_name.length >= 4)) {
			alert("common user Name Should be Greater Then 4 Characters");
			return;
		}
		if (!(common_password.length >= 6)) {
			alert("Common Password Should be Greter then 6 Characters");
			return;
		}
		if (!isValidEmail(common_mail_id)) {
			alert("Kindly specify Proper Email Address");
			return;
		}
		if (start_value > end_value) {
			alert("End Range Should be greater then Start Range");
			return;
		}
		params = params + "&common_user_name=" + common_user_name
				+ "&common_password=" + common_password;
		params = params + "&common_mail_id=" + common_mail_id + "&start_value="
				+ start_value + "&end_value=" + end_value;
	}
	if (type == "mailid") {
		var common_user_name = document.getElementById("common_user_name").value;
		var common_password = document.getElementById("common_password").value;
		var mail_id = document.getElementById("common_mail_id").value;
		if (common_user_name == "" && common_password == "" && mail_id == "") {
			alert("Kindly Specify the Proper values");
			return;
		}
		if (!(common_user_name.length >= 4)) {
			alert("common user Name Should be Greater Then 4 Characters");
			return;
		}
		if (!(common_password.length >= 6)) {
			alert("Common Password Should be Greter then 6 Characters");
			return;
		}
		if (!isValidEmail(mail_id)) {
			alert("Kindly specify Proper Email Address");
			return;
		}

		params = params + "&common_user_name=" + common_user_name
				+ "&common_password=" + common_password;
		params = params + "&mail_id=" + mail_id;
	}
	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContent;
	xmlHttp.send(params);
}

function isValidEmail(email) {
	return ((email.indexOf(".") > 2 && email.indexOf("@") > 0));
}

function postAnswer(q_id, ans_id) {
	showLoading();
	if (confirm("Are u sure u wannt to post this answer? ")) {

		var params = "action=postAnswer&ans_id=" + ans_id + "&q_id=" + q_id+"&start=0";

		xmlHttp = createXmlHttpRequest();
		xmlHttp.open("POST", url, true);
		xmlHttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlHttp.setRequestHeader("Content-length", params.length);
		xmlHttp.setRequestHeader("Connection", "close");
		xmlHttp.onreadystatechange = updateQuestionList;
		xmlHttp.send(params);
	} else {
		return false;
	}
}
/*
 * function loadReport(q_id) {
 * 
 * var params = "action=loadReport&q_id="+q_id;
 * 
 * xmlHttp = createXmlHttpRequest(); xmlHttp.open("GET", url, true);
 * xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
 * xmlHttp.setRequestHeader("Content-length", params.length);
 * xmlHttp.setRequestHeader("Connection", "close"); xmlHttp.onreadystatechange =
 * updateContentForReport; xmlHttp.send(params); }
 * 
 * function updateContentForReport(){ if (xmlHttp.readyState == 4 &&
 * xmlHttp.status == 200) { document.getElementById("work_area").innerHTML =
 * xmlHttp.responseText; loadChart(); //setTimeout(function()
 * {loadChart();},1250); } }
 */

/*
 * var params = { bgcolor:"#FFFFFF" };
 * 
 * var dta = document.getElementById("chart_data").value; var settings =
 * document.getElementById("chart_settings").value;
 * 
 * var flashVars = { path: "../../amcharts/flash/",
 * 
 *  // settings_file: "../sampleData/column_settings.xml", // data_file:
 * "../sampleData/column_data.xml"
 * 
 * chart_data:dta , chart_settings:settings };
 * 
 * function loadChart() {
 *  // change == to != to test flash version if(AmCharts.recommended() == "js") {
 * var amFallback = new AmCharts.AmFallback(); // amFallback.settingsFile =
 * flashVars.settings_file; // doesn't support multiple settings files or
 * additional_chart_settins as flash does // amFallback.dataFile =
 * flashVars.data_file; amFallback.chartSettings = flashVars.chart_settings;
 * amFallback.chartData = flashVars.chart_data; amFallback.type = "pie";
 * amFallback.write("chartdiv"); } else {
 * swfobject.embedSWF("../../amcharts/flash/ampie.swf", "chartdiv", "600",
 * "400", "8.0.0", "../../amcharts/flash/expressInstall.swf", flashVars,
 * params); } }
 */

function encode(val) {
	return encodeURIComponent(val);
}

function decode() {
	var obj = document.getElementById('dencoder');
	var encoded = obj.value;
	obj.value = decodeURIComponent(encoded.replace(/\+/g, " "));
}

function logOut() {
	showLoading();
	var params = "action=logOut";

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateContentForlogOut;
	xmlHttp.send(params);
}

function updateContentForlogOut() {
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		window.location = "http://localhost:8080/JPollingWeb/JPollingServlet";
	}
}

function hideLoading(){
	setTimeout(function() {document.getElementById("loading").style.display='none';},500);
}

function hideLoadingWithDelay(){
	setTimeout(function() {document.getElementById("loading").style.display='none';},2000);
}

function showLoading(){
	document.getElementById("loading").style.display='block';
}

function loadReport(qId,start) {
	showLoading();
	var params = "action=setRedirection&landingPage=loadReport&qId=" + qId+"&start="+start;

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateReloadPage;
	xmlHttp.send(params);
}

function updateReloadPage(){
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		var result = xmlHttp.responseText;
		if (result.indexOf("done") != -1) {
			//setResponsePage(MyPage.this);
			window.location = dispUrl;
		} 
		//window.location = "//localhost/JPollingWeb/JPollingServlet";
	}
}

function deleteQuestion(questionId) {
	showLoading();
	var params = "action=deleteQuestion&questionId="+questionId;

	xmlHttp = createXmlHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.setRequestHeader("Content-length", params.length);
	xmlHttp.setRequestHeader("Connection", "close");
	xmlHttp.onreadystatechange = updateQuestion;
	xmlHttp.send(params);
}

function updateQuestion() {
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		hideLoading();
		loadQuestion(0);
	}
}

function uploadImage(){
	/*var img = document.getElementById("imgLoader");

	var reader = new FileReader();
	var imgData = reader.readAsDataURL(img.files[0]);
                     
    var ajax = new XMLHttpRequest();
	ajax.open("POST",'http://localhost:8080/JPollingWeb/UploadServlet',true);
	    ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	ajax.send(img);
	
	ajax.setRequestHeader("Content-Type", "application/upload");
	ajax.send(imgData);*/
	

    var sampleFile = document.getElementById("imgLoader").files[0];

    var formdata = new FormData();

    //formdata.append("sampleText", sampleText);

    formdata.append("sampleFile", sampleFile);

    var xhr = new XMLHttpRequest();       

    xhr.open("POST","http://localhost:8080/JPollingWeb/UploadServlet", true);

    xhr.send(formdata);

    xhr.onload = function(e) {

        if (this.status == 200) {
           alert(this.responseText);

        }
    }; 

}