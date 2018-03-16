package com.cpssoft.dev.zweb.action;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

public class LoginAction {

	public HttpServletRequest request;
	public HttpServletResponse response;

	public void login() {
		HttpSession session = request.getSession();

		Long counter = (Long) session.getAttribute("counter");
		if (counter == null)
			counter = 0L;
		counter++;

		session.setAttribute("counter", counter);

		writeResponse("<input name=\"username\"><input type=\"password\" name=\"pass\">");
	}

	public void loginSubmit() {
		System.out.println(request.getMethod());
		System.out.println(request.getRequestURI());
		System.out.println(request.getParameter("id"));

		JsonObject resp = new JsonObject();
		resp.addProperty("s", true);

		JsonObject payload = new JsonObject();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		payload.addProperty("current", now.format(formatter));

		resp.add("d", payload);

		writeResponse(resp);
	}

	public void loginRedirect() {
		sendRedirect("/static/favicon.ico");
	}

	protected void writeResponse(String html) {
		try {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(html);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void writeResponse(JsonObject json) {
		try {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void sendRedirect(String location) {
		try {
			response.sendRedirect(location);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}