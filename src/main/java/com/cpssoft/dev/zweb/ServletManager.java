package com.cpssoft.dev.zweb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletManager extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.setContentType("text/html");
		// response.setStatus(HttpServletResponse.SC_OK);
		// response.getWriter().println("<h1>Hello from HelloServletxx</h1>");

		System.out.println(request.getRequestURI());

		if ("/".equals(request.getRequestURI())) {
			LoginAction action = new LoginAction();
			action.request = request;
			action.response = response;

			action.login();
			// action.loginSubmit();
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
