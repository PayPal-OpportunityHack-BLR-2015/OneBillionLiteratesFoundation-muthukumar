/**
 * Project development
 **/
package com.project.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.project.jpolling.dao.answer.AnswersDAO;
import com.project.jpolling.dao.answer.QADAO;
import com.project.jpolling.dao.mapping.MappingDAO;
import com.project.jpolling.dao.questions.QuestionsDAO;
import com.project.jpolling.dao.users.UsersDAO;
import com.project.jpolling.to.answer.AnswerTO;
import com.project.jpolling.to.answer.QATO;
import com.project.jpolling.to.mapping.MappingTO;
import com.project.jpolling.to.question.QuestionTO;
import com.project.jpolling.to.user.UserTO;
import com.project.mailing.SendMail;
import com.project.utils.DateUtils;
import com.project.utils.JPollingUtil;
import com.project.web.layout.JPollingLayout;

/**
 * @author P.Ayyasamy
 * @since 1.0
 */
public class JPollingHelper {

	private UserTO userTOObject;

	/**
	 * @param userTO
	 */
	public JPollingHelper(UserTO userTO) {
		this.userTOObject = userTO;
	}

	/**
	 * @param userTOObject
	 */
	public JPollingHelper() {
		userTOObject = new UserTO();
	}

	public String createUsers(HttpServletRequest request) {
		String type = "", content = null;
		if (request.getParameter("type") != null) {
			type = request.getParameter("type");
			if (type.equals("single")) {
				content = singleUser(request);
			} else if (type.equals("multiple")) {
				content = multipleUser(request);
			} else if (type.equals("mailid")) {
				content = mailUser(request);
			} else {

			}
		}
		return content;
	}

	public String singleUser(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		UsersDAO usersDAO = new UsersDAO();
		UserTO userTO = new UserTO();
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("fileName"));
		try {
			if (request.getParameter("user_name") != null
					&& request.getParameter("password") != null
					&& request.getParameter("mail_id") != null ) {
				String userId = request.getParameter("user_name");
				String password = request.getParameter("password");
				String mailId = request.getParameter("mail_id");
				userTO.setUserName(userId);
				userTO.setPassword(password);
				userTO.setMailId(mailId);
				userTO.setUserType("user");
				usersDAO.saveSingleUser(userTO);
				System.out.println("User " + userId + " Saved successfully");
				System.out.println("Rediecting to Load User Page");
				xml = xml.append(loadUsers(request));
			} else {
				xml = xml.append(loadUsers(request));
			}
		} catch (Exception e) {
			System.out.println("Exxception in SingleUser method");
		}
		return xml.toString();
	}
	
	public String saveEditUser(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		UsersDAO usersDAO = new UsersDAO();
		UserTO userTO = new UserTO();
		try {
			if (request.getParameter("user_name") != null
					&& request.getParameter("password") != null
					&& request.getParameter("mail_id") != null) {
				String userName = request.getParameter("user_name");
				String password = request.getParameter("password");
				String mailId = request.getParameter("mail_id");
				long userId = Long.parseLong(request.getParameter("userId"));
				userTO.setUserId(userId);
				userTO.setUserName(userName);
				userTO.setPassword(password);
				userTO.setMailId(mailId);
				userTO.setUserType("user");
				usersDAO.saveSingleUser(userTO);
				System.out.println("User " + userId + " Saved successfully");
				System.out.println("Rediecting to Load User Page");
				xml = xml.append(loadUsers(request));
			} else {
				xml = xml.append(loadUsers(request));
			}
		} catch (Exception e) {
			System.out.println("Exxception in SingleUser method");
		}
		return xml.toString();
	}

	public String multipleUser(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		UsersDAO usersDAO = new UsersDAO();
		try {
			if (request.getParameter("common_user_name") != null
					&& request.getParameter("common_password") != null
					&& request.getParameter("common_mail_id") != null
					&& request.getParameter("start_value") != null
					&& request.getParameter("end_value") != null) {
				String userId = request.getParameter("common_user_name");
				String password = request.getParameter("common_password");
				String mailId = request.getParameter("common_mail_id");
				int startRange = 0, endRange = 0;
				startRange = Integer.parseInt(request
						.getParameter("start_value"));
				endRange = Integer.parseInt(request.getParameter("end_value"));
				mailId = request.getParameter("common_mail_id");
				int count = usersDAO.saveMultipleUser(userId, startRange,
						endRange, password, mailId);
				System.out.println(count
						+ " Number of Users Saved successfully");
				System.out.println("Rediecting to Load User Page");
				xml = xml.append(loadUsers(request));
			} else {
				xml = xml.append(loadUsers(request));
			}
		} catch (Exception e) {
			System.out.println("Exxception in MultipleUser method");
		}
		return xml.toString();
	}

	public String mailUser(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		StringTokenizer st;
		List<String> mailIds = new ArrayList<String>();
		UsersDAO usersDAO = new UsersDAO();
		try {
			if (request.getParameter("common_user_name") != null
					&& request.getParameter("common_password") != null
					&& request.getParameter("mail_id") != null) {

				String userId = request.getParameter("common_user_name");
				String password = request.getParameter("common_password");
				String mailId = request.getParameter("mail_id");
				st = new StringTokenizer(mailId, ",");
				while (st.hasMoreTokens()) {
					mailIds.add(st.nextToken());
				}
				usersDAO.saveUserByMailId(userId, password, mailIds);
				System.out.println("User " + userId + " Saved successfully");
				System.out.println("Rediecting to Load User Page");
				xml = xml.append(loadUsers(request));
			} else {
				xml = xml.append(loadUsers(request));
			}
		} catch (Exception e) {
			System.out.println("Exxception in MailUser method");
		}
		return xml.toString();
	}

	public String validateUser(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(true);
		StringBuffer xml = new StringBuffer();
		String userName = "", password = "";
		if (request.getParameter("user_name") != null
				&& request.getParameter("password") != null) {
			userName = request.getParameter("user_name");
			password = request.getParameter("password");
			UsersDAO userDAO = new UsersDAO();
			UserTO userTO = new UserTO();
			userTO = userDAO.getUserByUserNameAndPassword(userName, password);
			session.setAttribute("userName", userName);
			session.setAttribute("userTO", userTO);
			// xml.append("<div id='result'>");
			if (userTO != null && userTO.getUserType().equals("admin")) {
				xml.append("done");
				session.setAttribute("landingPage", "loadAdminPage");
			} else if (userTO != null && userTO.getUserType().equals("user")) {
				xml.append("done");
				session.setAttribute("landingPage", "loadClientPage");
			} else {
				xml.append("unAuthorized");
			}
			// xml.append("</div>");
		}
		return xml.toString();
	}

	public void logOut(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(true);
			session.removeAttribute("userTO");
			session.removeAttribute("userName");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String loadAdminPage(HttpServletRequest request) throws Exception {
		StringBuffer xml = new StringBuffer();
		String userName = "";
		HttpSession session = request.getSession(true);
		if (session.getAttribute("userName") != null) {
			userName = (String) session.getAttribute("userName");
		}
		JPollingLayout jLayout = new JPollingLayout();
		String workAreaContent = loadHome(request);
		xml.append(jLayout.buildAdminLayout(workAreaContent, userName));

		return xml.toString();
	}

	/**
	 * 
	 * @param request
	 * @return
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String loadHome(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();

		try {
			HttpSession session = request.getSession(true);
			UserTO userTO = (UserTO) session.getAttribute("userTO");
			UsersDAO usersDAO = new UsersDAO();
			QuestionsDAO questionsDAO = new QuestionsDAO();
			AnswersDAO answersDAO = new AnswersDAO();

			xml.append("<table  width='100%' class='body_content'>");
			xml.append("<tr>");
			xml.append("<td><div class='headings'>");
			xml.append("<table width='100%' >");
			xml.append("<tr>");
			xml.append("<td width='6%' align='right'><img src='/JPollingWeb/StaticContent/Images/home_ico.png' width='25' height='25' alt=' '></td><td width='63%'><h4 align='left'> Home page</h4></td>");
			xml.append("<td width='31%'><table align='right'>");
			xml.append("<tr>");
			xml.append("<td></td><td></td>");
			xml.append("</tr>");
			xml.append("</table></td>");
			xml.append("</tr>");
			xml.append("</table>");
			xml.append("</div>");

			xml.append("<table width='100%' cellpadding='3' cellspacing='3' >");
			xml.append("<tr>");
			xml.append("<td width='50%' height='193'>");
			xml.append("<table width='100%' class='data_table'>");
			xml.append("<tr>");
			xml.append("<td height='26' colspan='2' class='data_table_td_head'>System Details</td>");
			xml.append("</tr>");
			xml.append("<tr>");
			long userCount = usersDAO.getUsercount(null);
			xml.append("<td width='62%' ><div align='left'>Total Students</div></td>");
			xml.append("<td width='38%' ><div align='left'>" + userCount
					+ "</div></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			long questionCount = questionsDAO.getAdminQuestionsCount(userTO);
			xml.append("<td > <div align='left'>Total Questions Count </div></td>");
			xml.append("<td ><div align='left'>" + questionCount
					+ "</div></td>");
			xml.append("</tr>");
			xml.append("<tr >");
			long answerCount = answersDAO.getAnswerCount();
			xml.append("<td > <div align='left'>Total Answers Count </div></td>");
			xml.append("<td ><div align='left'>" + answerCount + "</div></td>");
			xml.append("</tr>");
			xml.append("</table></td>");
			/*xml.append("<td width='50%'>");
			xml.append("<table width='100%' class='data_table'>");
			xml.append("<tr>");
			xml.append("<td colspan='2' class='data_table_td_head'>Question Details</td>");
			xml.append("</tr>");
			xml.append("<tr >");

			xml.append("<td width='76%' ><div align='left'>Max Users for a question </div></td>");
			xml.append("<td width='24%' ><div align='left'>75</div></td>");
			xml.append("</tr>");
			xml.append("<tr >");
			xml.append("<td > <div align='left'>Min Users for a question </div></td>");
			xml.append("<td ><div align='left'>5</div></td>");
			xml.append("</tr>");
			xml.append("<tr >");
			xml.append("<td > <div align='left'>Max Replies for a question </div></td>");
			xml.append("<td ><div align='left'>98 %</div></td>");*/
			xml.append("</tr>");
			xml.append("</table></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td colspan='2' class=''>");
			xml.append("<div class='headings'>Last Posted Question</div></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td colspan='2'><table width='100%'>");
			xml.append("<tr>");
			if(questionsDAO.getLastQuestion()!=null)
				xml.append("<td colspan='2' class='data_table_td_1'>"
					+ questionsDAO.getLastQuestion().getQuestion() + "</td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td>&nbsp;</td>");
			xml.append("<td><table width='100%' class='data_table'>");

			if(questionsDAO.getLastQuestion()!=null){
				List<AnswerTO> ansList = answersDAO.getAnswerList(questionsDAO
						.getLastQuestion().getId());
				int i = 0;
				for (AnswerTO answerTO : ansList) {
					MappingDAO mapping = new MappingDAO();
					double percentege = mapping.getPercentageForAnswerId(answerTO);
					i += 1;
					xml.append("<tr>");
					xml.append("<td  width='7%'>" + i + "</td>");
					xml.append("<td  width='61%'>" + answerTO.getAnswer() + "</td>");
					xml.append("<td  width='32%'><span class='progressBar' id='element_"
							+ i + "'>" + percentege + "%</span></td>");
					xml.append("</tr>");
				}
			}
			
			xml.append("</table></td>");
			xml.append("</tr>");
			xml.append("</table></td>");
			xml.append("</tr>");
			xml.append("</table>");
			xml.append("<p>&nbsp;</p>");
			xml.append("<table width='100%' style='margin-top:10px'>");
			xml.append("</table>");
			xml.append("</div></td>");
			xml.append("</tr>");
			xml.append("</table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml.toString();
	}

	/**
	 * 
	 * @param request
	 * @return
	 *
	 * @Author Prabhu
	 * @since 1.0
	 */
	public String loadCreateQuestion(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		try {
			xml.append("<table width='100%' class='body_content'>");
			xml.append("<tbody><tr>");
			xml.append("<td><div class='headings'>");
			xml.append("<table width='100%'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='6%' align='right'><img width='25' height='25' alt='X' src='/JPollingWeb/StaticContent/Images/home_ico.png'></td>");
			xml.append("<td width='63%'><h4 align='left'> Question-Answers</h4></td>");
			xml.append("<td width='31%'><table align='right'>");
			xml.append("<tbody><tr>");
			xml.append("<td></td>");
			xml.append("</tr>");
			xml.append("</tbody></table></td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("</div>");
			xml.append("<div id='body_content'>");
			xml.append("<div class='headings'>Create Questions with Answers</div>");
			xml.append("<table width='100%' class='data_table'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='' height='40' class='data_table_td_1'>Question : </td>");
			/*xml.append("<td> <table><tr> <td width='70%'> <textarea id='question' style='width: 443px; height: 26px;'></textarea></td>  " +
					" <td  width='30%'> <form action='upload' method='post' enctype='multipart/form-data'><input type='file' name='file' /><input type='submit' value='upload' /></form> </tr> </table></td> ");*/
			
			xml.append("<td><table> <tr> <td width='80%' ><textarea id='question' style='width: 443px; height: 26px;'></textarea></td> <td>  <input type='file' id='imgLoader'>  " +
					" <input type='Button' class='submit_button' onclick='uploadImage()' value='submit'> </td> </tr> </table> </td> ");
			
			xml.append(getImgScript());
			
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Valid Upto : </td>");
			xml.append("<td valign='top' ><input type='text' id='end_date' class='fields' value='"
					+ JPollingUtil.getDateTime() + "'></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'> </td>");
			xml.append("<td valign='top' ><strong>Answer List</strong></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Answer 1 :</td>");
			xml.append("<td><table><tr> <input type='radio' name='answerSet' value='answer_1'>  <td><textarea id='answer_1' style='width: 443px; height: 26px;'></textarea></td></tr></table></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Answer 2 :</td>");
			xml.append("<td><table><tr> <input type='radio' name='answerSet' value='answer_2'>  <td><textarea id='answer_2' style='width: 443px; height: 26px;'></textarea></td></tr></table></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Answer 3 :</td>");
			xml.append("<td><table><tr> <input type='radio' name='answerSet' value='answer_3'>  <td><textarea id='answer_3' style='width: 443px; height: 26px;'></textarea></td></tr></table></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Answer 4 :</td>");
			xml.append("<td><table><tr> <input type='radio' name='answerSet' value='answer_4'>  <td><textarea id='answer_4' style='width: 443px; height: 26px;'></textarea></td></tr></table></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td></td>");
			xml.append("<td align='right'><input type='Button' class='submit_button' onclick='createQuestionPickUser()' value='Pick Users'></td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("<p>&nbsp;</p>");
			xml.append("</div></td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml.toString();

	}
	
	public String getImgScript(){
		String string;
		string = "";
		return string;
	}

	public String loadUserList(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		try {
			int start = 0;
			String question = "";
			if (request.getParameter("start") != null) {
				start = Integer.parseInt(request.getParameter("start"));
			}
			
			if(request.getParameter("question") != null){
				question = request.getParameter("question");
			}
			boolean pickUser = Boolean.parseBoolean(request
					.getParameter("pickUesr"));

			UsersDAO usersDAO = new UsersDAO();
			long count = usersDAO.getUsercount(question);
			List<UserTO> usersList = new ArrayList<UserTO>();
			usersList = usersDAO.getUsers(start,question);
			//long count = usersList.size();

			xml.append("<table width='100%' class='data_table'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='3%' class='data_table_td_head'>&nbsp;</td>");
			xml.append("<td width='15%' class='data_table_td_head'>User ID</td>");
			// xml.append("<td width='14%' class='data_table_td_head'>Password</td>");
			xml.append("<td width='22%' class='data_table_td_head'>Mail ID</td>");
			xml.append("<td width='20%' class='data_table_td_head'>Action</td>");
			xml.append("</tr>");
			xml.append("<tr>");
			if(usersList.size()>0) { 
			for (UserTO userTO : usersList) {
				if (userTO != null) {
					xml.append("<tr>");
					xml.append("<td width='5%' >");
					if (pickUser) {
						xml.append("<input type='checkbox' id='"
								+ userTO.getUserId()
								+ "' onChange='selecteOrUnselectUser("
								+ userTO.getUserId() + ");'>");
					}
					xml.append("</td>");
					xml.append("<td width='25%' >" + userTO.getUserName()
							+ "</td>");
					// xml.append("<td width='14%' >" + userTO.getPassword() +
					// "</td>");
					xml.append("<td width='25%' >" + userTO.getMailId()
							+ "</td>");
					/*if (userTO.isVerified()) {
						xml.append("<td width='25%' >Verified</td>");
					} else {
						xml.append("<td width='25%' >Not Verified</td>");
					}*/
					
					xml.append("<td width='25%' ><a href='#' onclick='loadEditUser("+ userTO.getUserId() +")'>Edit User</a></td>");
					xml.append("</tr>");
				}
			}
			} else {
				xml.append("<tr>");
				xml.append("<td></td>");
				xml.append("<td><center>No Answers available</center></td>");
				xml.append("</tr>");
			}
			xml.append("</tbody></table>");
			/*if(usersList != null && usersList.size() == 0){
				xml.append("<div>No Records Available</div>");
			}*/
			xml.append("<div class='data_table_footer' id='page_navigation'>");
			xml.append(getPageNavigation(count, start, "navigateUserList", "\""
					+ pickUser + "\""));
			xml.append("</div>");
		} catch (Exception e) {
			System.out.println("Exception in loading user list");
			e.printStackTrace();
		}
		return xml.toString();
	}

	public String getUserDate(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		long userId = Long.parseLong(request.getParameter("userId"));
		UsersDAO userDAO = new UsersDAO();
		UserTO userTO = new UserTO();
		MappingDAO mapping = new MappingDAO();
		userTO.setUserId(userId);
		userDAO.getUserById(userTO);
		xml.append("<table><tr> <td>Answerd Questions</td> <td> "+mapping.getMappingForUser(userTO)+" </td> </tr></table>");
		return xml.toString();
	}
		
	public String loadUsers(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();

		try {

			xml.append("<table width='100%' class='body_content'>");
			xml.append("<tbody><tr>");
			xml.append("<td><div class='headings'>");
			xml.append("<table width='100%'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='6%' align='right'><img width='25' height='25' alt='X' src='/JPollingWeb/StaticContent/Images/users.png'></td>");
			xml.append("<td width='63%'><h4 align='left' class='quick_links'> Users</h4></td>");
			xml.append("<td width='31%'><table align='right'>");
			xml.append("<tbody><tr>");
			xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""
					+ "single"
					+ "\")'><img width='25' height='25' title='Create Single User' src='/JPollingWeb/StaticContent/Images/add_user.png'/></a></td>");
			xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""
					+ "multiple"
					+ "\")'><img width='25' height='25' title='Create Multiple Users' src='/JPollingWeb/StaticContent/Images/add_users.png' /></a></td>");
			xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""
					+ "mailid"
					+ "\")'><img width='25' height='25' title='Create Users By Mail' src='/JPollingWeb/StaticContent/Images/add_user_mail.png' /></a></td>");
			xml.append("</tr>");
			xml.append("</tbody></table></td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("</div>");
			xml.append("<div>");
			xml.append("<table width='100%' cellspacing='3' cellpadding='3' align='center'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='100%' class=''><div class='headings'>User List</div></td>");
			xml.append("</tr>");
			xml.append("<tr><td align='center'>");
			xml.append("<div id='user_list'>");
			xml.append(loadUserList(request));
			xml.append("</div>");
			xml.append("</td></tr>");
			xml.append("</tbody></table>");
		} catch (Exception e) {
			System.out.println("Exception loading Users");
		}
		return xml.toString();
	}

	private String getPageNavigation(long count, long start, String onClick,
			String extraParem) {
		StringBuffer html = new StringBuffer();
		long perPage = 5;
		long end = start + perPage;

		long totalPage = 1;
		if (count > perPage) {
			totalPage = count / perPage;
			if (totalPage * perPage < count) {
				totalPage++;
			}
		}
		long currentPage = end / perPage;
		html.append("<table width='100%' align='center'>");
		html.append("<tbody><tr>");
		html.append("<td align='center'><table>");
		html.append("<tbody><tr>");
		html.append("<td width='25%'>");
		if (currentPage > 1) {
			String previousOnClick;
			if (extraParem != null) {
				previousOnClick = onClick + "(" + (start - perPage) + ","
						+ extraParem + ");";
			} else {
				previousOnClick = onClick + "(" + (start - perPage) + ");";
			}
			html.append("<a href='#' onclick='")
					.append(previousOnClick)
					.append("'><img width='25' height='25' src='/JPollingWeb/StaticContent/Images/PreviousButton_blue.png'></a>");
		} else {
			html.append("<a href='#'><img width='25' height='25' src='/JPollingWeb/StaticContent/Images/PreviousButton_dark.png'></a>");
		}
		html.append("</td>");
		html.append("<td width='50%'><div align='center'>");
		html.append(currentPage).append("&nbsp;/&nbsp;").append(totalPage)
				.append("</div></td>");
		html.append("<td width='25%'>");
		if (currentPage < totalPage) {
			String nextOnClick;
			if (extraParem != null) {
				nextOnClick = onClick + "(" + (end) + "," + extraParem + ");";
			} else {
				nextOnClick = onClick + "(" + (end) + ");";
			}

			html.append("<a href='#' onclick='")
					.append(nextOnClick)
					.append("'><img width='25' height='25' src='/JPollingWeb/StaticContent/Images/NextButton_blue.png'></a>");
		} else {
			html.append("<a href='#'><img width='25' height='25' src='/JPollingWeb/StaticContent/Images/NextButton_dark.png'></a>");
		}
		html.append("</td>");
		html.append("</tr>");
		html.append("</tbody></table></td>");
		html.append("</tr>");
		html.append("</tbody></table>");
		html.append("<p>&nbsp;</p>");
		html.append("<table width='100%' style='margin-top:10px'>");
		html.append("</table>");
		return html.toString();
	}

	public String loadCreateUsers(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		String type = request.getParameter("type");

		xml.append("<table width='100%' class='body_content'>");
		xml.append("<tbody><tr>");
		xml.append("<td><div class='headings'>");
		xml.append("<table width='100%'>");
		xml.append("<tbody><tr>");
		xml.append("<td width='6%' align='right'><img width='25' height='25' alt='X' src='/JPollingWeb/StaticContent/Images/users.png'></td>");
		xml.append("<td width='63%'><h4 align='left' class='quick_links'> Users</h4></td>");
		xml.append("<td width='31%'><table align='right'>");
		xml.append("<tbody><tr>");
		xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""
				+ "single"
				+ "\")'><img width='25' height='25' title='Create Single User' src='/JPollingWeb/StaticContent/Images/add_user.png'/></a></td>");
		// xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""+"multiple"+"\")'><img width='25' height='25' title='Create Multiple Users' src='/JPollingWeb/StaticContent/Images/add_users.png' /></a></td>");
		// xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""+"mailid"+"\")'><img width='25' height='25' title='Create Users By Mail' src='/JPollingWeb/StaticContent/Images/add_user_mail.png'/></a></td>");
		xml.append("</tr>");
		xml.append("</tbody></table></td>");
		xml.append("</tr>");
		xml.append("</tbody></table>");
		xml.append("</div>");
		xml.append("</td></tr>");
		xml.append("<tr><td>");
		xml.append("<div>");
		xml.append("<div id='body_content'>");

		if (type == null || type.equalsIgnoreCase("single")) {
			xml.append("<div class='headings'>Create Single User</div>");
			xml.append("<table width='100%' align='left'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='20%' class='data_table_td_1'> User Name * </td>");
			xml.append("<td width='23%'><input type='text' id='user_name'></td>");
			xml.append("<td width='18%' >&nbsp;</td>");
			xml.append("<td width='29%'>&nbsp;</td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Password <font color = 'red'><strong>*</strong></font> </td>");
			xml.append("<td><input type='password' id='password'></td>");
			xml.append("<td>&nbsp;</td>");
			xml.append("<td>&nbsp;</td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Mail Id</td>");
			xml.append("<td><input type='text' id='mail_id'></td>");
			xml.append("<td>&nbsp;</td>");
			xml.append("<td>&nbsp;</td>");
			xml.append("</tr>");
		} else if (type.equalsIgnoreCase("multiple")) {
			xml.append("<div class='headings'>Create Multiple Users</div>");
			xml.append("<table width='100%' align='left'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='30%' class='data_table_td_1'>Common User Name </td>");
			xml.append("<td width='23%' ><input type='text' id='common_user_name'></td>");
			xml.append("<td width='18%' class='data_table_td_1'>Total Users </td>");
			xml.append("<td width='29%' ><div align='center' id='user_count'>10</div></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Start Value * </td>");
			xml.append("<td ><input type='text' id='start_value'></td>");
			xml.append("<td class='data_table_td_1'>Stop Value * </td>");
			xml.append("<td ><input type='text' id='end_value'></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Password");
			xml.append("</td>");
			xml.append("<td >");
			xml.append("<input type='password' id='common_password'>");
			xml.append("</td>");
			xml.append("<td class='data_table_td_1'>Mail Id");
			xml.append("</td>");
			xml.append("<td >");
			xml.append("<input type='text' id='common_mail_id'>");
			xml.append("</td>");
			// xml.append("<td>&nbsp;</td>");
			// xml.append("<td>&nbsp;</td>");
			xml.append("</tr>");
		} else { // else if(type.equalsIgnoreCase("mailid")
			xml.append("<div class='headings'>Create Users By Mail Id</div>");
			xml.append("<table width='100%' align='left'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='30%' class='data_table_td_1'>Common User Name </td>");
			xml.append("<td width='23%' ><input type='text' id='common_user_name'></td>");
			xml.append("<td class='data_table_td_1'>Password");
			xml.append("</td>");
			xml.append("<td >");
			xml.append("<input type='password' id='common_password'>");
			xml.append("</td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td height='64' class='data_table_td_1'><p>Mail Id's *</p>");
			xml.append("<p> comma seperated </p></td>");
			xml.append("<td colspan='3' >&nbsp;");
			xml.append("<textarea style='width: 463px; height: 78px;' id='common_mail_id' name='user_maillId' cols='75' rows='4'></textarea></td>");
			xml.append("</tr>");
			xml.append("</tbody>");
		}

		xml.append("<tr>");
		xml.append("<td>&nbsp;</td>");
		xml.append("<td>&nbsp;</td>");
		xml.append("<td>&nbsp;</td>");
		xml.append("<td><input type='button'  onclick='createUsers(\"" + type
				+ "\")' value='Create User' id='createUsers'></td>");
		xml.append("</tr>");
		xml.append("</tbody></table>");
		xml.append("</div>");
		xml.append("<p>&nbsp;</p>");
		xml.append("</div></td>");
		xml.append("</tr>");
		xml.append("</tbody></table>");

		return xml.toString();
	}
	
	public String loadEditUser(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();

		long userId = Long.parseLong(request.getParameter("userId"));
		UserTO userTO = new UserTO();
		UsersDAO userDao = new UsersDAO();
		userTO.setUserId(userId);
		
		userTO = userDao.getUserById(userTO);
		xml.append("<table width='100%' class='body_content'>");
		xml.append("<tbody><tr>");
		xml.append("<td><div class='headings'>");
		xml.append("<table width='100%'>");
		xml.append("<tbody><tr>");
		xml.append("<td width='6%' align='right'><img width='25' height='25' alt='X' src='/JPollingWeb/StaticContent/Images/users.png'></td>");
		xml.append("<td width='63%'><h4 align='left' class='quick_links'> Users</h4></td>");
		xml.append("<td width='31%'><table align='right'>");
		xml.append("<tbody><tr>");
		xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""
				+ "single"
				+ "\")'><img width='25' height='25' title='Create Single User' src='/JPollingWeb/StaticContent/Images/add_user.png'/></a></td>");
		// xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""+"multiple"+"\")'><img width='25' height='25' title='Create Multiple Users' src='/JPollingWeb/StaticContent/Images/add_users.png' /></a></td>");
		// xml.append("<td class='quick_links'><a href='#' onclick='loadCreateUsers(\""+"mailid"+"\")'><img width='25' height='25' title='Create Users By Mail' src='/JPollingWeb/StaticContent/Images/add_user_mail.png'/></a></td>");
		xml.append("</tr>");
		xml.append("</tbody></table></td>");
		xml.append("</tr>");
		xml.append("</tbody></table>");
		xml.append("</div>");
		xml.append("</td></tr>");
		xml.append("<tr><td>");
		xml.append("<div>");
		xml.append("<div id='body_content'>");

		if(userTO != null){
		//if (type == null || type.equalsIgnoreCase("single")) {
			//xml.append("<div class='headings'>Create Single User</div>");
			xml.append("<table width='100%' align='left'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='20%' class='data_table_td_1'> User Name * </td>");
			xml.append("<td width='23%'><input type='text' id='user_name' value= '"+ userTO.getUserName()+"'></td>");
			xml.append("<td width='18%' >&nbsp;</td>");
			xml.append("<td width='29%'>&nbsp;</td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Password <font color = 'red'><strong>*</strong></font> </td>");
			xml.append("<td><input type='password' id='password' value= '"+ userTO.getPassword()+"'></td>");
			xml.append("<td>&nbsp;</td>");
			xml.append("<td>&nbsp;</td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td class='data_table_td_1'>Mail Id</td>");
			xml.append("<td><input type='text' id='mail_id' value= '"+ userTO.getMailId()+"'></td>");
			xml.append("<td>&nbsp;</td>");
			xml.append("<td>&nbsp;</td>");
			xml.append("</tr>");
		}
		
		xml.append("<tr>");
		xml.append("<td>&nbsp;</td>");
		xml.append("<td>&nbsp;</td>");
		xml.append("<td>&nbsp;</td>");
		xml.append("<td><input type='button'  onclick='saveEditUser(\"" + userId
				+ "\")' value='Save User' id='saveUsers'></td>");
		xml.append("</tr>");
		xml.append("</tbody></table>");
		xml.append("</div>");
		xml.append("<p>&nbsp;</p>");
		xml.append("</div></td>");
		xml.append("</tr>");
		xml.append("</tbody></table>");

		return xml.toString();
	}

	public String createQuestionPickUser(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		try {
			QuestionsDAO questionsDAO = new QuestionsDAO();
			QuestionTO questionTO = new QuestionTO();
			questionTO.setQuestion(request.getParameter("question"));
			questionTO.setEndDate(DateUtils.parse(request.getParameter("end_date")));
			questionTO.setStartDate(new Date());
			//questionTO.setEndDate(JPollingUtil.getSqlDateTime());
			questionTO = questionsDAO.saveQuestion(questionTO);
			
			String selected = request.getParameter("selected");
			
			HttpSession session = request.getSession(true);

			if(session.getAttribute("fileName") != null){
				this.renameFile((String)session.getAttribute("fileName"),questionTO.getId());
			}
			AnswersDAO answerDAO;
			QADAO qaDAO;
			AnswerTO answerTO;
			StringTokenizer answers = new StringTokenizer(
					(String) request.getParameter("answers"), "!_");

			while (answers.hasMoreElements()) {
				String ans = answers.nextToken();
				
				if (ans != "" && ans != null) {
					
					answerDAO = new AnswersDAO();
					answerTO = new AnswerTO();
					answerTO.setQuestion(questionTO);
					answerTO.setAnswer(ans);
					answerTO = answerDAO.saveAnswer(answerTO);
					
					if(ans.equals(selected) && answerTO!=null){
						qaDAO = new QADAO();
						QATO qaTO = new QATO();
						qaTO.setQuestion(questionTO);
						qaTO.setAnswer(answerTO);
						qaDAO.saveQA(qaTO);
					}
				}
			}
			
			xml.append("<table width='100%' class='body_content'>");
			xml.append("<tbody><tr>");
			xml.append("<td><div class='headings'>");
			xml.append("<table width='100%'>");
			xml.append("<tbody><tr>");
			xml.append("<td width='6%' align='right'><img width='25' height='25' alt='X' src='/JPollingWeb/StaticContent/Images/home_ico.png'></td>");
			xml.append("<td width='63%'><h4 align='left'> Question-Answers</h4></td>");
			xml.append("<td width='31%'><table align='right'>");
			xml.append("<tbody><tr>");
			xml.append("<td><img width='25' height='25' src='/JPollingWeb/StaticContent/Images/users.png'></td>");
			xml.append("<td>Create</td>");
			xml.append("</tr>");
			xml.append("</tbody></table></td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("</div>");
			xml.append("<div id='body_content'>");
			xml.append("<div class='headings'>Pick Users");
			xml.append("<input type='hidden' id='selected_users' value=''></div>");
			xml.append("<table width='100%'>");
			xml.append("<tbody><tr><td align='center'>");
			xml.append("<div id='user_list'>");
			System.out.println("");
			xml.append(loadUserList(request));
			xml.append("</div></td>");
			xml.append("</tr>");
			xml.append("<tr>");
			xml.append("<td></td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("</div>");
			xml.append("<div align='right' id='button_div'>");
			xml.append("<table>");
			xml.append("<tbody><tr>");
			xml.append("<td align='right'><input type='button' class='submit_button' value='Assign Question' onclick='assignQuestion("
					+ questionTO.getId() + ")'></td>");
			xml.append("<td align='right'><input type='button' class='submit_button' value='Assign Question &amp; Send Mail'></td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("</div>");
			xml.append("<p>&nbsp;</p>");
			xml.append("</div></td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml.toString();
	}

	/**
	 * Load question Page
	 * 
	 * @param request
	 * @return
	 * @author siva
	 * @since 1.0
	 */
	public String loadQuestion(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserTO userTO = (UserTO) session.getAttribute("userTO");
		StringBuffer xml = new StringBuffer();

		try {
			xml.append("<table width='100%' class='body_content'>");
			xml.append("<tbody><tr>");
			xml.append("<td>");
			if (userTO.getUserType().equalsIgnoreCase("admin")) {
				xml.append("<div class='headings'>");
				xml.append("<table width='100%'>");
				xml.append("<tbody><tr>");
				xml.append("<td width='6%' align='right'><img width='25' height='25' alt='X' src='/JPollingWeb/StaticContent/Images/Question.png'></td>");
				xml.append("<td width='63%'><h4 align='left' class='quick_links'> Question Bank</h4></td>");
				xml.append("<td width='31%'><table align='right'>");
				xml.append("<tbody><tr>");
				xml.append("<td class='quick_links'><a onclick='loadCreateQuestion()' href='#'><img width='25' height='25' title= 'Add ' src='/JPollingWeb/StaticContent/Images/add.png'></a></td>");
				xml.append("</tr>");
				xml.append("</tbody></table></td>");
				xml.append("</tr>");
				xml.append("</tbody></table>");
				xml.append("</div>");

			}
			xml.append("<div class='headings'>All Question - Answers</div>");

			xml.append("<div id='questions_div'>");
			xml.append(loadQuestionList(request));
			xml.append("</div>");
			xml.append("</td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("<p>&nbsp;</p>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml.toString();
	}

	public String loadQuestionList(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		HttpSession session = request.getSession(true);
		UserTO userTO = (UserTO) session.getAttribute("userTO");
		try {
			boolean isReport = false;
			int start = 0;
			if (request.getParameter("start") != null) {
				start = Integer.parseInt(request.getParameter("start"));
			}
			// isReport =
			// Boolean.parseBoolean(request.getParameter("isReport"));

			xml.append(generateQuestionList(userTO, start, isReport));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml.toString();
	}

	public String generateQuestionList(UserTO userTO, int start,
			boolean isReport) {
		StringBuffer xml = new StringBuffer();
		try {
			List<QuestionTO> questionList = new ArrayList<QuestionTO>();
			QuestionsDAO questionDAO = new QuestionsDAO();
			MappingDAO mappingDAO = new MappingDAO();
			long count = 0;
			xml.append("<div id='questions_list'>");
			xml.append("<table width='100%' cellpadding='1'>");
			xml.append("<tbody><tr class='data_table_td_head'>");
			xml.append("<td width='4%' height='40'>&nbsp;</td>");
			xml.append("<td width='65%' >Question</td>");
			xml.append("<td width='15%'>Start date</td>");
			xml.append("<td width='16%'>End date</td>");
			xml.append("</tr>");
			if (userTO.getUserType().equals("admin")) {
				count = questionDAO.getAdminQuestionsCount(userTO);
				questionList = questionDAO.getAdminQuestions(start);
			} else {
				count = mappingDAO.getUserQuestionsCount(userTO);
				questionList = mappingDAO.getUserQuestions(userTO, start);
			}
			int i = 0;
			int tot = questionList.size();
			if (tot > 0) {
				Iterator<QuestionTO> itrList = questionList.iterator();
				while (itrList.hasNext()) {
					i += 1;
					QuestionTO question = itrList.next();
					xml.append("<tr>");
					if (isReport) {
						xml.append("<td class='data_table_td_1' width='5%' height='37'><a href='' onclick='loadSubReport("
								+ question.getId()
								+ ")'><img width='25' height='25' alt='Report' src='/JPollingWeb/StaticContent/Images/pie_chart.png'/></a></td>");
						xml.append("<td class='data_table_td_1'>");
						xml.append(question.getQuestion());
						xml.append("</td>");
					} else {
						xml.append("<td class='data_table_td_1' width='4%' height='40'></td>");
						xml.append("<td class='data_table_td_1'>");
						xml.append("<a style='cursor:pointer;' onclick='showAnswer("
								+ question.getId() + "," + tot + ");'>");
						xml.append(question.getQuestion());
						xml.append("</a>");
						if (userTO.getUserType().equals("admin")) {
							xml.append("<span><a href='#' onclick='deleteQuestion("
									+ question.getId()
									+ ")'><img width='25' height='25' src = '/JPollingWeb/StaticContent/Images/delete.png'></a></span>");
						}
						String fileName = "C:\\Project\\hack\\tomcat\\webapps\\JPollingWeb\\StaticContent\\Images\\uploads\\"+question.getId()+".png";
						if(new File(fileName).exists()){
							xml.append("<br /><br /><img src='/JPollingWeb/StaticContent/Images/uploads/"+question.getId()+".png' width='200px' height='150px' />");
						}
						
						xml.append("</td>");
					}
					xml.append("<td class='data_table_td_1'>");
					xml.append(question.getStartDate());
					xml.append("</td>");
					xml.append("<td class='data_table_td_1'>");
					xml.append(question.getEndDate());
					xml.append("</td>");
					xml.append("</tr>");
					xml.append("<tr><td></td>");
					xml.append("<td colspan='3'>");
					xml.append("<div id='ans_div_" + question.getId() + "'>");
					xml.append("</div></td></tr>");
				}
			} else {
				xml.append("<tr class='data_table_td_1'>");
				xml.append("<td width='5%' height='40' align='center' colspan='4'>Record not available</td>");
				xml.append("</tr>");
			}
			xml.append("</tbody></table>");
			xml.append("</div>");
			xml.append("<div class='data_table_footer' id='page_navigation'>");
			if (isReport) {
				xml.append(getPageNavigation(count, start,
						"navigateQuestionList", "true"));
			} else {
				xml.append(getPageNavigation(count, start,
						"navigateQuestionList", "false"));
			}
			xml.append("</div>");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return xml.toString();
	}

	/**
	 * Show Answer based on question id
	 * 
	 * @param question_id
	 * @return
	 * @author siva
	 * @since 1.0
	 */
	public String showAnswer(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserTO userTO = (UserTO) session.getAttribute("userTO");
		StringBuffer xml = new StringBuffer();
		AnswersDAO answersDAO = new AnswersDAO();
		try {
			long questionId = Integer.parseInt(request
					.getParameter("question_id"));
			List<AnswerTO> answerList = answersDAO.getAnswerList(questionId);
			xml.append("<table cellpadding='1' width='100%' class='data_table'><tbody><tr>");
			xml.append("<td width='8%' class='data_table_td_head'>&nbsp;</td>");
			xml.append("<td width='80%' class='data_table_td_head'>Answers</td>");
			if(userTO.getUserType().equalsIgnoreCase("admin")){
				xml.append("<td width='12%' class='data_table_td_head'></td>");
			}
			xml.append("</tr>");
			int i = 0;
			if (answerList.size() > 0) {
				Iterator<AnswerTO> itr = answerList.iterator();
				while (itr.hasNext()) {
					i += 1;
					AnswerTO answerTO = itr.next();
					xml.append("<tr>");
					xml.append("<td align='center'>");
					if ("user".equals(userTO.getUserType())) {
						xml.append("<input id='"
								+ answerTO.getId()
								+ "' name='answers' type='radio' onclick='postAnswer("
								+ answerTO.getQuestion().getId() + ","
								+ answerTO.getId() + ")'>");
					} else {
						xml.append(i);
					}
					xml.append("</td>");
					xml.append("<td >" + answerTO.getAnswer() + "</td>");
					
					if(userTO.getUserType().equalsIgnoreCase("admin")){
						MappingDAO mapping = new MappingDAO();
						double percentege = mapping.getPercentageForAnswerId(answerTO);
						//i += 1;
						//xml.append("<tr>");
						//xml.append("<td  width='7%'>" + i + "</td>");
						//xml.append("<td  width='61%'>" + answerTO.getAnswer() + "</td>");
						xml.append("<td  width='32%'><span class='progressBar' id='element_" + i + "'>" + percentege + "%</span></td>");
					}
					//xml.append("</tr>");
					xml.append("</tr>");
				}
			} else {
				xml.append("<tr>");
				xml.append("<td></td>");
				xml.append("<td><center>No Answers available</center></td>");
				xml.append("</tr>");
			}
			xml.append("</tbody></table>");
		} catch (Exception e) {
			System.out.println("Exception in loading answers");
			e.printStackTrace();
		}
		return xml.toString();
	}

	/**
	 * Load home page of client
	 * 
	 * @param request
	 * @return
	 * @author siva
	 * @since 1.0
	 */

	public String loadClientPage(HttpServletRequest request) {
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
		xml.append("<td  width='4%' valign='top'></td>");
		xml.append("<td  width='96%' valign='top'>");
		xml.append("<div class='border_div'>");
		xml.append("<div class='border_div_bottom'>");
		xml.append("<div id='work_area'>");
		xml.append(loadClientHome(request));
		xml.append("</div>");
		xml.append("</div></div></td>");
		xml.append("</tr>");
		xml.append("</table>");
		return xml.toString();
	}

	/**
	 * Load client center part
	 * 
	 * @param request
	 * @return
	 * @author siva
	 * @since 1.0
	 */
	public String loadClientHome(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		HttpSession session = request.getSession(true);
		UserTO userTO = (UserTO) session.getAttribute("userTO");
		try {
			xml.append("<table width='100%' class='body_content'>");
			xml.append("<tbody><tr>");
			xml.append("<td><div class='headings'>");
			xml.append("<table width='100%'>");
			xml.append("<tbody><tr>");
			xml.append("<td align='left'><img width='0' height='25' alt='X' src='/JPollingWeb/StaticContent/Images/white_space.jpg'>");
			xml.append("<table align='left' width='100%'>");
			xml.append("<tbody><tr>");
			xml.append("<td><img width='25' height='25' alt='X' src='/JPollingWeb/StaticContent/Images/users.png'></td>");
			xml.append("<td width='93%' valign='top'>" + userTO.getUserName()
					+ "</td>");
			xml.append("<td align='right'>");
			xml.append("<a href='#' onclick='logOut();' ><img src='/JPollingWeb/StaticContent/Images/logout.png' width='25' height='25' title='Log out' alt='Log Out' /></a>");
			xml.append("</td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("</td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
			xml.append("</div>");
			xml.append(loadQuestion(request));
			xml.append("</td>");
			xml.append("</tr>");
			xml.append("</tbody></table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml.toString();
	}

	/**
	 * 
	 * @param request
	 * @return
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String assignQuestion(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		MappingDAO mappingDAO = new MappingDAO();
		QuestionTO questionTO = new QuestionTO();
		questionTO.setId(Long.parseLong(request.getParameter("question_id")));
		StringTokenizer userIds = new StringTokenizer(
				(String) request.getParameter("user_ids"), "!_");

		while (userIds.hasMoreElements()) {
			String id = userIds.nextToken();
			if (id != "" && id != null) {

				MappingTO mapping = new MappingTO();
				UserTO userTO = new UserTO();
				userTO.setUserId(Long.parseLong(id));
				mapping.setUser(userTO);
				mapping.setQuestion(questionTO);
				mappingDAO.saveMapping(mapping);
			}
		}
		xml.append(loadQuestion(request));
		return xml.toString();
	}

	/**
	 * 
	 * @param request
	 * @return
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String assignQuestionSendMail(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		try {
			xml.append(assignQuestion(request));
			StringTokenizer userIds = new StringTokenizer(
					(String) request.getParameter("user_ids"), "!_");

			while (userIds.hasMoreElements()) {
				String id = userIds.nextToken();
				if (id != "" && id != null) {
					UsersDAO usersDAO = new UsersDAO();
					UserTO userTO = new UserTO();
					userTO.setUserId(Long.parseLong(id));
					userTO = usersDAO.getUserById(userTO);
					String mailContent = generateMailforUser(userTO);
					SendMail mail = new SendMail();
					mail.send(userTO.getMailId(), "JPolling", mailContent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml.toString();
	}

	/**
	 * 
	 * @param request
	 * @return
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String postAnswer(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		HttpSession session = request.getSession(true);
		UserTO userTO = (UserTO) session.getAttribute("userTO");
		if (userTO != null && userTO.getUserId() != 0) {
			if (request.getParameter("ans_id") != null
					&& request.getParameter("q_id") != null) {
				long ansId = Long.parseLong(request.getParameter("ans_id"));
				long questionId = Long.parseLong(request.getParameter("q_id"));

				QuestionTO questionTO = new QuestionTO();
				questionTO.setId(questionId);
				AnswerTO answerTO = new AnswerTO();
				answerTO.setId(ansId);
				MappingDAO mappingDAO = new MappingDAO();
				MappingTO mapping = new MappingTO();

				mapping = mappingDAO.getMapping(userTO, questionTO);
				answerTO.setQuestion(questionTO);
				mapping.setAnswer(answerTO);

				mappingDAO.saveMapping(mapping);
			}
		}
		xml.append(loadQuestionList(request));

		return xml.toString();
	}

	/**
	 * Method to generate the mail content
	 * 
	 * @param userTO
	 * @return String
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */

	public String generateMailforUser(UserTO userTO) {

		StringBuffer mailContent = new StringBuffer();

		mailContent.append("<html>");
		mailContent.append("");
		mailContent.append("<body>");
		mailContent
				.append("<h4> This is an auto generated mail from JLearning System</h4>");
		mailContent.append("</br>");
		mailContent.append(" A Question have been assigned to You ");
		mailContent.append("</br>");
		mailContent.append("<table width='100%' border='0'>");
		mailContent.append("<tr>");
		mailContent.append("<td>");
		mailContent.append("<USER NAME :>");
		mailContent.append("</td>");
		mailContent.append("<td>");
		mailContent.append(userTO.getUserName());
		mailContent.append("</td>");
		mailContent.append("</tr>");
		mailContent.append("<td>");
		mailContent.append("<PASSWORD :>");
		mailContent.append("</td>");
		mailContent.append("<td>");
		mailContent.append(userTO.getPassword());
		mailContent.append("</td>");
		mailContent.append("</tr>");
		mailContent.append("</table>");
		mailContent.append("<a href='" + JPollingLayout.URL
				+ "'>Click here to Sign in</a>");
		mailContent.append("</body>");
		mailContent.append("</html>");

		return mailContent.toString();

	}

	public String loadReport(HttpServletRequest request) {
		JReportingComponent jReportingComponent = new JReportingComponent();

		HttpSession session = request.getSession(true);
		UserTO userTO = (UserTO) session.getAttribute("userTO");
		long questionId = 0;
		int start = 0;
		questionId = Long.parseLong((String) session.getAttribute("qId"));
		start = Integer.parseInt((String) session.getAttribute("start"));

		String workAreaContent = jReportingComponent.generateReport(questionId,
				start, userTO);
		String userName = null;
		StringBuffer html = new StringBuffer();
		if (session.getAttribute("userName") != null) {
			userName = (String) session.getAttribute("userName");
		}
		JPollingLayout jLayout = new JPollingLayout();
		html.append(jLayout.buildAdminLayout(workAreaContent, userName));

		return html.toString();
	}

	public String setRedirection(HttpServletRequest request) {
		StringBuffer html = new StringBuffer();
		HttpSession session = request.getSession(true);

		try {
			Map valueMap = request.getParameterMap();
			Iterator i = valueMap.keySet().iterator();
			while (i.hasNext()) {
				String key = (String) i.next();
				String value = ((String[]) valueMap.get(key))[0];
				session.setAttribute(key, value);
			}
			// session.setAttribute("valueMap", request.getParameterMap());
			html.append("done");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return html.toString();
	}

	public void deleteQuestion(HttpServletRequest request) {
		// HttpSession session=request.getSession(true);
		int questionId = Integer.parseInt((String) request
				.getParameter("questionId"));
		boolean isdeleteAllowed = false;
		MappingDAO mappingDAO = new MappingDAO();
		isdeleteAllowed = mappingDAO.deleteMapping(questionId);
		if (isdeleteAllowed) {
			AnswersDAO answerDAO = new AnswersDAO();
			isdeleteAllowed = answerDAO.deleteAnswer(questionId);
			if (isdeleteAllowed) {
				QuestionsDAO questionDAO = new QuestionsDAO();
				isdeleteAllowed = questionDAO.deleteQuestion(questionId);
				System.out.println("Deleted Question Id " + questionId);
			}
		}

	}
	
	public void renameFile(String fileName,long id){
		File file = new File("C:\\Project\\hack\\tomcat\\webapps\\JPollingWeb\\StaticContent\\Images\\uploads\\"+fileName);
		File file2 = new File("C:\\Project\\hack\\tomcat\\webapps\\JPollingWeb\\StaticContent\\Images\\uploads\\"+id+".png");
		file.renameTo(file2);
	}
}
