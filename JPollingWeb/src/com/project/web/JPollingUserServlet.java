package com.project.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.jpolling.to.user.UserTO;
import com.project.web.layout.JPollingLayout;

/**
 * Servlet implementation class JPollingServlet
 */

public class JPollingUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public JPollingUserServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JPollingLayout jPollingLayout = new JPollingLayout();
		HttpSession session = request.getSession(true);
		StringBuffer totalPageContent = new StringBuffer();
		String landingPage = (String) session.getAttribute("landingPage");
		if(landingPage==null){
			landingPage=request.getParameter("action");
		}
		try {
			/*if (session.getAttribute("userTO") != null){
				UserTO userTO = (UserTO) session.getAttribute("userTO");
				if (userTO!=null && userTO.getUserType()!=null && userTO.getUserType().equals("admin")) {
					Class cls = Class.forName("com.project.web.JPollingUserHelper");
					Object clsObj = cls.newInstance();
					Class param[] = { HttpServletRequest.class };
					Method meth = cls.getDeclaredMethod(landingPage, param);
					Object argList[] = { request };
					Object retobj = meth.invoke(clsObj, argList);

					if (retobj != null) {
						if (retobj instanceof String) {
							totalPageContent.append((String) retobj);
						} else {
							totalPageContent = (StringBuffer) retobj;
						}
					}
				} else { // userHomePage
					JPollingUserHelper jPollingHelper = new JPollingUserHelper(userTO);
					totalPageContent.append(jPollingHelper.loadClientPage(request));
				}
				session.setAttribute("landingPage", landingPage);
			} else {*/
				totalPageContent.append(jPollingLayout.getUserRegistrationPage());
			//}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		StringBuffer html = new StringBuffer();
		html.append(jPollingLayout.buildHTML(totalPageContent.toString()));
		sendContent(html.toString(), request, response, "text/html");

	}

	/**
	 * 
	 * @param content
	 * @param request
	 * @param response
	 * @param contentType
	 * @throws IOException
	 * 
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	private void sendContent(String content, HttpServletRequest request,
			HttpServletResponse response, String contentType)
			throws IOException {

		PrintWriter out = response.getWriter();
		out.println(content);
		response.setContentType(contentType);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		StringBuffer content = new StringBuffer();
		try {
			String action = request.getParameter("action");
			HttpSession session = request.getSession(true);
			session.setAttribute("landingPage", action);
			Class cls = Class.forName("com.project.web.JPollingUserHelper");
			Object clsObj;
			clsObj = cls.newInstance();
			Class param[] = { HttpServletRequest.class };
			Method meth = cls.getDeclaredMethod(action, param);
			Object argList[] = { request };
			Object retobj = meth.invoke(clsObj, argList);

			if (retobj != null) {
				if (retobj instanceof String) {
					content.append((String) retobj);
				} else {
					content = (StringBuffer) retobj;
				}
			}
			sendContent(content.toString(), request, response, "text/html");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}