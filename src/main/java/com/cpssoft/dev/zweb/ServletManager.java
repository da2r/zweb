package com.cpssoft.dev.zweb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cpssoft.dev.zweb.action.LoginAction;
import com.cpssoft.dev.zweb.util.SystemUtil;

public class ServletManager extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(request.getMethod() + " " + request.getRequestURI());

		String requestURI = request.getRequestURI();
		if ("/".equals(requestURI)) {
			LoginAction action = new LoginAction();
			action.request = request;
			action.response = response;

			action.login();
			// action.loginSubmit();
			// action.loginRedirect();
		} else if ("/favicon.ico".equals(requestURI)) {
			writeFavicon(response);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void writeFavicon(HttpServletResponse response) throws IOException {
		// TODO Cache bytes
		File file = new File(SystemUtil.getSourceFolder() + "/static/favicon.ico");
		byte[] bytes = Files.readAllBytes(file.toPath());

		response.setHeader("Content-length", "" + bytes.length);
		response.setHeader("Cache-Control", "public, max-age=31536000");
		response.setContentType("image/x-icon");
		response.getOutputStream().write(bytes);
	}

}
