/**
* Project development
**/
package com.project.web.layout;


/**
 * @author P.Ayyasamy
 * @since 1.0
 */
public class JPollingLayout {
	
	public static final String URL ="http://localhost/JPollingWeb/JPollingServlet";

	/**
	 * 
	 * @return
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String getLoginPage() {
		
		StringBuffer xml = new StringBuffer();
		
		xml.append("<div id='login_container'>");
		xml.append("<div id='login_container_error' style='color:red; display:none'><strong> User Name and Password does not match.</strong></div>");
		xml.append("<div id='login_header'>");
		xml.append("<div id='login_header_left'>");
		xml.append("<h1>JLearning System</h1>");
		xml.append("<h2>One Million</h2>");
		xml.append("</div>");
		xml.append("<div id='login_header_right'>");
		xml.append("<p class='welcome'>Welcome, User. Please login.</p>");
		xml.append("<form id='JPollingForm' method='post' action=''>");
		xml.append("<p><label>Username");
		xml.append("<input type='text' class='fields' id='user_name' name='user_name' />");
		xml.append("</label>");
		xml.append("<label>Password");
		xml.append("<input type='password' id='password' class='fields' name='password' />");
		xml.append("<input type='button' class='submit_button' value='login' onClick='validateUser()' />");
		xml.append("</label></p>");
		xml.append("</form>");
		xml.append("</div>");
		xml.append("</div>");
		xml.append("</div>");
		xml.append("<script type='text/javascript'>");
		xml.append("document.getElementById('user_name').focus();");
		xml.append("</script>");
		
		return xml.toString();
	}



	public String buildHTML(String bodyContent){
		StringBuffer xml = new StringBuffer();

		xml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
		xml.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
		xml.append(getHeader());
		xml.append("<body>");
		xml.append("<div id='loading'>");
		xml.append("<div class='headings' >");
		xml.append("<table width='139' border='0'><tr>");
		xml.append("<td width='34'><img src='/JPollingWeb/StaticContent/Images/loading.gif' width='25' height='25' alt='Loading' /></td>");
		xml.append("<td width='89'> loading</td>");
		xml.append("</tr></table></div>");
		xml.append("</div>");
		xml.append("<div id='total_page' align='center'>");
		xml.append(bodyContent);
		xml.append("</div>");
		xml.append("<script type='text/javascript'>");
		xml.append("hideLoadingWithDelay();");
		xml.append("clearUrl();");
		xml.append("</script>");
		xml.append("</body>");
		xml.append("</html>");
		return xml.toString();
	}
	
	private String getHeader() {
		StringBuffer xml = new StringBuffer();
		xml.append("<head>");
		xml.append("<title>JLearning System</title>");
		// css
		xml.append("<link href='/JPollingWeb/StaticContent/CSS/JStyle.css' rel='stylesheet' type='text/css'/>");	
		// Java Scripts
		xml.append("<script type='text/javascript' src='/JPollingWeb/StaticContent/JS/prototype.js'></script>");
		xml.append("<script type='text/javascript' src='/JPollingWeb/StaticContent/JS/jsProgressBarHandler.js'></script>");
		xml.append("<script type='text/javascript' src='/JPollingWeb/StaticContent/JS/JScript.js'></script>");
		xml.append("<script type='text/javascript' src='/JPollingWeb/StaticContent/JS/jquery.js'></script>");
		xml.append("<script type='text/javascript' src='/JPollingWeb/StaticContent/JS/amcharts/flash/swfobject.js'></script>");
		xml.append("<script type='text/javascript' src='/JPollingWeb/StaticContent/JS/amcharts/amcharts.js'></script>");
		xml.append("<script type='text/javascript' src='/JPollingWeb/StaticContent/JS/amcharts/amfallback.js'></script>");
		xml.append("<script type='text/javascript' src='/JPollingWeb/StaticContent/JS/amcharts/raphael.js'></script>");
		xml.append("</head>");
		return xml.toString();
	}
	
	public String buildAdminLayout(String WorkAreaContent,String userName){
		StringBuffer xml = new StringBuffer();
		
		xml.append("<table  width='80%' align='center' cellspacing='10' id='main_table'>");
		xml.append("<tr id='main_row1'>");
		xml.append("<td height='93' colspan='2' width='100%'><table align='center' width='100%' id='header_table'>");
		xml.append("<tr>");
		xml.append("<td height='49' align='right'><div id='header_content'><h1>JLearning System</h1></div></td>");
		xml.append("</tr>");
		xml.append("</table></td>");
		xml.append("</tr>");
		xml.append("<tr id='main_row2'>");
		xml.append("<td width='20%'  valign='top'>");
		xml.append("<div class='border_div'>");
		xml.append("");
		xml.append("<table width='100%' class='split_bg'>");
		xml.append("<tr height='10%'>");
		xml.append("<td><div id='user_status' class='headings'>");
		xml.append("<table width='100%' >");
		xml.append("<tr>");
		xml.append("<td width='18%' height='25'>User </td>");
		xml.append("<td width='68%'><h4>"+userName+"</h4></td>");
		xml.append("<td width='14%'><a href='#' onclick='logOut();' ><img src='/JPollingWeb/StaticContent/Images/logout.png' width='25' height='25' title='Log out' alt='Log Out' /></a></td>");
		xml.append("</tr>");
		xml.append("</table>");
		xml.append("</div></td>");
		xml.append("</tr>");
		xml.append("<tr>");
		xml.append("<td width='122'><div id='navcontainer'>");
		xml.append("<div align='left'>");
		xml.append("<ul id='navlist' >");
		xml.append("<li class='active'>");
		xml.append("<a href='#' onclick='showLoading();loadHome()'>Home</a></li>");
		xml.append("<li><a href='#' onclick='loadUsers(0)'>Users</a></li>");
		xml.append("<li><a href='#' onclick='loadQuestion(0)'>Learning</a></li>");
		xml.append("<li><a href='' onclick='loadReport(0,0)'>Reports</a></li>");
		xml.append("</ul>");
		xml.append("</div>");
		xml.append("</div></td>");
		xml.append("</tr>");
		xml.append("<tr height='50%'>");
		xml.append("<td></td>");
		xml.append("</tr>");
		xml.append("</table>");
		xml.append("</div></td>");
		xml.append("<td  width='80%' valign='top'>");
		xml.append("<div class='border_div'>");
		xml.append("<div class='border_div_bottom'>");
		xml.append("<div id='work_area'>");
		
		xml.append(WorkAreaContent);
		
		xml.append("</div>");
		xml.append("</div></div></td>");
		xml.append("</tr>");
		xml.append("</table>");
		return xml.toString();
	}
	
public String getUserRegistrationPage() {
		
	StringBuffer xml = new StringBuffer();
	xml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
	xml.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
	xml.append(getHeader());
	xml.append("<body>");
	/*xml.append("<div id='loading'>");
	xml.append("<div class='headings' >");
	xml.append("<table width='139' border='0'><tr>");
	//xml.append("<td width='34'><img src='/JPollingWeb/StaticContent/Images/loadingBar.gif' width='25' height='25' alt='Loading' /></td>");
	//xml.append("<td width='89'> loading</td>");
	xml.append("</tr></table></div>");
	xml.append("</div>");*/
	xml.append("<div id='total_page' align='center'>");
	
	xml.append("<table  width='80%' align='center' cellspacing='10' id='main_table'>");
	xml.append("<tr id='main_row1'>");
	xml.append("<td height='93' colspan='2' width='100%'><table align='center' width='100%' id='header_table'>");
	xml.append("<tr>");
	xml.append("<td height='49' align='right'><div id='header_content'><h1>JPolling - Online Voting System</h1></div></td>");
	xml.append("</tr>");
	xml.append("</table></td>");
	xml.append("</tr>");
	xml.append("<tr id='main_row2'>");
	xml.append("<td  width='4%' valign='top'></td>");
	xml.append("<td  width='96%' valign='top'>");
	xml.append("<div class='border_div'>");
	xml.append("<div class='border_div_bottom'>");
	xml.append("<div id='work_area'>");
	//xml.append(loadClientHome(request));
	xml.append("<table height = '300px' width= '100%' style ='font-size:16px'>");
	xml.append("<tr style='color:red'><td><strong>*All Fields are Mandatory</strong></td></tr>");
	
	//xml.append("<form id='JPollingForm' method='post' action=''>");
	//xml.append("<p><label>Username</label></p>");
	xml.append("<tr><td>Username : </td>");
	xml.append("<td><input type='text' class='fields' id='user_name' name='user_name' /></td></tr>");
	//xml.append("</label></p>");
	//xml.append("<p><label>Password</label></p>");
	xml.append("<td>Password : </td>");
	xml.append("<td><input type='password' id='password' class='fields' name='password' /></td></tr>");
	xml.append("<td>Voter ID : </td>");
	xml.append("<td><input type='text' id='voter_id' class='fields' name='voter_id' /></td></tr>");
	xml.append("<td>Address1 : </td>");
	xml.append("<td><input type='textarea' id='address1' class='fields' name='address1' placeholder = 'Address Line 1'/></td></tr>");
	xml.append("<td>Address2 : </td>");
	xml.append("<td><input type='textarea' id='address2' class='fields' name='address2' placeholder = 'Address Line 2'/></td></tr>");
	xml.append("<tr><td>City : </td>");
	xml.append("<td><input type='text' class='fields' id='city' name='city' /></td></tr>");
	xml.append("<tr><td>State : </td>");
	xml.append("<td><input type='text' class='fields' id='state' name='state' /></td></tr>");
	
	xml.append("<tr><td><input type='button' class='submit_button' value='login' onClick='registerUser()' /></td></tr>");
	xml.append("<tr></tr>");
	xml.append("<tr></tr>");
	xml.append("<tr></tr>");
	xml.append("<tr></tr>");
	//xml.append("</label></p>");
	//xml.append("</form>");
	xml.append("</table>");
	xml.append("</div>");
	xml.append("</div></div></td>");
	xml.append("</tr>");
	xml.append("</table>");
	
	
	//xml.append(bodyContent);
	xml.append("</div>");
	xml.append("<script type='text/javascript'>");
	xml.append("hideLoadingWithDelay();");
	xml.append("clearUrl();");
	xml.append("</script>");
	xml.append("</body>");
	xml.append("</html>");
	return xml.toString();
	}
}
