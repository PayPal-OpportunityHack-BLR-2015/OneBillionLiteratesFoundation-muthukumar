package com.project.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;

	public void init() {
		// Get the file location where it would be stored.
		filePath = "C:\\Project\\hack\\tomcat\\webapps\\JPollingWeb\\StaticContent\\Images\\uploads\\";
	}

	/*public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		InputStream in = null;
		FileOutputStream fos = null;
		try {
			HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(
					request);
			InputStream is = wrappedRequest.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String imageString = writer.toString();
			imageString = imageString.substring("data:image/png;base64,"
					.length());
			byte[] contentData = imageString.getBytes();
			Base64 Base64 = new Base64();
			byte[] decodedData = Base64.decode(contentData);
			String imgName = filePath
					+ String.valueOf(System.currentTimeMillis()) + ".png";
			fos = new FileOutputStream(imgName);
			fos.write(decodedData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}*/
	
	protected void doPost(HttpServletRequest request,

	HttpServletResponse response)

	throws ServletException, IOException {

		String ajaxUpdateResult = "";
		

		try {
			
			
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory())
					.parseRequest(request);

			for (FileItem item : items) {

				if (item.isFormField()) {

					ajaxUpdateResult += "Field " + item.getFieldName() +

					" with value: " + item.getString()
							+ " is successfully read\n\r";

				} else {

					String fileName = item.getName();
					InputStream content = item.getInputStream();
					try {
						this.upload(content,filePath + fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					
					HttpSession session = request.getSession(true);
					session.setAttribute("fileName", fileName);

					ajaxUpdateResult += "File " + fileName
							+ " is successfully uploaded\n\r";

				}

			}

		} catch (FileUploadException e) {
			throw new ServletException("Parsing file upload failed.", e);
		}
		response.getWriter().print(ajaxUpdateResult);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with "
				+ getClass().getName() + ": POST method required.");
	}

	public void upload(InputStream is, String fileName)throws Exception {
		OutputStream outputStream = null;
		
		outputStream =  new FileOutputStream(new File(fileName));
		
		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = is.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		outputStream.flush();
		outputStream.close();
	}
}
